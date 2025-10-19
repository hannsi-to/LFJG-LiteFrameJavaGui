package me.hannsi.lfjg.core.debug;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.toolkit.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogGenerator {
    private static final Logger log = LoggerFactory.getLogger(LogGenerator.class);
    public static int barCount = 30;
    private String bar = "-";
    private String title;
    private String[] texts;

    public LogGenerator(LogGenerateType logGenerateType, Class<?> clazz, String id, String severity, String subMessage) {
        String title = clazz.getSimpleName() + " Debug Message";
        String source = "Source: " + clazz.getName();
        String type = "Type: ";
        severity = "Severity: " + severity;
        String message = "Message: ";
        id = "ID: " + id;

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
        this.texts = new String[]{source, type, id, severity, message};
    }

    public LogGenerator(LogGenerateType logGenerateType, Class<?> clazz, String id, String subMessage) {
        this(logGenerateType, clazz, String.valueOf(id), "Info", subMessage);
    }

    public LogGenerator(LogGenerateType logGenerateType, Class<?> clazz, int id, String subMessage) {
        this(logGenerateType, clazz, String.valueOf(id), "Info", subMessage);
    }

    public LogGenerator(String title, String... texts) {
        this.title = title;
        this.texts = texts;
    }

    public LogGenerator bar(String bar) {
        this.bar = bar;

        return this;
    }

    public String createLog() {
        return createLog("\n");
    }

    public String createLog(String first) {
        StringBuilder log = new StringBuilder(first);
        StringBuilder firstLine = new StringBuilder();
        firstLine.append(StringUtil.repeat(bar, MathHelper.max(0, barCount)));
        firstLine.append(" ").append(title).append(" ");
        firstLine.append(StringUtil.repeat(bar, MathHelper.max(0, barCount)));

        log.append(firstLine);

        for (String text : texts) {
            log.append("\n\t").append(text);
        }

        log.append("\n");
        log.append(StringUtil.repeat(bar, MathHelper.max(0, firstLine.length())));
        log.append("\n");

        return log.toString();
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

    public int getBarCount() {
        return barCount;
    }

    public void setBarCount(int barCount) {
        LogGenerator.barCount = barCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getTexts() {
        return texts;
    }

    public void setTexts(String[] texts) {
        this.texts = texts;
    }
}
