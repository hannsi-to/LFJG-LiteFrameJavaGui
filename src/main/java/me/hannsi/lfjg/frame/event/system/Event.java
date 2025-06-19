package me.hannsi.lfjg.frame.event.system;

import lombok.Getter;
import lombok.Setter;

/**
 * The Event class represents a generic event that can be canceled.
 */
@Setter
@Getter
public class Event {
    /**
     * -- GETTER --
     *  Checks if the event is canceled.
     *
     *
     * -- SETTER --
     *  Sets the canceled state of the event.
     *
     @return True if the event is canceled, false otherwise.
      * @param canceled True to cancel the event, false to uncancel it.
     */
    private boolean canceled = false;

}
