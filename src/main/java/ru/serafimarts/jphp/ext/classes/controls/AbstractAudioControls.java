package ru.serafimarts.jphp.ext.classes.controls;


import php.runtime.Memory;
import static php.runtime.annotation.Reflection.*;
import php.runtime.env.Environment;
import php.runtime.lang.BaseObject;
import php.runtime.memory.DoubleMemory;
import php.runtime.reflection.ClassEntity;
import ru.serafimarts.jphp.ext.AudioExtension;

import javax.sound.sampled.FloatControl;

@Name(AudioExtension.NAMESPACE + "controls\\AbstractAudioControls")
abstract public class AbstractAudioControls extends BaseObject {
    public AbstractAudioControls(Environment env) {
        super(env);
    }

    public AbstractAudioControls(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    protected FloatControl controls;

    @Abstract @Signature
    abstract public Memory getValue(Environment env, Memory... args);

    @Abstract @Signature(@Arg("value"))
    abstract public Memory setValue(Environment env, Memory... args);

    @Abstract @Signature
    abstract public Memory getMaximum(Environment env, Memory... args);

    @Abstract @Signature
    abstract public Memory getMinimum(Environment env, Memory... args);


    protected float validate(float value) {

        if (value < this.controls.getMinimum()) {
            value = this.controls.getMinimum();
        } else if (value > this.controls.getMaximum()) {
            value = this.controls.getMaximum();
        }

        return value;
    }
}
