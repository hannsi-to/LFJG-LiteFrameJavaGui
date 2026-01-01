package me.hannsi.lfjg.core.utils.toolkit;

import me.hannsi.lfjg.core.CoreSystemSetting;
import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UnicodeBlocks {
    public static final List<UnicodeBlock> BLOCKS = new ArrayList<>();

    static {
        boolean success = false;
        try {
            URL url = new URL(CoreSystemSetting.UNICODE_BLOCKS_URL);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                loadBlocks(reader);
                success = true;
            }
        } catch (IOException e) {
            DebugLog.warning(UnicodeBlocks.class, "Failed to retrieve Unicode Blocks (" + e.getMessage() + "). Loading from local file...");
        }

        if (!success) {
            try (BufferedReader reader = new BufferedReader(new FileReader("Blocks.txt"))) {
                loadBlocks(reader);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load Unicode Blocks locally.", e);
            }
        }

        if (CoreSystemSetting.UNICODE_BLOCKS_DEBUG_UNICODE) {
            StringBuilder unicodeDebug = new StringBuilder();
            for (UnicodeBlock block : BLOCKS) {
                String name = String.format("%-35s", block.name() + ":");
                String range = String.format("U+%04X - U+%04X", block.startCodePoint(), block.endCodePoint());
                unicodeDebug.append("\t").append(name).append(" ").append(range).append("\n");
            }
            if (unicodeDebug.length() >= 2) {
                unicodeDebug.delete(unicodeDebug.length() - 2, unicodeDebug.length());
            } else {
                unicodeDebug.append("NULL");
            }

            new LogGenerator("Unicode", unicodeDebug.toString()).logging(UnicodeBlocks.class, DebugLevel.DEBUG);
        }
    }

    private static void loadBlocks(BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.trim().isEmpty() || line.startsWith("#")) {
                continue;
            }

            String[] parts = line.split(";");
            if (parts.length != 2) {
                continue;
            }

            String codeRange = parts[0].trim();
            String name = parts[1].trim();
            String[] rangeParts = codeRange.split("\\.\\.");
            if (rangeParts.length != 2) {
                continue;
            }

            try {
                int startCodePoint = Integer.parseInt(rangeParts[0], 16);
                int endCodePoint = Integer.parseInt(rangeParts[1], 16);

                BLOCKS.add(new UnicodeBlock(name, startCodePoint, endCodePoint));
            } catch (NumberFormatException e) {
                DebugLog.error(UnicodeBlock.class, "Invalid number format: " + line);
                DebugLog.error(UnicodeBlock.class, e);
            }
        }
    }

    public static ArrayList<UnicodeBlock> getBlocks(String text) {
        List<UnicodeBlock> unicodeBlocks = new ArrayList<>();
        char[] chars = text.toCharArray();
        for (char aChar : chars) {
            unicodeBlocks.add(getBlock(aChar));
        }

        return new ArrayList<>(new HashSet<>(unicodeBlocks));
    }

    public static UnicodeBlock getBlock(int codePoint) {
        for (UnicodeBlock block : BLOCKS) {
            if (block.contains(codePoint)) {
                return block;
            }
        }
        return null;
    }
}