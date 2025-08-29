package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector3f;

import static me.hannsi.lfjg.render.LFJGRenderContext.shaderProgram;

public class Glow extends EffectBase {
    private float intensity = 1.0f;
    private float threshold = 0.7f;
    private float spread = 3.0f;
    private boolean useOriginalColor = true;
    private Color glowColor = Color.GOLD;
    private boolean glowOnly = false;

    Glow(String name) {
        super(name, false);
    }

    public static Glow createGlow(String name) {
        return new Glow(name);
    }

    public Glow intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public Glow intensity(double intensity) {
        this.intensity = (float) intensity;
        return this;
    }

    public Glow threshold(float threshold) {
        this.threshold = threshold;
        return this;
    }

    public Glow threshold(double threshold) {
        this.threshold = (float) threshold;
        return this;
    }

    public Glow spread(float spread) {
        this.spread = spread;
        return this;
    }

    public Glow spread(double spread) {
        this.spread = (float) spread;
        return this;
    }

    public Glow useOriginalColor(boolean useOriginalColor) {
        this.useOriginalColor = useOriginalColor;
        return this;
    }

    public Glow glowColor(Color glowColor) {
        this.glowColor = glowColor;
        return this;
    }

    public Glow glowOnly(boolean glowOnly) {
        this.glowOnly = glowOnly;
        return this;
    }

    @Override
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.PER_FRAME, FragmentShaderType.GLOW.getId());
        shaderProgram.setUniform("glowIntensity", UploadUniformType.ON_CHANGE, intensity);
        shaderProgram.setUniform("glowThreshold", UploadUniformType.ON_CHANGE, threshold);
        shaderProgram.setUniform("glowSpread", UploadUniformType.ON_CHANGE, spread);
        shaderProgram.setUniform("glowColor", UploadUniformType.ON_CHANGE, new Vector3f(glowColor.getRedF(), glowColor.getGreenF(), glowColor.getBlueF()));
        shaderProgram.setUniform("glowUseOriginalColor", UploadUniformType.ON_CHANGE, useOriginalColor);
        shaderProgram.setUniform("glowOnly", UploadUniformType.ON_CHANGE, glowOnly);

        super.drawFrameBuffer(latestFrameBuffer);
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public float getSpread() {
        return spread;
    }

    public void setSpread(float spread) {
        this.spread = spread;
    }

    public boolean isUseOriginalColor() {
        return useOriginalColor;
    }

    public void setUseOriginalColor(boolean useOriginalColor) {
        this.useOriginalColor = useOriginalColor;
    }

    public Color getGlowColor() {
        return glowColor;
    }

    public void setGlowColor(Color glowColor) {
        this.glowColor = glowColor;
    }

    public boolean isGlowOnly() {
        return glowOnly;
    }

    public void setGlowOnly(boolean glowOnly) {
        this.glowOnly = glowOnly;
    }
}
