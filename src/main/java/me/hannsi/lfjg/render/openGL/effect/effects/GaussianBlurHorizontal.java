package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.effect.system.EffectCache;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.math.MathUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class GaussianBlurHorizontal extends EffectBase {
    private Vector2f resolution;
    private float radiusX;

    public GaussianBlurHorizontal(Vector2f resolution,float radiusX) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/FragmentShader.fsh"),true, 6,"GaussianBlurHorizontal", (Class<GLObject>) null);

        this.resolution = resolution;
        this.radiusX = radiusX;
    }

    @Override
    public void frameBuffer(EffectCache effectCache, int oldIndex, GLObject glObject) {
        getFrameBuffer().getShaderProgramFBO().bind();

        getFrameBuffer().getShaderProgramFBO().setUniform2f("direction",new Vector2f(1,0));
        getFrameBuffer().getShaderProgramFBO().setUniform1f("radius",radiusX);
        getFrameBuffer().getShaderProgramFBO().setUniform2f("texelSize", new Vector2f(1.0f / glObject.getResolution().x(),1.0f / glObject.getResolution().y()));

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for(int i = 0; i < radiusX; i++){
            weightBuffer.put(MathUtil.calculateGaussianValue(i, radiusX / 2));
        }
        weightBuffer.rewind();
        getFrameBuffer().getShaderProgramFBO().setUniform1fv("values",weightBuffer);

        getFrameBuffer().getShaderProgramFBO().unbind();

        super.frameBuffer(effectCache, oldIndex, glObject);
    }
}
