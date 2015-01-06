package ru.serafimarts.jphp.ext;

import php.runtime.env.CompileScope;
import php.runtime.ext.support.Extension;
import ru.serafimarts.jphp.ext.classes.*;
import ru.serafimarts.jphp.ext.classes.controls.AbstractAudioControls;
import ru.serafimarts.jphp.ext.classes.controls.WrapBalanceControls;
import ru.serafimarts.jphp.ext.classes.controls.WrapPanControls;
import ru.serafimarts.jphp.ext.classes.controls.WrapVolumeControls;


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


        this.registerClass(scope, AbstractAudioControls.class);
        this.registerClass(scope, WrapBalanceControls.class);
        this.registerClass(scope, WrapPanControls.class);
        this.registerClass(scope, WrapVolumeControls.class);
    }
}
