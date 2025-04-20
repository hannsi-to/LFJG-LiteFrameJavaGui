package me.hannsi.lfjg.render.openGL.renderers.svg;

import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.system.Mesh;
import me.hannsi.lfjg.render.openGL.system.rendering.VAORendering;
import me.hannsi.lfjg.render.openGL.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.graphics.GLUtil;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;

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

        GLUtil glUtil = new GLUtil();
        glUtil.addGLTarget(GL30.GL_DEPTH_TEST, true);
        glUtil.addGLTarget(GL30.GL_BLEND);

        glUtil.enableTargets();
        GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        GL30.glActiveTexture(GL30.GL_TEXTURE0 + textureUnit);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
        vaoRendering.draw(mesh);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);

        glUtil.disableTargets();
        glUtil.cleanup();

        shaderProgramFBO.unbind();
    }

    public void addVertex(float x, float y, float width, float height) {
        float[] positions = new float[]{x, y, x + width, y, x + width, y + height, x, y + height};
        float[] uvs = new float[]{1, 1, 0, 1, 0, 0, 1, 0};

        mesh = new Mesh(ProjectionType.ORTHOGRAPHIC_PROJECTION, positions, null, uvs);
    }

    public VAORendering getVaoRendering() {
        return vaoRendering;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public ShaderProgram getShaderProgramFBO() {
        return shaderProgramFBO;
    }

    public ResourcesLocation getVertexShaderFBO() {
        return vertexShaderFBO;
    }

    public ResourcesLocation getFragmentShaderFBO() {
        return fragmentShaderFBO;
    }
}
