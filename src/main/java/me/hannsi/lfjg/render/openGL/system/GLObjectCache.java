package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;

import java.util.ArrayList;
import java.util.List;

public class GLObjectCache {
    private List<GLObject> glObjects;

    public GLObjectCache() {
        this.glObjects = new ArrayList<>();
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
