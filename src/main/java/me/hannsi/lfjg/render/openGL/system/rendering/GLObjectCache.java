package me.hannsi.lfjg.render.openGL.system.rendering;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GLObjectCache {
    private List<GLObject> glObjects;

    public GLObjectCache(Vector2f resolution) {
        glObjects = new ArrayList<>();
    }

    public void createCache(GLObject glObject) {
        glObjects.add(glObject);

        LogGenerator logGenerator = new LogGenerator("GLObjectCache Debug Message", "Source: GLObjectCache", "Type: Cache Creation", "ID: " + glObject.getObjectId(), "Severity: Info", "Message: Create object cache: " + glObject.getName());

        DebugLog.debug(getClass(), logGenerator.createLog());
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

    public GLObject getGLObject(long objectId) {
        for (GLObject glObject : glObjects) {
            if (glObject.getObjectId() == objectId) {
                return glObject;
            }
        }
        return null;
    }

    public List<GLObject> getGlObjects() {
        return glObjects;
    }

    public void setGlObjects(List<GLObject> glObjects) {
        this.glObjects = glObjects;
    }
}
