package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.effect.shader.ShaderProgram;
import me.hannsi.lfjg.render.openGL.system.bufferObject.VAO;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.lwjgl.opengl.GL30;

public class VAORendering {
    private Frame frame;
    private ShaderProgram shaderProgram;
    private VAO vao;

    public VAORendering(Frame frame) {
        this.frame = frame;
    }

    public void shaderProgramInit() {
        try {
            shaderProgram = new ShaderProgram();
            shaderProgram.createVertexShader(new ResourcesLocation("shader/vertexShader.vsh"));
            shaderProgram.createFragmentShader(new ResourcesLocation("shader/FragmentShader.fsh"));
            shaderProgram.link();
            shaderProgram.bind();
            shaderProgram.setUniform1i("screenWidth", 1920);
            shaderProgram.setUniform1i("screenHeight", 1080);
            shaderProgram.unbind();
        } catch (Exception e) {
            DebugLog.debug(getClass(), e);
        }
    }

    public void init() {
        shaderProgramInit();
    }

    public void cleanUp() {
        shaderProgram.cleanup();
        vao.disableVertexAttribArray();
        vao.clearAttributes();
        vao.deleteBuffers();
        vao.unBindVertexArray();
        vao.deleteVertexArrays();
    }

    public void draw() {
        shaderProgram.bind();
        vao.bindVertexArray();

        vao.enableVertexAttribArrays();

        GL30.glDrawArrays(GL30.GL_POLYGON, 0, 4);

        vao.disableVertexAttribArray();

        vao.unBindVertexArray();
        shaderProgram.unbind();
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public void setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public VAO getVao() {
        return vao;
    }

    public void setVao(VAO vao) {
        this.vao = vao;
    }
}