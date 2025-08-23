package me.hannsi.lfjg.core.utils.reflection.location;

import me.hannsi.lfjg.core.utils.type.types.LocationType;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public final class Location {
    private final String path;
    private final LocationType locationType;

    public Location(String path, LocationType locationType) {
        this.path = path;
        this.locationType = locationType;
    }

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
        switch (locationType) {
            case FILE:
                return new FileInputStream(path);
            case RESOURCE:
                return Location.class.getClassLoader().getResourceAsStream(path);
            case URL:
                return new URL(path).openStream();
            default:
                throw new IllegalArgumentException();
        }
    }

    public boolean exists() throws IOException {
        switch (locationType) {
            case FILE:
                return Files.exists(Paths.get(path));
            case RESOURCE:
                try (InputStream is = openStream()) {
                    return is != null;
                }
            case URL:
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(path).openConnection();
                    httpURLConnection.setRequestMethod("HEAD");
                    httpURLConnection.connect();
                    return httpURLConnection.getResponseCode() == 200;
                } catch (Exception e) {
                    return false;
                }
            default:
                throw new IllegalArgumentException();
        }
    }

    public URL getURL() throws MalformedURLException {
        if (locationType != LocationType.URL) {
            throw new IllegalStateException("LocationType is not URL: " + locationType);
        }
        return new URL(path);
    }

    public byte[] getBytes() {
        try (InputStream is = openStream();
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            byte[] data = new byte[8192];
            int nRead;
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            return buffer.toByteArray();
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
        switch (locationType) {
            case FILE: {
                File parent = new File(path).getParentFile();
                return (parent != null) ? new Location(parent.getPath(), locationType) : null;
            }
            case RESOURCE: {
                String normalizedPath = path.startsWith("/") ? path.substring(1) : path;
                int lastSlash = normalizedPath.lastIndexOf('/');
                return (lastSlash > 0) ? new Location(normalizedPath.substring(0, lastSlash), locationType) : null;
            }
            case URL: {
                try {
                    URL url = new URL(path);
                    String file = url.getFile();
                    int lastSlash = file.lastIndexOf('/');
                    if (lastSlash > 0) {
                        return new Location(new URL(url.getProtocol(), url.getHost(), url.getPort(), file.substring(0, lastSlash)).toString(), locationType);
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    return null;
                }
            }
            default:
                throw new IllegalArgumentException();
        }
    }

    public String path() {
        return path;
    }

    public LocationType locationType() {
        return locationType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Location that = (Location) obj;
        return Objects.equals(this.path, that.path) &&
                Objects.equals(this.locationType, that.locationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, locationType);
    }

    @Override
    public String toString() {
        return "Location[" +
                "path=" + path + ", " +
                "locationType=" + locationType + ']';
    }

}