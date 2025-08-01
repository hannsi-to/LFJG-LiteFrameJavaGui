package me.hannsi.lfjg.render.system.shader;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.debug.exceptions.shader.CompilingShaderException;
import me.hannsi.lfjg.render.debug.exceptions.shader.CreatingShaderException;
import me.hannsi.lfjg.render.debug.exceptions.shader.CreatingShaderProgramException;
import me.hannsi.lfjg.render.debug.exceptions.shader.LinkingShaderException;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.joml.*;
import org.lwjgl.system.MemoryStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

/**
 * Represents a shader program in the OpenGL rendering system.
 */
public class ShaderProgram {
    private final Map<String, Integer> uniformCache = new HashMap<>();
    private final Map<String, UniformValue> uniformValues = new HashMap<>();
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

    private static float[] toFloatArray(Object[] values) {
        float[] result = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            Object v = values[i];
            if (v instanceof Number) {
                result[i] = ((Number) v).floatValue();
            } else {
                throw new IllegalArgumentException("Value is not a number: " + v);
            }
        }
        return result;
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

    @SuppressWarnings("unchecked")
    public <T> void setUniform(String name, UploadUniformType updateType, T... values) {
        UniformValue existing = uniformValues.get(name);

        if (existing != null && updateType != UploadUniformType.PER_FRAME && (updateType != UploadUniformType.ON_CHANGE || existing.equalsValues((Object[]) values))) {
            return;
        }

        uniformValues.put(name, new UniformValue(updateType, (Object[]) values));

        int location = getUniformLocation(name);

        Object first = values[0];
        if (first instanceof Float) {
            float[] floatValues = toFloatArray(values);
            switch (floatValues.length) {
                case 1 -> glUniform1f(location, floatValues[0]);
                case 2 -> glUniform2f(location, floatValues[0], floatValues[1]);
                case 3 -> glUniform3f(location, floatValues[0], floatValues[1], floatValues[2]);
                case 4 -> glUniform4f(location, floatValues[0], floatValues[1], floatValues[2], floatValues[3]);
                default -> throw new IllegalArgumentException("Unsupported float uniform size: " + floatValues.length);
            }
        } else if (first instanceof Integer) {
            int[] intValues = Arrays.stream(values).mapToInt(v -> (Integer) v).toArray();
            switch (intValues.length) {
                case 1 -> glUniform1i(location, intValues[0]);
                case 2 -> glUniform2i(location, intValues[0], intValues[1]);
                case 3 -> glUniform3i(location, intValues[0], intValues[1], intValues[2]);
                case 4 -> glUniform4i(location, intValues[0], intValues[1], intValues[2], intValues[3]);
                default -> throw new IllegalArgumentException("Unsupported int uniform size: " + intValues.length);
            }
        } else if (first instanceof Matrix4f mat) {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                glUniformMatrix4fv(location, false, mat.get(stack.mallocFloat(16)));
            }
        } else if (first instanceof Vector2f vec) {
            glUniform2f(location, vec.x, vec.y);
        } else if (first instanceof Vector3f vec) {
            glUniform3f(location, vec.x, vec.y, vec.z);
        } else if (first instanceof Vector4f vec) {
            glUniform4f(location, vec.x, vec.y, vec.z, vec.w);
        } else if (first instanceof Vector2i vec) {
            glUniform2i(location, vec.x, vec.y);
        } else if (first instanceof Vector3i vec) {
            glUniform3i(location, vec.x, vec.y, vec.z);
        } else if (first instanceof Vector4i vec) {
            glUniform4i(location, vec.x, vec.y, vec.z, vec.w);
        } else {
            throw new IllegalArgumentException("Unsupported uniform value type: " + first.getClass());
        }
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