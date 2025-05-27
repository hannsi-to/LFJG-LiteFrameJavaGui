package me.hannsi.test;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.openGL.gui.system.Gui;
import me.hannsi.lfjg.render.openGL.gui.ui.TestButton;
import me.hannsi.lfjg.render.openGL.system.scene.IScene;
import me.hannsi.lfjg.render.openGL.system.scene.Scene;

public class TestScene3 implements IScene {
    public Scene scene;
    public Frame frame;

    public Gui gui;

    public TestScene3(Frame frame) {
        this.scene = new Scene("TestScene3", this);
        this.frame = frame;
    }

    @Override
    public void init() {
        gui = new Gui()
                .builder()
                .addItem(new TestButton(0, 0, 100, 100, 1))
                .setEventHandler();
        gui.init();
    }

    @Override
    public void drawFrame() {
        gui.renderItems(LFJGContext.mouseInfo, LFJGContext.keyboardInfo);
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
