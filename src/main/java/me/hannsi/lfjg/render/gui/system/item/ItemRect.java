package me.hannsi.lfjg.render.gui.system.item;

public class ItemRect extends Item {
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private float scale;

    public ItemRect(float x, float y, float width, float height, float scale) {
        super();

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
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

    public float getWidthWithScale() {
        return width * scale;
    }

    public float getHeightWithScale() {
        return height * scale;
    }
}
