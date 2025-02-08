package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

/**
 * Enumeration representing different types of shader rendering modes.
 */
public enum ShaderRenderingType implements IEnumTypeBase {
    SHADER_COLOR(1, "SHADER_COLOR"),
    SHADER_TEXTURE(2, "SHADER_TEXTURE");

    final int id;
    final String name;

    /**
     * Constructs a new ShaderRenderingType enumeration value.
     *
     * @param id the unique identifier of the shader rendering type
     * @param name the name of the shader rendering type
     */
    ShaderRenderingType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the unique identifier of the shader rendering type.
     *
     * @return the unique identifier of the shader rendering type
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the shader rendering type.
     *
     * @return the name of the shader rendering type
     */
    @Override
    public String getName() {
        return name;
    }
}