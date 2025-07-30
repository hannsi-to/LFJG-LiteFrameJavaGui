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

    public void setAtlas(Atlas atlas) {
        this.atlas = atlas;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    public List<Glyph> getGlyphs() {
        return glyphs;
    }

    public void setGlyphs(List<Glyph> glyphs) {
        this.glyphs = glyphs;
    }

    public List<Object> getKerning() {
        return kerning;
    }

    public void setKerning(List<Object> kerning) {
        this.kerning = kerning;
    }

    public static class Atlas {
        private String type;
        private int distanceRange;
        private double distanceRangeMiddle;
        private double size;
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

        public void setType(String type) {
            this.type = type;
        }

        public int getDistanceRange() {
            return distanceRange;
        }

        public void setDistanceRange(int distanceRange) {
            this.distanceRange = distanceRange;
        }

        public double getDistanceRangeMiddle() {
            return distanceRangeMiddle;
        }

        public void setDistanceRangeMiddle(double distanceRangeMiddle) {
            this.distanceRangeMiddle = distanceRangeMiddle;
        }

        public double getSize() {
            return size;
        }

        public void setSize(double size) {
            this.size = size;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getyOrigin() {
            return yOrigin;
        }

        public void setyOrigin(String yOrigin) {
            this.yOrigin = yOrigin;
        }
    }


    public static class Metrics {
        private double emSize;
        private double lineHeight;
        private double ascender;
        private double descender;
        private double underlineY;
        private double underlineThickness;

        @Override
        public String toString() {
            return String.format("Metrics {\n\t\temSize=%.2f, lineHeight=%.2f, ascender=%.2f, descender=%.2f, underlineY=%.2f, underlineThickness=%.2f\n\t}", emSize, lineHeight, ascender, descender, underlineY, underlineThickness);
        }

        public double getEmSize() {
            return emSize;
        }

        public void setEmSize(double emSize) {
            this.emSize = emSize;
        }

        public double getLineHeight() {
            return lineHeight;
        }

        public void setLineHeight(double lineHeight) {
            this.lineHeight = lineHeight;
        }

        public double getAscender() {
            return ascender;
        }

        public void setAscender(double ascender) {
            this.ascender = ascender;
        }

        public double getDescender() {
            return descender;
        }

        public void setDescender(double descender) {
            this.descender = descender;
        }

        public double getUnderlineY() {
            return underlineY;
        }

        public void setUnderlineY(double underlineY) {
            this.underlineY = underlineY;
        }

        public double getUnderlineThickness() {
            return underlineThickness;
        }

        public void setUnderlineThickness(double underlineThickness) {
            this.underlineThickness = underlineThickness;
        }
    }

    public static class Glyph {
        private int unicode;
        private double advance;
        private PlaneBounds planeBounds;
        private AtlasBounds atlasBounds;

        @Override
        public String toString() {
            return String.format("Glyph {\n\tunicode=U+%04X, advance=%.2f, planeBounds=%s, atlasBounds=%s\n}", unicode, advance, planeBounds, atlasBounds);
        }

        public int getUnicode() {
            return unicode;
        }

        public void setUnicode(int unicode) {
            this.unicode = unicode;
        }

        public double getAdvance() {
            return advance;
        }

        public void setAdvance(double advance) {
            this.advance = advance;
        }

        public PlaneBounds getPlaneBounds() {
            return planeBounds;
        }

        public void setPlaneBounds(PlaneBounds planeBounds) {
            this.planeBounds = planeBounds;
        }

        public AtlasBounds getAtlasBounds() {
            return atlasBounds;
        }

        public void setAtlasBounds(AtlasBounds atlasBounds) {
            this.atlasBounds = atlasBounds;
        }
    }

    public static class PlaneBounds {
        private double left;
        private double bottom;
        private double right;
        private double top;

        @Override
        public String toString() {
            return String.format("PlaneBounds {\n\t\tleft=%.2f, bottom=%.2f, right=%.2f, top=%.2f\n\t}", left, bottom, right, top);
        }

        public double getLeft() {
            return left;
        }

        public void setLeft(double left) {
            this.left = left;
        }

        public double getBottom() {
            return bottom;
        }

        public void setBottom(double bottom) {
            this.bottom = bottom;
        }

        public double getRight() {
            return right;
        }

        public void setRight(double right) {
            this.right = right;
        }

        public double getTop() {
            return top;
        }

        public void setTop(double top) {
            this.top = top;
        }
    }

    public static class AtlasBounds {
        private double left;
        private double bottom;
        private double right;
        private double top;

        @Override
        public String toString() {
            return String.format("AtlasBounds {\n\t\tleft=%.2f, bottom=%.2f, right=%.2f, top=%.2f\n\t}", left, bottom, right, top);
        }

        public double getLeft() {
            return left;
        }

        public void setLeft(double left) {
            this.left = left;
        }

        public double getBottom() {
            return bottom;
        }

        public void setBottom(double bottom) {
            this.bottom = bottom;
        }

        public double getRight() {
            return right;
        }

        public void setRight(double right) {
            this.right = right;
        }

        public double getTop() {
            return top;
        }

        public void setTop(double top) {
            this.top = top;
        }
    }
}
