package ru.serafimarts.jphp.ext.classes;

import javazoom.jl.decoder.JavaLayerException;
import php.runtime.Memory;
import php.runtime.env.Environment;
import php.runtime.ext.core.classes.stream.Stream;
import php.runtime.lang.BaseObject;
import php.runtime.memory.LongMemory;
import php.runtime.memory.ObjectMemory;
import php.runtime.reflection.ClassEntity;
import ru.serafimarts.jphp.ext.AudioExtension;
import ru.serafimarts.jphp.ext.classes.controls.WrapBalanceControls;
import ru.serafimarts.jphp.ext.classes.controls.WrapPanControls;
import ru.serafimarts.jphp.ext.classes.controls.WrapVolumeControls;
import ru.serafimarts.jphp.ext.decorators.Player;

import javax.sound.sampled.LineUnavailableException;
import java.io.BufferedInputStream;
import java.io.InputStream;

import static php.runtime.annotation.Reflection.*;


@Name(AudioExtension.NAMESPACE + "AudioTrack")
public class WrapAudioTrack extends BaseObject {

    public WrapAudioTrack(Environment env) {
        super(env);
    }


    public WrapAudioTrack(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    private Player player;

    @Property
    public ObjectMemory balance;

    @Property
    public ObjectMemory pan;

    @Property
    public ObjectMemory volume;

    @Signature({
            @Arg(value = "stream", typeClass = Stream.CLASS_NAME)
    })
    public Memory __construct(Environment env, Memory... args)
            throws LineUnavailableException, JavaLayerException {

        InputStream stream = Stream.getInputStream(env, args[0]);
        InputStream buffer = new BufferedInputStream(stream);

        this.player  = new Player(buffer);

        this.balance = new ObjectMemory(
            new WrapBalanceControls(env, this.player.getBalanceControls())
        );

        this.pan = new ObjectMemory(
            new WrapPanControls(env, this.player.getPanControls())
        );

        this.volume = new ObjectMemory(
            new WrapVolumeControls(env, this.player.getGainControls(), this.player.getMuteControls())
        );

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
}