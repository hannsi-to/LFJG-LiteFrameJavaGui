package me.hannsi.lfjg.render.openGL.gui.system.item;

public class ItemPoint extends Item {
    private final float x;
    private final float y;
    private float scale;

    public ItemPoint(float x, float y, float scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public float getX() {
        return x;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getY() {
        return y;
    }
}
