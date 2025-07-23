package me.hannsi.lfjg.core.debug;

import me.hannsi.lfjg.core.utils.math.MathHelper;

public class LogGenerator {
    private int barCount = 30;
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
            case CREATE_CACHE -> {
                type += "Cache Creation";
                message += "Create " + clazz.getSimpleName() + (!subMessage.isEmpty() ? " | " : "") + subMessage;
            }
            case CLEANUP -> {
                type += "Cleanup";
                message += clazz.getSimpleName() + " cleanup is complete" + (!subMessage.isEmpty() ? " | " : "") + subMessage;
            }
            case LOAD -> {
                type += "Load";
                message += "Load " + clazz.getSimpleName() + (!subMessage.isEmpty() ? " | " : "") + subMessage;
            }
            default -> throw new IllegalStateException("Unexpected value: " + logGenerateType);
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

    public String createLog() {
        StringBuilder log = new StringBuilder("\n");
        StringBuilder firstLine = new StringBuilder();

        firstLine.append("-".repeat(MathHelper.max(0, barCount)));
        firstLine.append(" ").append(title).append(" ");
        firstLine.append("-".repeat(MathHelper.max(0, barCount)));

        log.append(firstLine);

        for (String text : texts) {
            log.append("\n\t").append(text);
        }

        log.append("\n");
        log.append("-".repeat(MathHelper.max(0, firstLine.length())));
        log.append("\n");

        return log.toString();
    }

    public void logging(DebugLevel debugLevel) {
        switch (debugLevel) {
            case DEBUG -> DebugLog.debug(LogGenerator.class, createLog());
            case INFO -> DebugLog.info(LogGenerator.class, createLog());
            case WARNING -> DebugLog.warning(LogGenerator.class, createLog());
            case ERROR -> DebugLog.error(LogGenerator.class, createLog());
            default -> throw new IllegalStateException("Unexpected value: " + debugLevel);
        }
    }

    public int getBarCount() {
        return barCount;
    }

    public void setBarCount(int barCount) {
        this.barCount = barCount;
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
