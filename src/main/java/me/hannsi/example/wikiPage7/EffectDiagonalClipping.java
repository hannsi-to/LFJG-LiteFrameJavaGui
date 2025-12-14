package me.hannsi.example.wikiPage7;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.effect.effects.DiagonalClipping;
import me.hannsi.lfjg.render.effect.effects.Texture;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;

import static me.hannsi.lfjg.render.LFJGRenderContext.TEXTURE_CACHE;

public class EffectDiagonalClipping implements LFJGFrame {
    Frame frame;
    GLRect glRect1;
    EffectCache effectCache1;

    public static void main(String[] args) {
        new EffectDiagonalClipping().setFrame();
    }

    @Override
    public void init() {
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());

        TEXTURE_CACHE.createCache("Test1", Location.fromResource("texture/test/test1.jpg"));

        glRect1 = GLRect.createGLRect("GLRect1")
                .x1_y1_color1_2p(0, 0, Color.of(0, 0, 0, 0))
                .x3_y3_color3_2p(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), Color.of(0, 0, 0, 0))
                .fill()
                .rectUV(0, 1, 1, 0)
                .update();
        effectCache1 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("T   est1"))
                .createCache(DiagonalClipping.createDiagonalClipping("DiagonalClipping").centerX(500).centerY(500))
                .attachGLObject(glRect1);
    }

    @Override
    public void drawFrame() {
        glRect1.draw();
    }

    @Override
    public void stopFrame() {
        glRect1.cleanup();
        effectCache1.cleanup();
    }

    @Override
    public void setFrameSetting() {

    }

    public void setFrame() {
        frame = new Frame(this, "DiagonalClipping");
    }
}
