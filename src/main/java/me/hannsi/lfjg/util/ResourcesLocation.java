package me.hannsi.lfjg.util;

import me.hannsi.lfjg.frame.Frame;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Objects;

public class ResourcesLocation {
    private String path;
    private String filePath;
    private InputStream inputStream;
    private ByteBuffer byteBuffer;

    public ResourcesLocation(String path) {
        this.path = "/" + path;
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
}
