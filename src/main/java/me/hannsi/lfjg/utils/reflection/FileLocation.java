package me.hannsi.lfjg.utils.reflection;

import me.hannsi.lfjg.utils.buffer.ByteUtil;

import java.io.InputStream;
import java.util.Objects;

public class FileLocation {
    public String path;

    public FileLocation(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public InputStream getInputStream() {
        return ByteUtil.convertStringPathToInputStream(path);
    }

    public byte[] getBytes() {
        return ByteUtil.convertInputStreamToByteArray(Objects.requireNonNull(getInputStream()));
    }
}
