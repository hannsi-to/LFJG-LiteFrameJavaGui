package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import static me.hannsi.lfjg.render.LFJGRenderContext.SHADER_PROGRAM;

public class ChromaticAberration extends EffectBase {
    private float offsetAmount = 0.008f;
    private float angle = MathHelper.toRadians(90);
    private float strength = 0.7f;
    private AberrationType aberrationType = AberrationType.RED_BLUE_A;

    ChromaticAberration(String name) {
        super(name, false);
    }

    public static ChromaticAberration createChromaticAberration(String name) {
        return new ChromaticAberration(name);
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
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        SHADER_PROGRAM.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.CHROMATIC_ABERRATION.getId());
        SHADER_PROGRAM.setUniform("chromaticAberrationOffsetAmount", UploadUniformType.ON_CHANGE, offsetAmount);
        SHADER_PROGRAM.setUniform("chromaticAberrationAngle", UploadUniformType.ON_CHANGE, angle);
        SHADER_PROGRAM.setUniform("chromaticAberrationStrength", UploadUniformType.ON_CHANGE, strength);
        SHADER_PROGRAM.setUniform("aberrationType", UploadUniformType.ON_CHANGE, aberrationType.getId());

        super.drawFrameBuffer(latestFrameBuffer);
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
