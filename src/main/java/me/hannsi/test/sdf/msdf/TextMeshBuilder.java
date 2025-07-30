package me.hannsi.test.sdf.msdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextMeshBuilder {
    private MSDFFont msdfFont;
    private Map<Integer, MSDFFont.Glyph> glyphMap;
    private TextMeshBuilder.TextMesh textMesh;

    TextMeshBuilder() {

    }

    public static TextMeshBuilder createTextMeshBuilder() {
        return new TextMeshBuilder();
    }

    public TextMeshBuilder msdfFont(MSDFFont msdfFont) {
        this.msdfFont = msdfFont;
        this.glyphMap = msdfFont.getGlyphMap();

        return this;
    }

    public TextMeshBuilder generateMeshData(String text) {
        List<Float> positionList = new ArrayList<>();
        List<Float> uvList = new ArrayList<>();
        float cursorX = 0.0f;
        float cursorY = 0.0f;

        float atlasWidth = msdfFont.getAtlas().getWidth();
        float atlasHeight = msdfFont.getAtlas().getHeight();

        boolean flipY = "bottom".equalsIgnoreCase(msdfFont.getAtlas().getyOrigin());

        for (int i = 0; i < text.length(); ) {
            int charCode = text.codePointAt(i);
            i += Character.charCount(charCode);

            if (charCode == '\n') {
                cursorY -= (float) msdfFont.getMetrics().getLineHeight();
                cursorX = 0;
                continue;
            }

            MSDFFont.Glyph glyph = glyphMap.get(charCode);
            if (glyph == null || glyph.getPlaneBounds() == null) {
                continue;
            }

            float x0 = cursorX + (float) glyph.getPlaneBounds().getLeft();
            float y0 = cursorY + (float) glyph.getPlaneBounds().getBottom();
            float x1 = cursorX + (float) glyph.getPlaneBounds().getRight();
            float y1 = cursorY + (float) glyph.getPlaneBounds().getTop();

            float u0 = (float) (glyph.getAtlasBounds().getLeft() / atlasWidth);
            float v0 = (float) (glyph.getAtlasBounds().getBottom() / atlasHeight);
            float u1 = (float) (glyph.getAtlasBounds().getRight() / atlasWidth);
            float v1 = (float) (glyph.getAtlasBounds().getTop() / atlasHeight);

            if (flipY) {
                v0 = (float) ((atlasHeight - glyph.getAtlasBounds().getBottom()) / atlasHeight);
                v1 = (float) ((atlasHeight - glyph.getAtlasBounds().getTop()) / atlasHeight);
            }

            float[] px = {
                    x0, y0, x1, y0, x1, y1,
                    x1, y1, x0, y1, x0, y0
            };
            float[] uv = {
                    u0, v0, u1, v0, u1, v1,
                    u1, v1, u0, v1, u0, v0
            };

            for (float v : px) {
                positionList.add(v);
            }
            for (float v : uv) {
                uvList.add(v);
            }

            cursorX += (float) glyph.getAdvance();
        }

        float[] positions = new float[positionList.size()];
        float[] uvs = new float[uvList.size()];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = positionList.get(i);
        }
        for (int i = 0; i < uvs.length; i++) {
            uvs[i] = uvList.get(i);
        }

        textMesh = new TextMesh(positions, uvs);

        return this;
    }

    public MSDFFont getMsdfFont() {
        return msdfFont;
    }

    public void setMsdfFont(MSDFFont msdfFont) {
        this.msdfFont = msdfFont;
    }

    public Map<Integer, MSDFFont.Glyph> getGlyphMap() {
        return glyphMap;
    }

    public void setGlyphMap(Map<Integer, MSDFFont.Glyph> glyphMap) {
        this.glyphMap = glyphMap;
    }

    public TextMesh getTextMesh() {
        return textMesh;
    }

    public void setTextMesh(TextMesh textMesh) {
        this.textMesh = textMesh;
    }

    public record TextMesh(float[] positions, float[] uvs) {
    }
}
