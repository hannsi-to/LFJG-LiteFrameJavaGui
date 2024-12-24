package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.effect.system.EffectCache;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.math.MathUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class GaussianBlurVertical extends EffectBase {
    private Vector2f resolution;
    private float radiusY;

    public GaussianBlurVertical(Vector2f resolution,float radiusY) {
        super(resolution, new ResourcesLocation("shader/frameBuffer/FragmentShader.fsh"),true, 6,"GaussianBlurVertical", (Class<GLObject>) null);

        this.resolution = resolution;
        this.radiusY = radiusY;
    }

    @Override
    public void frameBuffer(EffectCache effectCache, int oldIndex, GLObject glObject) {
        getFrameBuffer().getShaderProgramFBO().bind();

        getFrameBuffer().getShaderProgramFBO().setUniform2f("direction",new Vector2f(0,1));
        getFrameBuffer().getShaderProgramFBO().setUniform1f("radius", radiusY);
        getFrameBuffer().getShaderProgramFBO().setUniform2f("texelSize", new Vector2f(1.0f / glObject.getResolution().x(),1.0f / glObject.getResolution().y()));

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for(int i = 0; i < radiusY; i++){
            weightBuffer.put(MathUtil.calculateGaussianValue(i, radiusY / 2));
        }
        weightBuffer.rewind();
        getFrameBuffer().getShaderProgramFBO().setUniform1fv("values",weightBuffer);

        getFrameBuffer().getShaderProgramFBO().unbind();

        super.frameBuffer(effectCache, oldIndex, glObject);
    }
}
