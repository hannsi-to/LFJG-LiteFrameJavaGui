package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GLObjectCache {
    private final FrameBuffer frameBuffer;
    private final Mesh mesh;
    private final Vector2f resolution;
    private final VAORendering vaoRendering;
    private final ShaderProgram shaderProgramFBO;
    private final ResourcesLocation vertexShaderFBO;
    private final ResourcesLocation fragmentShaderFBO;
    private List<GLObject> glObjects;

    public GLObjectCache(Vector2f resolution) {
        this.glObjects = new ArrayList<>();

        this.frameBuffer = new FrameBuffer();
        this.frameBuffer.createFrameBuffer((int) resolution.x(), (int) resolution.y());
        this.resolution = resolution;

        float[] positions = new float[]{-1, -1, 1, -1, 1, 1, -1, 1};

        float[] uvs = new float[]{0, 1, 1, 1, 1, 0, 0, 0};

        mesh = new Mesh(ProjectionType.OrthographicProjection, positions, null, uvs);

        shaderProgramFBO = new ShaderProgram();
        vertexShaderFBO = new ResourcesLocation("shader/frameBuffer/vertexShader.vsh");
        fragmentShaderFBO = new ResourcesLocation("shader/frameBuffer/FragmentShader.fsh");
        shaderProgramFBO.createVertexShader(vertexShaderFBO);
        shaderProgramFBO.createFragmentShader(fragmentShaderFBO);
        shaderProgramFBO.link();

        vaoRendering = new VAORendering();
    }

    public void createCache(GLObject glObject) {
        glObjects.add(glObject);
    }

    public void draw() {
        frameBuffer.bindFrameBuffer();

        for (GLObject glObject : glObjects) {
            glObject.draw();
        }

        frameBuffer.unbindFrameBuffer();

        shaderProgramFBO.bind();

        if (mesh.getTexture() != null) {
            shaderProgramFBO.setUniform1i("textureSampler", 1);
        }

        frameBuffer.bindTexture();
        vaoRendering.draw(mesh);
        frameBuffer.unbindTexture();

        shaderProgramFBO.unbind();
    }

    public void cleanup() {
        for (GLObject glObject : glObjects) {
            glObject.cleanup();
        }
    }

    public List<GLObject> getGlObjects() {
        return glObjects;
    }

    public void setGlObjects(List<GLObject> glObjects) {
        this.glObjects = glObjects;
    }

    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Vector2f getResolution() {
        return resolution;
    }

    public VAORendering getVaoRendering() {
        return vaoRendering;
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
