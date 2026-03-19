package me.hannsi.lfjg.render.renderers.polygon;

import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Corner;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.utils.math.MathHelper.*;

public class GLRoundedRect extends GLObject<GLRoundedRect> {
    private final Builder builder;

    protected GLRoundedRect(String name, Builder builder) {
        super(name);
        this.builder = builder;
    }

    public static VertexData1Step<GLRoundedRect> createRoundedRect(String name) {
        return new Builder(name);
    }

    private static float getAngleDiff(float endAngle, float startAngle, float cross) {
        float angleDiff = endAngle - startAngle;

        while (angleDiff > PI) {
            angleDiff -= PI_TIMES_2;
        }
        while (angleDiff < -PI) {
            angleDiff += PI_TIMES_2;
        }

        if (cross > 0) {
            if (angleDiff > 0) {
                angleDiff -= PI_TIMES_2;
            }
        } else {
            if (angleDiff < 0) {
                angleDiff += PI_TIMES_2;
            }
        }
        return angleDiff;
    }

    @Override
    public GLRoundedRect update() {
        for (int i = 0; i < builder.vertices.size(); i += 4) {
            Vertex vertex1 = builder.vertices.get(i);
            Vertex vertex2 = builder.vertices.get(i + 1);
            Vertex vertex3 = builder.vertices.get(i + 2);
            Vertex vertex4 = builder.vertices.get(i + 3);

            Corner c1 = builder.corners.get(i);
            Corner c2 = builder.corners.get(i + 1);
            Corner c3 = builder.corners.get(i + 2);
            Corner c4 = builder.corners.get(i + 3);

            Vertex[] vertices = {vertex1, vertex2, vertex3, vertex4};
            Corner[] corners = {c1, c2, c3, c4};

            for (int j = 0; j < 4; j++) {
                Vertex current = vertices[j];
                Vertex prev = vertices[(j + 3) % 4];
                Vertex next = vertices[(j + 1) % 4];
                Corner corner = corners[j];

                makeCorner(prev, current, next, corner);
            }
        }

        switch (builder.paintType) {
            case FILL ->
                    drawType(DrawType.POLYGON);
            case STROKE ->
                    drawType(DrawType.LINE_LOOP);
            default ->
                    throw new IllegalStateException("Unexpected value: " + builder.paintType);
        }

        return super.update();
    }

