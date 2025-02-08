package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

/**
 * <h1>Deprecated</h1>
 * Enumeration representing different types of rendering backends.
 */
@Deprecated
public enum RenderingType implements IEnumTypeBase {
    OpenGL(0, "OpenGL"),
    NanoVG(1, "NanoVG"),
    Vulkan(2, "Vulkan"),
    LibGDX(3, "LibGDX");

    final int id;
    final String name;

    /**
     * Constructs a new RenderingType enumeration value.
     *
     * @param id the unique identifier of the rendering type
     * @param name the name of the rendering type
     */
    RenderingType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the unique identifier of the rendering type.
     *
     * @return the unique identifier of the rendering type
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the rendering type.
     *
     * @return the name of the rendering type
     */
    @Override
    public String getName() {
        return name;
    }
}