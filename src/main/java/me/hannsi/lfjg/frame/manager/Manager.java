package me.hannsi.lfjg.frame.manager;

import me.hannsi.lfjg.frame.Frame;

public class Manager {
    private final Frame frame;
    private final String name;

    public Manager(Frame frame,String name) {
        this.frame = frame;
        this.name = name;
    }

    public Frame getFrame() {
        return frame;
    }

    public String getName() {
        return name;
    }
}
