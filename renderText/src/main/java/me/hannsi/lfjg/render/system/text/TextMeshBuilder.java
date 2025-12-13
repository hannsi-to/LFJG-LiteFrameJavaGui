package me.hannsi.lfjg.render.system.text;

import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.render.system.mesh.TestMesh;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.text.msdf.MSDFFont;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.hannsi.lfjg.render.LFJGRenderContext.MESH;

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
        private static final IntRef pointer = new IntRef();
        protected static List<Integer> ids = new ArrayList<>();
        public float[] positions;
        public float[] uvs;

        public TextMesh(float[] positions, float[] uvs) {
            this.positions = positions;
            this.uvs = uvs;
        }

        public TextMesh createMesh() {
            MESH.addObject(
                    TestMesh.Builder.createBuilder()
                            .objectIdPointer(pointer)
                            .drawType(DrawType.TRIANGLES)
                            .blendType(BlendType.NORMAL)
                            .pointSize(-1f)
                            .lineWidth(-1f)
                            .jointType(GLPolygon.DEFAULT_JOINT_TYPE)
                            .pointType(GLPolygon.DEFAULT_POINT_TYPE)
                            .vertices(
                                    new Vertex(positions[0], positions[1], 0, 0, 0, 0, 0, uvs[0], uvs[1], 0, 0, 0),
                                    new Vertex(positions[2], positions[3], 0, 0, 0, 0, 0, uvs[2], uvs[3], 0, 0, 0),
                                    new Vertex(positions[4], positions[5], 0, 0, 0, 0, 0, uvs[4], uvs[5], 0, 0, 0),
                                    new Vertex(positions[6], positions[7], 0, 0, 0, 0, 0, uvs[6], uvs[7], 0, 0, 0),
                                    new Vertex(positions[8], positions[9], 0, 0, 0, 0, 0, uvs[8], uvs[9], 0, 0, 0),
                                    new Vertex(positions[10], positions[11], 0, 0, 0, 0, 0, uvs[10], uvs[11], 0, 0, 0)
                            )
            );

            ids.add(pointer.getValue());

            return this;
        }

        public void cleanup() {
            for (int id : ids) {
                MESH.deleteObject(id);
            }
        }
    }
}
