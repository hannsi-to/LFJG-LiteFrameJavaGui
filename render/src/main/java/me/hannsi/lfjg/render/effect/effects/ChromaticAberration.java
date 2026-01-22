package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.effect.system.EffectBase;

public class ChromaticAberration extends EffectBase {
    private float offsetAmount = 0.008f;
    private float angle = MathHelper.toRadians(90);
    private float strength = 0.7f;
    private AberrationType aberrationType = AberrationType.RED_BLUE_A;

    ChromaticAberration(String name) {
        super(name);
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
