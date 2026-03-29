package me.hannsi.test;

import me.hannsi.lfjg.render.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.render.system.mesh.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * GLPolygon用ランダムポリゴン生成ユーティリティ
 *
 * 使い方:
 *   String code = RandomPolygonGenerator.generate();
 *   System.out.println(code);
 */
public class RandomPolygonGenerator {
    public static GLPolygon.PaintTypeStep<GLPolygon> generate(Shape shape, int minVerts, int maxVerts, float minX, float maxX, float minY, float maxY, Random rand, GLPolygon.VertexDataStep<GLPolygon> glPolygon) {
        int vertCount = minVerts + rand.nextInt(maxVerts - minVerts + 1);
        List<float[]> vertices = generateVertices(shape, vertCount, minX, maxX, minY, maxY, rand);

        for (int i = 0; i < vertices.size() - 1; i++) {
            float[] v = vertices.get(i);
            glPolygon.addVertex(new Vertex(v[0], v[1]));
        }

        float[] last = vertices.get(vertices.size() - 1);

        glPolygon.addVertex(new Vertex(last[0], last[1]));
        return glPolygon.end();
    }

    private static List<float[]> generateVertices(Shape shape, int n, float minX, float maxX, float minY, float maxY, Random rand) {
        float cx = (minX + maxX) / 2f;
        float cy = (minY + maxY) / 2f;
        float rx = (maxX - minX) / 2f * 0.8f; // 余白のため0.8倍
        float ry = (maxY - minY) / 2f * 0.8f;

        return switch (shape) {
            case CONVEX ->
                    convex(n, cx, cy, rx, ry, rand);
            case CONCAVE ->
                    concave(n, cx, cy, rx, ry, rand);
            case STAR ->
                    star(n, cx, cy, rx, ry, rand);
            case ORGANIC ->
                    organic(n, cx, cy, rx, ry, rand);
            default ->
                    concave(n, cx, cy, rx, ry, rand);
        };
    }

    private static List<float[]> convex(int n, float cx, float cy, float rx, float ry, Random rand) {
        List<float[]> pts = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n + (rand.nextDouble() - 0.5) * (Math.PI / n);
            float r = 0.6f + 0.4f * rand.nextFloat();
            pts.add(new float[]{
                    cx + (float) (rx * r * Math.cos(angle)),
                    cy + (float) (ry * r * Math.sin(angle))
            });
        }
        return pts;
    }

    private static List<float[]> concave(int n, float cx, float cy, float rx, float ry, Random rand) {
        List<float[]> pts = new ArrayList<>();
        int concaveCount = Math.max(1, n / 3);
        List<Integer> concaveIdx = new ArrayList<>();
        while (concaveIdx.size() < concaveCount) {
            int idx = rand.nextInt(n);
            if (!concaveIdx.contains(idx)) {
                concaveIdx.add(idx);
            }
        }

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            float r;
            if (concaveIdx.contains(i)) {
                r = 0.25f + 0.25f * rand.nextFloat();
            } else {
                r = 0.7f + 0.3f * rand.nextFloat();
            }
            pts.add(new float[]{
                    cx + (float) (rx * r * Math.cos(angle)),
                    cy + (float) (ry * r * Math.sin(angle))
            });
        }
        return pts;
    }

    private static List<float[]> star(int n, float cx, float cy, float rx, float ry, Random rand) {
        List<float[]> pts = new ArrayList<>();
        int points = Math.max(4, n / 2);
        float outerR = 0.8f + 0.2f * rand.nextFloat();
        float innerR = 0.2f + 0.25f * rand.nextFloat();

        for (int i = 0; i < points * 2; i++) {
            double angle = Math.PI * i / points - Math.PI / 2;
            float r = (i % 2 == 0) ? outerR : innerR;
            pts.add(new float[]{
                    cx + (float) (rx * r * Math.cos(angle)),
                    cy + (float) (ry * r * Math.sin(angle))
            });
        }
        return pts;
    }

    private static List<float[]> organic(int n, float cx, float cy, float rx, float ry, Random rand) {
        List<float[]> pts = new ArrayList<>();
        float[] radii = new float[n];
        for (int i = 0; i < n; i++) {
            radii[i] = 0.5f + 0.5f * rand.nextFloat();
        }

        for (int pass = 0; pass < 2; pass++) {
            float[] smoothed = new float[n];
            for (int i = 0; i < n; i++) {
                smoothed[i] = (radii[(i - 1 + n) % n] + radii[i] * 2 + radii[(i + 1) % n]) / 4f;
            }
            radii = smoothed;
        }

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            pts.add(new float[]{
                    cx + (float) (rx * radii[i] * Math.cos(angle)),
                    cy + (float) (ry * radii[i] * Math.sin(angle))
            });
        }
        return pts;
    }

    public enum Shape {
        CONVEX,
        CONCAVE,
        STAR,
        ORGANIC
    }
}
