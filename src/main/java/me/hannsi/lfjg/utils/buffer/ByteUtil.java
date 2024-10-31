package me.hannsi.lfjg.utils.buffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ByteUtil {
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
