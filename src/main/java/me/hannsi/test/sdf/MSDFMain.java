package me.hannsi.test.sdf;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.RefreshRateSetting;
import me.hannsi.lfjg.frame.system.LFJGFrame;
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

            String characters = "Hello world!!感じるなあ";
            String outputFileName = "my-sdf-font-final";
            int textureResolution = 512;

            MSDFGenerator generator = MSDFGenerator.createMSDFGenerator()
                    .type(AtlasType.MSDF)
                    .ttfPath(fontFilePath)
//                    .unicodeBlock(UnicodeBlocks.getBlocks(characters))
                    .charset(characters)
//                    .allGlyphs(true)
                    .outputName(outputFileName)
                    .atlasSize(AtlasSizeType.SQUARE, true);

            boolean success = generator.generate();

            if (success) {
                System.out.println("\nProcess finished successfully.");
            } else {
                System.err.println("\nProcess finished with errors.");
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
                    .generateMeshData(characters);

            textRenderer = TextRenderer.createTextRender()
                    .textMeshBuilder(textMeshBuilder)
                    .msdfTextureLoader(msdfTextureLoader)
                    .pos(new Vector2f(100, 100))
                    .size(64);
        } catch (IOException e) {
            System.err.println("\nFATAL: Failed to initialize SDFGenerator.");
        }

        UnicodeBlocks.getBlock(11);
    }

    @Override
    public void drawFrame() {
        textRenderer.draw("Hello world!!感じるなあ");
        DebugLog.debug(getClass(), "FPS:" + frame.getFps());
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, -1);
    }
}
