package me.hannsi.lfjg.render.openGL.renderers.polygon;

import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Vector2f;

/**
 * Class representing a point renderer in OpenGL.
 */
public class GLPoint extends GLPolygon {

    /**
     * Constructs a new GLPoint with the specified name.
     *
     * @param name the name of the point renderer
     */
    public GLPoint(String name) {
        super(name);
    }

    /**
     * Draws a point with the specified parameters.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param pointSize the size of the point
     * @param color the color of the point
     */
    public void point(float x, float y, float pointSize, Color color) {
        put().vertex(new Vector2f(x, y)).color(color).end();

        setDrawType(DrawType.POINTS).setPointSize(pointSize);
        rendering();
    }

    /**
     * Draws a point with the specified parameters.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param pointSize the size of the point
     * @param color the color of the point
     */
    public void point(double x, double y, double pointSize, Color color) {
        point((float) x, (float) y, (float) pointSize, color);
    }
}