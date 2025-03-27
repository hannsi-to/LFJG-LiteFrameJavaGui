package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

import static org.lwjgl.opengl.GL30.*;

/**
 * Represents a mesh in the OpenGL rendering system.
 * Handles the creation and management of vertex buffer objects (VBOs) and vertex array objects (VAOs).
 */
public class Mesh {
    public static ProjectionType DEFAULT_PROJECTION_TYPE = ProjectionType.OrthographicProjection;
    public static boolean DEFAULT_UES_EBO = true;

    private final float[] positions;
    private final float[] colors;
    private final float[] texture;
    private final ProjectionType projectionType;
    private final boolean uesEBO;
    private final int vaoId;
    private final List<Integer> vboIdList;
    private int numVertices;
    private int eboId;

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
        this(projectionType, positions, colors, texture, DEFAULT_UES_EBO);
    }

    public Mesh(ProjectionType projectionType, float[] positions, float[] colors, float[] texture, boolean uesEBO) {
        this.projectionType = projectionType;
        this.positions = positions;
        this.colors = colors;
        this.texture = texture;
        this.uesEBO = uesEBO;

        vboIdList = new ArrayList<>();

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        if (!uesEBO) {
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
            createVBO(positions, 0, size);
        } else {
            createVBOEBOFromPositions();
        }

        if (colors != null) {
            createVBO(colors, 1, 4);
        }

        if (texture != null) {
            createVBO(texture, 2, 2);
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
        this.uesEBO = true;

        numVertices = indices.length;
        vboIdList = new ArrayList<>();

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        createVBO(positions, 0, 3);
        createVBO(normals, 1, 3);
        createVBO(textCoords, 2, 2);

        createEBO(indices);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    private void createVBOEBOFromPositions() {
        Map<String, Integer> uniqueVertices = new HashMap<>();
        List<Integer> indices = new ArrayList<>();
        List<Float> uniquePositions = new ArrayList<>();
        int index = 0;
        int stride = (projectionType == ProjectionType.OrthographicProjection) ? 2 : 3;

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
        IntBuffer indicesBuffer = MemoryUtil.memCallocInt(indices.length);
        indicesBuffer.put(0, indices);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void createVBO(float[] values, int index, int size) {
        int vboId = glGenBuffers();
        vboIdList.add(vboId);
        FloatBuffer valuesBuffer = MemoryUtil.memCallocFloat(values.length);
        valuesBuffer.put(0, values);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, valuesBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(index);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        MemoryUtil.memFree(valuesBuffer);
    }

    /**
     * Cleans up the mesh by deleting the VBOs and VAO.
     */
    public void cleanup() {
        vboIdList.forEach(GL30::glDeleteBuffers);
        glDeleteVertexArrays(vaoId);
        vboIdList.clear();

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

    public boolean isUesEBO() {
        return uesEBO;
    }

    public int getEboId() {
        return eboId;
    }
}