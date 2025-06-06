package me.hannsi.test;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.gui.system.Gui;
import me.hannsi.lfjg.render.openGL.gui.ui.TestTextField;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.system.scene.IScene;
import me.hannsi.lfjg.render.openGL.system.scene.Scene;
import me.hannsi.lfjg.utils.graphics.color.Color;

public class TestScene3 implements IScene {
    public Scene scene;
    public Frame frame;

    public Gui gui;

    public GLRect glRect;

    public int x;

    public TestScene3(Frame frame) {
        this.scene = new Scene("TestScene3", this);
        this.frame = frame;
    }

    @Override
    public void init() {
        gui = new Gui()
                .builder()
//                .addItem(new TestButton(0, 0, 100, 100, 4))
//                .addItem(
//                        new TestLabel(500, 500, 1)
//                                .labelText("TestLabel")
//                )
                .addItem(
                        new TestTextField(0, 0, 100, 20, 1)
                                .typedField("GGGGG")
                )
                .setEventHandler();
        gui.init();

        x = 0;
        glRect = new GLRect("GLRect1");
        glRect.rectWH(x, 0, 200, 200, Color.of(100, 100, 100, 255));
    }

    @Override
    public void drawFrame() {
        gui.renderItems(LFJGContext.mouseInfo, LFJGContext.keyboardInfo);

        glRect.rectWH(x, 0, 200, 200, Color.of(100, 100, 100, 255));
        glRect.draw();
        x++;
    }

    @Override
    public void stopFrame() {
        gui.cleanup();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
