#version 330

in vec4 exColour;

out vec4 fragColor;

uniform float brightness = 0.0;
uniform float contrast = 1.0;
uniform float saturation = 1.0;
uniform float hue = 0.0;
uniform float gamma = 1.0;

float clampRange(float value, float minVal, float maxVal) { return clamp(value, minVal, maxVal); }

vec3 gammaCorrect(vec3 color, float gamma) { return pow(color, vec3(1.0 / gamma)); }

vec3 adjustSaturation(vec3 color, float saturation) { float grey = dot(color, vec3(0.3, 0.59, 0.11));
    return mix(vec3(grey), color, saturation); }

vec3 adjustBrightness(vec3 color, float brightness) { return color + vec3(brightness); }

vec3 adjustContrast(vec3 color, float contrast) { return (color - 0.5) * contrast + 0.5; }

vec3 adjustHue(vec3 color, float hue) { float angle = hue * 3.14159265;// hueは0.0~1.0の範囲と仮定
    mat3 hueRotation = mat3(cos(angle) + (1.0 - cos(angle)) / 3.0, (1.0 - cos(angle)) / 3.0 - sin(angle) / sqrt(3.0), (1.0 - cos(angle)) / 3.0 + sin(angle) / sqrt(3.0), (1.0 - cos(angle)) / 3.0 + sin(angle) / sqrt(3.0), cos(angle) + (1.0 - cos(angle)) / 3.0, (1.0 - cos(angle)) / 3.0 - sin(angle) / sqrt(3.0), (1.0 - cos(angle)) / 3.0 - sin(angle) / sqrt(3.0), (1.0 - cos(angle)) / 3.0 + sin(angle) / sqrt(3.0), cos(angle) + (1.0 - cos(angle)) / 3.0);
    return hueRotation * color; }

void main() { vec3 color = exColour.rgb;

    float aBrightness = clampRange(brightness, -1.0, 1.0);
    float aContrast = clampRange(contrast, 0.0, 2.0);
    float aSaturation = clampRange(saturation, 0.0, 2.0);
    float aHue = clampRange(hue, 0.0, 1.0);
    float aGamma = clampRange(gamma, 1.0, 3.0);

    if (aBrightness != 0.0) { color = adjustBrightness(color, aBrightness); }

    if (aContrast != 1.0) { color = adjustContrast(color, aContrast); }

    if (aSaturation != 1.0) { color = adjustSaturation(color, aSaturation); }

    if (aHue != 0.0) { color = adjustHue(color, aHue); }

    if (aGamma != 1.0) { color = gammaCorrect(color, aGamma); }

    fragColor = vec4(color, exColour.a); }