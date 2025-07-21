package me.hannsi.lfjg.render.gui.ui;

import me.hannsi.lfjg.frame.event.events.user.MouseButtonEvent;
import me.hannsi.lfjg.render.gui.system.item.items.Button;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.core.utils.toolkit.MouseInfo;

public class TestButton extends Button {
    GLRect glRect;
//    GLText glText;

    public TestButton(float x, float y, float width, float height, float scale) {
        super(x, y, width, height, scale);
    }

    @Override
    public void doAction(MouseButtonEvent event) {
        System.out.println("Button clicked!");

        super.doAction(event);
    }

    @Override
    public void init() {
        glRect = new GLRect("GLRect");
        glRect.rect(getX(), getY(), getWidthWithScale(), getHeightWithScale(), Color.of(255, 255, 255, 255));

//        glText = new GLText("GLText");
//        glText.text("font","AAAAAAAAA",getX(),getY(),20,false,1f, Color.of(255,255,255,255), AlignType.LEFT_BOTTOM);

        super.init();
    }

    @Override
    public void render(MouseInfo mouseInfo, KeyboardInfo keyboardInfo) {
        setHover(mouseInfo.isInside(getX(), getY(), getWidthWithScale(), getHeightWithScale()) && mouseInfo.isInWindow());

//        glText.draw();
        glRect.draw();

        super.render(mouseInfo, keyboardInfo);
    }

    @Override
    public void cleanup() {
//        glText.cleanup();
        glRect.cleanup();

        super.cleanup();
    }
}
