package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.debug.exceptions.frameBuffer.CompleteFrameBufferException;
import me.hannsi.lfjg.render.debug.exceptions.frameBuffer.CreatingFrameBufferException;
import me.hannsi.lfjg.render.debug.exceptions.render.scene.CreatingRenderBufferException;
import me.hannsi.lfjg.render.debug.exceptions.texture.CreatingTextureException;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;

public class FrameBuffer {
    private final int frameBufferId;
    private final int textureId;
    private final int renderBufferId;
    private final VAORendering vaoRendering;
    private float x;
    private float y;
    private float width;
    private float height;
    private Mesh mesh;
    private Matrix4f modelMatrix;
    private Matrix4f viewMatrix;

    private GLObject glObject;

    public FrameBuffer() {
        this(null);
    }

    public FrameBuffer(float x, float y, float width, float height) {
        this(null, x, y, width, height);
    }

    public FrameBuffer(GLObject glObject) {
        this(glObject, 0, 0, Core.frameBufferSize.x(), Core.frameBufferSize.y());
    }

    public FrameBuffer(GLObject glObject, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        frameBufferId = GL30.glGenFramebuffers();
        if (frameBufferId == 0) {
            throw new CreatingFrameBufferException("Failed to create frame buffer");
        }

        textureId = GL11.glGenTextures();
        if (textureId == 0) {
            throw new CreatingTextureException("Failed to create texture");
        }

        renderBufferId = GL30.glGenRenderbuffers();
        if (renderBufferId == 0) {
            throw new CreatingRenderBufferException("Failed to create render buffer");
        }

        vaoRendering = new VAORendering();

        float[] positions = new float[]{x, y, x + width, y, x + width, y + height, x, y + height};
        float[] uvs = new float[]{0, 0, 1, 0, 1, 1, 0, 1};

        mesh = Mesh.createMesh()
                .projectionType(ProjectionType.ORTHOGRAPHIC_PROJECTION)
                .createBufferObject2D(DrawType.QUADS, positions, null, uvs)
                .builderClose();

        this.glObject = glObject;
    }

    public void cleanup() {
        GLStateCache.deleteFrameBuffer(frameBufferId);
        GLStateCache.deleteTexture(GL11.GL_TEXTURE_2D, textureId);
        GLStateCache.deleteRenderBuffer(renderBufferId);

        vaoRendering.cleanup();
        mesh.cleanup();
        vaoRendering.cleanup();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                frameBufferId,
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public void createMatrix(Matrix4f modelMatrix, Matrix4f viewMatrix) {
        this.modelMatrix = modelMatrix;
        this.viewMatrix = viewMatrix;
    }

    public void createFrameBuffer() {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Framebuffer size must be > 0: width=" + width + ", height=" + height);
        }

        GLStateCache.bindFrameBuffer(frameBufferId);
        GLStateCache.bindTexture(GL11.GL_TEXTURE_2D, textureId);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, (int) width, (int) height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, textureId, 0);

        bindRenderBuffer();
        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH32F_STENCIL8, (int) width, (int) height);
        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL30.GL_RENDERBUFFER, renderBufferId);

        int status = GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER);
        if (status != GL30.GL_FRAMEBUFFER_COMPLETE) {
            throw new CompleteFrameBufferException("Frame Buffer not complete: 0x" + Integer.toHexString(status));
        }

        GLStateCache.bindRenderBuffer(0);
        GLStateCache.bindTexture(GL11.GL_TEXTURE_2D, 0);
        GLStateCache.bindFrameBuffer(0);
    }

    public void drawFrameBuffer() {
        drawFrameBuffer(true);
    }

    public void drawFrameBuffer(boolean drawVAORendering) {
        LFJGRenderContext.shaderProgram.bind();

        LFJGRenderContext.shaderProgram.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.FRAME_BUFFER.getId());
        LFJGRenderContext.shaderProgram.setUniform("modelMatrix", UploadUniformType.PER_FRAME, modelMatrix);
        LFJGRenderContext.shaderProgram.setUniform("frameBufferSampler", UploadUniformType.ONCE, 3);

        GLStateCache.enable(GL11.GL_BLEND);
        GLStateCache.disable(GL11.GL_DEPTH_TEST);
        GLStateCache.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        bindTexture();
        if (drawVAORendering) {
            drawVAORendering();
        }
    }

    public void drawVAORendering() {
        vaoRendering.draw(mesh);
    }

    public void bindTexture(int unit) {
        GLStateCache.activeTexture(GL13.GL_TEXTURE0 + unit);
        GLStateCache.bindTexture(GL11.GL_TEXTURE_2D, textureId);
    }

    public void bindTexture() {
        GLStateCache.activeTexture(GL13.GL_TEXTURE3);
        GLStateCache.bindTexture(GL11.GL_TEXTURE_2D, textureId);
    }

    public void bindRenderBuffer() {
        GLStateCache.bindRenderBuffer(renderBufferId);
    }

    public void bindDrawFrameBuffer() {
        GLStateCache.bindDrawFrameBuffer(frameBufferId);
    }

    public void bindReadFrameBuffer() {
        GLStateCache.bindReadFrameBuffer(frameBufferId);
    }

    public void bindFrameBuffer() {
        bindFrameBufferNoClear();

        GLStateCache.clearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
    }

    public void bindFrameBufferNoClear() {
        GLStateCache.bindFrameBuffer(frameBufferId);
    }

    public void unbindFrameBuffer() {
        GLStateCache.bindFrameBuffer(0);
    }

    public int getFrameBufferId() {
        return frameBufferId;
    }

    public int getTextureId() {
        return textureId;
    }

    public int getRenderBufferId() {
        return renderBufferId;
    }

    public VAORendering getVaoRendering() {
        return vaoRendering;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public ShaderProgram getShaderProgramFBO() {
        return null;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public GLObject getGlObject() {
        return glObject;
    }

    public void setGlObject(GLObject glObject) {
        this.glObject = glObject;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
    }
}