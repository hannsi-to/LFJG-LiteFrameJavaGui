package me.hannsi.lfjg.render.openGL.gui.system;

import me.hannsi.lfjg.frame.event.events.user.CharEvent;
import me.hannsi.lfjg.frame.event.events.user.KeyEvent;
import me.hannsi.lfjg.frame.event.events.user.MouseButtonEvent;
import me.hannsi.lfjg.render.openGL.system.Id;
import me.hannsi.lfjg.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.utils.toolkit.MouseInfo;


public class Item {
    private final long id;

    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private float scale;

    public Item(float x, float y, float width, float height, float scale) {
        id = Id.latestItemId++;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    public void render(MouseInfo mouseInfo, KeyboardInfo keyboardInfo) {

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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
