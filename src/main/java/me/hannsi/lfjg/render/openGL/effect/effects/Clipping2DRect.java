package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector4f;

/**
 * Class representing a 2D Clipping Rectangle effect in OpenGL.
 */
public class Clipping2DRect extends EffectBase {
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private boolean invert;

    /**
     * Constructs a new Clipping2DRect effect with the specified parameters.
     *
     * @param x1     the x-coordinate of the first corner
     * @param y1     the y-coordinate of the first corner
     * @param x2     the x-coordinate of the opposite corner
     * @param y2     the y-coordinate of the opposite corner
     * @param invert whether to invert the clipping region
     */
    public Clipping2DRect(float x1, float y1, float x2, float y2, boolean invert) {
        super(new ResourcesLocation("shader/frameBuffer/filter/Clipping2D.fsh"), true, 5, "Clipping2DRect", (Class<GLObject>) null);

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.invert = invert;
    }

    /**
     * Constructs a new Clipping2DRect effect with the specified parameters.
     *
     * @param x1 the x-coordinate of the first corner
     * @param y1 the y-coordinate of the first corner
     * @param x2 the x-coordinate of the opposite corner
     * @param y2 the y-coordinate of the opposite corner
     */
    public Clipping2DRect(float x1, float y1, float x2, float y2) {
        this(x1, y1, x2, y2, false);
    }

    /**
     * Constructs a new Clipping2DRect effect with the specified parameters.
     *
     * @param x1     the x-coordinate of the first corner
     * @param y1     the y-coordinate of the first corner
     * @param x2     the x-coordinate of the opposite corner
     * @param y2     the y-coordinate of the opposite corner
     * @param invert whether to invert the clipping region
     */
    public Clipping2DRect(double x1, double y1, double x2, double y2, boolean invert) {
        super(new ResourcesLocation("shader/frameBuffer/filter/Clipping2D.fsh"), true, 5, "Clipping2DRect", (Class<GLObject>) null);

        this.x1 = (float) x1;
        this.y1 = (float) y1;
        this.x2 = (float) x2;
        this.y2 = (float) y2;
        this.invert = invert;
    }

    /**
     * Constructs a new Clipping2DRect effect with the specified parameters.
     *
     * @param x1 the x-coordinate of the first corner
     * @param y1 the y-coordinate of the first corner
     * @param x2 the x-coordinate of the opposite corner
     * @param y2 the y-coordinate of the opposite corner
     */
    public Clipping2DRect(double x1, double y1, double x2, double y2) {
        this(x1, y1, x2, y2, false);
    }

    /**
     * Pushes the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        getFrameBuffer().bindFrameBuffer();

        super.frameBufferPush(baseGLObject);
    }

    /**
     * Pops the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        getFrameBuffer().unbindFrameBuffer();

        super.frameBufferPop(baseGLObject);
    }

    /**
     * Draws the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBuffer(GLObject baseGLObject) {
        getFrameBuffer().drawFrameBuffer();

        super.frameBuffer(baseGLObject);
    }

    /**
     * Sets the uniform variables for the shader program.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void setUniform(GLObject baseGLObject) {
        getFrameBuffer().getShaderProgramFBO().setUniform("resolution", LFJGContext.resolution);
        getFrameBuffer().getShaderProgramFBO().setUniform("clippingRect2DBool", true);
        getFrameBuffer().getShaderProgramFBO().setUniform("clippingRect2DInvert", invert);
        getFrameBuffer().getShaderProgramFBO().setUniform("clippingRect2DSize", new Vector4f(x1, y1, x2, y2));

        super.setUniform(baseGLObject);
    }

    /**
     * Gets the x-coordinate of the first corner.
     *
     * @return the x-coordinate of the first corner
     */
    public float getX1() {
        return x1;
    }

    /**
     * Sets the x-coordinate of the first corner.
     *
     * @param x1 the x-coordinate of the first corner
     */
    public void setX1(float x1) {
        this.x1 = x1;
    }

    /**
     * Gets the y-coordinate of the first corner.
     *
     * @return the y-coordinate of the first corner
     */
    public float getY1() {
        return y1;
    }

    /**
     * Sets the y-coordinate of the first corner.
     *
     * @param y1 the y-coordinate of the first corner
     */
    public void setY1(float y1) {
        this.y1 = y1;
    }

    /**
     * Gets the x-coordinate of the opposite corner.
     *
     * @return the x-coordinate of the opposite corner
     */
    public float getX2() {
        return x2;
    }

    /**
     * Sets the x-coordinate of the opposite corner.
     *
     * @param x2 the x-coordinate of the opposite corner
     */
    public void setX2(float x2) {
        this.x2 = x2;
    }

    /**
     * Gets the y-coordinate of the opposite corner.
     *
     * @return the y-coordinate of the opposite corner
     */
    public float getY2() {
        return y2;
    }

    /**
     * Sets the y-coordinate of the opposite corner.
     *
     * @param y2 the y-coordinate of the opposite corner
     */
    public void setY2(float y2) {
        this.y2 = y2;
    }

    /**
     * Checks if the clipping region is inverted.
     *
     * @return true if the clipping region is inverted, false otherwise
     */
    public boolean isInvert() {
        return invert;
    }

    /**
     * Sets whether the clipping region is inverted.
     *
     * @param invert true to invert the clipping region, false otherwise
     */
    public void setInvert(boolean invert) {
        this.invert = invert;
    }
}