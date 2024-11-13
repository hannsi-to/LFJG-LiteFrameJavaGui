#version 330

in vec4 outColor;
in  vec2 outTexCoord;

out vec4 fragColor;

uniform int target;
uniform int blendMode;

uniform sampler2D texture_sampler;

uniform float brightness = 50.0;
uniform float contrast = 50.0;
uniform float saturation = 50.0;
uniform float hue = 50.0;
uniform float gamma = 50.0;

float clampRange(float value, float minVal, float maxVal) { return clamp(value, minVal, maxVal); }

vec3 gammaCorrect(vec3 color, float gamma) { return pow(color, vec3(1.0 / gamma)); }

vec3 adjustSaturation(vec3 color, float saturation) {
    float grey = dot(color, vec3(0.3, 0.59, 0.11));
    return mix(vec3(grey), color, saturation);
}

vec3 adjustBrightness(vec3 color, float brightness) {
    return color + vec3(brightness);
}

vec3 adjustContrast(vec3 color, float contrast) {
    return (color - 0.5) * contrast + 0.5;
}

vec3 adjustHue(vec3 color, float hue) {
    float angle = hue * 3.14159265;
    mat3 hueRotation = mat3(
    cos(angle) + (1.0 - cos(angle)) / 3.0, (1.0 - cos(angle)) / 3.0 - sin(angle) / sqrt(3.0), (1.0 - cos(angle)) / 3.0 + sin(angle) / sqrt(3.0),
    (1.0 - cos(angle)) / 3.0 + sin(angle) / sqrt(3.0), cos(angle) + (1.0 - cos(angle)) / 3.0, (1.0 - cos(angle)) / 3.0 - sin(angle) / sqrt(3.0),
    (1.0 - cos(angle)) / 3.0 - sin(angle) / sqrt(3.0), (1.0 - cos(angle)) / 3.0 + sin(angle) / sqrt(3.0), cos(angle) + (1.0 - cos(angle)) / 3.0
    );
    return hueRotation * color;
}

vec3 applyColorCorrection(vec3 color){
    float aBrightness = clampRange((brightness - 50.0) / 50.0, -1.0, 1.0);
    float aContrast = clampRange(contrast / 50.0, 0.0, 2.0);
    float aSaturation = clampRange(saturation / 50.0, 0.0, 2.0);
    float aHue = clampRange(hue / 100.0, 0.0, 1.0);
    float aGamma = clampRange((gamma / 50.0) * 2.0, 1.0, 3.0);

    if (aBrightness != 0.0) {
        color = adjustBrightness(color, aBrightness);
    }
    if (aContrast != 1.0){
        color = adjustContrast(color, aContrast);
    }
    if (aSaturation != 1.0) {
        color = adjustSaturation(color, aSaturation);
    }
    if (aHue != 0.0) {
        color = adjustHue(color, aHue);
    }
    if (aGamma != 1.0) {
        color = gammaCorrect(color, aGamma);
    }

    return color;
}

vec4 applyBlendMode(vec4 textureColor, vec4 tintColor, int mode) {
    if (mode == 0) {
        return textureColor * tintColor;
    } else if (mode == 1) {
        return textureColor + tintColor;
    } else if (mode == 2) {
        return textureColor - tintColor;
    } else if (mode == 3) {
        return (textureColor + tintColor) * 0.5;
    } else if (mode == 4) {
        return 1.0 - ((1.0 - textureColor) * (1.0 - tintColor));
    } else if (mode == 5) {
        return mix(2.0 * textureColor * tintColor,
        1.0 - 2.0 * (1.0 - textureColor) * (1.0 - tintColor),
        step(0.5, textureColor));
    } else if (mode == 6) {
        return textureColor / (1.0 - tintColor);
    } else {
        return textureColor * tintColor;
    }
}

void main() {
    vec4 color = vec4(1f);

    if (target == 1){
        color = outColor;
    }
    if (target == 2){
        color = texture(texture_sampler, outTexCoord);
    }
    if (target == (1 | 2)){
        vec4 temp1 = outColor;
        vec4 temp2 = texture(texture_sampler, outTexCoord);
        color = applyBlendMode(temp1,temp2,blendMode);
    }

    if (target == 1 || target == (1 | 2)){
        color = vec4(applyColorCorrection(color.rgb), color.a);
    }

    fragColor = color;
}