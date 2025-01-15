package me.hannsi.lfjg.utils.buffer;

import me.hannsi.lfjg.debug.debug.DebugLog;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ByteUtil {
    public static InputStream convertStringToInputStream(String value) {
        return new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
    }

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
