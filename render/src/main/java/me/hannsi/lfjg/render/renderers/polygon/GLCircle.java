package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.render.renderers.PaintType;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a circle renderer in OpenGL.
 */
public class GLCircle extends GLPolygon<GLCircle> {
    public static final int DEFAULT_SEGMENT = 16;

    private final Builder builder;

    GLCircle(String name, Builder builder) {
        super(name);
        this.builder = builder;
    }

    public static CenterXDataStep createGLCirce(String name) {
        return new Builder(name);
    }

    public GLCircle update() {
        List<float[]> vertices = new ArrayList<>();

        for (int i = 0; i <= builder.segment; i++) {
            double angle = 2.0 * Math.PI * i / builder.segment;
            float px = (float) (builder.cx + MathHelper.cos(angle) * builder.xRadius);
            float py = (float) (builder.cy + MathHelper.sin(angle) * builder.yRadius);

            vertices.add(new float[]{px, py});
        }

        switch (builder.paintType) {
            case FILL:
                drawType(DrawType.TRIANGLE_FAN);

                Color centerColor = getCenterColor();
                put().position(new Vector2f(builder.cx, builder.cy)).color(centerColor).end();

                for (float[] v : vertices) {
                    Color useColor = getCornerBlend(v[0], v[1]);
                    put().position(new Vector2f(v[0], v[1])).color(useColor).end();
                }

                break;
            case OUT_LINE:
                drawType(DrawType.LINE_LOOP).lineWidth(builder.lineWidth);

                for (float[] v : vertices) {
                    Color useColor = getCornerBlend(v[0], v[1]);
                    put().position(new Vector2f(v[0], v[1])).color(useColor).end();
                }

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + builder.paintType);
        }

        rendering();

        return this;
    }

    private Color getCenterColor() {
        int blendR = 0;
        int blendG = 0;
        int blendB = 0;
        int blendA = 0;
        for (Color c : builder.colors) {
            blendR += c.getRed();
            blendG += c.getGreen();
            blendB += c.getBlue();
            blendA += c.getAlpha();
        }

        return new Color(
                blendR / builder.colors.length,
                blendG / builder.colors.length,
                blendB / builder.colors.length,
                blendA / builder.colors.length
        );
    }

    private Color getCornerBlend(float px, float py) {
        if (builder.colors.length == 1) {
            return builder.colors[0];
        } else if (builder.colors.length == 2) {
            float ty = (py - (builder.cy - builder.yRadius)) / (2 * builder.yRadius);

            return MathHelper.lerpColor(builder.colors[0], builder.colors[1], ty);
        } else if (builder.colors.length == 3) {
            float tx = (px - (builder.cx - builder.xRadius)) / (2 * builder.xRadius);
            float ty = (py - (builder.cy - builder.yRadius)) / (2 * builder.yRadius);

            Color top = MathHelper.lerpColor(builder.colors[0], builder.colors[1], tx);
            Color bottom = builder.colors[2];

            return MathHelper.lerpColor(top, bottom, ty);
        } else if (builder.colors.length >= 4) {
            float tx = (px - (builder.cx - builder.xRadius)) / (2 * builder.xRadius);
            float ty = (py - (builder.cy - builder.yRadius)) / (2 * builder.yRadius);

            Color top = MathHelper.lerpColor(builder.colors[0], builder.colors[1], tx);
            Color bottom = MathHelper.lerpColor(builder.colors[3], builder.colors[2], tx);

            return MathHelper.lerpColor(top, bottom, ty);
        } else {
            throw new IllegalArgumentException("The colors array is invalid: " + builder.colors.length);
        }
    }

    public interface CenterXDataStep {
        CenterYDataStep cx_xRadius(float cx, float xRadius);
    }

    public interface CenterYDataStep {
        SegmentStep cy_yRadius(float cy, float yRadius);
    }

    public interface SegmentStep {
        ColorsStep segment();

        ColorsStep segment(int segment);
    }

    public interface ColorsStep {
        PaintTypeStep colors(Color... colors);
    }

    public interface PaintTypeStep {
        GLCircle fill();

        LineWidthStep outLine();
    }

    public interface LineWidthStep {
        GLCircle lineWidth(float lineWidth);
    }

    public static class Builder implements CenterXDataStep, CenterYDataStep, SegmentStep, ColorsStep, PaintTypeStep, LineWidthStep {
        protected final String name;
        protected float cx;
        protected float xRadius;
        protected float cy;
        protected float yRadius;
        protected int segment;
        protected Color[] colors;
        protected PaintType paintType;
        protected float lineWidth;

        private GLCircle glCircle;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public CenterYDataStep cx_xRadius(float cx, float xRadius) {
            this.cx = cx;
            this.xRadius = xRadius;

            return this;
        }

        @Override
        public SegmentStep cy_yRadius(float cy, float yRadius) {
            this.cy = cy;
            this.yRadius = yRadius;

            return this;
        }

        @Override
        public ColorsStep segment() {
            segment = DEFAULT_SEGMENT;

            return this;
        }

        @Override
        public ColorsStep segment(int segment) {
            this.segment = segment;

            return this;
        }

        @Override
        public PaintTypeStep colors(Color... colors) {
            this.colors = colors;

            return this;
        }

        @Override
        public GLCircle fill() {
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
        public GLCircle lineWidth(float lineWidth) {
            this.lineWidth = lineWidth;

            return build();
        }

        private GLCircle build() {
            if (glCircle == null) {
                return glCircle = new GLCircle(name, this);
            } else {
                return glCircle.update();
            }
        }

    }
}