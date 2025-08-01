package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

public class ChromaticAberration extends EffectBase {
    private float offsetAmount = 0.008f;
    private float angle = MathHelper.toRadians(90);
    private float strength = 0.7f;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("offsetAmount", UploadUniformType.ON_CHANGE, offsetAmount);
        getFrameBuffer().getShaderProgramFBO().setUniform("angle", UploadUniformType.ON_CHANGE, angle);
        getFrameBuffer().getShaderProgramFBO().setUniform("strength", UploadUniformType.ON_CHANGE, strength);
        getFrameBuffer().getShaderProgramFBO().setUniform("aberrationType", UploadUniformType.ON_CHANGE, aberrationType.getId());
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