package me.hannsi.lfjg.render.nanoVG.renderers.svg;

import me.hannsi.lfjg.render.nanoVG.NanoVGUtil;
import me.hannsi.lfjg.render.nanoVG.system.svg.SVG;
import me.hannsi.lfjg.utils.color.Color;
import org.lwjgl.nanovg.NanoVG;

public class NanoVGSVG {
    public long nvg;
    public float x;
    public float y;
    public float width;
    public float height;
    public boolean base;
    public Color color;
    private SVG svg;

    public NanoVGSVG(long nvg) {
        this.nvg = nvg;
    }

    public void setSVGSetting(SVG svg) {
        this.svg = svg;
    }

    public void setSize(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setBase(boolean base, Color color) {
        this.base = base;
        this.color = color;
    }

    public void draw() {
        if (svg == null) {
            System.err.println("SVG is not set.");
            return;
        }
        NanoVG.nvgSave(nvg);

        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgTranslate(nvg, x, y);
        NanoVG.nvgScale(nvg, width, height);

        svg.drawSVG(nvg);

        NanoVGUtil.fillColor(nvg, color);

        NanoVG.nvgFill(nvg);

        NanoVG.nvgRestore(nvg);
    }
}