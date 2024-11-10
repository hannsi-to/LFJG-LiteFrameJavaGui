package me.hannsi.lfjg.render.openGL.system.bufferObject;

import org.lwjgl.opengl.GL30;

public class VAO {
    private final int vaoId;
    private VBO vertexVBO;
    private VBO colorVBO;
    private VBO textureVBO;

    public VAO() {
        this.vaoId = genVertexArrays();
    }

    public void deleteBuffers() {
        this.vertexVBO.deleteBuffers();

        if (this.colorVBO != null) {
            this.colorVBO.deleteBuffers();
        }

        if (this.textureVBO != null) {
            this.textureVBO.deleteBuffers();
        }
    }

    public void disableVertexAttribArray() {
        this.vertexVBO.disableVertexAttribArray();

        if (this.colorVBO != null) {
            this.colorVBO.disableVertexAttribArray();
        }

        if (this.textureVBO != null) {
            this.textureVBO.disableVertexAttribArray();
        }
    }

    public void enableVertexAttribArrays() {
        this.vertexVBO.enableVertexAttribArray();

        if (this.colorVBO != null) {
            this.colorVBO.enableVertexAttribArray();
        }

        if (this.textureVBO != null) {
            this.textureVBO.enableVertexAttribArray();
        }
    }

    public void configureAttributes() {
        this.vertexVBO.bindBuffer();
        this.vertexVBO.bufferData();
        this.vertexVBO.vertexAttribPointer();
        this.vertexVBO.unBindBuffer();

        if (this.colorVBO != null) {
            this.colorVBO.bindBuffer();
            this.colorVBO.bufferData();
            this.colorVBO.vertexAttribPointer();
            this.colorVBO.unBindBuffer();
        }

        if (this.textureVBO != null) {
            this.textureVBO.bindBuffer();
            this.textureVBO.bufferData();
            this.textureVBO.vertexAttribPointer();
            this.textureVBO.unBindBuffer();
        }
    }

    public void clearAttributes() {
        this.vertexVBO.unBindBuffer();

        if (this.colorVBO != null) {
            this.colorVBO.unBindBuffer();
        }

        if (this.textureVBO != null) {
            this.textureVBO.unBindBuffer();
        }
    }

    public void deleteVertexArrays() {
        GL30.glDeleteVertexArrays(this.vaoId);
    }

    public void bindVertexArray() {
        GL30.glBindVertexArray(this.vaoId);
    }

    public void unBindVertexArray() {
        GL30.glBindVertexArray(0);
    }

    public int genVertexArrays() {
        return GL30.glGenVertexArrays();
    }

    public int getVaoId() {
        return vaoId;
    }

    public VBO getVertexVBO() {
        return vertexVBO;
    }

    public void setVertexVBO(VBO vertexVBO) {
        this.vertexVBO = vertexVBO;
    }

    public VBO getColorVBO() {
        return colorVBO;
    }

    public void setColorVBO(VBO colorVBO) {
        this.colorVBO = colorVBO;
    }

    public VBO getTextureVBO() {
        return textureVBO;
    }

    public void setTextureVBO(VBO textureVBO) {
        this.textureVBO = textureVBO;
    }
}
