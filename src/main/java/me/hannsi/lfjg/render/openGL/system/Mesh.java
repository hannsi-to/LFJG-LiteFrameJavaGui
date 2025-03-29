package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.debug.debug.system.DebugLevel;
import me.hannsi.lfjg.debug.debug.log.LogGenerator;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.GL_DRAW_INDIRECT_BUFFER;

/**
 * Represents a mesh in the OpenGL rendering system.
 * Handles the creation and management of vertex buffer objects (VBOs) and vertex array objects (VAOs).
 */
public class Mesh {
    public static ProjectionType DEFAULT_PROJECTION_TYPE = ProjectionType.OrthographicProjection;
    public static boolean DEFAULT_USE_EBO = true;
    public static boolean DEFAULT_USE_INDIRECT = true;
    public static int DEFAULT_USAGE_HINT = GL_STATIC_DRAW;

    private final float[] positions;
    private final float[] colors;
    private final float[] texture;
    private final ProjectionType projectionType;
    private final boolean useEBO;
    private final boolean useIndirect;
    private final int vaoId;
    private final List<Integer> bufferIdList;
    private int numVertices;
    private int eboId;
    private int indirectBufferId;
    private final int usageHint = DEFAULT_USAGE_HINT;

    /**
     * Constructs a new Mesh instance with the specified positions and colors.
     *
     * @param positions the vertex positions
     * @param colors    the vertex colors
     */
    public Mesh(float[] positions, float[] colors) {
        this(DEFAULT_PROJECTION_TYPE, positions, colors);
    }

    /**
     * Constructs a new Mesh instance with the specified projection type and positions.
     *
     * @param projectionType the projection type
     * @param positions      the vertex positions
     */
    public Mesh(ProjectionType projectionType, float[] positions) {
        this(projectionType, positions, null, null);
    }

    /**
     * Constructs a new Mesh instance with the specified projection type, positions, and colors.
     *
     * @param projectionType the projection type
     * @param positions      the vertex positions
     * @param colors         the vertex colors
     */
    public Mesh(ProjectionType projectionType, float[] positions, float[] colors) {
        this(projectionType, positions, colors, null);
    }

    /**
     * Constructs a new Mesh instance with the specified projection type, positions, colors, and texture coordinates.
     *
     * @param projectionType the projection type
     * @param positions      the vertex positions
     * @param colors         the vertex colors
     * @param texture        the texture coordinates
     */
    public Mesh(ProjectionType projectionType, float[] positions, float[] colors, float[] texture) {
        this(projectionType, positions, colors, texture, DEFAULT_USE_EBO);
    }

    public Mesh(ProjectionType projectionType, float[] positions, float[] colors, float[] texture, boolean useEBO) {
        this(projectionType, positions, colors, texture, useEBO, DEFAULT_USE_INDIRECT);
    }

