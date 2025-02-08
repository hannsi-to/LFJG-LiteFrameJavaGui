package me.hannsi.lfjg.render.openGL.system.rendering;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.exceptions.frameBuffer.CompleteFrameBufferException;
import me.hannsi.lfjg.debug.exceptions.frameBuffer.CreatingFrameBufferException;
import me.hannsi.lfjg.debug.exceptions.render.CreatingRenderBufferException;
import me.hannsi.lfjg.debug.exceptions.texture.CreatingTextureException;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.Mesh;
import me.hannsi.lfjg.render.openGL.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.graphics.GLUtil;
import me.hannsi.lfjg.utils.math.Projection;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImageWrite;

import java.nio.ByteBuffer;

/**
 * Represents a frame buffer in the OpenGL rendering system.
 */
public class FrameBuffer {
    private final int frameBufferId;
    private final int textureId;
    private final int renderBufferId;
    private final Mesh mesh;
    private final VAORendering vaoRendering;
    private Vector2f resolution;
    private ShaderProgram shaderProgramFBO;
    private ResourcesLocation vertexShaderFBO;
    private ResourcesLocation fragmentShaderFBO;

    private Matrix4f projectionMatrix;
    private Matrix4f modelMatrix;
    private Matrix4f viewMatrix;

    private boolean uesStencil;
    private GLObject glObject;

    /**
     * Constructs a new FrameBuffer with the specified resolution.
     *
     * @param resolution the resolution of the frame buffer
     */
    public FrameBuffer(Vector2f resolution) {
        this(resolution, false, null);
    }

    /**
     * Constructs a new FrameBuffer with the specified resolution, stencil usage, and GL object.
     *
     * @param resolution the resolution of the frame buffer
     * @param uesStencil whether to use stencil buffer
     * @param glObject the GL object associated with the frame buffer
     */
    public FrameBuffer(Vector2f resolution, boolean uesStencil, GLObject glObject) {
        this.resolution = resolution;

        frameBufferId = GL30.glGenFramebuffers();
        if (frameBufferId == 0) {
            throw new CreatingFrameBufferException("Failed to create frame buffer");
        }

        textureId = GL30.glGenTextures();
        if (textureId == 0) {
            throw new CreatingTextureException("Failed to create texture");
        }

        renderBufferId = GL30.glGenRenderbuffers();
        if (renderBufferId == 0) {
            throw new CreatingRenderBufferException("Failed to create render buffer");
        }

        shaderProgramFBO = new ShaderProgram();
        vertexShaderFBO = new ResourcesLocation("shader/frameBuffer/VertexShader.vsh");
        fragmentShaderFBO = new ResourcesLocation("shader/frameBuffer/FragmentShader.fsh");

        vaoRendering = new VAORendering();

        float[] positions = new float[]{0, 0, resolution.x(), 0, resolution.x(), resolution.y(), 0, resolution.y()};

        float[] uvs = new float[]{0, 0, 1, 0, 1, 1, 0, 1};

        mesh = new Mesh(ProjectionType.OrthographicProjection, positions, null, uvs);

        this.uesStencil = uesStencil;
        this.glObject = glObject;
    }

    /**
     * Creates the shader program for the frame buffer.
     */
    public void createShaderProgram() {
        shaderProgramFBO.createVertexShader(vertexShaderFBO);
        shaderProgramFBO.createFragmentShader(fragmentShaderFBO);
        shaderProgramFBO.link();

        Projection projection = new Projection(ProjectionType.OrthographicProjection, (int) resolution.x(), (int) resolution.y());
        projectionMatrix = projection.getProjMatrix();
        modelMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
    }

    /**
     * Cleans up the frame buffer by deleting the frame buffer and texture.
     */
    public void cleanup() {
        GL30.glDeleteFramebuffers(frameBufferId);
        GL30.glDeleteTextures(textureId);
    }

