package me.hannsi.lfjg.render.nanoVG.system.polygon;

import me.hannsi.lfjg.utils.graphics.color.Color;

/**
 * Base class for NanoVG renderers.
 */
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

    /**
     * Constructs a new NanoVGRendererBase with the specified NanoVG context.
     *
     * @param nvg the NanoVG context
     */
    public NanoVGRendererBase(long nvg) {
        this.nvg = nvg;
    }

    /**
     * Sets the size of the renderer.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param width the width
     * @param height the height
     */
    public void setSize(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cx = x + (width / 2f);
        this.cy = y + (height / 2f);
        this.range = Math.max(width, height);
    }

    /**
     * Sets the size of the renderer using center coordinates and range.
     *
     * @param cx the center x-coordinate
     * @param cy the center y-coordinate
     * @param range the range
     */
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

    /**
     * Sets the base color of the renderer.
     *
     * @param base whether to use the base color
     * @param color1 the base color
     */
    public void setBase(boolean base, Color color1) {
        this.base = base;
        this.gradientMode = GradientMode.Normal;
        this.color1 = color1;
    }

    /**
     * Sets the outline properties of the renderer.
     *
     * @param outLine whether to use an outline
     * @param lineWidth the width of the outline
     * @param outLineColor1 the color of the outline
     */
    public void setOutLine(boolean outLine, float lineWidth, Color outLineColor1) {
        this.outLine = outLine;
        this.outLineGradientMode = GradientMode.Normal;
        this.lineWidth = lineWidth;
        this.outLineColor1 = outLineColor1;
    }

    /**
     * Sets a linear gradient for the renderer.
     *
     * @param color2 the second color for the gradient
     */
    public void setLinearGradient(Color color2) {
        this.gradientMode = GradientMode.Linear;
        this.color2 = color2;
    }

    /**
     * Sets a linear gradient for the outline.
     *
     * @param outLineColor2 the second color for the outline gradient
     */
    public void setLinearGradientOutLine(Color outLineColor2) {
        this.outLineGradientMode = GradientMode.Linear;
        this.outLineColor2 = outLineColor2;
    }

    /**
     * Sets a box gradient for the renderer.
     *
     * @param feather the feather value for the gradient
     * @param color2 the second color for the gradient
     */
    public void setBoxGradient(float feather, Color color2) {
        this.gradientMode = GradientMode.Box;
        this.feather = feather;
        this.color2 = color2;
    }

    /**
     * Sets a box gradient for the outline.
     *
     * @param outLineFeather the feather value for the outline gradient
     * @param outLineColor2 the second color for the outline gradient
     */
    public void setBoxGradientOutLine(float outLineFeather, Color outLineColor2) {
        this.outLineGradientMode = GradientMode.Box;
        this.outLineFeather = outLineFeather;
        this.outLineColor2 = outLineColor2;
    }

    /**
     * Sets a radial gradient for the renderer.
     *
     * @param inRange the inner range for the gradient
     * @param outRange the outer range for the gradient
     * @param color2 the second color for the gradient
     */
    public void setRadialGradient(float inRange, float outRange, Color color2) {
        this.gradientMode = GradientMode.Radial;
        this.inRange = inRange;
        this.outRange = outRange;
        this.color2 = color2;
    }

    /**
     * Sets a radial gradient for the outline.
     *
     * @param outLineInRange the inner range for the outline gradient
     * @param outLineOutRange the outer range for the outline gradient
     * @param outLineColor2 the second color for the outline gradient
     */
    public void setRadialGradientOutLine(float outLineInRange, float outLineOutRange, Color outLineColor2) {
        this.outLineGradientMode = GradientMode.Radial;
        this.outLineInRange = outLineInRange;
        this.outLineOutRange = outLineOutRange;
        this.outLineColor2 = outLineColor2;
    }

    /**
     * Draws the renderer.
     */
    public void draw() {

    }

    // Getter and setter methods for various properties

    /**
     * Gets the NanoVG context.
     *
     * @return the NanoVG context
     */
    public long getNvg() {
        return nvg;
    }

    /**
     * Sets the NanoVG context.
     *
     * @param nvg the NanoVG context
     */
    public void setNvg(long nvg) {
        this.nvg = nvg;
    }

    /**
     * Gets the x-coordinate.
     *
     * @return the x-coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the x-coordinate.
     *
     * @param x the x-coordinate
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate.
     *
     * @return the y-coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the y-coordinate.
     *
     * @param y the y-coordinate
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * Sets the width.
     *
     * @param width the width
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the height.
     *
     * @param height the height
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Gets the center x-coordinate.
     *
     * @return the center x-coordinate
     */
    public float getCx() {
        return cx;
    }

    /**
     * Sets the center x-coordinate.
     *
     * @param cx the center x-coordinate
     */
    public void setCx(float cx) {
        this.cx = cx;
    }

    /**
     * Gets the center y-coordinate.
     *
     * @return the center y-coordinate
     */
    public float getCy() {
        return cy;
    }

    /**
     * Sets the center y-coordinate.
     *
     * @param cy the center y-coordinate
     */
    public void setCy(float cy) {
        this.cy = cy;
    }

    /**
     * Gets the line width.
     *
     * @return the line width
     */
    public float getLineWidth() {
        return lineWidth;
    }

    /**
     * Sets the line width.
     *
     * @param lineWidth the line width
     */
    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**
     * Gets the gradient mode.
     *
     * @return the gradient mode
     */
    public GradientMode getGradientMode() {
        return gradientMode;
    }

    /**
     * Sets the gradient mode.
     *
     * @param gradientMode the gradient mode
     */
    public void setGradientMode(GradientMode gradientMode) {
        this.gradientMode = gradientMode;
    }

    /**
     * Gets the outline gradient mode.
     *
     * @return the outline gradient mode
     */
    public GradientMode getOutLineGradientMode() {
        return outLineGradientMode;
    }

    /**
     * Sets the outline gradient mode.
     *
     * @param outLineGradientMode the outline gradient mode
     */
    public void setOutLineGradientMode(GradientMode outLineGradientMode) {
        this.outLineGradientMode = outLineGradientMode;
    }

    /**
     * Gets the feather value.
     *
     * @return the feather value
     */
    public float getFeather() {
        return feather;
    }

    /**
     * Sets the feather value.
     *
     * @param feather the feather value
     */
    public void setFeather(float feather) {
        this.feather = feather;
    }

    /**
     * Gets the outline feather value.
     *
     * @return the outline feather value
     */
    public float getOutLineFeather() {
        return outLineFeather;
    }

    /**
     * Sets the outline feather value.
     *
     * @param outLineFeather the outline feather value
     */
    public void setOutLineFeather(float outLineFeather) {
        this.outLineFeather = outLineFeather;
    }

    /**
     * Gets the inner range for the gradient.
     *
     * @return the inner range for the gradient
     */
    public float getInRange() {
        return inRange;
    }

    /**
     * Sets the inner range for the gradient.
     *
     * @param inRange the inner range for the gradient
     */
    public void setInRange(float inRange) {
        this.inRange = inRange;
    }

    /**
     * Gets the inner range for the outline gradient.
     *
     * @return the inner range for the outline gradient
     */
    public float getOutLineInRange() {
        return outLineInRange;
    }

    /**
     * Sets the inner range for the outline gradient.
     *
     * @param outLineInRange the inner range for the outline gradient
     */
    public void setOutLineInRange(float outLineInRange) {
        this.outLineInRange = outLineInRange;
    }

    /**
     * Gets the outer range for the gradient.
     *
     * @return the outer range for the gradient
     */
    public float getOutRange() {
        return outRange;
    }

    /**
     * Sets the outer range for the gradient.
     *
     * @param outRange the outer range for the gradient
     */
    public void setOutRange(float outRange) {
        this.outRange = outRange;
    }

    /**
     * Gets the outer range for the outline gradient.
     *
     * @return the outer range for the outline gradient
     */
    public float getOutLineOutRange() {
        return outLineOutRange;
    }

    /**
     * Sets the outer range for the outline gradient.
     *
     * @param outLineOutRange the outer range for the outline gradient
     */
    public void setOutLineOutRange(float outLineOutRange) {
        this.outLineOutRange = outLineOutRange;
    }

    /**
     * Gets the first color.
     *
     * @return the first color
     */
    public Color getColor1() {
        return color1;
    }

    /**
     * Sets the first color.
     *
     * @param color1 the first color
     */
    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    /**
     * Gets the second color.
     *
     * @return the second color
     */
    public Color getColor2() {
        return color2;
    }

    /**
     * Sets the second color.
     *
     * @param color2 the second color
     */
    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    /**
     * Gets the first outline color.
     *
     * @return the first outline color
     */
    public Color getOutLineColor1() {
        return outLineColor1;
    }

    /**
     * Sets the first outline color.
     *
     * @param outLineColor1 the first outline color
     */
    public void setOutLineColor1(Color outLineColor1) {
        this.outLineColor1 = outLineColor1;
    }

    /**
     * Gets the second outline color.
     *
     * @return the second outline color
     */
    public Color getOutLineColor2() {
        return outLineColor2;
    }

    /**
     * Sets the second outline color.
     *
     * @param outLineColor2 the second outline color
     */
    public void setOutLineColor2(Color outLineColor2) {
        this.outLineColor2 = outLineColor2;
    }

    /**
     * Checks if the base color is used.
     *
     * @return true if the base color is used, false otherwise
     */
    public boolean isBase() {
        return base;
    }

    /**
     * Sets whether to use the base color.
     *
     * @param base true to use the base color, false otherwise
     */
    public void setBase(boolean base) {
        this.base = base;
    }

    /**
     * Checks if the outline is used.
     *
     * @return true if the outline is used, false otherwise
     */
    public boolean isOutLine() {
        return outLine;
    }

    /**
     * Sets whether to use the outline.
     *
     * @param outLine true to use the outline, false otherwise
     */
    public void setOutLine(boolean outLine) {
        this.outLine = outLine;
    }

    /**
     * Gets the range.
     *
     * @return the range
     */
    public float getRange() {
        return range;
    }

    /**
     * Sets the range.
     *
     * @param range the range
     */
    public void setRange(float range) {
        this.range = range;
    }

    /**
     * Gets the point size.
     *
     * @return the point size
     */
    public float getPointSize() {
        return pointSize;
    }

    /**
     * Sets the point size.
     *
     * @param pointSize the point size
     */
    public void setPointSize(float pointSize) {
        this.pointSize = pointSize;
    }
}