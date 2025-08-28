package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector3f;

import static me.hannsi.lfjg.render.LFJGRenderContext.shaderProgram;

public class ChromaKey extends EffectBase {
    private Color chromaKeyColor = Color.GREEN;
    private float hueRange = 0.1f;
    private float saturationRange = 0.3f;
    private float boundarySmoothness = 0.05f;
    private Color colorAdjustment = Color.of(0, 0, 0, 0);

    ChromaKey(String name) {
        super(name,false);
    }

    public static ChromaKey createChromaKey(String name) {
        return new ChromaKey(name);
    }

    public ChromaKey chromaKeyColor(Color chromaKeyColor) {
        this.chromaKeyColor = chromaKeyColor;
        return this;
    }

    public ChromaKey hueRange(float hueRange) {
        this.hueRange = hueRange;
        return this;
    }

    public ChromaKey hueRange(double hueRange) {
        this.hueRange = (float) hueRange;
        return this;
    }

    public ChromaKey hueRange(int hueRange) {
        this.hueRange = hueRange / 255f;
        return this;
    }

    public ChromaKey saturationRange(float saturationRange) {
        this.saturationRange = saturationRange;
        return this;
    }

    public ChromaKey saturationRange(double saturationRange) {
        this.saturationRange = (float) saturationRange;
        return this;
    }

    public ChromaKey saturationRange(int saturationRange) {
        this.saturationRange = saturationRange / 255f;
        return this;
    }

    public ChromaKey boundarySmoothness(float boundarySmoothness) {
        this.boundarySmoothness = boundarySmoothness;
        return this;
    }

    public ChromaKey boundarySmoothness(double boundarySmoothness) {
        this.boundarySmoothness = (float) boundarySmoothness;
        return this;
    }

    public ChromaKey colorAdjustment(Color colorAdjustment) {
        this.chromaKeyColor = colorAdjustment;
        return this;
    }

    @Override
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.PER_FRAME, FragmentShaderType.CHROMA_KEY.getId());
        shaderProgram.setUniform("chromaKeyColor", UploadUniformType.ON_CHANGE, new Vector3f(chromaKeyColor.getRedF(), chromaKeyColor.getGreenF(), chromaKeyColor.getBlueF()));
        shaderProgram.setUniform("chromaKeyHueRange", UploadUniformType.ON_CHANGE, hueRange);
        shaderProgram.setUniform("chromaKeySaturationRange", UploadUniformType.ON_CHANGE, saturationRange);
        shaderProgram.setUniform("chromaKeyBoundarySmoothness", UploadUniformType.ON_CHANGE, boundarySmoothness);
        shaderProgram.setUniform("chromaKeyColorAdjustment", UploadUniformType.ON_CHANGE, new Vector3f(colorAdjustment.getRedF(), colorAdjustment.getGreenF(), colorAdjustment.getBlueF()));

        super.drawFrameBuffer(latestFrameBuffer);
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
