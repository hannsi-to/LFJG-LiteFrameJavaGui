package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.openGL.system.rendering.GLObjectCache;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;

public class ObjectClipping extends EffectBase {
    private final GLObjectCache glObjectCache;
    private final String clippingObjectName;
    private final boolean invert;
    protected FrameBuffer clippingFrameBuffer;

    public ObjectClipping(GLObjectCache glObjectCache, String clippingObjectName, boolean invert) {
        super(new ResourcesLocation("shader/frameBuffer/filter/ObjectClipping.fsh"), true, 27, "ObjectClipping");

        this.clippingFrameBuffer = new FrameBuffer();
        this.clippingFrameBuffer.createFrameBuffer();
        this.clippingFrameBuffer.createShaderProgram();

        this.glObjectCache = glObjectCache;
        this.clippingObjectName = clippingObjectName;
        this.invert = invert;
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
        GLObject clippingObject = glObjectCache.getGLObject(clippingObjectName);
        clippingObject.draw(false);

        if (clippingObject.getEffectCache() != null) {
            clippingObject.getEffectCache().getEndFrameBuffer().bindTexture(1);
        } else {
            clippingObject.getFrameBuffer().bindTexture(1);
        }

        getFrameBuffer().drawFrameBuffer();

        if (clippingObject.getEffectCache() != null) {
            clippingObject.getEffectCache().getEndFrameBuffer().bindTexture(1);
        } else {
            clippingObject.getFrameBuffer().unbindTexture(1);
        }

        super.frameBuffer(baseGLObject);
    }

    @Override
    public void setUniform(GLObject baseGLObject) {
        getFrameBuffer().getShaderProgramFBO().setUniform1i("clippingTextureSampler", 1);
        getFrameBuffer().getShaderProgramFBO().setUniform("invert", invert);

        super.setUniform(baseGLObject);
    }

    @Override
    public void cleanup() {
        clippingFrameBuffer.cleanup();
        super.cleanup();
    }
}
