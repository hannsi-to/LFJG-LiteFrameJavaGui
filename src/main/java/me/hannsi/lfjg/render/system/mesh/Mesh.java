package me.hannsi.lfjg.render.system.mesh;

import lombok.Getter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.render.debug.exceptions.render.meshBuilder.MeshBuilderException;
import me.hannsi.lfjg.utils.type.types.AttributeType;
import me.hannsi.lfjg.utils.type.types.BufferObjectType;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;
import java.util.*;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.GL_DRAW_INDIRECT_BUFFER;

@Getter
public class Mesh implements AutoCloseable {
    private final int vaoId;
    private final HashMap<BufferObjectType, PersistentMappedVBO> vboIds;
    private final List<DrawCommand> drawCommands;
    private PersistentMappedEBO eboId;
    private int indirectBufferId;
    private int numVertices;
    private int count;
    private ProjectionType projectionType;
    private int flagsHint;
    private int usageHint;
    private boolean useElementBufferObject;
    private boolean useIndirect;

    Mesh() {
        this.vaoId = glGenVertexArrays();
        this.vboIds = new HashMap<>();

        this.projectionType = MeshConstants.PROJECTION_TYPE;
        this.flagsHint = MeshConstants.FLAGS_HINT;
        this.usageHint = MeshConstants.USAGE_HINT;
        this.useElementBufferObject = MeshConstants.USE_ELEMENT_BUFFER_OBJECT;
        this.useIndirect = MeshConstants.USE_INDIRECT;
        this.drawCommands = new ArrayList<>();

        glBindVertexArray(vaoId);
    }

    public static Mesh initMesh() {
        return new Mesh();
    }

