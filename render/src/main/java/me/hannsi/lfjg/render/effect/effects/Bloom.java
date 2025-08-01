package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

public class Bloom extends EffectBase {
    private float intensity = 1f;
    private float spread = 0f;
    private float threshold = 1f;

    Bloom() {
        super(Location.fromResource("shader/frameBuffer/filter/Bloom.fsh"), true, 9, "Bloom");
    }

    public static Bloom createBloom() {
        return new Bloom();
    }

    public Bloom intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public Bloom intensity(double intensity) {
        this.intensity = (float) intensity;
        return this;
    }

    public Bloom spread(float spread) {
        this.spread = spread;
        return this;
    }

    public Bloom spread(double spread) {
        this.spread = (float) spread;
        return this;
    }

    public Bloom threshold(float threshold) {
        this.threshold = threshold;
        return this;
    }

    public Bloom threshold(double threshold) {
        this.threshold = (float) threshold;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("intensity", UploadUniformType.ON_CHANGE, intensity);
        getFrameBuffer().getShaderProgramFBO().setUniform("spread", UploadUniformType.ON_CHANGE, spread);
        getFrameBuffer().getShaderProgramFBO().setUniform("threshold", UploadUniformType.ON_CHANGE, threshold);

        super.setUniform(baseGLObject);
    }

}