package me.hannsi.test.sdf;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.RefreshRateSetting;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.renderers.font.TextFormatType;
import me.hannsi.test.sdf.msdf.*;
import org.joml.Vector2f;

import java.io.IOException;

public class MSDFMain implements LFJGFrame {
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

        try {
            Location fontFilePath = Location.fromFile("C:\\Users\\hanns\\idea-project\\LFJG-LiteFrameJavaGui\\core\\src\\main\\resources\\font\\GenZenGothic.ttf");

            String characters = "Hello world!!あ";
            String outputFileName = "my-sdf-font-final";
            int textureResolution = 512;

            MSDFGenerator generator = MSDFGenerator.createMSDFGenerator()
                    .type(AtlasType.MSDF)
                    .ttfPath(fontFilePath)
                    .unicodeBlock(UnicodeBlocks.getBlocks(characters))
                    .outputName(outputFileName)
                    .atlasSize(AtlasSizeType.SQUARE4, true)
                    .yOrigin(AtlasYOriginType.BOTTOM);

            boolean success = generator.generate();

            if (success) {
                System.out.println("Process finished successfully.");
            } else {
                System.err.println("Process finished with errors.");
            }

            Location jsonLocation = generator.getJsonOutputLocation();
            MSDFFont msdfFont = MSDFJsonLoader.createMSDFJsonLoader()
                    .jsonLocation(jsonLocation)
                    .parseFile();

            Location textureLocation = generator.getTextureOutputLocation();
            MSDFTextureLoader msdfTextureLoader = MSDFTextureLoader.createMSDFTextureLoader()
                    .textureLocation(textureLocation)
                    .loadTexture();
            TextMeshBuilder textMeshBuilder = TextMeshBuilder.createTextMeshBuilder()
                    .msdfFont(msdfFont)
                    .chars(generator.getChars())
                    .generateMeshData();

            textRenderer = TextRenderer.createTextRender()
                    .textMeshBuilder(textMeshBuilder)
                    .msdfTextureLoader(msdfTextureLoader)
                    .defaultFontColor(Color.WHITE)
                    .pos(new Vector2f(100, 100))
                    .size(64);
        } catch (IOException e) {
            System.err.println("\nFATAL: Failed to initialize SDFGenerator.");
        }
    }

    @Override
    public void drawFrame() {
        textRenderer.draw(TextFormatType.SPASE_X + "{40}しるT" + TextFormatType.DOUBLE_UNDERLINE + "しるT" + TextFormatType.RESET + TextFormatType.SPASE_Y + "{-40}" + TextFormatType.NEWLINE + "ばは" + TextFormatType.DARK_BLUE + TextFormatType.STRIKETHROUGH + "あ" + TextFormatType.GOLD + "ほ");
//        textRenderer.draw("しるT" + "しるT" + TextFormatType.RESET + "ばは" + TextFormatType.DARK_BLUE + TextFormatType.STRIKETHROUGH + "あ" + TextFormatType.GOLD + "ほ");
//        DebugLog.debug(getClass(), "FPS:" + frame.getFps());
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, -1);
    }
}
