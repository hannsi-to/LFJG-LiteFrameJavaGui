package me.hannsi.lfjg.render.uitl;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum GPUVendor implements IEnumTypeBase {
    NVIDIA(0, "Nvidia"),
    AMD(1, "AMD"),
    INTEL(2, "Intel"),
    UNKNOWN(3, "Unknown");

    final int id;
    final String name;

    GPUVendor(int id, String name) {
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
