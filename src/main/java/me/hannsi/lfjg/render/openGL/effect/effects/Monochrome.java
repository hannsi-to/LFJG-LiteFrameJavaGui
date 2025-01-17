package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Monochrome extends EffectBase {
    private Vector2f resolution;
    private float intensity;
    private Color color;
    private boolean preserveBrightness;

    public Monochrome(Vector2f resolution, float intensity, Color color, boolean preserveBrightness) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/Monochrome.fsh"), true, 23, "Monochrome");

        this.resolution = resolution;
        this.intensity = intensity;
        this.color = color;
        this.preserveBrightness = preserveBrightness;
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
        getFrameBuffer().getShaderProgramFBO().bind();
        getFrameBuffer().getShaderProgramFBO().setUniform1f("intensity", intensity);
        getFrameBuffer().getShaderProgramFBO().setUniform3f("color", new Vector3f(color.getRedF(), color.getGreenF(), color.getBlueF()));
        getFrameBuffer().getShaderProgramFBO().setUniformBoolean("preserveBrightness", preserveBrightness);
        getFrameBuffer().getShaderProgramFBO().unbind();
        super.setUniform(baseGLObject);
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
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
