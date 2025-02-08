package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Class representing a Flash effect in OpenGL.
 */
public class Flash extends EffectBase {
    private float intensity;
    private float x;
    private float y;
    private FlashBlendMode flashBlendMode;
    private Color lightColor;

    /**
     * Constructs a new Flash effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param intensity the intensity of the flash
     * @param x the x-coordinate of the flash position
     * @param y the y-coordinate of the flash position
     * @param flashBlendMode the blend mode of the flash
     * @param lightColor the color of the light
     */
    public Flash(Vector2f resolution, float intensity, float x, float y, FlashBlendMode flashBlendMode, Color lightColor) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/Flash.fsh"), true, 10, "Flash");

        this.intensity = intensity;
        this.x = x;
        this.y = y;
        this.flashBlendMode = flashBlendMode;
        this.lightColor = lightColor;
    }

    /**
     * Constructs a new Flash effect with the specified parameters.
     *
     * @param resolution the resolution of the effect
     * @param intensity the intensity of the flash
     * @param x the x-coordinate of the flash position
     * @param y the y-coordinate of the flash position
     * @param flashBlendMode the blend mode of the flash
     * @param lightColor the color of the light
     */
    public Flash(Vector2f resolution, double intensity, double x, double y, FlashBlendMode flashBlendMode, Color lightColor) {
        this(resolution, (float) intensity, (float) x, (float) y, flashBlendMode, lightColor);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("screenSize", getResolution());
        getFrameBuffer().getShaderProgramFBO().setUniform("intensity", intensity);
        getFrameBuffer().getShaderProgramFBO().setUniform("screenPosition", new Vector2f(x, y));
        getFrameBuffer().getShaderProgramFBO().setUniform1i("blendMode", flashBlendMode.getId());
        getFrameBuffer().getShaderProgramFBO().setUniform("lightColor", new Vector3f(lightColor.getRedF(), lightColor.getGreenF(), lightColor.getBlueF()));

        super.setUniform(baseGLObject);
    }

    /**
     * Gets the intensity of the flash.
     *
     * @return the intensity of the flash
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * Sets the intensity of the flash.
     *
     * @param intensity the intensity of the flash
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets the x-coordinate of the flash position.
     *
     * @return the x-coordinate of the flash position
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the flash position.
     *
     * @param x the x-coordinate of the flash position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the flash position.
     *
     * @return the y-coordinate of the flash position
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the flash position.
     *
     * @param y the y-coordinate of the flash position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets the blend mode of the flash.
     *
     * @return the blend mode of the flash
     */
    public FlashBlendMode getFlashBlendMode() {
        return flashBlendMode;
    }

    /**
     * Sets the blend mode of the flash.
     *
     * @param flashBlendMode the blend mode of the flash
     */
    public void setFlashBlendMode(FlashBlendMode flashBlendMode) {
        this.flashBlendMode = flashBlendMode;
    }

    /**
     * Gets the color of the light.
     *
     * @return the color of the light
     */
    public Color getLightColor() {
        return lightColor;
    }

    /**
     * Sets the color of the light.
     *
     * @param lightColor the color of the light
     */
    public void setLightColor(Color lightColor) {
        this.lightColor = lightColor;
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