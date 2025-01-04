package me.hannsi.lfjg.utils.graphics.color;

import me.hannsi.lfjg.utils.math.MathUtil;
import org.joml.Math;

import java.util.ArrayList;
import java.util.List;

public class ColorUtil {
    public static Color getRainbow(int delay, float timing, float saturation, float brightness) {
        float hue = System.currentTimeMillis() % (int) (delay * 1000.0f) / (delay * 1000.0f) + timing;
        return Color.getHSBColor(hue, saturation / 255f, brightness / 255f);
    }

    public static Color getRainbow(int delay, float saturation, float brightness) {
        return getRainbow(delay, 0, saturation, brightness);
    }

    public static int toARGB(final int r, final int g, final int b, final int a) {
        return fixColorRange(r, g, b, a).getRGB();
    }

    public static int toRGBA(final int r, final int g, final int b) {
        return toRGBA(r, g, b, 255);
    }

    public static int toRGBA(final int r, final int g, final int b, final int a) {
        return (r << 16) + (g << 8) + b + (a << 24);
    }

    public static int toRGBA(final float r, final float g, final float b, final float a) {
        return toRGBA((int) (r * 255.0f), (int) (g * 255.0f), (int) (b * 255.0f), (int) (a * 255.0f));
    }

    public static Color setRed(Color color, float red) {
        return fixColorRange((int) (red * 255.0f), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Color setGreen(Color color, float green) {
        return fixColorRange(color.getRed(), (int) (green * 255.0f), color.getBlue(), color.getAlpha());
    }

    public static Color setBlue(Color color, float blue) {
        return fixColorRange(color.getRed(), color.getGreen(), (int) (blue * 255.0f), color.getAlpha());
    }

    public static Color setAlpha(Color color, float alpha) {
        return fixColorRange(color.getRed(), color.getGreen(), color.getBlue(), (int) (alpha * 255.0f));
    }

    public static Color setRed(Color color, int red) {
        return fixColorRange(red, color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Color setGreen(Color color, int green) {
        return fixColorRange(color.getRed(), green, color.getBlue(), color.getAlpha());
    }

    public static Color setBlue(Color color, int blue) {
        return fixColorRange(color.getRed(), color.getGreen(), blue, color.getAlpha());
    }

    public static Color setAlpha(Color color, int alpha) {
        return fixColorRange(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static boolean checkColorRange(int checkRed, int checkGreen, int checkBlue, int checkAlpha) {
        List<Integer> checkColors = new ArrayList<>();
        checkColors.add(checkRed);
        checkColors.add(checkGreen);
        checkColors.add(checkBlue);
        checkColors.add(checkAlpha);

        for (Integer integer : checkColors) {
            return MathUtil.isWithinRange(integer, 0, 255);
        }

        return true;
    }

    public static Color fixColorRange(int fixRed, int fixGreen, int fixBlue, int fixAlpha) {
        if (checkColorRange(fixRed, fixGreen, fixBlue, fixAlpha)) {
            return new Color(fixRed, fixGreen, fixBlue, fixAlpha);
        } else {
            return new Color(MathUtil.clampI(fixRed, 0, 255), MathUtil.clampI(fixGreen, 0, 255), MathUtil.clampI(fixBlue, 0, 255), MathUtil.clampI(fixAlpha, 0, 255));
        }
    }

    /***
     * @param color color
     * @param gamma 1.0
     * @return color
     */
    public static Color applyGammaCorrection(Color color, float gamma) {
        int r = clamp((int) (java.lang.Math.pow(color.getRed() / 255.0, gamma) * 255));
        int g = clamp((int) (java.lang.Math.pow(color.getGreen() / 255.0, gamma) * 255));
        int b = clamp((int) (java.lang.Math.pow(color.getBlue() / 255.0, gamma) * 255));
        int a = color.getAlpha();
        return new Color(r, g, b, a);
    }

    /***
     * @param color color
     * @param deltaB 0.0
     * @return color
     */
    public static Color applyBrightness(Color color, float deltaB) {
        int r = clamp((int) (color.getRed() + deltaB * 255));
        int g = clamp((int) (color.getGreen() + deltaB * 255));
        int b = clamp((int) (color.getBlue() + deltaB * 255));
        int a = color.getAlpha();
        return new Color(r, g, b, a);
    }

    /***
     * @param color color
     * @param alpha 1.0
     * @return color
     */
    public static Color applyContrast(Color color, float alpha) {
        int r = clamp((int) (((color.getRed() / 255.0 - 0.5) * alpha + 0.5) * 255));
        int g = clamp((int) (((color.getGreen() / 255.0 - 0.5) * alpha + 0.5) * 255));
        int b = clamp((int) (((color.getBlue() / 255.0 - 0.5) * alpha + 0.5) * 255));
        int a = color.getAlpha();
        return new Color(r, g, b, a);
    }

    /***
     * @param color color
     * @param deltaS 1.0
     * @return color
     */
    public static Color applySaturation(Color color, float deltaS) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hsb[1] = clampSaturation(hsb[1] * deltaS);  // 彩度の調整
        int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
        return new Color(rgb, true);
    }

    /***
     * @param color color
     * @param deltaH 0.0
     * @return color
     */
    public static Color applyHue(Color color, float deltaH) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hsb[0] = (hsb[0] + deltaH) % 1.0f;
        int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
        return new Color(rgb, true);
    }

    private static float clampSaturation(float value) {
        return Math.max(0.0f, Math.min(1.0f, value));
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
