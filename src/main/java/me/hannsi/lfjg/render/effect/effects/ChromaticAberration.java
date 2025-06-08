package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

/**
 * Class representing a Chromatic Aberration effect in OpenGL.
 */
public class ChromaticAberration extends EffectBase {
    private float offsetAmount;
    private float angle;
    private float strength;
    private AberrationType aberrationType;

    /**
     * Constructs a new ChromaticAberration effect with the specified parameters.
     *
     * @param offsetAmount   the offset amount for the chromatic aberration
     * @param angle          the angle of the chromatic aberration
     * @param strength       the strength of the chromatic aberration
     * @param aberrationType the type of chromatic aberration
     */
    public ChromaticAberration(float offsetAmount, float angle, float strength, AberrationType aberrationType) {
        super(new ResourcesLocation("shader/frameBuffer/filter/ChromaticAberration.fsh"), true, 22, "ChromaticAberration");

        this.offsetAmount = offsetAmount;
        this.angle = angle;
        this.strength = strength;
        this.aberrationType = aberrationType;
    }

    /**
     * Constructs a new ChromaticAberration effect with the specified parameters.
     *
     * @param offsetAmount   the offset amount for the chromatic aberration
     * @param angle          the angle of the chromatic aberration
     * @param strength       the strength of the chromatic aberration
     * @param aberrationType the type of chromatic aberration
     */
    public ChromaticAberration(double offsetAmount, double angle, double strength, AberrationType aberrationType) {
        this((float) offsetAmount, (float) angle, (float) strength, aberrationType);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("offsetAmount", offsetAmount);
        getFrameBuffer().getShaderProgramFBO().setUniform("angle", angle);
        getFrameBuffer().getShaderProgramFBO().setUniform("strength", strength);
        getFrameBuffer().getShaderProgramFBO().setUniform1i("aberrationType", aberrationType.getId());
        super.setUniform(baseGLObject);
    }

    /**
     * Gets the offset amount for the chromatic aberration.
     *
     * @return the offset amount
     */
    public float getOffsetAmount() {
        return offsetAmount;
    }

    /**
     * Sets the offset amount for the chromatic aberration.
     *
     * @param offsetAmount the offset amount
     */
    public void setOffsetAmount(float offsetAmount) {
        this.offsetAmount = offsetAmount;
    }

    /**
     * Gets the angle of the chromatic aberration.
     *
     * @return the angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Sets the angle of the chromatic aberration.
     *
     * @param angle the angle
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

    /**
     * Gets the strength of the chromatic aberration.
     *
     * @return the strength
     */
    public float getStrength() {
        return strength;
    }

    /**
     * Sets the strength of the chromatic aberration.
     *
     * @param strength the strength
     */
    public void setStrength(float strength) {
        this.strength = strength;
    }

    /**
     * Gets the type of chromatic aberration.
     *
     * @return the type of chromatic aberration
     */
    public AberrationType getAberrationType() {
        return aberrationType;
    }

    /**
     * Sets the type of chromatic aberration.
     *
     * @param aberrationType the type of chromatic aberration
     */
    public void setAberrationType(AberrationType aberrationType) {
        this.aberrationType = aberrationType;
    }

    /**
     * Enum representing the types of chromatic aberration.
     */
    public enum AberrationType implements IEnumTypeBase {
        RedGreenA("RedGreenA", 0), RedBlueA("RedBlueA", 1), GreenBlueA("GreenBlueA", 2), RedGreenB("RedGreenB", 3), RedBlueB("RedBlueB", 4), GreenBlueB("GreenBlueB", 5);

        final String name;
        final int id;

        AberrationType(String name, int id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getId() {
            return id;
        }
    }
}