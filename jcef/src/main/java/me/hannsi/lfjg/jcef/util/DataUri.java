package me.hannsi.lfjg.jcef.util;

public class DataUri {
    public static String create(String mimeType, String contents) {
        return "data:" + mimeType + ";base64," + java.util.Base64.getEncoder().encodeToString(contents.getBytes());
    }
}
