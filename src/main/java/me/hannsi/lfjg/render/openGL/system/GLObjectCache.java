package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GLObjectCache {
    public static long glLatestObjectId;
    private List<GLObject> glObjects;

    public GLObjectCache(Vector2f resolution) {
        glObjects = new ArrayList<>();
    }

    public void createCache(GLObject glObject) {
        glObjects.add(glObject);
    }

    public void draw() {
        for (GLObject glObject : glObjects) {
            glObject.draw();
        }
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
}
