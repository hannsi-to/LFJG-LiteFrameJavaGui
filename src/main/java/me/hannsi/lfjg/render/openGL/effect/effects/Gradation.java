package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.utils.type.types.BlendType;
import org.joml.Vector2f;
import org.joml.Vector4f;

/**
 * Class representing a Gradation effect in OpenGL.
 */
public class Gradation extends EffectBase {
    private float centerX;
    private float centerY;
    private float angle;
    private float width;
    private ShapeMode shapeMode;
    private BlendType blendType;
    private Color startColor;
    private Color endColor;
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
     * Gets the x-coordinate of the center.
     *
     * @return the x-coordinate of the center
     */
    public float getCenterX() {
        return centerX;
    }

    /**
     * Sets the x-coordinate of the center.
     *
     * @param centerX the x-coordinate of the center
     */
    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    /**
     * Gets the y-coordinate of the center.
     *
     * @return the y-coordinate of the center
     */
    public float getCenterY() {
        return centerY;
    }

    /**
     * Sets the y-coordinate of the center.
     *
     * @param centerY the y-coordinate of the center
     */
    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    /**
     * Gets the angle of the gradation.
     *
     * @return the angle of the gradation
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Sets the angle of the gradation.
     *
     * @param angle the angle of the gradation
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

    /**
     * Gets the width of the gradation.
     *
     * @return the width of the gradation
     */
    public float getWidth() {
        return width;
    }

    /**
     * Sets the width of the gradation.
     *
     * @param width the width of the gradation
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Gets the shape mode of the gradation.
     *
     * @return the shape mode of the gradation
     */
    public ShapeMode getShapeMode() {
        return shapeMode;
    }

    /**
     * Sets the shape mode of the gradation.
     *
     * @param shapeMode the shape mode of the gradation
     */
    public void setShapeMode(ShapeMode shapeMode) {
        this.shapeMode = shapeMode;
    }

    /**
     * Gets the blend type of the gradation.
     *
     * @return the blend type of the gradation
     */
    public BlendType getBlendType() {
        return blendType;
    }

    /**
     * Sets the blend type of the gradation.
     *
     * @param blendType the blend type of the gradation
     */
    public void setBlendType(BlendType blendType) {
        this.blendType = blendType;
    }

    /**
     * Gets the start color of the gradation.
     *
     * @return the start color of the gradation
     */
    public Color getStartColor() {
        return startColor;
    }

    /**
     * Sets the start color of the gradation.
     *
     * @param startColor the start color of the gradation
     */
    public void setStartColor(Color startColor) {
        this.startColor = startColor;
    }

    /**
     * Gets the end color of the gradation.
     *
     * @return the end color of the gradation
     */
    public Color getEndColor() {
        return endColor;
    }

    /**
     * Sets the end color of the gradation.
     *
     * @param endColor the end color of the gradation
     */
    public void setEndColor(Color endColor) {
        this.endColor = endColor;
    }

    /**
     * Gets the intensity of the gradation.
     *
     * @return the intensity of the gradation
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * Sets the intensity of the gradation.
     *
     * @param intensity the intensity of the gradation
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
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