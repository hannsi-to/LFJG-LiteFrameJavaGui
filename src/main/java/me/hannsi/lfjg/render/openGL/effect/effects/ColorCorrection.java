package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.render.openGL.system.bufferObject.VAO;
import me.hannsi.lfjg.render.openGL.system.bufferObject.VBO;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.color.ColorUtil;

import java.util.ArrayList;
import java.util.List;

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
    public void pop(Frame frame, GLPolygon basePolygon) {
        super.pop(frame, basePolygon);
    }

    @Override
    public void push(Frame frame, GLPolygon basePolygon) {
        List<Color> colors = new ArrayList<>();

        basePolygon.getVboColor().flip();

        float[] colorData = new float[basePolygon.getVboColor().getFloatBuffer().remaining()];
        basePolygon.getVboColor().getFloatBuffer().get(colorData);

        for (int i = 0; i < colorData.length; i += 4) {
            float r = colorData[i];
            float g = colorData[i + 1];
            float b = colorData[i + 2];
            float a = colorData[i + 3];

            Color color = new Color(r, g, b, a);
            color = ColorUtil.applyBrightness(color, brightness);
            color = ColorUtil.applyContrast(color, contrast);
            color = ColorUtil.applySaturation(color, saturation);
            color = ColorUtil.applyHue(color, hue);
            color = ColorUtil.applyGammaCorrection(color, gamma);

            colors.add(color);
        }

        VBO vboColor = new VBO(basePolygon.getVboColor().getVertices(), basePolygon.getVboColor().getSize());
        for (Color color : colors) {
            vboColor.put(color);
        }

        VAO vaoColor = new VAO(vboColor);

        basePolygon.getVaoRendering().setColor(vaoColor);

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
