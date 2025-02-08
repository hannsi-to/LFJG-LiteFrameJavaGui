package me.hannsi.lfjg.utils.buffer;

import me.hannsi.lfjg.debug.debug.DebugLog;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for byte operations.
 */
public class ByteUtil {

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
        InputStream inputStream = ByteUtil.class.getClassLoader().getResourceAsStream(path);

        if (inputStream == null) {
            try {
                File file = new File(path);
                if (file.exists()) {
                    inputStream = new FileInputStream(file);
                } else {
                    throw new IllegalArgumentException("File or resource not found: " + path);
                }
            } catch (Exception e) {
                DebugLog.debug(ByteUtil.class, e);
            }
        }

        return inputStream;
    }

    /**
     * Converts an InputStream to a byte array.
     *
     * @param inputStream the InputStream to convert
     * @return a byte array containing the InputStream data
     * @throws IOException if an I/O error occurs
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
            e.printStackTrace();
        }

        return buffer.toByteArray();
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
}