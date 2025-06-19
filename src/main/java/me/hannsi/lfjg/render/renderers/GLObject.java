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
import me.hannsi.lfjg.render.system.MeshBuilder;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.rendering.VAORendering;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import me.hannsi.lfjg.utils.type.types.BlendType;
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
    private MeshBuilder meshBuilder;
    private FrameBuffer frameBuffer;
    private ShaderProgram shaderProgram;

    private Matrix4f modelMatrix;
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

    private float centerX;
    private float centerY;

    private float angleX;
    private float angleY;
    private float angleZ;

    private float scaleX;
    private float scaleY;
    private float scaleZ;

    /**
     * Constructs a new GLObject with the specified name.
     *
     * @param name the name of the GLObject
     */
    public GLObject(String name) {
        this.name = name;

        this.vaoRendering = null;
        this.meshBuilder = null;
        this.frameBuffer = null;
        this.shaderProgram = null;

        this.modelMatrix = null;
        this.viewMatrix = null;

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
        if (meshBuilder != null) {
            meshBuilder.cleanup();
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
    public void create(FileLocation vertexShader, FileLocation fragmentShader) {
        vaoRendering = new VAORendering();

        frameBuffer = new FrameBuffer();
        frameBuffer.createFrameBuffer();
        frameBuffer.createShaderProgram();

        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(vertexShader);
        shaderProgram.createFragmentShader(fragmentShader);
        shaderProgram.link();

        modelMatrix = new Matrix4f();
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
        shaderProgram.setUniform("projectionMatrix", LFJGContext.projection.getProjMatrix());
        shaderProgram.setUniform("modelMatrix", modelMatrix);
        shaderProgram.setUniform("viewMatrix", viewMatrix);
        shaderProgram.setUniform("resolution", LFJGContext.frameBufferSize);
        if (meshBuilder.getTextures() != null) {
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

    public GLObject translate(float x, float y, float z) {
        centerX += x;
        centerY += y;
        modelMatrix.translate(x, y, z);

        return this;
    }

    public GLObject rotateXYZ(float angleX, float angleY, float angleZ) {
        this.angleX += angleX;
        this.angleY += angleY;
        this.angleZ += angleZ;
        modelMatrix.rotateXYZ(angleX, angleY, angleZ);

        return this;
    }

    public GLObject scale(float scaleX, float scaleY, float scaleZ) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
        modelMatrix.scale(scaleX, scaleY, scaleZ);

        return this;
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
     * @param meshBuilder the Mesh to set
     * @return the GLObject instance
     */
    public GLObject setMeshBuilder(MeshBuilder meshBuilder) {
        this.meshBuilder = meshBuilder;

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
     * Sets the model matrix of the GLObject.
     *
     * @param modelMatrix the model matrix to set
     * @return the GLObject instance
     */
    public GLObject setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;

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