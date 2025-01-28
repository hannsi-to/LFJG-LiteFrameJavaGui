package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Glow extends EffectBase {
    private Vector2f resolution;
    private float intensity;
    private float threshold;
    private float spread;
    private boolean useOriginalColor;
    private Color glowColor;
    private boolean glowOnly;

    public Glow(Vector2f resolution, float intensity, float threshold, float spread, boolean useOriginalColor, Color glowColor, boolean glowOnly) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/Glow.fsh"), true, 11, "Glow");

        this.resolution = resolution;
        this.intensity = intensity;
        this.spread = spread;
        this.threshold = threshold;
        this.useOriginalColor = useOriginalColor;
        this.glowColor = glowColor;
        this.glowOnly = glowOnly;
    }

    public Glow(Vector2f resolution, float intensity, float threshold, float spread, boolean glowOnly) {
        this(resolution, intensity, threshold, spread, true, new Color(0, 0, 0, 0), glowOnly);
    }

    public Glow(Vector2f resolution, double intensity, double threshold, double spread, boolean useOriginalColor, Color glowColor, boolean glowOnly) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/Glow.fsh"), true, 11, "Glow");

        this.resolution = resolution;
        this.intensity = (float) intensity;
        this.spread = (float) spread;
        this.threshold = (float) threshold;
        this.useOriginalColor = useOriginalColor;
        this.glowColor = glowColor;
        this.glowOnly = glowOnly;
    }

    public Glow(Vector2f resolution, double intensity, double threshold, double spread, boolean glowOnly) {
        this(resolution, intensity, threshold, spread, true, new Color(0, 0, 0, 0), glowOnly);
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
        getFrameBuffer().getShaderProgramFBO().setUniform1f("intensity", intensity);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("threshold", threshold);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("spread", spread);
        getFrameBuffer().getShaderProgramFBO().setUniform3f("glowColor", new Vector3f(glowColor.getRedF(), glowColor.getGreenF(), glowColor.getBlueF()));
        getFrameBuffer().getShaderProgramFBO().setUniformBoolean("useOriginalColor", useOriginalColor);
        getFrameBuffer().getShaderProgramFBO().setUniformBoolean("glowOnly", glowOnly);

        super.setUniform(baseGLObject);
    }

    public boolean isGlowOnly() {
        return glowOnly;
    }

    public void setGlowOnly(boolean glowOnly) {
        this.glowOnly = glowOnly;
    }

    public Color getGlowColor() {
        return glowColor;
    }

    public void setGlowColor(Color glowColor) {
        this.glowColor = glowColor;
    }

    public boolean isUseOriginalColor() {
        return useOriginalColor;
    }

    public void setUseOriginalColor(boolean useOriginalColor) {
        this.useOriginalColor = useOriginalColor;
    }

    public float getSpread() {
        return spread;
    }

    public void setSpread(float spread) {
        this.spread = spread;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
    }
}
