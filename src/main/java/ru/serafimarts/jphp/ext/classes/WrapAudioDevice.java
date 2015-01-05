package ru.serafimarts.jphp.ext.classes;

import php.runtime.Memory;
import javax.sound.sampled.Port;
import javax.sound.sampled.Mixer;
import php.runtime.env.Environment;
import javax.sound.sampled.AudioSystem;
import php.runtime.memory.StringMemory;
import php.runtime.reflection.ClassEntity;
import ru.serafimarts.jphp.ext.AudioExtension;
import static php.runtime.annotation.Reflection.*;
import org.develnext.jphp.swing.classes.components.support.RootObject;


@Name(AudioExtension.NAMESPACE + "AudioDevice")
public class WrapAudioDevice extends RootObject {
    public static final int MICROPHONE      = 2;
    public static final int LINE_IN         = 4;
    public static final int COMPACT_DISC    = 8;
    public static final int SPEAKER         = 16;
    public static final int HEADPHONE       = 32;
    public static final int LINE_OUT        = 64;



    public WrapAudioDevice(Environment env) {
        super(env);
    }

    public WrapAudioDevice(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    public WrapAudioDevice(Environment env, Mixer.Info mixer) {
        super(env);
        this.info = mixer;
    }


    private Mixer.Info info;
    public Mixer.Info getInfo() { return info; }

    @Signature
    private Memory __construct(Environment env, Memory... args) {
        return Memory.NULL;
    }


    @Signature
    public Memory getName(Environment env, Memory... args) {
        return new StringMemory(info.getName());
    }

    @Signature
    public Memory getVendor(Environment env, Memory... args) {
        return new StringMemory(info.getVendor());
    }

    @Signature
    public Memory getVersion(Environment env, Memory... args) {
        return new StringMemory(info.getVersion());
    }

    @Signature
    public Memory getDescription(Environment env, Memory... args) {
        return new StringMemory(info.getDescription());
    }

    @Signature({
            @Arg("supportedType")
    })
    public Memory isSupported(Environment env, Memory... args)
        throws RuntimeException {

        int type = args[0].toInteger();
        Port.Info portInfo;


        switch (type) {
            case MICROPHONE:
                portInfo = Port.Info.MICROPHONE;
                break;
            case LINE_IN:
                portInfo = Port.Info.LINE_IN;
                break;
            case COMPACT_DISC:
                portInfo = Port.Info.COMPACT_DISC;
                break;
            case SPEAKER:
                portInfo = Port.Info.SPEAKER;
                break;
            case HEADPHONE:
                portInfo = Port.Info.HEADPHONE;
                break;
            case LINE_OUT:
                portInfo = Port.Info.LINE_OUT;
                break;
            default:
                throw new RuntimeException("Undefined supported type.");
        }

        Mixer mixer = AudioSystem.getMixer(info);

        return mixer.isLineSupported(portInfo)
                ? Memory.TRUE
                : Memory.FALSE;
    }

}