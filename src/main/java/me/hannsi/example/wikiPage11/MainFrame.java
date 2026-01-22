package me.hannsi.example.wikiPage11;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.renderers.polygon.GLBezierLine;
import org.joml.Vector2f;

public class MainFrame implements LFJGFrame {
    GLBezierLine glBezierLine;
    private Frame frame;

    public static void main(String[] args) {
        new MainFrame().setFrame();
    }

    @Override
    public void init() {
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());

        glBezierLine = GLBezierLine.createGLBezierLine("GLBezierLine1")
                .addControlPoint(new GLBezierLine.BezierPoint(new Vector2f(500, 500), Color.WHITE))
                .addControlPoint(new GLBezierLine.BezierPoint(new Vector2f(550, 600), Color.WHITE))
                .addControlPoint(new GLBezierLine.BezierPoint(new Vector2f(600, 500), Color.WHITE))
                .end()
                .segment(64)
                .lineWidth(2f)
                .update();
    }

    @Override
    public void drawFrame() {
//        glBezierLine.draw();
    }

    @Override
    public void stopFrame() {
//        glBezierLine.cleanup();
    }

    @Override
    public void setFrameSetting() {

    }

    public void setFrame() {
        frame = new Frame(this, "MainFrame");
    }
}