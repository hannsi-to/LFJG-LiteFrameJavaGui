package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

/**
 * Class representing a Draw Object effect in OpenGL.
 */
public class DrawObject extends EffectBase {

    DrawObject() {
        super(Integer.MAX_VALUE, "DrawObject");
    }

    public static DrawObject createDrawObject() {
        return new DrawObject();
    }

    /**
     * Draws the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBuffer(GLObject baseGLObject) {
        baseGLObject.getFrameBuffer().drawFrameBuffer();
        super.frameBuffer(baseGLObject);
    }
}