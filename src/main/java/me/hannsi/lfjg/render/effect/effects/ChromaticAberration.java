package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

/**
 * Class representing a Chromatic Aberration effect in OpenGL.
 */
@Getter
@Setter
public class ChromaticAberration extends EffectBase {
    /**
     * -- SETTER --
     *  Sets the offset amount for the chromatic aberration.
     *
     *
     * -- GETTER --
     *  Gets the offset amount for the chromatic aberration.
     *
     @param offsetAmount the offset amount
      * @return the offset amount
     */
    private float offsetAmount;
    /**
     * -- SETTER --
     *  Sets the angle of the chromatic aberration.
     *
     *
     * -- GETTER --
     *  Gets the angle of the chromatic aberration.
     *
     @param angle the angle
      * @return the angle
     */
    private float angle;
    /**
     * -- SETTER --
     *  Sets the strength of the chromatic aberration.
     *
     *
     * -- GETTER --
     *  Gets the strength of the chromatic aberration.
     *
     @param strength the strength
      * @return the strength
     */
    private float strength;
    /**
     * -- SETTER --
     *  Sets the type of chromatic aberration.
     *
     *
     * -- GETTER --
     *  Gets the type of chromatic aberration.
     *
     @param aberrationType the type of chromatic aberration
      * @return the type of chromatic aberration
     */
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