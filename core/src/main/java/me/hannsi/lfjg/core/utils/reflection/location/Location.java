package me.hannsi.lfjg.core.utils.reflection.location;

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
import java.nio.file.Paths;

public record Location(String path, LocationType locationType) {
    public static Location fromFile(String absolutePath) {
        return new Location(absolutePath, LocationType.FILE);
    }

    public static Location fromResource(String resourcePath) {
        String normalizedPath = resourcePath.startsWith("/") || resourcePath.startsWith("\\") ? resourcePath.substring(1) : resourcePath;

        return new Location(normalizedPath, LocationType.RESOURCE);
    }

    public static Location fromURL(String url) {
        return new Location(url, LocationType.URL);
    }

    public InputStream openStream() throws IOException {
        return switch (locationType) {
            case FILE -> new FileInputStream(path);
            case RESOURCE -> Location.class.getClassLoader().getResourceAsStream(path);
            case URL -> new URL(path).openStream();
        };
    }

    public boolean exists() throws IOException {
        return switch (locationType) {
            case FILE -> Files.exists(Paths.get(path));
            case RESOURCE -> {
                try (InputStream is = openStream()) {
                    yield is != null;
                }
            }
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
        if (locationType != LocationType.FILE) {
            throw new UnsupportedOperationException("getFile() supports only FILE location type.");
        }
        return new File(path).getAbsoluteFile();
    }

    public Location getParent() {
        return switch (locationType) {
            case FILE -> {
                File parent = new File(path).getParentFile();
                yield (parent != null) ? new Location(parent.getPath(), locationType) : null;
            }
            case RESOURCE -> {
                String normalizedPath = path.startsWith("/") ? path.substring(1) : path;
                int lastSlash = normalizedPath.lastIndexOf('/');
                yield (lastSlash > 0)
                        ? new Location(normalizedPath.substring(0, lastSlash), locationType)
                        : null;
            }
            case URL -> {
                try {
                    URL url = new URL(path);
                    String file = url.getFile();
                    int lastSlash = file.lastIndexOf('/');
                    yield (lastSlash > 0) ? new Location(new URL(url.getProtocol(), url.getHost(), url.getPort(), file.substring(0, lastSlash)).toString(), locationType) : null;
                } catch (Exception e) {
                    yield null;
                }
            }
        };
    }
}