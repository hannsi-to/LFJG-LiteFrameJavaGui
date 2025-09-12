package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class GaussianBlurHorizontal extends EffectBase {
    private float radiusX = 30f;

    GaussianBlurHorizontal(String name) {
        super(name, false);
    }

    public static GaussianBlurHorizontal createGaussianBlurHorizontal(String name) {
        return new GaussianBlurHorizontal(name);
    }

    public GaussianBlurHorizontal radiusX(float radiusX) {
        this.radiusX = radiusX;
        return this;
    }

    public GaussianBlurHorizontal radiusX(double radiusX) {
        this.radiusX = (float) radiusX;
        return this;
    }

    @Override
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        LFJGRenderContext.shaderProgram.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.GAUSSIAN_BLUR_HORIZONTAL.getId());
        LFJGRenderContext.shaderProgram.setUniform("gaussianBlurDirection", UploadUniformType.ON_CHANGE, new Vector2f(1, 0));
        LFJGRenderContext.shaderProgram.setUniform("gaussianBlurRadius", UploadUniformType.ON_CHANGE, radiusX);

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i < radiusX; i++) {
            weightBuffer.put(MathHelper.calculateGaussianValue(i, radiusX / 2));
        }
        weightBuffer.rewind();
        LFJGRenderContext.shaderProgram.setUniform("gaussianBlurValues", UploadUniformType.PER_FRAME, weightBuffer);

        super.drawFrameBuffer(latestFrameBuffer);
    }

    public float getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(float radiusX) {
        this.radiusX = radiusX;
    }
}
