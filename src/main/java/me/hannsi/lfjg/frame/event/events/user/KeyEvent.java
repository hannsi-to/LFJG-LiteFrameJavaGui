package me.hannsi.lfjg.frame.event.events.user;

import lombok.Getter;
import me.hannsi.lfjg.frame.event.system.Event;

@Getter
public class KeyEvent extends Event {
    private final long window;
    private final int key;
    private final int scancode;
    private final int action;
    private final int mods;

    public KeyEvent(long window, int key, int scancode, int action, int mods) {
        this.window = window;
        this.key = key;
        this.scancode = scancode;
        this.action = action;
        this.mods = mods;
    }

}
