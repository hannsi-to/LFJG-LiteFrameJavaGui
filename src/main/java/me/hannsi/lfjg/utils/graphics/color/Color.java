package me.hannsi.lfjg.utils.graphics.color;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

public class Color {
    public static final Color white = new Color(255, 255, 255);
    public static final Color WHITE = white;
    public static final Color lightGray = new Color(192, 192, 192);
    public static final Color LIGHT_GRAY = lightGray;
    public static final Color gray = new Color(128, 128, 128);
    public static final Color GRAY = gray;
    public static final Color darkGray = new Color(64, 64, 64);
    public static final Color DARK_GRAY = darkGray;
    public static final Color black = new Color(0, 0, 0);
    public static final Color BLACK = black;
    public static final Color red = new Color(255, 0, 0);
    public static final Color RED = red;
    public static final Color pink = new Color(255, 175, 175);
    public static final Color PINK = pink;
    public static final Color orange = new Color(255, 200, 0);
    public static final Color ORANGE = orange;
    public static final Color yellow = new Color(255, 255, 0);
    public static final Color YELLOW = yellow;
    public static final Color green = new Color(0, 255, 0);
    public static final Color GREEN = green;
    public static final Color magenta = new Color(255, 0, 255);
    public static final Color MAGENTA = magenta;
    public static final Color cyan = new Color(0, 255, 255);
    public static final Color CYAN = cyan;
    public static final Color blue = new Color(0, 0, 255);
    public static final Color BLUE = blue;

    private final java.awt.Color value;

    public Color(java.awt.Color value) {
        this.value = value;
    }

    public Color(int r, int g, int b) {
        this(new java.awt.Color(r, g, b, 255));
    }

    public Color(int r, int g, int b, int a) {
        this(new java.awt.Color(r, g, b, a));
    }

    public Color(int rgb) {
        this(new java.awt.Color(rgb));
    }

    public Color(int rgba, boolean hasalpha) {
        this(new java.awt.Color(rgba, hasalpha));
    }

    public Color(float r, float g, float b) {
        this(new java.awt.Color(r, g, b));
    }

    public Color(float r, float g, float b, float a) {
        this(new java.awt.Color(r, g, b, a));
    }

    public Color(ColorSpace cspace, float[] components, float alpha) {
        this(new java.awt.Color(cspace, components, alpha));
    }

    public Color(String nm) {
        this(new java.awt.Color((Integer.decode(nm) >> 16) & 0xFF, (Integer.decode(nm) >> 8) & 0xFF, Integer.decode(nm) & 0xFF));
    }

    public static Color decode(String nm) throws NumberFormatException {
        int i = Integer.decode(nm);
        return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
    }

    public static Color getColor(String nm) {
        return getColor(nm, null);
    }

    public static Color getColor(String nm, Color v) {
        Integer intval = Integer.getInteger(nm);
        if (intval == null) {
            return v;
        }
        int i = intval;
        return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
    }

