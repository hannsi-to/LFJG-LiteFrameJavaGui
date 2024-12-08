package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.FrameBuffer;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;

public class GaussianBlur extends EffectBase {
    private FrameBuffer frameBuffer;

    private float radiusX;
    private float radiusY;

    public GaussianBlur(Vector2f resolution,float radiusX,float radiusY) {
        super(6, "GaussianBlur", (Class<GLObject>) null);

        this.radiusX = radiusX;
        this.radiusY = radiusY;

        frameBuffer = new FrameBuffer(resolution);
        //frameBuffer.setFragmentShaderFBO(new ResourcesLocation("shader/frameBuffer/filter/GaussianBlur.fsh"));
        frameBuffer.createFrameBuffer();
        frameBuffer.createShaderProgram();
    }

    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        frameBuffer.unbindFrameBuffer();

        frameBuffer.drawFrameBuffer();

        //frameBuffer.unbindFrameBuffer();

        //frameBuffer.getShaderProgramFBO().bind();
        //frameBuffer.getShaderProgramFBO().setUniform2f("direction",new Vector2f(1,0));
        //frameBuffer.getShaderProgramFBO().setUniform1f("radius",radiusX);
        //frameBuffer.getShaderProgramFBO().setUniform2f("texelSize", new Vector2f(1.0f / baseGLObject.getResolution().x(),1.0f / baseGLObject.getResolution().y()));

        //final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        //for(int i = 0; i < radiusX; i++){
        //  weightBuffer.put(calculateGaussianValue(i, radiusX / 2));
        //}
        //weightBuffer.rewind();
        //frameBuffer.getShaderProgramFBO().setUniform1fv("weights",weightBuffer);

        //frameBuffer.drawFrameBuffer();

        super.frameBufferPop(baseGLObject);
    }

    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        frameBuffer.bindFrameBuffer();

        //baseGLObject.getFrameBuffer().bindReadFrameBuffer();
        //frameBuffer.bindDrawFrameBuffer();

        //GL30.glBlitFramebuffer(
        //        0,0,1920,1080,
        //        0,0,1920,1080,
        //        GL30.GL_COLOR_BUFFER_BIT,
        //        GL30.GL_NEAREST
        //);

        //frameBuffer.unBindDrawFrameBuffer();
        //baseGLObject.getFrameBuffer().unBindReadFrameBuffer();

        super.frameBufferPush(baseGLObject);
    }

    private static float calculateGaussianValue(float x, float sigma) {
        double PI = 3.141592653;
        double output = 1.0 / Math.sqrt(2.0 * PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }
}
