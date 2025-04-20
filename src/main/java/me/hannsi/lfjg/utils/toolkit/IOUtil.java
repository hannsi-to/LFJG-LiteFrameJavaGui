package me.hannsi.lfjg.utils.toolkit;

import me.hannsi.lfjg.utils.math.MathHelper;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import me.hannsi.lfjg.utils.reflection.location.URLLocation;
import org.bytedeco.opencv.opencv_core.Mat;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

import static org.lwjgl.system.MemoryUtil.*;

/**
 * Utility class for byte operations.
 */
public class IOUtil {
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

    /**
     * Converts a string to an InputStream using UTF-8 encoding.
     *
     * @param value the string to convert
     * @return an InputStream containing the string data
     */
    public static InputStream convertStringToInputStream(String value) {
        return new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Converts a file path or resource path to an InputStream.
     *
     * @param path the path to the file or resource
     * @return an InputStream for the file or resource
     * @throws IllegalArgumentException if the file or resource is not found
     */
    public static InputStream convertStringPathToInputStream(String path) {
        InputStream inputStream = IOUtil.class.getClassLoader().getResourceAsStream(path);

        if (inputStream == null) {
            try {
                File file = new File(path);
                if (file.exists()) {
                    inputStream = new FileInputStream(file);
                } else {
                    throw new IllegalArgumentException("File or resource not found: " + path);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return inputStream;
    }

    /**
     * Converts an InputStream to a byte array.
     *
     * @param inputStream the InputStream to convert
     * @return a byte array containing the InputStream data
     */
    public static byte[] convertInputStreamToByteArray(InputStream inputStream) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        try {
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return buffer.toByteArray();
    }

    public static ByteBuffer toByteBuffer(InputStream input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        try {
            for (int n = 0; n != -1; n = input.read(buffer)) {
                output.write(buffer, 0, n);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = output.toByteArray();
        ByteBuffer data = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder()).put(bytes);
        data.flip();
        return data;
    }

    /**
     * Reads an InputStream and converts it to a string using UTF-8 encoding.
     *
     * @param inputStream the InputStream to read
     * @return a string containing the InputStream data
     * @throws RuntimeException if an I/O error occurs
     */
    public static String readInputStreamToString(InputStream inputStream) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[512];

        int read;
        while (true) {
            try {
                if ((read = inputStream.read(buffer, 0, buffer.length)) == -1) {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.write(buffer, 0, read);
        }

        return out.toString(StandardCharsets.UTF_8);
    }

    public static ByteBuffer svgToByteBuffer(FileLocation fileLocation) {
        ByteBuffer buffer = memAlloc(128 * 1024);
        try (FileInputStream fis = new FileInputStream(fileLocation.getPath())) {
            boolean isGzip = fileLocation.getPath().endsWith(".gz");

            InputStream is = fis;
            if (isGzip) {
                is = new GZIPInputStream(is);
            }

            try (ReadableByteChannel rbc = Channels.newChannel(is)) {
                while (true) {
                    int bytesRead = rbc.read(buffer);
                    if (bytesRead == -1) {
                        break;
                    }

                    if (buffer.remaining() == 0) {
                        buffer = memRealloc(buffer, (buffer.capacity() * 3) >> 1);
                    }
                }
            }

        } catch (IOException e) {
            memFree(buffer);
            throw new RuntimeException(e);
        }

        buffer.put((byte) 0);
        buffer.flip();

        return buffer;
    }

    public static ByteBuffer downloadSVG(URLLocation spec) {
        ByteBuffer buffer = memAlloc(128 * 1024);
        try {
            URL url = spec.getURL();

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Accept-Encoding", "gzip");
            InputStream is = con.getInputStream();
            if ("gzip".equals(con.getContentEncoding())) {
                is = new GZIPInputStream(is);
            }

            try (ReadableByteChannel rbc = Channels.newChannel(is)) {
                int c;
                while ((c = rbc.read(buffer)) != -1) {
                    if (c == 0) {
                        buffer = memRealloc(buffer, (buffer.capacity() * 3) >> 1);
                    }
                }
            }
        } catch (IOException e) {
            memFree(buffer);
            throw new RuntimeException(e);
        }
        buffer.put((byte) 0);
        buffer.flip();

        return buffer;
    }

    public static void premultiplyAlpha(ByteBuffer image, int w, int h, int stride) {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int i = y * stride + x * 4;

                float alpha = (image.get(i + 3) & 0xFF) / 255.0f;
                image.put(i, (byte) MathHelper.round(((image.get(i) & 0xFF) * alpha)));
                image.put(i + 1, (byte) MathHelper.round(((image.get(i + 1) & 0xFF) * alpha)));
                image.put(i + 2, (byte) MathHelper.round(((image.get(i + 2) & 0xFF) * alpha)));
            }
        }
    }

    public static ByteBuffer loadResourceToByteBuffer(String path) {
        try (InputStream is = Files.newInputStream(Paths.get(path))) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            byte[] data = new byte[16384];
            int nRead;
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            ByteBuffer byteBuffer = memAlloc(buffer.size());
            byteBuffer.put(buffer.toByteArray());
            byteBuffer.flip();
            return byteBuffer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}