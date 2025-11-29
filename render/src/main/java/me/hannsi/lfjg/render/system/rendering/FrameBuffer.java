package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
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

import java.nio.ByteBuffer;

import static me.hannsi.lfjg.core.Core.frameBufferSize;
import static me.hannsi.lfjg.render.LFJGRenderContext.GL_STATE_CACHE;
import static me.hannsi.lfjg.render.LFJGRenderContext.SHADER_PROGRAM;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;
import static org.lwjgl.opengl.GL30.*;

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
        this(glObject, 0, 0, frameBufferSize.x(), frameBufferSize.y());
    }

    public FrameBuffer(GLObject glObject, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        frameBufferId = glGenFramebuffers();
        if (frameBufferId == 0) {
            throw new CreatingFrameBufferException("Failed to create frame buffer");
        }

        textureId = glGenTextures();
        if (textureId == 0) {
            throw new CreatingTextureException("Failed to create texture");
        }

        renderBufferId = glGenRenderbuffers();
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
        GL_STATE_CACHE.deleteFrameBuffer(frameBufferId);
        GL_STATE_CACHE.deleteTexture(GL_TEXTURE_2D, textureId);
        GL_STATE_CACHE.deleteRenderBuffer(renderBufferId);

        vaoRendering.cleanup();
        mesh.cleanup();
        vaoRendering.cleanup();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                frameBufferId,
                ""
        ).logging(getClass(), DebugLevel.DEBUG);
    }

    public void createMatrix(Matrix4f modelMatrix, Matrix4f viewMatrix) {
        this.modelMatrix = modelMatrix;
        this.viewMatrix = viewMatrix;
    }

    public void createFrameBuffer() {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Framebuffer size must be > 0: width=" + width + ", height=" + height);
        }

        GL_STATE_CACHE.bindFrameBuffer(frameBufferId);
        GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D, textureId);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, (int) width, (int) height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureId, 0);

        bindRenderBuffer();
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH32F_STENCIL8, (int) width, (int) height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, renderBufferId);

        int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);
        if (status != GL_FRAMEBUFFER_COMPLETE) {
            throw new CompleteFrameBufferException("Frame Buffer not complete: 0x" + Integer.toHexString(status));
        }

        GL_STATE_CACHE.bindRenderBuffer(0);
        GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D, 0);
        GL_STATE_CACHE.bindFrameBuffer(0);
    }

    public void drawFrameBuffer() {
        drawFrameBuffer(true);
    }

    public void drawFrameBuffer(boolean drawVAORendering) {
        SHADER_PROGRAM.bind();

        SHADER_PROGRAM.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.FRAME_BUFFER.getId());
        SHADER_PROGRAM.setUniform("modelMatrix", UploadUniformType.PER_FRAME, modelMatrix);
        SHADER_PROGRAM.setUniform("frameBufferSampler", UploadUniformType.ONCE, 3);

        GL_STATE_CACHE.enable(GL_BLEND);
        GL_STATE_CACHE.disable(GL_DEPTH_TEST);
        GL_STATE_CACHE.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        bindTexture();
        if (drawVAORendering) {
            drawVAORendering();
        }
    }

    public void drawVAORendering() {
        vaoRendering.draw(mesh);
    }

    public void bindTexture(int unit) {
        GL_STATE_CACHE.activeTexture(GL_TEXTURE0 + unit);
        GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D, textureId);
    }

    public void bindTexture() {
        GL_STATE_CACHE.activeTexture(GL_TEXTURE3);
        GL_STATE_CACHE.bindTexture(GL_TEXTURE_2D, textureId);
    }

    public void bindRenderBuffer() {
        GL_STATE_CACHE.bindRenderBuffer(renderBufferId);
    }

    public void bindDrawFrameBuffer() {
        GL_STATE_CACHE.bindDrawFrameBuffer(frameBufferId);
    }

    public void bindReadFrameBuffer() {
        GL_STATE_CACHE.bindReadFrameBuffer(frameBufferId);
    }

    public void bindFrameBuffer() {
        bindFrameBufferNoClear();

        GL_STATE_CACHE.clearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    public void bindFrameBufferNoClear() {
        GL_STATE_CACHE.bindFrameBuffer(frameBufferId);
    }

    public void unbindFrameBuffer() {
        GL_STATE_CACHE.bindFrameBuffer(0);
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