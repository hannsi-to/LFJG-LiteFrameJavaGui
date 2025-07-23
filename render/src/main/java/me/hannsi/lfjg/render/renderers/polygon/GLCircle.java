package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;

import static me.hannsi.lfjg.core.utils.math.MathHelper.*;

/**
 * Class representing a circle renderer in OpenGL.
 */
public class GLCircle extends GLPolygon {
    /**
     * Constructs a new GLCircle with the specified name.
     *
     * @param name the name of the circle renderer
     */
    public GLCircle(String name) {
        super(name);
    }

    /**
     * Draws a filled circle with the specified parameters.
     *
     * @param xCenter      the x-coordinate of the circle center
     * @param yCenter      the y-coordinate of the circle center
     * @param radius       the radius of the circle
     * @param segmentCount the number of segments to use for the circle
     * @param colors       the colors to use for the circle
     */
    public void circle(double xCenter, double yCenter, double radius, int segmentCount, Color... colors) {
        circle((float) xCenter, (float) yCenter, (float) radius, (float) radius, segmentCount, colors);
    }

    /**
     * Draws a filled circle with the specified parameters.
     *
     * @param xCenter      the x-coordinate of the circle center
     * @param yCenter      the y-coordinate of the circle center
     * @param radius       the radius of the circle
     * @param segmentCount the number of segments to use for the circle
     * @param colors       the colors to use for the circle
     */
    public void circle(float xCenter, float yCenter, float radius, int segmentCount, Color... colors) {
        circle(xCenter, yCenter, radius, radius, segmentCount, colors);
    }

    /**
     * Draws a filled circle with the specified parameters.
     *
     * @param xCenter      the x-coordinate of the circle center
     * @param yCenter      the y-coordinate of the circle center
     * @param xRadius      the x-radius of the circle
     * @param yRadius      the y-radius of the circle
     * @param segmentCount the number of segments to use for the circle
     * @param colors       the colors to use for the circle
     */
    public void circle(double xCenter, double yCenter, double xRadius, double yRadius, int segmentCount, Color... colors) {
        circle((float) xCenter, (float) yCenter, (float) xRadius, (float) yRadius, segmentCount, colors);
    }

    /**
     * Draws a filled circle with the specified parameters.
     *
     * @param xCenter      the x-coordinate of the circle center
     * @param yCenter      the y-coordinate of the circle center
     * @param xRadius      the x-radius of the circle
     * @param yRadius      the y-radius of the circle
     * @param segmentCount the number of segments to use for the circle
     * @param colors       the colors to use for the circle
     */
    public void circle(float xCenter, float yCenter, float xRadius, float yRadius, int segmentCount, Color... colors) {
        int colorCount = colors.length;

        if (colorCount < 1) {
            return;
        }

        for (int i = 0; i <= segmentCount; i++) {
            float theta = 2.0f * PI * i / segmentCount;
            float x = xCenter + xRadius * cos(theta);
            float y = yCenter + yRadius * sin(theta);

            Color color;
            if (colorCount > 1) {
                float colorIndex = (float) i / segmentCount * (colorCount - 1);
                int startColorIndex = (int) colorIndex;
                int endColorIndex = min(startColorIndex + 1, colorCount - 1);
                float factor = colorIndex - startColorIndex;

                Color startColor = colors[startColorIndex];
                Color endColor = colors[endColorIndex];

                float r = startColor.getRed() / 255.0f + factor * (endColor.getRed() / 255.0f - startColor.getRed() / 255.0f);
                float g = startColor.getGreen() / 255.0f + factor * (endColor.getGreen() / 255.0f - startColor.getGreen() / 255.0f);
                float b = startColor.getBlue() / 255.0f + factor * (endColor.getBlue() / 255.0f - startColor.getBlue() / 255.0f);
                float a = startColor.getAlpha() / 255.0f + factor * (endColor.getAlpha() / 255.0f - startColor.getAlpha() / 255.0f);

                color = new Color(r, g, b, a);
            } else {
                Color color2 = colors[0];
                color = new Color(color2.getRed() / 255.0f, color2.getGreen() / 255.0f, color2.getBlue() / 255.0f, color2.getAlpha() / 255.0f);
            }

            put().vertex(new Vector2f(x, y)).color(color).end();
        }

        setDrawType(DrawType.POLYGON);
        rendering();
    }

