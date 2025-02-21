package me.hannsi.lfjg.render.openGL.system;

/**
 * Represents unique identifiers for OpenGL objects and effect caches.
 */
public class Id {
    /**
     * The initial ID for OpenGL objects.
     */
    public static final long initialGLObjectId = Long.MIN_VALUE;

    /**
     * The initial ID for effect caches.
     */
    public static final long initialEffectCacheId = Long.MIN_VALUE;
    public static final long initialAnimationCacheId = Long.MIN_VALUE;

    /**
     * The latest ID assigned to an OpenGL object.
     */
    public static long glLatestObjectId = initialGLObjectId;
}