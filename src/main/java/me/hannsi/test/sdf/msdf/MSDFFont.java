package me.hannsi.test.sdf.msdf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MSDFFont {
    private Atlas atlas;
    private Metrics metrics;
    private List<Glyph> glyphs;
    private List<Object> kerning;

    public Map<Integer, Glyph> getGlyphMap() {
        Map<Integer, Glyph> map = new HashMap<>();
        for (Glyph glyph : glyphs) {
            map.put(glyph.getUnicode(), glyph);
        }
        return map;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MSDFFont {\n");
        sb.append("\tAtlas: ").append(atlas != null ? atlas.toString() : "null").append("\n");
        sb.append("\tMetrics: ").append(metrics != null ? metrics.toString() : "null").append("\n");
        sb.append("\tGlyphs:\n");
        if (glyphs != null) {
            for (Glyph g : glyphs) {
                sb.append("\t\t").append(g.toString().replace("\n", "\n\t\t")).append("\n");
            }
        } else {
            sb.append("\t\tnull\n");
        }
        sb.append("\tKerning:\n");
        if (kerning != null) {
            for (Object k : kerning) {
                sb.append("\t\t").append(k.toString()).append("\n");
            }
        } else {
            sb.append("\t\tnull\n");
        }
        sb.append("}");
        return sb.toString();
    }

    public Atlas getAtlas() {
        return atlas;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public List<Glyph> getGlyphs() {
        return glyphs;
    }

    public List<Object> getKerning() {
        return kerning;
    }

    public static class Atlas {
        private String type;
        private int distanceRange;
        private float distanceRangeMiddle;
        private float size;
        private int width;
        private int height;
        private String yOrigin;

        @Override
        public String toString() {
            return String.format("Atlas {\n\t\ttype='%s', distanceRange=%d, distanceRangeMiddle=%.2f, size=%.2f, width=%d, height=%d, yOrigin='%s'\n\t}", type, distanceRange, distanceRangeMiddle, size, width, height, yOrigin);
        }

        public String getType() {
            return type;
        }

        public int getDistanceRange() {
            return distanceRange;
        }

        public float getDistanceRangeMiddle() {
            return distanceRangeMiddle;
        }

        public float getSize() {
            return size;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public String getyOrigin() {
            return yOrigin;
        }
    }


    public static class Metrics {
        private float emSize;
        private float lineHeight;
        private float ascender;
        private float descender;
        private float underlineY;
        private float underlineThickness;

        @Override
        public String toString() {
            return String.format("Metrics {\n\t\temSize=%.2f, lineHeight=%.2f, ascender=%.2f, descender=%.2f, underlineY=%.2f, underlineThickness=%.2f\n\t}", emSize, lineHeight, ascender, descender, underlineY, underlineThickness);
        }

        public float getEmSize() {
            return emSize;
        }

        public float getLineHeight() {
            return lineHeight;
        }

        public float getAscender() {
            return ascender;
        }

        public float getDescender() {
            return descender;
        }

        public float getUnderlineY() {
            return underlineY;
        }

        public float getUnderlineThickness() {
            return underlineThickness;
        }
    }

    public static class Glyph {
        private int unicode;
        private float advance;
        private PlaneBounds planeBounds;
        private AtlasBounds atlasBounds;

        @Override
        public String toString() {
            return String.format("Glyph {\n\tunicode=U+%04X, advance=%.2f, planeBounds=%s, atlasBounds=%s\n}", unicode, advance, planeBounds, atlasBounds);
        }

        public int getUnicode() {
            return unicode;
        }

        public float getAdvance() {
            return advance;
        }

        public PlaneBounds getPlaneBounds() {
            return planeBounds;
        }

        public AtlasBounds getAtlasBounds() {
            return atlasBounds;
        }
    }

    public static class PlaneBounds {
        private float left;
        private float bottom;
        private float right;
        private float top;

        @Override
        public String toString() {
            return String.format("PlaneBounds {\n\t\tleft=%.2f, bottom=%.2f, right=%.2f, top=%.2f\n\t}", left, bottom, right, top);
        }

        public float getLeft() {
            return left;
        }

        public float getBottom() {
            return bottom;
        }

        public float getRight() {
            return right;
        }

        public float getTop() {
            return top;
        }
    }

    public static class AtlasBounds {
        private float left;
        private float bottom;
        private float right;
        private float top;

        @Override
        public String toString() {
            return String.format("AtlasBounds {\n\t\tleft=%.2f, bottom=%.2f, right=%.2f, top=%.2f\n\t}", left, bottom, right, top);
        }

        public float getLeft() {
            return left;
        }

        public float getBottom() {
            return bottom;
        }

        public float getRight() {
            return right;
        }

        public float getTop() {
            return top;
        }
    }
}
