package me.hannsi.lfjg.render.system.text.msdf.type;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum GlyphPXAlign implements IEnumTypeBase {
    OFF("off", 0),
    ON("on", 1),
    HORIZONTAL("horizontal", 2),
    VERTICAL("vertical", 3);

    final String name;
    final int id;

    GlyphPXAlign(String name, int id) {
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
