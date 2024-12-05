package me.hannsi.lfjg.utils.reflection;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.utils.buffer.ByteUtil;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Objects;

public class ResourcesLocation {
    private String path;

    public ResourcesLocation(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public InputStream getInputStream() {
        return Frame.class.getClassLoader().getResourceAsStream(path);
    }

    public byte[] getBytes() {
        return ByteUtil.convertInputStreamToByteArray(Objects.requireNonNull(getInputStream()));
    }
}
