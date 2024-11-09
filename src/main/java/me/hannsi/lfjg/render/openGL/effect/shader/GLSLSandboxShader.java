package me.hannsi.lfjg.render.openGL.effect.shader;

import me.hannsi.lfjg.utils.graphics.DisplayUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.toolkit.ToolkitUtil;
import org.joml.Vector2f;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL20.*;

public class GLSLSandboxShader {
    private final int programId;

    public GLSLSandboxShader(ResourcesLocation rl) throws IOException {
        int program = glCreateProgram();

        glAttachShader(program, createShader((new ResourcesLocation("shader/vertexShader.vsh")).getInputStream(), GL_VERTEX_SHADER));
        glAttachShader(program, createShader((rl).getInputStream(), GL_FRAGMENT_SHADER));
        glLinkProgram(program);

        int linked = glGetProgrami(program, GL_LINK_STATUS);
        if (linked == 0) {
            System.err.println(glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH)));
            throw new IllegalStateException("Shader failed to link");
        }

        this.programId = program;
    }

    public void setUniformVector2f(String name, Vector2f value) {
        int uniformId = glGetUniformLocation(programId, name);

        float[] size = new float[2];
        size[0] = value.x;
        size[1] = value.y;

        glUniform2fv(uniformId, size);
    }

    public void setUniform1f(String name, float value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform1f(uniformId, value);
    }

    public void setUniform1i(String name, int value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform1i(uniformId, value);
    }

    public void useShader() {
        glUseProgram(this.programId);

        setUniform1i("screenWidth",1920);
        setUniform1i("screenHeight",1080);
    }

    public void finishShader() {
        glUseProgram(0);
    }

    private int createShader(InputStream inputStream, int shaderType) throws IOException {
        int shader = glCreateShader(shaderType);

        glShaderSource(shader, readStreamToString(inputStream));
        glCompileShader(shader);

        int compiled = glGetShaderi(shader, GL_COMPILE_STATUS);
        if (compiled == 0) {
            System.err.println(glGetShaderInfoLog(shader, glGetShaderi(shader, GL_INFO_LOG_LENGTH)));
            throw new IllegalStateException("Failed to compile shader");
        }

        return shader;
    }

    private String readStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[512];

        int read;
        while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, read);
        }

        return out.toString(StandardCharsets.UTF_8);
    }

    public int getProgramId() {
        return programId;
    }
}