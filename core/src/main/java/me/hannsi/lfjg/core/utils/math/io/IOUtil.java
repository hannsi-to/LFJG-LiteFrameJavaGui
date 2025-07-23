package me.hannsi.lfjg.core.utils.math.io;

import me.hannsi.lfjg.core.utils.Util;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.reflection.location.Location;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
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

public class IOUtil extends Util {
    public static ByteBuffer convertBufferedImageToByteBuffer(BufferedImage image, boolean flipVertically) {
        int width = image.getWidth();
        int height = image.getHeight();

        if (image.getType() != BufferedImage.TYPE_4BYTE_ABGR) {
            BufferedImage converted = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g = converted.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            image = converted;
        }

        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        int stride = width * 4;

        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4).order(ByteOrder.nativeOrder());

        if (flipVertically) {
            for (int y = height - 1; y >= 0; y--) {
                int rowStart = y * stride;
                for (int x = 0; x < width; x++) {
                    int i = rowStart + x * 4;

                    byte a = pixels[i];
                    byte b = pixels[i + 1];
                    byte g = pixels[i + 2];
                    byte r = pixels[i + 3];

                    buffer.put(r).put(g).put(b).put(a);
                }
            }
        } else {
            for (int y = 0; y < height; y++) {
                int rowStart = y * stride;
                for (int x = 0; x < width; x++) {
                    int i = rowStart + x * 4;

                    byte a = pixels[i];
                    byte b = pixels[i + 1];
                    byte g = pixels[i + 2];
                    byte r = pixels[i + 3];

                    buffer.put(r).put(g).put(b).put(a);
                }
            }
        }

        buffer.flip();
        return buffer;
    }

    public static ByteBuffer convertRGBAtoBGRA(ByteBuffer rgbaBuffer, int width, int height) {
        int numPixels = width * height;
        int requiredBytes = numPixels * 4;

        if (rgbaBuffer.remaining() < requiredBytes) {
            throw new IllegalArgumentException("RGBA buffer does not contain enough data!");
        }

        ByteBuffer rgbaDup = rgbaBuffer.duplicate();
        rgbaDup.order(ByteOrder.nativeOrder());

        ByteBuffer bgraBuffer = ByteBuffer.allocateDirect(requiredBytes).order(ByteOrder.nativeOrder());

        for (int i = 0; i < numPixels; i++) {
            byte r = rgbaDup.get();
            byte g = rgbaDup.get();
            byte b = rgbaDup.get();
            byte a = rgbaDup.get();

            bgraBuffer.put(b).put(g).put(r).put(a);
        }

        bgraBuffer.flip();
        return bgraBuffer;
    }

    public static ByteBuffer convertBGRAtoRGBA(ByteBuffer bgraBuffer, int width, int height) {
        int numPixels = width * height;
        int requiredBytes = numPixels * 4;

        if (bgraBuffer.remaining() < requiredBytes) {
            throw new IllegalArgumentException("BGRA buffer does not contain enough data!");
        }

        ByteBuffer bgraDup = bgraBuffer.duplicate();
        bgraDup.order(ByteOrder.nativeOrder());

        ByteBuffer rgbaBuffer = ByteBuffer.allocateDirect(requiredBytes).order(ByteOrder.nativeOrder());

        for (int i = 0; i < numPixels; i++) {
            byte b = bgraDup.get();
            byte g = bgraDup.get();
            byte r = bgraDup.get();
            byte a = bgraDup.get();

            rgbaBuffer.put(r).put(g).put(b).put(a);
        }

        rgbaBuffer.flip();
        return rgbaBuffer;
    }

//    public static ByteBuffer matToByteBufferRGBA(Mat mat) {
//        int width = mat.cols();
//        int height = mat.rows();
//        int channels = mat.channels();
//        int bufferSize = width * height * channels;
//
//        ByteBuffer buffer = mat.createBuffer();
//
//        if (channels == 3) {
//            for (int i = 0; i < bufferSize; i += 3) {
//                byte b = buffer.get(i);
//                byte g = buffer.get(i + 1);
//                byte r = buffer.get(i + 2);
//
//                buffer.put(i, b);
//                buffer.put(i + 1, g);
//                buffer.put(i + 2, r);
//                buffer.put((byte) 255);
//            }
//        }
//
//        return buffer;
//    }

    public static InputStream convertStringToInputStream(String value) {
        return new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
    }

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

    public static ByteBuffer svgToByteBuffer(Location fileLocation) {
        ByteBuffer buffer = memAlloc(128 * 1024);
        try (FileInputStream fis = new FileInputStream(fileLocation.path())) {
            boolean isGzip = fileLocation.path().endsWith(".gz");

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

    public static ByteBuffer downloadSVG(Location spec) {
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

    public static <T> boolean allElementsEqual(T[] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        T first = array[0];
        for (int i = 1; i < array.length; i++) {
            if (!first.equals(array[i])) {
                return false;
            }
        }
        return true;
    }
}