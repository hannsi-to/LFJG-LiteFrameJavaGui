package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.utils.type.types.BlendType;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Gradation extends EffectBase {
    private Vector2f resolution;
    private float centerX;
    private float centerY;
    private float angle;
    private float width;
    private ShapeMode shapeMode;
    private BlendType blendType;
    private Color startColor;
    private Color endColor;
    private float intensity;

    public Gradation(Vector2f resolution, float centerX, float centerY, float angle, float width, ShapeMode shapeMode, BlendType blendType, Color startColor, Color endColor, float intensity) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/Gradation.fsh"), true, 24, "Gradation");

        this.resolution = resolution;
        this.centerX = centerX;
        this.centerY = centerY;
        this.angle = angle;
        this.width = width;
        this.shapeMode = shapeMode;
        this.blendType = blendType;
        this.startColor = startColor;
        this.endColor = endColor;
        this.intensity = intensity;
    }

    public Gradation(Vector2f resolution, double centerX, double centerY, double angle, double width, ShapeMode shapeMode, BlendType blendType, Color startColor, Color endColor, double intensity) {
        this(resolution, (float) centerX, (float) centerY, (float) angle, (float) width, shapeMode, blendType, startColor, endColor, (float) intensity);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("center", new Vector2f(centerX / resolution.x, centerY / resolution.y));
        getFrameBuffer().getShaderProgramFBO().setUniform("angle", angle);
        getFrameBuffer().getShaderProgramFBO().setUniform("width", width);
        getFrameBuffer().getShaderProgramFBO().setUniform1i("gradientShape", shapeMode.getId());
        getFrameBuffer().getShaderProgramFBO().setUniform1i("blendMode", blendType.getId());
        getFrameBuffer().getShaderProgramFBO().setUniform("startColor", new Vector4f(startColor.getRedF(), startColor.getGreenF(), startColor.getBlueF(), startColor.getAlphaF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("endColor", new Vector4f(endColor.getRedF(), endColor.getGreenF(), endColor.getBlueF(), endColor.getAlphaF()));
        getFrameBuffer().getShaderProgramFBO().setUniform("intensity", intensity);

        super.setUniform(baseGLObject);
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
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
        Line("Line", 0), Circle("Circle", 1), Rectangle("Rectangle", 2);

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
