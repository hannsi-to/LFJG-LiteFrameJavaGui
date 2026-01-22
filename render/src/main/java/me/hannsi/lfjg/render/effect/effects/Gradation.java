package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.BlendType;

public class Gradation extends EffectBase {
    private float centerX = 500f;
    private float centerY = 500f;
    private float angle = MathHelper.toRadians(45);
    private float width = 0.1f;
    private ShapeMode shapeMode = ShapeMode.RECTANGLE;
    private BlendType blendType = BlendType.ADD;
    private Color startColor = Color.RED.setAlpha(50);
    private Color endColor = Color.BLUE.setAlpha(50);
    private float intensity = 1f;

    Gradation(String name) {
        super(name);
    }

    public static Gradation createGradation(String name) {
        return new Gradation(name);
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

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
