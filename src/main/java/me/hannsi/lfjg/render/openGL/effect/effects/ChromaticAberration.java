package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;
import org.joml.Vector2f;

public class ChromaticAberration extends EffectBase {
    private float offsetAmount;
    private float angle;
    private float strength;
    private AberrationType aberrationType;

    public ChromaticAberration(Vector2f resolution, float offsetAmount, float angle, float strength, AberrationType aberrationType) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/ChromaticAberration.fsh"), true, 22, "ChromaticAberration");

        this.offsetAmount = offsetAmount;
        this.angle = angle;
        this.strength = strength;
        this.aberrationType = aberrationType;
    }

    public ChromaticAberration(Vector2f resolution, double offsetAmount, double angle, double strength, AberrationType aberrationType) {
        this(resolution, (float) offsetAmount, (float) angle, (float) strength, aberrationType);
    }

    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        getFrameBuffer().bindFrameBuffer();
        super.frameBufferPush(baseGLObject);
    }

    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        getFrameBuffer().unbindFrameBuffer();
        super.frameBufferPop(baseGLObject);
    }

    @Override
    public void frameBuffer(GLObject baseGLObject) {
        getFrameBuffer().drawFrameBuffer();
        super.frameBuffer(baseGLObject);
    }

    @Override
    public void setUniform(GLObject baseGLObject) {
        getFrameBuffer().getShaderProgramFBO().setUniform("offsetAmount", offsetAmount);
        getFrameBuffer().getShaderProgramFBO().setUniform("angle", angle);
        getFrameBuffer().getShaderProgramFBO().setUniform("strength", strength);
        getFrameBuffer().getShaderProgramFBO().setUniform1i("aberrationType", aberrationType.getId());
        super.setUniform(baseGLObject);
    }

    public float getOffsetAmount() {
        return offsetAmount;
    }

    public void setOffsetAmount(float offsetAmount) {
        this.offsetAmount = offsetAmount;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public AberrationType getAberrationType() {
        return aberrationType;
    }

    public void setAberrationType(AberrationType aberrationType) {
        this.aberrationType = aberrationType;
    }

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
