package me.hannsi.lfjg.utils.graphics.image;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import me.hannsi.lfjg.utils.toolkit.IOUtil;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;

import java.nio.ByteBuffer;

import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;

/**
 * Class representing image data, including the image path, OpenCV Mat object, and ByteBuffer.
 */
@Getter
@Setter
public class ImageData {
    /**
     * -- SETTER --
     *  Sets the image path.
     *
     *
     * -- GETTER --
     *  Gets the image path.
     *
     @param imagePath the new image path
      * @return the image path
     */
    private ResourcesLocation imagePath;
    /**
     * -- SETTER --
     *  Sets the OpenCV Mat object.
     *
     *
     * -- GETTER --
     *  Gets the OpenCV Mat object.
     *
     @param mat the new Mat object
      * @return the Mat object
     */
    private Mat mat;
    /**
     * -- SETTER --
     *  Sets the ByteBuffer containing the image data in RGBA format.
     *
     *
     * -- GETTER --
     *  Gets the ByteBuffer containing the image data in RGBA format.
     *
     @param byteBuffer the new ByteBuffer
      * @return the ByteBuffer
     */
    private ByteBuffer byteBuffer;

    /**
     * Constructs an ImageData instance from the specified image path.
     *
     * @param imagePath the path to the image resource
     */
    public ImageData(ResourcesLocation imagePath) {
        this.imagePath = imagePath;

        Mat bgrMat = opencv_imgcodecs.imdecode(new Mat(imagePath.getBytes()), opencv_imgcodecs.IMREAD_COLOR);

        if (bgrMat.empty()) {
            DebugLog.error(getClass(), "Failed to load image");
            return;
        }

        mat = new Mat();

        cvtColor(bgrMat, mat, opencv_imgproc.COLOR_BGR2RGBA);
        this.byteBuffer = IOUtil.matToByteBufferRGBA(mat);
    }

    public void cleanup() {
        imagePath.cleanup();
        mat = null;
        byteBuffer.clear();
    }

}