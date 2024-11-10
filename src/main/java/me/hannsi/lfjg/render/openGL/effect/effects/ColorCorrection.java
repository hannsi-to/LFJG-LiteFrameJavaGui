package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLPolygon;

public class ColorCorrection extends EffectBase {
    private float brightness;
    private float contrast;
    private float saturation;
    private float hue;
    private float gamma;

    public ColorCorrection(float brightness, float contrast, float saturation, float hue, float gamma) {
        super(3, "ColorCorrection", null);

        this.brightness = brightness;
        this.contrast = contrast;
        this.saturation = saturation;
        this.hue = hue;
        this.gamma = gamma;
    }

    @Override
    public void push(Frame frame, GLPolygon basePolygon) {
        basePolygon.getVaoRendering().getShaderProgram().bind();
        basePolygon.getVaoRendering().getShaderProgram().setUniform1f("brightness", brightness);
        basePolygon.getVaoRendering().getShaderProgram().setUniform1f("contrast", contrast);
        basePolygon.getVaoRendering().getShaderProgram().setUniform1f("saturation", saturation);
        basePolygon.getVaoRendering().getShaderProgram().setUniform1f("hue", hue);
        basePolygon.getVaoRendering().getShaderProgram().setUniform1f("gamma", gamma);
        basePolygon.getVaoRendering().getShaderProgram().unbind();

        super.push(frame, basePolygon);
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

    public float getGamma() {
        return gamma;
    }

    public void setGamma(float gamma) {
        this.gamma = gamma;
    }
}
