package me.hannsi.lfjg.event.events.render;

import me.hannsi.lfjg.event.system.Event;

/**
 * The DrawFrameWithNanoVGEvent class represents an event that occurs when a frame is drawn using NanoVG.
 */
public class DrawFrameWithNanoVGEvent extends Event {
    private final long nvg;

    /**
     * Constructs a DrawFrameWithNanoVGEvent with the specified NanoVG context.
     *
     * @param nvg The NanoVG context.
     */
    public DrawFrameWithNanoVGEvent(long nvg) {
        this.nvg = nvg;
    }

    /**
     * Retrieves the NanoVG context associated with this event.
     *
     * @return The NanoVG context.
     */
    public long getNvg() {
        return nvg;
    }
}
