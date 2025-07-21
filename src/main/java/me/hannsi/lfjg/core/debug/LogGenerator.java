package me.hannsi.lfjg.core.debug;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.utils.math.MathHelper;

/**
 * The LogGenerator class is responsible for generating formatted log messages with a title and multiple text lines.
 */
@Setter
@Getter
public class LogGenerator {
    /**
     * -- GETTER --
     * Retrieves the bar count used for formatting the log message.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the bar count used for formatting the log message.
     *
     * @return The bar count.
     * @param barCount The bar count to set.
     */
    private int barCount = 30;
    /**
     * -- GETTER --
     * Retrieves the title of the log message.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the title of the log message.
     *
     * @return The title of the log message.
     * @param title The title to set.
     */
    private String title;
    /**
     * -- GETTER --
     * Retrieves the text lines of the log message.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the text lines of the log message.
     *
     * @return The text lines of the log message.
     * @param texts The text lines to set.
     */
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

    /**
     * Constructs a LogGenerator object with the specified title and text lines.
     *
     * @param title The title of the log message.
     * @param texts The text lines to include in the log message.
     */
    public LogGenerator(String title, String... texts) {
        this.title = title;
        this.texts = texts;
    }

    /**
     * Creates a formatted log message with the specified title and text lines.
     *
     * @return The formatted log message as a String.
     */
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

}
