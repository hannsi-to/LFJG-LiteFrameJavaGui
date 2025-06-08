package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import org.joml.Vector3f;

/**
 * Class representing a Chroma Key effect in OpenGL.
 */
public class ChromaKey extends EffectBase {
    private Color chromaKeyColor;
    private float hueRange;
    private float saturationRange;
    private float boundarySmoothness;
    private Color colorAdjustment;

    /**
     * Constructs a new ChromaKey effect with the specified parameters.
     *
     * @param chromaKeyColor     the chroma key color
     * @param hueRange           the hue range for the chroma key
     * @param saturationRange    the saturation range for the chroma key
     * @param boundarySmoothness the smoothness of the boundary
     * @param colorAdjustment    the color adjustment
     */
    public ChromaKey(Color chromaKeyColor, float hueRange, float saturationRange, float boundarySmoothness, Color colorAdjustment) {
        super(new ResourcesLocation("shader/frameBuffer/filter/ChromaKey.fsh"), true, 12, "ChromaKey");

        this.chromaKeyColor = chromaKeyColor;
        this.hueRange = hueRange;
        this.saturationRange = saturationRange;
        this.boundarySmoothness = boundarySmoothness;
        this.colorAdjustment = colorAdjustment;
    }

    /**
     * Constructs a new ChromaKey effect with the specified parameters.
     *
     * @param chromaKeyColor     the chroma key color
     * @param hueRange           the hue range for the chroma key
     * @param saturationRange    the saturation range for the chroma key
     * @param boundarySmoothness the smoothness of the boundary
     * @param colorAdjustment    the color adjustment
     */
    public ChromaKey(Color chromaKeyColor, double hueRange, double saturationRange, double boundarySmoothness, Color colorAdjustment) {
        this(chromaKeyColor, (float) hueRange, (float) saturationRange, (float) boundarySmoothness, colorAdjustment);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("chromaKeyColor", new Vector3f(chromaKeyColor.getRedF(), chromaKeyColor.getGreenF(), chromaKeyColor.getBlueF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("hueRange", hueRange);
        getFrameBuffer().getShaderProgramFBO().setUniform("saturationRange", saturationRange);
        getFrameBuffer().getShaderProgramFBO().setUniform("boundarySmoothness", boundarySmoothness);
        getFrameBuffer().getShaderProgramFBO().setUniform("colorAdjustment", new Vector3f(colorAdjustment.getRedF(), colorAdjustment.getGreenF(), colorAdjustment.getBlueF()));

        super.setUniform(baseGLObject);
    }

    /**
     * Gets the chroma key color.
     *
     * @return the chroma key color
     */
    public Color getChromaKeyColor() {
        return chromaKeyColor;
    }

    /**
     * Sets the chroma key color.
     *
     * @param chromaKeyColor the chroma key color
     */
    public void setChromaKeyColor(Color chromaKeyColor) {
        this.chromaKeyColor = chromaKeyColor;
    }

    /**
     * Gets the hue range for the chroma key.
     *
     * @return the hue range for the chroma key
     */
    public float getHueRange() {
        return hueRange;
    }

    /**
     * Sets the hue range for the chroma key.
     *
     * @param hueRange the hue range for the chroma key
     */
    public void setHueRange(float hueRange) {
        this.hueRange = hueRange;
    }

    /**
     * Gets the saturation range for the chroma key.
     *
     * @return the saturation range for the chroma key
     */
    public float getSaturationRange() {
        return saturationRange;
    }

    /**
     * Sets the saturation range for the chroma key.
     *
     * @param saturationRange the saturation range for the chroma key
     */
    public void setSaturationRange(float saturationRange) {
        this.saturationRange = saturationRange;
    }

    /**
     * Gets the boundary smoothness.
     *
     * @return the boundary smoothness
     */
    public float getBoundarySmoothness() {
        return boundarySmoothness;
    }

    /**
     * Sets the boundary smoothness.
     *
     * @param boundarySmoothness the boundary smoothness
     */
    public void setBoundarySmoothness(float boundarySmoothness) {
        this.boundarySmoothness = boundarySmoothness;
    }

    /**
     * Gets the color adjustment.
     *
     * @return the color adjustment
     */
    public Color getColorAdjustment() {
        return colorAdjustment;
    }

    /**
     * Sets the color adjustment.
     *
     * @param colorAdjustment the color adjustment
     */
    public void setColorAdjustment(Color colorAdjustment) {
        this.colorAdjustment = colorAdjustment;
    }
}