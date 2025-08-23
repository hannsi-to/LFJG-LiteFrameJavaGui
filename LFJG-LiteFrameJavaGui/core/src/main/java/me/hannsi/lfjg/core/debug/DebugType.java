package me.hannsi.lfjg.core.debug;

public enum DebugType {
    ERROR("Error"),
    EXCEPTION("Exception"),
    TEXT("Text");

    final String display;

    DebugType(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}