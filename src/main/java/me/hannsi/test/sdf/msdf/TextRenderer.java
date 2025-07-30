package me.hannsi.test.sdf.msdf;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class TextRenderer {
    protected ShaderProgram shaderProgram;
    protected Mesh mesh;
    protected VAORendering vaoRendering;
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
        this.mesh = Mesh.createMesh();
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

        float x0 = (float) glyph.getPlaneBounds().getLeft();
        float y0 = (float) glyph.getPlaneBounds().getBottom();
        float x1 = (float) glyph.getPlaneBounds().getRight();
        float y1 = (float) glyph.getPlaneBounds().getTop();

        int texWidth = textMeshBuilder.getMsdfFont().getAtlas().getWidth();
        int texHeight = textMeshBuilder.getMsdfFont().getAtlas().getHeight();

        float u0 = (float) (glyph.getAtlasBounds().getLeft() / texWidth);
        float v0 = (float) (glyph.getAtlasBounds().getBottom() / texHeight);
        float u1 = (float) (glyph.getAtlasBounds().getRight() / texWidth);
        float v1 = (float) (glyph.getAtlasBounds().getTop() / texHeight);

        float[] positions = {
                x0, y0,
                x1, y0,
                x1, y1,

                x1, y1,
                x0, y1,
                x0, y0
        };

        float[] uvs = {
                u0, v0,
                u1, v0,
                u1, v1,

                u1, v1,
                u0, v1,
                u0, v0
        };

        mesh.projectionType(ProjectionType.ORTHOGRAPHIC_PROJECTION)
                .useElementBufferObject(false)
                .createBufferObject2D(positions, null, uvs)
                .builderClose();

        return this;
    }

    public TextRenderer draw() {
        shaderProgram.bind();

        shaderProgram.setUniform("projectionMatrix", Core.projection2D.getProjMatrix());
        shaderProgram.setUniform("viewMatrix", new Matrix4f());
        shaderProgram.setUniform("modelMatrix", new Matrix4f().translate(pos.x(), pos.y(), 0).scale(size, size, 1));
        shaderProgram.setUniform("uFontAtlas", 0);
        shaderProgram.setUniform("uFontColor", new Vector3f(1, 1, 1));
        shaderProgram.setUniform("uDistanceRange", (float) textMeshBuilder.getMsdfFont().getAtlas().getDistanceRange());

        GLStateCache.enable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GLStateCache.enable(GL_TEXTURE_2D);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, msdfTextureLoader.textureId);

        vaoRendering.draw(mesh, GL_TRIANGLES);

        glBindTexture(GL_TEXTURE_2D, 0);

        shaderProgram.unbind();

        return this;
    }
}
