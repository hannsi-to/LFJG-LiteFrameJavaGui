package me.hannsi.lfjg.testRender.system.shader;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.math.map.string2objectMap.String2ObjectMap;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.testRender.debug.exceptions.shader.CompilingShaderException;
import me.hannsi.lfjg.testRender.debug.exceptions.shader.CreatingShaderException;
import me.hannsi.lfjg.testRender.debug.exceptions.shader.CreatingShaderProgramException;
import me.hannsi.lfjg.testRender.debug.exceptions.shader.LinkingShaderException;
import org.joml.*;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static me.hannsi.lfjg.core.CoreSystemSetting.DEBUG_RUN;
import static me.hannsi.lfjg.testRender.LFJGRenderContext.glStateCache;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    private final int programId;
    private final String2ObjectMap<UniformValue> uniformValues;
    private final String2ObjectMap<Integer> uniformCache;
    private final Map<Integer, Integer> shaderIds;

    public ShaderProgram() {
        uniformCache = new String2ObjectMap<>();
        uniformValues = new String2ObjectMap<>();
        shaderIds = new HashMap<>();

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

    private static int[] toIntArray(Object[] values) {
        int[] result = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            Object v = values[i];
            if (v instanceof Number) {
                result[i] = ((Number) v).intValue();
            } else {
                throw new IllegalArgumentException("Value is not a number: " + v);
            }
        }
        return result;
    }

    public void cleanup() {
        uniformValues.clear();
        uniformCache.clear();

        for (int shaderId : shaderIds.values()) {
            glDeleteShader(shaderId);
        }
        shaderIds.clear();

        if (programId != 0) {
            glStateCache.deleteProgram(programId);
        }

//        new LogGenerator(
//                LogGenerateType.CLEANUP,
//                getClass(),
//                "ProgramId: " + programId + " | VertexShaderId: " + vertexShaderId + " | FragmentShaderId: " + fragmentShaderId,
//                ""
//        ).logging(getClass(), DebugLevel.DEBUG);
    }

    public void createVertexShader(Location fileLocation) {
        createShader(new GLSLCode(fileLocation).createCode(), ShaderType.VERTEX_SHADER);
    }

    public void createFragmentShader(Location fileLocation) {
        createShader(new GLSLCode(fileLocation).createCode(), ShaderType.FRAGMENT_SHADER);
    }

    public void createGeometryShader(Location fileLocation) {
        createShader(new GLSLCode(fileLocation).createCode(), ShaderType.GEOMETRY_SHADER);
    }

    public void createComputeShader(Location fileLocation) {
        createShader(new GLSLCode(fileLocation).createCode(), ShaderType.COMPUTE_SHADER);
    }

    protected void createShader(String shaderCode, ShaderType shaderType) {
        if (shaderType == ShaderType.COMPUTE_SHADER && !shaderIds.isEmpty()) {
            throw new IllegalStateException("Compute shader must be standalone");
        }

        if (!shaderIds.isEmpty() && shaderIds.containsKey(ShaderType.COMPUTE_SHADER.getId())) {
            throw new IllegalStateException("Cannot mix compute shader with others");
        }

        if (shaderIds.containsKey(shaderType.getId())) {
            throw new IllegalStateException("Shader already exists: " + shaderType.getName());
        }

        int shaderId = glCreateShader(shaderType.getId());
        if (shaderId == 0) {
            throw new CreatingShaderException("Error creating shader. Type: " + shaderType.getName());
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new CompilingShaderException("Error compiling Shader code: " + glGetShaderInfoLog(shaderId));
        }

        glAttachShader(programId, shaderId);

        shaderIds.put(shaderType.getId(), shaderId);
    }

    public void link() {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new LinkingShaderException("Error linking Shader code: " + glGetProgramInfoLog(programId));
        }

        for (int shaderId : shaderIds.values()) {
            glDetachShader(programId, shaderId);
        }

        if (DEBUG_RUN) {
            glValidateProgram(programId);
            if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
                DebugLog.warning(getClass(), "Warning validating Shader code: " + glGetProgramInfoLog(programId));
            }
        }
    }

    private int getUniformLocation(String name) {
        Integer location = uniformCache.get(name);
        if (location != null) {
            return location;
        }

        int loc = glGetUniformLocation(programId, name);
        uniformCache.put(name, loc);
        return loc;
    }

    public void bind() {
        glStateCache.useProgram(programId);
    }

    @SuppressWarnings("unchecked")
    public <T> void setUniform(String name, UploadUniformType updateType, T... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Uniform values cannot be empty");
        }

        UniformValue existing = uniformValues.get(name);

        if (existing != null && updateType != UploadUniformType.PER_FRAME && (updateType != UploadUniformType.ON_CHANGE || existing.equalsValues((Object[]) values))) {
            return;
        }

        uniformValues.put(name, new UniformValue(updateType, (Object[]) values));

        int location = getUniformLocation(name);
        if (location == -1) {
            return;
        }

        Object first = values[0];
        switch (first) {
            case Boolean ignored -> {
                int booleanValue = ((boolean) first) ? 1 : 0;
                glUniform1i(location, booleanValue);
            }
            case Float ignored -> {
                float[] floatValues = toFloatArray(values);
                switch (floatValues.length) {
                    case 1:
                        glUniform1f(location, floatValues[0]);
                        break;
                    case 2:
                        glUniform2f(location, floatValues[0], floatValues[1]);
                        break;
                    case 3:
                        glUniform3f(location, floatValues[0], floatValues[1], floatValues[2]);
                        break;
                    case 4:
                        glUniform4f(location, floatValues[0], floatValues[1], floatValues[2], floatValues[3]);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported float uniform size: " + floatValues.length);
                }
            }
            case Integer ignored -> {
                int[] intValues = toIntArray(values);
                switch (intValues.length) {
                    case 1:
                        glUniform1i(location, intValues[0]);
                        break;
                    case 2:
                        glUniform2i(location, intValues[0], intValues[1]);
                        break;
                    case 3:
                        glUniform3i(location, intValues[0], intValues[1], intValues[2]);
                        break;
                    case 4:
                        glUniform4i(location, intValues[0], intValues[1], intValues[2], intValues[3]);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported int uniform size: " + intValues.length);
                }
            }
            case FloatBuffer floatBuffer -> {
                int size = floatBuffer.remaining();
                switch (size) {
                    case 1:
                        glUniform1fv(location, floatBuffer);
                        break;
                    case 2:
                        glUniform2fv(location, floatBuffer);
                        break;
                    case 3:
                        glUniform3fv(location, floatBuffer);
                        break;
                    case 4:
                        glUniform4fv(location, floatBuffer);
                        break;
                    case 16:
                        glUniformMatrix4fv(location, false, floatBuffer);
                        break;
                    default:
                        if (size > 4 && size <= 256) {
                            glUniform1fv(location, floatBuffer);
                            break;
                        }
                        throw new IllegalArgumentException("Unsupported FloatBuffer size: " + floatBuffer.remaining());
                }
            }
            case Matrix4f mat -> {
                try (MemoryStack stack = MemoryStack.stackPush()) {
                    glUniformMatrix4fv(location, false, mat.get(stack.mallocFloat(16)));
                }
            }
            case Vector2f vec ->
                    glUniform2f(location, vec.x, vec.y);
            case Vector3f vec ->
                    glUniform3f(location, vec.x, vec.y, vec.z);
            case Vector4f vec ->
                    glUniform4f(location, vec.x, vec.y, vec.z, vec.w);
            case Vector2i vec ->
                    glUniform2i(location, vec.x, vec.y);
            case Vector3i vec ->
                    glUniform3i(location, vec.x, vec.y, vec.z);
            case Vector4i vec ->
                    glUniform4i(location, vec.x, vec.y, vec.z, vec.w);
            case Color color ->
                    glUniform4f(location, color.getRedF(), color.getGreenF(), color.getBlueF(), color.getAlphaF());
            case null, default ->
                    throw new IllegalArgumentException("Unsupported uniform value type: " + Objects.requireNonNull(first).getClass());
        }
    }

    public int getProgramId() {
        return programId;
    }
}