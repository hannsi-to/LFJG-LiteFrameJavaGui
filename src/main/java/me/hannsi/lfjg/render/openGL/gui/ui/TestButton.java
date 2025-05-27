package me.hannsi.lfjg.render.openGL.gui.ui;

import me.hannsi.lfjg.frame.event.events.user.MouseButtonEvent;
import me.hannsi.lfjg.render.openGL.gui.system.items.Button;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.utils.toolkit.MouseInfo;

public class TestButton extends Button {
    GLRect glRect;

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
        glRect.rect(getX(), getY(), getWidth(), getHeight(), Color.of(255, 255, 255, 255));

        super.init();
    }

    @Override
    public void render(MouseInfo mouseInfo, KeyboardInfo keyboardInfo) {
        setHover(mouseInfo.isInside(getX(), getY(), getWidth(), getHeight()) && mouseInfo.isInWindow());

        glRect.draw();

        super.render(mouseInfo, keyboardInfo);
    }

    @Override
    public void cleanup() {
        glRect.cleanup();

        super.cleanup();
    }
}
