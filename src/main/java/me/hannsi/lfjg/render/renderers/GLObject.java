package me.hannsi.lfjg.render.renderers;

import lombok.Data;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.Id;
import me.hannsi.lfjg.render.animation.system.AnimationCache;
import me.hannsi.lfjg.render.effect.system.EffectBase;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.type.types.BlendType;
import me.hannsi.lfjg.utils.type.types.BufferObjectType;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Represents an OpenGL object with various properties and methods for rendering.
 */
@Data
public class GLObject implements Cloneable {
    private String name;
    private long objectId;

    private VAORendering vaoRendering;
    private Mesh mesh;
    private FrameBuffer frameBuffer;
    private ShaderProgram shaderProgram;

    private Matrix4f viewMatrix;

    private EffectCache effectCache;
    private AnimationCache animationCache;
    private BlendType blendType;
    private DrawType drawType;
    private float lineWidth;
    private float pointSize;

    private float x;
    private float y;
    private float width;
    private float height;

    private Transform transform;

    /**
     * Constructs a new GLObject with the specified name.
     *
     * @param name the name of the GLObject
     */
    public GLObject(String name) {
        this.name = name;

        this.vaoRendering = null;
        this.mesh = null;
        this.frameBuffer = null;
        this.shaderProgram = null;

        this.viewMatrix = null;
        this.transform = new Transform();

        this.lineWidth = -1f;
        this.pointSize = -1f;
        this.blendType = null;
        this.drawType = null;
        this.objectId = ++Id.latestGLObjectId;
    }

    /**
     * Cleans up the resources used by the GLObject.
     */
    public void cleanup() {
        if (animationCache != null) {
            animationCache.cleanup(this);
        }
        if (effectCache != null) {
            effectCache.cleanup();
        }
        if (mesh != null) {
            mesh.cleanup();
        }
        if (frameBuffer != null) {
            frameBuffer.cleanup();
        }
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
        if (vaoRendering != null) {
            vaoRendering.cleanup();
        }

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                String.valueOf(objectId),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    /**
     * Initializes the GLObject by creating necessary OpenGL resources.
     */
    public void create(Location vertexShader, Location fragmentShader) {
        vaoRendering = new VAORendering();

        frameBuffer = new FrameBuffer();
        frameBuffer.createFrameBuffer();
        frameBuffer.createShaderProgram();

        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(vertexShader);
        shaderProgram.createFragmentShader(fragmentShader);
        shaderProgram.link();

        viewMatrix = new Matrix4f();

        blendType = BlendType.NORMAL;
    }

    /**
     * Draws the GLObject using the specified resolution and projection.
     */
    public void draw() {
        draw(true);
    }

    public void draw(boolean autoDraw) {
        bindResources();
        setupRenderState();
        uploadUniforms();

        vaoRendering.draw(this);

        if (effectCache != null) {
            effectCache.push(this);
        }
        uploadCache();
        if (effectCache != null) {
            effectCache.pop(this);
        }

        frameBuffer.unbindFrameBuffer();

        if (autoDraw) {
            drawFrameBuffer();
        }
    }

    private void uploadCache() {
        if (effectCache != null) {
            effectCache.frameBuffer(this);
        }
        if (animationCache != null) {
            animationCache.loop(this);
        }
    }

    private void setupRenderState() {
        if (lineWidth != -1f) {
            glLineWidth(lineWidth);
        }
        if (pointSize != -1f) {
            glPointSize(pointSize);
        }

        GLStateCache.setBlendFunc(blendType.getSfactor(), blendType.getDfactor());
        GLStateCache.setBlendEquation(blendType.getEquation());
        GLStateCache.enable(GL_BLEND);
        GLStateCache.disable(GL_DEPTH_TEST);
    }

    private void uploadUniforms() {
        shaderProgram.setUniformMatrix4fv("projectionMatrix", LFJGContext.projection.getProjMatrix());
        shaderProgram.setUniformMatrix4fv("modelMatrix", transform.getModelMatrix());
        shaderProgram.setUniformMatrix4fv("viewMatrix", viewMatrix);
        shaderProgram.setUniform2i("resolution", LFJGContext.frameBufferSize);
        if (mesh.getVboIds().get(BufferObjectType.TEXTURE_BUFFER) != null) {
            shaderProgram.setUniform1i("textureSampler", 0);
        }
    }

    private void bindResources() {
        frameBuffer.bindFrameBuffer();
        shaderProgram.bind();
    }

    public void drawFrameBuffer() {
        if (effectCache != null) {
            effectCache.getEndFrameBuffer().drawFrameBuffer();
        } else {
            frameBuffer.drawFrameBuffer();
        }
    }

    public GLObject copy(String objectName) {
        GLObject glObject;
        try {
            glObject = (GLObject) clone();
            glObject.setName(objectName);
            glObject.setObjectId(++Id.latestGLObjectId);

            new LogGenerator(
                    getClass().getSimpleName() + " Debug Message",
                    "Source: " + getClass().getName(),
                    "Type: Copy",
                    "ID: " + glObject.getObjectId(),
                    "Severity: Info",
                    "Message: Create object copy: " + glObject.getName()
            ).logging(DebugLevel.INFO);
        } catch (Exception e) {
            new LogGenerator(
                    getClass().getSimpleName() + " Debug Message",
                    "Source: " + getClass().getName(),
                    "Type: Copy",
                    "ID: " + this.getObjectId(),
                    "Severity: Error",
                    "Message: Failed to create object copy: " + this.getName()
            ).logging(DebugLevel.ERROR);
            throw new RuntimeException(e);
        }

        return glObject;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Gets the effect base at the specified index.
     *
     * @param index the index of the effect base
     * @return the effect base at the specified index
     */
    public EffectBase getEffectBase(int index) {
        return effectCache.getEffectBase(index);
    }

    @Deprecated
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the VAORendering of the GLObject.
     *
     * @param vaoRendering the VAORendering to set
     * @return the GLObject instance
     */
    public GLObject setVaoRendering(VAORendering vaoRendering) {
        this.vaoRendering = vaoRendering;

        return this;
    }

    /**
     * Sets the Mesh of the GLObject.
     *
     * @param mesh the Mesh to set
     * @return the GLObject instance
     */
    public GLObject setMesh(Mesh mesh) {
        this.mesh = mesh;

        return this;
    }

    /**
     * Sets the ShaderProgram of the GLObject.
     *
     * @param shaderProgram the ShaderProgram to set
     * @return the GLObject instance
     */
    public GLObject setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;

        return this;
    }

    /**
     * Sets the blend type of the GLObject.
     *
     * @param blendType the blend type to set
     * @return the GLObject instance
     */
    public GLObject setBlendType(BlendType blendType) {
        this.blendType = blendType;

        return this;
    }

    /**
     * Sets the draw type of the GLObject.
     *
     * @param drawType the draw type to set
     * @return the GLObject instance
     */
    public GLObject setDrawType(DrawType drawType) {
        this.drawType = drawType;

        return this;
    }

    /**
     * Sets the line width of the GLObject.
     *
     * @param lineWidth the line width to set
     * @return the GLObject instance
     */
    public GLObject setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;

        return this;
    }

    /**
     * Sets the point size of the GLObject.
     *
     * @param pointSize the point size to set
     * @return the GLObject instance
     */
    public GLObject setPointSize(float pointSize) {
        this.pointSize = pointSize;

        return this;
    }

    @Deprecated
    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }
}