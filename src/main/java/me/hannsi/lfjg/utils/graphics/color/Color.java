package me.hannsi.lfjg.utils.graphics.color;

import me.hannsi.lfjg.utils.math.MathHelper;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

import static me.hannsi.lfjg.utils.math.MathHelper.*;

/**
 * Utility class for handling colors.
 */
public class Color {
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color LIGHT_GRAY = new Color(192, 192, 192);
    public static final Color GRAY = new Color(128, 128, 128);
    public static final Color DARK_GRAY = new Color(64, 64, 64);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color PINK = new Color(255, 175, 175);
    public static final Color ORANGE = new Color(255, 200, 0);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color MAGENTA = new Color(255, 0, 255);
    public static final Color CYAN = new Color(0, 255, 255);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color PURPLE = new Color(128, 0, 128);
    public static final Color BROWN = new Color(165, 42, 42);
    public static final Color GOLD = new Color(255, 215, 0);
    public static final Color SILVER = new Color(192, 192, 192);
    public static final Color OLIVE = new Color(128, 128, 0);
    public static final Color TEAL = new Color(0, 128, 128);
    public static final Color NAVY = new Color(0, 0, 128);
    public static final Color LIME = new Color(50, 205, 50);
    public static final Color INDIGO = new Color(75, 0, 130);
    public static final Color VIOLET = new Color(238, 130, 238);
    public static final Color MAROON = new Color(128, 0, 0);
    public static final Color CRIMSON = new Color(220, 20, 60);
    public static final Color SALMON = new Color(250, 128, 114);
    public static final Color CORAL = new Color(255, 127, 80);
    public static final Color CHOCOLATE = new Color(210, 105, 30);
    public static final Color SIENNA = new Color(160, 82, 45);
    public static final Color KHAKI = new Color(240, 230, 140);
    public static final Color BEIGE = new Color(245, 245, 220);
    public static final Color IVORY = new Color(255, 255, 240);
    public static final Color LAVENDER = new Color(230, 230, 250);
    public static final Color PLUM = new Color(221, 160, 221);
    public static final Color ORCHID = new Color(218, 112, 214);
    public static final Color TURQUOISE = new Color(64, 224, 208);
    public static final Color SKY_BLUE = new Color(135, 206, 235);
    public static final Color DEEP_SKY_BLUE = new Color(0, 191, 255);
    public static final Color MIDNIGHT_BLUE = new Color(25, 25, 112);
    public static final Color SEA_GREEN = new Color(46, 139, 87);
    public static final Color FOREST_GREEN = new Color(34, 139, 34);
    public static final Color MINT = new Color(189, 252, 201);
    public static final Color PEACH = new Color(255, 218, 185);
    public static final Color PERIWINKLE = new Color(204, 204, 255);
    public static final Color ROSE = new Color(255, 228, 225);
    public static final Color HONEYDEW = new Color(240, 255, 240);

    private final java.awt.Color value;

    /**
     * Constructs a new Color instance from an existing java.awt.Color.
     *
     * @param value the java.awt.Color instance
     */
    public Color(java.awt.Color value) {
        this.value = value;
    }

    /**
     * Constructs a new Color instance with the specified RGB values.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     */
    public Color(int r, int g, int b) {
        this(new java.awt.Color(r, g, b, 255));
    }

    /**
     * Constructs a new Color instance with the specified RGBA values.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     */
    public Color(int r, int g, int b, int a) {
        this(new java.awt.Color(r, g, b, a));
    }

    /**
     * Constructs a new Color instance with the specified RGB value.
     *
     * @param rgb the RGB value
     */
    public Color(int rgb) {
        this(new java.awt.Color(rgb));
    }

    /**
     * Constructs a new Color instance with the specified RGBA value and alpha flag.
     *
     * @param rgba     the RGBA value
     * @param hasalpha true if the alpha component is included
     */
    public Color(int rgba, boolean hasalpha) {
        this(new java.awt.Color(rgba, hasalpha));
    }

    /**
     * Constructs a new Color instance with the specified RGB float values.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     */
    public Color(float r, float g, float b) {
        this(new java.awt.Color(r, g, b));
    }

    /**
     * Constructs a new Color instance with the specified RGBA float values.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     */
    public Color(float r, float g, float b, float a) {
        this(new java.awt.Color(r, g, b, a));
    }

    /**
     * Constructs a new Color instance with the specified ColorSpace, components, and alpha value.
     *
     * @param cspace     the ColorSpace
     * @param components the color components
     * @param alpha      the alpha component
     */
    public Color(ColorSpace cspace, float[] components, float alpha) {
        this(new java.awt.Color(cspace, components, alpha));
    }

