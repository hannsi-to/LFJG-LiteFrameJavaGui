package me.hannsi.lfjg.render.rendering;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.renderer.bufferObject.VAO;
import me.hannsi.lfjg.utils.graphics.GL11Util;
import me.hannsi.lfjg.utils.image.ImageData;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.image.TextureLoader;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.lwjgl.opengl.GL11;

public class VAORendering {
    private final Frame frame;
    private final GL11Util gl11Util;
    private VAO vertex;
    private VAO color;
    private VAO texture;

    private int textureId = -1;
    private ResourcesLocation texturePath;
    private ImageData imageData;

    public VAORendering(Frame frame) {
        this.frame = frame;
        this.gl11Util = new GL11Util();
    }

    private void genVertexBufferObjects() {
        if (vertex != null && vertex.getVbo().getVertexBufferObjectHandle() == -1) {
            vertex.getVbo().genVertexBufferObject();
        }

        if (color != null && color.getVbo().getVertexBufferObjectHandle() == -1) {
            color.getVbo().genVertexBufferObject();
        }
        if (texture != null && texture.getVbo().getVertexBufferObjectHandle() == -1) {
            texture.getVbo().genVertexBufferObject();
        }
    }

    private void addTargets() {
        gl11Util.addGL11Target(GL11.GL_BLEND);
        if (texture != null) {
            gl11Util.addGL11Target(GL11.GL_TEXTURE_2D);
        }
    }

    private void enableTargets() {
        gl11Util.enableTargets();

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        if (texture != null) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        }
    }

    private void setVAODatum() {
        vertex.setVAOData();
        if (color != null) {
            color.setVAOData();
        }
        if (texture != null) {
            texture.setVAOData();
        }
    }

    private void glPointers() {
        vertex.bindBuffer();
        GL11.glVertexPointer(vertex.getVbo().getSize(), GL11.GL_FLOAT, 0, 0);
        if (color != null) {
            color.bindBuffer();
            GL11.glColorPointer(color.getVbo().getSize(), GL11.GL_FLOAT, 0, 0);
        }
        if (texture != null) {
            texture.bindBuffer();
            GL11.glTexCoordPointer(texture.getVbo().getSize(), GL11.GL_FLOAT, 0, 0);
        }
    }

    private void glEnableClientStateCaps() {
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        if (color != null) {
            GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
        }
        if (texture != null) {
            GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        }
    }

    private void glDisableClientStateCaps() {
        if (texture != null) {
            GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        }
        if (color != null) {
            GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
        }
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
    }

    private void unBindBuffers() {
        vertex.unBindBuffer();

        if (color != null) {
            color.unBindBuffer();
        }

        if (texture != null) {
            texture.unBindBuffer();
        }
    }

    private void disableTargets() {
        if (texture != null) {
            GL11.glDeleteTextures(textureId);
        }

        gl11Util.disableTargets();
        gl11Util.finish();
    }

    private void genTextureId() {
        if (textureId == -1) {
            this.imageData = new ImageData(frame, texturePath);
            textureId = TextureLoader.createTexture(imageData.getByteBuffer(), imageData.getMat().cols(), imageData.getMat().rows());
        }
    }

    public void drawArrays(DrawType drawType) {
        genVertexBufferObjects();

        setVAODatum();

        if (texture != null) {
            genTextureId();
        }

        addTargets();
        enableTargets();
        glPointers();
        glEnableClientStateCaps();

        GL11.glDrawArrays(drawType.getId(), 0, vertex.getVbo().getVertices());

        glDisableClientStateCaps();
        unBindBuffers();
        disableTargets();
    }

    public VAO getVertex() {
        return vertex;
    }

    public void setVertex(VAO vertex) {
        this.vertex = vertex;
    }

    public VAO getColor() {
        return color;
    }

    public void setColor(VAO color) {
        this.color = color;
    }

    public VAO getTexture() {
        return texture;
    }

    public void setTexture(VAO texture) {
        this.texture = texture;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public Frame getFrame() {
        return frame;
    }

    public ResourcesLocation getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(ResourcesLocation texturePath) {
        if (this.texturePath == null || this.texturePath != texturePath) {
            this.texturePath = texturePath;
        }
    }

    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }
}
