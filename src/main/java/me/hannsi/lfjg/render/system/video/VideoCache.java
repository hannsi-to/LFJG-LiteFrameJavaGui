package me.hannsi.lfjg.render.system.video;

import java.awt.image.BufferedImage;

public class VideoCache {
    public static final int CACHE_SIZE = 250;

    private int cacheSize;
    private BufferedImage[] cache;
    private int cacheIndex;

    VideoCache(int cacheSize) {
        this.cacheSize = cacheSize;
        this.cache = new BufferedImage[cacheSize];
        this.cacheIndex = 0;
    }

    public static VideoCache initVideoCache() {
        return initVideoCache(CACHE_SIZE);
    }

    public static VideoCache initVideoCache(int cacheSize) {
        return new VideoCache(cacheSize);
    }

    public void addFrame(BufferedImage frame) {
        cache[cacheIndex] = frame;
        cacheIndex++;
    }

    public BufferedImage getFrame() {
        BufferedImage frame = cache[0];
        if (frame == null) {
            return null;
        }

        for (int i = 0; i < cacheIndex; i++) {
            cache[i] = cache[i + 1];
        }

        cacheIndex--;

        return frame;
    }

    public boolean checkUpdate() {
        return cacheIndex < cacheSize;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public BufferedImage[] getCache() {
        return cache;
    }

    public void setCache(BufferedImage[] cache) {
        this.cache = cache;
    }
}
