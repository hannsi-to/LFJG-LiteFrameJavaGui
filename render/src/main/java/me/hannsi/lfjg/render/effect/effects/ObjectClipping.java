package me.hannsi.lfjg.render.effect.effects;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.rendering.GLObjectCache;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

public class ObjectClipping extends EffectBase {
    private final GLObjectCache glObjectCache;
    private final String clippingObjectName;
    protected FrameBuffer clippingFrameBuffer;
    private boolean invert = false;

    ObjectClipping(GLObjectCache glObjectCache, String clippingObjectName) {
        super(Location.fromResource("shader/frameBuffer/filter/ObjectClipping.fsh"), true, 27, "ObjectClipping");

        this.clippingFrameBuffer = new FrameBuffer();
        this.clippingFrameBuffer.createFrameBuffer();
        this.clippingFrameBuffer.createShaderProgram();

        this.glObjectCache = glObjectCache;
        this.clippingObjectName = clippingObjectName;
    }

    public static ObjectClipping createObjectClipping(GLObjectCache glObjectCache, String clippingObjectName) {
        return new ObjectClipping(glObjectCache, clippingObjectName);
    }

    public ObjectClipping invert(boolean invert) {
        this.invert = invert;
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
        getFrameBuffer().getShaderProgramFBO().setUniform("clippingTextureSampler", UploadUniformType.ON_CHANGE, 1);
        getFrameBuffer().getShaderProgramFBO().setUniform("invert", UploadUniformType.ON_CHANGE, invert);

        super.setUniform(baseGLObject);
    }

    @Override
    public void cleanup() {
        clippingFrameBuffer.cleanup();
        super.cleanup();
    }

    public GLObjectCache getGlObjectCache() {
        return glObjectCache;
    }

    public String getClippingObjectName() {
        return clippingObjectName;
    }

    public FrameBuffer getClippingFrameBuffer() {
        return clippingFrameBuffer;
    }

    public void setClippingFrameBuffer(FrameBuffer clippingFrameBuffer) {
        this.clippingFrameBuffer = clippingFrameBuffer;
    }

    public boolean isInvert() {
        return invert;
    }

    public void setInvert(boolean invert) {
        this.invert = invert;
    }
}
