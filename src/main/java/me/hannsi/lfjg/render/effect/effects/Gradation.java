package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.utils.type.types.BlendType;
import org.joml.Vector2f;
import org.joml.Vector4f;

/**
 * Class representing a Gradation effect in OpenGL.
 */
@Getter
@Setter
public class Gradation extends EffectBase {
    /**
     * -- SETTER --
     *  Sets the x-coordinate of the center.
     *
     *
     * -- GETTER --
     *  Gets the x-coordinate of the center.
     *
     @param centerX the x-coordinate of the center
      * @return the x-coordinate of the center
     */
    private float centerX;
    /**
     * -- SETTER --
     *  Sets the y-coordinate of the center.
     *
     *
     * -- GETTER --
     *  Gets the y-coordinate of the center.
     *
     @param centerY the y-coordinate of the center
      * @return the y-coordinate of the center
     */
    private float centerY;
    /**
     * -- SETTER --
     *  Sets the angle of the gradation.
     *
     *
     * -- GETTER --
     *  Gets the angle of the gradation.
     *
     @param angle the angle of the gradation
      * @return the angle of the gradation
     */
    private float angle;
    /**
     * -- SETTER --
     *  Sets the width of the gradation.
     *
     *
     * -- GETTER --
     *  Gets the width of the gradation.
     *
     @param width the width of the gradation
      * @return the width of the gradation
     */
    private float width;
    /**
     * -- SETTER --
     *  Sets the shape mode of the gradation.
     *
     *
     * -- GETTER --
     *  Gets the shape mode of the gradation.
     *
     @param shapeMode the shape mode of the gradation
      * @return the shape mode of the gradation
     */
    private ShapeMode shapeMode;
    /**
     * -- SETTER --
     *  Sets the blend type of the gradation.
     *
     *
     * -- GETTER --
     *  Gets the blend type of the gradation.
     *
     @param blendType the blend type of the gradation
      * @return the blend type of the gradation
     */
    private BlendType blendType;
    /**
     * -- SETTER --
     *  Sets the start color of the gradation.
     *
     *
     * -- GETTER --
     *  Gets the start color of the gradation.
     *
     @param startColor the start color of the gradation
      * @return the start color of the gradation
     */
    private Color startColor;
    /**
     * -- SETTER --
     *  Sets the end color of the gradation.
     *
     *
     * -- GETTER --
     *  Gets the end color of the gradation.
     *
     @param endColor the end color of the gradation
      * @return the end color of the gradation
     */
    private Color endColor;
    /**
     * -- SETTER --
     *  Sets the intensity of the gradation.
     *
     *
     * -- GETTER --
     *  Gets the intensity of the gradation.
     *
     @param intensity the intensity of the gradation
      * @return the intensity of the gradation
     */
    private float intensity;

    /**
     * Constructs a new Gradation effect with the specified parameters.
     *
     * @param centerX    the x-coordinate of the center
     * @param centerY    the y-coordinate of the center
     * @param angle      the angle of the gradation
     * @param width      the width of the gradation
     * @param shapeMode  the shape mode of the gradation
     * @param blendType  the blend type of the gradation
     * @param startColor the start color of the gradation
     * @param endColor   the end color of the gradation
     * @param intensity  the intensity of the gradation
     */
    public Gradation(float centerX, float centerY, float angle, float width, ShapeMode shapeMode, BlendType blendType, Color startColor, Color endColor, float intensity) {
        super(new ResourcesLocation("shader/frameBuffer/filter/Gradation.fsh"), true, 24, "Gradation");

        this.centerX = centerX;
        this.centerY = centerY;
        this.angle = angle;
        this.width = width;
        this.shapeMode = shapeMode;
        this.blendType = blendType;
        this.startColor = startColor;
        this.endColor = endColor;
        this.intensity = intensity;
    }

    /**
     * Constructs a new Gradation effect with the specified parameters.
     *
     * @param centerX    the x-coordinate of the center
     * @param centerY    the y-coordinate of the center
     * @param angle      the angle of the gradation
     * @param width      the width of the gradation
     * @param shapeMode  the shape mode of the gradation
     * @param blendType  the blend type of the gradation
     * @param startColor the start color of the gradation
     * @param endColor   the end color of the gradation
     * @param intensity  the intensity of the gradation
     */
    public Gradation(double centerX, double centerY, double angle, double width, ShapeMode shapeMode, BlendType blendType, Color startColor, Color endColor, double intensity) {
        this((float) centerX, (float) centerY, (float) angle, (float) width, shapeMode, blendType, startColor, endColor, (float) intensity);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("center", new Vector2f(centerX / LFJGContext.frameBufferSize.x, centerY / LFJGContext.frameBufferSize.y));
        getFrameBuffer().getShaderProgramFBO().setUniform("angle", angle);
        getFrameBuffer().getShaderProgramFBO().setUniform("width", width);
        getFrameBuffer().getShaderProgramFBO().setUniform1i("gradientShape", shapeMode.getId());
        getFrameBuffer().getShaderProgramFBO().setUniform1i("blendMode", blendType.getId());
        getFrameBuffer().getShaderProgramFBO().setUniform("startColor", new Vector4f(startColor.getRedF(), startColor.getGreenF(), startColor.getBlueF(), startColor.getAlphaF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("endColor", new Vector4f(endColor.getRedF(), endColor.getGreenF(), endColor.getBlueF(), endColor.getAlphaF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("intensity", intensity);

        super.setUniform(baseGLObject);
    }

    /**
     * Enum representing the shape mode of the gradation.
     */
    public enum ShapeMode implements IEnumTypeBase {
        Line("Line", 0), Circle("Circle", 1), Rectangle("Rectangle", 2);

        final String name;
        final int id;

        ShapeMode(String name, int id) {
            this.name = name;
            this.id = id;
        }

        /**
         * Gets the ID of the shape mode.
         *
         * @return the ID of the shape mode
         */
        @Override
        public int getId() {
            return id;
        }

        /**
         * Gets the name of the shape mode.
         *
         * @return the name of the shape mode
         */
        @Override
        public String getName() {
            return name;
        }
    }
}