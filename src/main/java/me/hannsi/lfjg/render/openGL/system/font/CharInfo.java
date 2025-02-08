package me.hannsi.lfjg.render.openGL.system.font;

import org.joml.Vector2f;

/**
 * Represents character information for font rendering in OpenGL.
 */
public class CharInfo {
    public Vector2f[] textureCoordinates;
    private int sourceX;
    private int sourceY;
    private int width;
    private int height;

    /**
     * Constructs a new CharInfo with the specified parameters.
     *
     * @param sourceX the x-coordinate of the character in the texture
     * @param sourceY the y-coordinate of the character in the texture
     * @param width   the width of the character
     * @param height  the height of the character
     */
    public CharInfo(int sourceX, int sourceY, int width, int height) {
        this.textureCoordinates = new Vector2f[2];

        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.width = width;
        this.height = height;
    }

    /**
     * Calculates the texture coordinates for the character.
     *
     * @param fontWidth  the width of the font texture
     * @param fontHeight the height of the font texture
     */
    public void calculateTextureCoordinates(int fontWidth, int fontHeight) {
        float x0 = (float) sourceX / (float) fontWidth;
        float x1 = (float) (sourceX + width) / (float) fontWidth;
        float y0 = (float) ((sourceY + 5) - height) / (float) fontHeight;
        float y1 = (float) ((sourceY + 5)) / (float) fontHeight;

        textureCoordinates[0] = new Vector2f(x0, y1);
        textureCoordinates[1] = new Vector2f(x1, y0);
    }

    /**
     * Gets the x-coordinate of the character in the texture.
     *
     * @return the x-coordinate of the character
     */
    public int getSourceX() {
        return sourceX;
    }

    /**
     * Sets the x-coordinate of the character in the texture.
     *
     * @param sourceX the x-coordinate to set
     */
    public void setSourceX(int sourceX) {
        this.sourceX = sourceX;
    }

    /**
     * Gets the y-coordinate of the character in the texture.
     *
     * @return the y-coordinate of the character
     */
    public int getSourceY() {
        return sourceY;
    }

    /**
     * Sets the y-coordinate of the character in the texture.
     *
     * @param sourceY the y-coordinate to set
     */
    public void setSourceY(int sourceY) {
        this.sourceY = sourceY;
    }

    /**
     * Gets the width of the character.
     *
     * @return the width of the character
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the character.
     *
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height of the character.
     *
     * @return the height of the character
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the character.
     *
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the texture coordinates of the character.
     *
     * @return the texture coordinates of the character
     */
    public Vector2f[] getTextureCoordinates() {
        return textureCoordinates;
    }

    /**
     * Sets the texture coordinates of the character.
     *
     * @param textureCoordinates the texture coordinates to set
     */
    public void setTextureCoordinates(Vector2f[] textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
    }
}