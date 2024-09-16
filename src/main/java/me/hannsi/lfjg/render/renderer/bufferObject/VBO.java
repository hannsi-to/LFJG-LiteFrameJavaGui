package me.hannsi.lfjg.render.renderer.bufferObject;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public class VBO {
    private FloatBuffer floatBuffer;
    private int vertexBufferObjectHandle;
    private int vertices;
    private int size;

    public VBO(int vertices,int size){
        if(vertices == -1){
            vertices = 5;
        }

        this.vertices = vertices;
        this.size = size;

        this.floatBuffer = BufferUtils.createFloatBuffer(vertices * size);
    }

    public void put(float value){
        if(!this.floatBuffer.hasRemaining()){
            this.vertices++;
            FloatBuffer newBuffer = BufferUtils.createFloatBuffer(vertices * size);

            this.floatBuffer.flip();
            newBuffer.put(this.floatBuffer);
            this.floatBuffer = newBuffer;
        }

        this.floatBuffer.put(value);
    }

    public void put(float... values){
        for(float value : values){
            put(value);
        }
    }

    public void genVertexBufferObject(){
        this.floatBuffer.flip();
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
