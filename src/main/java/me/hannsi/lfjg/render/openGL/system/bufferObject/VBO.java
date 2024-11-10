package me.hannsi.lfjg.render.openGL.system.bufferObject;

import me.hannsi.lfjg.utils.color.Color;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;

public class VBO {
    private final int vboId;
    private int location;
    private int vertices;
    private int size;
    private int usage;
    private FloatBuffer floatBuffer;

    public VBO(int location, int vertices, int size, int usage) {
        this.vboId = genBuffers();

        this.vertices = vertices;
        this.size = size;
        this.usage = usage;

        this.location = location;

        this.floatBuffer = BufferUtils.createFloatBuffer(vertices * size);
    }

    public VBO(int location, int vertices, int size) {
        this(location, vertices, size, GL30.GL_STATIC_DRAW);
    }

    public void flip() {
        this.floatBuffer.flip();
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

    public void enableVertexAttribArray() {
        GL30.glEnableVertexAttribArray(this.location);
    }

    public void disableVertexAttribArray() {
        GL30.glDisableVertexAttribArray(this.location);
    }

    public void vertexAttribPointer() {
        GL30.glVertexAttribPointer(this.location, this.size, GL30.GL_FLOAT, false, 0, 0);
    }

    public void bufferData() {
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, this.floatBuffer, usage);
    }

    private int genBuffers() {
        return GL30.glGenBuffers();
    }

    public void bindBuffer() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.vboId);
    }

    public void unBindBuffer() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
    }

    public void deleteBuffers() {
        GL30.glDeleteBuffers(this.vboId);
    }

    public int getVboId() {
        return vboId;
    }

    public int getVertices() {
        return vertices;
    }

    public void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public FloatBuffer getFloatBuffer() {
        return floatBuffer;
    }

    public void setFloatBuffer(FloatBuffer floatBuffer) {
        this.floatBuffer = floatBuffer;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
