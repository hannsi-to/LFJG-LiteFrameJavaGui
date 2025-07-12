package me.hannsi.lfjg.render.system.rendering;

import lombok.Getter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.render.renderers.GLObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Caches OpenGL objects for rendering.
 */
@Getter
public class GLObjectCache {
    /**
     * Maps objectId to GLObject.
     */
    private final Map<Long, GLObject> glObjects;
    private final FrameBuffer frameBuffer;

    /**
     * Constructs a new GLObjectCache.
     */
    public GLObjectCache() {
        glObjects = new HashMap<>();
        frameBuffer = new FrameBuffer();

        frameBuffer.createShaderProgram();
        frameBuffer.createFrameBuffer();
    }

    public static GLObjectCache createGLObjectCache() {
        return new GLObjectCache();
    }

    /**
     * Adds a GLObject to the cache.
     *
     * @param glObject the GLObject to cache
     * @return this
     */
    public GLObjectCache createCache(GLObject glObject) {
        glObjects.put(glObject.getObjectId(), glObject);

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
        draw(glObject -> true);
    }

    @Deprecated
    public void draw(String... ignoreGLObjectNames) {
        if (ignoreGLObjectNames == null) {
            draw();
            return;
        }

        Set<String> ignoreSet = Set.of(ignoreGLObjectNames);
        draw(glObject -> !ignoreSet.contains(glObject.getName()));
    }

    public void draw(Predicate<GLObject> filter) {
        frameBuffer.bindFrameBuffer();

        for (GLObject glObject : glObjects.values()) {
            if (filter != null && !filter.test(glObject)) continue;
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
        for (GLObject glObject : glObjects.values()) {
            glObject.cleanup();
            ids.append(glObject.getObjectId()).append(", ");
        }

        frameBuffer.cleanup();
        glObjects.clear();

        if (ids.length() > 2) {
            ids.setLength(ids.length() - 2);
        }

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.toString(),
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
        return glObjects.get(objectId);
    }

    /**
     * Gets the GLObject with the specified name.
     *
     * @param name the name of the GLObject
     * @return the matching GLObject, or null if not found
     */
    public GLObject getGLObject(String name) {
        for (GLObject glObject : glObjects.values()) {
            if (glObject.getName().equals(name)) {
                return glObject;
            }
        }
        return null;
    }
}
