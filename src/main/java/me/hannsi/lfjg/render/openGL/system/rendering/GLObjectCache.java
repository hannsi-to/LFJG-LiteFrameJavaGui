package me.hannsi.lfjg.render.openGL.system.rendering;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Caches OpenGL objects for rendering.
 */
public class GLObjectCache {
    private List<GLObject> glObjects;
    private FrameBuffer frameBuffer;

    /**
     * Constructs a new GLObjectCache.
     */
    public GLObjectCache() {
        glObjects = new ArrayList<>();

        frameBuffer = new FrameBuffer();

        frameBuffer.createShaderProgram();
        frameBuffer.createFrameBuffer();
    }

    /**
     * Creates a cache for the specified GLObject.
     *
     * @param glObject the GLObject to cache
     */
    public void createCache(GLObject glObject) {
        glObjects.add(glObject);

        LogGenerator logGenerator = new LogGenerator("GLObjectCache Debug Message", "Source: GLObjectCache", "Type: Cache Creation", "ID: " + glObject.getObjectId(), "Severity: Info", "Message: Create object cache: " + glObject.getName());
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Draws all cached GLObjects with the specified resolution and projection.
     */
    public void draw() {
        draw((String) null);
    }

    public void draw(String... ignoreGLObjectNames) {
        frameBuffer.bindFrameBuffer();

        for (GLObject glObject : glObjects) {
            if (ignoreGLObjectNames != null) {
                boolean ignore = false;

                for (String ignoreGLObjectName : ignoreGLObjectNames) {
                    if (glObject.getName().equals(ignoreGLObjectName)) {
                        ignore = true;
                        break;
                    }
                }

                if (ignore) {
                    continue;
                }
            }

            glObject.draw();
        }

        frameBuffer.unbindFrameBuffer();
        frameBuffer.drawFrameBuffer();
    }

    /**
     * Cleans up all cached GLObjects.
     */
    public void cleanup() {
        for (GLObject glObject : glObjects) {
            glObject.cleanup();
        }

        glObjects.clear();
        frameBuffer.cleanup();

        LogGenerator logGenerator = new LogGenerator("GLObjectCache", "Source: GLObjectCache", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: GLObjectCache cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
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

    public GLObject getGLObject(String name) {
        for (GLObject glObject : glObjects) {
            if (glObject.getName().equals(name)) {
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

    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }
}