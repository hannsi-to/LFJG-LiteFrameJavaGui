package me.hannsi.lfjg.render.uitl.id;

/**
 * Represents unique identifiers for OpenGL objects and effect caches.
 */
public class Id {
    public static final int initialSceneId = Integer.MIN_VALUE;
    public static final int initialGLObjectId = Integer.MIN_VALUE;

    public static int latestSceneId = initialSceneId;
    public static int latestGLObjectId = initialGLObjectId;
}