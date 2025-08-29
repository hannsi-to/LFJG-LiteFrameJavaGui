package me.hannsi.lfjg.render;

/**
 * Represents unique identifiers for OpenGL objects and effect caches.
 */
public class Id {
    public static final int initialSceneId = Integer.MIN_VALUE;
    public static final long initialGLObjectId = Long.MIN_VALUE;

    public static int latestSceneId = initialSceneId;
    public static long latestGLObjectId = initialGLObjectId;
}