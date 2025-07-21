package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static org.lwjgl.nanovg.NanoVG.*;

public enum AlignType implements IEnumTypeBase {
    LEFT_TOP("LeftTop", NVG_ALIGN_LEFT | NVG_ALIGN_TOP),
    LEFT_MIDDLE("LeftMiddle", NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE),
    LEFT_BOTTOM("LeftBottom", NVG_ALIGN_LEFT | NVG_ALIGN_BOTTOM),
    LEFT_BASELINE("LeftBaseLine", NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE),

    CENTER_TOP("CenterTop", NVG_ALIGN_CENTER | NVG_ALIGN_TOP),
    CENTER_MIDDLE("CenterMiddle", NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE),
    CENTER_BOTTOM("CenterBottom", NVG_ALIGN_CENTER | NVG_ALIGN_BOTTOM),
    CENTER_BASELINE("CenterBaseLine", NVG_ALIGN_CENTER | NVG_ALIGN_BASELINE),

    RIGHT_TOP("RightTop", NVG_ALIGN_RIGHT | NVG_ALIGN_TOP),
    RIGHT_MIDDLE("RightMiddle", NVG_ALIGN_RIGHT | NVG_ALIGN_MIDDLE),
    RIGHT_BOTTOM("RightBottom", NVG_ALIGN_RIGHT | NVG_ALIGN_BOTTOM),
    RIGHT_BASELINE("RightBaseLine", NVG_ALIGN_RIGHT | NVG_ALIGN_BASELINE);

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
