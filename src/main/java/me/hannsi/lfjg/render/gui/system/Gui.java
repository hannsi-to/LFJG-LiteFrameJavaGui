package me.hannsi.lfjg.render.gui.system;

import me.hannsi.lfjg.frame.event.events.user.CharEvent;
import me.hannsi.lfjg.frame.event.events.user.KeyEvent;
import me.hannsi.lfjg.frame.event.events.user.MouseButtonEvent;
import me.hannsi.lfjg.frame.event.system.EventHandler;
import me.hannsi.lfjg.frame.frame.IFrame;
import me.hannsi.lfjg.render.gui.system.item.Item;
import me.hannsi.lfjg.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.utils.toolkit.MouseInfo;

import java.util.ArrayList;
import java.util.List;

public class Gui {
    private List<Item> items;

    public Gui() {

    }

    public Gui builder() {
        items = new ArrayList<>();
        return this;
    }

    public Gui setEventHandler() {
        IFrame.eventManager.register(this);

        return this;
    }

    public Gui addItem(Item item) {
        items.add(item);
        return this;
    }

    public void init() {
        for (Item item : items) {
            item.init();
        }
    }

    public void renderItems(MouseInfo mouseInfo, KeyboardInfo keyboardInfo) {
        for (Item item : items) {
            item.render(mouseInfo, keyboardInfo);
        }
    }

    public void cleanup() {
        for (Item item : items) {
            item.cleanup();
        }
    }

    @EventHandler
    public void mouseButtonEvent(MouseButtonEvent event) {
        for (Item item : items) {
            item.mouseButtonEvent(event);
        }
    }

    @EventHandler
    public void keyEvent(KeyEvent event) {
        for (Item item : items) {
            item.keyEvent(event);
        }
    }

    @EventHandler
    public void charEvent(CharEvent event) {
        for (Item item : items) {
            item.charEvent(event);
        }
    }
}
