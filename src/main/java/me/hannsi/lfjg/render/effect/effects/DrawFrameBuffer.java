package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;

@Getter
class DrawFrameBuffer extends EffectBase {
    private FrameBuffer frameBuffer2 = null;
    @Setter
    private float translateX = 0f;
    @Setter
    private float translateY = 0f;

    DrawFrameBuffer() {
        super(Integer.MAX_VALUE - 1, "DrawFrameBuffer");
    }

    public static DrawFrameBuffer createDrawFrameBuffer() {
        return new DrawFrameBuffer();
    }

    public DrawFrameBuffer frameBuffer2(FrameBuffer frameBuffer2) {
        this.frameBuffer2 = frameBuffer2;
        return this;
    }

    public DrawFrameBuffer translateX(float translateX) {
        this.translateX = translateX;
        return this;
    }

    public DrawFrameBuffer translateX(double translateX) {
        this.translateX = (float) translateX;
        return this;
    }

    public DrawFrameBuffer translateY(float translateY) {
        this.translateY = translateY;
        return this;
    }

    public DrawFrameBuffer translateY(double translateY) {
        this.translateY = (float) translateY;
        return this;
    }

    @Override
    public void frameBuffer(GLObject baseGLObject) {
        frameBuffer2.getModelMatrix().translate(translateX, translateY, 0);
        frameBuffer2.drawFrameBuffer();
        frameBuffer2.getModelMatrix().translate(-translateX, -translateY, 0);

        super.frameBuffer(baseGLObject);
    }

    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer2 = frameBuffer;
    }

}
