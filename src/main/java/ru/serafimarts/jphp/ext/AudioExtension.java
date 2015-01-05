package ru.serafimarts.jphp.ext;

import org.develnext.jphp.genapi.DocGenerator;
import php.runtime.env.CompileScope;
import php.runtime.ext.support.Extension;
import ru.serafimarts.jphp.ext.classes.WrapAudioDevice;
import ru.serafimarts.jphp.ext.classes.WrapAudioSystem;
import ru.serafimarts.jphp.ext.classes.WrapAudioTrack;

import java.io.File;

public class AudioExtension extends Extension {
    public final static String NAMESPACE = "php\\audio\\";

    @Override
    public String getName() {
        return "AudioExtension";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }


    @Override
    public void onRegister(CompileScope scope) {
        this.registerClass(scope, WrapAudioDevice.class);
        this.registerClass(scope, WrapAudioSystem.class);
        this.registerClass(scope, WrapAudioTrack.class);
    }
}
