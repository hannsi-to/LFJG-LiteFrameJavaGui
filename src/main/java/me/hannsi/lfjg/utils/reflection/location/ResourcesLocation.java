package me.hannsi.lfjg.utils.reflection.location;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a resource location and providing utility methods to interact with the resource.
 */
public class ResourcesLocation extends FileLocation {
    private static final Map<String, String> resourceCache = new HashMap<>();

    /**
     * Constructs a ResourcesLocation instance with the specified path.
     *
     * @param path the path to the resource
     */
    public ResourcesLocation(String path) {
        super(getAbsolutePath(path).replace("\\", "/"));
    }

    /**
     * Gets the absolute path of the specified resource.
     *
     * @param path the relative path to the resource
     * @return the absolute path of the resource
     * @throws RuntimeException if the URI syntax is invalid or the resource is not found
     */
    public static String getAbsolutePath(String path) {
        if (resourceCache.containsKey(path)) {
            return resourceCache.get(path);
        }

        try (InputStream in = ResourcesLocation.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new RuntimeException("Resource not found: " + path);
            }

            String suffix = path.contains(".") ? path.substring(path.lastIndexOf('.')) : ".tmp";
            String prefix = "resource_" + path.hashCode();

            Path tempFile = Files.createTempFile(prefix, suffix);
            tempFile.toFile().deleteOnExit();

            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);

            String absPath = tempFile.toAbsolutePath().toString();
            resourceCache.put(path, absPath);

            return absPath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource: " + path, e);
        }
    }
}