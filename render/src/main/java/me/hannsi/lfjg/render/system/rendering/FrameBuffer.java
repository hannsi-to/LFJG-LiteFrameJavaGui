package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.debug.exceptions.frameBuffer.CompleteFrameBufferException;
import me.hannsi.lfjg.render.debug.exceptions.frameBuffer.CreatingFrameBufferException;
import me.hannsi.lfjg.render.debug.exceptions.render.scene.CreatingRenderBufferException;
import me.hannsi.lfjg.render.debug.exceptions.texture.CreatingTextureException;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Matrix4f;

import java.nio.ByteBuffer;

import static me.hannsi.lfjg.core.Core.frameBufferSize;
import static org.lwjgl.opengl.GL11.glGenTextures;
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
        this(uesStencil, glObject, 0, 0, frameBufferSize.x(), frameBufferSize.y());
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

        mesh = Mesh.createMesh()
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
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Framebuffer size must be > 0: width=" + width + ", height=" + height);
        }

        GLStateCache.bindFrameBuffer(frameBufferId);
        GLStateCache.bindTexture(GL_TEXTURE_2D, textureId);

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

        GLStateCache.bindRenderBuffer(0);
        GLStateCache.bindTexture(GL_TEXTURE_2D, 0);
        GLStateCache.bindFrameBuffer(0);
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

        shaderProgramFBO.setUniform("projectionMatrix", UploadUniformType.ON_CHANGE, Core.projection2D.getProjMatrix());
        shaderProgramFBO.setUniform("modelMatrix", UploadUniformType.ON_CHANGE, modelMatrix);
        shaderProgramFBO.setUniform("viewMatrix", UploadUniformType.ON_CHANGE, viewMatrix);
        shaderProgramFBO.setUniform("textureSampler", UploadUniformType.ONCE, textureUnit);

        GLStateCache.enable(GL_BLEND);
        GLStateCache.disable(GL_DEPTH_TEST);
        if (uesStencil) {
            GLStateCache.enable(GL_STENCIL_TEST);
        }

        GLStateCache.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        if (uesStencil) {
            glClear(GL_STENCIL_BUFFER_BIT);
            GLStateCache.stencilFunc(GL_ALWAYS, 1, 0xff);
            GLStateCache.stencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
            GLStateCache.colorMask(false, false, false, false);
            vaoRendering.draw(glObject.getMesh());
            GLStateCache.colorMask(true, true, true, true);

            GLStateCache.stencilFunc(GL_EQUAL, 1, 0xff);
            GLStateCache.stencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
        }

        bindTexture(textureUnit);
        vaoRendering.draw(mesh);
    }

    public void bindTexture(int textureUnit) {
        GLStateCache.activeTexture(GL_TEXTURE0 + textureUnit);
        GLStateCache.bindTexture(GL_TEXTURE_2D, textureId);
    }

    /**
     * Binds the render buffer.
     */
    public void bindRenderBuffer() {
        GLStateCache.bindRenderBuffer(renderBufferId);
    }

    /**
     * Binds the frame buffer for drawing.
     */
    public void bindDrawFrameBuffer() {
        GLStateCache.bindDrawFrameBuffer(frameBufferId);
    }

    /**
     * Binds the frame buffer for reading.
     */
    public void bindReadFrameBuffer() {
        GLStateCache.bindReadFrameBuffer(frameBufferId);
    }

    /**
     * Binds the frame buffer.
     */
    public void bindFrameBuffer() {
        bindFrameBufferNoClear();

        GLStateCache.clearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    public void bindFrameBufferNoClear() {
        GLStateCache.bindFrameBuffer(frameBufferId);
    }

    /**
     * Unbinds the frame buffer.
     */
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
        return shaderProgramFBO;
    }

    public void setShaderProgramFBO(ShaderProgram shaderProgramFBO) {
        this.shaderProgramFBO = shaderProgramFBO;
    }

    public Location getVertexShaderFBO() {
        return vertexShaderFBO;
    }

    public void setVertexShaderFBO(Location vertexShaderFBO) {
        this.vertexShaderFBO = vertexShaderFBO;
    }

    public Location getFragmentShaderFBO() {
        return fragmentShaderFBO;
    }

    public void setFragmentShaderFBO(Location fragmentShaderFBO) {
        this.fragmentShaderFBO = fragmentShaderFBO;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public boolean isUesStencil() {
        return uesStencil;
    }

    public void setUesStencil(boolean uesStencil) {
        this.uesStencil = uesStencil;
    }

    public GLObject getGlObject() {
        return glObject;
    }

    public void setGlObject(GLObject glObject) {
        this.glObject = glObject;
    }
}