package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;

/**
 * Class representing a Radial Blur effect in OpenGL.
 */
public class RadialBlur extends EffectBase {
    private float range;
    private float centerX;
    private float centerY;

    /**
     * Constructs a new RadialBlur effect with the specified parameters.
     *
     * @param range   the range of the blur
     * @param centerX the X coordinate of the blur center
     * @param centerY the Y coordinate of the blur center
     */
    public RadialBlur(float range, float centerX, float centerY) {
        super(new ResourcesLocation("shader/frameBuffer/filter/RadialBlur.fsh"), true, 18, "RadialBlur");

        this.range = range;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /**
     * Constructs a new RadialBlur effect with the specified parameters.
     *
     * @param range   the range of the blur
     * @param centerX the X coordinate of the blur center
     * @param centerY the Y coordinate of the blur center
     */
    public RadialBlur(double range, double centerX, double centerY) {
        super(new ResourcesLocation("shader/frameBuffer/filter/RadialBlur.fsh"), true, 18, "RadialBlur");

        this.range = (float) range;
        this.centerX = (float) centerX;
        this.centerY = (float) centerY;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("range", range);
        getFrameBuffer().getShaderProgramFBO().setUniform("centerX", centerX / LFJGContext.frameBufferSize.x);
        getFrameBuffer().getShaderProgramFBO().setUniform("centerY", centerY / LFJGContext.frameBufferSize.y);

        super.setUniform(baseGLObject);
    }

    /**
     * Gets the range of the blur.
     *
     * @return the range of the blur
     */
    public float getRange() {
        return range;
    }

    /**
     * Sets the range of the blur.
     *
     * @param range the range of the blur
     */
    public void setRange(float range) {
        this.range = range;
    }

    /**
     * Gets the X coordinate of the blur center.
     *
     * @return the X coordinate of the blur center
     */
    public float getCenterX() {
        return centerX;
    }

    /**
     * Sets the X coordinate of the blur center.
     *
     * @param centerX the X coordinate of the blur center
     */
    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    /**
     * Gets the Y coordinate of the blur center.
     *
     * @return the Y coordinate of the blur center
     */
    public float getCenterY() {
        return centerY;
    }

    /**
     * Sets the Y coordinate of the blur center.
     *
     * @param centerY the Y coordinate of the blur center
     */
    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }
}