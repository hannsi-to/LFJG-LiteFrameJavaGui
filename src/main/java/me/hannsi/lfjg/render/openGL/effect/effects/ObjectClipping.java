package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.openGL.system.rendering.GLObjectCache;

public class ObjectClipping extends EffectBase {
    private final GLObjectCache glObjectCache;
    private final String clippingObjectName;
    private final boolean invert;
    protected FrameBuffer clippingFrameBuffer;

    public ObjectClipping(GLObjectCache glObjectCache, String clippingObjectName, boolean invert) {
        super(27, "ObjectClipping");

        this.clippingFrameBuffer = new FrameBuffer();
        this.clippingFrameBuffer.createFrameBuffer();
        this.clippingFrameBuffer.createShaderProgram();

        this.glObjectCache = glObjectCache;
        this.clippingObjectName = clippingObjectName;
        this.invert = invert;
    }

    /**
     * Pushes the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPush(GLObject baseGLObject) {
        getFrameBuffer().bindFrameBuffer();
        super.frameBufferPush(baseGLObject);
    }

    /**
     * Pops the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBufferPop(GLObject baseGLObject) {
        getFrameBuffer().unbindFrameBuffer();
        super.frameBufferPop(baseGLObject);
    }

    /**
     * Draws the frame buffer for the base GL object.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void frameBuffer(GLObject baseGLObject) {
        GLObject clippingObject = glObjectCache.getGLObject(clippingObjectName);

        super.frameBuffer(baseGLObject);
    }

    /**
     * Sets the uniform variables for the shader program.
     *
     * @param baseGLObject the base GL object
     */
    @Override
    public void setUniform(GLObject baseGLObject) {
        super.setUniform(baseGLObject);
    }

    @Override
    public void cleanup() {
        clippingFrameBuffer.cleanup();
        super.cleanup();
    }
}
