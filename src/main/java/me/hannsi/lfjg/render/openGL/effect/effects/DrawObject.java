package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;

/**
 * Class representing a Draw Object effect in OpenGL.
 */
public class DrawObject extends EffectBase {

    /**
     * Constructs a new DrawObject effect with the specified resolution.
     */
    public DrawObject() {
        super(Integer.MAX_VALUE, "DrawObject");
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