    /**
     * Draws a circle outline with the specified parameters.
     *
     * @param xCenter      the x-coordinate of the circle center
     * @param yCenter      the y-coordinate of the circle center
     * @param radius       the radius of the circle
     * @param segmentCount the number of segments to use for the circle
     * @param lineWidth    the width of the outline
     * @param colors       the colors to use for the circle
     */
    public void circleOutLine(double xCenter, double yCenter, double radius, int segmentCount, double lineWidth, Color... colors) {
        circleOutLine((float) xCenter, (float) yCenter, (float) radius, (float) radius, segmentCount, (float) lineWidth, colors);
    }

    /**
     * Draws a circle outline with the specified parameters.
     *
     * @param xCenter      the x-coordinate of the circle center
     * @param yCenter      the y-coordinate of the circle center
     * @param radius       the radius of the circle
     * @param segmentCount the number of segments to use for the circle
     * @param lineWidth    the width of the outline
     * @param colors       the colors to use for the circle
     */
    public void circleOutLine(float xCenter, float yCenter, float radius, int segmentCount, float lineWidth, Color... colors) {
        circleOutLine(xCenter, yCenter, radius, radius, segmentCount, lineWidth, colors);
    }

    /**
     * Draws a circle outline with the specified parameters.
     *
     * @param xCenter      the x-coordinate of the circle center
     * @param yCenter      the y-coordinate of the circle center
     * @param xRadius      the x-radius of the circle
     * @param yRadius      the y-radius of the circle
     * @param segmentCount the number of segments to use for the circle
     * @param lineWidth    the width of the outline
     * @param colors       the colors to use for the circle
     */
    public void circleOutLine(double xCenter, double yCenter, double xRadius, double yRadius, int segmentCount, double lineWidth, Color... colors) {
        circleOutLine((float) xCenter, (float) yCenter, (float) xRadius, (float) yRadius, segmentCount, (float) lineWidth, colors);
    }

    /**
     * Draws a circle outline with the specified parameters.
     *
     * @param xCenter      the x-coordinate of the circle center
     * @param yCenter      the y-coordinate of the circle center
     * @param xRadius      the x-radius of the circle
     * @param yRadius      the y-radius of the circle
     * @param segmentCount the number of segments to use for the circle
     * @param lineWidth    the width of the outline
     * @param colors       the colors to use for the circle
     */
    public void circleOutLine(float xCenter, float yCenter, float xRadius, float yRadius, int segmentCount, float lineWidth, Color... colors) {
        int colorCount = colors.length;

        if (colorCount < 1) {
            return;
        }

        for (int i = 0; i <= segmentCount; i++) {
            float theta = 2.0f * PI * i / segmentCount;
            float x = xCenter + xRadius * cos(theta);
            float y = yCenter + yRadius * sin(theta);

            Color color;
            if (colorCount > 1) {
                float colorIndex = (float) i / segmentCount * (colorCount - 1);
                int startColorIndex = (int) colorIndex;
                int endColorIndex = min(startColorIndex + 1, colorCount - 1);
                float factor = colorIndex - startColorIndex;

                Color startColor = colors[startColorIndex];
                Color endColor = colors[endColorIndex];

                float r = startColor.getRed() / 255.0f + factor * (endColor.getRed() / 255.0f - startColor.getRed() / 255.0f);
                float g = startColor.getGreen() / 255.0f + factor * (endColor.getGreen() / 255.0f - startColor.getGreen() / 255.0f);
                float b = startColor.getBlue() / 255.0f + factor * (endColor.getBlue() / 255.0f - startColor.getBlue() / 255.0f);
                float a = startColor.getAlpha() / 255.0f + factor * (endColor.getAlpha() / 255.0f - startColor.getAlpha() / 255.0f);

                color = new Color(r, g, b, a);
            } else {
                Color color2 = colors[0];
                color = new Color(color2.getRed() / 255.0f, color2.getGreen() / 255.0f, color2.getBlue() / 255.0f, color2.getAlpha() / 255.0f);
            }

            put().vertex(new Vector2f(x, y)).color(color).end();
        }

        setDrawType(DrawType.LINE_LOOP).setLineWidth(lineWidth);
        rendering();
    }
}