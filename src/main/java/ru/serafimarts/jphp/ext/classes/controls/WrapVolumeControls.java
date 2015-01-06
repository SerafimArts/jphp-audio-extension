package ru.serafimarts.jphp.ext.classes.controls;

import php.runtime.Memory;
import php.runtime.env.Environment;
import php.runtime.memory.DoubleMemory;
import php.runtime.reflection.ClassEntity;
import ru.serafimarts.jphp.ext.AudioExtension;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.FloatControl;

import static php.runtime.annotation.Reflection.*;


@Name(AudioExtension.NAMESPACE + "controls\\VolumeControls")
public class WrapVolumeControls extends AbstractAudioControls {

    protected BooleanControl muteControls;

    public WrapVolumeControls(Environment env) {
        super(env);
    }

    public WrapVolumeControls(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    public WrapVolumeControls(Environment env, FloatControl controls, BooleanControl muteControls) {
        super(env);
        this.controls = controls;
        this.muteControls = muteControls;
    }


    @Signature
    private Memory __construct(Environment env, Memory... args) {
        return Memory.NULL;
    }


    @Signature
    public Memory getValue(Environment env, Memory... args) {


        return DoubleMemory.valueOf(
            // -100 / (max-min) * ($value - min) = $percents
            (float) (-100 / this.controls.getMinimum()) * (this.controls.getValue() - this.controls.getMinimum())
        );
    }


    @Signature(@Arg("value"))
    public Memory setValue(Environment env, Memory... args) {
        float value = args[0].toFloat();

        // check value interval
        if (value < 0.0) {
            value = (float) 0.0;
        } else if (value > 100.0) {
            value = (float) 100.0;
        }

        // (max-min) / 100 * $percents + min = $value
        double volume = Math.abs(this.controls.getMinimum() / 100 * value) + this.controls.getMinimum();

        this.muteControls.setValue((boolean)(value < 1.0));

        this.controls.setValue((float) volume);


        return Memory.NULL;
    }


    @Signature
    public Memory getMaximum(Environment env, Memory... args) {
        return DoubleMemory.valueOf(100.0);
    }


    @Signature
    public Memory getMinimum(Environment env, Memory... args) {
        return DoubleMemory.valueOf(0.0);
    }
}