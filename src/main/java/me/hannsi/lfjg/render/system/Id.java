package me.hannsi.lfjg.render.system;

/**
 * Represents unique identifiers for OpenGL objects and effect caches.
 */
public class Id {
    public static final int initialSceneId = Integer.MIN_VALUE;
    /**
     * The initial ID for OpenGL objects.
     */
    public static final long initialGLObjectId = Long.MIN_VALUE;

    /**
     * The initial ID for effect caches.
     */
    public static final long initialEffectCacheId = Long.MIN_VALUE;
    public static final long initialAnimationCacheId = Long.MIN_VALUE;
    public static final long initialItemId = Long.MIN_VALUE;

    public static int latestSceneId = initialSceneId;
    /**
     * The latest ID assigned to an OpenGL object.
     */
    public static long latestGLObjectId = initialGLObjectId;
    public static long latestEffectCacheId = initialEffectCacheId;
    public static long latestAnimationCacheId = initialAnimationCacheId;
    public static long latestItemId = initialItemId;
}