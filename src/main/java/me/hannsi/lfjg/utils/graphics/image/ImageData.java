package me.hannsi.lfjg.utils.graphics.image;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.utils.buffer.BufferUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;

import java.nio.ByteBuffer;

import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;

/**
 * Class representing image data, including the image path, OpenCV Mat object, and ByteBuffer.
 */
public class ImageData {
    private ResourcesLocation imagePath;
    private Mat mat;
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
        this.byteBuffer = BufferUtil.matToByteBufferRGBA(mat);
    }

    public void cleanup() {
        imagePath.cleanup();
        mat = null;
        byteBuffer.clear();
    }

    /**
     * Gets the image path.
     *
     * @return the image path
     */
    public ResourcesLocation getImagePath() {
        return imagePath;
    }

    /**
     * Sets the image path.
     *
     * @param imagePath the new image path
     */
    public void setImagePath(ResourcesLocation imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Gets the OpenCV Mat object.
     *
     * @return the Mat object
     */
    public Mat getMat() {
        return mat;
    }

    /**
     * Sets the OpenCV Mat object.
     *
     * @param mat the new Mat object
     */
    public void setMat(Mat mat) {
        this.mat = mat;
    }

    /**
     * Gets the ByteBuffer containing the image data in RGBA format.
     *
     * @return the ByteBuffer
     */
    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    /**
     * Sets the ByteBuffer containing the image data in RGBA format.
     *
     * @param byteBuffer the new ByteBuffer
     */
    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
}