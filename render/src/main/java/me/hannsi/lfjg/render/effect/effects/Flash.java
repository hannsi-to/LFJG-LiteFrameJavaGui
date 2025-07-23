package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

public class Flash extends EffectBase {
    private Vector2i resolution = new Vector2i();
    private float intensity = 0.08f;
    private float x = 1920 / 2f;
    private float y = 1080 / 2f;
    private FlashBlendMode flashBlendMode = FlashBlendMode.BACKWARD_SYNTHESIS;
    private Color lightColor = Color.FOREST_GREEN;

    Flash() {
        super(Location.fromResource("shader/frameBuffer/filter/Flash.fsh"), true, 10, "Flash");
    }

    public static Flash createFlash() {
        return new Flash();
    }

    public Flash resolution(Vector2i resolution) {
        this.resolution = resolution;
        return this;
    }

    public Flash intensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public Flash intensity(double intensity) {
        this.intensity = (float) intensity;
        return this;
    }

    public Flash x(float x) {
        this.x = x;
        return this;
    }

    public Flash x(double x) {
        this.x = (float) x;
        return this;
    }

    public Flash y(float y) {
        this.y = y;
        return this;
    }

    public Flash y(double y) {
        this.y = (float) y;
        return this;
    }

    public Flash flashBlendMode(FlashBlendMode flashBlendMode) {
        this.flashBlendMode = flashBlendMode;
        return this;
    }

    public Flash lightColor(Color lightColor) {
        this.lightColor = lightColor;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("screenSize", resolution);
        getFrameBuffer().getShaderProgramFBO().setUniform("intensity", intensity);
        getFrameBuffer().getShaderProgramFBO().setUniform("screenPosition", new Vector2f(x, y));
        getFrameBuffer().getShaderProgramFBO().setUniform("blendMode", flashBlendMode.getId());
        getFrameBuffer().getShaderProgramFBO().setUniform("lightColor", new Vector3f(lightColor.getRedF(), lightColor.getGreenF(), lightColor.getBlueF()));

        super.setUniform(baseGLObject);
    }

    public Vector2i getResolution() {
        return resolution;
    }

    public void setResolution(Vector2i resolution) {
        this.resolution = resolution;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public FlashBlendMode getFlashBlendMode() {
        return flashBlendMode;
    }

    public void setFlashBlendMode(FlashBlendMode flashBlendMode) {
        this.flashBlendMode = flashBlendMode;
    }

    public Color getLightColor() {
        return lightColor;
    }

    public void setLightColor(Color lightColor) {
        this.lightColor = lightColor;
    }

    public enum FlashBlendMode implements IEnumTypeBase {
        FORWARD_SYNTHESIS("ForwardSynthesis", 0),
        BACKWARD_SYNTHESIS("BackwardSynthesis", 1),
        LIGHT_COMPONENT_ONLY("LightComponentOnly", 2),
        ORIGINAL_COLOR("OriginalColor", 3);

        final String name;
        final int id;

        FlashBlendMode(String name, int id) {
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