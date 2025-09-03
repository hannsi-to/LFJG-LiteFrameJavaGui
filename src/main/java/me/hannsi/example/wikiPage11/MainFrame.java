package me.hannsi.example.wikiPage11;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.renderers.polygon.GLBezierLine;
import org.joml.Vector2f;

public class MainFrame implements LFJGFrame {
    GLBezierLine gLBezierLine;
    private Frame frame;

    public static void main(String[] args) {
        new MainFrame().setFrame();
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();

        gLBezierLine = new GLBezierLine("GLBezierLine1");

        GLBezierLine.BezierPoint[] points = new GLBezierLine.BezierPoint[]{
                new GLBezierLine.BezierPoint(new Vector2f(500, 500), Color.WHITE),
                new GLBezierLine.BezierPoint(new Vector2f(550, 600), Color.WHITE),
                new GLBezierLine.BezierPoint(new Vector2f(600, 500), Color.WHITE)
        };

        gLBezierLine.bezierLine(points, 2.0f, 64);
    }

    @Override
    public void drawFrame() {
        gLBezierLine.draw();
    }

    @Override
    public void stopFrame() {
        gLBezierLine.cleanup();
    }

    @Override
    public void setFrameSetting() {

    }

    public void setFrame() {
        frame = new Frame(this, "MainFrame");
    }
}