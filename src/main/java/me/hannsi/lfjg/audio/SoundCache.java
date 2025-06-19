package me.hannsi.lfjg.audio;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.utils.toolkit.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.audio.LFJGOpenALContext.openALDevice;
import static org.lwjgl.openal.AL.createCapabilities;
import static org.lwjgl.openal.AL10.alDistanceModel;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * The SoundCache class is responsible for managing sound buffers, sound sources, and the OpenAL context.
 */
public class SoundCache {
    private final Map<String, SoundData> soundDataList;
    /**
     * -- GETTER --
     * Retrieves the SoundListener associated with this SoundCache.
     *
     * @return The SoundListener.
     */
    @Getter
    private SoundListener listener;
    /**
     * -- SETTER --
     * Sets the OpenAL context for this SoundCache.
     * <p>
     * <p>
     * -- GETTER --
     * Retrieves the OpenAL context associated with this SoundCache.
     *
     * @param context The OpenAL context to set.
     * @return The OpenAL context.
     */
    @Getter
    @Setter
    private long context;

    SoundCache(String desiredDevice) {
        soundDataList = new HashMap<>();

        openALDevice = alcOpenDevice(desiredDevice);
        if (openALDevice == NULL) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }
        ALCCapabilities deviceCaps = ALC.createCapabilities(openALDevice);
        this.context = alcCreateContext(openALDevice, (IntBuffer) null);
        if (context == NULL) {
            throw new IllegalStateException("Failed to create OpenAL context.");
        }
        alcMakeContextCurrent(context);
        createCapabilities(deviceCaps);
    }

    public static SoundCache initSoundCache() {
        return initSoundCache(AudioDevices.DEFAULT);
    }

    public static SoundCache initSoundCache(String desiredDevice) {
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

    /**
     * Cleans up the resources associated with this SoundCache.
     */
    public void cleanup() {
        soundDataList.forEach((name, soundData) -> {
            soundData.cleanup();
        });
        soundDataList.clear();
        if (context != NULL) {
            alcDestroyContext(context);
        }
        if (openALDevice != NULL) {
            alcCloseDevice(openALDevice);
        }

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                String.valueOf(context),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    /**
     * Sets the SoundListener for this SoundCache.
     *
     * @param listener The SoundListener to set.
     */
    public SoundCache setListener(SoundListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * Plays the SoundSource with the specified name if it is not already playing.
     *
     * @param name The name of the sound source.
     */
    public void playSoundData(String name) {
        SoundData soundData = soundDataList.get(name);
        if (soundData != null && !soundData.isPlaying()) {
            soundData.play();
        }
    }

    /**
     * Sets the attenuation model for sound sources.
     *
     * @param model The attenuation model to set.
     */
    public SoundCache setAttenuationModel(int model) {
        alDistanceModel(model);
        return this;
    }

    /**
     * Updates the position and orientation of the SoundListener based on the camera's view matrix.
     *
     * @param camera The Camera object to use for updating the listener's position and orientation.
     */
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

    /**
     * Retrieves the OpenAL device associated with this SoundCache.
     *
     * @return The OpenAL device.
     */
    public long getOpenALDevice() {
        return openALDevice;
    }
}
