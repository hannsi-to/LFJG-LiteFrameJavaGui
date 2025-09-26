package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.renderers.PaintType;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;

public class GLTriangle extends GLPolygon {
    public GLTriangle(String name, float x1, float y1, Color color1, float x2, float y2, Color color2, float x3, float y3, Color color3, PaintType paintType, float lineWidth) {
        super(name);

        put().vertex(new Vector2f(x1, y1)).color(color1).end();
        put().vertex(new Vector2f(x2, y2)).color(color2).end();
        put().vertex(new Vector2f(x3, y3)).color(color3).end();

        switch (paintType) {
            case FILL:
                setDrawType(DrawType.TRIANGLES);
                break;
            case OUT_LINE:
                setDrawType(DrawType.LINE_LOOP).setLineWidth(lineWidth);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + paintType);
        }

        rendering();
    }

    public static VertexData1Step createGLTriangle(String name) {
        return new Builder(name);
    }

    public interface VertexData1Step {
        VertexData2Step x1_y1_color1(float x1, float y1, Color color1);
    }

    public interface VertexData2Step {
        VertexData3Step x2_y2_color2(float x2, float y2, Color color2);

        VertexData3Step width2_height2_color2(float width2, float height2, Color color2);
    }

    public interface VertexData3Step {
        PaintTypeStep x3_y3_color3(float x3, float y3, Color color3);

        PaintTypeStep width3_height3_color3(float width3, float height3, Color color3);
    }

    public interface PaintTypeStep {
        GLTriangle fill();

        LineWidthStep outLine();
    }

    public interface LineWidthStep {
        GLTriangle lineWidth(float lineWidth);
    }

    public static class Builder implements VertexData1Step, VertexData2Step, VertexData3Step, PaintTypeStep, LineWidthStep {
        private final String name;
        private float x1;
        private float y1;
        private Color color1;
        private float x2;
        private float y2;
        private Color color2;
        private float x3;
        private float y3;
        private Color color3;
        private PaintType paintType;
        private float lineWidth;

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
        public VertexData3Step x2_y2_color2(float x2, float y2, Color color2) {
            this.x2 = x2;
            this.y2 = y2;
            this.color2 = color2;

            return this;
        }

        @Override
        public VertexData3Step width2_height2_color2(float width2, float height2, Color color2) {
            this.x2 = x1 + width2;
            this.y2 = y1 + height2;
            this.color2 = color2;

            return this;
        }

        @Override
        public PaintTypeStep x3_y3_color3(float x3, float y3, Color color3) {
            this.x3 = x3;
            this.y3 = y3;
            this.color3 = color3;

            return this;
        }

        @Override
        public PaintTypeStep width3_height3_color3(float width3, float height3, Color color3) {
            this.x3 = x2 + width3;
            this.y3 = y2 + height3;
            this.color3 = color3;

            return this;
        }

        @Override
        public GLTriangle fill() {
            this.paintType = PaintType.FILL;
            this.lineWidth = -1;

            return new GLTriangle(name, x1, y1, color1, x2, y2, color2, x3, y3, color3, paintType, lineWidth);
        }

        @Override
        public LineWidthStep outLine() {
            this.paintType = PaintType.OUT_LINE;

            return this;
        }

        @Override
        public GLTriangle lineWidth(float lineWidth) {
            this.lineWidth = lineWidth;

            return new GLTriangle(name, x1, y1, color1, x2, y2, color2, x3, y3, color3, paintType, lineWidth);
        }
    }
}