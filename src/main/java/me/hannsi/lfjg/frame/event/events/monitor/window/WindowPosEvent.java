package me.hannsi.lfjg.frame.event.events.monitor.window;

import lombok.Getter;
import me.hannsi.lfjg.frame.event.system.Event;

@Getter
public class WindowPosEvent extends Event {
    private final long window;
    private final int xpos;
    private final int ypos;

    public WindowPosEvent(long window, int xpos, int ypos) {
        this.window = window;
        this.xpos = xpos;
        this.ypos = ypos;
    }

}
