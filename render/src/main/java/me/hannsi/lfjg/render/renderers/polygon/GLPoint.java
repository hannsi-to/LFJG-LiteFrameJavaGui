package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;

/**
 * Class representing a point renderer in OpenGL.
 */
public class GLPoint extends GLPolygon<GLPoint> {
    private final Builder builder;

    GLPoint(String name, Builder builder) {
        super(name);
        this.builder = builder;
    }

    public static VertexDataStep createGLPoint(String name) {
        return new Builder(name);
    }

    public GLPoint update() {
        put().vertex(new Vector2f(builder.x, builder.y)).color(builder.color).end();

        setDrawType(DrawType.POINTS).setPointSize(builder.pointSize);
        rendering();

        return this;
    }

    public interface VertexDataStep {
        PointSizeStep x_y_color(float x, float y, Color color);
    }

    public interface PointSizeStep {
        GLPoint pointSize(float pointSize);
    }

    private static class Builder implements VertexDataStep, PointSizeStep {
        private final String name;
        private float x;
        private float y;
        private Color color;
        private float pointSize;

        private GLPoint glPoint;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public PointSizeStep x_y_color(float x, float y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;

            return this;
        }

        @Override
        public GLPoint pointSize(float pointSize) {
            this.pointSize = pointSize;
            return build();
        }

        private GLPoint build() {
            if (glPoint == null) {
                return glPoint = new GLPoint(name, this);
            } else {
                return glPoint.update();
            }
        }
    }
}