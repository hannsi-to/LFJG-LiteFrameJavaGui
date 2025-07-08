package me.hannsi.lfjg.render.system.rendering;

import lombok.Data;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.debug.exceptions.frameBuffer.CompleteFrameBufferException;
import me.hannsi.lfjg.render.debug.exceptions.frameBuffer.CreatingFrameBufferException;
import me.hannsi.lfjg.render.debug.exceptions.render.scene.CreatingRenderBufferException;
import me.hannsi.lfjg.render.debug.exceptions.texture.CreatingTextureException;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Matrix4f;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL30.*;

/**
 * Represents a frame buffer in the OpenGL rendering system.
 */
@Data
public class FrameBuffer {
    private static float lastClearR = -1f, lastClearG = -1f, lastClearB = -1f, lastClearA = -1f;

    private final int frameBufferId;
    private final int textureId;
    private final int renderBufferId;
    private final VAORendering vaoRendering;
    private float x;
    private float y;
    private float width;
    private float height;
    private Mesh mesh;
    private ShaderProgram shaderProgramFBO;
    private Location vertexShaderFBO;
    private Location fragmentShaderFBO;
    private Matrix4f modelMatrix;
    private Matrix4f viewMatrix;

    private boolean uesStencil;
    private GLObject glObject;

    /**
     * Constructs a new FrameBuffer with the specified resolution.
     */
    public FrameBuffer() {
        this(false, null);
    }

    public FrameBuffer(float x, float y, float width, float height) {
        this(false, null, x, y, width, height);
    }

    public FrameBuffer(boolean uesStencil, GLObject glObject) {
        this(uesStencil, glObject, 0, 0, LFJGContext.frameBufferSize.x(), LFJGContext.frameBufferSize.y());
    }

    /**
     * Constructs a new FrameBuffer with the specified resolution, stencil usage, and GL object.
     *
     * @param uesStencil whether to use stencil buffer
     * @param glObject   the GL object associated with the frame buffer
     */
    public FrameBuffer(boolean uesStencil, GLObject glObject, float x, float y, float width, float height) {
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

        shaderProgramFBO = new ShaderProgram();
        vertexShaderFBO = Location.fromResource("shader/frameBuffer/VertexShader.vsh");
        fragmentShaderFBO = Location.fromResource("shader/frameBuffer/FragmentShader.fsh");

        vaoRendering = new VAORendering();

        float[] positions = new float[]{x, y, x + width, y, x + width, y + height, x, y + height};

        float[] uvs = new float[]{0, 0, 1, 0, 1, 1, 0, 1};

        mesh = Mesh.initMesh()
                .projectionType(ProjectionType.ORTHOGRAPHIC_PROJECTION)
                .createBufferObject2D(positions, null, uvs)
                .builderClose();

        this.uesStencil = uesStencil;
        this.glObject = glObject;
    }

    /**
     * Cleans up the frame buffer by deleting the frame buffer and texture.
     */
    public void cleanup() {
        glDeleteFramebuffers(frameBufferId);
        glDeleteTextures(textureId);
        glDeleteRenderbuffers(renderBufferId);

        vaoRendering.cleanup();
        mesh.cleanup();
        shaderProgramFBO.cleanup();
        vaoRendering.cleanup();

        modelMatrix = null;
        viewMatrix = null;

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                frameBufferId,
                ""
        ).logging(DebugLevel.DEBUG);
    }

    /**
     * Creates the shader program for the frame buffer.
     */
    public void createShaderProgram() {
        shaderProgramFBO.createVertexShader(vertexShaderFBO);
        shaderProgramFBO.createFragmentShader(fragmentShaderFBO);
        shaderProgramFBO.link();

        modelMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
    }

    /**
     * Creates the frame buffer and its associated texture and render buffer.
     */
    public void createFrameBuffer() {
        glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glBindRenderbuffer(GL_RENDERBUFFER, renderBufferId);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, (int) width, (int) height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureId, 0);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw new CompleteFrameBufferException("Frame Buffer not complete");
        }

        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH32F_STENCIL8, (int) width, (int) height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, renderBufferId);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw new RuntimeException("Frame Buffer not complete");
        }

        glBindRenderbuffer(GL_RENDERBUFFER, 0);
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    /**
     * Draws the frame buffer using the default texture unit.
     */
    public void drawFrameBuffer() {
        drawFrameBuffer(0);
    }

    /**
     * Draws the frame buffer using the specified texture unit.
     *
     * @param textureUnit the texture unit to use
     */
    public void drawFrameBuffer(int textureUnit) {
        shaderProgramFBO.bind();

        shaderProgramFBO.setUniformMatrix4fv("projectionMatrix", LFJGContext.projection2D.getProjMatrix());
        shaderProgramFBO.setUniformMatrix4fv("modelMatrix", modelMatrix);
        shaderProgramFBO.setUniformMatrix4fv("viewMatrix", viewMatrix);
        shaderProgramFBO.setUniform1i("textureSampler", textureUnit);

        GLStateCache.enable(GL_BLEND);
        GLStateCache.disable(GL_DEPTH_TEST);
        if (uesStencil) {
            GLStateCache.enable(GL_STENCIL_TEST);
        }

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        if (uesStencil) {
            glClear(GL_STENCIL_BUFFER_BIT);
            glStencilFunc(GL_ALWAYS, 1, 0xff);
            glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
            glColorMask(false, false, false, false);
            vaoRendering.draw(glObject.getMesh());
            glColorMask(true, true, true, true);

            glStencilFunc(GL_EQUAL, 1, 0xff);
            glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
        }

        bindTexture(textureUnit);
        vaoRendering.draw(mesh);
        unbindTexture(textureUnit);

        shaderProgramFBO.unbind();
    }

    /**
     * Binds the render buffer.
     */
    public void bindRenderBuffer() {
        glBindRenderbuffer(GL_RENDERBUFFER, renderBufferId);
    }

    /**
     * Unbinds the render buffer.
     */
    public void unbindRenderBuffer() {
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
    }

    /**
     * Binds the frame buffer for drawing.
     */
    public void bindDrawFrameBuffer() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, frameBufferId);
    }

    /**
     * Unbinds the frame buffer from drawing.
     */
    public void unbindDrawFrameBuffer() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
    }

    /**
     * Binds the frame buffer for reading.
     */
    public void bindReadFrameBuffer() {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, frameBufferId);
    }

    /**
     * Unbinds the frame buffer from reading.
     */
    public void unBindReadFrameBuffer() {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);
    }

    /**
     * Binds the frame buffer.
     */
    public void bindFrameBuffer() {
        bindFrameBufferNoClear();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    public void bindFrameBufferNoClear() {
        glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId);
    }

    /**
     * Unbinds the frame buffer.
     */
    public void unbindFrameBuffer() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    /**
     * Binds the texture to the specified texture unit.
     *
     * @param textureUnit the texture unit to bind to
     */
    public void bindTexture(int textureUnit) {
        glActiveTexture(GL_TEXTURE0 + textureUnit);
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    /**
     * Unbinds the texture from the specified texture unit.
     *
     * @param textureUnit the texture unit to unbind from
     */
    public void unbindTexture(int textureUnit) {
//        glBindTexture(GL_TEXTURE_2D, 0);
    }
}