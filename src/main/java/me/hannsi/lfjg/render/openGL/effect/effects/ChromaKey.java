package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class ChromaKey extends EffectBase {
    private Color chromaKeyColor;
    private float hueRange;
    private float saturationRange;
    private float boundarySmoothness;
    private Color colorAdjustment;

    public ChromaKey(Vector2f resolution, Color chromaKeyColor, float hueRange, float saturationRange, float boundarySmoothness, Color colorAdjustment) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/ChromaKey.fsh"), true, 12, "ChromaKey");

        this.chromaKeyColor = chromaKeyColor;
        this.hueRange = hueRange;
        this.saturationRange = saturationRange;
        this.boundarySmoothness = boundarySmoothness;
        this.colorAdjustment = colorAdjustment;
    }

    public ChromaKey(Vector2f resolution, Color chromaKeyColor, double hueRange, double saturationRange, double boundarySmoothness, Color colorAdjustment) {
        this(resolution, chromaKeyColor, (float) hueRange, (float) saturationRange, (float) boundarySmoothness, colorAdjustment);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("chromaKeyColor", new Vector3f(chromaKeyColor.getRedF(), chromaKeyColor.getGreenF(), chromaKeyColor.getBlueF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("hueRange", hueRange);
        getFrameBuffer().getShaderProgramFBO().setUniform("saturationRange", saturationRange);
        getFrameBuffer().getShaderProgramFBO().setUniform("boundarySmoothness", boundarySmoothness);
        getFrameBuffer().getShaderProgramFBO().setUniform("colorAdjustment", new Vector3f(colorAdjustment.getRedF(), colorAdjustment.getGreenF(), colorAdjustment.getBlueF()));

        super.setUniform(baseGLObject);
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
