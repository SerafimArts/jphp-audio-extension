package ru.serafimarts.jphp.ext.decorators;

import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.*;


public class AudioOutput {
    private boolean open = false;

    private AudioFormat fmt = null;
    private DataLine.Info info = null;
    private SourceDataLine source = null;
    private byte[] byteBuf = new byte[4096];


    public AudioOutput(Decoder decoder)
            throws JavaLayerException {

        this.setOpen(true);


        this.fmt = new AudioFormat((float) decoder.getOutputFrequency(), 16, decoder.getOutputChannels(), true, false);
        this.info = new DataLine.Info(SourceDataLine.class, fmt);

        try {

            Line line = AudioSystem.getLine(this.info);
            if (line instanceof SourceDataLine) {
                this.source = (SourceDataLine) line;
                this.source.open(this.fmt);
                this.source.start();
            }
        } catch (Exception e) {
            throw new JavaLayerException("Cannot obtain source audio line", (Throwable) e);
        }
    }

    public SourceDataLine getLine() {
        return source;
    }

    public AudioFormat getAudioFormat() {
        return fmt;
    }

    public synchronized boolean isOpen() {
        return this.open;
    }

    protected void setOpen(boolean open) {
        this.open = open;
    }

    public int millisecondsToBytes(AudioFormat fmt, int time) {
        return (int) ((double) ((float) time * fmt.getSampleRate() * (float) fmt.getChannels() * (float) fmt.getSampleSizeInBits()) / 8000.0D);
    }

    public void write(short[] samples, int offs, int len) throws JavaLayerException {
        if (this.isOpen()) {
            byte[] b = this.toByteArray(samples, offs, len);
            this.source.write(b, 0, len * 2);
        }
    }


    public synchronized void close() {
        if (this.isOpen()) {
            this.source.close();
            this.setOpen(false);
        }
    }


    /**
     * Тут есть небольшая бага - данные
     * очищаются не сразу и некоторые куски могут отправиться в
     * выходной поток - короче щёлкать может.
     */
    public void flush() {
        if (this.isOpen()) {
            this.source.flush();
        }
    }


    public int getPosition() {
        return (int) (this.source.getMicrosecondPosition() / 1000L);
    }


    protected byte[] getByteArray(int length) {
        if (this.byteBuf.length < length) {
            this.byteBuf = new byte[length + 1024];
        }

        return this.byteBuf;
    }

    protected byte[] toByteArray(short[] samples, int offs, int len) {
        byte[] b = this.getByteArray(len * 2);

        short s;
        for (int idx = 0; len-- > 0; b[idx++] = (byte) (s >>> 8)) {
            s = samples[offs++];
            b[idx++] = (byte) s;
        }

        return b;
    }

}
