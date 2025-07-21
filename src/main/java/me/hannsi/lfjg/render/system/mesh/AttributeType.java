package me.hannsi.lfjg.render.system.mesh;

import lombok.Getter;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

@Getter
public enum AttributeType implements IEnumTypeBase {
    POSITION_2D(0, "Position2D", 0, 2),
    POSITION_3D(1, "Position3D", 0, 3),
    COLOR(2, "Color", 1, 4),
    TEXTURE(3, "Texture", 2, 2),
    NORMAL_2D(4, "Normal2D", 3, 2),
    NORMAL_3D(5, "Normal3D", 3, 3);

    final int id;
    final String name;
    final int index;
    final int size;

    AttributeType(int id, String name, int index, int size) {
        this.id = id;
        this.name = name;
        this.index = index;
        this.size = size;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }
}
