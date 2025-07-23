package me.hannsi.lfjg.core.utils.reflection.location;

import me.hannsi.lfjg.core.manager.WorkspaceManager;
import me.hannsi.lfjg.core.utils.type.types.LocationType;
import org.lwjgl.BufferUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public record Location(String path, LocationType locationType) {

    public static Location fromFile(String absolutePath) {
        return new Location(absolutePath, LocationType.FILE);
    }

    public static Location fromResource(String resourcePath) {
        String normalizedPath = resourcePath.startsWith("/") || resourcePath.startsWith("\\") ? resourcePath.substring(1) : resourcePath;
        Path fullPath = Paths.get(WorkspaceManager.currentWorkspace, normalizedPath);

        return new Location(fullPath.toString(), LocationType.RESOURCE);
    }

    public static Location fromURL(String url) {
        return new Location(url, LocationType.URL);
    }

    public InputStream openStream() throws IOException {
        return switch (locationType) {
            case FILE, RESOURCE -> new FileInputStream(path);
            case URL -> new URL(path).openStream();
        };
    }

    public boolean exists() throws IOException {
        return switch (locationType) {
            case FILE, RESOURCE -> Files.exists(Paths.get(path));
            case URL -> {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(path).openConnection();
                    httpURLConnection.setRequestMethod("HEAD");
                    httpURLConnection.connect();
                    yield httpURLConnection.getResponseCode() == 200;
                } catch (Exception e) {
                    yield false;
                }
            }
        };
    }

    public URL getURL() throws MalformedURLException {
        if (locationType != LocationType.URL) {
            throw new IllegalStateException("LocationType is not URL: " + locationType);
        }
        return new URL(path);
    }

    public byte[] getBytes() {
        try (InputStream is = openStream()) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ByteBuffer getByteBuffer() {
        byte[] bytes = getBytes();
        ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

    public File getFile() {
        if (locationType == LocationType.URL) {
            throw new UnsupportedOperationException("Only FILE or RESOURCE supports getFile()");
        }
        return new File(path).getAbsoluteFile();
    }

    public Location getParent() {
        File parent = new File(path).getParentFile();
        return (parent != null) ? new Location(parent.getPath(), locationType) : null;
    }
}