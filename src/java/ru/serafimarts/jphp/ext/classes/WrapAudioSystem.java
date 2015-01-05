package ru.serafimarts.jphp.ext.classes;


import php.runtime.Memory;
import javax.sound.sampled.Mixer;
import php.runtime.env.Environment;
import php.runtime.memory.ArrayMemory;
import php.runtime.memory.LongMemory;
import php.runtime.memory.ObjectMemory;
import javax.sound.sampled.AudioSystem;
import php.runtime.reflection.ClassEntity;
import ru.serafimarts.jphp.ext.AudioExtension;
import static php.runtime.annotation.Reflection.*;
import org.develnext.jphp.swing.classes.components.support.RootObject;


@Name(AudioExtension.NAMESPACE + "AudioSystem")
public class WrapAudioSystem extends RootObject {
    public WrapAudioSystem(Environment env) {
        super(env);
    }

    public WrapAudioSystem(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }


    @Signature
    public Memory __construct(Environment env, Memory... args) {
        return Memory.NULL;
    }


    protected static Memory getFilteredDevices(Environment env, String filter)
    {
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        ArrayMemory devices = new ArrayMemory();

        for (short i = 0; i < mixerInfo.length; i++) {
            if (mixerInfo[i].getDescription().contains(filter)) {
                devices.add(new ObjectMemory(new WrapAudioDevice(env, mixerInfo[i])));
            }
        }

        return devices;
    }

    @Signature({
            @Arg(value = "supported", optional = @Optional("null"))
    })
    public static Memory getDevices(Environment env, Memory... args)
            throws RuntimeException
    {
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        ArrayMemory devices    = new ArrayMemory();

        int supported = args[0].toInteger();

        for (short i = 0; i < mixerInfo.length; i++) {
            WrapAudioDevice device = new WrapAudioDevice(env, mixerInfo[i]);
            if (supported == 0 || device.isSupported(env, LongMemory.valueOf(supported)) == Memory.TRUE) {
                devices.add(new ObjectMemory(device));
            }
        }

        return devices;
    }

}