    public static Color getColor(String nm, int v) {
        Integer intval = Integer.getInteger(nm);
        int i = (intval != null) ? intval : v;
        return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, (i >> 0) & 0xFF);
    }

    public static int HSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float) Math.floor(hue)) * 6.0f;
            float f = h - (float) java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
                case 0:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (t * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 1:
                    r = (int) (q * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 2:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (t * 255.0f + 0.5f);
                    break;
                case 3:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (q * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 4:
                    r = (int) (t * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 5:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (q * 255.0f + 0.5f);
                    break;
            }
        }
        return 0xff000000 | (r << 16) | (g << 8) | (b);
    }

    public static float[] RGBtoHSB(int r, int g, int b, float[] hsbvals) {
        float hue, saturation, brightness;
        if (hsbvals == null) {
            hsbvals = new float[3];
        }
        int cmax = Math.max(r, g);
        if (b > cmax) {
            cmax = b;
        }
        int cmin = Math.min(r, g);
        if (b < cmin) {
            cmin = b;
        }

        brightness = ((float) cmax) / 255.0f;
        if (cmax != 0) {
            saturation = ((float) (cmax - cmin)) / ((float) cmax);
        } else {
            saturation = 0;
        }
        if (saturation == 0) {
            hue = 0;
        } else {
            float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
            float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
            float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
            if (r == cmax) {
                hue = bluec - greenc;
            } else if (g == cmax) {
                hue = 2.0f + redc - bluec;
            } else {
                hue = 4.0f + greenc - redc;
            }
            hue = hue / 6.0f;
            if (hue < 0) {
                hue = hue + 1.0f;
            }
        }
        hsbvals[0] = hue;
        hsbvals[1] = saturation;
        hsbvals[2] = brightness;
        return hsbvals;
    }

    public static Color getHSBColor(float h, float s, float b) {
        return new Color(HSBtoRGB(h, s, b));
    }

    public int getRed() {
        return this.value.getRed();
    }

    public int getGreen() {
        return this.value.getGreen();
    }

    public int getBlue() {
        return this.value.getBlue();
    }

    public int getAlpha() {
        return this.value.getAlpha();
    }

    public int getRGB() {
        return this.value.getRGB();
    }

    public float getRedF() {
        return this.value.getRed() / 255f;
    }

    public float getGreenF() {
        return this.value.getGreen() / 255f;
    }

    public float getBlueF() {
        return this.value.getBlue() / 255f;
    }

    public float getAlphaF() {
        return this.value.getAlpha() / 255f;
    }

    public Color setRed(Color color, float red) {
        return ColorUtil.fixColorRange((int) (red * 255.0f), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public Color setGreen(Color color, float green) {
        return ColorUtil.fixColorRange(color.getRed(), (int) (green * 255.0f), color.getBlue(), color.getAlpha());
    }

    public Color setBlue(Color color, float blue) {
        return ColorUtil.fixColorRange(color.getRed(), color.getGreen(), (int) (blue * 255.0f), color.getAlpha());
    }

    public Color setAlpha(Color color, float alpha) {
        return ColorUtil.fixColorRange(color.getRed(), color.getGreen(), color.getBlue(), (int) (alpha * 255.0f));
    }

    public Color setRed(java.awt.Color color, int red) {
        return ColorUtil.fixColorRange(red, color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public Color setGreen(Color color, int green) {
        return ColorUtil.fixColorRange(color.getRed(), green, color.getBlue(), color.getAlpha());
    }

    public Color setBlue(Color color, int blue) {
        return ColorUtil.fixColorRange(color.getRed(), color.getGreen(), blue, color.getAlpha());
    }

    public Color setAlpha(Color color, int alpha) {
        return ColorUtil.fixColorRange(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public Color brighter() {
        return new Color(this.value.brighter());
    }

    public Color darker() {
        return new Color(this.value.darker());
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public boolean equals(Color color) {
        return color.getRGB() == this.getRGB();
    }

    public String toString() {
        return getClass().getName() + "[r=" + getRed() + ",g=" + getGreen() + ",b=" + getBlue() + "]";
    }

    public float[] getRGBComponents(float[] compArray) {
        return this.value.getRGBComponents(compArray);
    }

    public float[] getRGBColorComponents(float[] compArray) {
        return this.value.getRGBColorComponents(compArray);
    }

    public float[] getComponents(float[] compArray) {
        return this.value.getComponents(compArray);
    }

    public float[] getColorComponents(float[] compArray) {
        return this.value.getColorComponents(compArray);
    }

    public float[] getComponents(ColorSpace cspace, float[] compArray) {
        return this.value.getComponents(cspace, compArray);
    }

    public float[] getColorComponents(ColorSpace cspace, float[] compArray) {
        return this.value.getColorComponents(cspace, compArray);
    }

    public ColorSpace getColorSpace() {
        return this.value.getColorSpace();
    }

    public synchronized PaintContext createContext(ColorModel cm, Rectangle r, Rectangle2D r2d, AffineTransform xform, RenderingHints hints) {
        return this.value.createContext(cm, r, r2d, xform, hints);
    }

    public int getTransparency() {
        return this.value.getTransparency();
    }
}