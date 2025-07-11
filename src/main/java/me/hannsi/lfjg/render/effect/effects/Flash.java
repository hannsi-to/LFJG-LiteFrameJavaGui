package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Class representing a Flash effect in OpenGL.
 */
@Getter
@Setter
public class Flash extends EffectBase {
    /**
     * -- SETTER --
     * Sets the intensity of the flash.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the intensity of the flash.
     *
     * @param intensity the intensity of the flash
     * @return the intensity of the flash
     */
    private float intensity = 0.08f;
    /**
     * -- SETTER --
     * Sets the x-coordinate of the flash position.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the x-coordinate of the flash position.
     *
     * @param x the x-coordinate of the flash position
     * @return the x-coordinate of the flash position
     */
    private float x = 1920 / 2f;
    /**
     * -- SETTER --
     * Sets the y-coordinate of the flash position.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the y-coordinate of the flash position.
     *
     * @param y the y-coordinate of the flash position
     * @return the y-coordinate of the flash position
     */
    private float y = 1080 / 2f;
    /**
     * -- SETTER --
     * Sets the blend mode of the flash.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the blend mode of the flash.
     *
     * @param flashBlendMode the blend mode of the flash
     * @return the blend mode of the flash
     */
    private FlashBlendMode flashBlendMode = FlashBlendMode.BackwardSynthesis;
    /**
     * -- SETTER --
     * Sets the color of the light.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the color of the light.
     *
     * @param lightColor the color of the light
     * @return the color of the light
     */
    private Color lightColor = Color.FOREST_GREEN;

    Flash() {
        super(Location.fromResource("shader/frameBuffer/filter/Flash.fsh"), true, 10, "Flash");
    }

    public static Flash createFlash() {
        return new Flash();
    }

    public Flash intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public Flash intensity(double intensity) {
        this.intensity = (float) intensity;
        return this;
    }

    public Flash x(float x) {
        this.x = x;
        return this;
    }

    public Flash x(double x) {
        this.x = (float) x;
        return this;
    }

    public Flash y(float y) {
        this.y = y;
        return this;
    }

    public Flash y(double y) {
        this.y = (float) y;
        return this;
    }

    public Flash flashBlendMode(FlashBlendMode flashBlendMode) {
        this.flashBlendMode = flashBlendMode;
        return this;
    }

    public Flash lightColor(Color lightColor) {
        this.lightColor = lightColor;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("screenSize", LFJGContext.frameBufferSize);
        getFrameBuffer().getShaderProgramFBO().setUniform("intensity", intensity);
        getFrameBuffer().getShaderProgramFBO().setUniform("screenPosition", new Vector2f(x, y));
        getFrameBuffer().getShaderProgramFBO().setUniform("blendMode", flashBlendMode.getId());
        getFrameBuffer().getShaderProgramFBO().setUniform("lightColor", new Vector3f(lightColor.getRedF(), lightColor.getGreenF(), lightColor.getBlueF()));

        super.setUniform(baseGLObject);
    }

    /**
     * Enum representing the blend modes for the Flash effect.
     */
    public enum FlashBlendMode implements IEnumTypeBase {
        ForwardSynthesis("ForwardSynthesis", 0),
        BackwardSynthesis("BackwardSynthesis", 1),
        LightComponentOnly("LightComponentOnly", 2),
        OriginalColor("OriginalColor", 3);

        final String name;
        final int id;

        FlashBlendMode(String name, int id) {
            this.name = name;
            this.id = id;
        }

        /**
         * Gets the ID of the blend mode.
         *
         * @return the ID of the blend mode
         */
        @Override
        public int getId() {
            return id;
        }

        /**
         * Gets the name of the blend mode.
         *
         * @return the name of the blend mode
         */
        @Override
        public String getName() {
            return name;
        }
    }
}