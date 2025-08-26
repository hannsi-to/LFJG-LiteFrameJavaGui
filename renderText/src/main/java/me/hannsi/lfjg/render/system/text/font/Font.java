package me.hannsi.lfjg.render.system.text.font;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.manager.WorkspaceManager;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.UnicodeBlock;
import me.hannsi.lfjg.render.system.text.TextMeshBuilder;
import me.hannsi.lfjg.render.system.text.msdf.MSDFFont;
import me.hannsi.lfjg.render.system.text.msdf.MSDFGenerator;
import me.hannsi.lfjg.render.system.text.msdf.MSDFJsonLoader;
import me.hannsi.lfjg.render.system.text.msdf.MSDFTextureLoader;
import me.hannsi.lfjg.render.system.text.msdf.type.AtlasSizeType;
import me.hannsi.lfjg.render.system.text.msdf.type.AtlasType;
import me.hannsi.lfjg.render.system.text.msdf.type.AtlasYOriginType;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Font {
    protected MSDFGenerator msdfGenerator;
    protected MSDFFont msdfFont;
    protected MSDFTextureLoader msdfTextureLoader;
    protected TextMeshBuilder textMeshBuilder;
    protected Location textureLocation;
    protected Location jsonLocation;

    private String name;

    public Font() throws IOException {
        this.msdfGenerator = MSDFGenerator.createMSDFGenerator()
                .type(AtlasType.MSDF)
                .atlasSize(AtlasSizeType.SQUARE4, true)
                .yOrigin(AtlasYOriginType.BOTTOM);
    }

    public static Font createCustomFont() {
        try {
            return new Font();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanup() {
        msdfGenerator.cleanup();
        msdfTextureLoader.cleanup();
        textMeshBuilder.cleanup();
    }

    public Font createCache(){
        try {
            boolean success = msdfGenerator.generate();

            if (!success) {
                DebugLog.error(getClass(),"Process finished with errors.");
            }

            textureLocation = msdfGenerator.getTextureOutputLocation();
            jsonLocation = msdfGenerator.getJsonOutputLocation();

            msdfFont = MSDFJsonLoader.createMSDFJsonLoader()
                    .jsonLocation(jsonLocation)
                    .parseFile();
            msdfTextureLoader = MSDFTextureLoader.createMSDFTextureLoader()
                    .textureLocation(textureLocation)
                    .loadTexture();
            textMeshBuilder = TextMeshBuilder.createTextMeshBuilder()
                    .msdfFont(msdfFont)
                    .chars(msdfGenerator.getChars())
                    .generateMeshData();
        }catch (IOException e){
            DebugLog.error(getClass(),e);
        }

        return this;
    }

    public Font name(String name) {
        this.name = name;

        msdfGenerator.outputName(WorkspaceManager.currentWorkspace + "/font/" + name + "/" + name);

        return this;
    }

    public Font trueTypeFontPath(Location ttfPath) {
        msdfGenerator.ttfPath(ttfPath);
        return this;
    }

    public Font unicodeBlocks(List<UnicodeBlock> unicodeBlocks){
        msdfGenerator.unicodeBlock(unicodeBlocks);

        return this;
    }

    public String getName() {
        return name;
    }

    public MSDFGenerator getMsdfGenerator() {
        return msdfGenerator;
    }

    public MSDFFont getMsdfFont() {
        return msdfFont;
    }

    public MSDFTextureLoader getMsdfTextureLoader() {
        return msdfTextureLoader;
    }

    public TextMeshBuilder getTextMeshBuilder() {
        return textMeshBuilder;
    }

    public Location getTextureLocation() {
        return textureLocation;
    }

    public Location getJsonLocation() {
        return jsonLocation;
    }
}
