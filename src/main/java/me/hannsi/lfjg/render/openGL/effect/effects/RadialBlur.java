package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

public class RadialBlur extends EffectBase {
    private Vector2f resolution;
    private float range;
    private float centerX;
    private float centerY;

    public RadialBlur(Vector2f resolution, float range, float centerX, float centerY) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/RadialBlur.fsh"), true, 18, "RadialBlur");

        this.resolution = resolution;
        this.range = range;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public RadialBlur(Vector2f resolution, double range, double centerX, double centerY) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/RadialBlur.fsh"), true, 18, "RadialBlur");

        this.resolution = resolution;
        this.range = (float) range;
        this.centerX = (float) centerX;
        this.centerY = (float) centerY;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("range", range);
        getFrameBuffer().getShaderProgramFBO().setUniform("centerX", centerX / resolution.x);
        getFrameBuffer().getShaderProgramFBO().setUniform("centerY", centerY / resolution.y);

        super.setUniform(baseGLObject);
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
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
}
