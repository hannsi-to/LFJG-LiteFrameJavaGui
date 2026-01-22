package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.render.renderers.PaintType;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Class representing a rounded rectangle renderer in OpenGL.
 */
public class GLRoundedRect extends GLPolygon<GLRoundedRect> {
    public static final int DEFAULT_SEGMENT = 16;

    private final Builder builder;

    GLRoundedRect(String name, Builder builder) {
        super(name);
        this.builder = builder;
    }

    public static VertexData1Step createRoundedRect(String name) {
        return new Builder(name);
    }

    public GLRoundedRect update() {
        float minX = MathHelper.min(MathHelper.min(builder.x1, builder.x2), MathHelper.min(builder.x3, builder.x4));
        float maxX = MathHelper.max(MathHelper.max(builder.x1, builder.x2), MathHelper.max(builder.x3, builder.x4));
        float minY = MathHelper.min(MathHelper.min(builder.y1, builder.y2), MathHelper.min(builder.y3, builder.y4));
        float maxY = MathHelper.max(MathHelper.max(builder.y1, builder.y2), MathHelper.max(builder.y3, builder.y4));

        List<float[]> perimeter = new ArrayList<>();

        BiConsumer<float[], Integer> addArc = (params, dummy) -> {
            boolean toggle = params[0] == 1.0f;
            float cx = params[1];
            float cy = params[2];
            float radius = params[3];
            float startDeg = params[4];
            float endDeg = params[5];
            float segmentDeg = params[6];
            float cornerX = params[7];
            float cornerY = params[8];

            if (!toggle || radius <= 0f) {
                perimeter.add(new float[]{cornerX, cornerY});
                return;
            }

            float step = segmentDeg;
            if (step <= 0f) {
                step = 1f;
            }
            for (float deg = startDeg; deg <= endDeg + 0.0001f; deg += step) {
                double rad = MathHelper.toRadians(deg);
                float px = (float) (cx + MathHelper.cos(rad) * radius);
                float py = (float) (cy + MathHelper.sin(rad) * radius);

                perimeter.add(new float[]{px, py});
            }
        };

        addArc.accept(new float[]{
                builder.toggleRadius1 ? 1f : 0f,
                builder.x1 + builder.radius1, builder.y1 + builder.radius1, builder.radius1,
                180f, 270f, builder.segment1,
                builder.x1, builder.y1
        }, 0);

        addArc.accept(new float[]{
                builder.toggleRadius2 ? 1f : 0f,
                builder.x2 - builder.radius2, builder.y2 + builder.radius2, builder.radius2,
                270f, 360f, builder.segment2,
                builder.x2, builder.y2
        }, 0);

        addArc.accept(new float[]{
                builder.toggleRadius3 ? 1f : 0f,
                builder.x3 - builder.radius3, builder.y3 - builder.radius3, builder.radius3,
                0f, 90f, builder.segment3,
                builder.x3, builder.y3
        }, 0);

        addArc.accept(new float[]{
                builder.toggleRadius4 ? 1f : 0f,
                builder.x4 + builder.radius4, builder.y4 - builder.radius4, builder.radius4,
                90f, 180f, builder.segment4,
                builder.x4, builder.y4
        }, 0);

        switch (builder.paintType) {
            case FILL:
                drawType(DrawType.TRIANGLE_FAN);

                if (!perimeter.isEmpty()) {
                    perimeter.add(perimeter.get(0));
                }

                if (perimeter.size() >= 3) {
                    float[] first = perimeter.get(0);

                    for (int i = 1; i < perimeter.size() - 1; i++) {
                        float[] p1 = perimeter.get(i);
                        float[] p2 = perimeter.get(i + 1);

                        put().position(new Vector2f(first[0], first[1])).color(getCornerBlend(first[0], first[1], minX, maxX, minY, maxY)).end();
                        put().position(new Vector2f(p1[0], p1[1])).color(getCornerBlend(p1[0], p1[1], minX, maxX, minY, maxY)).end();
                        put().position(new Vector2f(p2[0], p2[1])).color(getCornerBlend(p2[0], p2[1], minX, maxX, minY, maxY)).end();
                    }
                }
                break;
            case OUT_LINE:
                drawType(DrawType.LINE_LOOP).lineWidth(builder.lineWidth);

                for (float[] p : perimeter) {
                    float px = p[0], py = p[1];
                    Color useColor = getCornerBlend(px, py, minX, maxX, minY, maxY);

                    put().position(new Vector2f(px, py)).color(useColor).end();
                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + builder.paintType);
        }

        rendering();

        return this;
    }

