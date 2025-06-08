package me.hannsi.lfjg.debug;

/**
 * Enum representing different types of debug messages.
 */
public enum DebugType {
    EXCEPTION("Exception"), // Represents an exception debug message
    TEXT("Text"); // Represents a text debug message

    final String display;

    /**
     * Constructor for DebugType enum.
     *
     * @param display the display name of the debug type
     */
    DebugType(String display) {
        this.display = display;
    }

    /**
     * Gets the display name of the debug type.
     *
     * @return the display name of the debug type
     */
    public String getDisplay() {
        return display;
    }
}