package me.hannsi.lfjg.render.system.text.font;

import me.hannsi.lfjg.core.manager.WorkspaceManager;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.system.text.msdf.MSDFGenerator;
import me.hannsi.lfjg.render.system.text.msdf.type.AtlasSizeType;
import me.hannsi.lfjg.render.system.text.msdf.type.AtlasType;
import me.hannsi.lfjg.render.system.text.msdf.type.AtlasYOriginType;

import java.io.IOException;

public class Font {
    protected MSDFGenerator msdfGenerator;
    protected Location pngLocation;
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

    public Font name(String name) {
        this.name = name;

        msdfGenerator.outputName(WorkspaceManager.currentWorkspace + "/" + name);

        return this;
    }

    public Font trueTypeFontPath(Location ttfPath) {
        msdfGenerator.ttfPath(ttfPath);
        return this;
    }
}
