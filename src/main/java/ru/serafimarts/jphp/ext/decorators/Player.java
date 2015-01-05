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


    public void play()
            throws JavaLayerException {
        this.output.flush();
        this.played = true;
        this.unmute();
    }


    public void pause() {
        this.mute();
        this.output.flush();
        this.played = false;
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

    public float getVolume() {
        // 100 / (max-min) * ($value - min) = $percents

        return 100 / this.gain.getMinimum() *
                (this.gain.getValue() - this.gain.getMinimum());
    }

    public void setVolume(float value) {
        // (max-min) / 100 * $percents + min = $value

        if (value < 0.0) {
            value = (float) 0.0;
        } else if (value > 100.0) {
            value = (float) 100.0;
        }

        double volume = Math.abs(this.gain.getMinimum() / 100 * value) + this.gain.getMinimum();

        if (value < 1.0) {
            this.mute();
        } else {
            this.unmute();
        }

        this.gain.setValue((float) volume);
    }

    public float getVolumeMinimum() {
        return (float) 0.0;
    }

    public float getVolumeMaximum() {
        return (float) 100.0;
    }


    public float getBalance() {
        return this.balance.getValue();
    }

    public void setBalance(float value) {
        if (value < this.balance.getMinimum()) {
            value = this.balance.getMinimum();
        } else if (value > this.balance.getMaximum()) {
            value = this.balance.getMaximum();
        }

        this.balance.setValue(value);
    }

    public float getBalanceMinimum() {
        return this.balance.getMinimum();
    }

    public float getBalanceMaximum() {
        return this.balance.getMaximum();
    }


    public float getPan() {
        return this.pan.getValue();
    }

    public void setPan(float value) {
        if (value < this.pan.getMinimum()) {
            value = this.pan.getMinimum();
        } else if (value > this.pan.getMaximum()) {
            value = this.pan.getMaximum();
        }

        this.pan.setValue(value);
    }

    public float getPanMinimum() {
        return this.pan.getMinimum();
    }

    public float getPanMaximum() {
        return this.pan.getMaximum();
    }


    private void render() {
        new Thread() {
            public void run() {
                try {
                    while (frames > 0 && !ended) {
                        if (played) {
                            frames--;
                            //System.out.println(frames);
                            //System.out.println(this.played);

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
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();
    }
}
