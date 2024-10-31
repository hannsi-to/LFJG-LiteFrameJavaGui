package me.hannsi.lfjg.utils.math.vertex.vector;

public class Vector2i {
    public int x;
    public int y;

    /**
     * Creates a default 2-tuple vector with all values set to 0.
     */
    public Vector2i() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Creates a 2-tuple vector with specified values.
     *
     * @param x x value
     * @param y y value
     */
    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}