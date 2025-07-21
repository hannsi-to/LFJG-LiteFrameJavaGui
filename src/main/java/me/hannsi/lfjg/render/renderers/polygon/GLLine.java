package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.type.types.DrawType;
import org.joml.Vector2f;

/**
 * Class representing a line renderer in OpenGL.
 */
public class GLLine extends GLPolygon {

    /**
     * Constructs a new GLLine with the specified name.
     *
     * @param name the name of the line renderer
     */
    public GLLine(String name) {
        super(name);
    }

    /**
     * Draws a line with the specified parameters.
     *
     * @param x1 the x-coordinate of the start point
     * @param y1 the y-coordinate of the start point
     * @param x2 the x-coordinate of the end point
     * @param y2 the y-coordinate of the end point
     * @param lineWidth the width of the line
     * @param color the color of the line
     */
    public void line(double x1, double y1, double x2, double y2, double lineWidth, Color color) {
        line((float) x1, (float) y1, (float) x2, (float) y2, (float) lineWidth, color);
    }

    /**
     * Draws a line with the specified parameters.
     *
     * @param x1 the x-coordinate of the start point
     * @param y1 the y-coordinate of the start point
     * @param x2 the x-coordinate of the end point
     * @param y2 the y-coordinate of the end point
     * @param lineWidth the width of the line
     * @param color the color of the line
     */
    public void line(float x1, float y1, float x2, float y2, float lineWidth, Color color) {
        put().vertex(new Vector2f(x1, y1)).color(color).end();
        put().vertex(new Vector2f(x2, y2)).color(color).end();

        setDrawType(DrawType.LINES).setLineWidth(lineWidth);
        rendering();
    }

    /**
     * Draws a line with the specified width and height.
     *
     * @param x1 the x-coordinate of the start point
     * @param y1 the y-coordinate of the start point
     * @param width the width of the line
     * @param height the height of the line
     * @param lineWidth the width of the line
     * @param color the color of the line
     */
    public void lineWH(float x1, float y1, float width, float height, float lineWidth, Color color) {
        line(x1, y1, x1 + width, y1 + height, lineWidth, color);
    }

    /**
     * Draws a line with the specified width and height.
     *
     * @param x1 the x-coordinate of the start point
     * @param y1 the y-coordinate of the start point
     * @param width the width of the line
     * @param height the height of the line
     * @param lineWidth the width of the line
     * @param color the color of the line
     */
    public void lineWH(double x1, double y1, double width, double height, double lineWidth, Color color) {
        line((float) x1, (float) y1, (float) x1 + (float) width, (float) y1 + (float) height, (float) lineWidth, color);
    }

    /**
     * Draws a gradient line with the specified parameters.
     *
     * @param x1 the x-coordinate of the start point
     * @param y1 the y-coordinate of the start point
     * @param x2 the x-coordinate of the end point
     * @param y2 the y-coordinate of the end point
     * @param lineWidth the width of the line
     * @param color1 the color of the start point
     * @param color2 the color of the end point
     */
    public void line(double x1, double y1, double x2, double y2, double lineWidth, Color color1, Color color2) {
        line((float) x1, (float) y1, (float) x2, (float) y2, (float) lineWidth, color1, color2);
    }

    /**
     * Draws a gradient line with the specified parameters.
     *
     * @param x1 the x-coordinate of the start point
     * @param y1 the y-coordinate of the start point
     * @param x2 the x-coordinate of the end point
     * @param y2 the y-coordinate of the end point
     * @param lineWidth the width of the line
     * @param color1 the color of the start point
     * @param color2 the color of the end point
     */
    public void line(float x1, float y1, float x2, float y2, float lineWidth, Color color1, Color color2) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y2)).color(color2).end();

        setDrawType(DrawType.LINES).setLineWidth(lineWidth);
        rendering();
    }

    /**
     * Draws a gradient line with the specified width and height.
     *
     * @param x1 the x-coordinate of the start point
     * @param y1 the y-coordinate of the start point
     * @param width the width of the line
     * @param height the height of the line
     * @param lineWidth the width of the line
     * @param color1 the color of the start point
     * @param color2 the color of the end point
     */
    public void lineWH(double x1, double y1, double width, double height, double lineWidth, Color color1, Color color2) {
        line((float) x1, (float) y1, (float) x1 + (float) width, (float) y1 + (float) height, (float) lineWidth, color1, color2);
    }

    /**
     * Draws a gradient line with the specified width and height.
     *
     * @param x1 the x-coordinate of the start point
     * @param y1 the y-coordinate of the start point
     * @param width the width of the line
     * @param height the height of the line
     * @param lineWidth the width of the line
     * @param color1 the color of the start point
     * @param color2 the color of the end point
     */
    public void lineWH(float x1, float y1, float width, float height, float lineWidth, Color color1, Color color2) {
        line(x1, y1, x1 + width, y1 + height, lineWidth, color1, color2);
    }
}