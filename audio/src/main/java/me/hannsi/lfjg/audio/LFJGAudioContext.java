package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.core.utils.reflection.reference.LongRef;

public class LFJGAudioContext {
    public static AudioDevices audioDevices;
    public static long DEVICE_POINTER;
    public static long CONTEXT;

    public static void init() {
        LongRef devicePointer = new LongRef();
        LongRef context = new LongRef();
        audioDevices = new AudioDevices(devicePointer, context);
        audioDevices.openDevice(null);
    }
}
