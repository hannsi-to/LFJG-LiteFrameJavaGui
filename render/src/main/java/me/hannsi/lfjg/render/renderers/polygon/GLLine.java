package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;

/**
 * Class representing a line renderer in OpenGL.
 */
public class GLLine extends GLPolygon<GLLine> {
    private final Builder builder;

    public GLLine(String name, Builder builder) {
        super(name);

        this.builder = builder;
    }

    public static VertexData1Step createGLLine(String name) {
        return new Builder(name);
    }

    public GLLine update() {
        put().position(new Vector2f(builder.x1, builder.y1)).color(builder.color1).end();
        put().position(new Vector2f(builder.x2, builder.y2)).color(builder.color2).end();

        setDrawType(DrawType.LINES).setLineWidth(builder.lineWidth);
        rendering();

        return this;
    }

    public interface VertexData1Step {
        VertexData2Step x1_y1_color1(float x1, float y1, Color color1);
    }

    public interface VertexData2Step {
        LineWidthStep x2_y2_color2(float x2, float y2, Color color2);

        LineWidthStep width_height_color2(float width, float height, Color color2);
    }

    public interface LineWidthStep {
        GLLine lineWidth(float lineWidth);
    }

    public static class Builder implements VertexData1Step, VertexData2Step, LineWidthStep {
        private final String name;
        private float x1;
        private float y1;
        private Color color1;
        private float x2;
        private float y2;
        private Color color2;
        private float lineWidth;

        private GLLine glLine;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public VertexData2Step x1_y1_color1(float x1, float y1, Color color1) {
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
            this.lineWidth = lineWidth;

            return build();
        }

        private GLLine build() {
            if (glLine == null) {
                return glLine = new GLLine(name, this);
            } else {
                return glLine.update();
            }
        }
    }
}