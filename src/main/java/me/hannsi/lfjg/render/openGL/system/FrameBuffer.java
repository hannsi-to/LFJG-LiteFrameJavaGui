package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.debug.exceptions.frameBuffer.CompleteFrameBufferException;
import me.hannsi.lfjg.debug.exceptions.frameBuffer.CreatingFrameBufferException;
import me.hannsi.lfjg.debug.exceptions.render.CreatingRenderBufferException;
import me.hannsi.lfjg.debug.exceptions.texture.CreatingTextureException;
import me.hannsi.lfjg.render.openGL.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;

public class FrameBuffer {
    private final int frameBufferId;
    private final int textureId;
    private final int renderBufferId;

    private final Vector2f resolution;

    private final Mesh mesh;
    private final VAORendering vaoRendering;

    private ShaderProgram shaderProgramFBO;
    private ResourcesLocation vertexShaderFBO;
    private ResourcesLocation fragmentShaderFBO;

    private Matrix4f projectionMatrix;
    private Matrix4f modelMatrix;
    private Matrix4f viewMatrix;

    public FrameBuffer(Vector2f resolution) {
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
        if(renderBufferId == 0){
            throw new CreatingRenderBufferException("Failed to create render buffer");
        }

        shaderProgramFBO = new ShaderProgram();
        vertexShaderFBO = new ResourcesLocation("shader/frameBuffer/vertexShader.vsh");
        fragmentShaderFBO = new ResourcesLocation("shader/frameBuffer/FragmentShader.fsh");

        vaoRendering = new VAORendering();

        float[] positions = new float[]{
                0,0,
                resolution.x(),0,
                resolution.x(),resolution.y(),
                0,resolution.y()
        };

        float[] uvs = new float[]{0, 1, 1, 1, 1, 0, 0, 0};

        mesh = new Mesh(ProjectionType.OrthographicProjection, positions, null, uvs);
    }

    public void createShaderProgram(){
        shaderProgramFBO.createVertexShader(vertexShaderFBO);
        shaderProgramFBO.createFragmentShader(fragmentShaderFBO);
        shaderProgramFBO.link();

        Projection projection = new Projection(ProjectionType.OrthographicProjection, (int) resolution.x(), (int) resolution.y());
        projectionMatrix = projection.getProjMatrix();
        modelMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
    }

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

        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH24_STENCIL8, (int) resolution.x(), (int) resolution.y());
        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL30.GL_RENDERBUFFER, renderBufferId);

        if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
            throw new RuntimeException("Frame Buffer not complete");
        }

        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER,0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    public void drawFrameBuffer(){
        drawFrameBuffer(0);
    }

    public void drawFrameBuffer(int textureUnit){
        GL30.glPushMatrix();
        shaderProgramFBO.bind();

        shaderProgramFBO.setUniformMatrix4fv("projectionMatrix", projectionMatrix);
        shaderProgramFBO.setUniformMatrix4fv("modelMatrix", modelMatrix);
        shaderProgramFBO.setUniformMatrix4fv("viewMatrix", viewMatrix);
        shaderProgramFBO.setUniform1i("textureSampler", textureUnit);

        bindTexture(textureUnit);
        vaoRendering.draw(mesh);
        unbindTexture(textureUnit);

        shaderProgramFBO.unbind();
        GL30.glPopMatrix();
    }

    public void bindRenderBuffer(){
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderBufferId);
    }

    public void unbindRenderBuffer(){
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER,0);
    }

    public void bindDrawFrameBuffer(){
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, frameBufferId);
    }

    public void unBindDrawFrameBuffer(){
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
    }

    public void bindReadFrameBuffer(){
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, frameBufferId);
    }

    public void unBindReadFrameBuffer(){
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, 0);
    }

    public void bindFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferId);
    }

    public void unbindFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    public void bindTexture(int textureUnit) {
        GL30.glActiveTexture(GL30.GL_TEXTURE0 + textureUnit);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
    }

    public void unbindTexture(int textureUnit) {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
    }

    public int getFrameBufferId() {
        return frameBufferId;
    }

    public int getTextureId() {
        return textureId;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public ResourcesLocation getFragmentShaderFBO() {
        return fragmentShaderFBO;
    }

    public ResourcesLocation getVertexShaderFBO() {
        return vertexShaderFBO;
    }

    public ShaderProgram getShaderProgramFBO() {
        return shaderProgramFBO;
    }

    public VAORendering getVaoRendering() {
        return vaoRendering;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public int getRenderBufferId() {
        return renderBufferId;
    }

    public void setShaderProgramFBO(ShaderProgram shaderProgramFBO) {

        this.shaderProgramFBO = shaderProgramFBO;
    }

    public void setVertexShaderFBO(ResourcesLocation vertexShaderFBO) {
        this.vertexShaderFBO = vertexShaderFBO;
    }

    public void setFragmentShaderFBO(ResourcesLocation fragmentShaderFBO) {
        this.fragmentShaderFBO = fragmentShaderFBO;
    }
}
