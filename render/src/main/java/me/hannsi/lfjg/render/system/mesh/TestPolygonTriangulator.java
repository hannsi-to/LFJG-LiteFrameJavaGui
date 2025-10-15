package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.PolygonTriangulatorException;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.renderers.PointType;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;

import java.util.*;

import static me.hannsi.lfjg.core.utils.math.MathHelper.*;

public class TestPolygonTriangulator {
    public static final boolean DEBUG = false;
    private static final float MITER_LIMIT = 4.0f;

    private ProjectionType projectionType;
    private DrawType drawType;
    private float lineWidth;
    private JointType lineJointType;
    private float pointSize;
    private PointType pointType;
    private Vertex[] vertices;
    private float[] outPositions;
    private TestElementPair result;

    public static TestPolygonTriangulator createPolygonTriangulator() {
        return new TestPolygonTriangulator();
    }

    public static Vector2f computeLineIntersection(Vector2f p1, Vector2f p2, Vector2f p3, Vector2f p4) {
        float x1 = p1.x;
        float y1 = p1.y;
        float x2 = p2.x;
        float y2 = p2.y;
        float x3 = p3.x;
        float y3 = p3.y;
        float x4 = p4.x;
        float y4 = p4.y;

        float denom = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (denom == 0) {
            return null;
        }

        float t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denom;

        float ix = x1 + t * (x2 - x1);
        float iy = y1 + t * (y2 - y1);

        return new Vector2f(ix, iy);
    }

