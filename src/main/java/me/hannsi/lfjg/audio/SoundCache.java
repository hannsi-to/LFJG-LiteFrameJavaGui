package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.utils.toolkit.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final List<SoundBuffer> soundBufferList;
    private final Map<String, SoundSource> soundSourceMap;
    private long context;
    private SoundListener listener;

    /**
     * Constructs a SoundCache object using the default audio device.
     */
    public SoundCache() {
        this(AudioDevices.DEFAULT);
    }

    /**
     * Constructs a SoundCache object using the specified audio device.
     *
     * @param deviceByte The ByteBuffer representation of the audio device.
     */
    public SoundCache(ByteBuffer deviceByte) {
        soundBufferList = new ArrayList<>();
        soundSourceMap = new HashMap<>();

        openALDevice = alcOpenDevice(deviceByte);
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

    /**
     * Adds a SoundBuffer to the sound buffer list.
     *
     * @param soundBuffer The SoundBuffer to add.
     */
    public void addSoundBuffer(SoundBuffer soundBuffer) {
        this.soundBufferList.add(soundBuffer);
    }

    /**
     * Adds a SoundSource to the sound source map with the specified name.
     *
     * @param name        The name of the sound source.
     * @param soundSource The SoundSource to add.
     */
    public void addSoundSource(String name, SoundSource soundSource) {
        this.soundSourceMap.put(name, soundSource);
    }

    /**
     * Creates a sound cache by adding a SoundBuffer and SoundSource with the specified name.
     *
     * @param name        The name of the sound cache.
     * @param soundBuffer The SoundBuffer to add.
     * @param soundSource The SoundSource to add.
     */
    public void createCache(String name, SoundBuffer soundBuffer, SoundSource soundSource) {
        addSoundBuffer(soundBuffer);
        addSoundSource(name, soundSource);

        LogGenerator logGenerator = new LogGenerator("SoundCache Debug Message", "Source: SoundCache", "Type: Cache Creation", "Name: " + name, "Severity: Info", "Message: Create sound cache");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Cleans up the resources associated with this SoundCache.
     */
    public void cleanup() {
        soundSourceMap.values().forEach(SoundSource::cleanup);
        soundSourceMap.clear();
        soundBufferList.forEach(SoundBuffer::cleanup);
        soundBufferList.clear();
        if (context != NULL) {
            alcDestroyContext(context);
        }
        if (openALDevice != NULL) {
            alcCloseDevice(openALDevice);
        }

        LogGenerator logGenerator = new LogGenerator("SoundCache", "Source: SoundCache", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: SoundCache cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Retrieves the SoundListener associated with this SoundCache.
     *
     * @return The SoundListener.
     */
    public SoundListener getListener() {
        return this.listener;
    }

    /**
     * Sets the SoundListener for this SoundCache.
     *
     * @param listener The SoundListener to set.
     */
    public void setListener(SoundListener listener) {
        this.listener = listener;
    }

    /**
     * Retrieves the SoundSource with the specified name.
     *
     * @param name The name of the sound source.
     * @return The SoundSource, or null if not found.
     */
    public SoundSource getSoundSource(String name) {
        return this.soundSourceMap.get(name);
    }

    /**
     * Plays the SoundSource with the specified name if it is not already playing.
     *
     * @param name The name of the sound source.
     */
    public void playSoundSource(String name) {
        SoundSource soundSource = this.soundSourceMap.get(name);
        if (soundSource != null && !soundSource.isPlaying()) {
            soundSource.play();
        }
    }

    /**
     * Removes the SoundSource with the specified name from the sound source map.
     *
     * @param name The name of the sound source to remove.
     */
    public void removeSoundSource(String name) {
        this.soundSourceMap.remove(name);
    }

    /**
     * Sets the attenuation model for sound sources.
     *
     * @param model The attenuation model to set.
     */
    public void setAttenuationModel(int model) {
        alDistanceModel(model);
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

    /**
     * Retrieves the list of SoundBuffers in this SoundCache.
     *
     * @return The list of SoundBuffers.
     */
    public List<SoundBuffer> getSoundBufferList() {
        return soundBufferList;
    }

    /**
     * Retrieves the map of SoundSources in this SoundCache.
     *
     * @return The map of SoundSources.
     */
    public Map<String, SoundSource> getSoundSourceMap() {
        return soundSourceMap;
    }

    /**
     * Retrieves the OpenAL context associated with this SoundCache.
     *
     * @return The OpenAL context.
     */
    public long getContext() {
        return context;
    }

    /**
     * Sets the OpenAL context for this SoundCache.
     *
     * @param context The OpenAL context to set.
     */
    public void setContext(long context) {
        this.context = context;
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
