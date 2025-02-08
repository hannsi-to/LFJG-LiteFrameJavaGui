package me.hannsi.lfjg.render.openGL.system.rendering;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.math.Projection;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Caches OpenGL objects for rendering.
 */
public class GLObjectCache {
    private List<GLObject> glObjects;

    /**
     * Constructs a new GLObjectCache.
     */
    public GLObjectCache() {
        glObjects = new ArrayList<>();
    }

    /**
     * Creates a cache for the specified GLObject.
     *
     * @param glObject the GLObject to cache
     */
    public void createCache(GLObject glObject) {
        glObjects.add(glObject);

        LogGenerator logGenerator = new LogGenerator("GLObjectCache Debug Message", "Source: GLObjectCache", "Type: Cache Creation", "ID: " + glObject.getObjectId(), "Severity: Info", "Message: Create object cache: " + glObject.getName());

        DebugLog.debug(getClass(), logGenerator.createLog());
    }

    /**
     * Draws all cached GLObjects with the specified resolution and projection.
     *
     * @param resolution the resolution to use for drawing
     * @param projection the projection to use for drawing
     */
    public void draw(Vector2f resolution, Projection projection) {
        for (GLObject glObject : glObjects) {
            glObject.draw(resolution, projection);
        }
    }

    /**
     * Cleans up all cached GLObjects.
     */
    public void cleanup() {
        for (GLObject glObject : glObjects) {
            glObject.cleanup();
        }
    }

    /**
     * Gets the GLObject with the specified object ID.
     *
     * @param objectId the ID of the GLObject to retrieve
     * @return the GLObject with the specified ID, or null if not found
     */
    public GLObject getGLObject(long objectId) {
        for (GLObject glObject : glObjects) {
            if (glObject.getObjectId() == objectId) {
                return glObject;
            }
        }
        return null;
    }

    /**
     * Gets the list of cached GLObjects.
     *
     * @return the list of cached GLObjects
     */
    public List<GLObject> getGlObjects() {
        return glObjects;
    }

    /**
     * Sets the list of cached GLObjects.
     *
     * @param glObjects the new list of cached GLObjects
     */
    public void setGlObjects(List<GLObject> glObjects) {
        this.glObjects = glObjects;
    }
}