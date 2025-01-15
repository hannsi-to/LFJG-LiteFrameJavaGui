package me.hannsi.lfjg.utils.reflection;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

public class ResourcesLocation extends FileLocation {
    public ResourcesLocation(String path) {
        super(getAbsolutePath(path).replace("\\", "/"));
    }

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
