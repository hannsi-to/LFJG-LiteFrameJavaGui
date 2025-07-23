package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;

/**
 * Class representing a Gradation effect in OpenGL.
 */
@Getter
@Setter
public class Gradation extends EffectBase {
    private Vector2i resolution = new Vector2i();
    /**
     * -- SETTER --
     * Sets the x-coordinate of the center.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the x-coordinate of the center.
     *
     * @param centerX the x-coordinate of the center
     * @return the x-coordinate of the center
     */
    private float centerX = 500f;
    /**
     * -- SETTER --
     * Sets the y-coordinate of the center.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the y-coordinate of the center.
     *
     * @param centerY the y-coordinate of the center
     * @return the y-coordinate of the center
     */
    private float centerY = 500f;
    /**
     * -- SETTER --
     * Sets the angle of the gradation.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the angle of the gradation.
     *
     * @param angle the angle of the gradation
     * @return the angle of the gradation
     */
    private float angle = MathHelper.toRadians(45);
    /**
     * -- SETTER --
     * Sets the width of the gradation.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the width of the gradation.
     *
     * @param width the width of the gradation
     * @return the width of the gradation
     */
    private float width = 0.1f;
    /**
     * -- SETTER --
     * Sets the shape mode of the gradation.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the shape mode of the gradation.
     *
     * @param shapeMode the shape mode of the gradation
     * @return the shape mode of the gradation
     */
    private ShapeMode shapeMode = ShapeMode.RECTANGLE;
    /**
     * -- SETTER --
     * Sets the blend type of the gradation.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the blend type of the gradation.
     *
     * @param blendType the blend type of the gradation
     * @return the blend type of the gradation
     */
    private BlendType blendType = BlendType.ADD;
    /**
     * -- SETTER --
     * Sets the start color of the gradation.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the start color of the gradation.
     *
     * @param startColor the start color of the gradation
     * @return the start color of the gradation
     */
    private Color startColor = Color.RED;
    /**
     * -- SETTER --
     * Sets the end color of the gradation.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the end color of the gradation.
     *
     * @param endColor the end color of the gradation
     * @return the end color of the gradation
     */
    private Color endColor = Color.BLUE;
    /**
     * -- SETTER --
     * Sets the intensity of the gradation.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the intensity of the gradation.
     *
     * @param intensity the intensity of the gradation
     * @return the intensity of the gradation
     */
    private float intensity = 1f;

    Gradation() {
        super(Location.fromResource("shader/frameBuffer/filter/Gradation.fsh"), true, 24, "Gradation");
    }

    public static Gradation createGradation() {
        return new Gradation();
    }

    public Gradation resolution(Vector2i resolution) {
        this.resolution = resolution;
        return this;
    }

    public Gradation centerX(float centerX) {
        this.centerX = centerX;
        return this;
    }

    public Gradation centerX(double centerX) {
        this.centerX = (float) centerX;
        return this;
    }

    public Gradation centerY(float centerY) {
        this.centerY = centerY;
        return this;
    }

    public Gradation centerY(double centerY) {
        this.centerY = (float) centerY;
        return this;
    }

    public Gradation angleRadian(float angleRadian) {
        this.angle = angleRadian;
        return this;
    }

    public Gradation angleRadian(double angleRadian) {
        this.angle = (float) angleRadian;
        return this;
    }

    public Gradation angleDegree(int angleDegree) {
        this.angle = MathHelper.toRadians(angleDegree);
        return this;
    }

    public Gradation width(float width) {
        this.width = width;
        return this;
    }

    public Gradation width(double width) {
        this.width = (float) width;
        return this;
    }

    public Gradation shapeMode(ShapeMode shapeMode) {
        this.shapeMode = shapeMode;
        return this;
    }

    public Gradation blendType(BlendType blendType) {
        this.blendType = blendType;
        return this;
    }

    public Gradation startColor(Color startColor) {
        this.startColor = startColor;
        return this;
    }

    public Gradation endColor(Color endColor) {
        this.endColor = endColor;
        return this;
    }

    public Gradation intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public Gradation intensity(double intensity) {
        this.intensity = (float) intensity;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("center", new Vector2f(centerX / resolution.x, centerY / resolution.y));
        getFrameBuffer().getShaderProgramFBO().setUniform("angle", angle);
        getFrameBuffer().getShaderProgramFBO().setUniform("width", width);
        getFrameBuffer().getShaderProgramFBO().setUniform("gradientShape", shapeMode.getId());
        getFrameBuffer().getShaderProgramFBO().setUniform("blendMode", blendType.getId());
        getFrameBuffer().getShaderProgramFBO().setUniform("startColor", new Vector4f(startColor.getRedF(), startColor.getGreenF(), startColor.getBlueF(), startColor.getAlphaF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("endColor", new Vector4f(endColor.getRedF(), endColor.getGreenF(), endColor.getBlueF(), endColor.getAlphaF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("intensity", intensity);
        float aspectRatio = (float) resolution.x() / (float) resolution.y();
        getFrameBuffer().getShaderProgramFBO().setUniform("aspectRatio", aspectRatio);

        super.setUniform(baseGLObject);
    }

    /**
     * Enum representing the shape mode of the gradation.
     */
    public enum ShapeMode implements IEnumTypeBase {
        LINE("Line", 0),
        CIRCLE("Circle", 1),
        RECTANGLE("Rectangle", 2);

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