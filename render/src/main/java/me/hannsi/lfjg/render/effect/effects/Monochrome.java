package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector3f;

public class Monochrome extends EffectBase {
    private float intensity = 0.8f;
    private Color color = Color.DARK_GRAY;
    private boolean preserveBrightness = true;

    Monochrome() {
        super(Location.fromResource("shader/frameBuffer/filter/Monochrome.fsh"), true, 23, "Monochrome");
    }

    public static Monochrome createMonochrome() {
        return new Monochrome();
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
        getFrameBuffer().getShaderProgramFBO().setUniform("intensity", intensity);
        getFrameBuffer().getShaderProgramFBO().setUniform("color", new Vector3f(color.getRedF(), color.getGreenF(), color.getBlueF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("preserveBrightness", preserveBrightness);

        super.setUniform(baseGLObject);
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