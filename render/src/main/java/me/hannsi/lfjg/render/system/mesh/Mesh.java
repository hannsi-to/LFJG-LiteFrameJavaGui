package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.debug.exceptions.render.mesh.MeshBuilderException;
import me.hannsi.lfjg.render.system.mesh.persistent.PersistentMappedEBO;
import me.hannsi.lfjg.render.system.mesh.persistent.PersistentMappedIBO;
import me.hannsi.lfjg.render.system.mesh.persistent.PersistentMappedVBO;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import org.lwjgl.opengl.GL30;

import java.util.*;

public class Mesh {
    private final int vaoId;
    private final HashMap<BufferObjectType, PersistentMappedVBO> vboIds;
    private final List<DrawCommand> drawCommands;
    private PersistentMappedEBO eboId;
    private PersistentMappedIBO iboId;
    private int numVertices;
    private int count;
    private ProjectionType projectionType;
    private int flagsHint;
    private boolean useElementBufferObject;
    private boolean useIndirect;

    Mesh() {
        this.vaoId = GL30.glGenVertexArrays();
        this.vboIds = new HashMap<>();

        this.projectionType = MeshConstants.DEFAULT_PROJECTION_TYPE;
        this.flagsHint = MeshConstants.DEFAULT_FLAGS_HINT;
        this.useElementBufferObject = MeshConstants.DEFAULT_USE_ELEMENT_BUFFER_OBJECT;
        this.useIndirect = MeshConstants.DEFAULT_USE_INDIRECT;
        this.drawCommands = new ArrayList<>();

        GLStateCache.bindVertexArray(vaoId);
    }

    public static Mesh createMesh() {
        return new Mesh();
    }

    public String getIds() {
        StringBuilder ids = new StringBuilder();
        for (Map.Entry<BufferObjectType, PersistentMappedVBO> vboEntry : vboIds.entrySet()) {
            BufferObjectType bufferObjectType = vboEntry.getKey();
            PersistentMappedVBO persistentMappedVBO = vboEntry.getValue();
            int[] bufferId = persistentMappedVBO.getBufferIds();

            ids.append(bufferObjectType.getName()).append(": ").append(Arrays.toString(bufferId)).append(" | ");
        }

        if (useElementBufferObject) {
            int id = eboId.getBufferId();
            ids.append("ElementBufferObject: [").append(id).append("] | ");
        }

        if (useIndirect) {
            int id = iboId.getBufferId();
            ids.append("IndirectBufferObject: [").append(id).append("] | ");
        }

        ids.append("VertexArrayObject: [").append(vaoId).append("]");

        return ids.toString();
    }

    public void cleanup() {
        StringBuilder ids = new StringBuilder();
        for (Map.Entry<BufferObjectType, PersistentMappedVBO> vboEntry : vboIds.entrySet()) {
            BufferObjectType bufferObjectType = vboEntry.getKey();
            PersistentMappedVBO persistentMappedVBO = vboEntry.getValue();
            int[] bufferId = persistentMappedVBO.getBufferIds();

            persistentMappedVBO.cleanup();

            ids.append(bufferObjectType.getName()).append(": ").append(Arrays.toString(bufferId)).append(" | ");
        }

        vboIds.clear();
        if (useElementBufferObject) {
            int id = eboId.getBufferId();
            eboId.cleanup();
            ids.append("ElementBufferObject: ").append(id).append(" | ");
        }

        if (useIndirect) {
            int id = iboId.getBufferId();
            iboId.cleanup();
            ids.append("IndirectBufferObject: ").append(id).append(" | ");
        }

        GL30.glDeleteVertexArrays(vaoId);
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

    public Mesh createBufferObject2D(DrawType drawType, float[] positions, float[] colors, float[] textures) {
        if (positions != null) {
            if (positions.length != 0) {
                createPositionsVBO(positions, drawType);
            }
        } else {
            throw new MeshBuilderException("Can not create mesh object. positions = null");
        }

        if (colors != null) {
            if (colors.length != 0) {
                vboIds.put(
                        BufferObjectType.COLOR_BUFFER,
                        new PersistentMappedVBO(
                                colors.length,
                                flagsHint
                        ).update(colors).attribute(BufferObjectType.COLOR_BUFFER, 0, 0)
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
                        ).update(textures).attribute(BufferObjectType.TEXTURE_BUFFER, 0, 0)
                );
            }
        }

        if (useIndirect) {
            createIndirectBuffer();
        }

        return this;
    }

    public Mesh createBufferObject2D(float[] positions, float[] colors, float[] textures, int[] indices) {
        vboIds.put(
                BufferObjectType.POSITION_BUFFER,
                new PersistentMappedVBO(
                        positions.length,
                        flagsHint
                ).update(positions).attribute(BufferObjectType.POSITION_BUFFER, 0, 0)
        );
        count = positions.length / projectionType.getStride();

        if (indices != null) {
            this.useElementBufferObject = true;
            numVertices = indices.length;

            eboId = new PersistentMappedEBO(
                    numVertices,
                    flagsHint
            ).update(indices);
        }

        if (colors != null) {
            vboIds.put(
                    BufferObjectType.COLOR_BUFFER,
                    new PersistentMappedVBO(
                            colors.length,
                            flagsHint
                    ).update(colors).attribute(BufferObjectType.COLOR_BUFFER, 0, 0)
            );
        }

        if (textures != null) {
            vboIds.put(
                    BufferObjectType.TEXTURE_BUFFER,
                    new PersistentMappedVBO(
                            textures.length,
                            flagsHint
                    ).update(textures).attribute(BufferObjectType.TEXTURE_BUFFER, 0, 0)
            );
        }

        if (useIndirect) {
            createIndirectBuffer();
        }

        return this;
    }

