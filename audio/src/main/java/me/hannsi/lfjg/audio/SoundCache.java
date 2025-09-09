package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.toolkit.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.openal.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

public class SoundCache {
    private final long context;
    private final Map<String, SoundData> soundDataList;
    private SoundListener listener;

    SoundCache(String desiredDevice) {
        soundDataList = new HashMap<>();

        LFJGOpenALContext.openALDevice = ALC10.alcOpenDevice(desiredDevice);
        if (LFJGOpenALContext.openALDevice == MemoryUtil.NULL) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }
        ALCCapabilities deviceCaps = ALC.createCapabilities(LFJGOpenALContext.openALDevice);
        this.context = ALC10.alcCreateContext(LFJGOpenALContext.openALDevice, (IntBuffer) null);
        if (context == MemoryUtil.NULL) {
            throw new IllegalStateException("Failed to create OpenAL context.");
        }
        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
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
        ).logging(DebugLevel.DEBUG);

        return this;
    }

    public void cleanup() {
        soundDataList.forEach((name, soundData) -> {
            soundData.cleanup();
        });
        soundDataList.clear();
        if (context != MemoryUtil.NULL) {
            ALC10.alcDestroyContext(context);
        }
        if (LFJGOpenALContext.openALDevice != MemoryUtil.NULL) {
            ALC10.alcCloseDevice(LFJGOpenALContext.openALDevice);
        }

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                String.valueOf(context),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public void playSoundData(String name) {
        SoundData soundData = soundDataList.get(name);
        if (soundData != null && !soundData.isPlaying()) {
            soundData.play();
        }
    }

    public SoundCache setAttenuationModel(int model) {
        AL10.alDistanceModel(model);
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
        return LFJGOpenALContext.openALDevice;
    }

    public long getContext() {
        return context;
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
