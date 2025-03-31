package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.debug.debug.log.LogGenerator;
import me.hannsi.lfjg.debug.debug.system.DebugLevel;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.openal.ALC10.ALC_DEVICE_SPECIFIER;
import static org.lwjgl.openal.ALC10.alcGetString;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * The AudioDevices class is responsible for managing audio devices and their corresponding ByteBuffer representations.
 */
public class AudioDevices {
    public static final ByteBuffer DEFAULT = null;
    private Map<String, ByteBuffer> deviceMap;

    /**
     * Constructs an AudioDevices object and initializes the device map with available audio devices.
     */
    public AudioDevices() {
        this.deviceMap = new HashMap<>();

        String deviceList = alcGetString(NULL, ALC_DEVICE_SPECIFIER);
        assert deviceList != null;
        String[] devices = deviceList.split("\0");

        for (String device : devices) {
            if (!device.isEmpty()) {
                ByteBuffer deviceNameBuffer = ByteBuffer.wrap(device.getBytes(StandardCharsets.UTF_8));
                deviceMap.put(device, deviceNameBuffer);
            }
        }
    }

    public void cleanup() {
        deviceMap.clear();

        LogGenerator logGenerator = new LogGenerator("AudioDevices", "Source: AudioDevices", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: Audio device cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Retrieves the ByteBuffer representation of the specified audio device.
     *
     * @param deviceName The name of the audio device.
     * @return The ByteBuffer representation of the audio device, or null if not found.
     */
    public ByteBuffer getByteBuffer(String deviceName) {
        return deviceMap.get(deviceName);
    }

    /**
     * Retrieves the map of audio devices and their corresponding ByteBuffer representations.
     *
     * @return The map of audio devices and their ByteBuffer representations.
     */
    public Map<String, ByteBuffer> getDeviceMap() {
        return deviceMap;
    }

    /**
     * Sets the map of audio devices and their corresponding ByteBuffer representations.
     *
     * @param deviceMap The map of audio devices and their ByteBuffer representations.
     */
    public void setDeviceMap(Map<String, ByteBuffer> deviceMap) {
        this.deviceMap = deviceMap;
    }
}
