package ru.serafimarts.jphp.ext.classes;

import javazoom.jl.decoder.JavaLayerException;
import php.runtime.Memory;
import php.runtime.env.Environment;
import php.runtime.ext.core.classes.stream.Stream;
import php.runtime.lang.BaseObject;
import php.runtime.memory.DoubleMemory;
import php.runtime.memory.LongMemory;
import php.runtime.memory.ObjectMemory;
import php.runtime.reflection.ClassEntity;
import ru.serafimarts.jphp.ext.AudioExtension;
import ru.serafimarts.jphp.ext.decorators.Player;

import javax.sound.sampled.LineUnavailableException;
import java.io.BufferedInputStream;
import java.io.InputStream;

import static php.runtime.annotation.Reflection.*;


@Name(AudioExtension.NAMESPACE + "AudioTrack")
public class WrapAudioTrack extends BaseObject {


    private Player player;

    public WrapAudioTrack(Environment env) {
        super(env);
    }


    public WrapAudioTrack(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    @Signature({
            @Arg(value = "stream", typeClass = Stream.CLASS_NAME)
    })
    public Memory __construct(Environment env, Memory... args)
            throws LineUnavailableException, JavaLayerException {

        InputStream stream = Stream.getInputStream(env, args[0]);
        InputStream buffer = new BufferedInputStream(stream);

        player = new Player(buffer);

        return Memory.NULL;
    }


    @Signature
    public Memory play(Environment env, Memory... args)
            throws JavaLayerException {
        player.play();
        return ObjectMemory.valueOf(this);
    }

    @Signature
    public Memory pause(Environment env, Memory... args) {
        player.pause();
        return ObjectMemory.valueOf(this);
    }

    @Signature
    public Memory toggle(Environment env, Memory... args)
            throws JavaLayerException {
        player.toggle();
        return ObjectMemory.valueOf(this);
    }

    @Signature
    public Memory stop(Environment env, Memory... args) {
        player.stop();
        return ObjectMemory.valueOf(this);
    }

    @Signature
    public Memory mute(Environment env, Memory... args) {
        player.mute();
        return ObjectMemory.valueOf(this);
    }

    @Signature
    public Memory unmute(Environment env, Memory... args) {
        player.unmute();
        return ObjectMemory.valueOf(this);
    }


    @Signature
    public Memory isPlayed(Environment env, Memory... args) {
        return player.isPlayed()
                ? Memory.TRUE
                : Memory.FALSE;
    }

    @Signature
    public Memory isPaused(Environment env, Memory... args) {
        return player.isPaused()
                ? Memory.TRUE
                : Memory.FALSE;
    }

    @Signature
    public Memory isComplete(Environment env, Memory... args) {
        return player.isComplete()
                ? Memory.TRUE
                : Memory.FALSE;
    }


    @Signature
    public Memory getPosition(Environment env, Memory... args) {
        return LongMemory.valueOf(player.getPosition());
    }


    @Signature
    public Memory setVolume(Environment env, Memory... args) {
        this.player.setVolume(args[0].toFloat());
        return ObjectMemory.valueOf(this);
    }

    @Signature
    public Memory getVolume(Environment env, Memory... args) {
        return DoubleMemory.valueOf(this.player.getVolume());
    }

    @Signature
    public Memory getVolumeMinimum(Environment env, Memory... args) {
        return DoubleMemory.valueOf(this.player.getVolumeMinimum());
    }

    @Signature
    public Memory getVolumeMaximum(Environment env, Memory... args) {
        return DoubleMemory.valueOf(this.player.getVolumeMaximum());
    }


    @Signature
    public Memory setPan(Environment env, Memory... args) {
        this.player.setPan(args[0].toFloat());
        return ObjectMemory.valueOf(this);
    }

    @Signature
    public Memory getPan(Environment env, Memory... args) {
        return DoubleMemory.valueOf(this.player.getPan());
    }

    @Signature
    public Memory getPanMinimum(Environment env, Memory... args) {
        return DoubleMemory.valueOf(this.player.getPanMinimum());
    }

    @Signature
    public Memory getPanMaximum(Environment env, Memory... args) {
        return DoubleMemory.valueOf(this.player.getPanMaximum());
    }


    @Signature
    public Memory setBalance(Environment env, Memory... args) {
        this.player.setBalance(args[0].toFloat());
        return ObjectMemory.valueOf(this);
    }

    @Signature
    public Memory getBalance(Environment env, Memory... args) {
        return DoubleMemory.valueOf(this.player.getBalance());
    }

    @Signature
    public Memory getBalanceMinimum(Environment env, Memory... args) {
        return DoubleMemory.valueOf(this.player.getBalanceMinimum());
    }

    @Signature
    public Memory getBalanceMaximum(Environment env, Memory... args) {
        return DoubleMemory.valueOf(this.player.getBalanceMaximum());
    }
}