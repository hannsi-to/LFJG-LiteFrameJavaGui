package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.animation.Easing;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.renderers.PaintType;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.utils.math.MathHelper.*;
import static me.hannsi.lfjg.render.RenderSystemSetting.GL_CIRCLE_DEFAULT_SEGMENT_COUNT;

public class GLCircle extends GLObject<GLCircle> {
    private final Builder builder;

    GLCircle(String name, Builder builder) {
        super(name);
        this.builder = builder;
    }

    public static VertexCenterDataStep<GLCircle> createGLCircle(String name) {
        return new Builder(name);
    }

    @Override
    public GLCircle update() {
        float angle = abs(builder.endAngle - builder.startAngle);

        DrawType drawType;
        switch (builder.paintType) {
            case FILL -> {
                if (builder.xInnerRadius > 0 || builder.yInnerRadius > 0) {
                    drawType = DrawType.TRIANGLE_STRIP;
                } else {
                    put(builder.centerVertex).end();
                    drawType = DrawType.TRIANGLE_FAN;
                }
            }
            case STROKE ->
                    drawType = DrawType.LINE_STRIP;
            default ->
                    throw new IllegalStateException("Unexpected value: " + builder.paintType);
        }

        float step = angle / (builder.segmentCount * builder.colors.size());
        int colorCount = builder.colors.size();

        for (float j = builder.startAngle; j <= builder.endAngle; j += step) {
            float t = (j - builder.startAngle) / angle;

            float colorPos = t * (colorCount - 1);
            int index = min((int) colorPos, colorCount - 2);
            float localT = colorPos - index;
            float easedT = builder.easing.ease(localT);

            Color c1 = builder.colors.get(index);
            Color c2 = builder.colors.get(index + 1);
            Color blended = lerpColor(c1, c2, easedT);

            float x1 = sin(toRadians(j)) * builder.xRadius;
            float y1 = cos(toRadians(j)) * builder.yRadius;
            put(builder.centerVertex.copy().moveXYZ(x1, y1, 0).replaceColor(blended)).end();

            if (builder.paintType == PaintType.FILL && (builder.xInnerRadius > 0 || builder.yInnerRadius > 0)) {
                float x2 = sin(toRadians(j)) * builder.xInnerRadius;
                float y2 = cos(toRadians(j)) * builder.yInnerRadius;
                put(builder.centerVertex.copy().moveXYZ(x2, y2, 0).replaceColor(blended)).end();
            }
        }

        drawType(drawType);
        return super.update();
    }

    public interface VertexCenterDataStep<T> {
        CircleDataStep<T> vertexCenter(Vertex vertex);
    }

    public interface CircleDataStep<T> {
        ColorsStep<T> circleData(float xRadius, float yRadius);

        ColorsStep<T> circleData_innerRadius(float xRadius, float yRadius, float xInnerRadius, float yInnerRadius);

        ColorsStep<T> circleData(float xRadius, float yRadius, int segmentCount);

        ColorsStep<T> circleData_innerRadius(float xRadius, float yRadius, float xInnerRadius, float yInnerRadius, int segmentCount);

        ColorsStep<T> circleData(float xRadius, float yRadius, float startAngle, float endAngle);

        ColorsStep<T> circleData_innerRadius(float xRadius, float yRadius, float xInnerRadius, float yInnerRadius, float startAngle, float endAngle);

        ColorsStep<T> circleData(float xRadius, float yRadius, float startAngle, float endAngle, int segmentCount);

        ColorsStep<T> circleData_innerRadius(float xRadius, float yRadius, float xInnerRadius, float yInnerRadius, float startAngle, float endAngle, int segmentCount);
    }

    public interface ColorsStep<T> {
        ColorsStep<T> color(Color color);

        EasingColorStep<T> color_end_easingColor(Color color);

        PaintTypeStep<T> color_end(Color color);
    }

