package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum ShaderRenderingType implements IEnumTypeBase {
    SHADER_COLOR(1, "SHADER_COLOR"), SHADER_TEXTURE(2, "SHADER_TEXTURE");

    final int id;
    final String name;

    ShaderRenderingType(int id, String name) {
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
