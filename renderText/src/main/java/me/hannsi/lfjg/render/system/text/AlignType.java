package me.hannsi.lfjg.render.system.text;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum AlignType implements IEnumTypeBase {
    LEFT_TOP("LeftTop", Align.ALIGN_LEFT | Align.ALIGN_TOP),
    LEFT_MIDDLE("LeftMiddle", Align.ALIGN_LEFT | Align.ALIGN_MIDDLE),
    LEFT_BOTTOM("LeftBottom", Align.ALIGN_LEFT | Align.ALIGN_BOTTOM),
    LEFT_BASELINE("LeftBaseLine", Align.ALIGN_LEFT | Align.ALIGN_BASELINE),

    CENTER_TOP("CenterTop", Align.ALIGN_CENTER | Align.ALIGN_TOP),
    CENTER_MIDDLE("CenterMiddle", Align.ALIGN_CENTER | Align.ALIGN_MIDDLE),
    CENTER_BOTTOM("CenterBottom", Align.ALIGN_CENTER | Align.ALIGN_BOTTOM),
    CENTER_BASELINE("CenterBaseLine", Align.ALIGN_CENTER | Align.ALIGN_BASELINE),

    RIGHT_TOP("RightTop", Align.ALIGN_RIGHT | Align.ALIGN_TOP),
    RIGHT_MIDDLE("RightMiddle", Align.ALIGN_RIGHT | Align.ALIGN_MIDDLE),
    RIGHT_BOTTOM("RightBottom", Align.ALIGN_RIGHT | Align.ALIGN_BOTTOM),
    RIGHT_BASELINE("RightBaseLine", Align.ALIGN_RIGHT | Align.ALIGN_BASELINE);

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
