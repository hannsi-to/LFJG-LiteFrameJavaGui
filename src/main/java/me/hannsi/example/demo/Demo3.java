package me.hannsi.example.demo;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.LFJGRenderContext;
import me.hannsi.lfjg.render.effect.effects.*;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;

public class Demo3 implements IScene {
    Frame frame;
    Scene scene;

    GLRect glRect1;
    EffectCache effectCache1;
    GLRect glRect2;
    EffectCache effectCache2;
    GLRect glRect3;
    EffectCache effectCache3;
    GLRect glRect4;
    EffectCache effectCache4;
    GLRect glRect5;
    EffectCache effectCache5;
    GLRect glRect6;
    EffectCache effectCache6;
    GLRect glRect7;
    EffectCache effectCache7;
    GLRect glRect8;
    EffectCache effectCache8;
    GLRect glRect9;
    EffectCache effectCache9;
    GLRect glRect10;
    EffectCache effectCache10;
    GLRect glRect11;
    EffectCache effectCache11;
    GLRect glRect12;
    EffectCache effectCache12;
    GLRect glRect13;
    EffectCache effectCache13;
    GLRect glRect14;
    EffectCache effectCache14;
    GLRect glRect15;
    EffectCache effectCache15;
    GLRect glRect16;
    EffectCache effectCache16;
    GLRect glRect17;
    EffectCache effectCache17;
    GLRect glRect18;
    EffectCache effectCache18;
    GLRect glRect19;
    EffectCache effectCache19;
    GLRect glRect20;
    EffectCache effectCache20;
    GLRect glRect21;
    EffectCache effectCache21;
    GLRect glRect22;
    EffectCache effectCache22;

    public Demo3(Frame frame) {
        this.frame = frame;
        this.scene = new Scene("Demo3", this);
    }

