package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector3f;

public class Monochrome extends EffectBase {
    private float intensity = 1f;
    private Color color = Color.DARK_GRAY;
    private boolean preserveBrightness = true;

    Monochrome(String name) {
        super(name, false);
    }

    public static Monochrome createMonochrome(String name) {
        return new Monochrome(name);
    }

    public Monochrome intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public Monochrome intensity(double intensity) {
        this.intensity = (float) intensity;
        return this;
    }

    public Monochrome color(Color color) {
        this.color = color;
        return this;
    }

    public Monochrome preserveBrightness(boolean preserveBrightness) {
        this.preserveBrightness = preserveBrightness;
        return this;
    }

    @Override
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        LFJGRenderContext.shaderProgram.setUniform("fragmentShaderType", UploadUniformType.PER_FRAME, FragmentShaderType.MONOCHROME.getId());
        LFJGRenderContext.shaderProgram.setUniform("monochromeIntensity", UploadUniformType.ON_CHANGE, intensity);
        LFJGRenderContext.shaderProgram.setUniform("monochromeColor", UploadUniformType.ON_CHANGE, new Vector3f(color.getRedF(), color.getGreenF(), color.getBlueF()));
        LFJGRenderContext.shaderProgram.setUniform("monochromePreserveBrightness", UploadUniformType.ON_CHANGE, preserveBrightness);

        super.drawFrameBuffer(latestFrameBuffer);
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isPreserveBrightness() {
        return preserveBrightness;
    }

    public void setPreserveBrightness(boolean preserveBrightness) {
        this.preserveBrightness = preserveBrightness;
    }
}
