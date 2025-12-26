package me.hannsi.lfjg.core.utils.math.list;

import me.hannsi.lfjg.core.utils.math.StringHash;

public record AssetPath(String path, long hash) {
    public AssetPath(String path) {
        this(path, StringHash.hash64(path));
    }

    @Override
    public String toString() {
        return path;
    }
}
