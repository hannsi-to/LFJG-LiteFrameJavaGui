package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.shader.ShaderProgram;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GLObjectCache {
    private List<GLObject> glObjects;
    private final FrameBuffer frameBuffer;

    public GLObjectCache(Vector2f resolution) {
        glObjects = new ArrayList<>();

        frameBuffer = new FrameBuffer(resolution);
        frameBuffer.createFrameBuffer();
        frameBuffer.createShaderProgram();
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

        frameBuffer.drawFrameBuffer();
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
}
