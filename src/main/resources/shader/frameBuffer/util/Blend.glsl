#include "shader/frameBuffer/util/Luminance.glsl"

vec4 blendNormal(vec4 base, vec4 blend) {
    return blend;
}

vec4 blendAdd(vec4 base, vec4 blend) {
    return base + blend;
}

vec4 blendSubtract(vec4 base, vec4 blend) {
    return base - blend;
}

vec4 blendMultiply(vec4 base, vec4 blend) {
    return base * blend;
}

vec4 blendScreen(vec4 base, vec4 blend) {
    return 1.0 - (1.0 - base) * (1.0 - blend);
}

vec4 blendOverlay(vec4 base, vec4 blend) {
    return mix(2.0 * base * blend, 1.0 - 2.0 * (1.0 - base) * (1.0 - blend), step(0.5, base));
}

vec4 blendLighten(vec4 base, vec4 blend) {
    return max(base, blend);
}

vec4 blendDarken(vec4 base, vec4 blend) {
    return min(base, blend);
}

vec4 blendLuminosity(vec4 base, vec4 blend) {
    float lum = luminance(blend.rgb);
    return vec4(setLuminance(base.rgb, lum), base.a);
}

vec4 blendColorDifference(vec4 base, vec4 blend) {
    return abs(base - blend);
}

vec4 blendShade(vec4 base, vec4 blend) {
    return base * (1.0 - blend);
}

vec4 blendLightAndShadow(vec4 base, vec4 blend) {
    return mix(base * 2.0 * blend, 1.0 - 2.0 * (1.0 - base) * (1.0 - blend), step(0.5, blend));
}

vec4 blendDifference(vec4 base, vec4 blend) {
    return abs(base - blend);
}

vec4 blend(vec4 base, vec4 blend, int mode){
    vec4 resultColor;

    if (blendMode == 0) {
        resultColor = blendNormal(baseColor, blendColor);
    } else if (blendMode == 1) {
        resultColor = blendAdd(baseColor, blendColor);
    } else if (blendMode == 2) {
        resultColor = blendSubtract(baseColor, blendColor);
    } else if (blendMode == 3) {
        resultColor = blendMultiply(baseColor, blendColor);
    } else if (blendMode == 4) {
        resultColor = blendScreen(baseColor, blendColor);
    } else if (blendMode == 5) {
        resultColor = blendOverlay(baseColor, blendColor);
    } else if (blendMode == 6){
        resultColor = blendLighten(baseColor, blendColor);
    } else if (blendMode == 7){
        resultColor = blendDarken(baseColor, blendColor);
    } else if (blendMode == 8){
        resultColor = blendLuminosity(baseColor, blendColor);
    } else if (blendMode == 9){
        resultColor = blendColorDifference(baseColor, blendColor);
    } else if (blendMode == 10){
        resultColor = blendShade(baseColor, blendColor);
    } else if (blendMode == 11){
        resultColor = blendLightAndShadow(baseColor, blendColor);
    } else if (blendMode == 12){
        resultColor = blendDifference(baseColor, blendColor);
    }

    return resultColor;
}