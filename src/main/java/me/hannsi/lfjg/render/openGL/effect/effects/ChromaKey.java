package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class ChromaKey extends EffectBase {
    private Vector2f resolution;
    private Color chromaKeyColor;
    private float hueRange;
    private float saturationRange;
    private float boundarySmoothness;
    private Color colorAdjustment;

    public ChromaKey(Vector2f resolution, Color chromaKeyColor, float hueRange, float saturationRange, float boundarySmoothness, Color colorAdjustment) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/ChromaKey.fsh"), true, 12, "ChromaKey");

        this.resolution = resolution;
        this.chromaKeyColor = chromaKeyColor;
        this.hueRange = hueRange;
        this.saturationRange = saturationRange;
        this.boundarySmoothness = boundarySmoothness;
        this.colorAdjustment = colorAdjustment;
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

        getFrameBuffer().getShaderProgramFBO().setUniform3f("chromaKeyColor", new Vector3f(chromaKeyColor.getRedF(), chromaKeyColor.getGreenF(), chromaKeyColor.getBlueF()));
        getFrameBuffer().getShaderProgramFBO().setUniform1f("hueRange", hueRange);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("saturationRange", saturationRange);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("boundarySmoothness", boundarySmoothness);
        getFrameBuffer().getShaderProgramFBO().setUniform3f("colorAdjustment", new Vector3f(colorAdjustment.getRedF(), colorAdjustment.getGreenF(), colorAdjustment.getBlueF()));

        getFrameBuffer().getShaderProgramFBO().unbind();

        super.setUniform(baseGLObject);
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
    }

    public Color getChromaKeyColor() {
        return chromaKeyColor;
    }

    public void setChromaKeyColor(Color chromaKeyColor) {
        this.chromaKeyColor = chromaKeyColor;
    }

    public float getHueRange() {
        return hueRange;
    }

    public void setHueRange(float hueRange) {
        this.hueRange = hueRange;
    }

    public float getSaturationRange() {
        return saturationRange;
    }

    public void setSaturationRange(float saturationRange) {
        this.saturationRange = saturationRange;
    }

    public float getBoundarySmoothness() {
        return boundarySmoothness;
    }

    public void setBoundarySmoothness(float boundarySmoothness) {
        this.boundarySmoothness = boundarySmoothness;
    }

    public Color getColorAdjustment() {
        return colorAdjustment;
    }

    public void setColorAdjustment(Color colorAdjustment) {
        this.colorAdjustment = colorAdjustment;
    }
}
