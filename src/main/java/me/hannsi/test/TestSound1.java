package me.hannsi.test;

import me.hannsi.lfjg.audio.*;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.SoundEffectType;
import me.hannsi.lfjg.utils.type.types.SoundFilterType;
import me.hannsi.lfjg.utils.type.types.SoundLoaderType;
import org.joml.Vector3f;
import org.lwjgl.openal.AL11;

public class TestSound1 implements IScene {
    public Scene scene;
    public Frame frame;

    SoundCache soundCache;

    public TestSound1(Frame frame) {
        this.scene = new Scene("TestSound1", this);
        this.frame = frame;
    }

    @Override
    public void init() {
        AudioDevices audioDevices = new AudioDevices();
        soundCache = SoundCache.initSoundCache()
                .setAttenuationModel(AL11.AL_EXPONENT_DISTANCE)
                .setListener(new SoundListener(new Vector3f(0, 0, 0)))
                .createCache(
                        "test",
                        SoundData.createSoundData()
                                .loop(false)
                                .relative(false)
                                .position(new Vector3f(0, 0, 0))
                                .createSoundPCM(SoundLoaderType.STB_VORBIS, new ResourcesLocation("sound/test.ogg"))
                );

        SoundEffect soundEffect = SoundEffect.builderCreate()
                .initEffect()
                .initFilter()
                .addSoundEffect(SoundEffectType.REVERB)
                .addSoundFilter(SoundFilterType.LOWPASS_GAIN, 0.5f);
    }

    @Override
    public void drawFrame() {
        soundCache.getSoundData("test").gain(0.05f);
        soundCache.playSoundData("test");
    }

    @Override
    public void stopFrame() {
        soundCache.cleanup();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
