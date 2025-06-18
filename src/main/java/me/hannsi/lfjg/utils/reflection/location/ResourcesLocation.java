package me.hannsi.lfjg.utils.reflection.location;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Class representing a resource location and providing utility methods to interact with the resource.
 */
public class ResourcesLocation extends FileLocation {

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
        try (InputStream in = ResourcesLocation.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new RuntimeException("Resource not found: " + path);
            }

            String suffix = path.contains(".") ? path.substring(path.lastIndexOf('.')) : ".tmp";
            Path tempFile = Files.createTempFile("resource_", suffix);
            tempFile.toFile().deleteOnExit();

            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
            return tempFile.toAbsolutePath().toString();
        } catch (NullPointerException e) {
            throw new RuntimeException("Resource not found: " + path, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}