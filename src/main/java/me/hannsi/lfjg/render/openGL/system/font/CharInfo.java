package me.hannsi.lfjg.render.openGL.system.font;

import org.joml.Vector2f;

public class CharInfo {
    public Vector2f[] textureCoordinates;
    private int sourceX;
    private int sourceY;
    private int width;
    private int height;

    public CharInfo(int sourceX, int sourceY, int width, int height) {
        this.textureCoordinates = new Vector2f[2];

        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.width = width;
        this.height = height;
    }

    public void calculateTextureCoordinates(int fontWidth, int fontHeight) {
        float x0 = (float) sourceX / (float) fontWidth;
        float x1 = (float) (sourceX + width) / (float) fontWidth;
        float y0 = (float) ((sourceY + 5) - height) / (float) fontHeight;
        float y1 = (float) ((sourceY + 5)) / (float) fontHeight;

        textureCoordinates[0] = new Vector2f(x0, y1);
        textureCoordinates[1] = new Vector2f(x1, y0);
    }

    public int getSourceX() {
        return sourceX;
    }

    public void setSourceX(int sourceX) {
        this.sourceX = sourceX;
    }

    public int getSourceY() {
        return sourceY;
    }

    public void setSourceY(int sourceY) {
        this.sourceY = sourceY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Vector2f[] getTextureCoordinates() {
        return textureCoordinates;
    }

    public void setTextureCoordinates(Vector2f[] textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
    }
}
