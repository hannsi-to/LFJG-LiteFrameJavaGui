package me.hannsi.lfjg.core.utils.math.animation;

import static me.hannsi.lfjg.core.utils.math.MathHelper.*;

public class MultiBezierEasing implements Easing {
    private final BezierPoint[] controlPoints;
    private final int degree;
    private final long[] binomialCoeffs;

    public MultiBezierEasing(BezierPoint... controlPoints) {
        if (controlPoints.length < 2) {
            throw new IllegalArgumentException("At least 2 control points required");
        }

        int dims = controlPoints[0].dimensions;
        for (BezierPoint p : controlPoints) {
            if (p.dimensions != dims) {
                throw new IllegalArgumentException("All control points must have the same dimension");
            }
        }

        this.controlPoints = controlPoints.clone();
        this.degree = controlPoints.length - 1;
        this.binomialCoeffs = computeBinomialCoefficients(degree);
    }

    private static long[] computeBinomialCoefficients(int n) {
        long[] c = new long[n + 1];
        c[0] = 1;
        for (int i = 1; i <= n; i++) {
            c[i] = c[i - 1] * (n - i + 1) / i;
        }
        return c;
    }

    private static float binomialCoeffs(int n, int k) {
        if (k < 0 || k > n) {
            return 0;
        }
        float c = 1;
        for (int i = 0; i < k; i++) {
            c = c * (n - i) / (i + 1);
        }
        return c;
    }

    public BezierPoint evaluate(float t) {
        t = max(0, min(1, t));
        int dims = controlPoints[0].dimensions;
        float[] result = new float[dims];

        for (int i = 0; i <= degree; i++) {
            float basis = binomialCoeffs[i] * pow(t, i) * pow(1 - t, degree - i);

            for (int d = 0; d < dims; d++) {
                result[d] += basis * controlPoints[i].coords[d];
            }
        }

        return new BezierPoint(result);
    }

    public BezierPoint evaluateDeCasteljau(float t) {
        t = max(0, min(1, t));
        BezierPoint[] pts = controlPoints.clone();

        for (int r = 1; r <= degree; r++) {
            for (int i = 0; i <= degree - r; i++) {
                pts[i] = pts[i].lerp(pts[i + 1], t);
            }
        }

        return pts[0];
    }

    public BezierPoint derivative(float t) {
        t = max(0, min(1, t));
        int dims = controlPoints[0].dimensions;
        float[] result = new float[dims];

        for (int i = 0; i < degree; i++) {
            float basis = degree * binomialCoeffs(degree - 1, i) * pow(t, i) * pow(1 - t, degree - 1 - i);

            for (int d = 0; d < dims; d++) {
                result[d] += basis * (controlPoints[i + 1].coords[d] - controlPoints[i].coords[d]);
            }
        }

        return new BezierPoint(result);
    }

    public BezierPoint[] sample(int numSamples) {
        BezierPoint[] samples = new BezierPoint[numSamples];
        for (int i = 0; i < numSamples; i++) {
            samples[i] = evaluateDeCasteljau(i / (numSamples - 1f));
        }
        return samples;
    }

    public MultiBezierEasing[] split(float splitT) {
        splitT = max(0, min(1, splitT));
        int n = controlPoints.length;
        BezierPoint[][] triangle = new BezierPoint[n][n];

        System.arraycopy(controlPoints, 0, triangle[0], 0, n);

        for (int r = 1; r < n; r++) {
            for (int i = 0; i < n - r; i++) {
                triangle[r][i] = triangle[r - 1][i].lerp(triangle[r - 1][i + 1], splitT);
            }
        }

        BezierPoint[] left = new BezierPoint[n];
        for (int i = 0; i < n; i++) {
            left[i] = triangle[i][0];
        }

        BezierPoint[] right = new BezierPoint[n];
        for (int i = 0; i < n; i++) {
            right[i] = triangle[n - 1 - i][i];
        }

        return new MultiBezierEasing[]{
                new MultiBezierEasing(left),
                new MultiBezierEasing(right)
        };
    }

    public BezierPoint[] getControlPoints() {
        return controlPoints.clone();
    }

    public int getDegree() {
        return degree;
    }

    public int getDimensions() {
        return controlPoints[0].dimensions;
    }

    @Override
    public float ease(float value) {
        BezierPoint bezierPoint = evaluateDeCasteljau(value);
        if (bezierPoint.dimensions != 1) {
            throw new IllegalArgumentException("This bezier point is not 1 dimensions");
        }

        return bezierPoint.coords[0];
    }
}
