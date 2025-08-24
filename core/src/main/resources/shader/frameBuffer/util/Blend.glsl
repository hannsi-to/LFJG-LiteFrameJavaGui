vec4 blendNormal(vec4 base, vec4 blend) {
    if (blend.a == 0){
        return base;
    }

    return blend;
}

vec4 blendAdd(vec4 base, vec4 blend) {
    vec3 blendedColor = base.rgb + blend.rgb;
    blendedColor = clamp(blendedColor, 0.0, 1.0);

    float blendedAlpha = max(base.a, blend.a);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendAlphaAdd(vec4 base, vec4 blend) {
    vec3 blendedColor = base.rgb + blend.rgb;
    blendedColor = clamp(blendedColor, 0.0, 1.0);

    float blendedAlpha = base.a + blend.a;
    blendedAlpha = clamp(blendedAlpha, 0.0, 1.0);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendSubtract(vec4 base, vec4 blend) {
    return base - blend;
}

vec4 blendReverseSubtract(vec4 base, vec4 blend) {
    return base - blend;
}

vec4 blendMultiply(vec4 base, vec4 blend) {
    return base * blend;
}

vec4 blendScreen(vec4 base, vec4 blend) {
    return 1.0 - (1.0 - base) * (1.0 - blend);
}

vec4 blendLighten(vec4 base, vec4 blend) {
    vec3 blendedColor = max(base.rgb, blend.rgb);
    float blendedAlpha = max(base.a, blend.a);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendDarken(vec4 base, vec4 blend) {
    vec3 blendedColor = min(base.rgb, blend.rgb);
    float blendedAlpha = min(base.a, blend.a);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendShade(vec4 base, vec4 blend) {
    return base * (1.0 - blend);
}

vec4 blendDifference(vec4 base, vec4 blend) {
    return abs(base - blend);
}

vec4 blendExclusion(vec4 base, vec4 blend) {
    vec3 blendedColor = base.rgb + blend.rgb - 2.0 * base.rgb * blend.rgb;
    float blendedAlpha = base.a + blend.a - 2.0 * base.a * blend.a;

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendDodge(vec4 base, vec4 blend) {
    vec3 blendedColor = base.rgb / (1.0 - blend.rgb);
    blendedColor = clamp(blendedColor, 0.0, 1.0);

    float blendedAlpha = base.a / (1.0 - blend.a);
    blendedAlpha = clamp(blendedAlpha, 0.0, 1.0);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendBurn(vec4 base, vec4 blend) {
    vec3 blendedColor = 1.0 - (1.0 - base.rgb) / blend.rgb;
    blendedColor = clamp(blendedColor, 0.0, 1.0);

    float blendedAlpha = 1.0 - (1.0 - base.a) / blend.a;
    blendedAlpha = clamp(blendedAlpha, 0.0, 1.0);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendInvert(vec4 base, vec4 blend) {
    vec3 blendedColor = 1.0 - blend.rgb;
    float blendedAlpha = 1.0 - base.a;

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendPremultipliedAlpha(vec4 base, vec4 blend) {
    vec3 blendedColor = base.rgb + blend.rgb * (1.0 - base.a);
    float blendedAlpha = base.a + blend.a * (1.0 - base.a);

    blendedAlpha = clamp(blendedAlpha, 0.0, 1.0);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendHardMix(vec4 base, vec4 blend) {
    vec3 blendedColor = (step(1.0, base.rgb + blend.rgb)) * vec3(1.0);
    float blendedAlpha = base.a + blend.a * (1.0 - base.a);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendLinearLight(vec4 base, vec4 blend) {
    vec3 blendedColor;
    blendedColor.r = (blend.r > 0.5) ? base.r + 2.0 * (blend.r - 0.5) : base.r - 2.0 * (1.0 - blend.r);
    blendedColor.g = (blend.g > 0.5) ? base.g + 2.0 * (blend.g - 0.5) : base.g - 2.0 * (1.0 - blend.g);
    blendedColor.b = (blend.b > 0.5) ? base.b + 2.0 * (blend.b - 0.5) : base.b - 2.0 * (1.0 - blend.b);

    float blendedAlpha = base.a + blend.a * (1.0 - base.a);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendVividLight(vec4 base, vec4 blend) {
    vec3 blendedColor;
    blendedColor.r = (blend.r > 0.5) ? base.r + 2.0 * (blend.r - 0.5) : base.r - 2.0 * (1.0 - blend.r);
    blendedColor.g = (blend.g > 0.5) ? base.g + 2.0 * (blend.g - 0.5) : base.g - 2.0 * (1.0 - blend.g);
    blendedColor.b = (blend.b > 0.5) ? base.b + 2.0 * (blend.b - 0.5) : base.b - 2.0 * (1.0 - blend.b);

    float blendedAlpha = base.a + blend.a * (1.0 - base.a);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendPinLight(vec4 base, vec4 blend) {
    vec3 blendedColor;
    blendedColor.r = (blend.r > 0.5) ? min(base.r, 2.0 * blend.r) : max(base.r, 2.0 * (blend.r - 1.0));
    blendedColor.g = (blend.g > 0.5) ? min(base.g, 2.0 * blend.g) : max(base.g, 2.0 * (blend.g - 1.0));
    blendedColor.b = (blend.b > 0.5) ? min(base.b, 2.0 * blend.b) : max(base.b, 2.0 * (blend.b - 1.0));

    float blendedAlpha = base.a + blend.a * (1.0 - base.a);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendHardLight(vec4 base, vec4 blend) {
    vec3 blendedColor;
    blendedColor.r = (blend.r > 0.5) ? (2.0 * base.r * blend.r) : (1.0 - 2.0 * (1.0 - base.r) * (1.0 - blend.r));
    blendedColor.g = (blend.g > 0.5) ? (2.0 * base.g * blend.g) : (1.0 - 2.0 * (1.0 - base.g) * (1.0 - blend.g));
    blendedColor.b = (blend.b > 0.5) ? (2.0 * base.b * blend.b) : (1.0 - 2.0 * (1.0 - base.b) * (1.0 - blend.b));

    float blendedAlpha = base.a + blend.a * (1.0 - base.a);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendSoftLight(vec4 base, vec4 blend) {
    vec3 blendedColor;
    blendedColor.r = (blend.r > 0.5) ? base.r + (1.0 - (1.0 - base.r) * pow(1.0 - 2.0 * blend.r, 2.0)) : base.r - (1.0 - base.r) * pow(1.0 - 2.0 * blend.r, 2.0);
    blendedColor.g = (blend.g > 0.5) ? base.g + (1.0 - (1.0 - base.g) * pow(1.0 - 2.0 * blend.g, 2.0)) : base.g - (1.0 - base.g) * pow(1.0 - 2.0 * blend.g, 2.0);
    blendedColor.b = (blend.b > 0.5) ? base.b + (1.0 - (1.0 - base.b) * pow(1.0 - 2.0 * blend.b, 2.0)) : base.b - (1.0 - base.b) * pow(1.0 - 2.0 * blend.b, 2.0);

    float blendedAlpha = base.a + blend.a * (1.0 - base.a);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendOverlay(vec4 base, vec4 blend) {
    return mix(2.0 * base * blend, 1.0 - 2.0 * (1.0 - base) * (1.0 - blend), step(0.5, base));
}

vec4 blendLuminance(vec4 base, vec4 blend) {
    float lum = luminance(blend.rgb);
    return vec4(setLuminance(base.rgb, lum), base.a);
}

vec4 blendColorAdd(vec4 base, vec4 blend) {
    vec3 blendedColor = base.rgb + blend.rgb;
    blendedColor = clamp(blendedColor, 0.0, 1.0);

    float blendedAlpha = base.a + blend.a * (1.0 - base.a);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blendSaturation(vec4 base, vec4 blend) {
    float gray = 0.2126 * base.r + 0.7152 * base.g + 0.0722 * base.b;
    vec3 diff = base.rgb - vec3(gray);

    vec3 blendedColor = gray + diff * blend.rgb;
    blendedColor = clamp(blendedColor, 0.0, 1.0);

    float blendedAlpha = base.a + blend.a * (1.0 - base.a);

    return vec4(blendedColor, blendedAlpha);
}

vec4 blend(vec4 baseColor, vec4 blendColor, int mode){
    vec4 resultColor;

    if (mode == 0){
        resultColor = blendNormal(baseColor, blendColor);
    } else if (mode == 1){
        resultColor = blendAdd(baseColor, blendColor);
    } else if (mode == 2){
        resultColor = blendAlphaAdd(baseColor, blendColor);
    } else if (mode == 3){
        resultColor = blendSubtract(baseColor, blendColor);
    } else if (mode == 4){
        resultColor = blendReverseSubtract(baseColor, blendColor);
    } else if (mode == 5){
        resultColor = blendMultiply(baseColor, blendColor);
    } else if (mode == 6){
        resultColor = blendScreen(baseColor, blendColor);
    } else if (mode == 7){
        resultColor = blendLighten(baseColor, blendColor);
    } else if (mode == 8){
        resultColor = blendDarken(baseColor, blendColor);
    }
    else if (mode == 9){
        resultColor = blendShade(baseColor, blendColor);
    } else if (mode == 10){
        resultColor = blendDifference(baseColor, blendColor);
    } else if (mode == 11){
        resultColor = blendExclusion(baseColor, blendColor);
    } else if (mode == 12){
        resultColor = blendDodge(baseColor, blendColor);
    } else if (mode == 13){
        resultColor = blendBurn(baseColor, blendColor);
    } else if (mode == 14){
        resultColor = blendInvert(baseColor, blendColor);
    } else if (mode == 15){
        resultColor = blendPremultipliedAlpha(baseColor, blendColor);
    } else if (mode == 16){
        resultColor = blendHardMix(baseColor, blendColor);
    } else if (mode == 17){
        resultColor = blendLinearLight(baseColor, blendColor);
    } else if (mode == 18){
        resultColor = blendVividLight(baseColor, blendColor);
    }
    else if (mode == 19){
        resultColor = blendPinLight(baseColor, blendColor);
    } else if (mode == 20){
        resultColor = blendHardLight(baseColor, blendColor);
    } else if (mode == 21){
        resultColor = blendSoftLight(baseColor, blendColor);
    } else if (mode == 22){
        resultColor = blendOverlay(baseColor, blendColor);
    } else if (mode == 23){
        resultColor = blendLuminance(baseColor, blendColor);
    } else if (mode == 24){
        resultColor = blendColorAdd(baseColor, blendColor);
    } else if (mode == 25){
        resultColor = blendSaturation(baseColor, blendColor);
    }

    return resultColor;
}