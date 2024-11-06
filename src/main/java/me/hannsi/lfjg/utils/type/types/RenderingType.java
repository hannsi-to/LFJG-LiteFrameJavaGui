package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

@Deprecated
public enum RenderingType implements IEnumTypeBase {
    OpenGL(0, "OpenGL"), NanoVG(1, "NanoVG"), Vulkan(2, "Vulkan"), LibGDX(3, "LibGDX");

    final int id;
    final String name;

    RenderingType(int id, String name) {
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
