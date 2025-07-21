package me.hannsi.lfjg.render.gui.ui;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.core.utils.toolkit.MouseInfo;
import me.hannsi.lfjg.render.gui.system.item.items.Label;
import me.hannsi.lfjg.render.renderers.font.AlignType;
import me.hannsi.lfjg.render.renderers.font.GLText;

public class TestLabel extends Label {
    GLText glText;

    public TestLabel(float x, float y, float scale) {
        super(x, y, scale);
    }

    @Override
    public void init() {
        glText = new GLText("GLText");
        glText.text("font", getLabelText(), getX(), getY(), 20f * getScale(), false, 0f, Color.of(255, 255, 0, 255), AlignType.LEFT_BOTTOM);

        super.init();
    }

    @Override
    public void render(MouseInfo mouseInfo, KeyboardInfo keyboardInfo) {
        glText.draw();

        super.render(mouseInfo, keyboardInfo);
    }

    @Override
    public void cleanup() {
        glText.cleanup();

        super.cleanup();
    }
}
