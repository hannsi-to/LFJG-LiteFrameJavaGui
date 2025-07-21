package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector2f;

/**
 * Class representing a Diagonal Clipping effect in OpenGL.
 */
@Getter
@Setter
public class DiagonalClipping extends EffectBase {
    /**
     * -- SETTER --
     * Sets the x-coordinate of the clipping center.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the x-coordinate of the clipping center.
     *
     * @param centerX the x-coordinate of the clipping center
     * @return the x-coordinate of the clipping center
     */
    private float centerX = 500;
    /**
     * -- SETTER --
     * Sets the y-coordinate of the clipping center.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the y-coordinate of the clipping center.
     *
     * @param centerY the y-coordinate of the clipping center
     * @return the y-coordinate of the clipping center
     */
    private float centerY = 500;
    /**
     * -- SETTER --
     * Sets the angle of the clipping.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the angle of the clipping.
     *
     * @param clipAngle the angle of the clipping
     * @return the angle of the clipping
     */
    private float clipAngle = MathHelper.toRadians(45);
    /**
     * -- SETTER --
     * Sets the width of the blur.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the width of the blur.
     *
     * @param blurWidth the width of the blur
     * @return the width of the blur
     */
    private float blurWidth = 0f;
    /**
     * -- SETTER --
     * Sets whether the clipping is inverted.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if the clipping is inverted.
     *
     * @param invertClip true to invert the clipping, false otherwise
     * @return true if the clipping is inverted, false otherwise
     */
    private boolean invertClip = false;

    DiagonalClipping() {
        super(Location.fromResource("shader/frameBuffer/filter/DiagonalClipping.fsh"), true, 15, "DiagonalClipping");
    }

    public static DiagonalClipping createDiagonalClipping() {
        return new DiagonalClipping();
    }

    public DiagonalClipping centerX(float centerX) {
        this.centerX = centerX;
        return this;
    }

    public DiagonalClipping centerX(double centerX) {
        this.centerX = (float) centerX;
        return this;
    }

    public DiagonalClipping centerY(float centerY) {
        this.centerY = centerY;
        return this;
    }

    public DiagonalClipping centerY(double centerY) {
        this.centerY = (float) centerY;
        return this;
    }

    public DiagonalClipping clipAngleRadian(float clipAngleRadian) {
        this.clipAngle = clipAngleRadian;
        return this;
    }

    public DiagonalClipping clipAngleRadian(double clipAngleRadian) {
        this.clipAngle = (float) clipAngleRadian;
        return this;
    }

    public DiagonalClipping clipAngleDegree(float clipAngleDegree) {
        this.clipAngle = MathHelper.toRadians(clipAngleDegree);
        return this;
    }

    public DiagonalClipping blurWidth(float blurWidth) {
        this.blurWidth = blurWidth;
        return this;
    }

    public DiagonalClipping blurWidth(double blurWidth) {
        this.blurWidth = (float) blurWidth;
        return this;
    }

    public DiagonalClipping invertClip(boolean invertClip) {
        this.invertClip = invertClip;
        return this;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("clipCenter", new Vector2f((centerX / LFJGContext.frameBufferSize.x) * 2.0f - 1.0f, (centerY / LFJGContext.frameBufferSize.y) * 2.0f - 1.0f));
        getFrameBuffer().getShaderProgramFBO().setUniform("clipAngle", clipAngle);
        getFrameBuffer().getShaderProgramFBO().setUniform("blurWidth", blurWidth);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertClip", invertClip);

        super.setUniform(baseGLObject);
    }

}