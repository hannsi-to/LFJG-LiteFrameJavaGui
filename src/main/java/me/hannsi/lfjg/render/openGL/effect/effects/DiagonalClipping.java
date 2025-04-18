package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import org.joml.Vector2f;

/**
 * Class representing a Diagonal Clipping effect in OpenGL.
 */
public class DiagonalClipping extends EffectBase {
    private float centerX;
    private float centerY;
    private float clipAngle;
    private float blurWidth;
    private boolean invertClip;

    /**
     * Constructs a new DiagonalClipping effect with the specified parameters.
     *
     * @param centerX    the x-coordinate of the clipping center
     * @param centerY    the y-coordinate of the clipping center
     * @param clipAngle  the angle of the clipping
     * @param blurWidth  the width of the blur
     * @param invertClip whether to invert the clipping
     */
    public DiagonalClipping(float centerX, float centerY, float clipAngle, float blurWidth, boolean invertClip) {
        super(new ResourcesLocation("shader/frameBuffer/filter/DiagonalClipping.fsh"), true, 15, "DiagonalClipping");

        this.centerX = centerX;
        this.centerY = centerY;
        this.clipAngle = clipAngle;
        this.blurWidth = blurWidth;
        this.invertClip = invertClip;
    }

    /**
     * Constructs a new DiagonalClipping effect with the specified parameters.
     *
     * @param centerX    the x-coordinate of the clipping center
     * @param centerY    the y-coordinate of the clipping center
     * @param clipAngle  the angle of the clipping
     * @param blurWidth  the width of the blur
     * @param invertClip whether to invert the clipping
     */
    public DiagonalClipping(double centerX, double centerY, double clipAngle, double blurWidth, boolean invertClip) {
        this((float) centerX, (float) centerY, (float) clipAngle, (float) blurWidth, invertClip);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("clipCenter", new Vector2f((centerX / LFJGContext.resolution.x) * 2.0f - 1.0f, (centerY / LFJGContext.resolution.y) * 2.0f - 1.0f));
        getFrameBuffer().getShaderProgramFBO().setUniform("clipAngle", clipAngle);
        getFrameBuffer().getShaderProgramFBO().setUniform("blurWidth", blurWidth);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertClip", invertClip);

        super.setUniform(baseGLObject);
    }

    /**
     * Gets the x-coordinate of the clipping center.
     *
     * @return the x-coordinate of the clipping center
     */
    public float getCenterX() {
        return centerX;
    }

    /**
     * Sets the x-coordinate of the clipping center.
     *
     * @param centerX the x-coordinate of the clipping center
     */
    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    /**
     * Gets the y-coordinate of the clipping center.
     *
     * @return the y-coordinate of the clipping center
     */
    public float getCenterY() {
        return centerY;
    }

    /**
     * Sets the y-coordinate of the clipping center.
     *
     * @param centerY the y-coordinate of the clipping center
     */
    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    /**
     * Gets the angle of the clipping.
     *
     * @return the angle of the clipping
     */
    public float getClipAngle() {
        return clipAngle;
    }

    /**
     * Sets the angle of the clipping.
     *
     * @param clipAngle the angle of the clipping
     */
    public void setClipAngle(float clipAngle) {
        this.clipAngle = clipAngle;
    }

    /**
     * Gets the width of the blur.
     *
     * @return the width of the blur
     */
    public float getBlurWidth() {
        return blurWidth;
    }

    /**
     * Sets the width of the blur.
     *
     * @param blurWidth the width of the blur
     */
    public void setBlurWidth(float blurWidth) {
        this.blurWidth = blurWidth;
    }

    /**
     * Checks if the clipping is inverted.
     *
     * @return true if the clipping is inverted, false otherwise
     */
    public boolean isInvertClip() {
        return invertClip;
    }

    /**
     * Sets whether the clipping is inverted.
     *
     * @param invertClip true to invert the clipping, false otherwise
     */
    public void setInvertClip(boolean invertClip) {
        this.invertClip = invertClip;
    }
}