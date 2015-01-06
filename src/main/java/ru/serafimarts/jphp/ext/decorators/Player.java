package ru.serafimarts.jphp.ext.decorators;

import javazoom.jl.decoder.*;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import java.io.InputStream;

public class Player {
    private final Object lock = new Object();

    // play\pause
    private boolean played = false;

    // frames count
    private int frames = 2147483647;

    // track ended
    private boolean ended = false;

    private Bitstream bitstream;
    private Decoder decoder;
    private Equalizer equalizer;
    private AudioOutput output;

    private FloatControl gain;
    private FloatControl balance;
    private FloatControl pan;
    private BooleanControl mute;

    public Player(InputStream stream)
            throws JavaLayerException, LineUnavailableException {

        this.bitstream = new Bitstream(stream);
        this.equalizer = new Equalizer();
        this.decoder = new Decoder();
        this.decoder.setEqualizer(this.equalizer);

        {
            /**
             * Требуется для чтения заголовоков файлов
             */

            Header h = bitstream.readFrame();
            SampleBuffer buffer = (SampleBuffer) decoder.decodeFrame(h, bitstream);
            bitstream.closeFrame();
        }

        this.output = new AudioOutput(this.decoder);

        this.gain = (FloatControl) this.output.getLine().getControl(FloatControl.Type.MASTER_GAIN);
        this.balance = (FloatControl) this.output.getLine().getControl(FloatControl.Type.BALANCE);
        this.pan = (FloatControl) this.output.getLine().getControl(FloatControl.Type.PAN);
        this.mute = (BooleanControl) this.output.getLine().getControl(BooleanControl.Type.MUTE);

        this.render();
    }


    public void play() throws JavaLayerException {
        this.played = true;
        this.unmute();
    }


    public void pause() {
        this.mute();
        this.played = false;
        this.output.flush();
    }


    public synchronized void stop() {
        this.ended = true;
        this.output.close();
        try {
            this.bitstream.close();
        } catch (BitstreamException e) {
            ;
        }
    }

    public void toggle()
            throws JavaLayerException {

        if (this.isPlayed()) {
            this.pause();
        } else {
            this.play();
        }
    }

    public void mute() {
        this.mute.setValue(true);
    }

    public void unmute() {
        this.mute.setValue(false);
    }



    public boolean isPlayed() {
        return this.played;
    }

    public boolean isPaused() {
        return !this.played;
    }

    public synchronized boolean isComplete() {
        return this.ended;
    }



    public int getPosition() {
        return this.output.getPosition();
    }

    public FloatControl getGainControls() {
        return this.gain;
    }

    public FloatControl getBalanceControls() {
        return this.balance;
    }

    public FloatControl getPanControls() {
        return this.pan;
    }

    public BooleanControl getMuteControls() { return this.mute; }



    private void render() {
        new Thread() {
            public void run() {
                try {
                    while (frames > 0 && !ended) {
                        if (played) {
                            frames--;
                            renderFrame();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    return;
                }
            }
        }.start();
    }


    private void renderFrame()
        throws BitstreamException, DecoderException, JavaLayerException {
            Header h = bitstream.readFrame();
            if (h == null) return;
            SampleBuffer buffer = (SampleBuffer) decoder.decodeFrame(h, bitstream);
            if (buffer == null) return;
            synchronized (lock) {
                output.write(buffer.getBuffer(), 0, buffer.getBufferLength());
            }
            bitstream.closeFrame();
    }
}
