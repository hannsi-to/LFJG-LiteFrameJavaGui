package me.hannsi.lfjg.render.openGL.renderers.polygon;

import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Vector2f;

/**
 * Class representing a rectangle renderer in OpenGL.
 */
public class GLRect extends GLPolygon {

    /**
     * Constructs a new GLRect with the specified name.
     *
     * @param name the name of the rectangle renderer
     */
    public GLRect(String name) {
        super(name);
    }

    /**
     * Draws a rectangle with the specified coordinates and color.
     *
     * @param x1    the x-coordinate of the first vertex
     * @param y1    the y-coordinate of the first vertex
     * @param x2    the x-coordinate of the second vertex
     * @param y2    the y-coordinate of the second vertex
     * @param color the color of the rectangle
     */
    public void rect(float x1, float y1, float x2, float y2, Color color) {
        put().vertex(new Vector2f(x1, y1)).color(color).end();
        put().vertex(new Vector2f(x2, y1)).color(color).end();
        put().vertex(new Vector2f(x2, y2)).color(color).end();
        put().vertex(new Vector2f(x1, y2)).color(color).end();

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle with the specified width and height.
     *
     * @param x      the x-coordinate of the first vertex
     * @param y      the y-coordinate of the first vertex
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param color  the color of the rectangle
     */
    public void rectWH(float x, float y, float width, float height, Color color) {
        rect(x, y, x + width, y + height, color);
    }

    /**
     * Draws a rectangle with the specified coordinates and gradient colors.
     *
     * @param x1     the x-coordinate of the first vertex
     * @param y1     the y-coordinate of the first vertex
     * @param x2     the x-coordinate of the second vertex
     * @param y2     the y-coordinate of the second vertex
     * @param color1 the color of the first vertex
     * @param color2 the color of the second vertex
     */
    public void rect(float x1, float y1, float x2, float y2, Color color1, Color color2) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color1).end();
        put().vertex(new Vector2f(x1, y2)).color(color1).end();

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle with the specified width, height, and gradient colors.
     *
     * @param x      the x-coordinate of the first vertex
     * @param y      the y-coordinate of the first vertex
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param color1 the color of the first vertex
     * @param color2 the color of the second vertex
     */
    public void rectWH(float x, float y, float width, float height, Color color1, Color color2) {
        rect(x, y, x + width, y + height, color1, color2);
    }

