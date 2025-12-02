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
import me.hannsi.lfjg.render.system.mesh.MeshConstants;
import org.joml.*;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.GL_STATE_CACHE;
import static me.hannsi.lfjg.render.LFJGRenderContext.SHADER_PROGRAM;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindBufferRange;
import static org.lwjgl.opengl.GL30.glMapBufferRange;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL44.glBufferStorage;

public class ShaderProgram {
    private final int programId;
    private final Map<String, UniformValue> uniformValues;
    private final Map<String, Integer> uniformCache;
    private final Map<String, UniformBlockValue> matrixUniformBlocksValues;
    private final Map<String, Integer> matrixUniformBlocksCache;
    private final FloatBuffer matrixUniformBlockMappedBuffer;

    private int vertexShaderId;
    private int fragmentShaderId;

    public ShaderProgram() {
        uniformCache = new HashMap<>();
        uniformValues = new HashMap<>();
        matrixUniformBlocksCache = new HashMap<>();
        matrixUniformBlocksValues = new HashMap<>();

        programId = glCreateProgram();
        if (programId == 0) {
            throw new CreatingShaderProgramException("Could not create Shader");
        }

        int matrixUniformBlockId = glGenBuffers();
        GL_STATE_CACHE.bindUniformBuffer(matrixUniformBlockId);
        glBufferStorage(GL_UNIFORM_BUFFER, STD140UniformBlockType.MAT4.getByteSize() * 3L, MeshConstants.DEFAULT_FLAGS_HINT);

        ByteBuffer byteBuffer = glMapBufferRange(
                GL_UNIFORM_BUFFER,
                0,
                STD140UniformBlockType.MAT4.getByteSize() * 3L,
                MeshConstants.DEFAULT_FLAGS_HINT
        );
        if (byteBuffer == null) {
            throw new RuntimeException("glMapBufferRange failed");
        }
        matrixUniformBlockMappedBuffer = byteBuffer.asFloatBuffer();

        int blockIndex = glGetUniformBlockIndex(programId, "Matrices");
        glUniformBlockBinding(programId, blockIndex, 0);
        glBindBufferRange(GL_UNIFORM_BUFFER, 0, matrixUniformBlockId, 0, STD140UniformBlockType.MAT4.getByteSize() * 3L);
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
        matrixUniformBlocksCache.clear();
        matrixUniformBlocksValues.clear();

        if (vertexShaderId != 0) {
            glDeleteShader(vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDeleteShader(fragmentShaderId);
        }
        if (programId != 0) {
            GL_STATE_CACHE.deleteProgram(programId);
        }

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                "ProgramId: " + programId + " | VertexShaderId: " + vertexShaderId + " | FragmentShaderId: " + fragmentShaderId,
                ""
        ).logging(getClass(), DebugLevel.DEBUG);
    }

    public void createVertexShader(Location fileLocation) {
        vertexShaderId = createShader(new GLSLCode(fileLocation).createCode(), GL_VERTEX_SHADER);
    }

    public void createFragmentShader(Location fileLocation) {
        fragmentShaderId = createShader(new GLSLCode(fileLocation).createCode(), GL_FRAGMENT_SHADER);
    }

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

    private int getUniformBlockLocation(String name) {
        return matrixUniformBlocksCache.computeIfAbsent(name, n -> glGetUniformBlockIndex(SHADER_PROGRAM.getProgramId(), name));
    }

    private int getUniformLocation(String name) {
        return uniformCache.computeIfAbsent(name, n -> glGetUniformLocation(programId, n));
    }

    public void bind() {
        GL_STATE_CACHE.useProgram(programId);
    }

    public void updateMatrixUniformBlock(Matrix4f projection, Matrix4f view, Matrix4f model) {
        projection.get(0, matrixUniformBlockMappedBuffer);
        view.get(16, matrixUniformBlockMappedBuffer);
        model.get(32, matrixUniformBlockMappedBuffer);
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
        if (first instanceof Boolean) {
            int booleanValue = ((boolean) first) ? 1 : 0;
            glUniform1i(location, booleanValue);
        } else if (first instanceof Float) {
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
        } else if (first instanceof Integer) {
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
        } else if (first instanceof FloatBuffer) {
            FloatBuffer floatBuffer = (FloatBuffer) first;
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
        } else if (first instanceof Matrix4f) {
            Matrix4f mat = (Matrix4f) first;
            try (MemoryStack stack = MemoryStack.stackPush()) {
                glUniformMatrix4fv(location, false, mat.get(stack.mallocFloat(16)));
            }
        } else if (first instanceof Vector2f) {
            Vector2f vec = (Vector2f) first;
            glUniform2f(location, vec.x, vec.y);
        } else if (first instanceof Vector3f) {
            Vector3f vec = (Vector3f) first;
            glUniform3f(location, vec.x, vec.y, vec.z);
        } else if (first instanceof Vector4f) {
            Vector4f vec = (Vector4f) first;
            glUniform4f(location, vec.x, vec.y, vec.z, vec.w);
        } else if (first instanceof Vector2i) {
            Vector2i vec = (Vector2i) first;
            glUniform2i(location, vec.x, vec.y);
        } else if (first instanceof Vector3i) {
            Vector3i vec = (Vector3i) first;
            glUniform3i(location, vec.x, vec.y, vec.z);
        } else if (first instanceof Vector4i) {
            Vector4i vec = (Vector4i) first;
            glUniform4i(location, vec.x, vec.y, vec.z, vec.w);
        } else if (first instanceof Color) {
            Color color = (Color) first;
            glUniform4f(location, color.getRedF(), color.getGreenF(), color.getBlueF(), color.getAlphaF());
        } else {
            throw new IllegalArgumentException("Unsupported uniform value type: " + first.getClass());
        }
    }

    public Map<String, UniformValue> getUniformValues() {
        return uniformValues;
    }

    public Map<String, Integer> getUniformCache() {
        return uniformCache;
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