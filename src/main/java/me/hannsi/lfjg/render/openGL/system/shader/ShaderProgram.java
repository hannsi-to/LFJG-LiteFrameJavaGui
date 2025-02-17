package me.hannsi.lfjg.render.openGL.system.shader;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.exceptions.shader.CompilingShaderException;
import me.hannsi.lfjg.debug.exceptions.shader.CreatingShaderException;
import me.hannsi.lfjg.debug.exceptions.shader.CreatingShaderProgramException;
import me.hannsi.lfjg.debug.exceptions.shader.LinkingShaderException;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

/**
 * Represents a shader program in the OpenGL rendering system.
 */
public class ShaderProgram {

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
    }

    /**
     * Creates a vertex shader from the specified resource location.
     *
     * @param resourcesLocation the location of the vertex shader resource
     */
    public void createVertexShader(ResourcesLocation resourcesLocation) {
        GLSLCode glslCode = new GLSLCode(resourcesLocation);
        String shaderCode = glslCode.createCode();

        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    /**
     * Creates a fragment shader from the specified resource location.
     *
     * @param resourcesLocation the location of the fragment shader resource
     */
    public void createFragmentShader(ResourcesLocation resourcesLocation) {
        GLSLCode glslCode = new GLSLCode(resourcesLocation);
        String shaderCode = glslCode.createCode();

        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
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
            throw new CompilingShaderException("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
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

    /**
     * Sets a boolean uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the boolean value to set
     */
    public void setUniform(String name, boolean value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform1i(uniformId, value ? 1 : 0);
    }

    /**
     * Sets a Matrix4f uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the Matrix4f value to set
     */
    public void setUniform(String name, Matrix4f value) {
        int uniformId = glGetUniformLocation(programId, name);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(uniformId, false, value.get(stack.mallocFloat(16)));
        }
    }

    /**
     * Sets a FloatBuffer uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the FloatBuffer value to set
     */
    public void setUniform(String name, FloatBuffer value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform1fv(uniformId, value);
    }

    /**
     * Sets a float uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the float value to set
     */
    public void setUniform(String name, float value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform1f(uniformId, value);
    }

    /**
     * Sets a Vector2f uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the Vector2f value to set
     */
    public void setUniform(String name, Vector2f value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform2f(uniformId, value.x, value.y);
    }

    /**
     * Sets a 2-component float uniform in the shader program.
     *
     * @param name   the name of the uniform
     * @param value1 the first float value
     * @param value2 the second float value
     */
    public void setUniform(String name, float value1, float value2) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform2f(uniformId, value1, value2);
    }

    /**
     * Sets a Vector3f uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the Vector3f value to set
     */
    public void setUniform(String name, Vector3f value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform3f(uniformId, value.x, value.y, value.z);
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
        int uniformId = glGetUniformLocation(programId, name);

        glUniform3f(uniformId, value1, value2, value3);
    }

    /**
     * Sets an integer uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the integer value to set
     */
    public void setUniform1i(String name, int value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform1i(uniformId, value);
    }

    /**
     * Sets a Vector4f uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param value the Vector4f value to set
     */
    public void setUniform(String name, Vector4f value) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform4f(uniformId, value.x, value.y, value.z, value.w);
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
        int uniformId = glGetUniformLocation(programId, name);

        glUniform4f(uniformId, value1, value2, value3, value4);
    }

    /**
     * Sets a Color uniform in the shader program.
     *
     * @param name  the name of the uniform
     * @param color the Color value to set
     */
    public void setUniform(String name, Color color) {
        int uniformId = glGetUniformLocation(programId, name);

        glUniform4f(uniformId, color.getRedF(), color.getGreenF(), color.getBlueF(), color.getAlphaF());
    }

    /**
     * Binds the shader program for use.
     */
    public void bind() {
        glUseProgram(programId);
        if (glGetError() != GL_NO_ERROR) {
            DebugLog.warning(getClass(), "Failed to set the program as active.");
        }
    }

    /**
     * Unbinds the shader program.
     */
    public void unbind() {
        glUseProgram(0);
    }

    /**
     * Gets the program ID.
     *
     * @return the program ID
     */
    public int getProgramId() {
        return programId;
    }

    /**
     * Gets the vertex shader ID.
     *
     * @return the vertex shader ID
     */
    public int getVertexShaderId() {
        return vertexShaderId;
    }

    /**
     * Sets the vertex shader ID.
     *
     * @param vertexShaderId the new vertex shader ID
     */
    public void setVertexShaderId(int vertexShaderId) {
        this.vertexShaderId = vertexShaderId;
    }

    /**
     * Gets the fragment shader ID.
     *
     * @return the fragment shader ID
     */
    public int getFragmentShaderId() {
        return fragmentShaderId;
    }

    /**
     * Sets the fragment shader ID.
     *
     * @param fragmentShaderId the new fragment shader ID
     */
    public void setFragmentShaderId(int fragmentShaderId) {
        this.fragmentShaderId = fragmentShaderId;
    }
}