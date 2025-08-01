package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector2i;
import org.joml.Vector3f;

public class Glow extends EffectBase {
    private Vector2i resolution = new Vector2i();
    private float intensity = 1.0f;
    private float threshold = 0.7f;
    private float spread = 3.0f;
    private boolean useOriginalColor = true;
    private Color glowColor = Color.GOLD;
    private boolean glowOnly = false;

    Glow() {
        super(Location.fromResource("shader/frameBuffer/filter/Glow.fsh"), true, 11, "Glow");
    }

    public static Glow createGlow() {
        return new Glow();
    }

    public Glow resolution(Vector2i resolution) {
        this.resolution = resolution;
        return this;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("threshold", UploadUniformType.ON_CHANGE, threshold);
        getFrameBuffer().getShaderProgramFBO().setUniform("spread", UploadUniformType.ON_CHANGE, spread);
        getFrameBuffer().getShaderProgramFBO().setUniform("glowColor", UploadUniformType.ON_CHANGE, new Vector3f(glowColor.getRedF(), glowColor.getGreenF(), glowColor.getBlueF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("useOriginalColor", UploadUniformType.ON_CHANGE, useOriginalColor);
        getFrameBuffer().getShaderProgramFBO().setUniform("glowOnly", UploadUniformType.ON_CHANGE, glowOnly);
        getFrameBuffer().getShaderProgramFBO().setUniform("texelSize", UploadUniformType.ON_CHANGE, resolution);

        super.setUniform(baseGLObject);
    }

    public Vector2i getResolution() {
        return resolution;
    }

    public void setResolution(Vector2i resolution) {
        this.resolution = resolution;
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