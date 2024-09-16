package me.hannsi.lfjg.util;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class BufferUtil {
    public static ByteBuffer inputStreamToByteBuffer(InputStream inputStream) {
        try {
            return ByteBuffer.wrap(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
