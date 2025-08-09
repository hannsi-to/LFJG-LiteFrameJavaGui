package me.hannsi.lfjg.render.system.text.msdf.type;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum AtlasType implements IEnumTypeBase {
    HARD_MASK("hardmask", 0),
    SOFT_MASK("softmask", 1),
    SDF("sdf", 2),
    PSDF("psdf", 3),
    MSDF("msdf", 4),
    MTSDF("mtsdf", 5);

    final String name;
    final int id;

    AtlasType(String name, int id) {
        this.name = name;
        this.id = id;
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
