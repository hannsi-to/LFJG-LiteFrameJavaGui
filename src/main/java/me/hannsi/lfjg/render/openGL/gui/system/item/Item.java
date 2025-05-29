package me.hannsi.lfjg.render.openGL.gui.system.item;

import me.hannsi.lfjg.frame.event.events.user.CharEvent;
import me.hannsi.lfjg.frame.event.events.user.KeyEvent;
import me.hannsi.lfjg.frame.event.events.user.MouseButtonEvent;
import me.hannsi.lfjg.render.openGL.system.Id;
import me.hannsi.lfjg.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.utils.toolkit.MouseInfo;


public class Item implements IItem {
    private final long id;


    public Item() {
        id = Id.latestItemId++;
    }

    @Override
    public void init() {

    }

    @Override
    public void render(MouseInfo mouseInfo, KeyboardInfo keyboardInfo) {

    }

    @Override
    public void cleanup() {

    }

    public void mouseButtonEvent(MouseButtonEvent event) {

    }

    public void keyEvent(KeyEvent event) {

    }

    public void charEvent(CharEvent event) {

    }

    public long getId() {
        return id;
    }
}
