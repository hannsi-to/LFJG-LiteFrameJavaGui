package me.hannsi.lfjg.frame.manager;

import lombok.Getter;
import me.hannsi.lfjg.frame.Frame;

/**
 * The Manager class is responsible for managing various aspects of the frame.
 */
@Getter
public class Manager {
    /**
     * -- GETTER --
     *  Retrieves the frame associated with this manager.
     *
     * @return The frame associated with this manager.
     */
    private final Frame frame;
    /**
     * -- GETTER --
     *  Retrieves the name of this manager.
     *
     * @return The name of this manager.
     */
    private final String name;

    /**
     * Constructs a Manager object with the specified frame and name.
     *
     * @param frame The frame associated with this manager.
     * @param name  The name of the manager.
     */
    public Manager(Frame frame, String name) {
        this.frame = frame;
        this.name = name;
    }

}