    /**
     * Draws a rectangle with the specified coordinates and gradient colors.
     *
     * @param x1     the x-coordinate of the first vertex
     * @param y1     the y-coordinate of the first vertex
     * @param x2     the x-coordinate of the second vertex
     * @param y2     the y-coordinate of the second vertex
     * @param color1 the color of the first vertex
     * @param color2 the color of the second vertex
     * @param color3 the color of the third vertex
     */
    public void rect(float x1, float y1, float x2, float y2, Color color1, Color color2, Color color3) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color3).end();
        put().vertex(new Vector2f(x1, y2)).color(color1).end();

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle with the specified width, height, and gradient colors.
     *
     * @param x      the x-coordinate of the first vertex
     * @param y      the y-coordinate of the first vertex
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param color1 the color of the first vertex
     * @param color2 the color of the second vertex
     * @param color3 the color of the third vertex
     */
    public void rectWH(float x, float y, float width, float height, Color color1, Color color2, Color color3) {
        rect(x, y, x + width, y + height, color1, color2, color3);
    }

    /**
     * Draws a rectangle with the specified coordinates and gradient colors.
     *
     * @param x1     the x-coordinate of the first vertex
     * @param y1     the y-coordinate of the first vertex
     * @param x2     the x-coordinate of the second vertex
     * @param y2     the y-coordinate of the second vertex
     * @param color1 the color of the first vertex
     * @param color2 the color of the second vertex
     * @param color3 the color of the third vertex
     * @param color4 the color of the fourth vertex
     */
    public void rect(float x1, float y1, float x2, float y2, Color color1, Color color2, Color color3, Color color4) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color3).end();
        put().vertex(new Vector2f(x1, y2)).color(color4).end();

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle with the specified width, height, and gradient colors.
     *
     * @param x      the x-coordinate of the first vertex
     * @param y      the y-coordinate of the first vertex
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param color1 the color of the first vertex
     * @param color2 the color of the second vertex
     * @param color3 the color of the third vertex
     * @param color4 the color of the fourth vertex
     */
    public void rectWH(float x, float y, float width, float height, Color color1, Color color2, Color color3, Color color4) {
        rect(x, y, x + width, y + height, color1, color2, color3, color4);
    }

    /**
     * Draws a rectangle outline with the specified coordinates, line width, and color.
     *
     * @param x1        the x-coordinate of the first vertex
     * @param y1        the y-coordinate of the first vertex
     * @param x2        the x-coordinate of the second vertex
     * @param y2        the y-coordinate of the second vertex
     * @param lineWidth the width of the line
     * @param color     the color of the outline
     */
    public void rectOutLine(float x1, float y1, float x2, float y2, float lineWidth, Color color) {
        put().vertex(new Vector2f(x1, y1)).color(color).end();
        put().vertex(new Vector2f(x2, y1)).color(color).end();
        put().vertex(new Vector2f(x2, y2)).color(color).end();
        put().vertex(new Vector2f(x1, y2)).color(color).end();
        setLineWidth(lineWidth);

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle outline with the specified width, height, line width, and color.
     *
     * @param x         the x-coordinate of the first vertex
     * @param y         the y-coordinate of the first vertex
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param lineWidth the width of the line
     * @param color     the color of the outline
     */
    public void rectWHOutLine(float x, float y, float width, float height, float lineWidth, Color color) {
        rectOutLine(x, y, x + width, y + height, lineWidth, color);
    }

    /**
     * Draws a rectangle outline with the specified coordinates, line width, and gradient colors.
     *
     * @param x1        the x-coordinate of the first vertex
     * @param y1        the y-coordinate of the first vertex
     * @param x2        the x-coordinate of the second vertex
     * @param y2        the y-coordinate of the second vertex
     * @param lineWidth the width of the line
     * @param color1    the color of the first vertex
     * @param color2    the color of the second vertex
     */
    public void rectOutLine(float x1, float y1, float x2, float y2, float lineWidth, Color color1, Color color2) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color1).end();
        put().vertex(new Vector2f(x1, y2)).color(color1).end();
        setLineWidth(lineWidth);

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle outline with the specified width, height, line width, and gradient colors.
     *
     * @param x         the x-coordinate of the first vertex
     * @param y         the y-coordinate of the first vertex
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param lineWidth the width of the line
     * @param color1    the color of the first vertex
     * @param color2    the color of the second vertex
     */
    public void rectWHOutLine(float x, float y, float width, float height, float lineWidth, Color color1, Color color2) {
        rectOutLine(x, y, x + width, y + height, lineWidth, color1, color2);
    }

    /**
     * Draws a rectangle outline with the specified coordinates, line width, and gradient colors.
     *
     * @param x1        the x-coordinate of the first vertex
     * @param y1        the y-coordinate of the first vertex
     * @param x2        the x-coordinate of the second vertex
     * @param y2        the y-coordinate of the second vertex
     * @param lineWidth the width of the line
     * @param color1    the color of the first vertex
     * @param color2    the color of the second vertex
     * @param color3    the color of the third vertex
     */
    public void rectOutLine(float x1, float y1, float x2, float y2, float lineWidth, Color color1, Color color2, Color color3) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color3).end();
        put().vertex(new Vector2f(x1, y2)).color(color1).end();
        setLineWidth(lineWidth);

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle outline with the specified width, height, line width, and gradient colors.
     *
     * @param x         the x-coordinate of the first vertex
     * @param y         the y-coordinate of the first vertex
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param lineWidth the width of the line
     * @param color1    the color of the first vertex
     * @param color2    the color of the second vertex
     * @param color3    the color of the third vertex
     */
    public void rectWHOutLine(float x, float y, float width, float height, float lineWidth, Color color1, Color color2, Color color3) {
        rectOutLine(x, y, x + width, y + height, lineWidth, color1, color2, color3);
    }

    /**
     * Draws a rectangle outline with the specified coordinates, line width, and gradient colors.
     *
     * @param x1        the x-coordinate of the first vertex
     * @param y1        the y-coordinate of the first vertex
     * @param x2        the x-coordinate of the second vertex
     * @param y2        the y-coordinate of the second vertex
     * @param lineWidth the width of the line
     * @param color1    the color of the first vertex
     * @param color2    the color of the second vertex
     * @param color3    the color of the third vertex
     * @param color4    the color of the fourth vertex
     */
    public void rectOutLine(float x1, float y1, float x2, float y2, float lineWidth, Color color1, Color color2, Color color3, Color color4) {
        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y1)).color(color2).end();
        put().vertex(new Vector2f(x2, y2)).color(color3).end();
        put().vertex(new Vector2f(x1, y2)).color(color4).end();
        setLineWidth(lineWidth);

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle outline with the specified width, height, line width, and gradient colors.
     *
     * @param x         the x-coordinate of the first vertex
     * @param y         the y-coordinate of the first vertex
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param lineWidth the width of the line
     * @param color1    the color of the first vertex
     * @param color2    the color of the second vertex
     * @param color3    the color of the third vertex
     * @param color4    the color of the fourth vertex
     */
    public void rectWHOutLine(float x, float y, float width, float height, float lineWidth, Color color1, Color color2, Color color3, Color color4) {
        rectOutLine(x, y, x + width, y + height, lineWidth, color1, color2, color3, color4);
    }

    /**
     * Draws a rectangle with the specified coordinates and color.
     *
     * @param x1    the x-coordinate of the first vertex
     * @param y1    the y-coordinate of the first vertex
     * @param x2    the x-coordinate of the second vertex
     * @param y2    the y-coordinate of the second vertex
     * @param color the color of the rectangle
     */
    public void rect(double x1, double y1, double x2, double y2, Color color) {
        put().vertex(new Vector2f((float) x1, (float) y1)).color(color).end();
        put().vertex(new Vector2f((float) x2, (float) y1)).color(color).end();
        put().vertex(new Vector2f((float) x2, (float) y2)).color(color).end();
        put().vertex(new Vector2f((float) x1, (float) y2)).color(color).end();

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle with the specified width and height.
     *
     * @param x      the x-coordinate of the first vertex
     * @param y      the y-coordinate of the first vertex
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param color  the color of the rectangle
     */
    public void rectWH(double x, double y, double width, double height, Color color) {
        rect(x, y, x + width, y + height, color);
    }

    /**
     * Draws a rectangle with the specified coordinates and gradient colors.
     *
     * @param x1     the x-coordinate of the first vertex
     * @param y1     the y-coordinate of the first vertex
     * @param x2     the x-coordinate of the second vertex
     * @param y2     the y-coordinate of the second vertex
     * @param color1 the color of the first vertex
     * @param color2 the color of the second vertex
     */
    public void rect(double x1, double y1, double x2, double y2, Color color1, Color color2) {
        put().vertex(new Vector2f((float) x1, (float) y1)).color(color1).end();
        put().vertex(new Vector2f((float) x2, (float) y1)).color(color2).end();
        put().vertex(new Vector2f((float) x2, (float) y2)).color(color1).end();
        put().vertex(new Vector2f((float) x1, (float) y2)).color(color1).end();

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle with the specified width, height, and gradient colors.
     *
     * @param x      the x-coordinate of the first vertex
     * @param y      the y-coordinate of the first vertex
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param color1 the color of the first vertex
     * @param color2 the color of the second vertex
     */
    public void rectWH(double x, double y, double width, double height, Color color1, Color color2) {
        rect(x, y, x + width, y + height, color1, color2);
    }

    /**
     * Draws a rectangle with the specified coordinates and gradient colors.
     *
     * @param x1     the x-coordinate of the first vertex
     * @param y1     the y-coordinate of the first vertex
     * @param x2     the x-coordinate of the second vertex
     * @param y2     the y-coordinate of the second vertex
     * @param color1 the color of the first vertex
     * @param color2 the color of the second vertex
     * @param color3 the color of the third vertex
     */
    public void rect(double x1, double y1, double x2, double y2, Color color1, Color color2, Color color3) {
        put().vertex(new Vector2f((float) x1, (float) y1)).color(color1).end();
        put().vertex(new Vector2f((float) x2, (float) y1)).color(color2).end();
        put().vertex(new Vector2f((float) x2, (float) y2)).color(color3).end();
        put().vertex(new Vector2f((float) x1, (float) y2)).color(color1).end();

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle with the specified width, height, and gradient colors.
     *
     * @param x      the x-coordinate of the first vertex
     * @param y      the y-coordinate of the first vertex
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param color1 the color of the first vertex
     * @param color2 the color of the second vertex
     * @param color3 the color of the third vertex
     */
    public void rectWH(double x, double y, double width, double height, Color color1, Color color2, Color color3) {
        rect(x, y, x + width, y + height, color1, color2, color3);
    }

    /**
     * Draws a rectangle with the specified coordinates and gradient colors.
     *
     * @param x1     the x-coordinate of the first vertex
     * @param y1     the y-coordinate of the first vertex
     * @param x2     the x-coordinate of the second vertex
     * @param y2     the y-coordinate of the second vertex
     * @param color1 the color of the first vertex
     * @param color2 the color of the second vertex
     * @param color3 the color of the third vertex
     * @param color4 the color of the fourth vertex
     */
    public void rect(double x1, double y1, double x2, double y2, Color color1, Color color2, Color color3, Color color4) {
        put().vertex(new Vector2f((float) x1, (float) y1)).color(color1).end();
        put().vertex(new Vector2f((float) x2, (float) y1)).color(color2).end();
        put().vertex(new Vector2f((float) x2, (float) y2)).color(color3).end();
        put().vertex(new Vector2f((float) x1, (float) y2)).color(color4).end();

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle with the specified width, height, and gradient colors.
     *
     * @param x      the x-coordinate of the first vertex
     * @param y      the y-coordinate of the first vertex
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param color1 the color of the first vertex
     * @param color2 the color of the second vertex
     * @param color3 the color of the third vertex
     * @param color4 the color of the fourth vertex
     */
    public void rectWH(double x, double y, double width, double height, Color color1, Color color2, Color color3, Color color4) {
        rect(x, y, x + width, y + height, color1, color2, color3, color4);
    }

    /**
     * Draws a rectangle outline with the specified coordinates, line width, and color.
     *
     * @param x1        the x-coordinate of the first vertex
     * @param y1        the y-coordinate of the first vertex
     * @param x2        the x-coordinate of the second vertex
     * @param y2        the y-coordinate of the second vertex
     * @param lineWidth the width of the line
     * @param color     the color of the outline
     */
    public void rectOutLine(double x1, double y1, double x2, double y2, double lineWidth, Color color) {
        put().vertex(new Vector2f((float) x1, (float) y1)).color(color).end();
        put().vertex(new Vector2f((float) x2, (float) y1)).color(color).end();
        put().vertex(new Vector2f((float) x2, (float) y2)).color(color).end();
        put().vertex(new Vector2f((float) x1, (float) y2)).color(color).end();
        setLineWidth((float) lineWidth);

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle outline with the specified width, height, line width, and color.
     *
     * @param x         the x-coordinate of the first vertex
     * @param y         the y-coordinate of the first vertex
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param lineWidth the width of the line
     * @param color     the color of the outline
     */
    public void rectWHOutLine(double x, double y, double width, double height, double lineWidth, Color color) {
        rectOutLine(x, y, x + width, y + height, lineWidth, color);
    }

    /**
     * Draws a rectangle outline with the specified coordinates, line width, and gradient colors.
     *
     * @param x1        the x-coordinate of the first vertex
     * @param y1        the y-coordinate of the first vertex
     * @param x2        the x-coordinate of the second vertex
     * @param y2        the y-coordinate of the second vertex
     * @param lineWidth the width of the line
     * @param color1    the color of the first vertex
     * @param color2    the color of the second vertex
     */
    public void rectOutLine(double x1, double y1, double x2, double y2, double lineWidth, Color color1, Color color2) {
        put().vertex(new Vector2f((float) x1, (float) y1)).color(color1).end();
        put().vertex(new Vector2f((float) x2, (float) y1)).color(color2).end();
        put().vertex(new Vector2f((float) x2, (float) y2)).color(color1).end();
        put().vertex(new Vector2f((float) x1, (float) y2)).color(color1).end();
        setLineWidth((float) lineWidth);

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle outline with the specified width, height, line width, and gradient colors.
     *
     * @param x         the x-coordinate of the first vertex
     * @param y         the y-coordinate of the first vertex
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param lineWidth the width of the line
     * @param color1    the color of the first vertex
     * @param color2    the color of the second vertex
     */
    public void rectWHOutLine(double x, double y, double width, double height, double lineWidth, Color color1, Color color2) {
        rectOutLine(x, y, x + width, y + height, lineWidth, color1, color2);
    }

    /**
     * Draws a rectangle outline with the specified coordinates, line width, and gradient colors.
     *
     * @param x1        the x-coordinate of the first vertex
     * @param y1        the y-coordinate of the first vertex
     * @param x2        the x-coordinate of the second vertex
     * @param y2        the y-coordinate of the second vertex
     * @param lineWidth the width of the line
     * @param color1    the color of the first vertex
     * @param color2    the color of the second vertex
     * @param color3    the color of the third vertex
     */
    public void rectOutLine(double x1, double y1, double x2, double y2, double lineWidth, Color color1, Color color2, Color color3) {
        put().vertex(new Vector2f((float) x1, (float) y1)).color(color1).end();
        put().vertex(new Vector2f((float) x2, (float) y1)).color(color2).end();
        put().vertex(new Vector2f((float) x2, (float) y2)).color(color3).end();
        put().vertex(new Vector2f((float) x1, (float) y2)).color(color1).end();
        setLineWidth((float) lineWidth);

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle outline with the specified width, height, line width, and gradient colors.
     *
     * @param x         the x-coordinate of the first vertex
     * @param y         the y-coordinate of the first vertex
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param lineWidth the width of the line
     * @param color1    the color of the first vertex
     * @param color2    the color of the second vertex
     * @param color3    the color of the third vertex
     */
    public void rectWHOutLine(double x, double y, double width, double height, double lineWidth, Color color1, Color color2, Color color3) {
        rectOutLine(x, y, x + width, y + height, lineWidth, color1, color2, color3);
    }

    /**
     * Draws a rectangle outline with the specified coordinates, line width, and gradient colors.
     *
     * @param x1        the x-coordinate of the first vertex
     * @param y1        the y-coordinate of the first vertex
     * @param x2        the x-coordinate of the second vertex
     * @param y2        the y-coordinate of the second vertex
     * @param lineWidth the width of the line
     * @param color1    the color of the first vertex
     * @param color2    the color of the second vertex
     * @param color3    the color of the third vertex
     * @param color4    the color of the fourth vertex
     */
    public void rectOutLine(double x1, double y1, double x2, double y2, double lineWidth, Color color1, Color color2, Color color3, Color color4) {
        put().vertex(new Vector2f((float) x1, (float) y1)).color(color1).end();
        put().vertex(new Vector2f((float) x2, (float) y1)).color(color2).end();
        put().vertex(new Vector2f((float) x2, (float) y2)).color(color3).end();
        put().vertex(new Vector2f((float) x1, (float) y2)).color(color4).end();
        setLineWidth((float) lineWidth);

        setDrawType(DrawType.QUADS);
        if (!isUpdate()) {
            rendering();
        } else {
            updateSubData();
        }
    }

    /**
     * Draws a rectangle outline with the specified width, height, line width, and gradient colors.
     *
     * @param x         the x-coordinate of the first vertex
     * @param y         the y-coordinate of the first vertex
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param lineWidth the width of the line
     * @param color1    the color of the first vertex
     * @param color2    the color of the second vertex
     * @param color3    the color of the third vertex
     * @param color4    the color of the fourth vertex
     */
    public void rectWHOutLine(double x, double y, double width, double height, double lineWidth, Color color1, Color color2, Color color3, Color color4) {
        rectOutLine(x, y, x + width, y + height, lineWidth, color1, color2, color3, color4);
    }
}
