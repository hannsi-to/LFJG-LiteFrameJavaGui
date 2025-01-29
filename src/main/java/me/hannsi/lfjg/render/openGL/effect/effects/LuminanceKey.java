package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;
import org.joml.Vector2f;

public class LuminanceKey extends EffectBase {
    private Vector2f resolution;
    private float threshold;
    private float blurAmount;
    private LuminanceMode luminanceMode;

    public LuminanceKey(Vector2f resolution, float threshold, float blurAmount, LuminanceMode luminanceMode) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/LuminanceKey.fsh"), true, 13, "LuminanceKey");

        this.resolution = resolution;
        this.threshold = threshold;
        this.blurAmount = blurAmount;
        this.luminanceMode = luminanceMode;
    }

    public LuminanceKey(Vector2f resolution, double threshold, double blurAmount, LuminanceMode luminanceMode) {
        this(resolution, (float) threshold, (float) blurAmount, luminanceMode);
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
        getFrameBuffer().getShaderProgramFBO().setUniform("threshold", threshold);
        getFrameBuffer().getShaderProgramFBO().setUniform("blurAmount", blurAmount);
        getFrameBuffer().getShaderProgramFBO().setUniform1i("mode", luminanceMode.getId());

        super.setUniform(baseGLObject);
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public float getBlurAmount() {
        return blurAmount;
    }

    public void setBlurAmount(float blurAmount) {
        this.blurAmount = blurAmount;
    }

    public LuminanceMode getLuminanceMode() {
        return luminanceMode;
    }

    public void setLuminanceMode(LuminanceMode luminanceMode) {
        this.luminanceMode = luminanceMode;
    }

    public enum LuminanceMode implements IEnumTypeBase {
        OnlyDark("OnlyDark", 0), OnlyLight("OnlyLight", 1), Both("Both", 2);

        final String name;
        final int id;

        LuminanceMode(String name, int id) {
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
