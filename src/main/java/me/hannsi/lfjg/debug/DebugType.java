package me.hannsi.lfjg.debug;

public enum DebugType {
    EXCEPTION("Exception"), TEXT("Text");

    final String display;

    DebugType(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