    @Override
    public void init() {
        LFJGRenderContext.textureCache.createCache("Demo3", Location.fromResource("texture/test/Demo3.jpg"));

        float stepX = 1920 / 8f + 20;
        float stepY = 1440 / 8f - 20;
        float offsetX = 20;
        float offsetY = frame.getWindowHeight() - stepY;
        glRect1 = GLRect.createGLRect("GLRect1")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        effectCache1 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .attachGLObject(glRect1);
        offsetX += stepX;
        glRect2 = GLRect.createGLRect("GLRect2")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect2.uv(0, 1, 1, 0);
        effectCache2 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(BoxBlur.createBoxBlur("BoxBlusr1"))
                .attachGLObject(glRect2);
        offsetX += stepX;
        glRect3 = GLRect.createGLRect("GLRect3")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect3.uv(0, 1, 1, 0);
        effectCache3 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(ChromaKey.createChromaKey("ChromaKey1").chromaKeyColor(Color.of(205, 107, 23, 255)))
                .attachGLObject(glRect3);
        offsetX += stepX;
        glRect4 = GLRect.createGLRect("GLRect4")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect4.uv(0, 1, 1, 0);
        effectCache4 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(ChromaticAberration.createChromaticAberration("ChromaticAberration1"))
                .attachGLObject(glRect4);
        offsetX += stepX;
        glRect5 = GLRect.createGLRect("GLRect5")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect5.uv(0, 1, 1, 0);
        effectCache5 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Bloom.createBloom("Bloom"))
                .attachGLObject(glRect5);
        offsetX += stepX;
        glRect6 = GLRect.createGLRect("GLRect6")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect6.uv(0, 1, 1, 0);
        effectCache6 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(ColorChanger.createColorChanger("ColorChanger1").targetColor(Color.of(17, 40, 133, 255)).newColor(Color.of(255, 0, 0, 255)))
                .attachGLObject(glRect6);
        offsetX += stepX;
        glRect7 = GLRect.createGLRect("GLRect7")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect7.uv(0, 1, 1, 0);
        effectCache7 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(ColorCorrection.createColorCorrection("ColorCorrection1"))
                .attachGLObject(glRect7);
        offsetX = 20;
        offsetY -= stepY + 40;
        glRect8 = GLRect.createGLRect("GLRect8")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect8.uv(0, 1, 1, 0);
        effectCache8 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(DiagonalClipping.createDiagonalClipping("DiagonalClipping").centerX(offsetX + (1920 / 8f) / 2f).centerY(offsetY + (1440 / 8f) / 2f))
                .attachGLObject(glRect8);
        offsetX += stepX;
        glRect9 = GLRect.createGLRect("GLRect9")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect9.uv(0, 1, 1, 0);
        effectCache9 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(DirectionalBlur.createDirectionBlur("DirectionalBlur1"))
                .attachGLObject(glRect9);
        offsetX += stepX;
        glRect10 = GLRect.createGLRect("GLRect10")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect10.uv(0, 1, 1, 0);
        effectCache10 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(EdgeExtraction.createEdgeExtraction("EdgeExtraction1"))
                .attachGLObject(glRect10);
        offsetX += stepX;
        glRect11 = GLRect.createGLRect("GLRect11")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect11.uv(0, 1, 1, 0);
        effectCache11 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(FXAA.createFXAA("FXAA1").useAlpha(true))
                .attachGLObject(glRect11);
        offsetX += stepX;
        glRect12 = GLRect.createGLRect("GLRect12")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect12.uv(0, 1, 1, 0);
        effectCache12 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Flash.createFlash("Flash1"))
                .attachGLObject(glRect12);
        offsetX += stepX;
        glRect13 = GLRect.createGLRect("GLRect13")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect13.uv(0, 1, 1, 0);
        effectCache13 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(GaussianBlurHorizontal.createGaussianBlurHorizontal("GaussianBlurHorizontal1"))
                .attachGLObject(glRect13);
        offsetX += stepX;
        glRect14 = GLRect.createGLRect("GLRect14")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect14.uv(0, 1, 1, 0);
        effectCache14 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(GaussianBlurVertical.createGaussianBlurVertical("GaussianBlurVertical1"))
                .attachGLObject(glRect14);
        offsetX = 20;
        offsetY -= stepY + 40;
        glRect15 = GLRect.createGLRect("GLRect15")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect15.uv(0, 1, 1, 0);
        effectCache15 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Glow.createGlow("Glow1"))
                .attachGLObject(glRect15);
        offsetX += stepX;
        glRect16 = GLRect.createGLRect("GLRect16")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect16.uv(0, 1, 1, 0);
        effectCache16 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Gradation.createGradation("Gradation1"))
                .attachGLObject(glRect16);
        offsetX += stepX;
        glRect17 = GLRect.createGLRect("GLRect17")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect17.uv(0, 1, 1, 0);
        effectCache17 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Inversion.createInversion("Inversion1"))
                .createCache(Translate.createTranslate("Translate1").x(600).y(-121))
                .attachGLObject(glRect17);
        offsetX += stepX;
        glRect18 = GLRect.createGLRect("GLRect18")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect18.uv(0, 1, 1, 0);
        effectCache18 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(LensBlur.createLensBlur("LensBlur1"))
                .attachGLObject(glRect18);
        offsetX += stepX;
        glRect19 = GLRect.createGLRect("GLRect19")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect19.uv(0, 1, 1, 0);
        effectCache19 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(LuminanceKey.createLuminanceKey("LuminanceKey1"))
                .attachGLObject(glRect19);
        offsetX += stepX;
        glRect20 = GLRect.createGLRect("GLRect20")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect20.uv(0, 1, 1, 0);
        effectCache20 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Monochrome.createMonochrome("Monochrome"))
                .attachGLObject(glRect20);
        offsetX += stepX;
        glRect21 = GLRect.createGLRect("GLRect21")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect21.uv(0, 1, 1, 0);
        effectCache21 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Pixelate.createPixelate("Pixelate1"))
                .attachGLObject(glRect21);
        offsetX = 20;
        offsetY -= stepY + 40;
        glRect22 = GLRect.createGLRect("GLRect22")
                .x1_y1_color1_2p(offsetX, offsetY, Color.of(0, 0, 0, 0))
                .width3_height3_color3_2p(1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0))
                .fill()
                .uv(0, 1, 1, 0)
                .update();
        glRect22.uv(0, 1, 1, 0);
        effectCache22 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(RadialBlur.createRadialBlur("RadialBlur1").centerX(offsetX + (1920 / 8f) / 2f).centerY(offsetY + (1440 / 8f) / 2f))
                .attachGLObject(glRect22);
    }

    @Override
    public void drawFrame() {
        glRect1.draw();
        glRect2.draw();
        glRect3.draw();
        glRect4.draw();
        glRect5.draw();
        glRect6.draw();
        glRect7.draw();
        glRect8.draw();
        glRect9.draw();
        glRect10.draw();
        glRect11.draw();
        glRect12.draw();
        glRect13.draw();
        glRect14.draw();
        glRect15.draw();
        glRect16.draw();
        glRect17.draw();
        glRect18.draw();
        glRect19.draw();
        glRect20.draw();
        glRect21.draw();
        glRect22.draw();
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
