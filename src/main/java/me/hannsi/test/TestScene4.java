package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.renderers.polygon.GLLine;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.renderers.polygon.GLTriangle;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;
import me.hannsi.lfjg.utils.graphics.color.Color;

public class TestScene4 implements IScene {
    public Scene scene;

    public GLLine glLine;
    public GLTriangle glTriangle;
    public GLRect glRect;

    public TestScene4(Frame frame) {
        this.scene = new Scene("TestScene4", this);
    }

    @Override
    public void init() {
        glTriangle = new GLTriangle("glTriangle");
        glTriangle.triangleOutLine(0, 0, 100, 100, 300, 200, 0.1f, Color.of(255, 0, 0, 255));

        glLine = new GLLine("glLine");
        glLine.line(0, 0, 100, 100, 1f, Color.of(255, 0, 255, 255));

        glRect = new GLRect("glRect");
        glRect.rectWHOutLine(100, 100, 200, 200, 0.1f, Color.of(100, 100, 100, 255));
    }

    @Override
    public void drawFrame() {
        glTriangle.draw();
        glLine.draw();
        glRect.draw();
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
