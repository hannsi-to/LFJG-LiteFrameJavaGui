package me.hannsi.lfjg.utils.reflection.location;

import me.hannsi.lfjg.utils.toolkit.IOUtil;

import java.io.InputStream;
import java.util.Objects;

/**
 * Class representing a file location and providing utility methods to interact with the file.
 */
public class FileLocation extends Location {
    public String path;

    /**
     * Constructs a FileLocation instance with the specified path.
     *
     * @param path the path to the file
     */
    public FileLocation(String path) {
        super(path, false, true);

        this.path = path;
    }

    /**
     * Gets an InputStream for the file at the specified path.
     *
     * @return the InputStream for the file
     */
    public InputStream getInputStream() {
        return IOUtil.convertStringPathToInputStream(path);
    }

    /**
     * Gets the bytes of the file at the specified path.
     *
     * @return the byte array of the file
     */
    public byte[] getBytes() {
        return IOUtil.convertInputStreamToByteArray(Objects.requireNonNull(getInputStream()));
    }
}