package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;

import javax.sound.sampled.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AudioDevices {
    public static final String DEFAULT = null;

    private Map<Mixer.Info, Boolean> deviceMap;

    public AudioDevices() {
        this.deviceMap = new HashMap<>();

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

            deviceMap.put(mixerInfo, supportsOutput);
        }

        int maxNameLength = deviceMap.keySet().stream()
                .mapToInt(mixerInfo -> mixerInfo.getName().length())
                .max()
                .orElse(10);

        String[] devices = new String[deviceMap.size()];
        AtomicInteger atomicIndex = new AtomicInteger(0);

        deviceMap.forEach((mixerInfo, supportsOutput) -> {
            int index = atomicIndex.getAndIncrement();
            String name = mixerInfo.getName();
            String version = mixerInfo.getVersion();
            String output = supportsOutput ? "Yes" : "No";

            String paddedName = String.format("%-" + maxNameLength + "s", name);
            devices[index] = String.format("[%2d] %s | Version: %-15s | Output: %s", index + 1, paddedName, version, output);
        });

        new LogGenerator("Audio Output Devices", devices).logging(DebugLevel.DEBUG);
    }

    public void cleanup() {
        deviceMap.clear();
        deviceMap = null;

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                hashCode(),
                ""
        ).logging(DebugLevel.DEBUG);
    }
}
