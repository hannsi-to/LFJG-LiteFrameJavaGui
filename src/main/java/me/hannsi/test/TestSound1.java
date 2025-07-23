package me.hannsi.test;

import me.hannsi.lfjg.audio.*;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;
import org.joml.Vector3f;

import static org.lwjgl.openal.AL11.AL_EXPONENT_DISTANCE;

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
                .setAttenuationModel(AL_EXPONENT_DISTANCE)
                .setListener(new SoundListener(new Vector3f(0, 0, 0)))
                .createCache(
                        "test",
                        SoundData.createSoundData()
                                .loop(true)
                                .relative(true)
                                .position(new Vector3f(0, 0, 0))
                                .createSoundPCM(SoundLoaderType.STB_VORBIS, Location.fromResource("sound/test.ogg"))
                                .attachBuffer()
                                .attachEffect(
                                        SoundEffect.builderCreate()
                                                .initEffect()
                                                .initFilter()
                                                .addSoundEffect(SoundEffectType.REVERB)
                                                .addSoundFilter(SoundFilterType.LOWPASS_GAIN, 0.5f)
                                )
                );
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
