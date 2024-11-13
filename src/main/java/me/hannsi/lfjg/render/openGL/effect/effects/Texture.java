package me.hannsi.lfjg.render.openGL.effect.effects;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.effect.system.EffectBase;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.utils.image.ImageData;
import me.hannsi.lfjg.utils.image.TextureLoader;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL30;

public class Texture extends EffectBase {
    private int textureId = -1;
    private ResourcesLocation texturePath;
    private ImageData imageData;
    private float x1;
    private float y1;
    private float x2;
    private float y2;

    public Texture(ResourcesLocation texturePath, float x1, float y1, float x2, float y2) {
        super(3, "Texture", (Class<GLPolygon>) null);

        this.texturePath = texturePath;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void rendering(Frame frame, GLPolygon basePolygon) {
        basePolygon.put().uv(new Vector2f(x1, y1)).end();
        basePolygon.put().uv(new Vector2f(x2, y1)).end();
        basePolygon.put().uv(new Vector2f(x2, y2)).end();
        basePolygon.put().uv(new Vector2f(x1, y2)).end();

        super.rendering(frame, basePolygon);
    }

    @Override
    public void push(Frame frame, GLPolygon basePolygon) {
        genTextureId();

        basePolygon.getVaoRendering().getShaderProgram().bind();
        basePolygon.getVaoRendering().getShaderProgram().setUniform1i("texture_sampler", 0);
        basePolygon.getVaoRendering().getShaderProgram().unbind();

        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);

        basePolygon.getVaoRendering().getGlUtil().addGLTarget(GL30.GL_TEXTURE_2D);

        super.push(frame, basePolygon);
    }

    @Override
    public void pop(Frame frame, GLPolygon basePolygon) {


        super.pop(frame, basePolygon);
    }

    private void genTextureId() {
        if (textureId == -1) {
            imageData = new ImageData(texturePath);
            textureId = TextureLoader.createTexture(imageData.getByteBuffer(), imageData.getMat().cols(), imageData.getMat().rows());
        }
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

    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }
}
