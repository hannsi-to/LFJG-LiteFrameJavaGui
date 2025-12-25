package me.hannsi.lfjg.render.system.rendering.texture.atlas;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum SpriteMemoryPolicy implements IEnumTypeBase {
    KEEP(0, "Keep"),
    RELEASE(1, "Release"),
    STREAMING(2, "Streaming");

    final int id;
    final String name;

    SpriteMemoryPolicy(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
