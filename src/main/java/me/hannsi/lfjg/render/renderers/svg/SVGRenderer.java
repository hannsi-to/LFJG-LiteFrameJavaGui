package me.hannsi.lfjg.render.renderers.svg;

import lombok.Getter;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

@Getter
public class SVGRenderer {
    private final VAORendering vaoRendering;
    private final ShaderProgram shaderProgramFBO;
    private final ResourcesLocation vertexShaderFBO;
    private final ResourcesLocation fragmentShaderFBO;
    private Mesh mesh;

    public SVGRenderer() {
        vaoRendering = new VAORendering();

        shaderProgramFBO = new ShaderProgram();
        vertexShaderFBO = new ResourcesLocation("shader/frameBuffer/VertexShader.vsh");
        fragmentShaderFBO = new ResourcesLocation("shader/frameBuffer/FragmentShader.fsh");
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

        shaderProgramFBO.setUniform("projectionMatrix", LFJGContext.projection.getProjMatrix());
        shaderProgramFBO.setUniform("modelMatrix", new Matrix4f());
        shaderProgramFBO.setUniform("viewMatrix", new Matrix4f());
        shaderProgramFBO.setUniform1i("textureSampler", textureUnit);

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

        mesh = Mesh.initMesh()
                .projectionType(ProjectionType.ORTHOGRAPHIC_PROJECTION)
                .createBufferObjects2D(positions, null, uvs)
                .builderClose();
    }
}
