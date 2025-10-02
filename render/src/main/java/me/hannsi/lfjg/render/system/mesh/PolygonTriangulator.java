package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.PolygonTriangulatorException;
import me.hannsi.lfjg.render.system.rendering.DrawType;

import java.util.*;

public class PolygonTriangulator {
    public static final boolean DEBUG = false;

    private ProjectionType projectionType;
    private DrawType drawType;
    private float[] positions;
    private ElementPair result;

    public static PolygonTriangulator createPolygonTriangulator() {
        return new PolygonTriangulator();
    }

    public PolygonTriangulator projectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;
        return this;
    }

    public PolygonTriangulator drawType(DrawType drawType) {
        this.drawType = drawType;
        return this;
    }

    public PolygonTriangulator positions(float[] positions) {
        this.positions = positions;
        return this;
    }

    public PolygonTriangulator process() {
        try {
            result = processTriangulator();
        } catch (Exception e) {
            DebugLog.error(getClass(), e);
            DebugLog.error(getClass(), new PolygonTriangulatorException("Triangulation failed"));
            result = new ElementPair(new float[0], new int[0]);
        }

        return this;
    }

    private ElementPair processTriangulator() {
        if (positions == null || positions.length < projectionType.getStride()) {
            DebugLog.warning(getClass(), "Positions are null or too short");
            return new ElementPair(new float[0], new int[0]);
        }

        int stride = projectionType.getStride();
        float[] outPositions = positions.clone();

        try {
            switch (drawType) {
                case POINTS:
                case LINES:
                case LINE_STRIP:
                case LINE_LOOP:
                case TRIANGLES:
                case TRIANGLE_FAN:
                    int triCount = positions.length / stride;
                    int[] triIndices = new int[triCount];
                    for (int i = 0; i < triCount; i++) {
                        triIndices[i] = i;
                    }

                    return new ElementPair(outPositions, triIndices);
                case QUADS:
                    List<Integer> idxListQ = new ArrayList<>();
                    int quadVertices = positions.length / stride;
                    for (int q = 0; q + 3 < quadVertices; q += 4) {
                        idxListQ.add(q);
                        idxListQ.add(q + 1);
                        idxListQ.add(q + 2);

                        idxListQ.add(q);
                        idxListQ.add(q + 2);
                        idxListQ.add(q + 3);
                    }

                    return new ElementPair(outPositions, idxListQ.stream().mapToInt(i -> i).toArray());

                case POLYGON:
                    try {
                        return handlePolygon(outPositions, stride);
                    } catch (Exception e) {
                        DebugLog.error(getClass(), e);
                        DebugLog.error(getClass(), new PolygonTriangulatorException("Polygon triangulation failed"));
                        return new ElementPair(new float[0], new int[0]);
                    }

                default:
                    throw new IllegalStateException("Unexpected DrawType: " + drawType);
            }
        } catch (Exception e) {
            DebugLog.error(getClass(), e);
            DebugLog.error(getClass(), new PolygonTriangulatorException("Triangulation process failed"));
            return new ElementPair(new float[0], new int[0]);
        }
    }

    private ElementPair handlePolygon(float[] outPositions, int stride) {
        List<float[]> vertices = new ArrayList<>();
        for (int i = 0; i < positions.length; i += stride) {
            float[] v = new float[stride];
            System.arraycopy(positions, i, v, 0, stride);
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

        return new ElementPair(outPositions, triangles.stream().flatMapToInt(Arrays::stream).toArray());
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
            int curr = mono.get(i), top = stack.peek();
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

    public ElementPair getResult() {
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