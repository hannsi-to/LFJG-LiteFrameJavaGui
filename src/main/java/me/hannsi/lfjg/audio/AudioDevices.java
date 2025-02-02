package me.hannsi.lfjg.audio;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.openal.ALC10.ALC_DEVICE_SPECIFIER;
import static org.lwjgl.openal.ALC10.alcGetString;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioDevices {
    public static final ByteBuffer DEFAULT = null;
    private Map<String, ByteBuffer> deviceMap;

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

    public ByteBuffer getByteBuffer(String deviceName) {
        return deviceMap.get(deviceName);
    }

    public Map<String, ByteBuffer> getDeviceMap() {
        return deviceMap;
    }

    public void setDeviceMap(Map<String, ByteBuffer> deviceMap) {
        this.deviceMap = deviceMap;
    }
}
