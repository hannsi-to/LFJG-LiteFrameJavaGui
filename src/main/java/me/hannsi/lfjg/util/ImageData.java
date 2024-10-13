package me.hannsi.lfjg.util;

import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.frame.Frame;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;

import java.nio.ByteBuffer;

import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;

public class ImageData {
    private Frame frame;
    private ResourcesLocation imagePath;
    private Mat mat;
    private ByteBuffer byteBuffer;

    public ImageData(Frame frame, ResourcesLocation imagePath) {
        this.frame = frame;
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

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public ResourcesLocation getImagePath() {
        return imagePath;
    }

    public void setImagePath(ResourcesLocation imagePath) {
        this.imagePath = imagePath;
    }

    public Mat getMat() {
        return mat;
    }

    public void setMat(Mat mat) {
        this.mat = mat;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
}
