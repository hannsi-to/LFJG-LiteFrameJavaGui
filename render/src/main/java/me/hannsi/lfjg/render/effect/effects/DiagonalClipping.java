package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class DiagonalClipping extends EffectBase {
    private Vector2i resolution = new Vector2i();
    private float centerX = 500;
    private float centerY = 500;
    private float clipAngle = MathHelper.toRadians(45);
    private float blurWidth = 0f;
    private boolean invertClip = false;

    DiagonalClipping() {
        super(Location.fromResource("shader/frameBuffer/filter/DiagonalClipping.fsh"), true, 15, "DiagonalClipping");
    }

    public static DiagonalClipping createDiagonalClipping() {
        return new DiagonalClipping();
    }

    public DiagonalClipping resolution(Vector2i resolution) {
        this.resolution = resolution;
        return this;
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

    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        getFrameBuffer().bindFrameBuffer();

        super.frameBufferPush(baseGLObject);
    }

    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        getFrameBuffer().unbindFrameBuffer();

        super.frameBufferPop(baseGLObject);
    }

    @Override
    public void frameBuffer(GLObject baseGLObject) {
        getFrameBuffer().drawFrameBuffer();

        super.frameBuffer(baseGLObject);
    }

    @Override
    public void setUniform(GLObject baseGLObject) {
        getFrameBuffer().getShaderProgramFBO().setUniform("clipCenter", new Vector2f((centerX / resolution.x) * 2.0f - 1.0f, (centerY / resolution.y) * 2.0f - 1.0f));
        getFrameBuffer().getShaderProgramFBO().setUniform("clipAngle", clipAngle);
        getFrameBuffer().getShaderProgramFBO().setUniform("blurWidth", blurWidth);
        getFrameBuffer().getShaderProgramFBO().setUniform("invertClip", invertClip);

        super.setUniform(baseGLObject);
    }

    public Vector2i getResolution() {
        return resolution;
    }

    public void setResolution(Vector2i resolution) {
        this.resolution = resolution;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public float getClipAngle() {
        return clipAngle;
    }

    public void setClipAngle(float clipAngle) {
        this.clipAngle = clipAngle;
    }

    public float getBlurWidth() {
        return blurWidth;
    }

    public void setBlurWidth(float blurWidth) {
        this.blurWidth = blurWidth;
    }

    public boolean isInvertClip() {
        return invertClip;
    }

    public void setInvertClip(boolean invertClip) {
        this.invertClip = invertClip;
    }
}