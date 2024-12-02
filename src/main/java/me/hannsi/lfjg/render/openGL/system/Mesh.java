package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private static final ProjectionType DEFAULT_PROJECTION_TYPE = ProjectionType.OrthographicProjection;

    private final float[] positions;
    private final float[] colors;
    private final ProjectionType projectionType;
    private final int vaoId;
    private final List<Integer> vboIdList;

    public Mesh(float[] positions, float[] colors) {
        this(DEFAULT_PROJECTION_TYPE, positions, colors);
    }

    public Mesh(ProjectionType projectionType, float[] positions, float[] colors) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            this.projectionType = projectionType;
            this.positions = positions;
            this.colors = colors;

            vboIdList = new ArrayList<>();

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            int vboId = glGenBuffers();
            vboIdList.add(vboId);
            FloatBuffer positionsBuffer = stack.callocFloat(positions.length);
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

            vboId = glGenBuffers();
            vboIdList.add(vboId);
            FloatBuffer colorsBuffer = stack.callocFloat(colors.length);
            colorsBuffer.put(0, colors);
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(1);
            glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }
    }

    public void cleanup() {
        vboIdList.forEach(GL30::glDeleteBuffers);
        glDeleteVertexArrays(vaoId);
    }

    public final int getVaoId() {
        return vaoId;
    }

    public ProjectionType getProjectionType() {
        return projectionType;
    }

    public List<Integer> getVboIdList() {
        return vboIdList;
    }

    public float[] getPositions() {
        return positions;
    }

    public float[] getColors() {
        return colors;
    }
}
