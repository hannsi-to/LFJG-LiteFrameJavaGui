package me.hannsi.lfjg.render.openGL.effect.system;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.FrameBuffer;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.joml.Vector2f;

public class EffectBase {
    private final FrameBuffer frameBuffer;
    private int id;
    private String name;
    private Class<GLObject>[] ignoreGLObject;

    @SafeVarargs
    public EffectBase(Vector2f resolution, ResourcesLocation vertexPath, ResourcesLocation fragmentPath, int id, String name, Class<GLObject>... ignoreGLObject) {
        this.id = id;
        this.name = name;
        this.ignoreGLObject = ignoreGLObject;

        frameBuffer = new FrameBuffer(resolution);

        if (vertexPath != null) {
            frameBuffer.setVertexShaderFBO(vertexPath);
        }

        if (fragmentPath != null) {
            frameBuffer.setFragmentShaderFBO(fragmentPath);
        }

        frameBuffer.createFrameBuffer();
        frameBuffer.createShaderProgram();
    }

    @SafeVarargs
    public EffectBase(Vector2f resolution, ResourcesLocation path, boolean isFragmentPath, int id, String name, Class<GLObject>... ignoreGLObject) {
        this(resolution, isFragmentPath ? null : path, isFragmentPath ? path : null, id, name, ignoreGLObject);
    }

    @SafeVarargs
    public EffectBase(Vector2f resolution, int id, String name, Class<GLObject>... ignoreGLObject) {
        this(resolution, null, null, id, name, ignoreGLObject);
    }

    public void draw(GLObject baseGLObject) {

    }

    public void pop(GLObject baseGLObject) {

    }

    public void push(GLObject baseGLObject) {

    }

    public void frameBuffer(EffectCache effectCache, int oldIndex, GLObject glObject) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<GLObject>[] getIgnoreGLObject() {
        return ignoreGLObject;
    }

    public void setIgnoreGLObject(Class<GLObject>[] ignoreGLObject) {
        this.ignoreGLObject = ignoreGLObject;
    }

    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }
}