    private Color getCornerBlend(float px, float py, float minX, float maxX, float minY, float maxY) {
        float tx = (px - minX) / (maxX - minX);
        float ty = (py - minY) / (maxY - minY);

        Color top = MathHelper.lerpColor(builder.color1, builder.color2, tx);
        Color bottom = MathHelper.lerpColor(builder.color4, builder.color3, tx);

        return MathHelper.lerpColor(top, bottom, ty);
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

        Radius1Step x3_y3_color3__2p(float x3, float y3, Color color3);

        Radius1Step width3_height3_color3_2p(float width3, float height3, Color color3);
    }

    public interface VertexData4Step {
        Radius1Step x4_y4_color4(float x4, float y4, Color color4);

        Radius1Step width4_height4_color4(float width4, float height4, Color color4);
    }

    public interface Radius1Step {
        Radius2Step toggleRadius1_radius1(boolean toggleRadius1, float radius1);

        Radius3Step toggleRadius1_radius1_2p(boolean toggleRadius1, float radius1);

        Radius2Step toggleRadius1_radius1_segment1(boolean toggleRadius1, float radius1, float segment1);

        Radius3Step toggleRadius1_radius1_segment1_2p(boolean toggleRadius1, float radius1, float segment1);
    }

    public interface Radius2Step {
        Radius3Step toggleRadius2_radius2(boolean toggleRadius2, float radius2);

        Radius3Step toggleRadius2_radius2_segment2(boolean toggleRadius2, float radius2, float segment2);
    }

    public interface Radius3Step {
        Radius4Step toggleRadius3_radius3(boolean toggleRadius3, float radius3);

        PaintTypeStep toggleRadius3_radius3_2p(boolean toggleRadius3, float radius3);

        Radius4Step toggleRadius3_radius3_segment1(boolean toggleRadius3, float radius3, float segment3);

        PaintTypeStep toggleRadius3_radius3_segment3_2p(boolean toggleRadius3, float radius3, float segment3);
    }

    public interface Radius4Step {
        PaintTypeStep toggleRadius4_radius4(boolean toggleRadius4, float radius4);

        PaintTypeStep toggleRadius4_radius4_segment4(boolean toggleRadius4, float radius4, float segment4);
    }

    public interface PaintTypeStep {
        GLRoundedRect fill();

        LineWidthStep outLine();
    }

    public interface LineWidthStep {
        GLRoundedRect lineWidth(float lineWidth);
    }

    public static class Builder implements VertexData1Step, VertexData2Step, VertexData3Step, VertexData4Step, Radius1Step, Radius2Step, Radius3Step, Radius4Step, PaintTypeStep, LineWidthStep {
        protected final String name;
        protected float x1;
        protected float y1;
        protected Color color1;
        protected boolean toggleRadius1;
        protected float radius1;
        protected float segment1;
        protected float x2;
        protected float y2;
        protected Color color2;
        protected boolean toggleRadius2;
        protected float radius2;
        protected float segment2;
        protected float x3;
        protected float y3;
        protected Color color3;
        protected boolean toggleRadius3;
        protected float radius3;
        protected float segment3;
        protected float x4;
        protected float y4;
        protected Color color4;
        protected boolean toggleRadius4;
        protected float radius4;
        protected float segment4;
        protected PaintType paintType;
        protected float lineWidth;

