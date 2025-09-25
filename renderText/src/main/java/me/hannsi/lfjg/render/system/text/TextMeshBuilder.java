package me.hannsi.lfjg.render.system.text;

import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.system.mesh.Mesh;
import me.hannsi.lfjg.render.system.text.msdf.MSDFFont;

import java.util.HashMap;
import java.util.Map;

public class TextMeshBuilder {
    protected Map<Integer, TextMesh> textMeshMap;

    private MSDFFont msdfFont;
    private Map<Integer, MSDFFont.Glyph> glyphMap;
    private char[] chars;

    TextMeshBuilder() {
        textMeshMap = new HashMap<>();
    }

    public static TextMeshBuilder createTextMeshBuilder() {
        return new TextMeshBuilder();
    }

    public void cleanup() {
        textMeshMap.clear();
        textMeshMap = null;
        msdfFont.cleanup();
        glyphMap.clear();
        glyphMap = null;
        chars = null;
    }

    public TextMeshBuilder msdfFont(MSDFFont msdfFont) {
        this.msdfFont = msdfFont;
        this.glyphMap = msdfFont.getGlyphMap();

        return this;
    }

    public TextMeshBuilder chars(char[] chars) {
        this.chars = chars;

        return this;
    }

    public TextMeshBuilder generateMeshData() {
        float atlasWidth = msdfFont.getAtlas().getWidth();
        float atlasHeight = msdfFont.getAtlas().getHeight();

        boolean flipY = "bottom".equalsIgnoreCase(msdfFont.getAtlas().getyOrigin());

        for (char aChar : chars) {
            MSDFFont.Glyph glyph = glyphMap.get((int) aChar);
            if (glyph == null || glyph.getPlaneBounds() == null) {
                continue;
            }

            float x0d = glyph.getPlaneBounds().getLeft();
            float y0d = glyph.getPlaneBounds().getBottom();
            float x1d = glyph.getPlaneBounds().getRight();
            float y1d = glyph.getPlaneBounds().getTop();

            float u0d = glyph.getAtlasBounds().getLeft() / atlasWidth;
            float v0d = glyph.getAtlasBounds().getBottom() / atlasHeight;
            float u1d = glyph.getAtlasBounds().getRight() / atlasWidth;
            float v1d = glyph.getAtlasBounds().getTop() / atlasHeight;

            if (flipY) {
                v0d = (atlasHeight - glyph.getAtlasBounds().getBottom()) / atlasHeight;
                v1d = (atlasHeight - glyph.getAtlasBounds().getTop()) / atlasHeight;
            }

            float[] px = {
                    x0d, y0d,
                    x1d, y0d,
                    x1d, y1d,
                    x1d, y1d,
                    x0d, y1d,
                    x0d, y0d
            };

            float[] uv = {
                    u0d, v0d,
                    u1d, v0d,
                    u1d, v1d,
                    u1d, v1d,
                    u0d, v1d,
                    u0d, v0d
            };

            textMeshMap.put((int) aChar, new TextMesh(px, uv).createMesh());
        }

        return this;
    }

    public MSDFFont getMsdfFont() {
        return msdfFont;
    }

    public Map<Integer, MSDFFont.Glyph> getGlyphMap() {
        return glyphMap;
    }

    public char[] getChars() {
        return chars;
    }

    public Map<Integer, TextMesh> getTextMeshMap() {
        return textMeshMap;
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
