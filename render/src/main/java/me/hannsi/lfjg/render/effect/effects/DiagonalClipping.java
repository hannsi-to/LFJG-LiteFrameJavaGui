package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.frameBuffer.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector2f;

import static me.hannsi.lfjg.render.LFJGRenderContext.shaderProgram;

public class DiagonalClipping extends EffectBase {
    private float centerX = 500;
    private float centerY = 500;
    private float clipAngle = MathHelper.toRadians(45);
    private float blurWidth = 0f;
    private boolean invertClip = false;

    DiagonalClipping(String name) {
        super(name, false);
    }

    public static DiagonalClipping createDiagonalClipping(String name) {
        return new DiagonalClipping(name);
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
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.DIAGONAL_CLIPPING.getId());
        shaderProgram.setUniform("diagonalClippingClipCenter", UploadUniformType.ON_CHANGE, new Vector2f(centerX, centerY));
        shaderProgram.setUniform("diagonalClippingClipAngle", UploadUniformType.ON_CHANGE, clipAngle);
        shaderProgram.setUniform("diagonalClippingBlurWidth", UploadUniformType.ON_CHANGE, blurWidth);
        shaderProgram.setUniform("diagonalClippingInvertClip", UploadUniformType.ON_CHANGE, invertClip);

        super.drawFrameBuffer(latestFrameBuffer);
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
