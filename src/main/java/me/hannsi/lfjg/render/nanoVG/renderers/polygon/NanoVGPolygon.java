package me.hannsi.lfjg.render.nanoVG.renderers.polygon;

import me.hannsi.lfjg.render.nanoVG.NanoVGUtil;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.math.MathUtil;
import me.hannsi.lfjg.utils.math.vertex.vector.Vector2f;
import org.lwjgl.nanovg.NanoVG;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NanoVGPolygon {
    public long nvg;
    public boolean base;
    public boolean outLine;
    public List<Vector2f> poses;
    public float x;
    public float y;
    public float width;
    public float height;
    public float cx;
    public float cy;
    public float range;
    public float lineWidth;
    public Color color1;
    public Color outLineColor1;

    public NanoVGPolygon(long nvg) {
        this.nvg = nvg;
        this.poses = new CopyOnWriteArrayList<>();
    }

    public void setSize(Vector2f... pos) {
        Collections.addAll(this.poses, pos);
        float[] xs = new float[pos.length];
        float[] ys = new float[pos.length];

        int count = 0;
        for (Vector2f p : pos) {
            xs[count] = p.getX();
            ys[count] = p.getY();

            count++;
        }

        float maxX = MathUtil.getLargest(xs);
        float maxY = MathUtil.getLargest(ys);
        float minX = MathUtil.getShortest(xs);
        float minY = MathUtil.getShortest(ys);

        float distance = MathUtil.distance(new Vector2f(minX, minY), new Vector2f(maxX, maxY));

        this.x = minX;
        this.y = minY;
        this.width = distance;
        this.height = distance;
        this.range = distance;
        this.cx = x + (width / 2f);
        this.cy = y + (height / 2f);
    }

    public void setOutLineBase(float lineWidth, Color outLineColor1) {
        this.outLine = true;
        this.lineWidth = lineWidth;
        this.outLineColor1 = outLineColor1;
    }

    public void setSize(List<Vector2f> poses) {
        this.poses.addAll(poses);
    }

    public void draw() {
        NanoVG.nvgBeginPath(nvg);

        int count = 0;
        for (Vector2f vec2f : poses) {
            if (count == 0) {
                NanoVG.nvgMoveTo(nvg, vec2f.getX(), vec2f.getY());
            }

            NanoVG.nvgLineTo(nvg, vec2f.getX(), vec2f.getY());

            count++;
        }

        if (base) {
            NanoVGUtil.fillColor(nvg, color1);
        }

        if (outLine) {
            Vector2f vec2f = poses.get(0);
            NanoVG.nvgLineTo(nvg, vec2f.getX(), vec2f.getY());

            NanoVG.nvgStrokeWidth(nvg, lineWidth);

            NanoVGUtil.strokeColor(nvg, outLineColor1);
        }

        NanoVG.nvgClosePath(nvg);
    }

    public boolean isBase() {
        return base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

    public void setBase(boolean base, Color color1) {
        this.base = base;
        this.color1 = color1;
    }

    public boolean isOutLine() {
        return outLine;
    }

    public void setOutLine(boolean outLine) {
        this.outLine = outLine;
    }

    public List<Vector2f> getPoses() {
        return poses;
    }

    public void setPoses(List<Vector2f> poses) {
        this.poses = poses;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public long getNvg() {
        return nvg;
    }

    public void setNvg(long nvg) {
        this.nvg = nvg;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getOutLineColor1() {
        return outLineColor1;
    }

    public void setOutLineColor1(Color outLineColor1) {
        this.outLineColor1 = outLineColor1;
    }
}
