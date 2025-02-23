package me.hannsi.lfjg.debug.debug;

import me.hannsi.lfjg.utils.math.MathHelper;

/**
 * The LogGenerator class is responsible for generating formatted log messages with a title and multiple text lines.
 */
public class LogGenerator {
    private int barCount = 30;
    private String title;
    private String[] texts;

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

    /**
     * Retrieves the bar count used for formatting the log message.
     *
     * @return The bar count.
     */
    public int getBarCount() {
        return barCount;
    }

    /**
     * Sets the bar count used for formatting the log message.
     *
     * @param barCount The bar count to set.
     */
    public void setBarCount(int barCount) {
        this.barCount = barCount;
    }

    /**
     * Retrieves the title of the log message.
     *
     * @return The title of the log message.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the log message.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the text lines of the log message.
     *
     * @return The text lines of the log message.
     */
    public String[] getTexts() {
        return texts;
    }

    /**
     * Sets the text lines of the log message.
     *
     * @param texts The text lines to set.
     */
    public void setTexts(String[] texts) {
        this.texts = texts;
    }
}
