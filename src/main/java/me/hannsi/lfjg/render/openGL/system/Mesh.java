package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

/**
 * Represents a mesh in the OpenGL rendering system.
 * Handles the creation and management of vertex buffer objects (VBOs) and vertex array objects (VAOs).
 */
public class Mesh {
    private static final ProjectionType DEFAULT_PROJECTION_TYPE = ProjectionType.OrthographicProjection;
    private final float[] positions;
    private final float[] colors;
    private final float[] texture;
    private final ProjectionType projectionType;
    private final int vaoId;
    private final List<Integer> vboIdList;
    private int numVertices;

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
        this.projectionType = projectionType;
        this.positions = positions;
        this.colors = colors;
        this.texture = texture;

        vboIdList = new ArrayList<>();

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        int vboId = glGenBuffers();
        vboIdList.add(vboId);
        FloatBuffer positionsBuffer = MemoryUtil.memAllocFloat(positions.length);
        positionsBuffer.put(0, positions);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);

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

        glVertexAttribPointer(0, size, GL_FLOAT, false, 0, 0);

        if (colors != null) {
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            FloatBuffer colorsBuffer = MemoryUtil.memAllocFloat(colors.length);
            colorsBuffer.put(0, colors);
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(1);
            glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

            MemoryUtil.memFree(colorsBuffer);
        }

        if (texture != null) {
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            FloatBuffer textCoordsBuffer = MemoryUtil.memAllocFloat(texture.length);
            textCoordsBuffer.put(0, texture);
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(2);
            glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);

            MemoryUtil.memFree(textCoordsBuffer);
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        MemoryUtil.memFree(positionsBuffer);
    }

    /**
     * Constructs a new Mesh instance with the specified positions, texture coordinates, and indices.
     *
     * @param positions  the vertex positions
     * @param textCoords the texture coordinates
     * @param indices    the indices
     */
    public Mesh(float[] positions, float[] textCoords, int[] indices) {
        this.positions = positions;
        this.texture = textCoords;
        this.projectionType = ProjectionType.PerspectiveProjection;
        this.colors = null;

        numVertices = indices.length;
        vboIdList = new ArrayList<>();

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        int vboId = glGenBuffers();
        vboIdList.add(vboId);
        FloatBuffer positionsBuffer = MemoryUtil.memAllocFloat(positions.length);
        positionsBuffer.put(0, positions);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        vboId = glGenBuffers();
        vboIdList.add(vboId);
        FloatBuffer textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
        textCoordsBuffer.put(0, textCoords);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        vboId = glGenBuffers();
        vboIdList.add(vboId);
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(0, indices);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        MemoryUtil.memFree(positionsBuffer);
        MemoryUtil.memFree(textCoordsBuffer);
        MemoryUtil.memFree(indicesBuffer);
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

        numVertices = indices.length;
        vboIdList = new ArrayList<>();

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        int vboId = glGenBuffers();
        vboIdList.add(vboId);
        FloatBuffer positionsBuffer = MemoryUtil.memCallocFloat(positions.length);
        positionsBuffer.put(0, positions);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        vboId = glGenBuffers();
        vboIdList.add(vboId);
        FloatBuffer normalsBuffer = MemoryUtil.memCallocFloat(normals.length);
        normalsBuffer.put(0, normals);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        vboId = glGenBuffers();
        vboIdList.add(vboId);
        FloatBuffer textCoordsBuffer = MemoryUtil.memCallocFloat(textCoords.length);
        textCoordsBuffer.put(0, textCoords);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);

        vboId = glGenBuffers();
        vboIdList.add(vboId);
        IntBuffer indicesBuffer = MemoryUtil.memCallocInt(indices.length);
        indicesBuffer.put(0, indices);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        MemoryUtil.memFree(positionsBuffer);
        MemoryUtil.memFree(normalsBuffer);
        MemoryUtil.memFree(textCoordsBuffer);
        MemoryUtil.memFree(indicesBuffer);
    }

    /**
     * Cleans up the mesh by deleting the VBOs and VAO.
     */
    public void cleanup() {
        vboIdList.forEach(GL30::glDeleteBuffers);
        glDeleteVertexArrays(vaoId);
        vboIdList.clear();
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
    public List<Integer> getVboIdList() {
        return vboIdList;
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
}