package me.hannsi.lfjg.utils.buffer;

import org.bytedeco.opencv.opencv_core.Mat;

import java.nio.ByteBuffer;

/**
 * Utility class for buffer operations.
 */
public class BufferUtil {

    /**
     * Converts an OpenCV Mat object to a ByteBuffer in RGBA format.
     *
     * @param mat the OpenCV Mat object to convert
     * @return a ByteBuffer containing the RGBA data
     */
    public static ByteBuffer matToByteBufferRGBA(Mat mat) {
        int width = mat.cols();
        int height = mat.rows();
        int channels = mat.channels();
        int bufferSize = width * height * channels;

        ByteBuffer buffer = mat.createBuffer();

        if (channels == 3) {
            for (int i = 0; i < bufferSize; i += 3) {
                byte b = buffer.get(i);
                byte g = buffer.get(i + 1);
                byte r = buffer.get(i + 2);

                buffer.put(i, b);
                buffer.put(i + 1, g);
                buffer.put(i + 2, r);
                buffer.put((byte) 255);
            }
        }

        return buffer;
    }
}