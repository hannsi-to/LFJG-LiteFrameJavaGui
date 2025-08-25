package me.hannsi.lfjg.render.effect.system;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;

public class EffectBase {
    private final Location vertexPath;
    private final Location fragmentPath;
    private final String name;

    private FrameBuffer frameBuffer;
    private int id;
    private Class<GLObject>[] ignoreGLObject;

    @SafeVarargs
    public EffectBase(Location vertexPath, Location fragmentPath, int id, String name, Class<GLObject>... ignoreGLObject) {
        this.id = id;
        this.name = name;
        this.ignoreGLObject = ignoreGLObject;
        this.vertexPath = vertexPath;
        this.fragmentPath = fragmentPath;
    }

    public EffectBase(Location path, boolean isFragmentPath, int id, String name) {
        this(isFragmentPath ? null : path, isFragmentPath ? path : null, id, name);
    }

    @SafeVarargs
    public EffectBase(Location path, boolean isFragmentPath, int id, String name, Class<GLObject>... ignoreGLObject) {
        this(isFragmentPath ? null : path, isFragmentPath ? path : null, id, name, ignoreGLObject);
    }

    @SafeVarargs
    public EffectBase(int id, String name, Class<GLObject>... ignoreGLObject) {
        this(null, null, id, name, ignoreGLObject);
    }

    public void create(GLObject glObject) {
        frameBuffer = new FrameBuffer(glObject.getTransform().getX(), glObject.getTransform().getY(), glObject.getTransform().getWidth(), glObject.getTransform().getHeight());

        if (vertexPath != null) {
//            frameBuffer.setVertexShaderFBO(vertexPath);
        }

        if (fragmentPath != null) {
//            frameBuffer.setFragmentShaderFBO(fragmentPath);
        }

        frameBuffer.createFrameBuffer();
        frameBuffer.createMatrix();
    }

    public void create(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;

        if (vertexPath != null) {
//            frameBuffer.setVertexShaderFBO(vertexPath);
        }

        if (fragmentPath != null) {
//            frameBuffer.setFragmentShaderFBO(fragmentPath);
        }

        frameBuffer.createFrameBuffer();
        frameBuffer.createMatrix();
    }

    public void cleanup() {
        if (frameBuffer != null) {
            frameBuffer.cleanup();
        }
        ignoreGLObject = null;

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                id,
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public void draw(GLObject baseGLObject) {

    }

    public void pop(GLObject baseGLObject) {

    }

    public void push(GLObject baseGLObject) {

    }

    public void frameBufferPush(GLObject baseGLObject) {

    }

    public void frameBufferPop(GLObject baseGLObject) {

    }

    public void frameBuffer(GLObject baseGLObject) {

    }

    public void setUniform(GLObject baseGLObject) {

    }

    public Location getVertexPath() {
        return vertexPath;
    }

    public Location getFragmentPath() {
        return fragmentPath;
    }

    public String getName() {
        return name;
    }

    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class<GLObject>[] getIgnoreGLObject() {
        return ignoreGLObject;
    }

    public void setIgnoreGLObject(Class<GLObject>[] ignoreGLObject) {
        this.ignoreGLObject = ignoreGLObject;
    }
}