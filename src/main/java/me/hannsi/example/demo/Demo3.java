package me.hannsi.example.demo;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.effect.effects.*;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;

import static me.hannsi.lfjg.render.LFJGRenderContext.textureCache;

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
        textureCache.createCache("Demo3", Location.fromResource("texture/test/Demo3.jpg"));

        float stepX = 1920 / 8f + 20;
        float stepY = 1440 / 8f - 20;
        float offsetX = 20;
        float offsetY = frame.getWindowHeight() - stepY;
        glRect1 = new GLRect("GLRect1");
        glRect1.uv(0, 1, 1, 0);
        glRect1.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache1 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .attachGLObject(glRect1);
        offsetX += stepX;
        glRect2 = new GLRect("GLRect2");
        glRect2.uv(0, 1, 1, 0);
        glRect2.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache2 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(BoxBlur.createBoxBlur("BoxBlusr1"))
                .attachGLObject(glRect2);
        offsetX += stepX;
        glRect3 = new GLRect("GLRect3");
        glRect3.uv(0, 1, 1, 0);
        glRect3.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache3 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(ChromaKey.createChromaKey("ChromaKey1").chromaKeyColor(Color.of(205, 107, 23, 255)))
                .attachGLObject(glRect3);
        offsetX += stepX;
        glRect4 = new GLRect("GLRect4");
        glRect4.uv(0, 1, 1, 0);
        glRect4.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache4 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(ChromaticAberration.createChromaticAberration("ChromaticAberration1"))
                .attachGLObject(glRect4);
        offsetX += stepX;
        glRect5 = new GLRect("GLRect5");
        glRect5.uv(0, 1, 1, 0);
        glRect5.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache5 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Bloom.createBloom("Bloom"))
                .attachGLObject(glRect5);
        offsetX += stepX;
        glRect6 = new GLRect("GLRect6");
        glRect6.uv(0, 1, 1, 0);
        glRect6.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache6 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(ColorChanger.createColorChanger("ColorChanger1").targetColor(Color.of(17, 40, 133, 255)).newColor(Color.of(255, 0, 0, 255)))
                .attachGLObject(glRect6);
        offsetX += stepX;
        glRect7 = new GLRect("GLRect7");
        glRect7.uv(0, 1, 1, 0);
        glRect7.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache7 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(ColorCorrection.createColorCorrection("ColorCorrection1"))
                .attachGLObject(glRect7);
        offsetX = 20;
        offsetY -= stepY + 40;
        glRect8 = new GLRect("GLRect8");
        glRect8.uv(0, 1, 1, 0);
        glRect8.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache8 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(DiagonalClipping.createDiagonalClipping("DiagonalClipping").centerX(offsetX + (1920 / 8f) / 2f).centerY(offsetY + (1440 / 8f) / 2f))
                .attachGLObject(glRect8);
        offsetX += stepX;
        glRect9 = new GLRect("GLRect9");
        glRect9.uv(0, 1, 1, 0);
        glRect9.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache9 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(DirectionalBlur.createDirectionBlur("DirectionalBlur1"))
                .attachGLObject(glRect9);
        offsetX += stepX;
        glRect10 = new GLRect("GLRect10");
        glRect10.uv(0, 1, 1, 0);
        glRect10.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache10 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(EdgeExtraction.createEdgeExtraction("EdgeExtraction1"))
                .attachGLObject(glRect10);
        offsetX += stepX;
        glRect11 = new GLRect("GLRect11");
        glRect11.uv(0, 1, 1, 0);
        glRect11.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache11 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(FXAA.createFXAA("FXAA1").useAlpha(true))
                .attachGLObject(glRect11);
        offsetX += stepX;
        glRect12 = new GLRect("GLRect12");
        glRect12.uv(0, 1, 1, 0);
        glRect12.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache12 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Flash.createFlash("Flash1"))
                .attachGLObject(glRect12);
        offsetX += stepX;
        glRect13 = new GLRect("GLRect13");
        glRect13.uv(0, 1, 1, 0);
        glRect13.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache13 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(GaussianBlurHorizontal.createGaussianBlurHorizontal("GaussianBlurHorizontal1"))
                .attachGLObject(glRect13);
        offsetX += stepX;
        glRect14 = new GLRect("GLRect14");
        glRect14.uv(0, 1, 1, 0);
        glRect14.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache14 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(GaussianBlurVertical.createGaussianBlurVertical("GaussianBlurVertical1"))
                .attachGLObject(glRect14);
        offsetX = 20;
        offsetY -= stepY + 40;
        glRect15 = new GLRect("GLRect15");
        glRect15.uv(0, 1, 1, 0);
        glRect15.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache15 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Glow.createGlow("Glow1"))
                .attachGLObject(glRect15);
        offsetX += stepX;
        glRect16 = new GLRect("GLRect16");
        glRect16.uv(0, 1, 1, 0);
        glRect16.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache16 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Gradation.createGradation("Gradation1"))
                .attachGLObject(glRect16);
        offsetX += stepX;
        glRect17 = new GLRect("GLRect17");
        glRect17.uv(0, 1, 1, 0);
        glRect17.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache17 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Inversion.createInversion("Inversion1"))
                .createCache(Translate.createTranslate("Translate1").x(600).y(-121))
                .attachGLObject(glRect17);
        offsetX += stepX;
        glRect18 = new GLRect("GLRect18");
        glRect18.uv(0, 1, 1, 0);
        glRect18.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache18 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(LensBlur.createLensBlur("LensBlur1"))
                .attachGLObject(glRect18);
        offsetX += stepX;
        glRect19 = new GLRect("GLRect19");
        glRect19.uv(0, 1, 1, 0);
        glRect19.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache19 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(LuminanceKey.createLuminanceKey("LuminanceKey1"))
                .attachGLObject(glRect19);
        offsetX += stepX;
        glRect20 = new GLRect("GLRect20");
        glRect20.uv(0, 1, 1, 0);
        glRect20.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache20 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Monochrome.createMonochrome("Monochrome"))
                .attachGLObject(glRect20);
        offsetX += stepX;
        glRect21 = new GLRect("GLRect21");
        glRect21.uv(0, 1, 1, 0);
        glRect21.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
        effectCache21 = EffectCache.createEffectCache()
                .createCache(Texture.createTexture("Texture1").textureName("Demo3"))
                .createCache(Pixelate.createPixelate("Pixelate1"))
                .attachGLObject(glRect21);
        offsetX = 20;
        offsetY -= stepY + 40;
        glRect22 = new GLRect("GLRect22");
        glRect22.uv(0, 1, 1, 0);
        glRect22.rectWH(offsetX, offsetY, 1920 / 8f, 1440 / 8f, Color.of(0, 0, 0, 0));
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
