package me.hannsi.lfjg.audio;

import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

import static org.lwjgl.openal.AL.createCapabilities;
import static org.lwjgl.openal.ALC.createCapabilities;
import static org.lwjgl.openal.ALC10.*;

public class LFJGAudioContext {
    public static String DEFAULT_DEVICE = AudioDevices.DEFAULT;
    public static long OPEN_AL_DEVICE;
    public static long CONTEXT;

    public static void init() {
        OPEN_AL_DEVICE = alcOpenDevice(DEFAULT_DEVICE);
        if (OPEN_AL_DEVICE == MemoryUtil.NULL) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }

        ALCCapabilities deviceCaps = createCapabilities(OPEN_AL_DEVICE);
        CONTEXT = alcCreateContext(OPEN_AL_DEVICE, (IntBuffer) null);
        if (CONTEXT == MemoryUtil.NULL) {
            throw new IllegalStateException("Failed to create OpenAL context.");
        }

        alcMakeContextCurrent(CONTEXT);
        createCapabilities(deviceCaps);
    }
}
