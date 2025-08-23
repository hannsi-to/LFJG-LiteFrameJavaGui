package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;

public class FrameBufferContents extends EffectBase {
    private float translateX = 0f;
    private float translateY = 0f;

    FrameBufferContents() {
        super(Location.fromResource("shader/frameBuffer/filter/FrameBufferContents.fsh"), true, 25, "FrameBufferContents");
    }

    public static FrameBufferContents createFrameBufferContents() {
        return new FrameBufferContents();
    }

    public FrameBufferContents translateX(float translateX) {
        this.translateX = translateX;
        return this;
    }

    public FrameBufferContents translateX(double translateX) {
        this.translateX = (float) translateX;
        return this;
    }

    public FrameBufferContents translateY(float translateY) {
        this.translateY = translateY;
        return this;
    }

    public FrameBufferContents translateY(double translateY) {
        this.translateY = (float) translateY;
        return this;
    }

    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        getFrameBuffer().bindFrameBuffer();
        super.frameBufferPush(baseGLObject);
    }

    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        getFrameBuffer().unbindFrameBuffer();
        super.frameBufferPop(baseGLObject);
    }

    @Override
    public void frameBuffer(GLObject baseGLObject) {
        getFrameBuffer().getModelMatrix().translate(translateX, translateY, 0);
        getFrameBuffer().drawFrameBuffer();
        getFrameBuffer().getModelMatrix().translate(-translateX, -translateY, 0);

        super.frameBuffer(baseGLObject);
    }

    @Override
    public void setUniform(GLObject baseGLObject) {
        getFrameBuffer().drawFrameBuffer();

        super.setUniform(baseGLObject);
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