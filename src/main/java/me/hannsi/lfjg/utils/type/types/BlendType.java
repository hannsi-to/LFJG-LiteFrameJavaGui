package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum BlendType implements IEnumTypeBase {
    MULTIPLY(0, "Multiply"), ADDITION(1, "Addition"), SUBTRACT(2, "Subtract"), AVERAGE(3, "Average"), SCREEN(4, "Screen"), OVERLAY(5, "OVERLAY"), COLORDODGE(6, "ColorDodge");

    final int id;
    final String name;

    BlendType(int id, String name) {
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