    public TestPolygonTriangulator projectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;
        return this;
    }

    public TestPolygonTriangulator drawType(DrawType drawType) {
        this.drawType = drawType;
        return this;
    }

    public TestPolygonTriangulator lineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    public TestPolygonTriangulator lineJointType(JointType lineJointType) {
        this.lineJointType = lineJointType;
        return this;
    }

    public TestPolygonTriangulator pointSize(float pointSize) {
        this.pointSize = pointSize;
        return this;
    }

    public TestPolygonTriangulator pointType(PointType pointType) {
        this.pointType = pointType;
        return this;
    }

    public TestPolygonTriangulator vertices(Vertex... vertices) {
        this.vertices = vertices;
        return this;
    }

    public TestPolygonTriangulator process() {
        try {
            result = processTriangulator();
        } catch (Exception e) {
            DebugLog.error(getClass(), e);
            DebugLog.error(getClass(), new PolygonTriangulatorException("Triangulation failed"));
            result = new TestElementPair(new Vertex[0], new int[0]);
        }

        return this;
    }

    private TestElementPair processTriangulator() {
        if (vertices == null || vertices.length < projectionType.getStride()) {
            DebugLog.warning(getClass(), "Positions are null or too short");
            return new TestElementPair(new Vertex[0], new int[0]);
        }

        outPositions = new float[vertices.length * 3];
        for (int i = 0, index = 0; i < vertices.length; i++, index += 3) {
            Vertex vertex = vertices[i];
            float[] pos = vertex.getPositions();
            outPositions[index] = pos[0];
            outPositions[index + 1] = pos[1];
            outPositions[index + 2] = pos[2];
        }

        int stride = projectionType.getStride();
        int lineCount = vertices.length / 2;
        List<Vertex> newVertices = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        try {
            switch (drawType) {
                case POINTS:
                    switch (pointType) {
                        case SQUARE:
                            for (Vertex vertex : vertices) {
                                float offset = sqrt(pow(pointSize, 2f) / 2f);
                                Vertex p1 = vertex.copy().replaceXYZ(vertex.x - offset, vertex.y + offset, vertex.z);
                                Vertex p2 = vertex.copy().replaceXYZ(vertex.x - offset, vertex.y - offset, vertex.z);
                                Vertex p3 = vertex.copy().replaceXYZ(vertex.x + offset, vertex.y + offset, vertex.z);
                                Vertex p4 = vertex.copy().replaceXYZ(vertex.x + offset, vertex.y - offset, vertex.z);

                                makeLineQuad(
                                        p1, p2, p3, p4,
                                        newVertices, indices
                                );
                            }

                            return new TestElementPair(newVertices.toArray(new Vertex[0]), indices.stream().mapToInt(Integer::intValue).toArray());
                            
                        case ROUND:
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + pointType);
                    }
                case LINES:
                    checkLineParameter();
                    if (lineCount < 1) {
                        throw new PolygonTriangulatorException("When DrawType.LINES is specified, vertex information must be two or more.");
                    }

                    for (int i = 0; i < vertices.length - 1; i += 2) {
                        makeLines(vertices[i], vertices[i + 1], lineWidth, newVertices, indices);
                    }

                    return new TestElementPair(newVertices.toArray(new Vertex[0]), indices.stream().mapToInt(Integer::intValue).toArray());

                case LINE_STRIP:
                    checkLineParameter();
                    if (lineCount < 1) {
                        throw new PolygonTriangulatorException("When DrawType.LINE_STRIP is specified, vertex information must be two or more.");
                    }

                    lineCount = vertices.length - 1;
                    makeLine(false, lineCount, newVertices, indices);
                    return new TestElementPair(newVertices.toArray(new Vertex[0]), indices.stream().mapToInt(Integer::intValue).toArray());

                case LINE_LOOP:
                    checkLineParameter();
                    if (lineCount < 1) {
                        throw new PolygonTriangulatorException("When DrawType.LINE_LOOP is specified, vertex information must be two or more.");
                    }

                    lineCount = vertices.length - 1;
                    makeLine(true, lineCount, newVertices, indices);
                    return new TestElementPair(newVertices.toArray(new Vertex[0]), indices.stream().mapToInt(Integer::intValue).toArray());

                case TRIANGLES:
                case TRIANGLE_FAN:
                    int triCount = outPositions.length / stride;
                    int[] triIndices = new int[triCount];
                    for (int i = 0; i < triCount; i++) {
                        triIndices[i] = i;
                    }

                    return new TestElementPair(vertices, triIndices);
                case QUADS:
                    List<Integer> idxListQ = new ArrayList<>();
                    int quadVertices = outPositions.length / stride;
                    for (int q = 0; q + 3 < quadVertices; q += 4) {
                        idxListQ.add(q);
                        idxListQ.add(q + 1);
                        idxListQ.add(q + 2);

                        idxListQ.add(q);
                        idxListQ.add(q + 2);
                        idxListQ.add(q + 3);
                    }

                    return new TestElementPair(vertices, idxListQ.stream().mapToInt(i -> i).toArray());

                case POLYGON:
                    try {
                        return handlePolygon(vertices, stride);
                    } catch (Exception e) {
                        DebugLog.error(getClass(), e);
                        DebugLog.error(getClass(), new PolygonTriangulatorException("Polygon triangulation failed"));
                        return new TestElementPair(vertices, new int[0]);
                    }

                default:
                    throw new IllegalStateException("Unexpected DrawType: " + drawType);
            }
        } catch (Exception e) {
            DebugLog.error(getClass(), e);
            DebugLog.error(getClass(), new PolygonTriangulatorException("Triangulation process failed"));
            return new TestElementPair(vertices, new int[0]);
        }
    }

    private void makeLines(Vertex v1, Vertex v2, float lineWidth, List<Vertex> newVertices, List<Integer> indices) {
        float dx = v2.x - v1.x;
        float dy = v2.y - v1.y;
        float nx = -dy;
        float ny = dx;
        float length = sqrt(pow(nx, 2) + pow(ny, 2));
        nx = nx / length * (lineWidth / 2f);
        ny = ny / length * (lineWidth / 2f);

        makeLineQuad(
                new Vertex(v1.x + nx, v1.y + ny, v1.z, v1.red, v1.green, v1.blue, v1.alpha, 0, 0, v1.normalsX, v1.normalsY, v1.normalsZ),
                new Vertex(v1.x - nx, v1.y - ny, v1.z, v1.red, v1.green, v1.blue, v1.alpha, 0, 1, v1.normalsX, v1.normalsY, v1.normalsZ),
                new Vertex(v2.x + nx, v2.y + ny, v2.z, v2.red, v2.green, v2.blue, v2.alpha, 1, 0, v2.normalsX, v2.normalsY, v2.normalsZ),
                new Vertex(v2.x - nx, v2.y - ny, v2.z, v2.red, v2.green, v2.blue, v2.alpha, 1, 1, v2.normalsX, v2.normalsY, v2.normalsZ),
                newVertices, indices
        );
    }

    private void makeLine(boolean loop, int lineCount, List<Vertex> newVertices, List<Integer> indices) {
        if (lineCount == 1) {
            Vertex v1 = vertices[0];
            Vertex v2 = vertices[1];
            float dx = v2.x - v1.x;
            float dy = v2.y - v1.y;
            float length = sqrt(pow(dx, 2) + pow(dy, 2));
            float nx = -dy / length * (lineWidth / 2f);
            float ny = dx / length * (lineWidth / 2f);

            makeLineQuad(
                    new Vertex(v1.x + nx, v1.y + ny, v1.z, v1.red, v1.green, v1.blue, v1.alpha, 0, 0, v1.normalsX, v1.normalsY, v1.normalsZ),
                    new Vertex(v1.x - nx, v1.y - ny, v1.z, v1.red, v1.green, v1.blue, v1.alpha, 0, 1, v1.normalsX, v1.normalsY, v1.normalsZ),
                    new Vertex(v2.x + nx, v2.y + ny, v2.z, v2.red, v2.green, v2.blue, v2.alpha, 1, 0, v2.normalsX, v2.normalsY, v2.normalsZ),
                    new Vertex(v2.x - nx, v2.y - ny, v2.z, v2.red, v2.green, v2.blue, v2.alpha, 1, 1, v2.normalsX, v2.normalsY, v2.normalsZ),
                    newVertices, indices
            );

            return;
        }

        if (loop) {
            vertices = Arrays.copyOf(vertices, vertices.length + 1);
            vertices[vertices.length - 1] = vertices[0];
        }

        Vertex vertex1;
        Vertex vertex2;
        Vertex vertex3;
        Vertex vertex4;
        Vertex vertex5;
        Vertex vertex6;
        Vertex crossVertex = null;
        Vector2f lastJointVertex = null;
        float lastSign = 0;
        for (int i = 0; i < vertices.length - 1; i++) {
            Vertex prevVertex;
            if (i == 0) {
                prevVertex = vertices[vertices.length - 2];
            } else {
                prevVertex = vertices[i - 1];
            }
            Vertex currentVertex = vertices[i];
            Vertex nextVertex = vertices[i + 1];

            float dx1 = currentVertex.x - prevVertex.x;
            float dy1 = currentVertex.y - prevVertex.y;
            float length1 = sqrt(pow(dx1, 2) + pow(dy1, 2));
            float normalX1 = -dy1 / length1 * (lineWidth / 2f);
            float normalY1 = dx1 / length1 * (lineWidth / 2f);

            float dx2 = nextVertex.x - currentVertex.x;
            float dy2 = nextVertex.y - currentVertex.y;
            float length2 = sqrt(pow(dx2, 2) + pow(dy2, 2));
            float normalX2 = -dy2 / length2 * (lineWidth / 2f);
            float normalY2 = dx2 / length2 * (lineWidth / 2f);

            float dot = dx1 * dx2 + dy1 * dy2;
            float cross = dx1 * dy2 - dy1 * dx2;
            float angle = atan2(cross, dot);
            float sign = (angle < 0) ? -1 : 1;
            Vector2f offset1 = new Vector2f(normalX1 * sign, normalY1 * sign);
            Vector2f offset2 = new Vector2f(normalX2 * sign, normalY2 * sign);

            Vector2f p1 = new Vector2f(prevVertex.x + offset1.x, prevVertex.y + offset1.y);
            Vector2f p2 = new Vector2f(currentVertex.x + offset1.x, currentVertex.y + offset1.y);
            Vector2f p3 = new Vector2f(currentVertex.x + offset2.x, currentVertex.y + offset2.y);
            Vector2f p4 = new Vector2f(nextVertex.x + offset2.x, nextVertex.y + offset2.y);

            Vector2f jointVertex2f = computeLineIntersection(p1, p2, p3, p4);

            offset1 = new Vector2f(normalX1 * -sign, normalY1 * -sign);
            offset2 = new Vector2f(normalX2 * -sign, normalY2 * -sign);
            p1 = new Vector2f(prevVertex.x + offset1.x, prevVertex.y + offset1.y);
            p2 = new Vector2f(currentVertex.x + offset1.x, currentVertex.y + offset1.y);
            p3 = new Vector2f(currentVertex.x + offset2.x, currentVertex.y + offset2.y);
            p4 = new Vector2f(nextVertex.x + offset2.x, nextVertex.y + offset2.y);

            Vector2f crossVertex2f = computeLineIntersection(p1, p2, p3, p4);

            vertex1 = new Vertex(prevVertex.x + normalX1, prevVertex.y + normalY1, prevVertex.z, prevVertex.red, prevVertex.green, prevVertex.blue, prevVertex.alpha, 0, 0, prevVertex.normalsX, prevVertex.normalsY, prevVertex.normalsZ);
            vertex2 = new Vertex(prevVertex.x - normalX1, prevVertex.y - normalY1, prevVertex.z, prevVertex.red, prevVertex.green, prevVertex.blue, prevVertex.alpha, 0, 1, prevVertex.normalsX, prevVertex.normalsY, prevVertex.normalsZ);
            if (lastJointVertex != null) {
                if (lastSign == -1) {
                    vertex2 = new Vertex(lastJointVertex.x, lastJointVertex.y, prevVertex.z, prevVertex.red, prevVertex.green, prevVertex.blue, prevVertex.alpha, 0, 1, prevVertex.normalsX, prevVertex.normalsY, prevVertex.normalsZ);
                } else {
                    vertex1 = new Vertex(lastJointVertex.x, lastJointVertex.y, prevVertex.z, prevVertex.red, prevVertex.green, prevVertex.blue, prevVertex.alpha, 0, 0, prevVertex.normalsX, prevVertex.normalsY, prevVertex.normalsZ);
                }
            }

            vertex3 = new Vertex(currentVertex.x + normalX1, currentVertex.y + normalY1, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 0, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
            vertex4 = new Vertex(currentVertex.x - normalX1, currentVertex.y - normalY1, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 1, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
            vertex5 = new Vertex(currentVertex.x + normalX2, currentVertex.y + normalY2, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 0, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
            vertex6 = new Vertex(currentVertex.x - normalX2, currentVertex.y - normalY2, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 1, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
            if (jointVertex2f != null) {
                if (sign == -1) {
                    vertex4 = new Vertex(jointVertex2f.x, jointVertex2f.y, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 1, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
                    vertex6 = new Vertex(jointVertex2f.x, jointVertex2f.y, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 1, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
                } else {
                    vertex3 = new Vertex(jointVertex2f.x, jointVertex2f.y, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 0, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
                    vertex5 = new Vertex(jointVertex2f.x, jointVertex2f.y, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 0, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
                }
            }

            if (crossVertex2f != null) {
                crossVertex = new Vertex(crossVertex2f.x, crossVertex2f.y, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 0, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
            }

            if (i >= 1) {
                makeLineQuad(
                        vertex1,
                        vertex2,
                        vertex3,
                        vertex4,
                        newVertices, indices
                );

                makeJoint(
                        vertex3,
                        vertex4,
                        vertex5,
                        vertex6,
                        crossVertex,
                        angle, sign, newVertices, indices
                );
            }

            lastJointVertex = jointVertex2f;
            lastSign = sign;
        }

        Vertex prevVertex = vertices[vertices.length - 2];
        Vertex currentVertex = vertices[vertices.length - 1];
        if (loop) {
            Vertex nextVertex = vertices[1];

            float dx1 = currentVertex.x - prevVertex.x;
            float dy1 = currentVertex.y - prevVertex.y;
            float length1 = sqrt(pow(dx1, 2) + pow(dy1, 2));
            float normalX1 = -dy1 / length1 * (lineWidth / 2f);
            float normalY1 = dx1 / length1 * (lineWidth / 2f);

            float dx2 = nextVertex.x - currentVertex.x;
            float dy2 = nextVertex.y - currentVertex.y;
            float length2 = sqrt(pow(dx2, 2) + pow(dy2, 2));
            float normalX2 = -dy2 / length2 * (lineWidth / 2f);
            float normalY2 = dx2 / length2 * (lineWidth / 2f);

            float dot = dx1 * dx2 + dy1 * dy2;
            float cross = dx1 * dy2 - dy1 * dx2;
            float angle = atan2(cross, dot);
            float sign = (angle < 0) ? -1 : 1;
            Vector2f offset1 = new Vector2f(normalX1 * sign, normalY1 * sign);
            Vector2f offset2 = new Vector2f(normalX2 * sign, normalY2 * sign);

            Vector2f p1 = new Vector2f(prevVertex.x + offset1.x, prevVertex.y + offset1.y);
            Vector2f p2 = new Vector2f(currentVertex.x + offset1.x, currentVertex.y + offset1.y);
            Vector2f p3 = new Vector2f(currentVertex.x + offset2.x, currentVertex.y + offset2.y);
            Vector2f p4 = new Vector2f(nextVertex.x + offset2.x, nextVertex.y + offset2.y);

            Vector2f jointVertex2f = computeLineIntersection(p1, p2, p3, p4);

            offset1 = new Vector2f(normalX1 * -sign, normalY1 * -sign);
            offset2 = new Vector2f(normalX2 * -sign, normalY2 * -sign);
            p1 = new Vector2f(prevVertex.x + offset1.x, prevVertex.y + offset1.y);
            p2 = new Vector2f(currentVertex.x + offset1.x, currentVertex.y + offset1.y);
            p3 = new Vector2f(currentVertex.x + offset2.x, currentVertex.y + offset2.y);
            p4 = new Vector2f(nextVertex.x + offset2.x, nextVertex.y + offset2.y);

            Vector2f crossVertex2f = computeLineIntersection(p1, p2, p3, p4);

            vertex1 = new Vertex(prevVertex.x + normalX1, prevVertex.y + normalY1, prevVertex.z, prevVertex.red, prevVertex.green, prevVertex.blue, prevVertex.alpha, 0, 0, prevVertex.normalsX, prevVertex.normalsY, prevVertex.normalsZ);
            vertex2 = new Vertex(prevVertex.x - normalX1, prevVertex.y - normalY1, prevVertex.z, prevVertex.red, prevVertex.green, prevVertex.blue, prevVertex.alpha, 0, 1, prevVertex.normalsX, prevVertex.normalsY, prevVertex.normalsZ);
            if (lastJointVertex != null) {
                if (lastSign == -1) {
                    vertex2 = new Vertex(lastJointVertex.x, lastJointVertex.y, prevVertex.z, prevVertex.red, prevVertex.green, prevVertex.blue, prevVertex.alpha, 0, 1, prevVertex.normalsX, prevVertex.normalsY, prevVertex.normalsZ);
                } else {
                    vertex1 = new Vertex(lastJointVertex.x, lastJointVertex.y, prevVertex.z, prevVertex.red, prevVertex.green, prevVertex.blue, prevVertex.alpha, 0, 0, prevVertex.normalsX, prevVertex.normalsY, prevVertex.normalsZ);
                }
            }

            vertex3 = new Vertex(currentVertex.x + normalX1, currentVertex.y + normalY1, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 0, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
            vertex4 = new Vertex(currentVertex.x - normalX1, currentVertex.y - normalY1, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 1, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
            vertex5 = new Vertex(currentVertex.x + normalX2, currentVertex.y + normalY2, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 0, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
            vertex6 = new Vertex(currentVertex.x - normalX2, currentVertex.y - normalY2, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 1, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
            if (jointVertex2f != null) {
                if (sign == -1) {
                    vertex4 = new Vertex(jointVertex2f.x, jointVertex2f.y, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 1, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
                    vertex6 = new Vertex(jointVertex2f.x, jointVertex2f.y, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 1, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
                } else {
                    vertex3 = new Vertex(jointVertex2f.x, jointVertex2f.y, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 0, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
                    vertex5 = new Vertex(jointVertex2f.x, jointVertex2f.y, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 0, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
                }
            }

            if (crossVertex2f != null) {
                crossVertex = new Vertex(crossVertex2f.x, crossVertex2f.y, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 0, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
            }

            makeLineQuad(
                    vertex1,
                    vertex2,
                    vertex3,
                    vertex4,
                    newVertices, indices
            );

            makeJoint(
                    vertex3,
                    vertex4,
                    vertex5,
                    vertex6,
                    crossVertex,
                    angle, sign, newVertices, indices
            );
        } else {

            float dx1 = currentVertex.x - prevVertex.x;
            float dy1 = currentVertex.y - prevVertex.y;
            float length1 = sqrt(pow(dx1, 2) + pow(dy1, 2));
            float normalX1 = -dy1 / length1 * (lineWidth / 2f);
            float normalY1 = dx1 / length1 * (lineWidth / 2f);

            vertex1 = new Vertex(prevVertex.x + normalX1, prevVertex.y + normalY1, prevVertex.z, prevVertex.red, prevVertex.green, prevVertex.blue, prevVertex.alpha, 0, 0, prevVertex.normalsX, prevVertex.normalsY, prevVertex.normalsZ);
            vertex2 = new Vertex(prevVertex.x - normalX1, prevVertex.y - normalY1, prevVertex.z, prevVertex.red, prevVertex.green, prevVertex.blue, prevVertex.alpha, 0, 1, prevVertex.normalsX, prevVertex.normalsY, prevVertex.normalsZ);
            if (lastJointVertex != null) {
                if (lastSign == -1) {
                    vertex2 = new Vertex(lastJointVertex.x, lastJointVertex.y, prevVertex.z, prevVertex.red, prevVertex.green, prevVertex.blue, prevVertex.alpha, 0, 1, prevVertex.normalsX, prevVertex.normalsY, prevVertex.normalsZ);
                } else {
                    vertex1 = new Vertex(lastJointVertex.x, lastJointVertex.y, prevVertex.z, prevVertex.red, prevVertex.green, prevVertex.blue, prevVertex.alpha, 0, 0, prevVertex.normalsX, prevVertex.normalsY, prevVertex.normalsZ);
                }
            }

            vertex3 = new Vertex(currentVertex.x + normalX1, currentVertex.y + normalY1, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 0, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);
            vertex4 = new Vertex(currentVertex.x - normalX1, currentVertex.y - normalY1, currentVertex.z, currentVertex.red, currentVertex.green, currentVertex.blue, currentVertex.alpha, 1, 1, currentVertex.normalsX, currentVertex.normalsY, currentVertex.normalsZ);

            makeLineQuad(
                    vertex1,
                    vertex2,
                    vertex3,
                    vertex4,
                    newVertices, indices
            );
        }
    }

    private void makeJoint(Vertex vertex3, Vertex vertex4, Vertex vertex5, Vertex vertex6, Vertex crossVertex, float angle, float sign, List<Vertex> newVertices, List<Integer> indices) {
        JointType tempJoinType = lineJointType;
        float sinHalfAngle = sin(angle * 0.5f);
        if (tempJoinType == JointType.MITER) {
            if (!(abs(sinHalfAngle) < 1e-5f)) {
                float miterLengthRatio = 1f / abs(sinHalfAngle);
                if (miterLengthRatio < MITER_LIMIT) {
                    tempJoinType = JointType.BEVEL;
                }
            }
        }

        int base;
        switch (tempJoinType) {
            case NONE:
                break;
            case MITER:
                if (crossVertex == null) {
                    return;
                }

                if (sign == -1) {
                    makeLineQuad(
                            crossVertex,
                            vertex3,
                            vertex5,
                            vertex6,
                            newVertices, indices
                    );
                } else {
                    makeLineQuad(
                            vertex4,
                            crossVertex,
                            vertex5,
                            vertex6,
                            newVertices, indices
                    );
                }
                break;
            case BEVEL:
                base = newVertices.size();
                newVertices.add(vertex3.copy());
                newVertices.add(vertex4.copy());
                if (sign == -1) {
                    newVertices.add(vertex5.copy());
                } else {
                    newVertices.add(vertex6.copy());
                }

                indices.add(base);
                indices.add(base + 1);
                indices.add(base + 2);
                break;
            case ROUND:
                // TODO

//                base = newVertices.size();
//
//                Vertex center;
//                float dx0;
//                float dy0;
//                float dx2;
//                float dy2;
//                if (sign == -1) {
//                    center = vertex4.copy();
//                } else {
//                    center = vertex5.copy();
//                }
//                newVertices.add(center);
//
//                float radius = lineWidth;
//                int segments = 20;
//
//                float startAngle = 0;
//                float endAngle = angle;
//                if (sign < 0) {
//                    float tmp = startAngle;
//                    startAngle = endAngle;
//                    endAngle = tmp;
//                }
//
//                for (int i = 0; i <= segments; i++) {
//                    float t = i / (float) segments;
//                    float theta = startAngle + (endAngle - startAngle) * t;
//
//                    float x = center.x + cos(theta) * radius;
//                    float y = center.y + sin(theta) * radius;
//
//                    Vertex v = center.copy();
//                    v.x = x;
//                    v.y = y;
//                    newVertices.add(v);
//                }
//
//                for (int i = 0; i < segments; i++) {
//                    indices.add(base);
//                    indices.add(base + i + 1);
//                    indices.add(base + i + 2);
//                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + lineJointType);
        }
    }

    private void makeLineQuad(Vertex v1, Vertex v2, Vertex v3, Vertex v4, List<Vertex> newVertices, List<Integer> indices) {
        int base = newVertices.size();
        newVertices.add(v1.copy());
        newVertices.add(v2.copy());
        newVertices.add(v3.copy());
        newVertices.add(v4.copy());

        indices.add(base);
        indices.add(base + 1);
        indices.add(base + 2);
        indices.add(base + 2);
        indices.add(base + 1);
        indices.add(base + 3);
    }

    private void checkLineParameter() {
        if (lineWidth < 0) {
            throw new PolygonTriangulatorException("Line width is less than zero.");
        }
        if (lineJointType == null) {
            throw new PolygonTriangulatorException("LineJointType is not set.");
        }
    }

    private TestElementPair handlePolygon(Vertex[] outVertices, int stride) {
        List<float[]> vertices = new ArrayList<>();
        for (int i = 0; i < this.outPositions.length; i += stride) {
            float[] v = new float[stride];
            System.arraycopy(this.outPositions, i, v, 0, stride);
            vertices.add(v);
        }

        ensureCCW(vertices);

        List<float[]> projected = new ArrayList<>();
        if (stride == 2) {
            projected.addAll(vertices);
        } else {
            for (float[] v : vertices) {
                projected.add(new float[]{v[0], v[2]});
            }
        }

        List<List<Integer>> monotonePolygons = partitionMonotoneCompleteWithLogging(projected);

        List<int[]> triangles = new ArrayList<>();
        for (List<Integer> mono : monotonePolygons) {
            List<int[]> t = triangulateMonotonePolygon(mono, projected);
            triangles.addAll(t);
            info(getClass(), "Generated triangles: " + Arrays.deepToString(t.toArray()));
        }

        return new TestElementPair(outVertices, triangles.stream().flatMapToInt(Arrays::stream).toArray());
    }

    private void ensureCCW(List<float[]> vertices) {
        if (!isPolygonCCW(vertices)) {
            Collections.reverse(vertices);
            info(getClass(), "Polygon vertices reversed to ensure CCW");
        }
    }

    private boolean isPolygonCCW(List<float[]> vertices) {
        float sum = 0;
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            float[] v0 = vertices.get(i);
            float[] v1 = vertices.get((i + 1) % n);
            sum += (v1[0] - v0[0]) * (v1[1] + v0[1]);
        }

        return sum < 0;
    }

    private List<List<Integer>> partitionMonotoneCompleteWithLogging(List<float[]> vertices) {
        int n = vertices.size();
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            order.add(i);
        }

        order.sort((i1, i2) -> {
            float dy = vertices.get(i2)[1] - vertices.get(i1)[1];
            if (abs(dy) > 1e-6) {
                return dy > 0 ? 1 : -1;
            }

            return Float.compare(vertices.get(i1)[0], vertices.get(i2)[0]);
        });

        VertexType[] types = new VertexType[n];
        for (int i = 0; i < n; i++) {
            types[i] = classifyVertex(vertices, i);
            info(getClass(), "Vertex " + i + " type: " + types[i]);
        }

        TreeMap<Float, Integer> edgeTree = new TreeMap<>();
        Map<Integer, Integer> helper = new HashMap<>();
        List<int[]> diagonals = new ArrayList<>();
        for (int idx : order) {
            try {
                switch (types[idx]) {
                    case START:
                        addEdgeWithLogging(edgeTree, helper, idx, vertices);
                        break;
                    case END:
                        int left = findLeftEdge(edgeTree, vertices.get(idx));
                        if (helper.containsKey(left)) {
                            diagonals.add(new int[]{idx, helper.get(left)});
                            info(getClass(), "Added diagonal: " + idx + " - " + helper.get(left));
                        }
                        removeEdgeWithLogging(edgeTree, helper, idx, vertices);
                        break;
                    case SPLIT:
                        int l = findLeftEdge(edgeTree, vertices.get(idx));
                        diagonals.add(new int[]{idx, helper.get(l)});
                        info(getClass(), "Split vertex diagonal: " + idx + " - " + helper.get(l));
                        helper.put(idx, idx);
                        addEdgeWithLogging(edgeTree, helper, idx, vertices);
                        break;
                    case MERGE:
                        int le = findLeftEdge(edgeTree, vertices.get(idx));
                        diagonals.add(new int[]{idx, helper.get(le)});
                        info(getClass(), "Merge vertex diagonal: " + idx + " - " + helper.get(le));
                        removeEdgeWithLogging(edgeTree, helper, idx, vertices);
                        break;
                    case REGULAR:
                        handleRegularVertex(edgeTree, helper, idx, vertices, diagonals);
                        break;
                }
            } catch (Exception e) {
                DebugLog.error(getClass(), e);
                DebugLog.error(getClass(), new PolygonTriangulatorException("Error processing vertex " + idx));
            }
        }

        info(getClass(), "Diagonals computed: " + Arrays.deepToString(diagonals.toArray()));
        return splitPolygonByDiagonals(vertices.size(), diagonals);
    }

    private void addEdgeWithLogging(TreeMap<Float, Integer> tree, Map<Integer, Integer> helper, int idx, List<float[]> v) {
        tree.put(v.get(idx)[0], idx);
        helper.put(idx, idx);
        info(getClass(), "Edge added at vertex " + idx);
    }

    private void removeEdgeWithLogging(TreeMap<Float, Integer> tree, Map<Integer, Integer> helper, int idx, List<float[]> v) {
        tree.remove(v.get(idx)[0]);
        helper.remove(idx);
        info(getClass(), "Edge removed at vertex " + idx);
    }


    private List<List<Integer>> splitPolygonByDiagonals(int n, List<int[]> diagonals) {
        List<Set<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new HashSet<>());

        for (int i = 0; i < n; i++) {
            int next = (i + 1) % n;
            graph.get(i).add(next);
            graph.get(next).add(i);
        }

        for (int[] diag : diagonals) {
            int a = diag[0];
            int b = diag[1];
            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        boolean[] visited = new boolean[n];
        List<List<Integer>> polygons = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }

            List<Integer> polygon = new ArrayList<>();
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(i);
            visited[i] = true;

            while (!stack.isEmpty()) {
                int v = stack.pop();
                polygon.add(v);
                for (int u : graph.get(v)) {
                    if (!visited[u]) {
                        visited[u] = true;
                        stack.push(u);
                    }
                }
            }

            polygon.sort(Integer::compareTo);
            polygons.add(polygon);
        }

        info(getClass(), "Generated subpolygon: " + polygons);

        return polygons;
    }

    private VertexType classifyVertex(List<float[]> v, int i) {
        int n = v.size();
        float[] prev = v.get((i + n - 1) % n);
        float[] curr = v.get(i);
        float[] next = v.get((i + 1) % n);
        if (curr[1] > prev[1] && curr[1] > next[1]) {
            return isLeft(prev, curr, next) ? VertexType.START : VertexType.SPLIT;
        } else if (curr[1] < prev[1] && curr[1] < next[1]) {
            return isLeft(prev, curr, next) ? VertexType.END : VertexType.MERGE;
        }

        return VertexType.REGULAR;
    }

    private boolean isLeft(float[] a, float[] b, float[] c) {
        return (c[0] - b[0]) * (a[1] - b[1]) - (c[1] - b[1]) * (a[0] - b[0]) > 0;
    }

    private int findLeftEdge(TreeMap<Float, Integer> tree, float[] p) {
        Float key = tree.floorKey(p[0]);
        return key != null ? tree.get(key) : -1;
    }

    private void handleRegularVertex(TreeMap<Float, Integer> tree, Map<Integer, Integer> helper, int idx, List<float[]> v, List<int[]> diagonals) {
        int left = findLeftEdge(tree, v.get(idx));
        if (left != -1) {
            diagonals.add(new int[]{idx, helper.get(left)});
            info(getClass(), "Regular vertex diagonal: " + idx + " - " + helper.get(left));
        }
    }

    private List<int[]> triangulateMonotonePolygon(List<Integer> mono, List<float[]> vertices) {
        List<int[]> triangles = new ArrayList<>();
        if (mono.size() < 3) {
            return triangles;
        }

        Stack<Integer> stack = new Stack<>();
        stack.push(mono.get(0));
        stack.push(mono.get(1));
        for (int i = 2; i < mono.size(); i++) {
            int curr = mono.get(i);
            int top = stack.peek();
            int last = stack.pop();
            while (!stack.isEmpty() && isCCW(vertices.get(stack.peek()), vertices.get(last), vertices.get(curr))) {
                triangles.add(new int[]{stack.peek(), last, curr});
                last = stack.pop();
            }

            stack.push(last);
            stack.push(curr);
        }

        info(getClass(), "Monotone polygon triangles: " + Arrays.deepToString(triangles.toArray()));

        return triangles;
    }

    private boolean isCCW(float[] a, float[] b, float[] c) {
        return (b[0] - a[0]) * (c[1] - a[1]) - (b[1] - a[1]) * (c[0] - a[0]) > 0;
    }

    public TestElementPair getResult() {
        return result;
    }

    private void info(Class<?> clazz, String text) {
        if (!DEBUG) {
            return;
        }

        DebugLog.info(clazz, text);
    }

    private enum VertexType {START, END, SPLIT, MERGE, REGULAR}
}
