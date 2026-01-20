package me.hannsi.lfjg.render.system.rendering.frameBuffer;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.render.debug.exceptions.frameBuffer.CompleteFrameBufferException;
import me.hannsi.lfjg.render.debug.exceptions.frameBuffer.CreatingFrameBufferException;
import me.hannsi.lfjg.render.debug.exceptions.render.scene.CreatingRenderBufferException;
import me.hannsi.lfjg.render.debug.exceptions.texture.CreatingTextureException;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.render.system.mesh.MeshBuilder;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Matrix4f;

import java.nio.ByteBuffer;

import static me.hannsi.lfjg.core.Core.frameBufferSize;
import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;
import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {
    public final IntRef id;
    private final int frameBufferId;
    private final int textureId;
    private final int renderBufferId;
    private final VAORendering vaoRendering;
    private float x;
    private float y;
    private float width;
    private float height;
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
        this.id = new IntRef();
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

        mesh.addObject(
                MeshBuilder.createBuilder()
                        .drawType(DrawType.QUADS)
                        .blendType(GLPolygon.DEFAULT_BLEND_TYPE)
                        .pointSize(-1f)
                        .lineWidth(-1f)
                        .jointType(GLPolygon.DEFAULT_JOINT_TYPE)
                        .pointType(GLPolygon.DEFAULT_POINT_TYPE)
                        .vertices(
                                new Vertex(x, y, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                new Vertex(x + width, y, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0),
                                new Vertex(x + width, y + height, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0),
                                new Vertex(x, y + height, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0)
                        )
        );

        this.glObject = glObject;
    }

    public void cleanup() {
        glStateCache.deleteFrameBuffer(frameBufferId);
        glStateCache.deleteTexture(GL_TEXTURE_2D, textureId);
        glStateCache.deleteRenderBuffer(renderBufferId);

        mesh.deleteObject(id.getValue());

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

        glStateCache.bindFrameBuffer(frameBufferId);
        glStateCache.bindTexture(GL_TEXTURE_2D, textureId);

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

        glStateCache.bindRenderBuffer(0);
        glStateCache.bindTexture(GL_TEXTURE_2D, 0);
        glStateCache.bindFrameBuffer(0);
    }

    public void drawFrameBuffer() {
        drawFrameBuffer(true);
    }

    public void drawFrameBuffer(boolean drawVAORendering) {
        shaderProgram.bind();

        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.FRAME_BUFFER.getId());
        shaderProgram.setUniform("modelMatrix", UploadUniformType.PER_FRAME, modelMatrix);
        shaderProgram.setUniform("frameBufferSampler", UploadUniformType.ONCE, 3);

        glStateCache.enable(GL_BLEND);
        glStateCache.disable(GL_DEPTH_TEST);
        glStateCache.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        bindTexture();
        if (drawVAORendering) {
            drawVAORendering();
        }
    }

    public void drawVAORendering() {
//        vaoRendering.draw();
    }

    public void bindTexture(int unit) {
        glStateCache.activeTexture(GL_TEXTURE0 + unit);
        glStateCache.bindTexture(GL_TEXTURE_2D, textureId);
    }

    public void bindTexture() {
        glStateCache.activeTexture(GL_TEXTURE3);
        glStateCache.bindTexture(GL_TEXTURE_2D, textureId);
    }

    public void bindRenderBuffer() {
        glStateCache.bindRenderBuffer(renderBufferId);
    }

    public void bindDrawFrameBuffer() {
        glStateCache.bindDrawFrameBuffer(frameBufferId);
    }

    public void bindReadFrameBuffer() {
        glStateCache.bindReadFrameBuffer(frameBufferId);
    }

    public void bindFrameBuffer() {
        bindFrameBufferNoClear();

        glStateCache.clearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    public void bindFrameBufferNoClear() {
        glStateCache.bindFrameBuffer(frameBufferId);
    }

    public void unbindFrameBuffer() {
        glStateCache.bindFrameBuffer(0);
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