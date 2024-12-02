package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;

public class ColorCorrection extends EffectBase {
    private float brightness;
    private float contrast;
    private float saturation;
    private float hue;
    private float gamma;

    public ColorCorrection(float brightness, float contrast, float saturation, float hue, float gamma) {
        super(3, "ColorCorrection", (Class<GLObject>) null);

        this.brightness = brightness;
        this.contrast = contrast;
        this.saturation = saturation;
        this.hue = hue;
        this.gamma = gamma;
    }

    @Override
    public void push(GLObject baseGLObject) {
        //basePolygon.getShaderProgram().bind();
        //basePolygon.getShaderProgram().setUniform1f("brightness", brightness);
        //basePolygon.getShaderProgram().setUniform1f("contrast", contrast);
        //basePolygon.getShaderProgram().setUniform1f("saturation", saturation);
        //basePolygon.getShaderProgram().setUniform1f("hue", hue);
        //basePolygon.getShaderProgram().setUniform1f("gamma", gamma);
        //basePolygon.getShaderProgram().unbind();

        super.push(baseGLObject);
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
