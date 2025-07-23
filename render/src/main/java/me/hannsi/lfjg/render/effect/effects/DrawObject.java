package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

public class DrawObject extends EffectBase {

    DrawObject() {
        super(Integer.MAX_VALUE, "DrawObject");
    }

    public static DrawObject createDrawObject() {
        return new DrawObject();
    }

    @Override
    public void frameBuffer(GLObject baseGLObject) {
        baseGLObject.getFrameBuffer().drawFrameBuffer();
        super.frameBuffer(baseGLObject);
    }
}