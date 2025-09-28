package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.renderers.PaintType;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;

/**
 * Class representing a rectangle renderer in OpenGL.
 */
public class GLRect extends GLPolygon<GLRect> {
    private final Builder builder;

    GLRect(String name, Builder builder) {
        super(name);
        this.builder = builder;
    }

    public static VertexData1Step createGLRect(String name) {
        return new Builder(name);
    }

    public GLRect update() {
        put().vertex(new Vector2f(builder.x1, builder.y1)).color(builder.color1).end();
        put().vertex(new Vector2f(builder.x2, builder.y2)).color(builder.color2).end();
        put().vertex(new Vector2f(builder.x3, builder.y3)).color(builder.color3).end();
        put().vertex(new Vector2f(builder.x4, builder.y4)).color(builder.color4).end();

        switch (builder.paintType) {
            case FILL:
                setDrawType(DrawType.QUADS);
                break;
            case OUT_LINE:
                setDrawType(DrawType.LINE_LOOP).setLineWidth(builder.lineWidth);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + builder.paintType);
        }

        rendering();

        return this;
    }

    public Builder getBuilder() {
        return builder;
    }

    public interface VertexData1Step {
        VertexData2Step x1_y1_color1(float x1, float y1, Color color1);

        VertexData3Step x1_y1_color1_2p(float x1, float y1, Color color1);
    }

    public interface VertexData2Step {
        VertexData3Step x2_y2_color2(float x2, float y2, Color color2);

        VertexData3Step width2_height2_color2(float width2, float height2, Color color2);
    }

    public interface VertexData3Step {
        VertexData4Step x3_y3_color3(float x3, float y3, Color color3);

        VertexData4Step width3_height3_color3(float width3, float height3, Color color3);

        PaintTypeStep x3_y3_color3_2p(float x3, float y3, Color color3);

        PaintTypeStep width3_height3_color3_2p(float width3, float height3, Color color3);
    }

    public interface VertexData4Step {
        PaintTypeStep x4_y4_color4(float x4, float y4, Color color4);

        PaintTypeStep width4_height4_color4(float width4, float height4, Color color4);
    }

    public interface PaintTypeStep {
        GLRect fill();

        LineWidthStep outLine();
    }

    public interface LineWidthStep {
        GLRect lineWidth(float lineWidth);
    }

    public static class Builder implements VertexData1Step, VertexData2Step, VertexData3Step, VertexData4Step, PaintTypeStep, LineWidthStep {
        protected final String name;
        protected float x1;
        protected float y1;
        protected Color color1;
        protected float x2;
        protected float y2;
        protected Color color2;
        protected float x3;
        protected float y3;
        protected Color color3;
        protected float x4;
        protected float y4;
        protected Color color4;
        protected PaintType paintType;
        protected float lineWidth;

        private GLRect glRect;

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
        public VertexData3Step x1_y1_color1_2p(float x1, float y1, Color color1) {
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
        public VertexData4Step x3_y3_color3(float x3, float y3, Color color3) {
            this.x3 = x3;
            this.y3 = y3;
            this.color3 = color3;

            return this;
        }

        @Override
        public VertexData4Step width3_height3_color3(float width3, float height3, Color color3) {
            this.x3 = x1 + width3;
            this.y3 = y1 + height3;
            this.color3 = color3;

            return this;
        }

        @Override
        public PaintTypeStep x3_y3_color3_2p(float x3, float y3, Color color3) {
            this.x2 = x3;
            this.y2 = y1;
            this.x3 = x3;
            this.y3 = y3;
            this.x4 = x1;
            this.y4 = y3;
            this.color2 = color1;
            this.color3 = color3;
            this.color4 = color3;

            return this;
        }

        @Override
        public PaintTypeStep width3_height3_color3_2p(float width3, float height3, Color color3) {
            this.x2 = x1 + width3;
            this.y2 = y1;
            this.x3 = x1 + width3;
            this.y3 = y1 + height3;
            this.x4 = x1;
            this.y4 = y1 + height3;
            this.color2 = color1;
            this.color3 = color3;
            this.color4 = color3;

            return this;
        }

        @Override
        public PaintTypeStep x4_y4_color4(float x4, float y4, Color color4) {
            this.x4 = x4;
            this.y4 = y4;
            this.color4 = color4;

            return this;
        }

        @Override
        public PaintTypeStep width4_height4_color4(float width4, float height4, Color color4) {
            this.x4 = x1 + width4;
            this.y4 = y1 + height4;
            this.color4 = color4;

            return this;
        }

        @Override
        public GLRect fill() {
            this.paintType = PaintType.FILL;
            this.lineWidth = -1;

            return build(name);
        }

        @Override
        public LineWidthStep outLine() {
            this.paintType = PaintType.OUT_LINE;

            return this;
        }

        @Override
        public GLRect lineWidth(float lineWidth) {
            this.lineWidth = lineWidth;

            return build(name);
        }


        private GLRect build(String name) {
            if (glRect == null) {
                return glRect = new GLRect(name, this);
            } else {
                return glRect.update();
            }
        }
    }
}
