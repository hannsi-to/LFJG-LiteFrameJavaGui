package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.PolygonTriangulatorException;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import org.joml.Vector2f;

import java.util.*;

public class TestPolygonTriangulator {
    public static final boolean DEBUG = false;
    private static final float MITER_LIMIT = 4;

    private ProjectionType projectionType;
    private DrawType drawType;
    private float lineWidth;
    private JointType lineJointType;
    private float pointSize;
    private Vertex[] vertices;
    private float[] outPositions;
    private TestElementPair result;

    public static TestPolygonTriangulator createPolygonTriangulator() {
        return new TestPolygonTriangulator();
    }

    public static Vector2f computeLineIntersection(Vector2f p1, Vector2f p2, Vector2f p3, Vector2f p4) {

        float x1 = p1.x, y1 = p1.y;
        float x2 = p2.x, y2 = p2.y;
        float x3 = p3.x, y3 = p3.y;
        float x4 = p4.x, y4 = p4.y;

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
        int prevBase = -1;
        try {
            switch (drawType) {
                case POINTS:
                case LINES:
                    checkLineParameter();
                    if (lineCount < 1) {
                        throw new PolygonTriangulatorException("When DrawType.LINES is specified, vertex information must be two or more.");
                    }

                    for (int i = 0; i < vertices.length - 1; i += 2) {
                        makeLineQuad(vertices[i], vertices[i + 1], lineWidth, newVertices, indices);
                    }

                    return new TestElementPair(newVertices.toArray(new Vertex[0]), indices.stream().mapToInt(Integer::intValue).toArray());

                case LINE_STRIP:
                    checkLineParameter();
                    if (lineCount < 1) {
                        throw new PolygonTriangulatorException("When DrawType.LINES is specified, vertex information must be two or more.");
                    }

                    for (int i = 0; i < vertices.length - 1; i++) {
                        makeLineQuadStrip(i, newVertices, indices);
                    }
                    return new TestElementPair(newVertices.toArray(new Vertex[0]), indices.stream().mapToInt(Integer::intValue).toArray());

                case LINE_LOOP:
                    checkLineParameter();
                    if (lineCount < 1) {
                        throw new PolygonTriangulatorException("When DrawType.LINES is specified, vertex information must be two or more.");
                    }

                    for (int i = 0; i < vertices.length; i++) {
                        makeLineQuad(vertices[i], vertices[(i + 1) % vertices.length], lineWidth, newVertices, indices);
                    }
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

    private void makeLineQuad(Vertex v1, Vertex v2, float lineWidth, List<Vertex> newVertices, List<Integer> indices) {
        float dx = v2.x - v1.x;
        float dy = v2.y - v1.y;
        float nx = -dy;
        float ny = dx;
        float length = MathHelper.sqrt(MathHelper.pow(nx, 2) + MathHelper.pow(ny, 2));
        nx = nx / length * (lineWidth / 2f);
        ny = ny / length * (lineWidth / 2f);

        int base = newVertices.size();
        indices.add(base);
        indices.add(base + 1);
        indices.add(base + 2);
        indices.add(base + 2);
        indices.add(base + 1);
        indices.add(base + 3);

        newVertices.add(new Vertex(v1.x + nx, v1.y + ny, v1.z, v1.red, v1.green, v1.blue, v1.alpha, 0, 0, v1.normalsX, v1.normalsY, v1.normalsZ));
        newVertices.add(new Vertex(v1.x - nx, v1.y - ny, v1.z, v1.red, v1.green, v1.blue, v1.alpha, 0, 1, v1.normalsX, v1.normalsY, v1.normalsZ));
        newVertices.add(new Vertex(v2.x + nx, v2.y + ny, v2.z, v2.red, v2.green, v2.blue, v2.alpha, 1, 0, v2.normalsX, v2.normalsY, v2.normalsZ));
        newVertices.add(new Vertex(v2.x - nx, v2.y - ny, v2.z, v2.red, v2.green, v2.blue, v2.alpha, 1, 1, v2.normalsX, v2.normalsY, v2.normalsZ));
    }

    private void makeLineQuadStrip(int vertexCount, List<Vertex> newVertices, List<Integer> indices) {
        Vertex prev;
        Vertex center = vertices[vertexCount];
        Vertex next = vertices[vertexCount + 1];

        float dx2 = next.x - center.x;
        float dy2 = next.y - center.y;
        float len2 = MathHelper.sqrt(dx2 * dx2 + dy2 * dy2);
        float nx2 = -dy2 / len2 * (lineWidth / 2f);
        float ny2 = dx2 / len2 * (lineWidth / 2f);
        if (vertexCount == 0) {
            float nx = -dy2;
            float ny = dx2;
            float length = MathHelper.sqrt(MathHelper.pow(nx, 2) + MathHelper.pow(ny, 2));
            nx = nx / length * (lineWidth / 2f);
            ny = ny / length * (lineWidth / 2f);

            int base = newVertices.size();
            indices.add(base);
            indices.add(base + 1);
            indices.add(base + 2);
            indices.add(base + 2);
            indices.add(base + 3);
            indices.add(base + 1);

            newVertices.add(new Vertex(center.x + nx, center.y + ny, center.z, center.red, center.green, center.blue, center.alpha, 0, 0, center.normalsX, center.normalsY, center.normalsZ));
            newVertices.add(new Vertex(center.x - nx, center.y - ny, center.z, center.red, center.green, center.blue, center.alpha, 0, 1, center.normalsX, center.normalsY, center.normalsZ));
            newVertices.add(new Vertex(next.x + nx, next.y + ny, next.z, next.red, next.green, next.blue, next.alpha, 1, 0, next.normalsX, next.normalsY, next.normalsZ));
            newVertices.add(new Vertex(next.x - nx, next.y - ny, next.z, next.red, next.green, next.blue, next.alpha, 1, 1, next.normalsX, next.normalsY, next.normalsZ));
        } else {
            prev = vertices[vertexCount - 1];

            float dx1 = center.x - prev.x;
            float dy1 = center.y - prev.y;
            float len1 = MathHelper.sqrt(dx1 * dx1 + dy1 * dy1);
            float nx1 = -dy1 / len1 * (lineWidth / 2f);
            float ny1 = dx1 / len1 * (lineWidth / 2f);

            float dot = dx1 * dx2 + dy1 * dy2;
            float cross = dx1 * dy2 - dy1 * dx2;
            float angle = MathHelper.atan2(cross, dot);

            float sign = (angle < 0) ? -1 : 1;
            Vector2f offset1 = new Vector2f(nx1 * sign, ny1 * sign);
            Vector2f offset2 = new Vector2f(nx2 * sign, ny2 * sign);

            Vector2f p1 = new Vector2f(prev.x + offset1.x, prev.y + offset1.y);
            Vector2f p2 = new Vector2f(center.x + offset1.x, center.y + offset1.y);
            Vector2f p3 = new Vector2f(center.x + offset2.x, center.y + offset2.y);
            Vector2f p4 = new Vector2f(next.x + offset2.x, next.y + offset2.y);

            Vector2f jointVertex2f = computeLineIntersection(p1, p2, p3, p4);

            float nx = -dy2;
            float ny = dx2;
            float length = MathHelper.sqrt(MathHelper.pow(nx, 2) + MathHelper.pow(ny, 2));
            nx = nx / length * (lineWidth / 2f);
            ny = ny / length * (lineWidth / 2f);

            if (jointVertex2f == null) {
                int base = newVertices.size();
                indices.add(base);
                indices.add(base + 1);
                indices.add(base + 2);
                indices.add(base + 2);
                indices.add(base + 3);
                indices.add(base + 1);

                newVertices.add(new Vertex(center.x + nx, center.y + ny, center.z, center.red, center.green, center.blue, center.alpha, 0, 0, center.normalsX, center.normalsY, center.normalsZ));
                newVertices.add(new Vertex(center.x - nx, center.y - ny, center.z, center.red, center.green, center.blue, center.alpha, 0, 1, center.normalsX, center.normalsY, center.normalsZ));
                newVertices.add(new Vertex(next.x + nx, next.y + ny, next.z, next.red, next.green, next.blue, next.alpha, 1, 0, next.normalsX, next.normalsY, next.normalsZ));
                newVertices.add(new Vertex(next.x - nx, next.y - ny, next.z, next.red, next.green, next.blue, next.alpha, 1, 1, next.normalsX, next.normalsY, next.normalsZ));

                return;
            }

            Vertex joint = new Vertex(jointVertex2f.x, jointVertex2f.y, center.z, center.red, center.green, center.blue, center.alpha, 0, 0, center.normalsX, center.normalsY, center.normalsZ);
            if (sign == 1) {
                newVertices.remove(newVertices.size() - 2);
                newVertices.add(newVertices.size() - 1, joint);

                int base = newVertices.size();
                indices.add(base);
                indices.add(base + 1);
                indices.add(base + 2);
                indices.add(base + 2);
                indices.add(base + 3);
                indices.add(base + 1);

                newVertices.add(joint);
                newVertices.add(new Vertex(center.x - nx, center.y - ny, center.z, center.red, center.green, center.blue, center.alpha, 0, 1, center.normalsX, center.normalsY, center.normalsZ));
            } else {
                newVertices.remove(newVertices.size() - 1);
                newVertices.add(joint);

                int base = newVertices.size();
                indices.add(base);
                indices.add(base + 1);
                indices.add(base + 2);
                indices.add(base + 2);
                indices.add(base + 3);
                indices.add(base + 1);

                newVertices.add(new Vertex(center.x + nx, center.y + ny, center.z, center.red, center.green, center.blue, center.alpha, 0, 0, center.normalsX, center.normalsY, center.normalsZ));
                newVertices.add(joint);
            }
            joint(angle, sign, prev, joint, next, newVertices, indices);
            newVertices.add(new Vertex(next.x + nx, next.y + ny, next.z, next.red, next.green, next.blue, next.alpha, 1, 0, next.normalsX, next.normalsY, next.normalsZ));
            newVertices.add(new Vertex(next.x - nx, next.y - ny, next.z, next.red, next.green, next.blue, next.alpha, 1, 1, next.normalsX, next.normalsY, next.normalsZ));
        }
    }

    private void joint(float angle, float sign, Vertex preVertex, Vertex jointVertex, Vertex nextVertex, List<Vertex> newVertices, List<Integer> indices) {
        switch (lineJointType) {
            case NONE:
                break;
            case MITER:
                if ((1 / MathHelper.sin(MathHelper.abs(angle) / 2f)) <= MITER_LIMIT) {

                } else {
                    doBevel(sign, newVertices, indices);
                }
                break;
            case BEVEL:
                doBevel(sign, newVertices, indices);
                break;
            case ROUND:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + lineJointType);
        }
    }

    private void doMiter(List<Vertex> newVertices, List<Integer> indices) {

    }

    private void doBevel(float sign, List<Vertex> newVertices, List<Integer> indices) {
        int base = newVertices.size();
        indices.add(base - 1);
        indices.add(base - 2);
        if (sign == 1) {
            indices.add(base - 3);
        } else {
            indices.add(base - 4);
        }
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
            if (Math.abs(dy) > 1e-6) {
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
