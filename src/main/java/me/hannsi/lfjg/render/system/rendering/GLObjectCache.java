package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.render.renderers.GLObject;

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

    public static GLObjectCache initGLObjectCache() {
        return new GLObjectCache();
    }

    /**
     * Creates a cache for the specified GLObject.
     *
     * @param glObject the GLObject to cache
     */
    public GLObjectCache createCache(GLObject glObject) {
        glObjects.add(glObject);

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                String.valueOf(glObject.getObjectId()),
                ""
        ).logging(DebugLevel.DEBUG);

        return this;
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
        StringBuilder ids = new StringBuilder();
        for (GLObject glObject : glObjects) {
            glObject.cleanup();
            ids.append(glObject.getObjectId()).append(", ");
        }

        glObjects.clear();
        frameBuffer.cleanup();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.substring(0, ids.length() - 2),
                ""
        ).logging(DebugLevel.DEBUG);
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