package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static me.hannsi.lfjg.render.LFJGRenderContext.shaderProgram;

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
        super(name, false);
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

    @Override
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.PER_FRAME, FragmentShaderType.GRADATION.getId());
        shaderProgram.setUniform("gradationCenter", UploadUniformType.ON_CHANGE, new Vector2f(centerX, centerY));
        shaderProgram.setUniform("gradationAngle", UploadUniformType.ON_CHANGE, angle);
        shaderProgram.setUniform("gradationWidth", UploadUniformType.ON_CHANGE, width);
        shaderProgram.setUniform("gradientShape", UploadUniformType.ON_CHANGE, shapeMode.getId());
        shaderProgram.setUniform("gradationBlendMode", UploadUniformType.ON_CHANGE, blendType.getId());
        shaderProgram.setUniform("gradationStartColor", UploadUniformType.ON_CHANGE, new Vector4f(startColor.getRedF(), startColor.getGreenF(), startColor.getBlueF(), startColor.getAlphaF()));
        shaderProgram.setUniform("gradationEndColor", UploadUniformType.ON_CHANGE, new Vector4f(endColor.getRedF(), endColor.getGreenF(), endColor.getBlueF(), endColor.getAlphaF()));
        shaderProgram.setUniform("gradationIntensity", UploadUniformType.ON_CHANGE, intensity);

        super.drawFrameBuffer(latestFrameBuffer);
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public ShapeMode getShapeMode() {
        return shapeMode;
    }

    public void setShapeMode(ShapeMode shapeMode) {
        this.shapeMode = shapeMode;
    }

    public BlendType getBlendType() {
        return blendType;
    }

    public void setBlendType(BlendType blendType) {
        this.blendType = blendType;
    }

    public Color getStartColor() {
        return startColor;
    }

    public void setStartColor(Color startColor) {
        this.startColor = startColor;
    }

    public Color getEndColor() {
        return endColor;
    }

    public void setEndColor(Color endColor) {
        this.endColor = endColor;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
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
