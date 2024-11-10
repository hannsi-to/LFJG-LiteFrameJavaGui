package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.effect.shader.ShaderProgram;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.render.openGL.system.bufferObject.VAO;
import me.hannsi.lfjg.utils.graphics.DisplayUtil;
import me.hannsi.lfjg.utils.graphics.GLUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;

public class VAORendering {
    private final GLUtil glUtil;
    private Frame frame;
    private ShaderProgram shaderProgram;
    private VAO vao;

    public VAORendering(Frame frame) {
        this.frame = frame;
        this.glUtil = new GLUtil();
    }

    public void shaderProgramInit(ResourcesLocation vertexShader, ResourcesLocation fragmentShader) {
        try {
            shaderProgram = new ShaderProgram();
            shaderProgram.createVertexShader(vertexShader);
            shaderProgram.createFragmentShader(fragmentShader);
            shaderProgram.link();
            shaderProgram.bind();
            shaderProgram.setUniform1i("screenWidth", DisplayUtil.getDisplayWidthI());
            shaderProgram.setUniform1i("screenHeight", DisplayUtil.getDisplayHeightI());
            shaderProgram.unbind();

        } catch (Exception e) {
            DebugLog.error(getClass(), e);
        }
    }

    public void init(ResourcesLocation vertexShader, ResourcesLocation fragmentShader) {
        shaderProgramInit(vertexShader, fragmentShader);
    }

    public void cleanUp() {
        shaderProgram.cleanup();
        vao.disableVertexAttribArray();
        vao.clearAttributes();
        vao.deleteBuffers();
        vao.unBindVertexArray();
        vao.deleteVertexArrays();
    }

    public void draw(GLPolygon glPolygon, Matrix4f modelMatrix) {
        shaderProgram.bind();
        shaderProgram.setUniformMatrix4fv("uModelMatrix", modelMatrix);

        vao.bindVertexArray();

        vao.enableVertexAttribArrays();

        glUtil.addGLTarget(GL30.GL_BLEND);
        glUtil.enableTargets();
        GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        GL30.glDrawArrays(GL30.GL_POLYGON, 0, 4);

        glUtil.disableTargets();

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