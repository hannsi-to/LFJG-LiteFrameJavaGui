package me.hannsi.lfjg.render.nanoVG.system.polygon;

import me.hannsi.lfjg.utils.color.Color;

public class NanoVGRendererBase {
    public long nvg;
    public boolean base;
    public boolean outLine;
    public float x;
    public float y;
    public float width;
    public float height;
    public float cx;
    public float cy;
    public float range;
    public float lineWidth;
    public float pointSize;
    public GradientMode gradientMode;
    public GradientMode outLineGradientMode;
    public float feather;
    public float outLineFeather;
    public float inRange;
    public float outLineInRange;
    public float outRange;
    public float outLineOutRange;
    public Color color1;
    public Color color2;
    public Color outLineColor1;
    public Color outLineColor2;

    public NanoVGRendererBase(long nvg) {
        this.nvg = nvg;
    }

    public void setSize(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cx = x + (width / 2f);
        this.cy = y + (height / 2f);
        this.range = Math.max(width, height);
    }

    public void setSize(float cx, float cy, float range) {
        this.cx = cx;
        this.cy = cy;
        this.range = range;
        this.pointSize = range;
        this.x = cx - range;
        this.y = cy - range;
        this.width = range * 2;
        this.height = range * 2;
    }

    public void setBase(boolean base, Color color1) {
        this.base = base;
        this.gradientMode = GradientMode.Normal;
        this.color1 = color1;
    }

    public void setOutLine(boolean outLine, float lineWidth, Color outLineColor1) {
        this.outLine = outLine;
        this.outLineGradientMode = GradientMode.Normal;
        this.lineWidth = lineWidth;
        this.outLineColor1 = outLineColor1;
    }

    public void setLinearGradient(Color color2) {
        this.gradientMode = GradientMode.Linear;
        this.color2 = color2;
    }

    public void setLinearGradientOutLine(Color outLineColor2) {
        this.outLineGradientMode = GradientMode.Linear;
        this.outLineColor2 = outLineColor2;
    }

    public void setBoxGradient(float feather, Color color2) {
        this.gradientMode = GradientMode.Box;
        this.feather = feather;
        this.color2 = color2;
    }

    public void setBoxGradientOutLine(float outLineFeather, Color outLineColor2) {
        this.outLineGradientMode = GradientMode.Box;
        this.outLineFeather = outLineFeather;
        this.outLineColor2 = outLineColor2;
    }

    public void setRadialGradient(float inRange, float outRange, Color color2) {
        this.gradientMode = GradientMode.Radial;
        this.inRange = inRange;
        this.outRange = outRange;
        this.color2 = color2;
    }

    public void setRadialGradientOutLine(float outLineInRange, float outLineOutRange, Color outLineColor2) {
        this.outLineGradientMode = GradientMode.Radial;
        this.outLineInRange = outLineInRange;
        this.outLineOutRange = outLineOutRange;
        this.outLineColor2 = outLineColor2;
    }

    public void draw() {

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

    public GradientMode getGradientMode() {
        return gradientMode;
    }

    public void setGradientMode(GradientMode gradientMode) {
        this.gradientMode = gradientMode;
    }

    public GradientMode getOutLineGradientMode() {
        return outLineGradientMode;
    }

    public void setOutLineGradientMode(GradientMode outLineGradientMode) {
        this.outLineGradientMode = outLineGradientMode;
    }

    public float getFeather() {
        return feather;
    }

    public void setFeather(float feather) {
        this.feather = feather;
    }

    public float getOutLineFeather() {
        return outLineFeather;
    }

    public void setOutLineFeather(float outLineFeather) {
        this.outLineFeather = outLineFeather;
    }

    public float getInRange() {
        return inRange;
    }

    public void setInRange(float inRange) {
        this.inRange = inRange;
    }

    public float getOutLineInRange() {
        return outLineInRange;
    }

    public void setOutLineInRange(float outLineInRange) {
        this.outLineInRange = outLineInRange;
    }

    public float getOutRange() {
        return outRange;
    }

    public void setOutRange(float outRange) {
        this.outRange = outRange;
    }

    public float getOutLineOutRange() {
        return outLineOutRange;
    }

    public void setOutLineOutRange(float outLineOutRange) {
        this.outLineOutRange = outLineOutRange;
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    public Color getOutLineColor1() {
        return outLineColor1;
    }

    public void setOutLineColor1(Color outLineColor1) {
        this.outLineColor1 = outLineColor1;
    }

    public Color getOutLineColor2() {
        return outLineColor2;
    }

    public void setOutLineColor2(Color outLineColor2) {
        this.outLineColor2 = outLineColor2;
    }

    public boolean isBase() {
        return base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

    public boolean isOutLine() {
        return outLine;
    }

    public void setOutLine(boolean outLine) {
        this.outLine = outLine;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getPointSize() {
        return pointSize;
    }

    public void setPointSize(float pointSize) {
        this.pointSize = pointSize;
    }
}
