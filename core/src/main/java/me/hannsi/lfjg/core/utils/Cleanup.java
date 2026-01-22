package me.hannsi.lfjg.core.utils;

import me.hannsi.lfjg.core.event.events.CleanupEvent;

public interface Cleanup {
    boolean cleanup(CleanupEvent event);
}
