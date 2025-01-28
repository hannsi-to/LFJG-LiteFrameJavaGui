package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.math.MathUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class GaussianBlurVertical extends EffectBase {
    private Vector2f resolution;
    private float radiusY;

    public GaussianBlurVertical(Vector2f resolution, float radiusY) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/filter/GaussianBlur.fsh"), true, 6, "GaussianBlurVertical", (Class<GLObject>) null);

        this.resolution = resolution;
        this.radiusY = radiusY;
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
        getFrameBuffer().getShaderProgramFBO().setUniform2f("direction", new Vector2f(0, 1));
        getFrameBuffer().getShaderProgramFBO().setUniform1f("radius", radiusY);
        getFrameBuffer().getShaderProgramFBO().setUniform2f("texelSize", new Vector2f(1.0f / baseGLObject.getResolution().x(), 1.0f / baseGLObject.getResolution().y()));

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i < radiusY; i++) {
            weightBuffer.put(MathUtil.calculateGaussianValue(i, radiusY / 2));
        }
        weightBuffer.rewind();
        getFrameBuffer().getShaderProgramFBO().setUniform1fv("values", weightBuffer);

        super.setUniform(baseGLObject);
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
    }

    public float getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(float radiusY) {
        this.radiusY = radiusY;
    }
}
