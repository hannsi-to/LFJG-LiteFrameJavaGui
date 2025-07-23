package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class GaussianBlurHorizontal extends EffectBase {
    private Vector2i resolution = new Vector2i();
    private float radiusX = 30f;

    GaussianBlurHorizontal() {
        super(Location.fromResource("shader/frameBuffer/filter/GaussianBlur.fsh"), true, 6, "GaussianBlurHorizontal");
    }

    public static GaussianBlurHorizontal createGaussianBlurHorizontal() {
        return new GaussianBlurHorizontal();
    }

    public GaussianBlurHorizontal resolution(Vector2i resolution) {
        this.resolution = resolution;
        return this;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("direction", new Vector2f(1, 0));
        getFrameBuffer().getShaderProgramFBO().setUniform("radius", radiusX);
        getFrameBuffer().getShaderProgramFBO().setUniform("texelSize", new Vector2f(1.0f / resolution.x(), 1.0f / resolution.y()));

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i < radiusX; i++) {
            weightBuffer.put(MathHelper.calculateGaussianValue(i, radiusX / 2));
        }
        weightBuffer.rewind();
        getFrameBuffer().getShaderProgramFBO().setUniform("values", weightBuffer);

        super.setUniform(baseGLObject);
    }

    public Vector2i getResolution() {
        return resolution;
    }

    public void setResolution(Vector2i resolution) {
        this.resolution = resolution;
    }

    public float getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(float radiusX) {
        this.radiusX = radiusX;
    }
}