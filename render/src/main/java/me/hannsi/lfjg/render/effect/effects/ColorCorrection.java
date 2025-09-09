package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

public class ColorCorrection extends EffectBase {
    private float brightness = 0.5f;
    private float contrast = 0.5f;
    private float saturation = 0.5f;
    private float hue = 0.5f;

    ColorCorrection(String name) {
        super(name, false);
    }

    public static ColorCorrection createColorCorrection(String name) {
        return new ColorCorrection(name);
    }

    public ColorCorrection brightness(float brightness) {
        this.brightness = brightness;
        return this;
    }

    public ColorCorrection brightness(double brightness) {
        this.brightness = (float) brightness;
        return this;
    }

    public ColorCorrection brightness(int brightness) {
        this.brightness = brightness / 255f;
        return this;
    }

    public ColorCorrection contrast(float contrast) {
        this.contrast = contrast;
        return this;
    }

    public ColorCorrection contrast(double contrast) {
        this.contrast = (float) contrast;
        return this;
    }

    public ColorCorrection contrast(int contrast) {
        this.contrast = contrast / 255f;
        return this;
    }

    public ColorCorrection saturation(float saturation) {
        this.saturation = saturation;
        return this;
    }

    public ColorCorrection saturation(double saturation) {
        this.saturation = (float) saturation;
        return this;
    }

    public ColorCorrection saturation(int saturation) {
        this.saturation = saturation / 255f;
        return this;
    }

    public ColorCorrection hue(float hue) {
        this.hue = hue;
        return this;
    }

    public ColorCorrection hue(double hue) {
        this.hue = (float) hue;
        return this;
    }

    public ColorCorrection hue(int hue) {
        this.hue = hue / 255f;
        return this;
    }

    @Override
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        LFJGRenderContext.shaderProgram.setUniform("fragmentShaderType", UploadUniformType.PER_FRAME, FragmentShaderType.COLOR_CORRECTION.getId());
        LFJGRenderContext.shaderProgram.setUniform("colorCorrectionBrightness", UploadUniformType.ON_CHANGE, brightness);
        LFJGRenderContext.shaderProgram.setUniform("colorCorrectionContrast", UploadUniformType.ON_CHANGE, contrast);
        LFJGRenderContext.shaderProgram.setUniform("colorCorrectionSaturation", UploadUniformType.ON_CHANGE, saturation);
        LFJGRenderContext.shaderProgram.setUniform("colorCorrectionHue", UploadUniformType.ON_CHANGE, hue);

        super.drawFrameBuffer(latestFrameBuffer);
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public float getContrast() {
        return contrast;
    }

    public void setContrast(float contrast) {
        this.contrast = contrast;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public float getHue() {
        return hue;
    }

    public void setHue(float hue) {
        this.hue = hue;
    }
}
