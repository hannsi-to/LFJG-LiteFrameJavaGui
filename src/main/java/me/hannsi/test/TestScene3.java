package me.hannsi.test;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.jcef.JCef;
import me.hannsi.lfjg.render.openGL.renderers.font.GLText;
import me.hannsi.lfjg.render.openGL.renderers.svg.GLSVG;
import me.hannsi.lfjg.render.openGL.system.font.Font;
import me.hannsi.lfjg.render.openGL.system.font.FontCache;
import me.hannsi.lfjg.render.openGL.system.scene.IScene;
import me.hannsi.lfjg.render.openGL.system.scene.Scene;
import me.hannsi.lfjg.render.openGL.system.svg.SVG;
import me.hannsi.lfjg.render.openGL.system.svg.SVGCache;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import me.hannsi.lfjg.utils.reflection.location.URLLocation;
import me.hannsi.lfjg.utils.type.types.AlignType;
import me.hannsi.lfjg.utils.type.types.TextFormatType;

import static me.hannsi.lfjg.frame.frame.LFJGContext.*;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class TestScene3 implements IScene {
    public Scene scene;
    public Frame frame;

    public JCef jCef;

    int pboId;

    public TestScene3(Frame frame) {
        this.scene = new Scene("TestScene3", this);
        this.frame = frame;
    }

    @Override
    public void init() {
        jCef = JCef.initJCef(args, (int) windowSize.x(), (int) windowSize.y(),false,false,false,Color.of(255,255,255,255));

        pboId = glGenBuffers();
    }

    @Override
    public void drawFrame() {

    }

    @Override
    public void stopFrame() {

    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
