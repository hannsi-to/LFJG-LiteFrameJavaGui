package me.hannsi.lfjg.utils.graphics.color;

import me.hannsi.lfjg.utils.Util;

import static me.hannsi.lfjg.utils.math.MathHelper.*;

public class ColorUtil extends Util {

    /**
     * Generates a rainbow color based on the delay, timing, saturation, and brightness.
     *
     * @param delay      the delay in milliseconds
     * @param timing     the timing offset
     * @param saturation the saturation value
     * @param brightness the brightness value
     * @return the generated rainbow color
     */
    public static Color getRainbow(int delay, float timing, float saturation, float brightness) {
        float hue = System.currentTimeMillis() % (int) (delay * 1000.0f) / (delay * 1000.0f) + timing;
        return Color.getHSBColor(hue, saturation / 255f, brightness / 255f);
    }

    /**
     * Generates a rainbow color based on the delay, saturation, and brightness.
     *
     * @param delay      the delay in milliseconds
     * @param saturation the saturation value
     * @param brightness the brightness value
     * @return the generated rainbow color
     */
    public static Color getRainbow(int delay, float saturation, float brightness) {
        return getRainbow(delay, 0, saturation, brightness);
    }

    /**
     * Converts the given RGBA components to an ARGB integer.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     * @return the ARGB integer
     */
    public static int toARGB(final int r, final int g, final int b, final int a) {
        return fixColorRange(r, g, b, a).getRGB();
    }

    /**
     * Converts the given RGB components to an RGBA integer with full opacity.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @return the RGBA integer
     */
    public static int toRGBA(final int r, final int g, final int b) {
        return toRGBA(r, g, b, 255);
    }

    /**
     * Converts the given RGBA components to an RGBA integer.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     * @return the RGBA integer
     */
    public static int toRGBA(final int r, final int g, final int b, final int a) {
        return (r << 16) + (g << 8) + b + (a << 24);
    }

    /**
     * Converts the given RGBA float components to an RGBA integer.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     * @return the RGBA integer
     */
    public static int toRGBA(final float r, final float g, final float b, final float a) {
        return toRGBA((int) (r * 255.0f), (int) (g * 255.0f), (int) (b * 255.0f), (int) (a * 255.0f));
    }

