package ru.serafimarts.jphp.ext.classes.controls;

import php.runtime.Memory;
import php.runtime.env.Environment;
import php.runtime.memory.DoubleMemory;
import php.runtime.reflection.ClassEntity;
import ru.serafimarts.jphp.ext.AudioExtension;

import javax.sound.sampled.FloatControl;
import static php.runtime.annotation.Reflection.*;


@Name(AudioExtension.NAMESPACE + "controls\\BalanceControls")
public class WrapBalanceControls extends AbstractAudioControls {

    public WrapBalanceControls(Environment env) {
        super(env);
    }

    public WrapBalanceControls(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    public WrapBalanceControls(Environment env, FloatControl controls) {
        super(env);
        this.controls = controls;
    }


    @Signature
    private Memory __construct(Environment env, Memory... args) {
        return Memory.NULL;
    }


    @Signature
    public Memory getValue(Environment env, Memory... args) {
        return DoubleMemory.valueOf(this.controls.getValue());
    }


    @Signature(@Arg("value"))
    public Memory setValue(Environment env, Memory... args) {
        this.controls.setValue(this.validate(args[0].toFloat()));
        return Memory.NULL;
    }


    @Signature
    public Memory getMaximum(Environment env, Memory... args) {
        return DoubleMemory.valueOf(this.controls.getMaximum());
    }


    @Signature
    public Memory getMinimum(Environment env, Memory... args) {
        return DoubleMemory.valueOf(this.controls.getMinimum());
    }
}