package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.toolkit.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.audio.LFJGAudioContext.CONTEXT;
import static me.hannsi.lfjg.audio.LFJGAudioContext.OPEN_AL_DEVICE;
import static org.lwjgl.openal.AL10.alDistanceModel;
import static org.lwjgl.openal.ALC10.alcCloseDevice;
import static org.lwjgl.openal.ALC10.alcDestroyContext;

public class SoundCache {
    private final Map<String, SoundData> soundDataList;
    private SoundListener listener;

    SoundCache(String desiredDevice) {
        soundDataList = new HashMap<>();
    }

    public static SoundCache createSoundCache() {
        return createSoundCache(AudioDevices.DEFAULT);
    }

    public static SoundCache createSoundCache(String desiredDevice) {
        return new SoundCache(desiredDevice);
    }

    public SoundCache createCache(String name, SoundData soundData) {
        soundDataList.put(name, soundData);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                name,
                ""
        ).logging(getClass(), DebugLevel.DEBUG);

        return this;
    }

    public void cleanup() {
        soundDataList.forEach((name, soundData) -> {
            soundData.cleanup();
        });
        soundDataList.clear();
        if (CONTEXT != MemoryUtil.NULL) {
            alcDestroyContext(CONTEXT);
        }
        if (OPEN_AL_DEVICE != MemoryUtil.NULL) {
            alcCloseDevice(OPEN_AL_DEVICE);
        }

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                String.valueOf(CONTEXT),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);
    }

    public void playSoundData(String name) {
        SoundData soundData = soundDataList.get(name);
        if (soundData != null && !soundData.isPlaying()) {
            soundData.play();
        }
    }

    public SoundCache setAttenuationModel(int model) {
        alDistanceModel(model);
        return this;
    }

    public void updateListenerPosition(Camera camera) {
        Matrix4f viewMatrix = camera.getViewMatrix();
        listener.setPosition(camera.getPosition());
        Vector3f at = new Vector3f();
        viewMatrix.positiveZ(at).negate();
        Vector3f up = new Vector3f();
        viewMatrix.positiveY(up);
        listener.setOrientation(at, up);
    }

    public SoundData getSoundData(String name) {
        return soundDataList.get(name);
    }

    public long getOpenALDevice() {
        return OPEN_AL_DEVICE;
    }


    public Map<String, SoundData> getSoundDataList() {
        return soundDataList;
    }

    public SoundListener getListener() {
        return listener;
    }

    public SoundCache setListener(SoundListener listener) {
        this.listener = listener;
        return this;
    }
}
