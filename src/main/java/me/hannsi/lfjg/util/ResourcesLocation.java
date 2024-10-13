package me.hannsi.lfjg.util;

import me.hannsi.lfjg.frame.Frame;

import java.io.InputStream;
import java.util.Objects;

public class ResourcesLocation {
    private String path;
    private InputStream inputStream;

    public ResourcesLocation(String path) {
        this.path = path;
        this.inputStream = Frame.class.getClassLoader().getResourceAsStream(path);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public byte[] getBytes() {
        return ByteUtil.convertInputStreamToByteArray(Objects.requireNonNull(inputStream));
    }
}
