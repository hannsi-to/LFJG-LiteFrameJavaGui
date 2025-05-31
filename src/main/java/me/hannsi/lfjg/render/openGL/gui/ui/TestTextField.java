package me.hannsi.lfjg.render.openGL.gui.ui;

import me.hannsi.lfjg.render.openGL.gui.system.item.items.TextField;
import me.hannsi.lfjg.render.openGL.renderers.font.GLText;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.utils.toolkit.MouseInfo;
import me.hannsi.lfjg.utils.type.types.AlignType;

public class TestTextField extends TextField {
    GLText glText;

    public TestTextField(float x, float y, float width, float height, float scale) {
        super(x, y, width, height, scale);
    }

    @Override
    public void cleanup() {
        glText.cleanup();

        super.cleanup();
    }

    @Override
    public void render(MouseInfo mouseInfo, KeyboardInfo keyboardInfo) {
        setHover(mouseInfo.isInside(getX(), getY(), getWidthWithScale(), getHeightWithScale()) && mouseInfo.isInWindow());
        glText.setText(getTypedField());
        glText.draw();

        super.render(mouseInfo, keyboardInfo);
    }

    @Override
    public void init() {
        glText = new GLText("TextField");
        glText.text("font", getTypedField(), getX(), getY(), 20f, false, 0f, Color.of(255, 255, 255, 255), AlignType.LEFT_BOTTOM);

        super.init();
    }
}
