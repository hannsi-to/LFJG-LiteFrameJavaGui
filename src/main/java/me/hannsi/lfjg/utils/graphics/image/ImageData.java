package me.hannsi.lfjg.utils.graphics.image;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.utils.reflection.location.Location;
import org.bytedeco.opencv.opencv_core.Mat;

import java.nio.ByteBuffer;

/**
 * Class representing image data, including the image path, OpenCV Mat object, and ByteBuffer.
 */
@Getter
@Setter
public class ImageData {
    /**
     * -- SETTER --
     * Sets the image path.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the image path.
     *
     * @param imagePath the new image path
     * @return the image path
     */
    private Location imagePath;
    /**
     * -- SETTER --
     * Sets the OpenCV Mat object.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the OpenCV Mat object.
     *
     * @param mat the new Mat object
     * @return the Mat object
     */
    private Mat mat;
    /**
     * -- SETTER --
     * Sets the ByteBuffer containing the image data in RGBA format.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the ByteBuffer containing the image data in RGBA format.
     *
     * @param byteBuffer the new ByteBuffer
     * @return the ByteBuffer
     */
    private ByteBuffer byteBuffer;

    public void cleanup() {
        mat = null;
        byteBuffer.clear();
    }

}