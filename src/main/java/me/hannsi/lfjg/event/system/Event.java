package me.hannsi.lfjg.event.system;

/**
 * The Event class represents a generic event that can be canceled.
 */
public class Event {
    private boolean canceled = false;

    /**
     * Checks if the event is canceled.
     *
     * @return True if the event is canceled, false otherwise.
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * Sets the canceled state of the event.
     *
     * @param canceled True to cancel the event, false to uncancel it.
     */
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
