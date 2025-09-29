package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.renderers.polygon.GLBezierLine;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;

public class TestScene2 implements IScene {
    public Scene scene;
    public Frame frame;

    GLBezierLine glBezierLine;


    public TestScene2(Frame frame) {
        this.scene = new Scene("TestScene2", this);
        this.frame = frame;
    }

    @Override
    public void init() {
//        GLBezierLine.BezierPoint[] points = {
//                new GLBezierLine.BezierPoint(new Vector2f(0, 0), Color.BLUE),
//                new GLBezierLine.BezierPoint(new Vector2f(200, 300), Color.GREEN),
//                new GLBezierLine.BezierPoint(new Vector2f(400, 300), Color.RED),
//                new GLBezierLine.BezierPoint(new Vector2f(1000, 1000), Color.YELLOW)
//        };
//
//        glBezierLine = new GLBezierLine("GLBezierLine");
//        glBezierLine.bezierLine(points, 1.0f, 64);
    }

    @Override
    public void drawFrame() {
        glBezierLine.draw();
    }

    @Override
    public void stopFrame() {
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
