package me.hannsi.lfjg.testRender.system.mesh;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.testRender.debug.exceptions.render.mesh.PolygonTriangulatorException;
import me.hannsi.lfjg.testRender.renderers.JointType;
import me.hannsi.lfjg.testRender.renderers.PaintType;
import me.hannsi.lfjg.testRender.renderers.PointType;
import me.hannsi.lfjg.testRender.system.mesh.triangulator.Triangle;
import me.hannsi.lfjg.testRender.system.mesh.triangulator.Triangulator;
import me.hannsi.lfjg.testRender.system.rendering.DrawType;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.hannsi.lfjg.core.utils.math.MathHelper.*;
import static me.hannsi.lfjg.testRender.RenderSystemSetting.POLYGON_TRIANGULATOR_MITER_LIMIT;
import static me.hannsi.lfjg.testRender.RenderSystemSetting.POLYGON_TRIANGULATOR_TOLERANCE;

public class Test2PolygonTriangulator {
    private Vertex[] vertices;
    private ProjectionType projectionType;
    private DrawType drawType;
    private PaintType paintType;
    private JointType strokeJointType;
    private PointType pointType;
    private float strokeWidth;
    private float pointSize;
    private TestElementPair result;

    Test2PolygonTriangulator() {
    }

    public static Test2PolygonTriangulator createPolygonTriangulator() {
        return new Test2PolygonTriangulator();
    }

    public Test2PolygonTriangulator vertices(Vertex... vertices) {
        this.vertices = vertices;
        return this;
    }

