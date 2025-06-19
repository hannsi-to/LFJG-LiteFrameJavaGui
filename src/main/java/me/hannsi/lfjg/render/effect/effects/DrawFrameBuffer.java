package me.hannsi.lfjg.render.effect.effects;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;

@Getter
public class DrawFrameBuffer extends EffectBase {
    private FrameBuffer frameBuffer2;
    @Setter
    private float translateX;
    @Setter
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

    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer2 = frameBuffer;
    }

}
