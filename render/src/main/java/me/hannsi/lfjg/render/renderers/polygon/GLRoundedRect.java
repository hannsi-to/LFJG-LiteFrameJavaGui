package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;

/**
 * Class representing a rounded rectangle renderer in OpenGL.
 */
public class GLRoundedRect extends GLPolygon {
    public static final int defaultSegmentCount = 16;

    /**
     * Constructs a new GLRoundedRect with the specified name.
     *
     * @param name the name of the rounded rectangle renderer
     */
    public GLRoundedRect(String name) {
        super(name);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param x1                the x-coordinate of the top-right corner
     * @param y1                the y-coordinate of the top-right corner
     * @param leftTop           whether to round the top-left corner
     * @param rightTop          whether to round the top-right corner
     * @param rightBottom       whether to round the bottom-right corner
     * @param leftBottom        whether to round the bottom-left corner
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param segmentCount      the number of segments for the rounded corners
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRect(float x, float y, float x1, float y1, boolean leftTop, boolean rightTop, boolean rightBottom, boolean leftBottom, float leftTopRadius, float rightTopRadius, float rightBottomRadius, float leftBottomRadius, int segmentCount, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        float i;

        if (leftBottom) {
            for (i = 0; i <= 90; i += segmentCount) {
                put().vertex(new Vector2f((float) (x + leftBottomRadius + MathHelper.sin(i * MathHelper.PI / 180.0D) * leftBottomRadius * -1.0D), (float) (y + leftBottomRadius + MathHelper.cos(i * MathHelper.PI / 180.0D) * leftBottomRadius * -1.0D))).color(bottomLeftColor).end();
            }
        } else {
            put().vertex(new Vector2f(x, y)).color(bottomLeftColor).end();
        }

        if (leftTop) {
            for (i = 90; i <= 180; i += segmentCount) {
                put().vertex(new Vector2f((float) (x + leftTopRadius + MathHelper.sin(i * MathHelper.PI / 180.0D) * leftTopRadius * -1.0D), (float) (y1 - leftTopRadius + MathHelper.cos(i * MathHelper.PI / 180.0D) * leftTopRadius * -1.0D))).color(topLeftColor).end();
            }
        } else {
            put().vertex(new Vector2f(x, y1)).color(topLeftColor).end();
        }

        if (rightTop) {
            for (i = 0; i <= 90; i += segmentCount) {
                put().vertex(new Vector2f((float) (x1 - rightTopRadius + MathHelper.sin(i * MathHelper.PI / 180.0D) * rightTopRadius), (float) (y1 - rightTopRadius + MathHelper.cos(i * MathHelper.PI / 180.0D) * rightTopRadius))).color(topRightColor).end();
            }
        } else {
            put().vertex(new Vector2f(x1, y1)).color(topRightColor).end();
        }

        if (rightBottom) {
            for (i = 90; i <= 180; i += segmentCount) {
                put().vertex(new Vector2f((float) (x1 - rightBottomRadius + MathHelper.sin(i * MathHelper.PI / 180.0D) * rightBottomRadius), (float) (y + rightBottomRadius + MathHelper.cos(i * MathHelper.PI / 180.0D) * rightBottomRadius))).color(bottomRightColor).end();
            }
        } else {
            put().vertex(new Vector2f(x1, y)).color(bottomRightColor).end();
        }

        setDrawType(DrawType.POLYGON);
        rendering();
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param x1                the x-coordinate of the top-right corner
     * @param y1                the y-coordinate of the top-right corner
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param segmentCount      the number of segments for the rounded corners
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRect(float x, float y, float x1, float y1, float leftTopRadius, float rightTopRadius, float rightBottomRadius, float leftBottomRadius, int segmentCount, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRect(x, y, x1, y1, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, segmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param x1                the x-coordinate of the top-right corner
     * @param y1                the y-coordinate of the top-right corner
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRect(float x, float y, float x1, float y1, float leftTopRadius, float rightTopRadius, float rightBottomRadius, float leftBottomRadius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRect(x, y, x1, y1, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                the x-coordinate of the bottom-left corner
     * @param y                the y-coordinate of the bottom-left corner
     * @param x1               the x-coordinate of the top-right corner
     * @param y1               the y-coordinate of the top-right corner
     * @param radius           the radius of the corners
     * @param topLeftColor     the color of the top-left corner
     * @param topRightColor    the color of the top-right corner
     * @param bottomRightColor the color of the bottom-right corner
     * @param bottomLeftColor  the color of the bottom-left corner
     */
    public void roundedRect(float x, float y, float x1, float y1, float radius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRect(x, y, x1, y1, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x      the x-coordinate of the bottom-left corner
     * @param y      the y-coordinate of the bottom-left corner
     * @param x1     the x-coordinate of the top-right corner
     * @param y1     the y-coordinate of the top-right corner
     * @param radius the radius of the corners
     * @param color  the color of the corners
     */
    public void roundedRect(float x, float y, float x1, float y1, float radius, Color color) {
        roundedRect(x, y, x1, y1, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, color, color, color, color);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param width             the width of the rectangle
     * @param height            the height of the rectangle
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param segmentCount      the number of segments for the rounded corners
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectWH(float x, float y, float width, float height, float leftTopRadius, float rightTopRadius, float rightBottomRadius, float leftBottomRadius, int segmentCount, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRect(x, y, x + width, y + height, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, segmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param width             the width of the rectangle
     * @param height            the height of the rectangle
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectWH(float x, float y, float width, float height, float leftTopRadius, float rightTopRadius, float rightBottomRadius, float leftBottomRadius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRect(x, y, x + width, y + height, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                the x-coordinate of the bottom-left corner
     * @param y                the y-coordinate of the bottom-left corner
     * @param width            the width of the rectangle
     * @param height           the height of the rectangle
     * @param radius           the radius of the corners
     * @param topLeftColor     the color of the top-left corner
     * @param topRightColor    the color of the top-right corner
     * @param bottomRightColor the color of the bottom-right corner
     * @param bottomLeftColor  the color of the bottom-left corner
     */
    public void roundedRectWH(float x, float y, float width, float height, float radius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRect(x, y, x + width, y + height, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x      the x-coordinate of the bottom-left corner
     * @param y      the y-coordinate of the bottom-left corner
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param radius the radius of the corners
     * @param color  the color of the corners
     */
    public void roundedRectWH(float x, float y, float width, float height, float radius, Color color) {
        roundedRect(x, y, x + width, y + height, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, color, color, color, color);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param x1                the x-coordinate of the top-right corner
     * @param y1                the y-coordinate of the top-right corner
     * @param lineWidth         the width of the outline
     * @param leftTop           whether to round the top-left corner
     * @param rightTop          whether to round the top-right corner
     * @param rightBottom       whether to round the bottom-right corner
     * @param leftBottom        whether to round the bottom-left corner
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param segmentCount      the number of segments for the rounded corners
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectOutLine(float x, float y, float x1, float y1, float lineWidth, boolean leftTop, boolean rightTop, boolean rightBottom, boolean leftBottom, float leftTopRadius, float rightTopRadius, float rightBottomRadius, float leftBottomRadius, int segmentCount, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        float i;

        if (leftBottom) {
            for (i = 0; i <= 90; i += segmentCount) {
                put().vertex(new Vector2f((float) (x + leftBottomRadius + MathHelper.sin(i * MathHelper.PI / 180.0D) * leftBottomRadius * -1.0D), (float) (y + leftBottomRadius + MathHelper.cos(i * MathHelper.PI / 180.0D) * leftBottomRadius * -1.0D))).color(bottomLeftColor).end();
            }
        } else {
            put().vertex(new Vector2f(x, y)).color(bottomLeftColor).end();
        }

        if (leftTop) {
            for (i = 90; i <= 180; i += segmentCount) {
                put().vertex(new Vector2f((float) (x + leftTopRadius + MathHelper.sin(i * MathHelper.PI / 180.0D) * leftTopRadius * -1.0D), (float) (y1 - leftTopRadius + MathHelper.cos(i * MathHelper.PI / 180.0D) * leftTopRadius * -1.0D))).color(topLeftColor).end();
            }
        } else {
            put().vertex(new Vector2f(x, y1)).color(topLeftColor).end();
        }

        if (rightTop) {
            for (i = 0; i <= 90; i += segmentCount) {
                put().vertex(new Vector2f((float) (x1 - rightTopRadius + MathHelper.sin(i * MathHelper.PI / 180.0D) * rightTopRadius), (float) (y1 - rightTopRadius + MathHelper.cos(i * MathHelper.PI / 180.0D) * rightTopRadius))).color(topRightColor).end();
            }
        } else {
            put().vertex(new Vector2f(x1, y1)).color(topRightColor).end();
        }

        if (rightBottom) {
            for (i = 90; i <= 180; i += segmentCount) {
                put().vertex(new Vector2f((float) (x1 - rightBottomRadius + MathHelper.sin(i * MathHelper.PI / 180.0D) * rightBottomRadius), (float) (y + rightBottomRadius + MathHelper.cos(i * MathHelper.PI / 180.0D) * rightBottomRadius))).color(bottomRightColor).end();
            }
        } else {
            put().vertex(new Vector2f(x1, y)).color(bottomRightColor).end();
        }

        setDrawType(DrawType.LINE_LOOP).setLineWidth(lineWidth);
        rendering();
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param x1                the x-coordinate of the top-right corner
     * @param y1                the y-coordinate of the top-right corner
     * @param lineWidth         the width of the outline
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param segmentCount      the number of segments for the rounded corners
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectOutLine(float x, float y, float x1, float y1, float lineWidth, float leftTopRadius, float rightTopRadius, float rightBottomRadius, float leftBottomRadius, int segmentCount, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRectOutLine(x, y, x1, y1, lineWidth, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, segmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param x1                the x-coordinate of the top-right corner
     * @param y1                the y-coordinate of the top-right corner
     * @param lineWidth         the width of the outline
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectOutLine(float x, float y, float x1, float y1, float lineWidth, float leftTopRadius, float rightTopRadius, float rightBottomRadius, float leftBottomRadius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRectOutLine(x, y, x1, y1, lineWidth, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                the x-coordinate of the bottom-left corner
     * @param y                the y-coordinate of the bottom-left corner
     * @param x1               the x-coordinate of the top-right corner
     * @param y1               the y-coordinate of the top-right corner
     * @param lineWidth        the width of the outline
     * @param radius           the radius of the corners
     * @param topLeftColor     the color of the top-left corner
     * @param topRightColor    the color of the top-right corner
     * @param bottomRightColor the color of the bottom-right corner
     * @param bottomLeftColor  the color of the bottom-left corner
     */
    public void roundedRectOutLine(float x, float y, float x1, float y1, float lineWidth, float radius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRectOutLine(x, y, x1, y1, lineWidth, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x         the x-coordinate of the bottom-left corner
     * @param y         the y-coordinate of the bottom-left corner
     * @param x1        the x-coordinate of the top-right corner
     * @param y1        the y-coordinate of the top-right corner
     * @param lineWidth the width of the outline
     * @param radius    the radius of the corners
     * @param color     the color of the corners
     */
    public void roundedRectOutLine(float x, float y, float x1, float y1, float lineWidth, float radius, Color color) {
        roundedRectOutLine(x, y, x1, y1, lineWidth, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, color, color, color, color);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param width             the width of the rectangle
     * @param height            the height of the rectangle
     * @param lineWidth         the width of the outline
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param segmentCount      the number of segments for the rounded corners
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectWHOutLine(float x, float y, float width, float height, float lineWidth, float leftTopRadius, float rightTopRadius, float rightBottomRadius, float leftBottomRadius, int segmentCount, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRectOutLine(x, y, x + width, y + height, lineWidth, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, segmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param width             the width of the rectangle
     * @param height            the height of the rectangle
     * @param lineWidth         the width of the outline
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectWHOutLine(float x, float y, float width, float height, float lineWidth, float leftTopRadius, float rightTopRadius, float rightBottomRadius, float leftBottomRadius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRectOutLine(x, y, x + width, y + height, lineWidth, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                the x-coordinate of the bottom-left corner
     * @param y                the y-coordinate of the bottom-left corner
     * @param width            the width of the rectangle
     * @param height           the height of the rectangle
     * @param lineWidth        the width of the outline
     * @param radius           the radius of the corners
     * @param topLeftColor     the color of the top-left corner
     * @param topRightColor    the color of the top-right corner
     * @param bottomRightColor the color of the bottom-right corner
     * @param bottomLeftColor  the color of the bottom-left corner
     */
    public void roundedRectWHOutLine(float x, float y, float width, float height, float lineWidth, float radius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRectOutLine(x, y, x + width, y + height, lineWidth, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x         the x-coordinate of the bottom-left corner
     * @param y         the y-coordinate of the bottom-left corner
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param lineWidth the width of the outline
     * @param radius    the radius of the corners
     * @param color     the color of the corners
     */
    public void roundedRectWHOutLine(float x, float y, float width, float height, float lineWidth, float radius, Color color) {
        roundedRectOutLine(x, y, x + width, y + height, lineWidth, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, color, color, color, color);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param x1                the x-coordinate of the top-right corner
     * @param y1                the y-coordinate of the top-right corner
     * @param leftTop           whether to round the top-left corner
     * @param rightTop          whether to round the top-right corner
     * @param rightBottom       whether to round the bottom-right corner
     * @param leftBottom        whether to round the bottom-left corner
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param segmentCount      the number of segments for the rounded corners
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRect(double x, double y, double x1, double y1, boolean leftTop, boolean rightTop, boolean rightBottom, boolean leftBottom, double leftTopRadius, double rightTopRadius, double rightBottomRadius, double leftBottomRadius, int segmentCount, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        double i;

        if (leftBottom) {
            for (i = 0; i <= 90; i += segmentCount) {
                put().vertex(new Vector2f((float) (x + leftBottomRadius + MathHelper.sin(i * MathHelper.PI / 180.0) * leftBottomRadius * -1.0), (float) (y + leftBottomRadius + MathHelper.cos(i * MathHelper.PI / 180.0) * leftBottomRadius * -1.0))).color(bottomLeftColor).end();
            }
        } else {
            put().vertex(new Vector2f((float) x, (float) y)).color(bottomLeftColor).end();
        }

        if (leftTop) {
            for (i = 90; i <= 180; i += segmentCount) {
                put().vertex(new Vector2f((float) (x + leftTopRadius + MathHelper.sin(i * MathHelper.PI / 180.0) * leftTopRadius * -1.0), (float) (y1 - leftTopRadius + MathHelper.cos(i * MathHelper.PI / 180.0) * leftTopRadius * -1.0))).color(topLeftColor).end();
            }
        } else {
            put().vertex(new Vector2f((float) x, (float) y1)).color(topLeftColor).end();
        }

        if (rightTop) {
            for (i = 0; i <= 90; i += segmentCount) {
                put().vertex(new Vector2f((float) (x1 - rightTopRadius + MathHelper.sin(i * MathHelper.PI / 180.0) * rightTopRadius), (float) (y1 - rightTopRadius + MathHelper.cos(i * MathHelper.PI / 180.0) * rightTopRadius))).color(topRightColor).end();
            }
        } else {
            put().vertex(new Vector2f((float) x1, (float) y1)).color(topRightColor).end();
        }

        if (rightBottom) {
            for (i = 90; i <= 180; i += segmentCount) {
                put().vertex(new Vector2f((float) (x1 - rightBottomRadius + MathHelper.sin(i * MathHelper.PI / 180.0) * rightBottomRadius), (float) (y + rightBottomRadius + MathHelper.cos(i * MathHelper.PI / 180.0) * rightBottomRadius))).color(bottomRightColor).end();
            }
        } else {
            put().vertex(new Vector2f((float) x1, (float) y)).color(bottomRightColor).end();
        }

        setDrawType(DrawType.POLYGON);
        rendering();
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param x1                the x-coordinate of the top-right corner
     * @param y1                the y-coordinate of the top-right corner
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param segmentCount      the number of segments for the rounded corners
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRect(double x, double y, double x1, double y1, double leftTopRadius, double rightTopRadius, double rightBottomRadius, double leftBottomRadius, int segmentCount, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRect(x, y, x1, y1, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, segmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param x1                the x-coordinate of the top-right corner
     * @param y1                the y-coordinate of the top-right corner
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRect(double x, double y, double x1, double y1, double leftTopRadius, double rightTopRadius, double rightBottomRadius, double leftBottomRadius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRect(x, y, x1, y1, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                the x-coordinate of the bottom-left corner
     * @param y                the y-coordinate of the bottom-left corner
     * @param x1               the x-coordinate of the top-right corner
     * @param y1               the y-coordinate of the top-right corner
     * @param radius           the radius of the corners
     * @param topLeftColor     the color of the top-left corner
     * @param topRightColor    the color of the top-right corner
     * @param bottomRightColor the color of the bottom-right corner
     * @param bottomLeftColor  the color of the bottom-left corner
     */
    public void roundedRect(double x, double y, double x1, double y1, double radius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRect(x, y, x1, y1, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x      the x-coordinate of the bottom-left corner
     * @param y      the y-coordinate of the bottom-left corner
     * @param x1     the x-coordinate of the top-right corner
     * @param y1     the y-coordinate of the top-right corner
     * @param radius the radius of the corners
     * @param color  the color of the corners
     */
    public void roundedRect(double x, double y, double x1, double y1, double radius, Color color) {
        roundedRect(x, y, x1, y1, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, color, color, color, color);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param width             the width of the rectangle
     * @param height            the height of the rectangle
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param segmentCount      the number of segments for the rounded corners
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectWH(double x, double y, double width, double height, double leftTopRadius, double rightTopRadius, double rightBottomRadius, double leftBottomRadius, int segmentCount, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRect(x, y, x + width, y + height, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, segmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param width             the width of the rectangle
     * @param height            the height of the rectangle
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectWH(double x, double y, double width, double height, double leftTopRadius, double rightTopRadius, double rightBottomRadius, double leftBottomRadius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRect(x, y, x + width, y + height, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x                the x-coordinate of the bottom-left corner
     * @param y                the y-coordinate of the bottom-left corner
     * @param width            the width of the rectangle
     * @param height           the height of the rectangle
     * @param radius           the radius of the corners
     * @param topLeftColor     the color of the top-left corner
     * @param topRightColor    the color of the top-right corner
     * @param bottomRightColor the color of the bottom-right corner
     * @param bottomLeftColor  the color of the bottom-left corner
     */
    public void roundedRectWH(double x, double y, double width, double height, double radius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRect(x, y, x + width, y + height, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle with the specified parameters.
     *
     * @param x      the x-coordinate of the bottom-left corner
     * @param y      the y-coordinate of the bottom-left corner
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param radius the radius of the corners
     * @param color  the color of the corners
     */
    public void roundedRectWH(double x, double y, double width, double height, double radius, Color color) {
        roundedRect(x, y, x + width, y + height, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, color, color, color, color);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param x1                the x-coordinate of the top-right corner
     * @param y1                the y-coordinate of the top-right corner
     * @param lineWidth         the width of the outline
     * @param leftTop           whether to round the top-left corner
     * @param rightTop          whether to round the top-right corner
     * @param rightBottom       whether to round the bottom-right corner
     * @param leftBottom        whether to round the bottom-left corner
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param segmentCount      the number of segments for the rounded corners
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectOutLine(double x, double y, double x1, double y1, double lineWidth, boolean leftTop, boolean rightTop, boolean rightBottom, boolean leftBottom, double leftTopRadius, double rightTopRadius, double rightBottomRadius, double leftBottomRadius, int segmentCount, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        double i;

        if (leftBottom) {
            for (i = 0; i <= 90; i += segmentCount) {
                put().vertex(new Vector2f((float) (x + leftBottomRadius + MathHelper.sin(i * MathHelper.PI / 180.0D) * leftBottomRadius * -1.0D), (float) (y + leftBottomRadius + MathHelper.cos(i * MathHelper.PI / 180.0D) * leftBottomRadius * -1.0D))).color(bottomLeftColor).end();
            }
        } else {
            put().vertex(new Vector2f((float) x, (float) y)).color(bottomLeftColor).end();
        }

        if (leftTop) {
            for (i = 90; i <= 180; i += segmentCount) {
                put().vertex(new Vector2f((float) (x + leftTopRadius + MathHelper.sin(i * MathHelper.PI / 180.0D) * leftTopRadius * -1.0D), (float) (y1 - leftTopRadius + MathHelper.cos(i * MathHelper.PI / 180.0D) * leftTopRadius * -1.0D))).color(topLeftColor).end();
            }
        } else {
            put().vertex(new Vector2f((float) x, (float) y1)).color(topLeftColor).end();
        }

        if (rightTop) {
            for (i = 0; i <= 90; i += segmentCount) {
                put().vertex(new Vector2f((float) (x1 - rightTopRadius + MathHelper.sin(i * MathHelper.PI / 180.0D) * rightTopRadius), (float) (y1 - rightTopRadius + MathHelper.cos(i * MathHelper.PI / 180.0D) * rightTopRadius))).color(topRightColor).end();
            }
        } else {
            put().vertex(new Vector2f((float) x1, (float) y1)).color(topRightColor).end();
        }

        if (rightBottom) {
            for (i = 90; i <= 180; i += segmentCount) {
                put().vertex(new Vector2f((float) (x1 - rightBottomRadius + MathHelper.sin(i * MathHelper.PI / 180.0D) * rightBottomRadius), (float) (y + rightBottomRadius + MathHelper.cos(i * MathHelper.PI / 180.0D) * rightBottomRadius))).color(bottomRightColor).end();
            }
        } else {
            put().vertex(new Vector2f((float) x1, (float) y)).color(bottomRightColor).end();
        }

        setDrawType(DrawType.LINE_LOOP).setLineWidth((float) lineWidth);
        rendering();
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param x1                the x-coordinate of the top-right corner
     * @param y1                the y-coordinate of the top-right corner
     * @param lineWidth         the width of the outline
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param segmentCount      the number of segments for the rounded corners
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectOutLine(double x, double y, double x1, double y1, double lineWidth, double leftTopRadius, double rightTopRadius, double rightBottomRadius, double leftBottomRadius, int segmentCount, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRectOutLine(x, y, x1, y1, lineWidth, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, segmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param x1                the x-coordinate of the top-right corner
     * @param y1                the y-coordinate of the top-right corner
     * @param lineWidth         the width of the outline
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectOutLine(double x, double y, double x1, double y1, double lineWidth, double leftTopRadius, double rightTopRadius, double rightBottomRadius, double leftBottomRadius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRectOutLine(x, y, x1, y1, lineWidth, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                the x-coordinate of the bottom-left corner
     * @param y                the y-coordinate of the bottom-left corner
     * @param x1               the x-coordinate of the top-right corner
     * @param y1               the y-coordinate of the top-right corner
     * @param lineWidth        the width of the outline
     * @param radius           the radius of the corners
     * @param topLeftColor     the color of the top-left corner
     * @param topRightColor    the color of the top-right corner
     * @param bottomRightColor the color of the bottom-right corner
     * @param bottomLeftColor  the color of the bottom-left corner
     */
    public void roundedRectOutLine(double x, double y, double x1, double y1, double lineWidth, double radius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRectOutLine(x, y, x1, y1, lineWidth, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x         the x-coordinate of the bottom-left corner
     * @param y         the y-coordinate of the bottom-left corner
     * @param x1        the x-coordinate of the top-right corner
     * @param y1        the y-coordinate of the top-right corner
     * @param lineWidth the width of the outline
     * @param radius    the radius of the corners
     * @param color     the color of the corners
     */
    public void roundedRectOutLine(double x, double y, double x1, double y1, double lineWidth, double radius, Color color) {
        roundedRectOutLine(x, y, x1, y1, lineWidth, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, color, color, color, color);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param width             the width of the rectangle
     * @param height            the height of the rectangle
     * @param lineWidth         the width of the outline
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param segmentCount      the number of segments for the rounded corners
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectWHOutLine(double x, double y, double width, double height, double lineWidth, double leftTopRadius, double rightTopRadius, double rightBottomRadius, double leftBottomRadius, int segmentCount, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRectOutLine(x, y, x + width, y + height, lineWidth, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, segmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                 the x-coordinate of the bottom-left corner
     * @param y                 the y-coordinate of the bottom-left corner
     * @param width             the width of the rectangle
     * @param height            the height of the rectangle
     * @param lineWidth         the width of the outline
     * @param leftTopRadius     the radius of the top-left corner
     * @param rightTopRadius    the radius of the top-right corner
     * @param rightBottomRadius the radius of the bottom-right corner
     * @param leftBottomRadius  the radius of the bottom-left corner
     * @param topLeftColor      the color of the top-left corner
     * @param topRightColor     the color of the top-right corner
     * @param bottomRightColor  the color of the bottom-right corner
     * @param bottomLeftColor   the color of the bottom-left corner
     */
    public void roundedRectWHOutLine(double x, double y, double width, double height, double lineWidth, double leftTopRadius, double rightTopRadius, double rightBottomRadius, double leftBottomRadius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRectOutLine(x, y, x + width, y + height, lineWidth, true, true, true, true, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x                the x-coordinate of the bottom-left corner
     * @param y                the y-coordinate of the bottom-left corner
     * @param width            the width of the rectangle
     * @param height           the height of the rectangle
     * @param lineWidth        the width of the outline
     * @param radius           the radius of the corners
     * @param topLeftColor     the color of the top-left corner
     * @param topRightColor    the color of the top-right corner
     * @param bottomRightColor the color of the bottom-right corner
     * @param bottomLeftColor  the color of the bottom-left corner
     */
    public void roundedRectWHOutLine(double x, double y, double width, double height, double lineWidth, double radius, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        roundedRectOutLine(x, y, x + width, y + height, lineWidth, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    /**
     * Draws a rounded rectangle outline with the specified parameters.
     *
     * @param x         the x-coordinate of the bottom-left corner
     * @param y         the y-coordinate of the bottom-left corner
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param lineWidth the width of the outline
     * @param radius    the radius of the corners
     * @param color     the color of the corners
     */
    public void roundedRectWHOutLine(double x, double y, double width, double height, double lineWidth, double radius, Color color) {
        roundedRectOutLine(x, y, x + width, y + height, lineWidth, true, true, true, true, radius, radius, radius, radius, defaultSegmentCount, color, color, color, color);
    }
}