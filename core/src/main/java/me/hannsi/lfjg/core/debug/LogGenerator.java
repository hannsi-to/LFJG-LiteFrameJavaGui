package me.hannsi.lfjg.core.debug;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.toolkit.FastStringBuilder;
import me.hannsi.lfjg.core.utils.toolkit.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.hannsi.lfjg.core.Core.stringBuilder;
import static me.hannsi.lfjg.core.CoreSystemSetting.LOG_GENERATOR_BAR_COUNT;

public class LogGenerator {
    private final List<String> texts;
    private String bar = "-";
    private String title;

    public LogGenerator(LogGenerateType logGenerateType, Class<?> clazz, String id, String severity, String subMessage, String... texts) {
        String title = clazz.getSimpleName() + " Debug Message";
        String source = "Source: " + clazz.getName();
        String type = "Type: ";
        severity = "Severity: " + severity;
        String message = "Message: ";
        if (!id.isEmpty()) {
            id = "ID: " + id;
        }

        switch (logGenerateType) {
            case CREATE_CACHE:
                type += "Cache Creation";
                message += "Create " + clazz.getSimpleName() + (!subMessage.isEmpty() ? " | " : "") + subMessage;
                break;
            case CLEANUP:
                type += "Cleanup";
                message += clazz.getSimpleName() + " cleanup is complete" + (!subMessage.isEmpty() ? " | " : "") + subMessage;
                break;
            case LOAD:
                type += "Load";
                message += "Load " + clazz.getSimpleName() + (!subMessage.isEmpty() ? " | " : "") + subMessage;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + logGenerateType);
        }

        this.title = title;
        this.texts = new ArrayList<>();
        this.texts.add(source);
        this.texts.add(type);
        if (!id.isEmpty()) {
            this.texts.add(id);
        }
        this.texts.add(severity);
        this.texts.add(message);
        this.texts.addAll(Arrays.asList(texts));
    }

    public LogGenerator(LogGenerateType logGenerateType, Class<?> clazz, String id, String subMessage, String... texts) {
        this(logGenerateType, clazz, id, "Info", subMessage, texts);
    }

    public LogGenerator(LogGenerateType logGenerateType, Class<?> clazz, int id, String subMessage, String... texts) {
        this(logGenerateType, clazz, String.valueOf(id), "Info", subMessage, texts);
    }

    public LogGenerator(String title, String... texts) {
        this.title = title;
        this.texts = new ArrayList<>();
        this.texts.addAll(Arrays.asList(texts));
    }

    private static String humanBytes(long bytes) {
        final String[] units = {"B", "KB", "MB", "GB", "TB"};
        double val = bytes;
        int idx = 0;
        while (val >= 1024 && idx < units.length - 1) {
            val /= 1024.0;
            idx++;
        }
        return String.format("%.2f %s", val, units[idx]);
    }

    public LogGenerator kv(String key, Object value) {
        texts.add("  " + key + ": " + value);
        return this;
    }

    public LogGenerator kvBytes(String key, long bytes) {
        texts.add("  " + key + ": " + humanBytes(bytes) + " (" + bytes + " B)");
        return this;
    }

    public LogGenerator kvHex(String key, long addr) {
        texts.add("  " + key + ": 0x" + Long.toHexString(addr));
        return this;
    }

    public LogGenerator text(String text) {
        texts.add(text);
        return this;
    }

    public LogGenerator bar(String bar) {
        this.bar = bar;

        return this;
    }

    public String createLog() {
        return createLog("\n");
    }

    public String createLog(String first) {
        stringBuilder = new FastStringBuilder();
        stringBuilder.append(first);
        stringBuilder.counterPush()
                .append(StringUtil.repeat(bar, MathHelper.max(0, LOG_GENERATOR_BAR_COUNT)))
                .append(" ").append(title).append(" ")
                .append(StringUtil.repeat(bar, MathHelper.max(0, LOG_GENERATOR_BAR_COUNT)));
        int barLength = stringBuilder.counterPop();
        for (String text : texts) {
            stringBuilder.append("\n\t").append(text);
        }

        stringBuilder.append("\n");
        stringBuilder.append(StringUtil.repeat(bar, MathHelper.max(0, barLength)));
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    public void logging(Class<?> clazz, DebugLevel debugLevel, boolean oneLine, boolean firstln) {
        String message = createLog(firstln ? "\n" : "");

        if (oneLine) {
            for (String s : StringUtil.splitln(message)) {
                switch (debugLevel) {
                    case DEBUG:
                        DebugLog.debug(clazz, s);
                        break;
                    case INFO:
                        DebugLog.info(clazz, s);
                        break;
                    case WARNING:
                        DebugLog.warning(clazz, s);
                        break;
                    case ERROR:
                        DebugLog.error(clazz, s);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + debugLevel);
                }
            }
        } else {
            switch (debugLevel) {
                case DEBUG:
                    DebugLog.debug(clazz, message);
                    break;
                case INFO:
                    DebugLog.info(clazz, message);
                    break;
                case WARNING:
                    DebugLog.warning(clazz, message);
                    break;
                case ERROR:
                    DebugLog.error(clazz, message);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + debugLevel);
            }
        }
    }

    public void logging(Class<?> clazz, DebugLevel debugLevel) {
        logging(clazz, debugLevel, false, true);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBar() {
        return bar;
    }

    public List<String> getTexts() {
        return texts;
    }
}
