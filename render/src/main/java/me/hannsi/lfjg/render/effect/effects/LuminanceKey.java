package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.frameBuffer.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import static me.hannsi.lfjg.render.LFJGRenderContext.shaderProgram;

public class LuminanceKey extends EffectBase {
    private float threshold = 0.6f;
    private float blurAmount = 0.1f;
    private LuminanceMode luminanceMode = LuminanceMode.BOTH;

    LuminanceKey(String name) {
        super(name, false);
    }

    public static LuminanceKey createLuminanceKey(String name) {
        return new LuminanceKey(name);
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
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.LUMINANCE_KEY.getId());
        shaderProgram.setUniform("luminanceKeyThreshold", UploadUniformType.ON_CHANGE, threshold);
        shaderProgram.setUniform("luminanceKeyBlurAmount", UploadUniformType.ON_CHANGE, blurAmount);
        shaderProgram.setUniform("luminanceKeyMode", UploadUniformType.ON_CHANGE, luminanceMode.getId());

        super.drawFrameBuffer(latestFrameBuffer);
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