    /**
     * Creates the frame buffer and its associated texture and render buffer.
     */
    public void createFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferId);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderBufferId);

        GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGBA, (int) resolution.x(), (int) resolution.y(), 0, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, (ByteBuffer) null);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);

        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D, textureId, 0);

        if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
            throw new CompleteFrameBufferException("Frame Buffer not complete");
        }

        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH32F_STENCIL8, (int) resolution.x(), (int) resolution.y());
        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL30.GL_RENDERBUFFER, renderBufferId);

        if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
            throw new RuntimeException("Frame Buffer not complete");
        }

        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
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
        GL30.glPushMatrix();
        shaderProgramFBO.bind();

        shaderProgramFBO.setUniform("projectionMatrix", projectionMatrix);
        shaderProgramFBO.setUniform("modelMatrix", modelMatrix);
        shaderProgramFBO.setUniform("viewMatrix", viewMatrix);
        shaderProgramFBO.setUniform1i("textureSampler", textureUnit);

        GLUtil glUtil = new GLUtil();
        glUtil.addGLTarget(GL30.GL_DEPTH_TEST, true);
        glUtil.addGLTarget(GL30.GL_BLEND);
        if (uesStencil) {
            glUtil.addGLTarget(GL30.GL_STENCIL_TEST);
        }

        glUtil.enableTargets();

        GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        bindTexture(textureUnit);

        if (uesStencil) {
            GL30.glClear(GL30.GL_STENCIL_BUFFER_BIT);
            GL30.glStencilFunc(GL30.GL_ALWAYS, 1, 0xff);
            GL30.glStencilOp(GL30.GL_KEEP, GL30.GL_KEEP, GL30.GL_REPLACE);
            GL30.glColorMask(false, false, false, false);
            vaoRendering.draw(glObject.getMesh());
            GL30.glColorMask(true, true, true, true);

            GL30.glStencilFunc(GL30.GL_EQUAL, 1, 0xff);
            GL30.glStencilOp(GL30.GL_KEEP, GL30.GL_KEEP, GL30.GL_KEEP);
        }

        vaoRendering.draw(mesh);

        unbindTexture(textureUnit);

        glUtil.disableTargets();
        glUtil.finish();

        shaderProgramFBO.unbind();
        GL30.glPopMatrix();
    }

    /**
     * Binds the render buffer.
     */
    public void bindRenderBuffer() {
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderBufferId);
    }

    /**
     * Unbinds the render buffer.
     */
    public void unbindRenderBuffer() {
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);
    }

    /**
     * Binds the frame buffer for drawing.
     */
    public void bindDrawFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, frameBufferId);
    }

    /**
     * Unbinds the frame buffer from drawing.
     */
    public void unbindDrawFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
    }

    /**
     * Binds the frame buffer for reading.
     */
    public void bindReadFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, frameBufferId);
    }

    /**
     * Unbinds the frame buffer from reading.
     */
    public void unBindReadFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, 0);
    }

    /**
     * Binds the frame buffer.
     */
    public void bindFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferId);
        GL30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT | GL30.GL_STENCIL_BUFFER_BIT);
    }

    /**
     * Unbinds the frame buffer.
     */
    public void unbindFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    /**
     * Binds the texture to the specified texture unit.
     *
     * @param textureUnit the texture unit to bind to
     */
    public void bindTexture(int textureUnit) {
        GL30.glActiveTexture(GL30.GL_TEXTURE0 + textureUnit);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
    }

    /**
     * Unbinds the texture from the specified texture unit.
     *
     * @param textureUnit the texture unit to unbind from
     */
    public void unbindTexture(int textureUnit) {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }

    /**
     * Saves the frame buffer to an image file.
     *
     * @param filePath the file path to save the image to
     */
    public void saveFrameBufferToImage(ResourcesLocation filePath) {
        int width = (int) resolution.x();
        int height = (int) resolution.y();

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        bindFrameBuffer();
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        unbindFrameBuffer();

        ByteBuffer flippedBuffer = flipVertically(buffer, width, height, 4);

        if (!STBImageWrite.stbi_write_png(filePath.getPath(), width, height, 4, flippedBuffer, width * 4)) {
            throw new RuntimeException("Failed to save frame buffer to image: " + filePath.getPath());
        }

        DebugLog.debug(getClass(), "Saved frame buffer to image: " + filePath.getPath());
    }

    /**
     * Flips the given buffer vertically.
     *
     * @param buffer the buffer to flip
     * @param width the width of the buffer
     * @param height the height of the buffer
     * @param channels the number of channels in the buffer
     * @return the flipped buffer
     */
    private ByteBuffer flipVertically(ByteBuffer buffer, int width, int height, int channels) {
        ByteBuffer flipped = BufferUtils.createByteBuffer(buffer.capacity());
        int stride = width * channels;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < stride; x++) {
                flipped.put((height - y - 1) * stride + x, buffer.get(y * stride + x));
            }
        }
        return flipped;
    }

    /**
     * Updates the resolution of the frame buffer.
     *
     * @param resolution the new resolution
     */
    public void updateResolution(Vector2f resolution) {
        this.resolution = resolution;
    }

    /**
     * Gets the frame buffer ID.
     *
     * @return the frame buffer ID
     */
    public int getFrameBufferId() {
        return frameBufferId;
    }

    /**
     * Gets the texture ID.
     *
     * @return the texture ID
     */
    public int getTextureId() {
        return textureId;
    }

    /**
     * Gets the projection matrix.
     *
     * @return the projection matrix
     */
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    /**
     * Sets the projection matrix.
     *
     * @param projectionMatrix the new projection matrix
     */
    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    /**
     * Gets the fragment shader resource location.
     *
     * @return the fragment shader resource location
     */
    public ResourcesLocation getFragmentShaderFBO() {
        return fragmentShaderFBO;
    }

    /**
     * Sets the fragment shader resource location.
     *
     * @param fragmentShaderFBO the new fragment shader resource location
     */
    public void setFragmentShaderFBO(ResourcesLocation fragmentShaderFBO) {
        this.fragmentShaderFBO = fragmentShaderFBO;
    }

    /**
     * Gets the vertex shader resource location.
     *
     * @return the vertex shader resource location
     */
    public ResourcesLocation getVertexShaderFBO() {
        return vertexShaderFBO;
    }

    /**
     * Sets the vertex shader resource location.
     *
     * @param vertexShaderFBO the new vertex shader resource location
     */
    public void setVertexShaderFBO(ResourcesLocation vertexShaderFBO) {
        this.vertexShaderFBO = vertexShaderFBO;
    }

    /**
     * Gets the shader program for the frame buffer.
     *
     * @return the shader program for the frame buffer
     */
    public ShaderProgram getShaderProgramFBO() {
        return shaderProgramFBO;
    }

    /**
     * Sets the shader program for the frame buffer.
     *
     * @param shaderProgramFBO the new shader program for the frame buffer
     */
    public void setShaderProgramFBO(ShaderProgram shaderProgramFBO) {
        this.shaderProgramFBO = shaderProgramFBO;
    }

    /**
     * Gets the VAO rendering object.
     *
     * @return the VAO rendering object
     */
    public VAORendering getVaoRendering() {
        return vaoRendering;
    }

    /**
     * Gets the mesh associated with the frame buffer.
     *
     * @return the mesh associated with the frame buffer
     */
    public Mesh getMesh() {
        return mesh;
    }

    /**
     * Gets the resolution of the frame buffer.
     *
     * @return the resolution of the frame buffer
     */
    public Vector2f getResolution() {
        return resolution;
    }

    /**
     * Sets the resolution of the frame buffer.
     *
     * @param resolution the new resolution
     */
    public void setResolution(Vector2f resolution) {
        this.resolution = resolution;
    }

    /**
     * Gets the render buffer ID.
     *
     * @return the render buffer ID
     */
    public int getRenderBufferId() {
        return renderBufferId;
    }

    /**
     * Gets the model matrix.
     *
     * @return the model matrix
     */
    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    /**
     * Sets the model matrix.
     *
     * @param modelMatrix the new model matrix
     */
    public void setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    /**
     * Gets the view matrix.
     *
     * @return the view matrix
     */
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    /**
     * Sets the view matrix.
     *
     * @param viewMatrix the new view matrix
     */
    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    /**
     * Checks if stencil buffer is used.
     *
     * @return true if stencil buffer is used, false otherwise
     */
    public boolean isUesStencil() {
        return uesStencil;
    }

    /**
     * Sets whether to use stencil buffer.
     *
     * @param uesStencil true to use stencil buffer, false otherwise
     */
    public void setUesStencil(boolean uesStencil) {
        this.uesStencil = uesStencil;
    }

    /**
     * Gets the GL object associated with the frame buffer.
     *
     * @return the GL object associated with the frame buffer
     */
    public GLObject getGlObject() {
        return glObject;
    }

    /**
     * Sets the GL object associated with the frame buffer.
     *
     * @param glObject the new GL object associated with the frame buffer
     */
    public void setGlObject(GLObject glObject) {
        this.glObject = glObject;
    }
}