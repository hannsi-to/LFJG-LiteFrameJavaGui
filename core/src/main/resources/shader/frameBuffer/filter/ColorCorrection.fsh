uniform float colorCorrectionBrightness = 0.0;
uniform float colorCorrectionContrast = 0.0;
uniform float colorCorrectionSaturation = 0.0;
uniform float colorCorrectionHue = 0.0;

vec3 colorCorrectionApplyBrightness(vec3 color, float value) {
    return color + value;
}

vec3 colorCorrectionApplyContrast(vec3 color, float value) {
    vec3 rgb = 0.5 + (1.0 + value) * (color.rgb - 0.5);
    return rgb;
}

vec3 colorCorrectionDesaturated(vec3 color) {
    vec3 luma = vec3(0.2126, 0.7152, 0.0722);
    vec3 rgb = vec3(dot(color, luma));
    return rgb;
}

vec3 colorCorrectionApplySaturation(vec3 color, float value) {
    vec3 desaturated = colorCorrectionDesaturated(color);
    return mix(desaturated, color, 1.0 + value);
}

vec3 colorCorrectionApplyHue(vec3 color, float value){
    vec3 hsb = rgb2hsb(color);
    hsb[0] = mod(hsb[0] + value, 1.0);
    if (hsb[0] < 0.0) {
        hsb[0] += 1.0;
    }

    return hsb2rgb(hsb);
}

vec3 colorCorrectionApplyColorCorrection(vec3 color) {
    color = colorCorrectionApplyHue(color, colorCorrectionHue);
    color = colorCorrectionApplySaturation(color, colorCorrectionSaturation);
    color = colorCorrectionApplyBrightness(color, colorCorrectionBrightness);
    color = colorCorrectionApplyContrast(color, colorCorrectionContrast);

    return color;
}

void colorCorrectionMain(){
    vec4 textureColor = texture(frameBufferSampler, outTexture);
    fragColor = vec4(colorCorrectionApplyColorCorrection(textureColor.rgb), textureColor.a);
}