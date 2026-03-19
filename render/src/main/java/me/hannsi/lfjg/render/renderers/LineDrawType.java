package me.hannsi.lfjg.render.renderers;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.system.rendering.DrawType;

public enum LineDrawType implements IEnumTypeBase {
    LINES(DrawType.LINES.getId(), DrawType.LINES.getName(), DrawType.LINES),
    LINE_STRIP(DrawType.LINE_STRIP.getId(), DrawType.LINE_STRIP.getName(), DrawType.LINE_STRIP),
    LINE_LOOP(DrawType.LINE_LOOP.getId(), DrawType.LINE_LOOP.getName(), DrawType.LINE_LOOP);

    final int id;
    final String name;
    final DrawType drawType;

    LineDrawType(int id, String name, DrawType drawType) {
        this.id = id;
        this.name = name;
        this.drawType = drawType;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public DrawType getDrawType() {
        return drawType;
    }
}