    public Test2PolygonTriangulator projectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;
        return this;
    }

    public Test2PolygonTriangulator drawType(DrawType drawType) {
        this.drawType = drawType;
        return this;
    }

    public Test2PolygonTriangulator paintType(PaintType paintType) {
        this.paintType = paintType;
        return this;
    }

    public Test2PolygonTriangulator strokeJointType(JointType strokeJointType) {
        this.strokeJointType = strokeJointType;
        return this;
    }

    public Test2PolygonTriangulator pointType(PointType pointType) {
        this.pointType = pointType;
        return this;
    }

    public Test2PolygonTriangulator strokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public Test2PolygonTriangulator pointSize(float pointSize) {
        this.pointSize = pointSize;
        return this;
    }

    public Test2PolygonTriangulator process() {
        try {
            result = processTriangulator();
        } catch (Exception e) {
            DebugLog.error(getClass(), e);
            throw e;
        }

        return this;
    }

    private TestElementPair processTriangulator() throws IllegalStateException, PolygonTriangulatorException {
        List<Vertex> vertexList = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();

        if (projectionType == ProjectionType.ORTHOGRAPHIC_PROJECTION) {
            if (vertices == null) {
                throw new NullPointerException("Vertices are null.");
            }
            if (vertices.length == 0) {
                throw new IllegalStateException("Vertices are empty.");
            }

            switch (drawType) {
                case POINTS ->
                        createPointsTriangulator(vertexList, indexList, vertices);
                case LINES -> {
                    checkLineParameter(drawType);
                    if ((vertices.length % 2) != 0) {
                        throw new PolygonTriangulatorException("DrawType.LINES requires an even number of vertices. Vertices are interpreted as pairs, but current count is " + vertices.length);
                    }

                    createLinesTriangulator(vertexList, indexList, vertices);
                }
                case LINE_STRIP -> {
                    checkLineParameter(drawType);

                    createLineStripTriangulator(vertexList, indexList, vertices);
                }
                case LINE_LOOP -> {
                    checkLineParameter(drawType);

                    createLineLoopTriangulator(vertexList, indexList, true, vertices);
                }
                case TRIANGLES -> {
                    checkTriangleParameter(drawType);
                    if ((vertices.length % 3) != 0) {
                        throw new PolygonTriangulatorException("DrawType.TRIANGLES requires an even number of vertices. Vertices are interpreted as pairs, but current count is " + vertices.length);
                    }
                    createTrianglesTriangulator(vertexList, indexList, vertices);
                }
                case TRIANGLE_STRIP -> {
                    checkTriangleParameter(drawType);

                    createTriangleStripTriangulator(vertexList, indexList, vertices);
                }
                case TRIANGLE_FAN -> {
                    checkTriangleParameter(drawType);

                    createTriangleFanTriangulator(vertexList, indexList, vertices);
                }
                case QUADS -> {
                    checkQuadParameter(drawType);
                    if ((vertices.length % 4) != 0) {
                        throw new PolygonTriangulatorException("DrawType.QUADS requires an even number of vertices. Vertices are interpreted as pairs, but current count is " + vertices.length);
                    }
                    createQuadsTriangulator(vertexList, indexList, vertices);
                }
                case POLYGON ->
                        createPolygonTriangulator(vertexList, indexList, true, vertices);
                default ->
                        throw new IllegalStateException("Unexpected value: " + drawType);
            }
        } else {
            throw new PolygonTriangulatorException("This projection type is not supported. ProjectionType: " + projectionType.getName());
        }

        return createElementPair(vertexList, indexList);
    }

    private void createTriangleFanTriangulator(List<Vertex> vertexList, List<Integer> indexList, Vertex[] vertices) {
        Vertex center = vertices[0];
        Vertex v1 = vertices[1];
        Vertex v2 = null;

        for (int i = 1; i < vertices.length; i++) {
            Vertex vertex = vertices[i];

            if (v2 != null) {
                v1 = v2;
            }
            v2 = vertex;

            switch (paintType) {
                case FILL ->
                        createPolygonTriangulator(vertexList, indexList, false, center, v1, v2);
                case STROKE ->
                        createLineLoopTriangulator(vertexList, indexList, false, center, v1, v2);
                default ->
                        throw new IllegalStateException("Unexpected value: " + paintType);
            }
        }
    }

    private void createTriangleStripTriangulator(List<Vertex> vertexList, List<Integer> indexList, Vertex[] vertices) {
        Vertex v1 = vertices[0];
        Vertex v2 = vertices[1];
        Vertex v3 = null;
        for (int i = 2; i < vertices.length; i++) {
            Vertex vertex = vertices[i];

            if (v3 != null) {
                v1 = v2;
                v2 = v3;
            }
            v3 = vertex;

            switch (paintType) {
                case FILL ->
                        createPolygonTriangulator(vertexList, indexList, false, v1, v2, v3);
                case STROKE ->
                        createLineLoopTriangulator(vertexList, indexList, false, v1, v2, v3);
                default ->
                        throw new IllegalStateException("Unexpected value: " + paintType);
            }
        }
    }

    private void createQuadsTriangulator(List<Vertex> vertexList, List<Integer> indexList, Vertex[] vertices) {
        for (int i = 0; i < vertices.length; i += 4) {
            switch (paintType) {
                case FILL ->
                        createPolygonTriangulator(vertexList, indexList, false, vertices[i], vertices[i + 1], vertices[i + 2], vertices[i + 3]);
                case STROKE ->
                        createLineLoopTriangulator(vertexList, indexList, false, vertices[i], vertices[i + 1], vertices[i + 2], vertices[i + 3]);
                default ->
                        throw new IllegalStateException("Unexpected value: " + paintType);
            }
        }
    }

    private void createTrianglesTriangulator(List<Vertex> vertexList, List<Integer> indexList, Vertex[] vertices) {
        for (int i = 0; i < vertices.length; i += 3) {
            switch (paintType) {
                case FILL ->
                        createPolygonTriangulator(vertexList, indexList, false, vertices[i], vertices[i + 1], vertices[i + 2]);
                case STROKE ->
                        createLineLoopTriangulator(vertexList, indexList, false, vertices[i], vertices[i + 1], vertices[i + 2]);
                default ->
                        throw new IllegalStateException("Unexpected value: " + paintType);
            }
        }
    }

    private void createLineLoopTriangulator(List<Vertex> vertexList, List<Integer> indexList, boolean useNullVertex, Vertex... vertices) {
        List<List<Vertex>> numPolygonVertices = new ArrayList<>();
        List<Vertex> numVertices = new ArrayList<>();
        if (useNullVertex) {
            for (Vertex vertex : vertices) {
                if (vertex.isNullVertex()) {
                    if (numVertices.size() < 3) {
                        throw new PolygonTriangulatorException("DrawType.LINE_LOOP requires at least three vertices. but current count is " + numVertices.size() + ". Polygon count is " + numPolygonVertices.size());
                    }

                    numPolygonVertices.add(new ArrayList<>(numVertices));
                    numVertices.clear();

                    continue;
                }

                numVertices.add(vertex.copy());
            }
        } else {
            numPolygonVertices.add(List.of(vertices));
        }

        for (List<Vertex> numPolygonVertex : numPolygonVertices) {
            int n = numPolygonVertex.size();

            float[] nx = new float[n];
            float[] ny = new float[n];
            for (int i = 0; i < n; i++) {
                Vertex a = getVertex(numPolygonVertex, i);
                Vertex b = getVertex(numPolygonVertex, i + 1);
                float dx = b.x - a.x;
                float dy = b.y - a.y;
                float len = sqrt(dx * dx + dy * dy);
                nx[i] = -dy / len * (strokeWidth / 2f);
                ny[i] = dx / len * (strokeWidth / 2f);
            }

            Vector2f[] joints = new Vector2f[n];
            float[] signs = new float[n];
            for (int j = 0; j < n; j++) {
                Vertex jPrev = getVertex(numPolygonVertex, j - 1);
                Vertex jCurrent = getVertex(numPolygonVertex, j);
                Vertex jNext = getVertex(numPolygonVertex, j + 1);
                int jIP = getNormalIndex(n, j - 1);
                int jIC = getNormalIndex(n, j);

                float c = cross(jPrev, jCurrent, jNext);
                float d = dot(jPrev, jCurrent, jNext);
                float s = (atan2(c, d) < 0) ? -1f : 1f;
                signs[j] = s;

                Vector2f o1 = new Vector2f(nx[jIP] * -s, ny[jIP] * -s);
                Vector2f o2 = new Vector2f(nx[jIC] * -s, ny[jIC] * -s);

                Vector2f lp1 = new Vector2f(jPrev.x + o1.x, jPrev.y + o1.y);
                Vector2f lp2 = new Vector2f(jCurrent.x + o1.x, jCurrent.y + o1.y);
                Vector2f lp3 = new Vector2f(jCurrent.x + o2.x, jCurrent.y + o2.y);
                Vector2f lp4 = new Vector2f(jNext.x + o2.x, jNext.y + o2.y);

                joints[j] = computeLineIntersection(lp1, lp2, lp3, lp4);
            }

            for (int i = 0; i < n; i++) {
                Vertex prev = getVertex(numPolygonVertex, i - 1);
                Vertex current = getVertex(numPolygonVertex, i);
                Vertex next = getVertex(numPolygonVertex, i + 1);

                int iPrev = getNormalIndex(numPolygonVertex.size(), i - 1);
                int iCurrent = getNormalIndex(numPolygonVertex.size(), i);
                int iNext = getNormalIndex(numPolygonVertex.size(), i + 1);

                JointType jointType = strokeJointType;
                if (jointType == JointType.MITER) {
                    Vector2f joint = joints[iCurrent];
                    float jx = joint.x - current.x;
                    float jy = joint.y - current.y;
                    float miterLength = sqrt(jx * jx + jy * jy) * 2f;
                    if (miterLength / strokeWidth > POLYGON_TRIANGULATOR_MITER_LIMIT) {
                        jointType = JointType.BEVEL;
                    }
                }

                float[] miter1 = calcMiter(nx[iPrev], ny[iPrev], nx[i], ny[i]);
                float[] miter2 = calcMiter(nx[i], ny[i], nx[iNext], ny[iNext]);

                Vertex p1 = current.copy().moveXYZ(miter1[0], miter1[1], current.z);
                Vertex p2 = current.copy().moveXYZ(-miter1[0], -miter1[1], current.z);
                Vertex p3 = next.copy().moveXYZ(-miter2[0], -miter2[1], next.z);
                Vertex p4 = next.copy().moveXYZ(miter2[0], miter2[1], next.z);
                Vertex v1 = current.copy().moveXYZ(nx[iCurrent], ny[iCurrent], current.z);
                Vertex v2 = current.copy().moveXYZ(-nx[iCurrent], -ny[iCurrent], current.z);
                Vertex v3 = next.copy().moveXYZ(-nx[iCurrent], -ny[iCurrent], next.z);
                Vertex v4 = next.copy().moveXYZ(nx[iCurrent], ny[iCurrent], next.z);
                Vertex v5 = current.copy().moveXYZ(-nx[iPrev], -ny[iPrev], prev.z);
                Vertex v6 = current.copy().moveXYZ(nx[iPrev], ny[iPrev], prev.z);
                if (jointType != JointType.NONE) {
                    List<Vertex> linePolygon = new ArrayList<>();
                    if (signs[iCurrent] == -1f) {
                        linePolygon.add(v1.copy());
                        linePolygon.add(current.copy());
                        linePolygon.add(p2.copy());
                    } else {
                        linePolygon.add(p1.copy());
                        linePolygon.add(current.copy());
                        linePolygon.add(v2.copy());
                    }

                    if (signs[iNext] == -1f) {
                        linePolygon.add(p3.copy());
                        linePolygon.add(next.copy());
                        linePolygon.add(v4.copy());
                    } else {
                        linePolygon.add(v3.copy());
                        linePolygon.add(next.copy());
                        linePolygon.add(p4.copy());
                    }

                    createPolygonTriangulator(vertexList, indexList, false, linePolygon.toArray(new Vertex[0]));
                }

                List<Vertex> jointPolygon = new ArrayList<>();
                switch (jointType) {
                    case NONE ->
                            createPolygonTriangulator(vertexList, indexList, false, List.of(
                                    current.copy().moveXYZ(nx[i], ny[i], current.z),
                                    current.copy().moveXYZ(-nx[i], -ny[i], current.z),
                                    next.copy().moveXYZ(-nx[i], -ny[i], next.z),
                                    next.copy().moveXYZ(nx[i], ny[i], next.z)
                            ).toArray(new Vertex[0]));
                    case MITER -> {
                        Vertex baseMiter1;
                        Vertex baseMiter2;
                        jointPolygon.add(current.copy());
                        if (signs[iCurrent] == 1f) {
                            baseMiter1 = v2.copy();
                            jointPolygon.add(baseMiter1);
                        } else {
                            baseMiter1 = v1.copy();
                            jointPolygon.add(baseMiter1);
                        }
                        if (signs[iCurrent] == 1f) {
                            baseMiter2 = v5.copy();
                            jointPolygon.add(baseMiter2);
                        } else {
                            baseMiter2 = v6.copy();
                            jointPolygon.add(baseMiter2);
                        }
                        createPolygonTriangulator(vertexList, indexList, false, jointPolygon.toArray(new Vertex[0]));

                        Vector2f joint = joints[iCurrent];
                        if (joint != null) {
                            createPolygonTriangulator(
                                    vertexList,
                                    indexList,
                                    false,
                                    List.of(
                                            baseMiter1.copy(),
                                            baseMiter2.copy(),
                                            current.copy().replaceXYZ(joint.x, joint.y, current.z)
                                    ).toArray(new Vertex[0])
                            );
                        }
                    }
                    case BEVEL -> {
                        jointPolygon.add(current.copy());
                        if (signs[iCurrent] == 1f) {
                            jointPolygon.add(v2.copy());
                        } else {
                            jointPolygon.add(v1.copy());
                        }
                        if (signs[iCurrent] == 1f) {
                            jointPolygon.add(v5.copy());
                        } else {
                            jointPolygon.add(v6.copy());
                        }

                        createPolygonTriangulator(vertexList, indexList, false, jointPolygon.toArray(new Vertex[0]));
                    }
                    case ROUND, ROUND_START, ROUND_END, ROUND_START_END -> {
                        Vertex baseMiter1;
                        Vertex baseMiter2;
                        Vertex center = current.copy();
                        if (signs[iCurrent] == 1f) {
                            baseMiter2 = v2.copy();
                            baseMiter1 = v5.copy();
                        } else {
                            baseMiter1 = v1.copy();
                            baseMiter2 = v6.copy();
                        }

                        float startAngle = atan2(baseMiter1.y - center.y, baseMiter1.x - center.x);
                        float endAngle = atan2(baseMiter2.y - center.y, baseMiter2.x - center.x);

                        float startDegrees = toDegrees(startAngle);
                        float endDegrees = toDegrees(endAngle);
                        float normalizedEnd = endDegrees;
                        while (normalizedEnd < startDegrees) {
                            normalizedEnd += 360f;
                        }

                        float spanAngle = normalizedEnd - startDegrees;
                        int fullSegments = calculateSegmentCount(strokeWidth);
                        int segmentCount = max(1, round(fullSegments * (spanAngle / 360f)));
                        makeCircle(
                                vertexList,
                                indexList,
                                center.copy(),
                                startDegrees,
                                endDegrees,
                                strokeWidth / 2f,
                                strokeWidth / 2f,
                                segmentCount,
                                false,
                                Color.of(current.getColors())
                        );
                    }
                    default ->
                            throw new IllegalStateException("Unexpected value: " + jointType);
                }
            }
        }
    }

    private void createLineStripTriangulator(List<Vertex> vertexList, List<Integer> indexList, Vertex... vertices) {
        int n = vertices.length;

        float[] nx = new float[n];
        float[] ny = new float[n];
        for (int i = 0; i < n; i++) {
            Vertex a = getVertex(vertices, i);
            Vertex b = getVertex(vertices, i + 1);
            float dx = b.x - a.x;
            float dy = b.y - a.y;
            float len = sqrt(dx * dx + dy * dy);
            nx[i] = -dy / len * (strokeWidth / 2f);
            ny[i] = dx / len * (strokeWidth / 2f);
        }

        Vector2f[] joints = new Vector2f[n];
        float[] signs = new float[n];
        for (int j = 0; j < n; j++) {
            Vertex jPrev = getVertex(vertices, j - 1);
            Vertex jCurrent = getVertex(vertices, j);
            Vertex jNext = getVertex(vertices, j + 1);
            int jIP = getNormalIndex(n, j - 1);
            int jIC = getNormalIndex(n, j);

            float c = cross(jPrev, jCurrent, jNext);
            float d = dot(jPrev, jCurrent, jNext);
            float s = (atan2(c, d) < 0) ? -1f : 1f;
            signs[j] = s;

            Vector2f o1 = new Vector2f(nx[jIP] * -s, ny[jIP] * -s);
            Vector2f o2 = new Vector2f(nx[jIC] * -s, ny[jIC] * -s);

            Vector2f lp1 = new Vector2f(jPrev.x + o1.x, jPrev.y + o1.y);
            Vector2f lp2 = new Vector2f(jCurrent.x + o1.x, jCurrent.y + o1.y);
            Vector2f lp3 = new Vector2f(jCurrent.x + o2.x, jCurrent.y + o2.y);
            Vector2f lp4 = new Vector2f(jNext.x + o2.x, jNext.y + o2.y);

            joints[j] = computeLineIntersection(lp1, lp2, lp3, lp4);
        }

        for (int i = 0; i < n - 1; i++) {
            Vertex prev = getVertex(vertices, i - 1);
            Vertex current = getVertex(vertices, i);
            Vertex next = getVertex(vertices, i + 1);

            int iPrev = getNormalIndex(vertices.length, i - 1);
            int iCurrent = getNormalIndex(vertices.length, i);
            int iNext = getNormalIndex(vertices.length, i + 1);

            JointType jointType = strokeJointType;
            if (jointType == JointType.MITER) {
                Vector2f joint = joints[iCurrent];
                float jx = joint.x - current.x;
                float jy = joint.y - current.y;
                float miterLength = sqrt(jx * jx + jy * jy) * 2f;
                if (miterLength / strokeWidth > POLYGON_TRIANGULATOR_MITER_LIMIT) {
                    jointType = JointType.BEVEL;
                }
            }

            float[] miter1 = calcMiter(nx[iPrev], ny[iPrev], nx[i], ny[i]);
            float[] miter2 = calcMiter(nx[i], ny[i], nx[iNext], ny[iNext]);

            Vertex p1 = current.copy().moveXYZ(miter1[0], miter1[1], current.z);
            Vertex p2 = current.copy().moveXYZ(-miter1[0], -miter1[1], current.z);
            Vertex p3 = next.copy().moveXYZ(-miter2[0], -miter2[1], next.z);
            Vertex p4 = next.copy().moveXYZ(miter2[0], miter2[1], next.z);
            Vertex v1 = current.copy().moveXYZ(nx[iCurrent], ny[iCurrent], current.z);
            Vertex v2 = current.copy().moveXYZ(-nx[iCurrent], -ny[iCurrent], current.z);
            Vertex v3 = next.copy().moveXYZ(-nx[iCurrent], -ny[iCurrent], next.z);
            Vertex v4 = next.copy().moveXYZ(nx[iCurrent], ny[iCurrent], next.z);
            Vertex v5 = current.copy().moveXYZ(-nx[iPrev], -ny[iPrev], prev.z);
            Vertex v6 = current.copy().moveXYZ(nx[iPrev], ny[iPrev], prev.z);
            if (jointType != JointType.NONE) {
                List<Vertex> linePolygon = new ArrayList<>();
                if (i == 0) {
                    linePolygon.add(v1.copy());
                    linePolygon.add(v2.copy());
                } else {
                    if (signs[iCurrent] == -1f) {
                        linePolygon.add(v1.copy());
                        linePolygon.add(current.copy());
                        linePolygon.add(p2.copy());
                    } else {
                        linePolygon.add(p1.copy());
                        linePolygon.add(current.copy());
                        linePolygon.add(v2.copy());
                    }
                }

                if (i == n - 2) {
                    linePolygon.add(v3.copy());
                    linePolygon.add(v4.copy());
                } else {
                    if (signs[iNext] == -1f) {
                        linePolygon.add(p3.copy());
                        linePolygon.add(next.copy());
                        linePolygon.add(v4.copy());
                    } else {
                        linePolygon.add(v3.copy());
                        linePolygon.add(next.copy());
                        linePolygon.add(p4.copy());
                    }
                }

                createPolygonTriangulator(vertexList, indexList, false, linePolygon.toArray(new Vertex[0]));
            }

            List<Vertex> jointPolygon = new ArrayList<>();
            if (i == 0 && (jointType != JointType.ROUND && jointType != JointType.ROUND_START && jointType != JointType.ROUND_END && jointType != JointType.ROUND_START_END)) {
                continue;
            }
            switch (jointType) {
                case NONE ->
                        createPolygonTriangulator(vertexList, indexList, false, List.of(
                                current.copy().moveXYZ(nx[i], ny[i], current.z),
                                current.copy().moveXYZ(-nx[i], -ny[i], current.z),
                                next.copy().moveXYZ(-nx[i], -ny[i], next.z),
                                next.copy().moveXYZ(nx[i], ny[i], next.z)
                        ).toArray(new Vertex[0]));
                case MITER -> {
                    Vertex baseMiter1;
                    Vertex baseMiter2;
                    jointPolygon.add(current.copy());
                    if (signs[iCurrent] == 1f) {
                        baseMiter1 = v2.copy();
                        jointPolygon.add(baseMiter1);
                    } else {
                        baseMiter1 = v1.copy();
                        jointPolygon.add(baseMiter1);
                    }
                    if (signs[iCurrent] == 1f) {
                        baseMiter2 = v5.copy();
                        jointPolygon.add(baseMiter2);
                    } else {
                        baseMiter2 = v6.copy();
                        jointPolygon.add(baseMiter2);
                    }
                    createPolygonTriangulator(vertexList, indexList, false, jointPolygon.toArray(new Vertex[0]));

                    Vector2f joint = joints[iCurrent];
                    if (joint != null) {
                        createPolygonTriangulator(
                                vertexList,
                                indexList,
                                false,
                                List.of(
                                        baseMiter1.copy(),
                                        baseMiter2.copy(),
                                        current.copy().replaceXYZ(joint.x, joint.y, current.z)
                                ).toArray(new Vertex[0])
                        );
                    }
                }
                case BEVEL -> {
                    jointPolygon.add(current.copy());
                    if (signs[iCurrent] == 1f) {
                        jointPolygon.add(v2.copy());
                    } else {
                        jointPolygon.add(v1.copy());
                    }
                    if (signs[iCurrent] == 1f) {
                        jointPolygon.add(v5.copy());
                    } else {
                        jointPolygon.add(v6.copy());
                    }

                    createPolygonTriangulator(vertexList, indexList, false, jointPolygon.toArray(new Vertex[0]));
                }
                case ROUND, ROUND_START, ROUND_END, ROUND_START_END -> {
                    if (i == 0 && (jointType == JointType.ROUND_START || jointType == JointType.ROUND_START_END)) {
                        Vertex center = current.copy();

                        float startAngle = atan2(v1.y - center.y, v1.x - center.x);
                        float endAngle = atan2(v2.y - center.y, v2.x - center.x);

                        float startDegrees = toDegrees(startAngle);
                        float endDegrees = toDegrees(endAngle);
                        float normalizedEnd = endDegrees;
                        while (normalizedEnd < startDegrees) {
                            normalizedEnd += 360f;
                        }

                        float spanAngle = normalizedEnd - startDegrees;
                        int fullSegments = calculateSegmentCount(strokeWidth);
                        int segmentCount = max(1, round(fullSegments * (spanAngle / 360f)));
                        makeCircle(
                                vertexList,
                                indexList,
                                center.copy(),
                                startDegrees,
                                endDegrees,
                                strokeWidth / 2f,
                                strokeWidth / 2f,
                                segmentCount,
                                false,
                                Color.of(center.getColors())
                        );
                    }
                    if (i != 0) {
                        Vertex baseMiter1;
                        Vertex baseMiter2;
                        Vertex center = current.copy();
                        if (signs[iCurrent] == 1f) {
                            baseMiter2 = v2.copy();
                            baseMiter1 = v5.copy();
                        } else {
                            baseMiter1 = v1.copy();
                            baseMiter2 = v6.copy();
                        }

                        float startAngle = atan2(baseMiter1.y - center.y, baseMiter1.x - center.x);
                        float endAngle = atan2(baseMiter2.y - center.y, baseMiter2.x - center.x);

                        float startDegrees = toDegrees(startAngle);
                        float endDegrees = toDegrees(endAngle);
                        float normalizedEnd = endDegrees;
                        while (normalizedEnd < startDegrees) {
                            normalizedEnd += 360f;
                        }

                        float spanAngle = normalizedEnd - startDegrees;
                        int fullSegments = calculateSegmentCount(strokeWidth);
                        int segmentCount = max(1, round(fullSegments * (spanAngle / 360f)));
                        makeCircle(
                                vertexList,
                                indexList,
                                center.copy(),
                                startDegrees,
                                endDegrees,
                                strokeWidth / 2f,
                                strokeWidth / 2f,
                                segmentCount,
                                false,
                                Color.of(current.getColors())
                        );
                    }
                    if (i == n - 2 && (jointType == JointType.ROUND_END || jointType == JointType.ROUND_START_END)) {
                        Vertex center = next.copy();

                        float startAngle = atan2(v3.y - center.y, v3.x - center.x);
                        float endAngle = atan2(v4.y - center.y, v4.x - center.x);

                        float startDegrees = toDegrees(startAngle);
                        float endDegrees = toDegrees(endAngle);
                        float normalizedEnd = endDegrees;
                        while (normalizedEnd < startDegrees) {
                            normalizedEnd += 360f;
                        }

                        float spanAngle = normalizedEnd - startDegrees;
                        int fullSegments = calculateSegmentCount(strokeWidth);
                        int segmentCount = max(1, round(fullSegments * (spanAngle / 360f)));
                        makeCircle(
                                vertexList,
                                indexList,
                                center.copy(),
                                startDegrees,
                                endDegrees,
                                strokeWidth / 2f,
                                strokeWidth / 2f,
                                segmentCount,
                                false,
                                Color.of(center.getColors())
                        );
                    }
                }
                default ->
                        throw new IllegalStateException("Unexpected value: " + jointType);
            }
        }
    }

    private void createLinesTriangulator(List<Vertex> vertexList, List<Integer> indexList, Vertex... vertices) {
        int n = vertices.length;

        float[] nx = new float[n];
        float[] ny = new float[n];
        for (int i = 0; i < n; i++) {
            Vertex a = getVertex(vertices, i);
            Vertex b = getVertex(vertices, i + 1);
            float dx = b.x - a.x;
            float dy = b.y - a.y;
            float len = sqrt(dx * dx + dy * dy);
            nx[i] = -dy / len * (strokeWidth / 2f);
            ny[i] = dx / len * (strokeWidth / 2f);
        }

        for (int i = 0; i < n; i += 2) {
            Vertex current = getVertex(vertices, i);
            Vertex next = getVertex(vertices, i + 1);

            Vertex v1 = current.copy().moveXYZ(nx[i], ny[i], current.z);
            Vertex v2 = current.copy().moveXYZ(-nx[i], -ny[i], current.z);
            Vertex v3 = next.copy().moveXYZ(-nx[i], -ny[i], next.z);
            Vertex v4 = next.copy().moveXYZ(nx[i], ny[i], next.z);

            if (strokeJointType == JointType.ROUND_START || strokeJointType == JointType.ROUND_START_END) {
                Vertex center = current.copy();

                float startAngle = atan2(v1.y - center.y, v1.x - center.x);
                float endAngle = atan2(v2.y - center.y, v2.x - center.x);

                float startDegrees = toDegrees(startAngle);
                float endDegrees = toDegrees(endAngle);
                float normalizedEnd = endDegrees;
                while (normalizedEnd < startDegrees) {
                    normalizedEnd += 360f;
                }

                float spanAngle = normalizedEnd - startDegrees;
                int fullSegments = calculateSegmentCount(strokeWidth);
                int segmentCount = max(1, round(fullSegments * (spanAngle / 360f)));
                makeCircle(
                        vertexList,
                        indexList,
                        center.copy(),
                        startDegrees,
                        endDegrees,
                        strokeWidth / 2f,
                        strokeWidth / 2f,
                        segmentCount,
                        false,
                        Color.of(center.getColors())
                );
            }

            createPolygonTriangulator(vertexList, indexList, false, List.of(
                    current.copy().moveXYZ(nx[i], ny[i], current.z),
                    current.copy().moveXYZ(-nx[i], -ny[i], current.z),
                    next.copy().moveXYZ(-nx[i], -ny[i], next.z),
                    next.copy().moveXYZ(nx[i], ny[i], next.z)
            ).toArray(new Vertex[0]));

            if (strokeJointType == JointType.ROUND_END || strokeJointType == JointType.ROUND_START_END) {
                Vertex center = next.copy();

                float startAngle = atan2(v3.y - center.y, v3.x - center.x);
                float endAngle = atan2(v4.y - center.y, v4.x - center.x);

                float startDegrees = toDegrees(startAngle);
                float endDegrees = toDegrees(endAngle);
                float normalizedEnd = endDegrees;
                while (normalizedEnd < startDegrees) {
                    normalizedEnd += 360f;
                }

                float spanAngle = normalizedEnd - startDegrees;
                int fullSegments = calculateSegmentCount(strokeWidth);
                int segmentCount = max(1, round(fullSegments * (spanAngle / 360f)));
                makeCircle(
                        vertexList,
                        indexList,
                        center.copy(),
                        startDegrees,
                        endDegrees,
                        strokeWidth / 2f,
                        strokeWidth / 2f,
                        segmentCount,
                        false,
                        Color.of(center.getColors())
                );
            }
        }
    }

    private Vector2f computeLineIntersection(Vector2f p1, Vector2f p2, Vector2f p3, Vector2f p4) {
        float x1 = p1.x;
        float y1 = p1.y;
        float x2 = p2.x;
        float y2 = p2.y;
        float x3 = p3.x;
        float y3 = p3.y;
        float x4 = p4.x;
        float y4 = p4.y;

        float denom = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (abs(denom) < 1e-6f) {
            return null;
        }

        float t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denom;

        float ix = x1 + t * (x2 - x1);
        float iy = y1 + t * (y2 - y1);

        return new Vector2f(ix, iy);
    }

    private float cross(Vertex p1, Vertex p2, Vertex p3) {
        float dx1 = p2.x - p1.x;
        float dy1 = p2.y - p1.y;
        float dx2 = p3.x - p2.x;
        float dy2 = p3.y - p2.y;
        return dx1 * dy2 - dy1 * dx2;
    }

    private float dot(Vertex p1, Vertex p2, Vertex p3) {
        float dx1 = p2.x - p1.x;
        float dy1 = p2.y - p1.y;
        float dx2 = p3.x - p2.x;
        float dy2 = p3.y - p2.y;
        return dx1 * dx2 + dy1 * dy2;
    }

    private Vector2f lineSegmentIntersection(Vector2f p1, Vector2f p2, Vector2f p3, Vector2f p4) {
        float f1x = p2.x - p1.x;
        float f1y = p2.y - p1.y;
        float f2x = p4.x - p3.x;
        float f2y = p4.y - p3.y;

        float denom = f1x * f2y - f1y * f2x;
        if (abs(denom) < 1e-10) {
            return null;
        }

        float dx = p3.x - p1.x;
        float dy = p3.y - p1.y;
        float t = (dx * f2y - dy * f2x) / denom;
        float u = (dx * f1y - dy * f1x) / denom;

        if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
            return new Vector2f(p1.x + t * f1x, p1.y + t * f1y);
        }
        return null;
    }

    private List<Vector2f[]> getEdges(Vertex[] quad) {
        List<Vector2f[]> edges = new ArrayList<>();
        int n = quad.length;
        for (int i = 0; i < n; i++) {
            edges.add(new Vector2f[]{quad[i].toVector2f(), quad[i + 1 % n].toVector2f()});
        }
        return edges;
    }

    private List<Vector2f> getIntersectionPoints(Vertex[] quad1, Vertex[] quad2) {
        List<Vector2f> intersections = new ArrayList<>();

        for (Vector2f[] e1 : getEdges(quad1)) {
            for (Vector2f[] e2 : getEdges(quad2)) {
                Vector2f p = lineSegmentIntersection(e1[0], e1[1], e2[0], e2[1]);
                if (p != null) {
                    intersections.add(p);
                }
            }
        }
        return intersections;
    }

    private float[] calcMiter(float nx1, float ny1, float nx2, float ny2) {
        float mx = nx1 + nx2;
        float my = ny1 + ny2;
        float len = sqrt(mx * mx + my * my);
        if (len < 0.0001f) {
            return new float[]{nx1, ny1};
        }
        mx /= len;
        my /= len;
        float dot = mx * nx1 / (strokeWidth / 2f) + my * ny1 / (strokeWidth / 2f);
        float scale = 1f / (max(dot, 0.0001f));
        scale = min(scale, POLYGON_TRIANGULATOR_MITER_LIMIT);
        return new float[]{mx * (strokeWidth / 2f) * scale, my * (strokeWidth / 2f) * scale};
    }

    private Vertex getVertex(Vertex[] vertices, int index) {
        return vertices[((index % vertices.length) + vertices.length) % vertices.length];
    }

    private Vertex getVertex(List<Vertex> vertices, int index) {
        return vertices.get(((index % vertices.size()) + vertices.size()) % vertices.size());
    }

    private int getNormalIndex(int length, int index) {
        return ((index % length) + length) % length;
    }

    private void checkQuadParameter(DrawType drawType) {
        if (vertices.length < 4) {
            throw new PolygonTriangulatorException(drawType.getName() + " requires at least 4 vertices. Current count: " + vertices.length);
        }
    }

    private void checkTriangleParameter(DrawType drawType) {
        if (vertices.length < 3) {
            throw new PolygonTriangulatorException(drawType.getName() + " requires at least 3 vertices. Current count: " + vertices.length);
        }
    }

    private void checkLineParameter(DrawType drawType) {
        if (strokeWidth < 0) {
            throw new PolygonTriangulatorException("Line width must be greater than or equal to 0. Current value: " + strokeWidth);
        }
        if (strokeJointType == null) {
            throw new PolygonTriangulatorException("LineJointType is not set. Please specify a valid LineJointType before triangulation.");
        }
        if (vertices.length < 2) {
            throw new PolygonTriangulatorException(drawType.getName() + " requires at least 2 vertices. Current count: " + vertices.length);
        }
    }


    private void createPolygonTriangulator(List<Vertex> vertexList, List<Integer> indexList, boolean useNullVertex, Vertex... vertices) {
        List<List<Vertex>> numPolygonVertices = new ArrayList<>();
        List<Vertex> numVertices = new ArrayList<>();
        if (useNullVertex) {
            for (Vertex vertex : vertices) {
                if (vertex.isNullVertex()) {
                    if (numVertices.size() < 3) {
                        throw new PolygonTriangulatorException("DrawType.LINE_LOOP requires at least three vertices. but current count is " + numVertices.size() + ". Polygon count is " + numPolygonVertices.size());
                    }

                    numPolygonVertices.add(new ArrayList<>(numVertices));
                    numVertices.clear();

                    continue;
                }

                numVertices.add(vertex.copy());
            }
        } else {
            numPolygonVertices.add(List.of(vertices));
        }


        for (List<Vertex> numPolygonVertex : numPolygonVertices) {
            Triangulator triangulator = new Triangulator(numPolygonVertex);
            List<Triangle> result = triangulator.triangulate();
            for (Triangle t : result) {
                int base = vertexList.size();
                vertexList.add(t.a);
                vertexList.add(t.b);
                vertexList.add(t.c);

                indexList.add(base);
                indexList.add(base + 1);
                indexList.add(base + 2);
            }
        }
    }

    private void createPointsTriangulator(List<Vertex> vertexList, List<Integer> indexList, Vertex... vertices) throws IllegalStateException {
        switch (pointType) {
            case SQUARE -> {
                for (Vertex vertex : vertices) {
                    float offset = sqrt(pow(pointSize, 2f) / 2f);
                    Vertex v1 = vertex.copy().replaceXYZ(vertex.x - offset, vertex.y - offset, vertex.z);
                    Vertex v2 = vertex.copy().replaceXYZ(vertex.x + offset, vertex.y - offset, vertex.z);
                    Vertex v3 = vertex.copy().replaceXYZ(vertex.x + offset, vertex.y + offset, vertex.z);
                    Vertex v4 = vertex.copy().replaceXYZ(vertex.x - offset, vertex.y + offset, vertex.z);

                    switch (paintType) {
                        case FILL ->
                                createPolygonTriangulator(vertexList, indexList, false, v1, v2, v3, v4);
                        case STROKE ->
                                createLineLoopTriangulator(vertexList, indexList, false, v1, v2, v3, v4);
                        default ->
                                throw new IllegalStateException("Unexpected value: " + paintType);
                    }
                }
            }
            case ROUND -> {
                for (Vertex vertex : vertices) {
                    switch (paintType) {
                        case FILL ->
                                makeCircle(vertexList, indexList, vertex.copy(), 0, 360, pointSize, pointSize, calculateSegmentCount(pointSize), false, Color.of(vertex.getColors()));
                        case STROKE ->
                                makeCircle(vertexList, indexList, vertex.copy(), 0, 360, pointSize, pointSize, calculateSegmentCount(pointSize), true, Color.of(vertex.getColors()));
                        default ->
                                throw new IllegalStateException("Unexpected value: " + paintType);
                    }
                }
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + pointType);
        }
    }

    private int calculateSegmentCount(float pointSize) {
        float circumference = PI * pointSize;
        int segments = max(6, (int) (circumference / POLYGON_TRIANGULATOR_TOLERANCE));

        return min(segments, 128);
    }

    private void makeCircle(List<Vertex> vertexList, List<Integer> indexList, Vertex center, float startAngle, float endAngle, float xRadius, float yRadius, int segmentCount, boolean isOutLine, Color... colors) {
        if (colors == null || colors.length == 0) {
            return;
        }

        float normalizedEnd = endAngle;
        while (normalizedEnd < startAngle) {
            normalizedEnd += 360f;
        }

        float angle = abs(normalizedEnd - startAngle);
        boolean isFullCircle = angle >= 360f;

        int totalSteps = segmentCount * colors.length;
        float step = angle / totalSteps;

        if (!isOutLine) {
            int base = vertexList.size();
            vertexList.add(center.copy());

            int loopCount = 0;
            for (float j = startAngle; j <= normalizedEnd + step * 0.5f; j += step) {
                float clampedJ = min(j, normalizedEnd);
                float t = (angle == 0f) ? 0f : (clampedJ - startAngle) / angle;
                Color blended = blendColor(t, colors);

                float rad = toRadians(clampedJ);
                float x = cos(rad) * xRadius;
                float y = sin(rad) * yRadius;

                int fanBase = vertexList.size();
                vertexList.add(center.copy().moveXYZ(x, y, 0).replaceColor(blended));

                if (loopCount > 0) {
                    indexList.add(base);
                    indexList.add(fanBase - 1);
                    indexList.add(fanBase);
                }
                loopCount++;
            }
        } else {
            List<Vertex> outlineVertices = new ArrayList<>();

            for (float j = startAngle; j <= normalizedEnd + step * 0.5f; j += step) {
                float clampedJ = min(j, normalizedEnd);
                float t = (angle == 0f) ? 0f : (clampedJ - startAngle) / angle;
                Color blended = blendColor(t, colors);

                float rad = toRadians(clampedJ);
                float x = cos(rad) * xRadius;
                float y = sin(rad) * yRadius;

                outlineVertices.add(center.copy().moveXYZ(x, y, 0).replaceColor(blended));
            }

            if (outlineVertices.size() >= 2) {
                if (isFullCircle) {
                    outlineVertices.removeLast();
                    createLineLoopTriangulator(vertexList, indexList, false, outlineVertices.toArray(new Vertex[0]));
                } else {
                    createLineStripTriangulator(vertexList, indexList, outlineVertices.toArray(new Vertex[0]));
                }
            }
        }
    }

    private Color blendColor(float t, Color[] colors) {
        if (colors.length == 1) {
            return colors[0];
        }

        float colorPos = t * (colors.length - 1);
        int index = min((int) colorPos, colors.length - 2);
        float localT = colorPos - index;

        return lerpColor(colors[index], colors[index + 1], localT);
    }

    private TestElementPair createElementPair(List<Vertex> vertexList, List<Integer> indexList) {
        List<Vertex> uniqueVertices = new ArrayList<>();
        List<Integer> newIndices = new ArrayList<>();

        Map<Vertex, Integer> vertexIndexMap = new HashMap<>();
        for (int oldIndex : indexList) {
            Vertex v = vertexList.get(oldIndex);

            Integer newIndex = vertexIndexMap.get(v);
            if (newIndex == null) {
                newIndex = uniqueVertices.size();
                uniqueVertices.add(v);
                vertexIndexMap.put(v, newIndex);
            }

            newIndices.add(newIndex);
        }

        return new TestElementPair(uniqueVertices.toArray(new Vertex[0]), newIndices.stream().mapToInt(Integer::intValue).toArray());
    }

    public TestElementPair getResult() {
        return result;
    }
}
