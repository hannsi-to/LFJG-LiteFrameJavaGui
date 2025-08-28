package me.hannsi.test.sdf;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.UnicodeBlocks;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.CheckSeveritiesSetting;
import me.hannsi.lfjg.frame.setting.settings.RefreshRateSetting;
import me.hannsi.lfjg.frame.setting.settings.SeverityType;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.effect.effects.Translate;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.polygon.GLLine;
import me.hannsi.lfjg.render.renderers.text.GLText;
import me.hannsi.lfjg.render.renderers.text.TextRenderer;
import me.hannsi.lfjg.render.system.text.AlignType;
import me.hannsi.lfjg.render.system.text.TextFormatType;
import me.hannsi.lfjg.render.system.text.font.Font;

import static me.hannsi.lfjg.render.LFJGRenderTextContext.fontCache;

public class MSDFMain implements LFJGFrame {
    GLLine glLine;
    GLText glText;

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

        glLine = new GLLine("GLLine1");
        glLine.line(0, 100, 1920, 100, 0.1f, Color.of(100, 100, 100, 255));

        glText = new GLText("GLText1");
        glText.text("Font1",TextFormatType.UNDERLINE + "abcdefghijkl" + TextFormatType.STRIKETHROUGH + "mnopqrstuvwxyz",0, 100,64,Color.WHITE,true, AlignType.LEFT_BASELINE);

        effectCache = EffectCache.createEffectCache()
                .createCache(Translate.createTranslate("Translate1").x(100))
                .attachGLObject(glText);
    }

    @Override
    public void drawFrame() {
//        textRenderer.draw(TextFormatType.UNDERLINE + "abcdefghijklmnopqrstuvwxyz");

        ((Translate) effectCache.getEffectBase("Translate1")).x(500);

        glText.draw();
        glLine.draw();


//        textRenderer.draw("しるT" + "しるT" + TextFormatType.RESET + "ばは" + TextFormatType.DARK_BLUE + TextFormatType.STRIKETHROUGH + "あ" + TextFormatType.GOLD + "ほ");
        DebugLog.debug(getClass(), "FPS:" + frame.getFps());
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, -1);
        frame.setFrameSettingValue(CheckSeveritiesSetting.class, new SeverityType[]{SeverityType.HIGH});
    }
}
