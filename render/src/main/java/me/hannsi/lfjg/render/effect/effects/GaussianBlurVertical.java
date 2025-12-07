package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.system.rendering.frameBuffer.FrameBuffer;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static me.hannsi.lfjg.render.LFJGRenderContext.SHADER_PROGRAM;

public class GaussianBlurVertical extends EffectBase {
    private float radiusX = 30f;

    GaussianBlurVertical(String name) {
        super(name, false);
    }

    public static GaussianBlurVertical createGaussianBlurVertical(String name) {
        return new GaussianBlurVertical(name);
    }

    public GaussianBlurVertical radiusX(float radiusX) {
        this.radiusX = radiusX;
        return this;
    }

    public GaussianBlurVertical radiusX(double radiusX) {
        this.radiusX = (float) radiusX;
        return this;
    }

    @Override
    public void drawFrameBuffer(FrameBuffer latestFrameBuffer) {
        SHADER_PROGRAM.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.GAUSSIAN_BLUR_VERTICAL.getId());
        SHADER_PROGRAM.setUniform("gaussianBlurDirection", UploadUniformType.ON_CHANGE, new Vector2f(0, 1));
        SHADER_PROGRAM.setUniform("gaussianBlurRadius", UploadUniformType.ON_CHANGE, radiusX);

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i < radiusX; i++) {
            weightBuffer.put(MathHelper.calculateGaussianValue(i, radiusX / 2));
        }
        weightBuffer.rewind();
        SHADER_PROGRAM.setUniform("gaussianBlurValues", UploadUniformType.PER_FRAME, weightBuffer);

        super.drawFrameBuffer(latestFrameBuffer);
    }

    public float getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(float radiusX) {
        this.radiusX = radiusX;
    }
}
