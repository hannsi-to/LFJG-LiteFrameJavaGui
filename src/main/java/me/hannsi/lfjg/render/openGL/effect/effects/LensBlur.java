package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

public class LensBlur extends EffectBase {
    private Vector2f resolution;
    private float range;
    private float intensity;

    public LensBlur(Vector2f resolution, float range, float intensity) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/LensBlur.fsh"), true, 20, "LensBlur");

        this.resolution = resolution;
        this.range = range;
        this.intensity = intensity;
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
        getFrameBuffer().getShaderProgramFBO().bind();
        getFrameBuffer().getShaderProgramFBO().setUniform1f("range", range);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("intensity", intensity);
        getFrameBuffer().getShaderProgramFBO().unbind();
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

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}
