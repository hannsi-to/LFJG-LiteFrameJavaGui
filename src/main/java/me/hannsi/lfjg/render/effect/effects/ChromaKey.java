package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.Location;
import org.joml.Vector3f;

/**
 * Class representing a Chroma Key effect in OpenGL.
 */
@Setter
@Getter
public class ChromaKey extends EffectBase {
    /**
     * -- GETTER --
     * Gets the chroma key color.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the chroma key color.
     *
     * @return the chroma key color
     * @param chromaKeyColor the chroma key color
     */
    private Color chromaKeyColor = Color.GREEN;
    /**
     * -- GETTER --
     * Gets the hue range for the chroma key.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the hue range for the chroma key.
     *
     * @return the hue range for the chroma key
     * @param hueRange the hue range for the chroma key
     */
    private float hueRange = 0.1f;
    /**
     * -- GETTER --
     * Gets the saturation range for the chroma key.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the saturation range for the chroma key.
     *
     * @return the saturation range for the chroma key
     * @param saturationRange the saturation range for the chroma key
     */
    private float saturationRange = 0.3f;
    /**
     * -- GETTER --
     * Gets the boundary smoothness.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the boundary smoothness.
     *
     * @return the boundary smoothness
     * @param boundarySmoothness the boundary smoothness
     */
    private float boundarySmoothness = 0.05f;
    /**
     * -- GETTER --
     * Gets the color adjustment.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the color adjustment.
     *
     * @return the color adjustment
     * @param colorAdjustment the color adjustment
     */
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
        getFrameBuffer().getShaderProgramFBO().setUniform("chromaKeyColor", new Vector3f(chromaKeyColor.getRedF(), chromaKeyColor.getGreenF(), chromaKeyColor.getBlueF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("hueRange", hueRange);
        getFrameBuffer().getShaderProgramFBO().setUniform("saturationRange", saturationRange);
        getFrameBuffer().getShaderProgramFBO().setUniform("boundarySmoothness", boundarySmoothness);
        getFrameBuffer().getShaderProgramFBO().setUniform("colorAdjustment", new Vector3f(colorAdjustment.getRedF(), colorAdjustment.getGreenF(), colorAdjustment.getBlueF()));

        super.setUniform(baseGLObject);
    }

}