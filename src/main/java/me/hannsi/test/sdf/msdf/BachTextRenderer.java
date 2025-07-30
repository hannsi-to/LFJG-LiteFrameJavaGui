package me.hannsi.test.sdf.msdf;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class BachTextRenderer {
    private final char[] chars;
    private final float x;
    private final float y;
    private final float size;

    private final List<TextRenderer> renderers = new ArrayList<>();

    private MSDFTextureLoader msdfTextureLoader;
    private TextMeshBuilder textMeshBuilder;

    public BachTextRenderer(String text, float x, float y, float size) {
        this.chars = text.toCharArray();
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public BachTextRenderer msdfTextureLoader(MSDFTextureLoader loader) {
        this.msdfTextureLoader = loader;
        return this;
    }

    public BachTextRenderer textMeshBuilder(TextMeshBuilder builder) {
        this.textMeshBuilder = builder;
        return this;
    }

    public void init() {
        float cursorX = x;
        float cursorY = y;

        for (char c : chars) {
            MSDFFont.Glyph glyph = textMeshBuilder.getGlyphMap().get((int) c);
            if (glyph == null) {
                continue;
            }

            if (glyph.getPlaneBounds() == null || glyph.getAtlasBounds() == null) {
                cursorX += (float) (glyph.getAdvance() * size);
                continue;
            }

            float advance = (float) glyph.getAdvance();

            TextRenderer tr = TextRenderer.createTextRender()
                    .msdfTextureLoader(msdfTextureLoader)
                    .textMeshBuilder(textMeshBuilder)
                    .c(c)
                    .pos(new Vector2f(cursorX, cursorY))
                    .size((int) size)
                    .init();

            renderers.add(tr);

            cursorX += advance * size;
        }
    }

    public void draw() {
        for (TextRenderer renderer : renderers) {
            renderer.draw();
        }
    }
}
