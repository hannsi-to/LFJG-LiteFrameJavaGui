package me.hannsi.test.sdf;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.UnicodeBlocks;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.RefreshRateSetting;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.effect.effects.RadialBlur;
import me.hannsi.lfjg.render.effect.effects.Texture;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.renderers.text.GLText;
import me.hannsi.lfjg.render.system.text.AlignType;
import me.hannsi.lfjg.render.system.text.TextFormatType;
import me.hannsi.lfjg.render.system.text.font.Font;

import static me.hannsi.lfjg.render.LFJGRenderContext.textureCache;
import static me.hannsi.lfjg.render.LFJGRenderTextContext.fontCache;

public class MSDFMain implements LFJGFrame {
    GLText glText;
    GLRect glRect;

    EffectCache effectCache;

    private Frame frame;

    public static void main(String... args) {
        new MSDFMain().setFrame();
    }

    public void setFrame() {
        frame = new Frame(this, "MSDFMain");
    }

    @Override
    public void init() {
        frame.updateLFJGLContext();

        fontCache.createCache(
                "Font1",
                Font.createCustomFont()
                        .name("Font1")
                        .trueTypeFontPath(Location.fromFile("C:\\Users\\hanns\\idea-project\\LFJG-LiteFrameJavaGui\\core\\src\\main\\resources\\font\\GenZenGothic.ttf"))
                        .unicodeBlocks(UnicodeBlocks.getBlocks(TextFormatType.UNDERLINE + "abcdefghijklmnopqrstuvwxyz"))
                        .createCache()
        );

        glText = new GLText("GLText1");
        glText.text("Font1", TextFormatType.UNDERLINE + "abcdefghijkl" + TextFormatType.STRIKETHROUGH + "mnopqrstuvwxyz", 0, 100, 64, Color.WHITE, true, AlignType.LEFT_BASELINE);

        glRect = new GLRect("GLRect1");
        glRect.uv(0, 1, 1, 0);
        glRect.rectWH(0, 0, 1920, 1080, Color.of(0, 0, 0, 0));

        textureCache.createCache("Test1", Location.fromResource("texture/test/test1.jpg"));

        effectCache = EffectCache.createEffectCache()
//                .createCache(Translate.createTranslate("Translate1").x(100))
//                .createCache(Scale.createScale("Scale").x(2).cx(0).cy(0).autoCenter(false))
//                .createCache(Rotate.createRotate().autoCenter(false).cx(0).cy(0).zDegree(45))
                .createCache(Texture.createTexture("Texture1").textureName("Test1"))
//                .createCache(Bloom.createBloom("Bloom"))
//                .createCache(BoxBlur.createBoxBlur("BoxBlusr1"))
//                .createCache(ChromaKey.createChromaKey("ChromaKey1").chromaKeyColor(Color.of(17, 40, 133,255)))
//                .createCache(ChromaticAberration.createChromaticAberration("ChromaticAberration1"))
//                .createCache(ClippingRect.createClippingRect("ClippingRect1").x1(0).y1(0).x2(500).y2(500))
//                .createCache(ColorChanger.createColorChanger("ColorChanger1").targetColor(Color.of(17, 40, 133,255)).newColor(Color.of(255,0,0,255)))
//                .createCache(ColorCorrection.createColorCorrection("ColorCorrection1"))
//                .createCache(DiagonalClipping.createDiagonalClipping("DiagonalClipping"))
//                .createCache(DirectionalBlur.createDirectionBlur("DirectionalBlur1"))
//                .createCache(EdgeExtraction.createEdgeExtraction("EdgeExtraction1"))
//                .createCache(FXAA.createFXAA("FXAA1"))
//                .createCache(Flash.createFlash("Flash1"))
//                .createCache(GaussianBlurHorizontal.createGaussianBlurHorizontal("GaussianBlurHorizontal1"))
//                .createCache(GaussianBlurVertical.createGaussianBlurVertical("GaussianBlurVertical1"))
//                .createCache(Glow.createGlow("Glow1"))
//                .createCache(Gradation.createGradation("Gradation1"))
//                .createCache(Inversion.createInversion("Inversion1"))
//                .createCache(LensBlur.createLensBlur("LensBlur1"))
//                .createCache(LuminanceKey.createLuminanceKey("LuminanceKey1"))
//                .createCache(Monochrome.createMonochrome("Monochrome"))
//                .createCache(Pixelate.createPixelate("Pixelate1"))
                .createCache(RadialBlur.createRadialBlur("RadialBlur1"))
                .attachGLObject(glRect)
                .attachGLObject(glText);
    }

    @Override
    public void drawFrame() {
//        textRenderer.draw(TextFormatType.UNDERLINE + "abcdefghijklmnopqrstuvwxyz");

//        ((Translate) effectCache.getEffectBase("Translate1")).x(500);

        glRect.draw();
        glText.draw();
//        glLine.draw();


//        textRenderer.draw("しるT" + "しるT" + TextFormatType.RESET + "ばは" + TextFormatType.DARK_BLUE + TextFormatType.STRIKETHROUGH + "あ" + TextFormatType.GOLD + "ほ");
        DebugLog.debug(getClass(), "FPS:" + frame.getFps());
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, -1);
//        frame.setFrameSettingValue(CheckSeveritiesSetting.class, new SeverityType[]{SeverityType.HIGH});
    }
}
