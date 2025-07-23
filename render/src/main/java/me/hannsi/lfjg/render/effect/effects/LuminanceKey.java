package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

public class LuminanceKey extends EffectBase {
    private float threshold = 0.6f;
    private float blurAmount = 0.1f;
    private LuminanceMode luminanceMode = LuminanceMode.BOTH;

    LuminanceKey() {
        super(Location.fromResource("shader/frameBuffer/filter/LuminanceKey.fsh"), true, 13, "LuminanceKey");
    }

    public static LuminanceKey createLuminanceKey() {
        return new LuminanceKey();
    }

    public LuminanceKey threshold(float threshold) {
        this.threshold = threshold;
        return this;
    }

    public LuminanceKey threshold(double threshold) {
        this.threshold = (float) threshold;
        return this;
    }

    public LuminanceKey blurAmount(float blurAmount) {
        this.blurAmount = blurAmount;
        return this;
    }

    public LuminanceKey blurAmount(double blurAmount) {
        this.blurAmount = (float) blurAmount;
        return this;
    }

    public LuminanceKey luminanceMode(LuminanceMode luminanceMode) {
        this.luminanceMode = luminanceMode;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("threshold", threshold);
        getFrameBuffer().getShaderProgramFBO().setUniform("blurAmount", blurAmount);
        getFrameBuffer().getShaderProgramFBO().setUniform("mode", luminanceMode.getId());

        super.setUniform(baseGLObject);
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
        ONLY_DARK("OnlyDark", 0),
        ONLY_Light("OnlyLight", 1),
        BOTH("Both", 2);

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