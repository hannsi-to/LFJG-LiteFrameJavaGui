package me.hannsi.lfjg.render.openGL.renderer.bufferObject;

import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.math.vertex.vector.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public class VBO {
    private final int size;
    private FloatBuffer floatBuffer;
    private int vertexBufferObjectHandle = -1;
    private int vertices;

    public VBO(int vertices, int size) {
        if (vertices == -1) {
            vertices = 5;
        }

        this.vertices = vertices;
        this.size = size;

        this.floatBuffer = BufferUtils.createFloatBuffer(vertices * size);
    }

    public void put(float value) {
        if (!this.floatBuffer.hasRemaining()) {
            this.vertices++;
            FloatBuffer newBuffer = BufferUtils.createFloatBuffer(vertices * size);

            this.floatBuffer.flip();
            newBuffer.put(this.floatBuffer);
            this.floatBuffer = newBuffer;
        }

        this.floatBuffer.put(value);
    }

    public void put(Vector2f vector2f) {
        put(vector2f.x);
        put(vector2f.y);
    }

    public void put(Color color) {
        put(color.getRedF());
        put(color.getGreenF());
        put(color.getBlueF());
        put(color.getAlphaF());
    }

    public void put(float... values) {
        for (float value : values) {
            put(value);
        }
    }

    public void flip() {
        this.floatBuffer.flip();
    }

    public void genVertexBufferObject() {
        flip();
        this.vertexBufferObjectHandle = GL20.glGenBuffers();
    }

    public FloatBuffer getFloatBuffer() {
        return floatBuffer;
    }

    public int getVertexBufferObjectHandle() {
        return vertexBufferObjectHandle;
    }

    public int getVertices() {
        return vertices;
    }

    public int getSize() {
        return size;
    }
}
