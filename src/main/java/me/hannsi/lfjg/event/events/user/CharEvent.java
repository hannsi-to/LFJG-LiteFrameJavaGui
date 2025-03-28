package me.hannsi.lfjg.event.events.user;

import me.hannsi.lfjg.event.system.Event;

public class CharEvent extends Event {
    private final long window;
    private final int codepoint;

    public CharEvent(long window, int codepoint) {
        this.window = window;
        this.codepoint = codepoint;
    }

    public long getWindow() {
        return window;
    }

    public int getCodepoint() {
        return codepoint;
    }
}
