package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

public enum BlendType implements IEnumTypeBase {
    Normal("Normal", 0),
    Add("Add", 1),
    Subtract("Subtract", 2),
    Multiply("Multiply", 3),
    Screen("Screen", 4),
    Overlay("Overlay", 5),
    Lighten("Lighten", 6),
    Darken("Darken", 7),
    Luminosity("Luminosity", 8),
    ColorDifference("ColorDifference", 9),
    Shade("Shade", 10),
    LightAndShadow("LightAndShadow", 11),
    Difference("Difference", 12);

    final int id;
    final String name;

    BlendType(String name, int id) {
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
