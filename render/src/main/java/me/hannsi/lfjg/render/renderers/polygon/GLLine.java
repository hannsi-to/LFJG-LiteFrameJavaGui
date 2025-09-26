package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;

/**
 * Class representing a line renderer in OpenGL.
 */
public class GLLine extends GLPolygon {
    public GLLine(String name, float x1, float y1, Color color1, float x2, float y2, Color color2, float lineWidth) {
        super(name);

        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y2)).color(color2).end();

        setDrawType(DrawType.LINES).setLineWidth(lineWidth);
        rendering();
    }

    public static Vertex1DataStep createGLLine(String name) {
        return new Builder(name);
    }

    public interface Vertex1DataStep {
        Vertex2DataStep x1_y1_color1(float x1, float y1, Color color1);
    }

    public interface Vertex2DataStep {
        LineWidthStep x2_y2_color2(float x2, float y2, Color color2);

        LineWidthStep width_height_color2(float width, float height, Color color2);
    }

    public interface LineWidthStep {
        GLLine lineWidth(float lineWidth);
    }

    private static class Builder implements Vertex1DataStep, Vertex2DataStep, LineWidthStep {
        private final String name;
        private float x1;
        private float y1;
        private Color color1;
        private float x2;
        private float y2;
        private Color color2;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public Vertex2DataStep x1_y1_color1(float x1, float y1, Color color1) {
            this.x1 = x1;
            this.y1 = y1;
            this.color1 = color1;

            return this;
        }

        @Override
        public LineWidthStep x2_y2_color2(float x2, float y2, Color color2) {
            this.x2 = x2;
            this.y2 = y2;
            this.color2 = color2;

            return this;
        }

        @Override
        public LineWidthStep width_height_color2(float width, float height, Color color2) {
            this.x2 = x1 + width;
            this.y2 = y1 + height;
            this.color2 = color2;

            return this;
        }

        @Override
        public GLLine lineWidth(float lineWidth) {
            return new GLLine(name, x1, y1, color1, x2, y2, color2, lineWidth);
        }
    }
}