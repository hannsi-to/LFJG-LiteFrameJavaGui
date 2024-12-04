package me.hannsi.lfjg.render.openGL.shader;

import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.debug.exceptions.CompilingShaderException;
import me.hannsi.lfjg.debug.exceptions.CreatingShaderException;
import me.hannsi.lfjg.debug.exceptions.CreatingShaderProgramException;
import me.hannsi.lfjg.debug.exceptions.LinkingShaderException;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private final int programId;

    private int vertexShaderId;

    private int fragmentShaderId;

    public ShaderProgram() {
        programId = glCreateProgram();
        if (programId == 0) {
            throw new CreatingShaderProgramException("Could not create Shader");
        }
    }

    public void createVertexShader(ResourcesLocation resourcesLocation) {
        GLSLCode glslCode = new GLSLCode(resourcesLocation);
        String shaderCode = glslCode.createCode();

        DebugLog.debug(getClass(), resourcesLocation.getPath() + "\n" + shaderCode + "\n");

        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(ResourcesLocation resourcesLocation) {
        GLSLCode glslCode = new GLSLCode(resourcesLocation);
        String shaderCode = glslCode.createCode();

        DebugLog.debug(getClass(), resourcesLocation.getPath() + "\n" + shaderCode + "\n");

        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    protected int createShader(String shaderCode, int shaderType) {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new CreatingShaderException("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new CompilingShaderException("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    public void link() {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new LinkingShaderException("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            DebugLog.warning(getClass(), "Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

    }

    public void setUniformBoolean(String name, boolean value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform1i(uniformId, value ? 1 : 0);
    }


    public void setUniformMatrix4fv(String name, Matrix4f value) {
        int uniformId = glGetUniformLocation(programId, name);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(uniformId, false, value.get(stack.mallocFloat(16)));
        }
    }

    public void setUniform1f(String name, float value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform1f(uniformId, value);
    }

    public void setUniform2f(String name, Vector2f value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform2f(uniformId, value.x, value.y);
    }

    public void setUniform1i(String name, int value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform1i(uniformId, value);
    }

    public void setUniform4f(String name, Vector4f value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform4f(uniformId, value.x, value.y, value.z, value.w);
    }

    public void bind() {
        glUseProgram(programId);
        if (glGetError() != GL_NO_ERROR) {
            DebugLog.warning(getClass(), "Failed to set the program as active.");
        }
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }

    public int getProgramId() {
        return programId;
    }

    public int getVertexShaderId() {
        return vertexShaderId;
    }

    public void setVertexShaderId(int vertexShaderId) {
        this.vertexShaderId = vertexShaderId;
    }

    public int getFragmentShaderId() {
        return fragmentShaderId;
    }

    public void setFragmentShaderId(int fragmentShaderId) {
        this.fragmentShaderId = fragmentShaderId;
    }
}