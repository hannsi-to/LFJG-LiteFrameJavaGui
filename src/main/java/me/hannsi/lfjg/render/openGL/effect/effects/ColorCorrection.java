package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.effect.system.EffectCache;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.FrameBuffer;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

public class ColorCorrection extends EffectBase {
    private Vector2f resolution;
    private float brightness;
    private float contrast;
    private float saturation;
    private float hue;

    public ColorCorrection(Vector2f resolution, float brightness, float contrast, float saturation, float hue) {
        super(resolution,new ResourcesLocation("shader/frameBuffer/filter/ColorCorrection.fsh"),true,4, "ColorCorrection", (Class<GLObject>) null);

        this.resolution = resolution;
        this.brightness = brightness;
        this.contrast = contrast;
        this.saturation = saturation;
        this.hue = hue;
    }

    @Override
    public void frameBuffer(EffectCache effectCache, int oldIndex, GLObject glObject) {
        getFrameBuffer().getShaderProgramFBO().bind();
        getFrameBuffer().getShaderProgramFBO().setUniform1f("brightness", brightness);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("contrast", contrast);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("saturation", saturation);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("hue", hue);
        getFrameBuffer().getShaderProgramFBO().unbind();

        super.frameBuffer(effectCache, oldIndex, glObject);
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
