package me.hannsi.test;

import me.hannsi.lfjg.frame.frame.Frame;
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

import static me.hannsi.lfjg.frame.frame.LFJGContext.fontCache;
import static me.hannsi.lfjg.frame.frame.LFJGContext.svgCache;

public class TestScene3 implements IScene {
    public Scene scene;
    public Frame frame;
    public GLText glText;
    public GLSVG glSVG;

    public TestScene3(Frame frame) {
        this.scene = new Scene("TestScene3", this);
        this.frame = frame;
    }

    @Override
    public void init() {
        fontCache = FontCache.initFontCache().createCache(new Font("Font1", new ResourcesLocation("font/default.ttf"))).loadFont();

        svgCache = SVGCache.initSVGCache().createCache(new SVG("0SquareFill", new URLLocation("https://icons.getbootstrap.jp/assets/icons/0-square-fill.svg"))).loadSVG();

        glText = new GLText("GLFont1");
        glText.text("Font1", TextFormatType.STRIKETHROUGH + "" + TextFormatType.UNDERLINE + TextFormatType.SPASE_X + "{10}Hello World!!" + TextFormatType.SPASE_Y + "{50}" + TextFormatType.NEWLINE + "This is test text", 800, 0, 64, true, 10f, Color.of(255, 100, 100, 255), AlignType.LEFT_BASELINE);

        glSVG = new GLSVG("GLSVG1");
        glSVG.svg("0SquareFill", 800, 0, 10, 10);
    }

    @Override
    public void drawFrame() {
        glText.draw();
        glSVG.draw();
    }

    @Override
    public void stopFrame() {
        fontCache.cleanup();
        svgCache.cleanup();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
