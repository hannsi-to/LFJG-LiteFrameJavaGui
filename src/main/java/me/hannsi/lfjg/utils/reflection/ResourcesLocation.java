package me.hannsi.lfjg.utils.reflection;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

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
        try {
            URI resourceUri = Objects.requireNonNull(ResourcesLocation.class.getClassLoader().getResource(path)).toURI();
            return Paths.get(resourceUri).toAbsolutePath().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI syntax for resource: " + path, e);
        } catch (NullPointerException e) {
            throw new RuntimeException("Resource not found: " + path, e);
        }
    }
}