    public void cleanup() {
        StringBuilder ids = new StringBuilder();
        for (Map.Entry<BufferObjectType, PersistentMappedVBO> vboEntry : vboIds.entrySet()) {
            BufferObjectType bufferObjectType = vboEntry.getKey();
            PersistentMappedVBO persistentMappedVBO = vboEntry.getValue();
            int bufferId = persistentMappedVBO.getBufferId();

            persistentMappedVBO.cleanup();

            ids.append(bufferObjectType.getName()).append(": ").append(bufferId).append(" | ");
        }

        vboIds.clear();
        if (useElementBufferObject) {
            int id = eboId.getBufferId();
            eboId.cleanup();
            ids.append("ElementBufferObject: ").append(id).append(" | ");
        }

        if (useIndirect) {
            glDeleteBuffers(indirectBufferId);
            ids.append("IndirectBufferObject: ").append(indirectBufferId).append(" | ");
        }

        glDeleteVertexArrays(vaoId);
        ids.append("VertexArrayObject: ").append(vaoId);

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.toString(),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public Mesh projectionType(ProjectionType projectionType) {
        this.projectionType = projectionType;
        return this;
    }

    public Mesh flagsHint(int flagsHint) {
        this.flagsHint = flagsHint;
        return this;
    }

    public Mesh usageHint(int usageHint) {
        this.usageHint = usageHint;
        return this;
    }

    public Mesh useElementBufferObject(boolean useElementBufferObject) {
        this.useElementBufferObject = useElementBufferObject;
        return this;
    }

    public Mesh useIndirect(boolean useIndirect) {
        this.useIndirect = useIndirect;
        return this;
    }

    public Mesh addDrawCommand(DrawCommand command) {
        drawCommands.add(command);
        return this;
    }

    public Mesh createBufferObject2D(float[] positions, float[] colors, float[] textures) {
        if (positions != null) {
            if (positions.length != 0) {
                createPositionsVBO(positions);
            }
        } else {
            throw new MeshBuilderException("Can not create mesh object. positions = null");
        }

        if (colors != null) {
            if (colors.length != 0) {
                vboIds.put(
                        BufferObjectType.COLORS_BUFFER,
                        new PersistentMappedVBO(
                                colors.length,
                                flagsHint
                        ).update(colors).attribute(AttributeType.COLOR)
                );
            }
        }

        if (textures != null) {
            if (textures.length != 0) {
                vboIds.put(
                        BufferObjectType.TEXTURE_BUFFER,
                        new PersistentMappedVBO(
                                textures.length,
                                flagsHint
                        ).update(textures).attribute(AttributeType.TEXTURE)
                );
            }
        }

        if (useIndirect) {
            createIndirectBuffer();
        }

        return this;
    }

    public Mesh createBufferObject3D(float[] positions, float[] normals, float[] textures, int[] indices) {
        this.useElementBufferObject = true;

        numVertices = indices.length;

        vboIds.put(
                BufferObjectType.POSITIONS_BUFFER,
                new PersistentMappedVBO(
                        positions.length,
                        flagsHint
                ).update(positions).attribute(AttributeType.POSITION_3D)
        );
        count = positions.length / projectionType.getStride();

        vboIds.put(
                BufferObjectType.NORMALS_BUFFER,
                new PersistentMappedVBO(
                        normals.length,
                        flagsHint
                ).update(normals).attribute(AttributeType.NORMAL_3D)
        );

        vboIds.put(
                BufferObjectType.TEXTURE_BUFFER,
                new PersistentMappedVBO(
                        textures.length,
                        flagsHint
                ).update(textures).attribute(AttributeType.TEXTURE)
        );

        eboId = new PersistentMappedEBO(
                numVertices,
                flagsHint
        ).update(indices);

        if (useIndirect) {
            createIndirectBuffer();
        }

        return this;
    }

    public void updateVBOData(BufferObjectType bufferObjectType, float[] values) {
        PersistentMappedVBO persistentMappedVBO = vboIds.get(bufferObjectType);
        if (persistentMappedVBO == null) {
            return;
        }

        if (bufferObjectType == BufferObjectType.POSITIONS_BUFFER) {
            if (useElementBufferObject) {
                ElementPair elementPair = getElementPositions(values);
                numVertices = elementPair.indices.length;
                eboId.update(elementPair.indices);
                count = elementPair.positions.length / projectionType.getStride();
                persistentMappedVBO.update(elementPair.positions);
            } else {
                count = values.length / projectionType.getStride();
                persistentMappedVBO.update(values);
            }
        } else {
            persistentMappedVBO.update(values);
        }
    }

    public Mesh builderClose() {
        glBindVertexArray(0);
        return this;
    }

    private void createPositionsVBO(float[] positions) {
        if (useElementBufferObject) {
            ElementPair elementPair = getElementPositions(positions);
            numVertices = elementPair.indices.length;

            vboIds.put(
                    BufferObjectType.POSITIONS_BUFFER,
                    new PersistentMappedVBO(
                            elementPair.positions.length,
                            flagsHint
                    ).update(elementPair.positions).attribute(AttributeType.POSITION_2D)
            );

            eboId = new PersistentMappedEBO(
                    elementPair.indices.length,
                    flagsHint
            ).update(elementPair.indices);
        } else {
            vboIds.put(
                    BufferObjectType.POSITIONS_BUFFER,
                    new PersistentMappedVBO(
                            positions.length,
                            flagsHint
                    ).update(positions).attribute(AttributeType.POSITION_2D)
            );
            count = positions.length / projectionType.getStride();
        }
    }

    private ElementPair getElementPositions(float[] positions) {
        Map<VertexKey, Integer> uniqueVertices = new HashMap<>();
        int stride = projectionType.getStride();
        int[] indices = new int[positions.length / stride];
        float[] uniquePositions = new float[positions.length];
        int uniqueCount = 0;

        for (int i = 0, idx = 0; i < positions.length; i += stride, idx++) {
            VertexKey key = new VertexKey(positions, i, stride);
            Integer existingIndex = uniqueVertices.get(key);
            if (existingIndex != null) {
                indices[idx] = existingIndex;
            } else {
                uniqueVertices.put(key, uniqueCount);
                indices[idx] = uniqueCount;
                System.arraycopy(positions, i, uniquePositions, uniqueCount * stride, stride);
                uniqueCount++;
            }
        }

        float[] finalPositions = Arrays.copyOf(uniquePositions, uniqueCount * stride);
        return new ElementPair(finalPositions, indices);
    }

    private void createIndirectBuffer() {
        int commandCount = drawCommands.isEmpty() ? 1 : drawCommands.size();
        IntBuffer buffer = BufferUtils.createIntBuffer(commandCount * 5);

        if (drawCommands.isEmpty()) {
            drawCommands.add(
                    new DrawCommand(
                            useElementBufferObject ? numVertices : count,
                            1,
                            0,
                            0,
                            0
                    )
            );
        }

        for (DrawCommand drawCommand : drawCommands) {
            drawCommand.putIntoBuffer(buffer);
        }

        buffer.flip();

        indirectBufferId = glGenBuffers();
        glBindBuffer(GL_DRAW_INDIRECT_BUFFER, indirectBufferId);
        glBufferData(GL_DRAW_INDIRECT_BUFFER, buffer, usageHint);
        glBindBuffer(GL_DRAW_INDIRECT_BUFFER, 0);
    }

    @Override
    public void close() {
        cleanup();
    }
}
