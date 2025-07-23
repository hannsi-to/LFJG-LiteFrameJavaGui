package me.hannsi.lfjg.core.debug;

import lombok.Getter;

/**
 * Enum representing different types of debug messages.
 */
@Getter
public enum DebugType {
    ERROR("Error"),
    EXCEPTION("Exception"),
    TEXT("Text");

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