    /**
     * Constructs a new Color instance from a string representation of the color.
     *
     * @param nm the string representation of the color
     */
    public Color(String nm) {
        this(new java.awt.Color((Integer.decode(nm) >> 16) & 0xFF, (Integer.decode(nm) >> 8) & 0xFF, Integer.decode(nm) & 0xFF));
    }

    /**
     * Decodes a string representation of a color to a Color instance.
     *
     * @param nm the string representation of the color
     * @return the Color instance
     * @throws NumberFormatException if the string cannot be decoded
     */
    public static Color decode(String nm) throws NumberFormatException {
        int i = Integer.decode(nm);
        return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
    }

    /**
     * Gets a Color instance from a system property.
     *
     * @param nm the name of the system property
     * @return the Color instance, or null if the property is not found
     */
    public static Color getColor(String nm) {
        return getColor(nm, null);
    }

    /**
     * Gets a Color instance from a system property, with a default value.
     *
     * @param nm the name of the system property
     * @param v  the default value
     * @return the Color instance
     */
    public static Color getColor(String nm, Color v) {
        Integer intval = Integer.getInteger(nm);
        if (intval == null) {
            return v;
        }
        int i = intval;
        return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
    }

    /**
     * Gets a Color instance from a system property, with a default RGB value.
     *
     * @param nm the name of the system property
     * @param v  the default RGB value
     * @return the Color instance
     */
    public static Color getColor(String nm, int v) {
        Integer intval = Integer.getInteger(nm);
        int i = (intval != null) ? intval : v;
        return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, (i >> 0) & 0xFF);
    }

    /**
     * Converts HSB values to an RGB integer.
     *
     * @param hue        the hue component
     * @param saturation the saturation component
     * @param brightness the brightness component
     * @return the RGB integer
     */
    public static int HSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - floor(hue)) * 6.0f;
            float f = h - floor(h);
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

    /**
     * Converts RGB values to HSB values.
     *
     * @param r       the red component
     * @param g       the green component
     * @param b       the blue component
     * @param hsbvals the array to store the HSB values
     * @return the array containing the HSB values
     */
    public static float[] RGBtoHSB(int r, int g, int b, float[] hsbvals) {
        float hue, saturation, brightness;
        if (hsbvals == null) {
            hsbvals = new float[3];
        }
        int cmax = max(r, g);
        if (b > cmax) {
            cmax = b;
        }
        int cmin = min(r, g);
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

    /**
     * Gets a Color instance from HSB values.
     *
     * @param h the hue component
     * @param s the saturation component
     * @param b the brightness component
     * @return the Color instance
     */
    public static Color getHSBColor(float h, float s, float b) {
        return new Color(HSBtoRGB(h, s, b));
    }

    /**
     * Creates a new Color instance with the specified RGB values.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @return the new Color instance
     */
    public static Color of(int r, int g, int b) {
        return new Color(r, g, b);
    }

    /**
     * Creates a new Color instance with the specified RGBA values.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     * @return the new Color instance
     */
    public static Color of(int r, int g, int b, int a) {
        return new Color(r, g, b, a);
    }

    /**
     * Creates a new Color instance with the specified RGB value.
     *
     * @param rgb the RGB value
     * @return the new Color instance
     */
    public static Color of(int rgb) {
        return new Color(rgb);
    }

    /**
     * Creates a new Color instance with the specified RGBA value and alpha flag.
     *
     * @param rgba     the RGBA value
     * @param hasalpha true if the alpha component is included
     * @return the new Color instance
     */
    public static Color of(int rgba, boolean hasalpha) {
        return new Color(rgba, hasalpha);
    }

    /**
     * Creates a new Color instance with the specified RGB float values.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @return the new Color instance
     */
    public static Color of(float r, float g, float b) {
        return new Color(r, g, b);
    }

    /**
     * Creates a new Color instance with the specified RGBA float values.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     * @return the new Color instance
     */
    public static Color of(float r, float g, float b, float a) {
        return new Color(r, g, b, a);
    }

    /**
     * Creates a new Color instance with the specified ColorSpace, components, and alpha value.
     *
     * @param cspace     the ColorSpace
     * @param components the color components
     * @param alpha      the alpha component
     * @return the new Color instance
     */
    public static Color of(ColorSpace cspace, float[] components, float alpha) {
        return new Color(cspace, components, alpha);
    }

    /**
     * Creates a new Color instance from a string representation of the color.
     *
     * @param nm the string representation of the color
     * @return the new Color instance
     */
    public static Color of(String nm) {
        return new Color(nm);
    }

    public static Color getRandomColor(
            int redMin, int redMax,
            int greenMin, int greenMax,
            int blueMin, int blueMax,
            int alphaMin, int alphaMax
    ) {
        int red = MathHelper.getRandomInt(redMin, redMax);
        int green = MathHelper.getRandomInt(greenMin, greenMax);
        int blue = MathHelper.getRandomInt(blueMin, blueMax);
        int alpha = MathHelper.getRandomInt(alphaMin, alphaMax);
        return ColorUtil.fixColorRange(red, green, blue, alpha);
    }

    public static Color getRandomColor(
            float redMin, float redMax,
            float greenMin, float greenMax,
            float blueMin, float blueMax,
            float alphaMin, float alphaMax
    ) {
        return getRandomColor(
                redMin * 255, redMax * 255,
                greenMin * 255, greenMax * 255,
                blueMin * 255, blueMax * 255,
                alphaMin * 255, alphaMax * 255
        );
    }

    public static Color getRandomColor(
            int redMin, int redMax,
            int greenMin, int greenMax,
            int blueMin, int blueMax
    ) {
        return getRandomColor(redMin, redMax, greenMin, greenMax, blueMin, blueMax, 255, 255);
    }

    public static Color getRandomColor(
            float redMin, float redMax,
            float greenMin, float greenMax,
            float blueMin, float blueMax
    ) {
        return getRandomColor(redMin, redMax, greenMin, greenMax, blueMin, blueMax, 255, 255);
    }

    public static Color getRandomColor() {
        return getRandomColor(0, 255, 0, 255, 0, 255);
    }

    /**
     * Gets the red component of the color.
     *
     * @return the red component
     */
    public int getRed() {
        return this.value.getRed();
    }

    /**
     * Gets the green component of the color.
     *
     * @return the green component
     */
    public int getGreen() {
        return this.value.getGreen();
    }

    /**
     * Gets the blue component of the color.
     *
     * @return the blue component
     */
    public int getBlue() {
        return this.value.getBlue();
    }

    /**
     * Gets the alpha component of the color.
     *
     * @return the alpha component
     */
    public int getAlpha() {
        return this.value.getAlpha();
    }

    /**
     * Gets the RGB value of the color.
     *
     * @return the RGB value
     */
    public int getRGB() {
        return this.value.getRGB();
    }

    /**
     * Gets the red component of the color as a float.
     *
     * @return the red component as a float
     */
    public float getRedF() {
        return this.value.getRed() / 255f;
    }

    /**
     * Gets the green component of the color as a float.
     *
     * @return the green component as a float
     */
    public float getGreenF() {
        return this.value.getGreen() / 255f;
    }

    /**
     * Gets the blue component of the color as a float.
     *
     * @return the blue component as a float
     */
    public float getBlueF() {
        return this.value.getBlue() / 255f;
    }

    /**
     * Gets the alpha component of the color as a float.
     *
     * @return the alpha component as a float
     */
    public float getAlphaF() {
        return this.value.getAlpha() / 255f;
    }

    /**
     * Sets the red component of the color.
     *
     * @param color the original color
     * @param red   the new red component
     * @return the new Color instance
     */
    public Color setRed(Color color, float red) {
        return ColorUtil.fixColorRange((int) (red * 255.0f), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    /**
     * Sets the green component of the color.
     *
     * @param color the original color
     * @param green the new green component
     * @return the new Color instance
     */
    public Color setGreen(Color color, float green) {
        return ColorUtil.fixColorRange(color.getRed(), (int) (green * 255.0f), color.getBlue(), color.getAlpha());
    }

    /**
     * Sets the blue component of the color.
     *
     * @param color the original color
     * @param blue  the new blue component
     * @return the new Color instance
     */
    public Color setBlue(Color color, float blue) {
        return ColorUtil.fixColorRange(color.getRed(), color.getGreen(), (int) (blue * 255.0f), color.getAlpha());
    }

    /**
     * Sets the alpha component of the color.
     *
     * @param color the original color
     * @param alpha the new alpha component
     * @return the new Color instance
     */
    public Color setAlpha(Color color, float alpha) {
        return ColorUtil.fixColorRange(color.getRed(), color.getGreen(), color.getBlue(), (int) (alpha * 255.0f));
    }

    /**
     * Sets the red component of the color.
     *
     * @param color the original color
     * @param red   the new red component
     * @return the new Color instance
     */
    public Color setRed(java.awt.Color color, int red) {
        return ColorUtil.fixColorRange(red, color.getGreen(), color.getBlue(), color.getAlpha());
    }

    /**
     * Sets the green component of the color.
     *
     * @param color the original color
     * @param green the new green component
     * @return the new Color instance
     */
    public Color setGreen(Color color, int green) {
        return ColorUtil.fixColorRange(color.getRed(), green, color.getBlue(), color.getAlpha());
    }

    /**
     * Sets the blue component of the color.
     *
     * @param color the original color
     * @param blue  the new blue component
     * @return the new Color instance
     */
    public Color setBlue(Color color, int blue) {
        return ColorUtil.fixColorRange(color.getRed(), color.getGreen(), blue, color.getAlpha());
    }

    /**
     * Sets the alpha component of the color.
     *
     * @param color the original color
     * @param alpha the new alpha component
     * @return the new Color instance
     */
    public Color setAlpha(Color color, int alpha) {
        return ColorUtil.fixColorRange(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    /**
     * Returns a brighter version of the color.
     *
     * @return the brighter color
     */
    public Color brighter() {
        return new Color(this.value.brighter());
    }

    /**
     * Returns a darker version of the color.
     *
     * @return the darker color
     */
    public Color darker() {
        return new Color(this.value.darker());
    }

    /**
     * Returns the hash code of the color.
     *
     * @return the hash code
     */
    public int hashCode() {
        return this.value.hashCode();
    }

    /**
     * Checks if the color is equal to another color.
     *
     * @param color the other color
     * @return true if the colors are equal, false otherwise
     */
    public boolean equals(Color color) {
        return color.getRGB() == this.getRGB();
    }

    /**
     * Returns a string representation of the color.
     *
     * @return a string representation of the color
     */
    public String toString() {
        return getClass().getName() + "[r=" + getRed() + ",g=" + getGreen() + ",b=" + getBlue() + "]";
    }

    /**
     * Gets the RGB components of the color.
     *
     * @param compArray the array to store the RGB components
     * @return the array containing the RGB components
     */
    public float[] getRGBComponents(float[] compArray) {
        return this.value.getRGBComponents(compArray);
    }

    /**
     * Gets the RGB color components of the color.
     *
     * @param compArray the array to store the RGB color components
     * @return the array containing the RGB color components
     */
    public float[] getRGBColorComponents(float[] compArray) {
        return this.value.getRGBColorComponents(compArray);
    }

    /**
     * Gets the components of the color.
     *
     * @param compArray the array to store the components
     * @return the array containing the components
     */
    public float[] getComponents(float[] compArray) {
        return this.value.getComponents(compArray);
    }

    /**
     * Gets the color components of the color.
     *
     * @param compArray the array to store the color components
     * @return the array containing the color components
     */
    public float[] getColorComponents(float[] compArray) {
        return this.value.getColorComponents(compArray);
    }

    /**
     * Gets the components of the color in the specified ColorSpace.
     *
     * @param cspace    the ColorSpace
     * @param compArray the array to store the components
     * @return the array containing the components
     */
    public float[] getComponents(ColorSpace cspace, float[] compArray) {
        return this.value.getComponents(cspace, compArray);
    }

    /**
     * Gets the color components of the color in the specified ColorSpace.
     *
     * @param cspace    the ColorSpace
     * @param compArray the array to store the color components
     * @return the array containing the color components
     */
    public float[] getColorComponents(ColorSpace cspace, float[] compArray) {
        return this.value.getColorComponents(cspace, compArray);
    }

    /**
     * Gets the ColorSpace of the color.
     *
     * @return the ColorSpace
     */
    public ColorSpace getColorSpace() {
        return this.value.getColorSpace();
    }

    /**
     * Creates a PaintContext for the color.
     *
     * @param cm    the ColorModel
     * @param r     the Rectangle
     * @param r2d   the Rectangle2D
     * @param xform the AffineTransform
     * @param hints the RenderingHints
     * @return the PaintContext
     */
    public synchronized PaintContext createContext(ColorModel cm, Rectangle r, Rectangle2D r2d, AffineTransform xform, RenderingHints hints) {
        return this.value.createContext(cm, r, r2d, xform, hints);
    }

    /**
     * Gets the transparency of the color.
     *
     * @return the transparency
     */
    public int getTransparency() {
        return this.value.getTransparency();
    }

    /**
     * Gets the java.awt.Color instance.
     *
     * @return the java.awt.Color instance
     */
    public java.awt.Color ofAwtColor() {
        return value;
    }
}