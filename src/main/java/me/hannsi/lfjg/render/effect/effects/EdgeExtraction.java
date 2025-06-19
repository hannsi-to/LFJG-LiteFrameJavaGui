package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import org.joml.Vector4f;

/**
 * Class representing an Edge Extraction effect in OpenGL.
 */
@Getter
@Setter
public class EdgeExtraction extends EffectBase {
    /**
     * -- SETTER --
     *  Sets the strength of the edges.
     *
     *
     * -- GETTER --
     *  Gets the strength of the edges.
     *
     @param edgeStrength the strength of the edges
      * @return the strength of the edges
     */
    private float edgeStrength;
    /**
     * -- SETTER --
     *  Sets the threshold for edge detection.
     *
     *
     * -- GETTER --
     *  Gets the threshold for edge detection.
     *
     @param threshold the threshold for edge detection
      * @return the threshold for edge detection
     */
    private float threshold;
    /**
     * -- SETTER --
     *  Sets whether luminance edge detection is enabled.
     *
     *
     * -- GETTER --
     *  Checks if luminance edge detection is enabled.
     *
     @param enableLuminanceEdge true to enable luminance edge detection, false otherwise
      * @return true if luminance edge detection is enabled, false otherwise
     */
    private boolean enableLuminanceEdge;
    /**
     * -- SETTER --
     *  Sets whether alpha edge detection is enabled.
     *
     *
     * -- GETTER --
     *  Checks if alpha edge detection is enabled.
     *
     @param enableAlphaEdge true to enable alpha edge detection, false otherwise
      * @return true if alpha edge detection is enabled, false otherwise
     */
    private boolean enableAlphaEdge;
    /**
     * -- SETTER --
     *  Sets the color of the edges.
     *
     *
     * -- GETTER --
     *  Gets the color of the edges.
     *
     @param edgeColor the color of the edges
      * @return the color of the edges
     */
    private Color edgeColor;

    /**
     * Constructs a new EdgeExtraction effect with the specified parameters.
     *
     * @param edgeStrength        the strength of the edges
     * @param threshold           the threshold for edge detection
     * @param enableLuminanceEdge whether to enable luminance edge detection
     * @param enableAlphaEdge     whether to enable alpha edge detection
     * @param edgeColor           the color of the edges
     */
    public EdgeExtraction(float edgeStrength, float threshold, boolean enableLuminanceEdge, boolean enableAlphaEdge, Color edgeColor) {
        super(new ResourcesLocation("shader/frameBuffer/filter/EdgeExtraction.fsh"), true, 14, "EdgeExtraction");

        this.edgeStrength = edgeStrength;
        this.threshold = threshold;
        this.enableLuminanceEdge = enableLuminanceEdge;
        this.enableAlphaEdge = enableAlphaEdge;
        this.edgeColor = edgeColor;
    }

    /**
     * Constructs a new EdgeExtraction effect with the specified parameters.
     *
     * @param edgeStrength        the strength of the edges
     * @param threshold           the threshold for edge detection
     * @param enableLuminanceEdge whether to enable luminance edge detection
     * @param enableAlphaEdge     whether to enable alpha edge detection
     * @param edgeColor           the color of the edges
     */
    public EdgeExtraction(double edgeStrength, double threshold, boolean enableLuminanceEdge, boolean enableAlphaEdge, Color edgeColor) {
        this((float) edgeStrength, (float) threshold, enableLuminanceEdge, enableAlphaEdge, edgeColor);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("edgeStrength", edgeStrength);
        getFrameBuffer().getShaderProgramFBO().setUniform("threshold", threshold);
        getFrameBuffer().getShaderProgramFBO().setUniform("enableLuminanceEdge", enableLuminanceEdge);
        getFrameBuffer().getShaderProgramFBO().setUniform("enableAlphaEdge", enableAlphaEdge);
        getFrameBuffer().getShaderProgramFBO().setUniform("edgeColor", new Vector4f(edgeColor.getRedF(), edgeColor.getGreenF(), edgeColor.getBlueF(), edgeColor.getAlphaF()));

        super.setUniform(baseGLObject);
    }

}