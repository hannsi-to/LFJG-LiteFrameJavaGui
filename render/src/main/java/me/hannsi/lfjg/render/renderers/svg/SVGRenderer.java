package me.hannsi.lfjg.render.renderers.svg;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class SVGRenderer {
    private final VAORendering vaoRendering;
    private final ShaderProgram shaderProgramFBO;
    private final Location vertexShaderFBO;
    private final Location fragmentShaderFBO;
    private Mesh mesh;

    public SVGRenderer() {
        vaoRendering = new VAORendering();

        shaderProgramFBO = new ShaderProgram();
        vertexShaderFBO = Location.fromResource("shader/VertexShader.vsh");
        fragmentShaderFBO = Location.fromResource("shader/frameBuffer/FragmentShader.fsh");
    }

    public void cleanup() {

    }

    public void init() {
        shaderProgramFBO.createVertexShader(vertexShaderFBO);
        shaderProgramFBO.createFragmentShader(fragmentShaderFBO);
        shaderProgramFBO.link();
    }

    public void flush(int textureId, int textureUnit) {
        shaderProgramFBO.bind();

        shaderProgramFBO.setUniform("projectionMatrix", UploadUniformType.ON_CHANGE, Core.projection2D.getProjMatrix());
        shaderProgramFBO.setUniform("modelMatrix", UploadUniformType.PER_FRAME, new Matrix4f());
        shaderProgramFBO.setUniform("viewMatrix", UploadUniformType.PER_FRAME, new Matrix4f());
        shaderProgramFBO.setUniform("textureSampler", UploadUniformType.ONCE, textureUnit);

        GLStateCache.enable(GL_BLEND);
        GLStateCache.disable(GL_DEPTH_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glActiveTexture(GL_TEXTURE0 + textureUnit);
        glBindTexture(GL_TEXTURE_2D, textureId);
        vaoRendering.draw(mesh);
        glBindTexture(GL_TEXTURE_2D, 0);

        shaderProgramFBO.unbind();
    }

    public void addVertex(float x, float y, float width, float height) {
        float[] positions = new float[]{x, y, x + width, y, x + width, y + height, x, y + height};
        float[] uvs = new float[]{1, 1, 0, 1, 0, 0, 1, 0};

        mesh = Mesh.createMesh()
                .projectionType(ProjectionType.ORTHOGRAPHIC_PROJECTION)
                .createBufferObject2D(positions, null, uvs)
                .builderClose();
    }

    public VAORendering getVaoRendering() {
        return vaoRendering;
    }

    public ShaderProgram getShaderProgramFBO() {
        return shaderProgramFBO;
    }

    public Location getVertexShaderFBO() {
        return vertexShaderFBO;
    }

    public Location getFragmentShaderFBO() {
        return fragmentShaderFBO;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
}
