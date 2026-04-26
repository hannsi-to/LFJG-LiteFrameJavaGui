package me.hannsi.lfjg.testRender.renderers.polygon;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.animation.Easing;
import me.hannsi.lfjg.render.render.Vertex;
import me.hannsi.lfjg.testRender.renderers.GLObject;
import me.hannsi.lfjg.testRender.renderers.PaintType;
import me.hannsi.lfjg.testRender.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.utils.math.MathHelper.*;
import static me.hannsi.lfjg.testRender.RenderSystemSetting.GL_CIRCLE_DEFAULT_SEGMENT_COUNT;

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
        CircleDataStep<T> vertexCenter(Vertex centerVertex);
    }

    public interface CircleDataStep<T> {
        CircleOptionStep<T> circleData(float xRadius, float yRadius);
    }

    public interface CircleOptionStep<T> {
        CircleOptionStep<T> innerRadius(float xInnerRadius, float yInnerRadius);

        CircleOptionStep<T> angle(float startAngle, float endAngle);

        CircleOptionStep<T> segmentCount(int segmentCount);

        ColorData1Step<T> end();
    }

    public interface ColorData1Step<T> {
        ColorDataStep<T> addColor1(Color color);
    }

    public interface ColorDataStep<T> {
        ColorDataStep<T> addColor(Color color);

        PaintTypeStep<T> easingColor(Easing easing);

        PaintTypeStep<T> endColor();
    }

    public static class Builder extends AbstractGLObjectBuilder<GLCircle> implements VertexCenterDataStep<GLCircle>, CircleDataStep<GLCircle>, CircleOptionStep<GLCircle>, ColorData1Step<GLCircle>, ColorDataStep<GLCircle> {
        private final String name;
        private final List<Color> colors = new ArrayList<>();
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
        }

        @Override
        public CircleDataStep<GLCircle> vertexCenter(Vertex centerVertex) {
            this.centerVertex = centerVertex;
            return this;
        }

        @Override
        public CircleOptionStep<GLCircle> circleData(float xRadius, float yRadius) {
            this.xRadius = xRadius;
            this.yRadius = yRadius;
            return this;
        }

        @Override
        public CircleOptionStep<GLCircle> innerRadius(float xInnerRadius, float yInnerRadius) {
            this.xInnerRadius = xInnerRadius;
            this.yInnerRadius = yInnerRadius;
            return this;
        }

        @Override
        public CircleOptionStep<GLCircle> angle(float startAngle, float endAngle) {
            this.startAngle = startAngle;
            this.endAngle = endAngle;
            return this;
        }

        @Override
        public CircleOptionStep<GLCircle> segmentCount(int segmentCount) {
            this.segmentCount = segmentCount;
            return this;
        }

        @Override
        public ColorData1Step<GLCircle> end() {
            return this;
        }

        @Override
        public ColorDataStep<GLCircle> addColor1(Color color) {
            this.colors.add(color);
            return this;
        }

        @Override
        public ColorDataStep<GLCircle> addColor(Color color) {
            this.colors.add(color);
            return this;
        }

        @Override
        public PaintTypeStep<GLCircle> easingColor(Easing easing) {
            this.easing = easing;
            return this;
        }

        @Override
        public PaintTypeStep<GLCircle> endColor() {
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