    public Mesh createBufferObject3D(float[] positions, int[] indices, float[] colors, float[] textures, float[] normals) {
        vboIds.put(
                BufferObjectType.POSITION_BUFFER,
                new PersistentMappedVBO(
                        positions.length,
                        flagsHint
                ).update(positions).attribute(BufferObjectType.POSITION_BUFFER, 0, 0)
        );
        count = positions.length / projectionType.getStride();

        if (indices != null) {
            this.useElementBufferObject = true;
            numVertices = indices.length;

            eboId = new PersistentMappedEBO(
                    numVertices,
                    flagsHint
            ).update(indices);
        }

        if (colors != null) {
            vboIds.put(
                    BufferObjectType.COLOR_BUFFER,
                    new PersistentMappedVBO(
                            colors.length,
                            flagsHint
                    ).update(colors).attribute(BufferObjectType.COLOR_BUFFER, 0, 0)
            );
        }

        if (textures != null) {
            vboIds.put(
                    BufferObjectType.TEXTURE_BUFFER,
                    new PersistentMappedVBO(
                            textures.length,
                            flagsHint
                    ).update(textures).attribute(BufferObjectType.TEXTURE_BUFFER, 0, 0)
            );
        }

        if (normals != null) {
            vboIds.put(
                    BufferObjectType.NORMAL_BUFFER,
                    new PersistentMappedVBO(
                            normals.length,
                            flagsHint
                    ).update(normals).attribute(BufferObjectType.NORMAL_BUFFER, 0, 0)
            );
        }

        if (useIndirect) {
            createIndirectBuffer();
        }

        return this;
    }

    public void updateVBOData(DrawType drawType, BufferObjectType bufferObjectType, float[] values) {
        PersistentMappedVBO persistentMappedVBO = vboIds.get(bufferObjectType);
        if (persistentMappedVBO == null) {
            return;
        }

        if (bufferObjectType == BufferObjectType.POSITION_BUFFER || bufferObjectType == BufferObjectType.POSITION_BUFFER) {
            if (useElementBufferObject) {
                ElementPair elementPair = getElementPositions(values, drawType, bufferObjectType == BufferObjectType.POSITION_BUFFER ? ProjectionType.ORTHOGRAPHIC_PROJECTION : ProjectionType.PERSPECTIVE_PROJECTION);
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
        GLStateCache.bindVertexArray(0);
        return this;
    }

    public int getDrawCommandCount() {
        return drawCommands.size();
    }

    private void createPositionsVBO(float[] positions, DrawType drawType) {
        if (useElementBufferObject) {
            ElementPair elementPair = getElementPositions(positions, drawType, ProjectionType.ORTHOGRAPHIC_PROJECTION);
            numVertices = elementPair.indices.length;

            vboIds.put(
                    BufferObjectType.POSITION_BUFFER,
                    new PersistentMappedVBO(
                            elementPair.positions.length,
                            flagsHint
                    ).update(elementPair.positions).attribute(BufferObjectType.POSITION_BUFFER, 0, 0)
            );

            eboId = new PersistentMappedEBO(
                    elementPair.indices.length,
                    flagsHint
            ).update(elementPair.indices);
        } else {
            vboIds.put(
                    BufferObjectType.POSITION_BUFFER,
                    new PersistentMappedVBO(
                            positions.length,
                            flagsHint
                    ).update(positions).attribute(BufferObjectType.POSITION_BUFFER, 0, 0)
            );
            count = positions.length / projectionType.getStride();
        }
    }

    private ElementPair getElementPositions(float[] positions, DrawType drawType, ProjectionType projectionType) {
        return PolygonTriangulator.createPolygonTriangulator()
                .drawType(drawType)
                .projectionType(projectionType)
                .positions(positions)
                .process()
                .getResult();
    }

    public void startFrame() {

    }

    public void endFrame() {
        vboIds.forEach((key, value) -> value.finishFrame());
    }

    private void createIndirectBuffer() {
        if (drawCommands.isEmpty()) {
            drawCommands.addAll(generateDefaultDrawCommands());
        }
        uploadIndirectBuffer();
    }

    private List<DrawCommand> generateDefaultDrawCommands() {
        int count = useElementBufferObject ? numVertices : this.count;
        List<DrawCommand> list = new ArrayList<>();
        list.add(new DrawCommand(count, 1, 0, 0, 0));
        return Collections.unmodifiableList(list);
    }

    private void uploadIndirectBuffer() {
        iboId = new PersistentMappedIBO(drawCommands.size(), flagsHint)
                .update(drawCommands);
    }

    public int getVaoId() {
        return vaoId;
    }

    public HashMap<BufferObjectType, PersistentMappedVBO> getVboIds() {
        return vboIds;
    }

    public List<DrawCommand> getDrawCommands() {
        return drawCommands;
    }

    public PersistentMappedEBO getEboId() {
        return eboId;
    }

    public PersistentMappedIBO getIboId() {
        return iboId;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public int getCount() {
        return count;
    }

    public ProjectionType getProjectionType() {
        return projectionType;
    }

    public int getFlagsHint() {
        return flagsHint;
    }

    public boolean isUseElementBufferObject() {
        return useElementBufferObject;
    }

    public boolean isUseIndirect() {
        return useIndirect;
    }
}
