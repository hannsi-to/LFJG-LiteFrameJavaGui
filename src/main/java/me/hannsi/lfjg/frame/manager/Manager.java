package me.hannsi.lfjg.frame.manager;

import me.hannsi.lfjg.frame.frame.Frame;

/**
 * The Manager class is responsible for managing various aspects of the frame.
 */
public class Manager {
    private final Frame frame;
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

    /**
     * Retrieves the frame associated with this manager.
     *
     * @return The frame associated with this manager.
     */
    public Frame getFrame() {
        return frame;
    }

    /**
     * Retrieves the name of this manager.
     *
     * @return The name of this manager.
     */
    public String getName() {
        return name;
    }
}
