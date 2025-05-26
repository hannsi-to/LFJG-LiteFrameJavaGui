package me.hannsi.lfjg.render.openGL.gui.item;

import me.hannsi.lfjg.render.openGL.system.Id;
import org.joml.Vector2f;


public class Item {
    private final long id;

    private float x;
    private float y;
    private float width;
    private float height;

    public Item() {
        this.id = Id.latestItemId++;
    }

    public void render(Vector2f mouse) {

    }
}