    public interface EasingColorStep<T> {
        PaintTypeStep<T> easingColor(Easing easing);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLCircle> implements VertexCenterDataStep<GLCircle>, CircleDataStep<GLCircle>, ColorsStep<GLCircle>, EasingColorStep<GLCircle> {
        private final String name;
        private final List<Color> colors;
        private Vertex centerVertex;
        private float xRadius;
        private float yRadius;
        private float xInnerRadius = 0;
        private float yInnerRadius = 0;
        private float startAngle = 0;
        private float endAngle = 360;
        private int segmentCount = GL_CIRCLE_DEFAULT_SEGMENT_COUNT;
        private Easing easing = Easing.easeLinear;

        private GLCircle glCircle;

        public Builder(String name) {
            this.name = name;

            this.colors = new ArrayList<>();
        }

        @Override
        public ColorsStep<GLCircle> circleData(float xRadius, float yRadius) {
            this.xRadius = xRadius;
            this.yRadius = yRadius;

            return this;
        }

        @Override
        public ColorsStep<GLCircle> circleData_innerRadius(float xRadius, float yRadius, float xInnerRadius, float yInnerRadius) {
            this.xRadius = xRadius;
            this.yRadius = yRadius;
            this.xInnerRadius = xInnerRadius;
            this.yInnerRadius = yInnerRadius;

            return this;
        }

        @Override
        public ColorsStep<GLCircle> circleData(float xRadius, float yRadius, int segmentCount) {
            this.xRadius = xRadius;
            this.yRadius = yRadius;
            this.segmentCount = segmentCount;

            return this;
        }

        @Override
        public ColorsStep<GLCircle> circleData_innerRadius(float xRadius, float yRadius, float xInnerRadius, float yInnerRadius, int segmentCount) {
            this.xRadius = xRadius;
            this.yRadius = yRadius;
            this.xInnerRadius = xInnerRadius;
            this.yInnerRadius = yInnerRadius;
            this.segmentCount = segmentCount;

            return this;
        }

        @Override
        public ColorsStep<GLCircle> circleData(float xRadius, float yRadius, float startAngle, float endAngle) {
            this.xRadius = xRadius;
            this.yRadius = yRadius;
            this.startAngle = startAngle;
            this.endAngle = endAngle;

            return this;
        }

        @Override
        public ColorsStep<GLCircle> circleData_innerRadius(float xRadius, float yRadius, float xInnerRadius, float yInnerRadius, float startAngle, float endAngle) {
            this.xRadius = xRadius;
            this.yRadius = yRadius;
            this.xInnerRadius = xInnerRadius;
            this.yInnerRadius = yInnerRadius;
            this.startAngle = startAngle;
            this.endAngle = endAngle;

            return this;
        }

        @Override
        public ColorsStep<GLCircle> circleData(float xRadius, float yRadius, float startAngle, float endAngle, int segmentCount) {
            this.xRadius = xRadius;
            this.yRadius = yRadius;
            this.startAngle = startAngle;
            this.endAngle = endAngle;
            this.segmentCount = segmentCount;

            return this;
        }

        @Override
        public ColorsStep<GLCircle> circleData_innerRadius(float xRadius, float yRadius, float xInnerRadius, float yInnerRadius, float startAngle, float endAngle, int segmentCount) {
            this.xRadius = xRadius;
            this.yRadius = yRadius;
            this.xInnerRadius = xInnerRadius;
            this.yInnerRadius = yInnerRadius;
            this.startAngle = startAngle;
            this.endAngle = endAngle;
            this.segmentCount = segmentCount;

            return this;
        }

        @Override
        public ColorsStep<GLCircle> color(Color color) {
            this.colors.add(color);

            return this;
        }

        @Override
        public PaintTypeStep<GLCircle> color_end(Color color) {
            this.colors.add(color);

            return this;
        }

        @Override
        public EasingColorStep<GLCircle> color_end_easingColor(Color color) {
            this.colors.add(color);

            return this;
        }

        @Override
        public PaintTypeStep<GLCircle> easingColor(Easing easing) {
            this.easing = easing;

            return this;
        }

        @Override
        public CircleDataStep<GLCircle> vertexCenter(Vertex vertex) {
            this.centerVertex = vertex;

            return this;
        }

        @Override
        protected GLCircle createOrGet() {
            if (glCircle == null) {
                return glCircle = new GLCircle(name, this);
            } else {
                return glCircle.update();
            }
        }
    }
}