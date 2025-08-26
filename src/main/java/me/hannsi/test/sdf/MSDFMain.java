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
    TextRenderer textRenderer;
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
        glText.text("Font1",TextFormatType.UNDERLINE + "abcdefghijklmnopqrstuvwxyz",1920 / 2f, 100,64,Color.WHITE,true, AlignType.CENTER_BOTTOM);

//        try {
//            Location fontFilePath = Location.fromFile("C:\\Users\\hanns\\idea-project\\LFJG-LiteFrameJavaGui\\core\\src\\main\\resources\\font\\GenZenGothic.ttf");
//
//            String characters = "Hello world!!あ";
//            String outputFileName = WorkspaceManager.currentWorkspace;
//            int textureResolution = 512;
//
//            MSDFGenerator generator = MSDFGenerator.createMSDFGenerator()
//                    .type(AtlasType.MTSDF)
//                    .ttfPath(fontFilePath)
//                    .unicodeBlock(UnicodeBlocks.getBlocks(characters))
//                    .outputName(outputFileName)
//                    .atlasSize(AtlasSizeType.SQUARE4, true)
//                    .yOrigin(AtlasYOriginType.BOTTOM);
//
//            boolean success = generator.generate();
//
//            if (success) {
//                System.out.println("Process finished successfully.");
//            } else {
//                System.err.println("Process finished with errors.");
//            }
//
//            Location jsonLocation = generator.getJsonOutputLocation();
//            MSDFFont msdfFont = MSDFJsonLoader.createMSDFJsonLoader()
//                    .jsonLocation(jsonLocation)
//                    .parseFile();
//
//            Location textureLocation = generator.getTextureOutputLocation();
//            MSDFTextureLoader msdfTextureLoader = MSDFTextureLoader.createMSDFTextureLoader()
//                    .textureLocation(textureLocation)
//                    .loadTexture();
//            TextMeshBuilder textMeshBuilder = TextMeshBuilder.createTextMeshBuilder()
//                    .msdfFont(msdfFont)
//                    .chars(generator.getChars())
//                    .generateMeshData();
//
//            textRenderer = TextRenderer.createTextRender()
//                    .textMeshBuilder(textMeshBuilder)
//                    .msdfTextureLoader(msdfTextureLoader)
//                    .defaultFontColor(Color.WHITE)
//                    .textFormat(true)
//                    .pos(new Vector2f(1920 / 2f, 100))
//                    .size(64);
//        } catch (IOException e) {
//            System.err.println("\nFATAL: Failed to initialize SDFGenerator.");
//        }
    }

    @Override
    public void drawFrame() {
//        textRenderer.draw(TextFormatType.UNDERLINE + "abcdefghijklmnopqrstuvwxyz");
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
