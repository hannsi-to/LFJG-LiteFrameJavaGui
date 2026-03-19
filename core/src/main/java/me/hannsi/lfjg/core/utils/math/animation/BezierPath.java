package me.hannsi.lfjg.core.utils.math.animation;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;
import static me.hannsi.lfjg.core.utils.math.MathHelper.min;

public class BezierPath {
    private final List<MultiBezierEasing> segments = new ArrayList<>();

    public BezierPath addSegment(MultiBezierEasing segment) {
        segments.add(segment);
        return this;
    }

    public BezierPath addSegment(BezierPoint endControlPoint, BezierPoint endPoint, Continuity continuity) {
        if (segments.isEmpty()) {
            throw new IllegalStateException("No previous segment");
        }

        MultiBezierEasing prev = segments.getLast();
        BezierPoint[] prevPts = prev.getControlPoints();
        BezierPoint lastPt = prevPts[prevPts.length - 1];
        BezierPoint lastCtrl = prevPts[prevPts.length - 2];

        BezierPoint firstCtrl = switch (continuity) {
            case NONE ->
                    lastPt;
            case C0 ->
                    lastPt.lerp(endPoint, 0.01f);
            case C1 ->
                    lastPt.scale(2).sub(lastCtrl);
            case G1 -> {
                BezierPoint mirrored = lastPt.scale(2).sub(lastCtrl);
                BezierPoint direction = mirrored.sub(lastPt);
                float length = lastPt.distanceTo(endPoint) * 0.3f;
                yield lastPt.add(direction.normalize().scale(length));
            }
        };

        addSegment(new MultiBezierEasing(lastPt, firstCtrl, endControlPoint, endPoint));
        return this;
    }

    public BezierPoint evaluate(float t) {
        if (segments.isEmpty()) {
            throw new IllegalStateException("No segments");
        }
        t = max(0, min(1, t));

        float segT = t * segments.size();
        int segIdx = min((int) segT, segments.size() - 1);
        float localT = segT - segIdx;

        return segments.get(segIdx).evaluateDeCasteljau(localT);
    }

    public BezierPoint evaluateArcLength(float t, int numSamples) {
        if (segments.isEmpty()) {
            throw new IllegalStateException("No segments");
        }
        t = max(0, min(1, t));

        BezierPoint[] samples = sampleAll(numSamples);
        float[] cumDist = new float[numSamples];
        for (int i = 1; i < numSamples; i++) {
            cumDist[i] = cumDist[i - 1] + samples[i - 1].distanceTo(samples[i]);
        }

        float totalLength = cumDist[numSamples - 1];
        float targetDist = t * totalLength;

        for (int i = 1; i < numSamples; i++) {
            if (cumDist[i] >= targetDist) {
                float localT = (targetDist - cumDist[i - 1]) / (cumDist[i] - cumDist[i - 1]);
                return samples[i - 1].lerp(samples[i], localT);
            }
        }

        return samples[numSamples - 1];
    }

    public BezierPoint[] sampleAll(int totalSamples) {
        BezierPoint[] result = new BezierPoint[totalSamples];
        for (int i = 0; i < totalSamples; i++) {
            result[i] = evaluate((float) i / (totalSamples - 1));
        }
        return result;
    }

    public int getSegmentCount() {
        return segments.size();
    }

    public enum Continuity implements IEnumTypeBase {
        NONE(0, "None"),
        C0(1, "C0"),
        C1(2, "C1"),
        G1(3, "G1");

        final int id;
        final String name;

        Continuity(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
