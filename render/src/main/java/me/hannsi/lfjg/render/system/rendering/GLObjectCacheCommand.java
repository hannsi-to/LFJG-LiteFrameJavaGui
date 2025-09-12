package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.render.debug.exceptions.render.object.GLObjectCacheCommandException;
import me.hannsi.lfjg.render.renderers.GLObject;

import java.util.ArrayList;
import java.util.List;

public class GLObjectCacheCommand {
    private final List<Long> ignoreEffectGLObjectId;
    private GLObjectCache glObjectCache;

    GLObjectCacheCommand() {
        this.ignoreEffectGLObjectId = new ArrayList<>();
    }

    public static GLObjectCacheCommand createGLObjectCacheCommand() {
        return new GLObjectCacheCommand();
    }

    public GLObjectCacheCommand attachGLObjectCache(GLObjectCache glObjectCache) {
        this.glObjectCache = glObjectCache;

        return this;
    }

    public GLObjectCacheCommand ignoreEffectGLObject(String... objectNames) {
        checkThrowAttachedGLObjectCache();

        for (String objectName : objectNames) {
            GLObject ignoreGLObject = glObjectCache.getGLObject(objectName);
            if (ignoreGLObject == null) {
                throwNotFoundIgnoreGLObjectWithName(objectName);

                continue;
            }

            ignoreEffectGLObjectId.add(ignoreGLObject.getObjectId());
        }

        return this;
    }

    public GLObjectCacheCommand ignoreEffectGLObject(long... objectIds) {
        checkThrowAttachedGLObjectCache();

        for (long objectId : objectIds) {
            GLObject ignoreGLObject = glObjectCache.getGLObject(objectId);
            if (ignoreGLObject == null) {
                throwNotFoundIgnoreGLObjectWithId(objectId);

                continue;
            }

            ignoreEffectGLObjectId.add(glObjectCache.getGLObject(objectId).getObjectId());
        }

        return this;
    }

    public boolean shouldIgnore(Long objectId) {
        return ignoreEffectGLObjectId.contains(objectId);
    }

    public boolean checkAttachedGLObjectCache() {
        return glObjectCache != null;
    }

    public void checkThrowAttachedGLObjectCache() {
        if (!checkAttachedGLObjectCache()) {
            throw new GLObjectCacheCommandException("This GLObjectCacheCommand does not have a GLObjectCache attached to it.");
        }
    }

    public void throwNotFoundIgnoreGLObjectWithId(long id) {
        throw new GLObjectCacheCommandException("GLObject was not found. Id: " + id);
    }

    public void throwNotFoundIgnoreGLObjectWithName(String name) {
        throw new GLObjectCacheCommandException("GLObject was not found. Name: " + name);
    }
}
