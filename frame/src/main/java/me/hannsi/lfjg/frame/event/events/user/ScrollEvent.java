package me.hannsi.lfjg.frame.event.events.user;

import lombok.Getter;
import me.hannsi.lfjg.core.event.Event;

@Getter
public class ScrollEvent extends Event {
    private final long window;
    private final double xoffset;
    private final double yoffset;

    public ScrollEvent(long window, double xoffset, double yoffset) {
        this.window = window;
        this.xoffset = xoffset;
        this.yoffset = yoffset;
    }

}
