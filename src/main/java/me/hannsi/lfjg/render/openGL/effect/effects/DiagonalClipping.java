package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

public class DiagonalClipping extends EffectBase {
    private Vector2f resolution;
    private float centerX;
    private float centerY;
    private float clipAngle;
    private float blurWidth;
    private boolean invertClip;

    public DiagonalClipping(Vector2f resolution, float centerX, float centerY, float clipAngle, float blurWidth, boolean invertClip) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/DiagonalClipping.fsh"), true, 15, "DiagonalClipping");

        this.resolution = resolution;
        this.centerX = centerX;
        this.centerY = centerY;
        this.clipAngle = clipAngle;
        this.blurWidth = blurWidth;
        this.invertClip = invertClip;
    }

    public DiagonalClipping(Vector2f resolution, double centerX, double centerY, double clipAngle, double blurWidth, boolean invertClip) {
        this(resolution, (float) centerX, (float) centerY, (float) clipAngle, (float) blurWidth, invertClip);
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
        getFrameBuffer().getShaderProgramFBO().setUniform2f("clipCenter", new Vector2f((centerX / resolution.x) * 2.0f - 1.0f, (centerY / resolution.y) * 2.0f - 1.0f));
        getFrameBuffer().getShaderProgramFBO().setUniform1f("clipAngle", clipAngle);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("blurWidth", blurWidth);
        getFrameBuffer().getShaderProgramFBO().setUniformBoolean("invertClip", invertClip);

        super.setUniform(baseGLObject);
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
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
