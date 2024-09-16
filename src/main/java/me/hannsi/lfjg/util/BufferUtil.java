package me.hannsi.lfjg.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class BufferUtil {
    public static ByteBuffer inputStreamToByteBuffer(InputStream inputStream) {
        try {
            return ByteBuffer.wrap(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
