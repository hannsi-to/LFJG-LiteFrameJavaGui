package me.hannsi.lfjg.frame.event.events.user;

import lombok.Getter;
import me.hannsi.lfjg.frame.event.system.Event;

@Getter
public class CharEvent extends Event {
    private final long window;
    private final int codepoint;

    public CharEvent(long window, int codepoint) {
        this.window = window;
        this.codepoint = codepoint;
    }

}
