package me.hannsi.lfjg.render.system.rendering;

import me.hannsi.lfjg.render.debug.exceptions.render.object.GLObjectCacheCommandException;

import java.util.ArrayList;
import java.util.List;

public class GLObjectCacheCommand {
    private GLObjectCache glObjectCache;
    private final List<Long> ignoreGLObjectId;

    GLObjectCacheCommand() {
        this.ignoreGLObjectId = new ArrayList<>();
    }

    public static GLObjectCacheCommand createGLObjectCacheCommand() {
        return new GLObjectCacheCommand();
    }

    public GLObjectCacheCommand attachGLObjectCache(GLObjectCache glObjectCache) {
        this.glObjectCache = glObjectCache;

        return this;
    }

    public GLObjectCacheCommand ignoreGLObject(String objectName) {
        if (glObjectCache == null) {
            throw new GLObjectCacheCommandException("This GLObjectCacheCommand does not have a GLObjectCache attached to it.");
        }

        return this;
    }
}
