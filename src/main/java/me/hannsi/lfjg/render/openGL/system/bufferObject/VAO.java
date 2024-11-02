package me.hannsi.lfjg.render.openGL.system.bufferObject;

import org.lwjgl.opengl.GL20;

public class VAO {
    private final VBO vbo;
    private final int usage;

    public VAO(VBO vbo, int usage) {
        this.vbo = vbo;
        this.usage = usage;
    }

    public VAO(VBO vbo) {
        this(vbo, GL20.GL_STATIC_DRAW);
    }

    public void setVAOData() {
        bindBuffer();
        bufferData();
        unBindBuffer();
    }

    public void bufferData() {
        GL20.glBufferData(GL20.GL_ARRAY_BUFFER, vbo.getFloatBuffer(), usage);
    }

    public void bindBuffer() {
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vbo.getVertexBufferObjectHandle());
    }

    public void unBindBuffer() {
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
    }

    public void deleteBuffer() {
        GL20.glDeleteBuffers(vbo.getVertexBufferObjectHandle());
    }

    public VBO getVbo() {
        return vbo;
    }
}
