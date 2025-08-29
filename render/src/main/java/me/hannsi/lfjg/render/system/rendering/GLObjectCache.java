package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.renderers.GLObject;

import java.util.*;
import java.util.function.Predicate;

public class GLObjectCache {
    private final Map<Long, GLObject> glObjects;

    public GLObjectCache() {
        glObjects = new HashMap<>();
    }

    public static GLObjectCache createGLObjectCache() {
        return new GLObjectCache();
    }

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

    public void draw() {
        draw(glObject -> true);
    }

    @Deprecated
    public void draw(String... ignoreGLObjectNames) {
        if (ignoreGLObjectNames == null) {
            draw();
            return;
        }

        Set<String> ignoreSet = new HashSet<>();
        Collections.addAll(ignoreSet, ignoreGLObjectNames);

        draw(glObject -> !ignoreSet.contains(glObject.getName()));
    }

    public void draw(Predicate<GLObject> filter) {
        for (GLObject glObject : glObjects.values()) {
            if (filter != null && !filter.test(glObject)) continue;
            glObject.draw();
        }
    }

    public void cleanup() {
        StringBuilder ids = new StringBuilder();
        for (GLObject glObject : glObjects.values()) {
            glObject.cleanup();
            ids.append(glObject.getObjectId()).append(", ");
        }

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

    public GLObject getGLObject(long objectId) {
        return glObjects.get(objectId);
    }

    public GLObject getGLObject(String name) {
        for (GLObject glObject : glObjects.values()) {
            if (glObject.getName().equals(name)) {
                return glObject;
            }
        }
        return null;
    }

    public Map<Long, GLObject> getGlObjects() {
        return glObjects;
    }
}
