package me.hannsi.lfjg.debug.debug;

public class LogGenerator {
    private int barCount = 30;
    private String title;
    private String[] texts;

    public LogGenerator(String title, String... texts) {
        this.title = title;
        this.texts = texts;
    }

    public String createLog() {
        StringBuilder log = new StringBuilder("\n");
        StringBuilder firstLine = new StringBuilder();

        firstLine.append("-".repeat(Math.max(0, barCount)));
        firstLine.append(" ").append(title).append(" ");
        firstLine.append("-".repeat(Math.max(0, barCount)));

        log.append(firstLine);

        for (String text : texts) {
            log.append("\n\t").append(text);
        }

        log.append("\n");
        log.append("-".repeat(Math.max(0, firstLine.length())));
        log.append("\n");

        return log.toString();
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
