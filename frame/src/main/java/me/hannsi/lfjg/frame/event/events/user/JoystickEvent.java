package me.hannsi.lfjg.frame.event.events.user;

import lombok.Getter;
import me.hannsi.lfjg.core.event.Event;

@Getter
public class JoystickEvent extends Event {
    private final int jid;
    private final int event;

    public JoystickEvent(int jid, int event) {
        this.jid = jid;
        this.event = event;
    }

}
