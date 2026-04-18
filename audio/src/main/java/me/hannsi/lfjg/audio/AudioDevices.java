package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.reference.LongRef;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.MemoryUtil;

import javax.sound.sampled.*;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.openal.AL.createCapabilities;
import static org.lwjgl.openal.ALC.createCapabilities;
import static org.lwjgl.openal.ALC10.*;

public class AudioDevices {
    private final LongRef devicePointer;
    private final LongRef context;
    private Map<String, Boolean> deviceMap;

    public AudioDevices(LongRef devicePointer, LongRef context) {
        this.devicePointer = devicePointer;
        this.context = context;
        this.deviceMap = new HashMap<>();

        LogGenerator logGenerator = new LogGenerator("Audio Device");
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (Mixer.Info mixerInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);

            Line.Info[] sourceLineInfo = mixer.getSourceLineInfo();
            boolean supportsOutput = false;
            for (Line.Info info : sourceLineInfo) {
                if (info instanceof DataLine.Info dataLineInfo) {
                    if (SourceDataLine.class.isAssignableFrom(dataLineInfo.getLineClass())) {
                        supportsOutput = true;
                        break;
                    }
                }
            }

            logGenerator.text("DeviceName: " + mixerInfo.getName() + ", Version: " + mixerInfo.getVersion() + ", SupportOutput: " + supportsOutput);
            deviceMap.put(mixerInfo.getName(), supportsOutput);
        }

        logGenerator.logging(getClass(), DebugLevel.DEBUG);
    }

    public void openDevice(String deviceName) {
        devicePointer.setValue(alcOpenDevice(deviceName));
        if (devicePointer.getValue() == MemoryUtil.NULL) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }


        ALCCapabilities deviceCaps = createCapabilities(devicePointer.getValue());
        context.setValue(alcCreateContext(devicePointer.getValue(), (IntBuffer) null));
        if (context.getValue() == MemoryUtil.NULL) {
            throw new IllegalStateException("Failed to create OpenAL context.");
        }

        alcMakeContextCurrent(context.getValue());
        createCapabilities(deviceCaps);
    }

    public void openDevice(int index) {
        if (index == -1) {
            openDevice(null);
        } else {
            int counter = 0;
            for (Map.Entry<String, Boolean> entry : deviceMap.entrySet()) {
                if (counter == index) {
                    openDevice(entry.getKey());

                    break;
                }

                counter++;
            }
        }
    }

    public Map<String, Boolean> getDeviceMap() {
        return deviceMap;
    }

    public void cleanup() {
        deviceMap.clear();
        deviceMap = null;

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                hashCode(),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);
    }
}
