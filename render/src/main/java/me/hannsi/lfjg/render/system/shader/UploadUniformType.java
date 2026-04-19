package me.hannsi.lfjg.render.system.shader;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum UploadUniformType implements IEnumTypeBase {
    ONCE("Once", 0),
    PER_FRAME("PreFrame", 1),
    ON_CHANGE("OnChange", 2);

    final int id;
    final String name;

    UploadUniformType(String name, int id) {
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
