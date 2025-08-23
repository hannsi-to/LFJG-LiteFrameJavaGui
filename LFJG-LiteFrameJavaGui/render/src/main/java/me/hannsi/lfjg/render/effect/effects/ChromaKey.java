package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector3f;

public class ChromaKey extends EffectBase {
    private Color chromaKeyColor = Color.GREEN;
    private float hueRange = 0.1f;
    private float saturationRange = 0.3f;
    private float boundarySmoothness = 0.05f;
    private Color colorAdjustment = Color.of(0, 0, 0, 0);

    ChromaKey() {
        super(Location.fromResource("shader/frameBuffer/filter/ChromaKey.fsh"), true, 12, "ChromaKey");
    }

    public static ChromaKey createChromaKey() {
        return new ChromaKey();
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

    /**
     * Pushes the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        getFrameBuffer().bindFrameBuffer();

        super.frameBufferPush(baseGLObject);
    }

    /**
     * Pops the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        getFrameBuffer().unbindFrameBuffer();

        super.frameBufferPop(baseGLObject);
    }

    /**
     * Draws the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBuffer(GLObject baseGLObject) {
        getFrameBuffer().drawFrameBuffer();

        super.frameBuffer(baseGLObject);
    }

    /**
     * Sets the uniform variables for the shader program.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void setUniform(GLObject baseGLObject) {
        getFrameBuffer().getShaderProgramFBO().setUniform("chromaKeyColor", UploadUniformType.ON_CHANGE, new Vector3f(chromaKeyColor.getRedF(), chromaKeyColor.getGreenF(), chromaKeyColor.getBlueF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("hueRange", UploadUniformType.ON_CHANGE, hueRange);
        getFrameBuffer().getShaderProgramFBO().setUniform("saturationRange", UploadUniformType.ON_CHANGE, saturationRange);
        getFrameBuffer().getShaderProgramFBO().setUniform("boundarySmoothness", UploadUniformType.ON_CHANGE, boundarySmoothness);
        getFrameBuffer().getShaderProgramFBO().setUniform("colorAdjustment", UploadUniformType.ON_CHANGE, new Vector3f(colorAdjustment.getRedF(), colorAdjustment.getGreenF(), colorAdjustment.getBlueF()));

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