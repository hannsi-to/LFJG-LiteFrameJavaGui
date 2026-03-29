package me.hannsi.lfjg.core.manager;

import me.hannsi.lfjg.core.event.EventHandler;
import me.hannsi.lfjg.core.event.events.CleanupEvent;

public class CleanupManager {
    @EventHandler
    public void cleanupEvent(CleanupEvent event) {
        event.debug(CleanupManager.class);
    }
}
