package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.math.MathHelper;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

/**
 * Class representing a Chromatic Aberration effect in OpenGL.
 */
@Getter
@Setter
public class ChromaticAberration extends EffectBase {
    /**
     * -- SETTER --
     * Sets the offset amount for the chromatic aberration.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the offset amount for the chromatic aberration.
     *
     * @param offsetAmount the offset amount
     * @return the offset amount
     */
    private float offsetAmount = 0.008f;
    /**
     * -- SETTER --
     * Sets the angle of the chromatic aberration.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the angle of the chromatic aberration.
     *
     * @param angle the angle
     * @return the angle
     */
    private float angle = MathHelper.toRadians(90);
    /**
     * -- SETTER --
     * Sets the strength of the chromatic aberration.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the strength of the chromatic aberration.
     *
     * @param strength the strength
     * @return the strength
     */
    private float strength = 0.7f;
    /**
     * -- SETTER --
     * Sets the type of chromatic aberration.
     * <p>
     * <p>
     * -- GETTER --
     * Gets the type of chromatic aberration.
     *
     * @param aberrationType the type of chromatic aberration
     * @return the type of chromatic aberration
     */
    private AberrationType aberrationType = AberrationType.RED_BLUE_A;

    ChromaticAberration() {
        super(Location.fromResource("shader/frameBuffer/filter/ChromaticAberration.fsh"), true, 22, "ChromaticAberration");
    }

    public static ChromaticAberration createChromaticAberration() {
        return new ChromaticAberration();
    }

    public ChromaticAberration offsetAmount(float offsetAmount) {
        this.offsetAmount = offsetAmount;
        return this;
    }

    public ChromaticAberration offsetAmount(double offsetAmount) {
        this.offsetAmount = (float) offsetAmount;
        return this;
    }

    public ChromaticAberration angleRadian(float angle) {
        this.angle = angle;
        return this;
    }

    public ChromaticAberration angleRadian(double angle) {
        this.angle = (float) angle;
        return this;
    }

    public ChromaticAberration angleDegree(float angle) {
        this.angle = MathHelper.toRadians(angle);
        return this;
    }

    public ChromaticAberration angleDegree(double angle) {
        this.angle = (float) MathHelper.toRadians(angle);
        return this;
    }

    public ChromaticAberration strength(float strength) {
        this.strength = strength;
        return this;
    }

    public ChromaticAberration strength(double strength) {
        this.strength = (float) strength;
        return this;
    }

    public ChromaticAberration aberrationType(AberrationType aberrationType) {
        this.aberrationType = aberrationType;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("offsetAmount", offsetAmount);
        getFrameBuffer().getShaderProgramFBO().setUniform("angle", angle);
        getFrameBuffer().getShaderProgramFBO().setUniform("strength", strength);
        getFrameBuffer().getShaderProgramFBO().setUniform("aberrationType", aberrationType.getId());
        super.setUniform(baseGLObject);
    }

    /**
     * Enum representing the types of chromatic aberration.
     */
    public enum AberrationType implements IEnumTypeBase {
        RED_GREEN_A("RedGreenA", 0),
        RED_BLUE_A("RedBlueA", 1),
        GREEN_BLUE_A("GreenBlueA", 2),
        RED_GREEN_B("RedGreenB", 3),
        RED_BLUE_B("RedBlueB", 4),
        GREEN_BLUE_B("GreenBlueB", 5);

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