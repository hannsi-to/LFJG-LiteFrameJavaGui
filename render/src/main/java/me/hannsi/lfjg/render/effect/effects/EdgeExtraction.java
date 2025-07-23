package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import org.joml.Vector4f;

/**
 * Class representing an Edge Extraction effect in OpenGL.
 */
@Getter
@Setter
public class EdgeExtraction extends EffectBase {
    /**
     * -- SETTER --
     * Sets the strength of the edges.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the strength of the edges.
     *
     * @param edgeStrength the strength of the edges
     * @return the strength of the edges
     */
    private float edgeStrength = 0.5f;
    /**
     * -- SETTER --
     * Sets the threshold for edge detection.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the threshold for edge detection.
     *
     * @param threshold the threshold for edge detection
     * @return the threshold for edge detection
     */
    private float threshold = 0.1f;
    /**
     * -- SETTER --
     * Sets whether luminance edge detection is enabled.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if luminance edge detection is enabled.
     *
     * @param enableLuminanceEdge true to enable luminance edge detection, false otherwise
     * @return true if luminance edge detection is enabled, false otherwise
     */
    private boolean enableLuminanceEdge = false;
    /**
     * -- SETTER --
     * Sets whether alpha edge detection is enabled.
     * <p>
     * <p>
     * -- GETTER --
     * Checks if alpha edge detection is enabled.
     *
     * @param enableAlphaEdge true to enable alpha edge detection, false otherwise
     * @return true if alpha edge detection is enabled, false otherwise
     */
    private boolean enableAlphaEdge = true;
    /**
     * -- SETTER --
     * Sets the color of the edges.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the color of the edges.
     *
     * @param edgeColor the color of the edges
     * @return the color of the edges
     */
    private Color edgeColor = Color.BLUE;

    EdgeExtraction() {
        super(Location.fromResource("shader/frameBuffer/filter/EdgeExtraction.fsh"), true, 14, "EdgeExtraction");
    }

    public static EdgeExtraction createEdgeExtraction() {
        return new EdgeExtraction();
    }

    public EdgeExtraction edgeStrength(float edgeStrength) {
        this.edgeStrength = edgeStrength;
        return this;
    }

    public EdgeExtraction edgeStrength(double edgeStrength) {
        this.edgeStrength = (float) edgeStrength;
        return this;
    }

    public EdgeExtraction threshold(float threshold) {
        this.threshold = threshold;
        return this;
    }

    public EdgeExtraction threshold(double threshold) {
        this.threshold = (float) threshold;
        return this;
    }

    public EdgeExtraction enableLuminanceEdge(boolean enableLuminanceEdge) {
        this.enableLuminanceEdge = enableLuminanceEdge;
        return this;
    }

    public EdgeExtraction enableAlphaEdge(boolean enableAlphaEdge) {
        this.enableAlphaEdge = enableAlphaEdge;
        return this;
    }

    public EdgeExtraction edgeColor(Color edgeColor) {
        this.edgeColor = edgeColor;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("edgeStrength", edgeStrength);
        getFrameBuffer().getShaderProgramFBO().setUniform("threshold", threshold);
        getFrameBuffer().getShaderProgramFBO().setUniform("enableLuminanceEdge", enableLuminanceEdge);
        getFrameBuffer().getShaderProgramFBO().setUniform("enableAlphaEdge", enableAlphaEdge);
        getFrameBuffer().getShaderProgramFBO().setUniform("edgeColor", new Vector4f(edgeColor.getRedF(), edgeColor.getGreenF(), edgeColor.getBlueF(), edgeColor.getAlphaF()));

        super.setUniform(baseGLObject);
    }

}