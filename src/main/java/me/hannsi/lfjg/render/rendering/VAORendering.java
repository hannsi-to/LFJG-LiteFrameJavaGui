package me.hannsi.lfjg.render.rendering;

import me.hannsi.lfjg.render.renderer.bufferObject.VAO;
import me.hannsi.lfjg.util.ImageLoader;
import me.hannsi.lfjg.util.ResourcesLocation;
import me.hannsi.lfjg.util.TextureLoader;
import me.hannsi.lfjg.util.type.types.DrawType;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class VAORendering {
    private final VAO vertex;
    private VAO color = null;
    private VAO texture = null;
    private int textureId = -1;
    private ResourcesLocation texturePath;

    public VAORendering(VAO vertex, VAO color,VAO texture) {
        this.vertex = vertex;
        this.color = color;
        this.texture = texture;
    }

    public VAORendering(VAO vertex, VAO color) {
        this.vertex = vertex;
        this.color = color;
    }

    public VAORendering(VAO vertex) {
        this.vertex = vertex;
    }

    private void genVertexBufferObjects(){
        vertex.getVbo().genVertexBufferObject();
        if(color != null){
            color.getVbo().genVertexBufferObject();
        }
        if(texture != null){
            texture.getVbo().genVertexBufferObject();
        }
    }

    private void enableTargets(){
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        if(texture != null){
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
    }

    private void setVAODatum(){
        vertex.setVAOData();
        if(color != null) {
            color.setVAOData();
        }
        if(texture != null){
            texture.setVAOData();
        }
    }

    private void glPointers(){
        vertex.bindBuffer();
        GL11.glVertexPointer(vertex.getVbo().getSize(),GL11.GL_FLOAT,0,0);
        if(color != null) {
            color.bindBuffer();
            GL11.glColorPointer(color.getVbo().getSize(), GL11.GL_FLOAT, 0, 0);
        }
        if(texture != null){
            texture.bindBuffer();
            GL11.glTexCoordPointer(texture.getVbo().getSize(),GL11.GL_FLOAT,0,0);
        }
    }

    private void glEnableClientStateCaps(){
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        if(color != null) {
            GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
        }
        if(texture != null){
            GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        }
    }

    private void glDisableClientStateCaps(){
        if(texture != null){
            GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        }
        if(color != null) {
            GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
        }
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
    }

    private void deleteBuffers(){
        vertex.deleteBuffer();
        if(color != null) {
            color.deleteBuffer();
        }
        if(texture != null){
            texture.deleteBuffer();
        }
    }

    private void disableTargets(){
        if(texture != null){
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDeleteTextures(textureId);
        }
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void genTextureId(){
        if(textureId == -1){
            IntBuffer width = BufferUtils.createIntBuffer(1);
            IntBuffer height = BufferUtils.createIntBuffer(1);
            IntBuffer channels = BufferUtils.createIntBuffer(1);
            ByteBuffer image = ImageLoader.loadImage(texturePath,width,height,channels);
            textureId = TextureLoader.createTexture(image, width.get(0), height.get(0));
            STBImage.stbi_image_free(image);
        }
    }

    public void drawArrays(DrawType drawType){
        if(texture != null){
            genTextureId();
        }

        genVertexBufferObjects();

        GL11.glPushMatrix();

        enableTargets();

        setVAODatum();

        glPointers();

        glEnableClientStateCaps();

        GL11.glDrawArrays(drawType.getId(),0,vertex.getVbo().getVertices());

        glDisableClientStateCaps();

        deleteBuffers();

        disableTargets();

        GL11.glPopMatrix();
    }

    public VAO getVertex() {
        return vertex;
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

    public ResourcesLocation getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(ResourcesLocation texturePath) {
        this.texturePath = texturePath;
    }
}
