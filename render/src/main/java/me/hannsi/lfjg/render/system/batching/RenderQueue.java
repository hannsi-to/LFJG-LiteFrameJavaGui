package me.hannsi.lfjg.render.system.batching;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum RenderQueue implements IEnumTypeBase {
    BACKGROUND(0, 1000, "Background"),
    GEOMETRY(1, 2000, "Geometry"),
    ALPHA_TEST(2, 2450, "AlphaTest"),
    Transparent(3, 3000, "Transparent"),
    OVERLAY(4, 4000, "Overlay");

    final int id;
    final int layerCount;
    final String name;

    RenderQueue(int id, int layerCount, String name) {
        this.id = id;
        this.layerCount = layerCount;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getLayerCount() {
        return layerCount;
    }

    @Override
    public String getName() {
        return name;
    }
}