    /**
     * Sets the red component of the given color.
     *
     * @param color the original color
     * @param red   the new red component
     * @return the new color with the updated red component
     */
    public static Color setRed(Color color, float red) {
        return fixColorRange((int) (red * 255.0f), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    /**
     * Sets the green component of the given color.
     *
     * @param color the original color
     * @param green the new green component
     * @return the new color with the updated green component
     */
    public static Color setGreen(Color color, float green) {
        return fixColorRange(color.getRed(), (int) (green * 255.0f), color.getBlue(), color.getAlpha());
    }

    /**
     * Sets the blue component of the given color.
     *
     * @param color the original color
     * @param blue  the new blue component
     * @return the new color with the updated blue component
     */
    public static Color setBlue(Color color, float blue) {
        return fixColorRange(color.getRed(), color.getGreen(), (int) (blue * 255.0f), color.getAlpha());
    }

    /**
     * Sets the alpha component of the given color.
     *
     * @param color the original color
     * @param alpha the new alpha component
     * @return the new color with the updated alpha component
     */
    public static Color setAlpha(Color color, float alpha) {
        return fixColorRange(color.getRed(), color.getGreen(), color.getBlue(), (int) (alpha * 255.0f));
    }

    /**
     * Sets the red component of the given color.
     *
     * @param color the original color
     * @param red   the new red component
     * @return the new color with the updated red component
     */
    public static Color setRed(Color color, int red) {
        return fixColorRange(red, color.getGreen(), color.getBlue(), color.getAlpha());
    }

    /**
     * Sets the green component of the given color.
     *
     * @param color the original color
     * @param green the new green component
     * @return the new color with the updated green component
     */
    public static Color setGreen(Color color, int green) {
        return fixColorRange(color.getRed(), green, color.getBlue(), color.getAlpha());
    }

    /**
     * Sets the blue component of the given color.
     *
     * @param color the original color
     * @param blue  the new blue component
     * @return the new color with the updated blue component
     */
    public static Color setBlue(Color color, int blue) {
        return fixColorRange(color.getRed(), color.getGreen(), blue, color.getAlpha());
    }

    /**
     * Sets the alpha component of the given color.
     *
     * @param color the original color
     * @param alpha the new alpha component
     * @return the new color with the updated alpha component
     */
    public static Color setAlpha(Color color, int alpha) {
        return fixColorRange(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    /**
     * Checks if the given color components are within the valid range (0-255).
     *
     * @param checkRed   the red component to check
     * @param checkGreen the green component to check
     * @param checkBlue  the blue component to check
     * @param checkAlpha the alpha component to check
     * @return true if all components are within the valid range, false otherwise
     */
    public static boolean checkColorRange(int checkRed, int checkGreen, int checkBlue, int checkAlpha) {
        int[] checkColors = new int[]{checkRed, checkGreen, checkBlue, checkAlpha};

        for (int check : checkColors) {
            return isWithinRange(check, 0, 255);
        }

        return true;
    }

    /**
     * Fixes the given color components to be within the valid range (0-255).
     *
     * @param fixRed   the red component to fix
     * @param fixGreen the green component to fix
     * @param fixBlue  the blue component to fix
     * @param fixAlpha the alpha component to fix
     * @return the new color with the fixed components
     */
    public static Color fixColorRange(int fixRed, int fixGreen, int fixBlue, int fixAlpha) {
        if (checkColorRange(fixRed, fixGreen, fixBlue, fixAlpha)) {
            return new Color(fixRed, fixGreen, fixBlue, fixAlpha);
        } else {
            return new Color(clamp(fixRed, 0, 255), clamp(fixGreen, 0, 255), clamp(fixBlue, 0, 255), clamp(fixAlpha, 0, 255));
        }
    }

    /**
     * Applies gamma correction to the given color.
     *
     * @param color the original color
     * @param gamma the gamma value
     * @return the new color with gamma correction applied
     */
    public static Color applyGammaCorrection(Color color, float gamma) {
        int r = clampColor((int) (pow(color.getRed() / 255.0, gamma) * 255));
        int g = clampColor((int) (pow(color.getGreen() / 255.0, gamma) * 255));
        int b = clampColor((int) (pow(color.getBlue() / 255.0, gamma) * 255));
        int a = color.getAlpha();
        return new Color(r, g, b, a);
    }

    /**
     * Applies brightness adjustment to the given color.
     *
     * @param color  the original color
     * @param deltaB the brightness adjustment value
     * @return the new color with brightness adjusted
     */
    public static Color applyBrightness(Color color, float deltaB) {
        int r = clampColor((int) (color.getRed() + deltaB * 255));
        int g = clampColor((int) (color.getGreen() + deltaB * 255));
        int b = clampColor((int) (color.getBlue() + deltaB * 255));
        int a = color.getAlpha();
        return new Color(r, g, b, a);
    }

    /**
     * Applies contrast adjustment to the given color.
     *
     * @param color the original color
     * @param alpha the contrast adjustment value
     * @return the new color with contrast adjusted
     */
    public static Color applyContrast(Color color, float alpha) {
        int r = clampColor((int) (((color.getRed() / 255.0 - 0.5) * alpha + 0.5) * 255));
        int g = clampColor((int) (((color.getGreen() / 255.0 - 0.5) * alpha + 0.5) * 255));
        int b = clampColor((int) (((color.getBlue() / 255.0 - 0.5) * alpha + 0.5) * 255));
        int a = color.getAlpha();
        return new Color(r, g, b, a);
    }

    /**
     * Applies saturation adjustment to the given color.
     *
     * @param color  the original color
     * @param deltaS the saturation adjustment value
     * @return the new color with saturation adjusted
     */
    public static Color applySaturation(Color color, float deltaS) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hsb[1] = clampSaturation(hsb[1] * deltaS);  // Adjust saturation
        int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
        return new Color(rgb, true);
    }

    /**
     * Applies hue adjustment to the given color.
     *
     * @param color  the original color
     * @param deltaH the hue adjustment value
     * @return the new color with hue adjusted
     */
    public static Color applyHue(Color color, float deltaH) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hsb[0] = (hsb[0] + deltaH) % 1.0f;
        int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
        return new Color(rgb, true);
    }

    /**
     * Clamps the saturation value to be within the valid range (0.0-1.0).
     *
     * @param value the saturation value to clamp
     * @return the clamped saturation value
     */
    private static float clampSaturation(float value) {
        return max(0.0f, min(1.0f, value));
    }

    /**
     * Clamps the given value to be within the valid range (0-255).
     *
     * @param value the value to clamp
     * @return the clamped value
     */
    private static int clampColor(int value) {
        return max(0, min(255, value));
    }
}