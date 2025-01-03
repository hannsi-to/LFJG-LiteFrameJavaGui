package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import org.joml.Vector2f;

public class DrawObject extends EffectBase {
    public DrawObject(Vector2f resolution) {
        super(resolution, Integer.MAX_VALUE, "DrawObject", (Class<GLObject>) null);
    }

    @Override
    public void frameBuffer(GLObject baseGLObject) {
        baseGLObject.getFrameBuffer().drawFrameBuffer();

        super.frameBuffer(baseGLObject);
    }
}