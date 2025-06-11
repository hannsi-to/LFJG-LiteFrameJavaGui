package me.hannsi.example.wikiPage3;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.frame.LFJGFrame;

public class MainFrame implements LFJGFrame {
    private Frame frame;

    public static void main(String[] args) {
        new MainFrame().setFrame();
    }

    @Override
    public void init() {

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

    public void setFrame() {
        frame = new Frame(this, "MainFrame");
    }
}
