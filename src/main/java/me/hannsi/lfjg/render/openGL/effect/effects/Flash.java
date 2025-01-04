package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Flash extends EffectBase {
    private Vector2f resolution;
    private float intensity;
    private float x;
    private float y;
    private FlashBlendMode flashBlendMode;
    private Color lightColor;

    public Flash(Vector2f resolution, float intensity, float x, float y, FlashBlendMode flashBlendMode, Color lightColor) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/Flash.fsh"), true, 10, "Flash");

        this.resolution = resolution;
        this.intensity = intensity;
        this.x = x;
        this.y = y;
        this.flashBlendMode = flashBlendMode;
        this.lightColor = lightColor;
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
        getFrameBuffer().getShaderProgramFBO().bind();

        getFrameBuffer().getShaderProgramFBO().setUniform2f("screenSize", resolution);
        getFrameBuffer().getShaderProgramFBO().setUniform1f("intensity", intensity);
        getFrameBuffer().getShaderProgramFBO().setUniform2f("screenPosition", new Vector2f(x, y));
        getFrameBuffer().getShaderProgramFBO().setUniform1i("blendMode", flashBlendMode.getId());
        getFrameBuffer().getShaderProgramFBO().setUniform3f("lightColor", new Vector3f(lightColor.getRedF(), lightColor.getGreenF(), lightColor.getBlueF()));

        getFrameBuffer().getShaderProgramFBO().unbind();

        super.setUniform(baseGLObject);
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
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
        ForwardSynthesis("ForwardSynthesis", 0), BackwardSynthesis("BackwardSynthesis", 1), LightComponentOnly("LightComponentOnly", 2), OriginalColor("OriginalColor", 3);

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
