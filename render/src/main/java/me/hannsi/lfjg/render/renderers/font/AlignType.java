package me.hannsi.lfjg.render.renderers.font;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static me.hannsi.lfjg.render.renderers.font.Align.*;

public enum AlignType implements IEnumTypeBase {
    LEFT_TOP("LeftTop", ALIGN_LEFT | ALIGN_TOP),
    LEFT_MIDDLE("LeftMiddle", ALIGN_LEFT | ALIGN_MIDDLE),
    LEFT_BOTTOM("LeftBottom", ALIGN_LEFT | ALIGN_BOTTOM),
    LEFT_BASELINE("LeftBaseLine", ALIGN_LEFT | ALIGN_BASELINE),

    CENTER_TOP("CenterTop", ALIGN_CENTER | ALIGN_TOP),
    CENTER_MIDDLE("CenterMiddle", ALIGN_CENTER | ALIGN_MIDDLE),
    CENTER_BOTTOM("CenterBottom", ALIGN_CENTER | ALIGN_BOTTOM),
    CENTER_BASELINE("CenterBaseLine", ALIGN_CENTER | ALIGN_BASELINE),

    RIGHT_TOP("RightTop", ALIGN_RIGHT | ALIGN_TOP),
    RIGHT_MIDDLE("RightMiddle", ALIGN_RIGHT | ALIGN_MIDDLE),
    RIGHT_BOTTOM("RightBottom", ALIGN_RIGHT | ALIGN_BOTTOM),
    RIGHT_BASELINE("RightBaseLine", ALIGN_RIGHT | ALIGN_BASELINE);

    final String name;
    final int id;

    AlignType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }
}
