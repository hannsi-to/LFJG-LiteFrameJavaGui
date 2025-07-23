package me.hannsi.lfjg.core.utils.graphics.image;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import org.bytedeco.opencv.opencv_core.Mat;

import java.nio.ByteBuffer;

public class ImageData {
    private Location imagePath;
    private Mat mat;
    private ByteBuffer byteBuffer;

    public void cleanup() {
        mat = null;
        byteBuffer.clear();
    }

    public Location getImagePath() {
        return imagePath;
    }

    public void setImagePath(Location imagePath) {
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