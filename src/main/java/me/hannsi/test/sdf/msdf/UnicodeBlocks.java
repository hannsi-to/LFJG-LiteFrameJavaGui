package me.hannsi.test.sdf.msdf;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UnicodeBlocks {
    public static final String BLOCKS_URL = "https://www.unicode.org/Public/UNIDATA/Blocks.txt";
    public static final List<UnicodeBlock> BLOCKS = new ArrayList<>();
    public static boolean DEBUG_UNICODE = true;

    static {
        try {
            URL url = new URL(BLOCKS_URL);

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
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

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        if (DEBUG_UNICODE) {
            StringBuilder unicodeDebug = new StringBuilder();
            for (UnicodeBlock block : BLOCKS) {
                String name = String.format("%-35s", block.name + ":");
                String range = String.format("U+%04X - U+%04X", block.startCodePoint, block.endCodePoint);
                unicodeDebug.append("\t").append(name).append(" ").append(range).append("\n");
            }
            if (unicodeDebug.length() >= 2) {
                unicodeDebug.delete(unicodeDebug.length() - 2, unicodeDebug.length());
            } else {
                unicodeDebug.append("NULL");
            }

            new LogGenerator(
                    "Unicode",
                    unicodeDebug.toString()
            ).logging(DebugLevel.DEBUG);
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