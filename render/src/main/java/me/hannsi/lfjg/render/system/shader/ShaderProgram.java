package me.hannsi.lfjg.render.system.shader;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.debug.exceptions.shader.CompilingShaderException;
import me.hannsi.lfjg.render.debug.exceptions.shader.CreatingShaderException;
import me.hannsi.lfjg.render.debug.exceptions.shader.CreatingShaderProgramException;
import me.hannsi.lfjg.render.debug.exceptions.shader.LinkingShaderException;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.joml.*;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

/**
 * Represents a shader program in the OpenGL rendering system.
 */
public class ShaderProgram {
    private final Map<String, Integer> uniformCache = new HashMap<>();
    private final int programId;

    private int vertexShaderId;
    private int fragmentShaderId;

    /**
     * Constructs a new ShaderProgram.
     */
    public ShaderProgram() {
        programId = glCreateProgram();
        if (programId == 0) {
            throw new CreatingShaderProgramException("Could not create Shader");
        }
    }

    /**
     * Cleans up the shader program by deleting it.
     */
    public void cleanup() {
        unbind();
        if (vertexShaderId != 0) {
            glDeleteShader(vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDeleteShader(fragmentShaderId);
        }
        if (programId != 0) {
            glDeleteProgram(programId);
        }

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                "ProgramId: " + programId + " | VertexShaderId: " + vertexShaderId + " | FragmentShaderId: " + fragmentShaderId,
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public void createVertexShader(Location fileLocation) {
        vertexShaderId = createShader(new GLSLCode(fileLocation).createCode(), GL_VERTEX_SHADER);
    }

    public void createFragmentShader(Location fileLocation) {
        fragmentShaderId = createShader(new GLSLCode(fileLocation).createCode(), GL_FRAGMENT_SHADER);
    }

    /**
     * Creates a shader of the specified type from the given shader code.
     *
     * @param shaderCode the shader code
     * @param shaderType the type of shader (e.g., GL_VERTEX_SHADER)
     * @return the shader ID
     */
    protected int createShader(String shaderCode, int shaderType) {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new CreatingShaderException("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new CompilingShaderException("Error compiling Shader code: " + glGetShaderInfoLog(shaderId));
        }

        glAttachShader(programId, shaderId);
        return shaderId;
    }

    /**
     * Links the shader program.
     */
    public void link() {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new LinkingShaderException("Error linking Shader code: " + glGetProgramInfoLog(programId));
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            DebugLog.warning(getClass(), "Warning validating Shader code: " + glGetProgramInfoLog(programId));
        }
    }

    private int getUniformLocation(String name) {
        return uniformCache.computeIfAbsent(name, n -> glGetUniformLocation(programId, n));
    }

    /**
     * Sets a boolean uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the boolean value to set
     */
    public void setUniform(String name, boolean value) {
        glUniform1i(getUniformLocation(name), value ? 1 : 0);
    }

    /**
     * Sets a Matrix4f uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the Matrix4f value to set
     */
    public void setUniform(String name, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(getUniformLocation(name), false, value.get(stack.mallocFloat(16)));
        }
    }

    /**
     * Sets a FloatBuffer uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the FloatBuffer value to set
     */
    public void setUniform(String name, FloatBuffer value) {
        glUniform1fv(getUniformLocation(name), value);
    }

    /**
     * Sets a float uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the float value to set
     */
    public void setUniform(String name, float value) {
        glUniform1f(getUniformLocation(name), value);
    }

    /**
     * Sets a Vector2f uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the Vector2f value to set
     */
    public void setUniform(String name, Vector2f value) {
        glUniform2f(getUniformLocation(name), value.x, value.y);
    }

    /**
     * Sets a 2-component float uniform in the shader program.
     *
     * @param name   the name of the uniform
     * @param value1 the first float value
     * @param value2 the second float value
     */
    public void setUniform(String name, float value1, float value2) {
        glUniform2f(getUniformLocation(name), value1, value2);
    }

    /**
     * Sets a Vector3f uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the Vector3f value to set
     */
    public void setUniform(String name, Vector3f value) {
        glUniform3f(getUniformLocation(name), value.x, value.y, value.z);
    }

    /**
     * Sets a 3-component float uniform in the shader program.
     *
     * @param name   the name of the uniform
     * @param value1 the first float value
     * @param value2 the second float value
     * @param value3 the third float value
     */
    public void setUniform(String name, float value1, float value2, float value3) {
        glUniform3f(getUniformLocation(name), value1, value2, value3);
    }

    /**
     * Sets an integer uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the integer value to set
     */
    public void setUniform(String name, int value) {
        glUniform1i(getUniformLocation(name), value);
    }

    public void setUniform(String name, int value1, int value2) {
        glUniform2i(getUniformLocation(name), value1, value2);
    }

    public void setUniform(String name, int value1, int value2, int value3) {
        glUniform3i(getUniformLocation(name), value1, value2, value3);
    }

    public void setUniform(String name, int value1, int value2, int value3, int value4) {
        glUniform4i(getUniformLocation(name), value1, value2, value3, value4);
    }

    public void setUniform(String name, Vector2i value) {
        glUniform2i(getUniformLocation(name), value.x, value.y);
    }

    public void setUniform(String name, Vector3i value) {
        glUniform3i(getUniformLocation(name), value.x, value.y, value.z);
    }

    public void setUniform(String name, Vector4i value) {
        glUniform4i(getUniformLocation(name), value.x, value.y, value.z, value.w);
    }

    /**
     * Sets a Vector4f uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the Vector4f value to set
     */
    public void setUniform(String name, Vector4f value) {
        glUniform4f(getUniformLocation(name), value.x, value.y, value.z, value.w);
    }

    /**
     * Sets a 4-component float uniform in the shader program.
     *
     * @param name   the name of the uniform
     * @param value1 the first float value
     * @param value2 the second float value
     * @param value3 the third float value
     * @param value4 the fourth float value
     */
    public void setUniform(String name, float value1, float value2, float value3, float value4) {
        glUniform4f(getUniformLocation(name), value1, value2, value3, value4);
    }

    /**
     * Sets a Color uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param color the Color value to set
     */
    public void setUniform(String name, Color color) {
        glUniform4f(getUniformLocation(name), color.getRedF(), color.getGreenF(), color.getBlueF(), color.getAlphaF());
    }

    /**
     * Binds the shader program for use.
     */
    public void bind() {
        GLStateCache.useProgram(programId);
    }

    /**
     * Unbinds the shader program.
     */
    public void unbind() {
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