        private GLRoundedRect glRoundedRect;

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
        public Radius1Step x3_y3_color3__2p(float x3, float y3, Color color3) {
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
        public Radius1Step width3_height3_color3_2p(float width3, float height3, Color color3) {
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
        public Radius1Step x4_y4_color4(float x4, float y4, Color color4) {
            this.x4 = x4;
            this.y4 = y4;
            this.color4 = color4;

            return this;
        }

        @Override
        public Radius1Step width4_height4_color4(float width4, float height4, Color color4) {
            this.x4 = x1 + width4;
            this.y4 = y1 + height4;
            this.color4 = color4;

            return this;
        }

        @Override
        public Radius2Step toggleRadius1_radius1(boolean toggleRadius1, float radius1) {
            this.toggleRadius1 = toggleRadius1;
            this.radius1 = radius1;
            this.segment1 = DEFAULT_SEGMENT;

            return this;
        }

        @Override
        public Radius3Step toggleRadius1_radius1_2p(boolean toggleRadius1, float radius1) {
            this.toggleRadius1 = toggleRadius1;
            this.radius1 = radius1;
            this.segment1 = DEFAULT_SEGMENT;

            return this;
        }

        @Override
        public Radius2Step toggleRadius1_radius1_segment1(boolean toggleRadius1, float radius1, float segment1) {
            this.toggleRadius1 = toggleRadius1;
            this.radius1 = radius1;
            this.segment1 = segment1;

            return this;
        }

        @Override
        public Radius3Step toggleRadius1_radius1_segment1_2p(boolean toggleRadius1, float radius1, float segment1) {
            this.toggleRadius1 = toggleRadius1;
            this.radius1 = radius1;
            this.segment1 = segment1;

            return this;
        }

        @Override
        public Radius3Step toggleRadius2_radius2(boolean toggleRadius2, float radius2) {
            this.toggleRadius2 = toggleRadius2;
            this.radius2 = radius2;
            this.segment2 = DEFAULT_SEGMENT;

            return this;
        }

        @Override
        public Radius3Step toggleRadius2_radius2_segment2(boolean toggleRadius2, float radius2, float segment2) {
            this.toggleRadius2 = toggleRadius2;
            this.radius2 = radius2;
            this.segment2 = segment2;

            return this;
        }

        @Override
        public Radius4Step toggleRadius3_radius3(boolean toggleRadius3, float radius3) {
            this.toggleRadius3 = toggleRadius3;
            this.radius3 = radius3;
            this.segment3 = DEFAULT_SEGMENT;

            return this;
        }

        @Override
        public PaintTypeStep toggleRadius3_radius3_2p(boolean toggleRadius3, float radius3) {
            this.toggleRadius2 = toggleRadius1;
            this.radius2 = radius1;
            this.segment2 = segment1;

            this.toggleRadius3 = toggleRadius3;
            this.radius3 = radius3;
            this.segment3 = DEFAULT_SEGMENT;

            this.toggleRadius4 = toggleRadius3;
            this.radius4 = radius3;
            this.segment4 = segment3;

            return this;
        }

        @Override
        public Radius4Step toggleRadius3_radius3_segment1(boolean toggleRadius3, float radius3, float segment3) {
            this.toggleRadius3 = toggleRadius3;
            this.radius3 = radius3;
            this.segment3 = segment3;

            return this;
        }

        @Override
        public PaintTypeStep toggleRadius3_radius3_segment3_2p(boolean toggleRadius3, float radius3, float segment3) {
            this.toggleRadius2 = toggleRadius1;
            this.radius2 = radius1;
            this.segment2 = segment1;

            this.toggleRadius3 = toggleRadius3;
            this.radius3 = radius3;
            this.segment3 = segment3;

            this.toggleRadius4 = toggleRadius3;
            this.radius4 = radius3;
            this.segment4 = segment3;

            return this;
        }

        @Override
        public PaintTypeStep toggleRadius4_radius4(boolean toggleRadius4, float radius4) {
            this.toggleRadius4 = toggleRadius4;
            this.radius4 = radius4;
            this.segment4 = DEFAULT_SEGMENT;

            return this;
        }

        @Override
        public PaintTypeStep toggleRadius4_radius4_segment4(boolean toggleRadius4, float radius4, float segment4) {
            this.toggleRadius4 = toggleRadius4;
            this.radius4 = radius4;
            this.segment4 = segment4;

            return this;
        }

        @Override
        public GLRoundedRect fill() {
            this.paintType = PaintType.FILL;
            this.lineWidth = -1;

            return build();
        }

        @Override
        public LineWidthStep outLine() {
            this.paintType = PaintType.OUT_LINE;

            return this;
        }

        @Override
        public GLRoundedRect lineWidth(float lineWidth) {
            this.lineWidth = lineWidth;

            return build();
        }

        private GLRoundedRect build() {
            if (glRoundedRect == null) {
                return glRoundedRect = new GLRoundedRect(name, this);
            } else {
                return glRoundedRect.update();
            }
        }
    }
}