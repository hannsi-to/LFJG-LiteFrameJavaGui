package me.hannsi.test.newRenderSystem;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;

import static me.hannsi.lfjg.frame.LFJGFrameContext.frame;

public class TestNewRenderSystem implements LFJGFrame {
    public static void main(String[] args) {
        new TestNewRenderSystem().setFrame();
    }

    @Override
    public void init() {
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());
    }

    @Override
    public void drawFrame() {

    }

    @Override
    public void stopFrame() {

    }

    @Override
    public void setFrameSetting() {

    }

    private void setFrame() {
        frame = new Frame(this, getClass().getSimpleName());
    }
}
