package me.hannsi.lfjg.debug;

import lombok.Getter;

/**
 * Enum representing different types of debug messages.
 */
@Getter
public enum DebugType {
    EXCEPTION("Exception"), // Represents an exception debug message
    TEXT("Text"); // Represents a text debug message

    /**
     * -- GETTER --
     * Gets the display name of the debug type.
     *
     * @return the display name of the debug type
     */
    final String display;

    /**
     * Constructor for DebugType enum.
     *
     * @param display the display name of the debug type
     */
    DebugType(String display) {
        this.display = display;
    }

}