    private void makeCorner(Vertex prev, Vertex current, Vertex next, Corner corner) {
        switch (corner.cornerType) {
            case NONE ->
                    put(current).end();
            case CIRCLE, ELLIPSE -> {
                float rx = corner.radiusX;
                float ry = corner.radiusY;

                float dx1 = prev.x - current.x;
                float dy1 = prev.y - current.y;
                float len1 = sqrt(dx1 * dx1 + dy1 * dy1);
                dx1 /= len1;
                dy1 /= len1;

                float dx2 = next.x - current.x;
                float dy2 = next.y - current.y;
                float len2 = sqrt(dx2 * dx2 + dy2 * dy2);
                dx2 /= len2;
                dy2 /= len2;

                float dot = dx1 * dx2 + dy1 * dy2;
                float halfAngle = acos(max(-1, min(1, dot))) / 2;

                float avgRadius = (rx + ry) / 2;
                float tangentDist = avgRadius / tan(halfAngle);

                tangentDist = min(tangentDist, min(len1, len2) * 0.9f);

                float actualRadius = tangentDist * tan(halfAngle);

                float tangentX1 = current.x + dx1 * tangentDist;
                float tangentY1 = current.y + dy1 * tangentDist;

                float tangentX2 = current.x + dx2 * tangentDist;
                float tangentY2 = current.y + dy2 * tangentDist;

                float bisectorX = dx1 + dx2;
                float bisectorY = dy1 + dy2;
                float bisectorLen = sqrt(bisectorX * bisectorX + bisectorY * bisectorY);

                if (bisectorLen > 0.0001f) {
                    bisectorX /= bisectorLen;
                    bisectorY /= bisectorLen;

                    float centerDist = actualRadius / sin(halfAngle);

                    float cross = dx1 * dy2 - dy1 * dx2;

                    float centerX;
                    float centerY;
                    if (cross > 0) {
                        centerX = current.x - bisectorX * centerDist;
                        centerY = current.y - bisectorY * centerDist;
                    } else {
                        centerX = current.x + bisectorX * centerDist;
                        centerY = current.y + bisectorY * centerDist;
                    }

                    float startAngle = atan2(tangentY1 - centerY, tangentX1 - centerX);
                    float endAngle = atan2(tangentY2 - centerY, tangentX2 - centerX);

                    float angleDiff = getAngleDiff(endAngle, startAngle, cross);

                    float slice = angleDiff / corner.segmentCount;

                    for (int k = 0; k <= corner.segmentCount; k++) {
                        float currentAngle = startAngle + slice * k;
                        float x = centerX + cos(currentAngle) * rx;
                        float y = centerY + sin(currentAngle) * ry;
                        put(current.copy().moveXYZ(x - current.x, y - current.y, 0)).end();
                    }
                } else {
                    put(current).end();
                }
            }
            case BEVEL, CHAMFER -> {
                float rx = corner.radiusX;
                float ry = corner.radiusY;

                float dx1 = prev.x - current.x;
                float dy1 = prev.y - current.y;
                float len1 = sqrt(dx1 * dx1 + dy1 * dy1);
                dx1 /= len1;
                dy1 /= len1;

                float dx2 = next.x - current.x;
                float dy2 = next.y - current.y;
                float len2 = sqrt(dx2 * dx2 + dy2 * dy2);
                dx2 /= len2;
                dy2 /= len2;

                float tangentDist1 = min(rx, len1 * 0.9f);
                float tangentDist2 = min(ry, len2 * 0.9f);

                float bevelX1 = current.x + dx1 * tangentDist1;
                float bevelY1 = current.y + dy1 * tangentDist1;

                float bevelX2 = current.x + dx2 * tangentDist2;
                float bevelY2 = current.y + dy2 * tangentDist2;

                put(current.copy().moveXYZ(bevelX1 - current.x, bevelY1 - current.y, 0)).end();

                put(current.copy().moveXYZ(bevelX2 - current.x, bevelY2 - current.y, 0)).end();
            }
            case INSET -> {
                float rx = corner.radiusX;
                float ry = corner.radiusY;

                float dx1 = prev.x - current.x;
                float dy1 = prev.y - current.y;
                float len1 = sqrt(dx1 * dx1 + dy1 * dy1);
                dx1 /= len1;
                dy1 /= len1;

                float dx2 = next.x - current.x;
                float dy2 = next.y - current.y;
                float len2 = sqrt(dx2 * dx2 + dy2 * dy2);
                dx2 /= len2;
                dy2 /= len2;

                float tangentDist1 = min(rx, len1 * 0.9f);
                float tangentDist2 = min(ry, len2 * 0.9f);

                float insetX1 = current.x + dx1 * tangentDist1;
                float insetY1 = current.y + dy1 * tangentDist1;

                float insetX2 = current.x + dx2 * tangentDist2;
                float insetY2 = current.y + dy2 * tangentDist2;

                float innerX = current.x + dx1 * tangentDist1 + dx2 * tangentDist2;
                float innerY = current.y + dy1 * tangentDist1 + dy2 * tangentDist2;

                put(current.copy().moveXYZ(insetX1 - current.x, insetY1 - current.y, 0)).end();

                put(current.copy().moveXYZ(innerX - current.x, innerY - current.y, 0)).end();

                put(current.copy().moveXYZ(insetX2 - current.x, insetY2 - current.y, 0)).end();
            }
            case CONCAVE -> {
                float rx = corner.radiusX;
                float ry = corner.radiusY;
                float dx1 = prev.x - current.x;
                float dy1 = prev.y - current.y;
                float len1 = sqrt(dx1 * dx1 + dy1 * dy1);
                dx1 /= len1;
                dy1 /= len1;

                float dx2 = next.x - current.x;
                float dy2 = next.y - current.y;
                float len2 = sqrt(dx2 * dx2 + dy2 * dy2);
                dx2 /= len2;
                dy2 /= len2;

                float dot = dx1 * dx2 + dy1 * dy2;
                float halfAngle = acos(max(-1, min(1, dot))) / 2;
                float avgRadius = (rx + ry) / 2;
                float tangentDist = avgRadius / tan(halfAngle);
                tangentDist = min(tangentDist, min(len1, len2) * 0.9f);

                float actualRadius = tangentDist * tan(halfAngle);
                float tangentX1 = current.x + dx1 * tangentDist;
                float tangentY1 = current.y + dy1 * tangentDist;
                float tangentX2 = current.x + dx2 * tangentDist;
                float tangentY2 = current.y + dy2 * tangentDist;
                float bisectorX = dx1 + dx2;
                float bisectorY = dy1 + dy2;
                float bisectorLen = sqrt(bisectorX * bisectorX + bisectorY * bisectorY);
                if (bisectorLen > 0.0001f) {
                    bisectorX /= bisectorLen;
                    bisectorY /= bisectorLen;

                    float centerDist = actualRadius / sin(halfAngle);
                    float cross = dx1 * dy2 - dy1 * dx2;
                    float centerX;
                    float centerY;

                    if (cross > 0) {
                        centerX = current.x + bisectorX * centerDist;
                        centerY = current.y + bisectorY * centerDist;
                    } else {
                        centerX = current.x - bisectorX * centerDist;
                        centerY = current.y - bisectorY * centerDist;
                    }

                    float startAngle = atan2(tangentY1 - centerY, tangentX1 - centerX);
                    float endAngle = atan2(tangentY2 - centerY, tangentX2 - centerX);
                    float angleDiff = getAngleDiff(endAngle, startAngle, -cross);
                    float slice = angleDiff / corner.segmentCount;
                    for (int k = 0; k <= corner.segmentCount; k++) {
                        float currentAngle = startAngle + slice * k;
                        float x = centerX + cos(currentAngle) * rx;
                        float y = centerY + sin(currentAngle) * ry;

                        put(current.copy().moveXYZ(x - current.x, y - current.y, 0)).end();
                    }
                } else {
                    put(current).end();
                }
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + corner.cornerType);
        }
    }

