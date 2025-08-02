package me.hannsi.test.sdf.msdf;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.system.mesh.Mesh;

import java.util.HashMap;
import java.util.Map;

public class TextMeshBuilder {
    protected Map<Integer, TextMesh> textMeshMap;

    private MSDFFont msdfFont;
    private Map<Integer, MSDFFont.Glyph> glyphMap;
    private String text;

    TextMeshBuilder() {
        textMeshMap = new HashMap<>();
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
        this.text = text;

        float atlasWidth = msdfFont.getAtlas().getWidth();
        float atlasHeight = msdfFont.getAtlas().getHeight();

        boolean flipY = "bottom".equalsIgnoreCase(msdfFont.getAtlas().getyOrigin());

        for (int i = 0; i < text.length(); ) {
            int charCode = text.codePointAt(i);
            i += Character.charCount(charCode);

            MSDFFont.Glyph glyph = glyphMap.get(charCode);
            if (glyph == null || glyph.getPlaneBounds() == null) {
                continue;
            }

            float x0 = (float) glyph.getPlaneBounds().getLeft();
            float y0 = (float) glyph.getPlaneBounds().getBottom();
            float x1 = (float) glyph.getPlaneBounds().getRight();
            float y1 = (float) glyph.getPlaneBounds().getTop();

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

            textMeshMap.put(charCode, new TextMesh(px, uv).createMesh());
        }

        return this;
    }

    public Map<Integer, TextMesh> getTextMeshMap() {
        return textMeshMap;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class TextMesh {
        public Mesh mesh;
        public float[] positions;
        public float[] uvs;

        public TextMesh(float[] positions, float[] uvs) {
            this.positions = positions;
            this.uvs = uvs;
        }

        public TextMesh createMesh() {
            mesh = Mesh.createMesh()
                    .projectionType(ProjectionType.ORTHOGRAPHIC_PROJECTION)
                    .useElementBufferObject(false)
                    .createBufferObject2D(positions, null, uvs);

            return this;
        }
    }
}
