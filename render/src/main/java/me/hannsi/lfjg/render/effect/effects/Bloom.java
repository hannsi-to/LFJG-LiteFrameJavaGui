package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.frameBuffer.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import static me.hannsi.lfjg.render.LFJGRenderContext.SHADER_PROGRAM;

public class Bloom extends EffectBase {
    private float intensity = 1f;
    private float spread = 0f;
    private float threshold = 1f;

    Bloom(String name) {
        super(name, false);
    }

    public static Bloom createBloom(String name) {
        return new Bloom(name);
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
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        SHADER_PROGRAM.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.BLOOM.getId());
        SHADER_PROGRAM.setUniform("bloomSpread", UploadUniformType.ON_CHANGE, spread);
        SHADER_PROGRAM.setUniform("bloomIntensity", UploadUniformType.ON_CHANGE, intensity);
        SHADER_PROGRAM.setUniform("bloomThreshold", UploadUniformType.ON_CHANGE, threshold);

        super.drawFrameBuffer(latestFrameBuffer);
    }
}