    @Override
    public int getObjectId() {
        return super.getObjectId();
    }

    public interface VertexData1Step<T> {
        VertexData2Step<T> vertex1(Vertex vertex1, Corner corner1);

        VertexData22pStep<T> vertex1_2p(Vertex vertex1, Corner corner1);
    }

    public interface VertexData2Step<T> {
        VertexData3Step<T> vertex2(Vertex vertex2, Corner corner2);
    }

    public interface VertexData22pStep<T> {
        VertexData1Step<T> vertex2_2p(Vertex vertex2, Corner corner2);

        PaintTypeStep<T> vertex2_2p_end(Vertex vertex2, Corner corner2);
    }

    public interface VertexData3Step<T> {
        VertexData4Step<T> vertex3(Vertex vertex3, Corner corner3);
    }

    public interface VertexData4Step<T> {
        VertexData1Step<T> vertex4(Vertex vertex4, Corner corner4);

        PaintTypeStep<T> vertex4_end(Vertex vertex4, Corner corner4);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLRoundedRect> implements VertexData1Step<GLRoundedRect>, VertexData2Step<GLRoundedRect>, VertexData22pStep<GLRoundedRect>, VertexData3Step<GLRoundedRect>, VertexData4Step<GLRoundedRect> {
        private final String name;
        private final List<Vertex> vertices;
        private final List<Corner> corners;
        private Vertex lastVertex2p;
        private Corner lastCorner2p;

        private GLRoundedRect glRoundedRect;

        public Builder(String name) {
            this.name = name;

            this.vertices = new ArrayList<>();
            this.corners = new ArrayList<>();
        }

        @Override
        public VertexData2Step<GLRoundedRect> vertex1(Vertex vertex1, Corner corner1) {
            this.vertices.add(vertex1);
            this.corners.add(corner1);

            return this;
        }

        @Override
        public VertexData22pStep<GLRoundedRect> vertex1_2p(Vertex vertex1, Corner corner1) {
            this.lastVertex2p = vertex1;
            this.lastCorner2p = corner1;
            this.vertices.add(vertex1);
            this.corners.add(corner1);

            return this;
        }

        @Override
        public VertexData1Step<GLRoundedRect> vertex2_2p(Vertex vertex2, Corner corner2) {
            this.vertices.add(lastVertex2p.copy().setX(vertex2.x));
            this.vertices.add(vertex2);
            this.vertices.add(lastVertex2p.copy().setY(vertex2.y));

            this.corners.add(lastCorner2p.copy());
            this.corners.add(corner2);
            this.corners.add(lastCorner2p.copy());

            return this;
        }

        @Override
        public PaintTypeStep<GLRoundedRect> vertex2_2p_end(Vertex vertex2, Corner corner2) {
            this.vertices.add(lastVertex2p.copy().setX(vertex2.x));
            this.vertices.add(vertex2);
            this.vertices.add(lastVertex2p.copy().setY(vertex2.y));

            this.corners.add(lastCorner2p.copy());
            this.corners.add(corner2);
            this.corners.add(lastCorner2p.copy());

            return this;
        }

        @Override
        public VertexData3Step<GLRoundedRect> vertex2(Vertex vertex2, Corner corner2) {
            this.vertices.add(vertex2);
            this.corners.add(corner2);

            return this;
        }

        @Override
        public VertexData4Step<GLRoundedRect> vertex3(Vertex vertex3, Corner corner3) {
            this.vertices.add(vertex3);
            this.corners.add(corner3);

            return this;
        }

        @Override
        public VertexData1Step<GLRoundedRect> vertex4(Vertex vertex4, Corner corner4) {
            this.vertices.add(vertex4);
            this.corners.add(corner4);

            return this;
        }

        @Override
        public PaintTypeStep<GLRoundedRect> vertex4_end(Vertex vertex4, Corner corner4) {
            this.vertices.add(vertex4);
            this.corners.add(corner4);

            return this;
        }

        @Override
        protected GLRoundedRect createOrGet() {
            if (glRoundedRect == null) {
                return glRoundedRect = new GLRoundedRect(name, this);
            } else {
                return glRoundedRect.update();
            }
        }
    }
}