    public Mesh(ProjectionType projectionType, float[] positions, float[] colors, float[] texture, boolean useEBO, boolean useIndirect) {
        this.projectionType = projectionType;
        this.positions = positions;
        this.colors = colors;
        this.texture = texture;
        this.useEBO = useEBO;
        this.useIndirect = useIndirect;

        bufferIdList = new ArrayList<>();

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        if (!useEBO) {
            createVBO(positions, 0, getSize());
        } else {
            createVBOEBOFromPositions();
        }

        if (colors != null) {
            createVBO(colors, 1, 4);
        }

        if (texture != null) {
            createVBO(texture, 2, 2);
        }

        if (useIndirect) {
            createIndirectBuffer();
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    /**
     * Constructs a new Mesh instance with the specified positions, normals, texture coordinates, and indices.
     *
     * @param positions  the vertex positions
     * @param normals    the vertex normals
     * @param textCoords the texture coordinates
     * @param indices    the indices
     */
    public Mesh(float[] positions, float[] normals, float[] textCoords, int[] indices) {
        this.positions = positions;
        this.texture = textCoords;
        this.projectionType = ProjectionType.PerspectiveProjection;
        this.colors = null;
        this.useEBO = true;
        this.useIndirect = DEFAULT_USE_INDIRECT;

        numVertices = indices.length;
        bufferIdList = new ArrayList<>();

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        createVBO(positions, 0, 3);
        createVBO(normals, 1, 3);
        createVBO(textCoords, 2, 2);

        createEBO(indices);

        if (useIndirect) {
            createIndirectBuffer();
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    private int getSize() {
        int size;
        switch (projectionType) {
            case OrthographicProjection -> {
                size = 2;
            }
            case PerspectiveProjection -> {
                size = 3;
            }
            default -> throw new IllegalStateException("Unexpected value: " + projectionType);
        }

        return size;
    }

    private void createIndirectBuffer() {
        IntBuffer drawCommand;
        if (useEBO) {
            drawCommand = BufferUtils.createIntBuffer(5);
            drawCommand.put(numVertices);
            drawCommand.put(1);
            drawCommand.put(0);
        } else {
            int size = getSize();
            int count = positions.length / size;

            drawCommand = BufferUtils.createIntBuffer(4);
            drawCommand.put(count);
            drawCommand.put(1);
        }
        drawCommand.put(0);
        drawCommand.put(0);
        drawCommand.flip();

        indirectBufferId = glGenBuffers();
        bufferIdList.add(indirectBufferId);

        glBindBuffer(GL_DRAW_INDIRECT_BUFFER, indirectBufferId);
        glBufferData(GL_DRAW_INDIRECT_BUFFER, drawCommand, usageHint);
        glBindBuffer(GL_DRAW_INDIRECT_BUFFER, 0);
    }

    private void createVBOEBOFromPositions() {
        Map<String, Integer> uniqueVertices = new HashMap<>();
        List<Integer> indices = new ArrayList<>();
        List<Float> uniquePositions = new ArrayList<>();
        int index = 0;
        int stride = getSize();

        for (int i = 0; i < positions.length; i += stride) {
            String key = Arrays.toString(Arrays.copyOfRange(positions, i, i + stride));
            if (uniqueVertices.containsKey(key)) {
                indices.add(uniqueVertices.get(key));
            } else {
                uniqueVertices.put(key, index);
                indices.add(index);
                for (int j = 0; j < stride; j++) {
                    uniquePositions.add(positions[i + j]);
                }
                index++;
            }
        }

        float[] newPositions = new float[uniquePositions.size()];
        for (int i = 0; i < uniquePositions.size(); i++) {
            newPositions[i] = uniquePositions.get(i);
        }

        int[] indicesArray = indices.stream().mapToInt(i -> i).toArray();
        createVBO(newPositions, 0, stride);
        createEBO(indicesArray);
        numVertices = indicesArray.length;
    }

    public void createEBO(int[] indices) {
        eboId = glGenBuffers();
        bufferIdList.add(eboId);
        IntBuffer indicesBuffer = MemoryUtil.memCallocInt(indices.length);
        indicesBuffer.put(0, indices);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, usageHint);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void createVBO(float[] values, int index, int size) {
        int vboId = glGenBuffers();
        bufferIdList.add(vboId);
        FloatBuffer valuesBuffer = MemoryUtil.memCallocFloat(values.length);
        valuesBuffer.put(0, values);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, valuesBuffer, usageHint);
        glEnableVertexAttribArray(index);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        MemoryUtil.memFree(valuesBuffer);
    }

    /**
     * Cleans up the mesh by deleting the VBOs and VAO.
     */
    public void cleanup() {
        bufferIdList.forEach(GL30::glDeleteBuffers);
        glDeleteVertexArrays(vaoId);
        bufferIdList.clear();

        LogGenerator logGenerator = new LogGenerator("Mesh", "Source: Mesh", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: Mesh cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Gets the VAO ID.
     *
     * @return the VAO ID
     */
    public final int getVaoId() {
        return vaoId;
    }

    /**
     * Gets the projection type.
     *
     * @return the projection type
     */
    public ProjectionType getProjectionType() {
        return projectionType;
    }

    /**
     * Gets the list of VBO IDs.
     *
     * @return the list of VBO IDs
     */
    public List<Integer> getBufferIdList() {
        return bufferIdList;
    }

    /**
     * Gets the vertex positions.
     *
     * @return the vertex positions
     */
    public float[] getPositions() {
        return positions;
    }

    /**
     * Gets the vertex colors.
     *
     * @return the vertex colors
     */
    public float[] getColors() {
        return colors;
    }

    /**
     * Gets the texture coordinates.
     *
     * @return the texture coordinates
     */
    public float[] getTexture() {
        return texture;
    }

    /**
     * Gets the number of vertices.
     *
     * @return the number of vertices
     */
    public int getNumVertices() {
        return numVertices;
    }

    public boolean isUseEBO() {
        return useEBO;
    }

    public int getEboId() {
        return eboId;
    }

    public boolean isUseIndirect() {
        return useIndirect;
    }

    public int getIndirectBufferId() {
        return indirectBufferId;
    }

    public int getUsageHint() {
        return usageHint;
    }
}