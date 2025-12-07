package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.frameBuffer.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static me.hannsi.lfjg.render.LFJGRenderContext.SHADER_PROGRAM;

public class Flash extends EffectBase {
    private float intensity = 0.08f;
    private float x = 1920 / 2f;
    private float y = 1080 / 2f;
    private FlashBlendMode flashBlendMode = FlashBlendMode.BACKWARD_SYNTHESIS;
    private Color lightColor = Color.FOREST_GREEN;

    Flash(String name) {
        super(name, false);
    }

    public static Flash createFlash(String name) {
        return new Flash(name);
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
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        SHADER_PROGRAM.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.FLASH.getId());
        SHADER_PROGRAM.setUniform("flashIntensity", UploadUniformType.ON_CHANGE, intensity);
        SHADER_PROGRAM.setUniform("flashScreenPosition", UploadUniformType.ON_CHANGE, new Vector2f(x, y));
        SHADER_PROGRAM.setUniform("flashBlendMode", UploadUniformType.ON_CHANGE, flashBlendMode.getId());
        SHADER_PROGRAM.setUniform("flashLightColor", UploadUniformType.ON_CHANGE, new Vector3f(lightColor.getRedF(), lightColor.getGreenF(), lightColor.getBlueF()));

        super.drawFrameBuffer(latestFrameBuffer);
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
