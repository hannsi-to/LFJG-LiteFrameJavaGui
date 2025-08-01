package me.hannsi.test.sdf.msdf;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

public class TextRenderer {
    protected ShaderProgram shaderProgram;
    protected Mesh mesh;
    protected VAORendering vaoRendering;

    private Matrix4f viewMatrix = new Matrix4f();
    private Vector3f fontColor = new Vector3f(1, 1, 1);
    private Matrix4f modelMatrix = new Matrix4f();

    private MSDFTextureLoader msdfTextureLoader;
    private TextMeshBuilder textMeshBuilder;
    private char c;
    private Vector2f pos;
    private int size;

    TextRenderer() {
        this.shaderProgram = new ShaderProgram();
        this.shaderProgram.createVertexShader(Location.fromResource("shader/msdf/VertexShader.vsh"));
        this.shaderProgram.createFragmentShader(Location.fromResource("shader/msdf/FragmentShader.fsh"));
        this.shaderProgram.link();

        this.vaoRendering = new VAORendering();
        this.mesh = Mesh.createMesh()
                .projectionType(ProjectionType.ORTHOGRAPHIC_PROJECTION)
                .useElementBufferObject(false);
    }

    public static TextRenderer createTextRender() {
        return new TextRenderer();
    }

    public TextRenderer msdfTextureLoader(MSDFTextureLoader msdfTextureLoader) {
        this.msdfTextureLoader = msdfTextureLoader;
        return this;
    }

    public TextRenderer textMeshBuilder(TextMeshBuilder textMeshBuilder) {
        this.textMeshBuilder = textMeshBuilder;
        return this;
    }

    public TextRenderer c(char c) {
        this.c = c;
        return this;
    }

    public TextRenderer pos(Vector2f pos) {
        this.pos = pos;
        return this;
    }

    public TextRenderer size(int size) {
        this.size = size;
        return this;
    }

    public TextRenderer init() {
        MSDFFont.Glyph glyph = textMeshBuilder.getGlyphMap().get((int) c);
        if (glyph == null) {
            throw new IllegalArgumentException(c + " glyph not found in map!");
        }

        var plane = glyph.getPlaneBounds();
        var atlas = glyph.getAtlasBounds();
        int texWidth = textMeshBuilder.getMsdfFont().getAtlas().getWidth();
        int texHeight = textMeshBuilder.getMsdfFont().getAtlas().getHeight();

        float x0 = (float) plane.getLeft();
        float y0 = (float) plane.getBottom();
        float x1 = (float) plane.getRight();
        float y1 = (float) plane.getTop();

        float u0 = (float) (atlas.getLeft() / texWidth);
        float v0 = (float) (atlas.getBottom() / texHeight);
        float u1 = (float) (atlas.getRight() / texWidth);
        float v1 = (float) (atlas.getTop() / texHeight);

        float[] positions = {
                x0, y0, x1, y0, x1, y1,
                x1, y1, x0, y1, x0, y0
        };

        float[] uvs = {
                u0, v0, u1, v0, u1, v1,
                u1, v1, u0, v1, u0, v0
        };

        mesh.createBufferObject2D(positions, null, uvs)
                .builderClose();

        return this;
    }

    public TextRenderer draw() {
        shaderProgram.bind();

        shaderProgram.setUniform("projectionMatrix", UploadUniformType.ON_CHANGE, Core.projection2D.getProjMatrix());
        shaderProgram.setUniform("viewMatrix", UploadUniformType.PER_FRAME, viewMatrix);
        shaderProgram.setUniform("modelMatrix", UploadUniformType.PER_FRAME, modelMatrix.translate(pos.x(), pos.y(), 0).scale(size, size, 1));
        shaderProgram.setUniform("uFontAtlas", UploadUniformType.ONCE, 0);
        shaderProgram.setUniform("uFontColor", UploadUniformType.ON_CHANGE, fontColor);
        shaderProgram.setUniform("uDistanceRange", UploadUniformType.ONCE, (float) textMeshBuilder.getMsdfFont().getAtlas().getDistanceRange());

        GLStateCache.enable(GL_BLEND);
        GLStateCache.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GLStateCache.enable(GL_TEXTURE_2D);
        GLStateCache.activeTexture(GL_TEXTURE0);
        GLStateCache.bindTexture(GL_TEXTURE_2D, msdfTextureLoader.textureId);

        vaoRendering.draw(mesh, GL_TRIANGLES);

        shaderProgram.unbind();

        modelMatrix.identity();

        return this;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public Vector3f getFontColor() {
        return fontColor;
    }

    public void setFontColor(Vector3f fontColor) {
        this.fontColor = fontColor;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    public MSDFTextureLoader getMsdfTextureLoader() {
        return msdfTextureLoader;
    }

    public void setMsdfTextureLoader(MSDFTextureLoader msdfTextureLoader) {
        this.msdfTextureLoader = msdfTextureLoader;
    }

    public TextMeshBuilder getTextMeshBuilder() {
        return textMeshBuilder;
    }

    public void setTextMeshBuilder(TextMeshBuilder textMeshBuilder) {
        this.textMeshBuilder = textMeshBuilder;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
