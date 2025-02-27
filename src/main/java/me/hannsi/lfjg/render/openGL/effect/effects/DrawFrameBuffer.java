package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;

public class DrawFrameBuffer extends EffectBase {
    private FrameBuffer frameBuffer2;
    private float translateX;
    private float translateY;

    public DrawFrameBuffer(FrameBuffer frameBuffer) {
        super(Integer.MAX_VALUE - 1, "DrawFrameBuffer");

        this.frameBuffer2 = frameBuffer;
    }

    @Override
    public void frameBuffer(GLObject baseGLObject) {
        frameBuffer2.getModelMatrix().translate(translateX, translateY, 0);
        frameBuffer2.drawFrameBuffer();
        frameBuffer2.getModelMatrix().translate(-translateX, -translateY, 0);

        super.frameBuffer(baseGLObject);
    }

    public FrameBuffer getFrameBuffer2() {
        return frameBuffer2;
    }

    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer2 = frameBuffer;
    }

    public float getTranslateX() {
        return translateX;
    }

    public void setTranslateX(float translateX) {
        this.translateX = translateX;
    }

    public float getTranslateY() {
        return translateY;
    }

    public void setTranslateY(float translateY) {
        this.translateY = translateY;
    }
}
