#version 330 core

in vec3 vertexColor;
out vec4 FragColor;

uniform float brightness;
uniform float contrast;
uniform float saturation;
uniform float hue;
uniform float gamma;

vec3 applyBrightness(vec3 color, float brightness) {
    return color * brightness;
}

vec3 applyContrast(vec3 color, float contrast) {
    return color * contrast;
}

vec3 applySaturation(vec3 color, float saturation) {
    float gray = dot(color, vec3(0.299, 0.587, 0.114));
    return mix(vec3(gray), color, saturation);
}

vec3 applyHue(vec3 color, float hue) {
    float angle = radians(hue);
    float s = sin(angle), c = cos(angle);
    mat3 hueRotation = mat3(
    vec3(0.299 + 0.701 * c + 0.168 * s, 0.587 - 0.587 * c + 0.330 * s, 0.114 - 0.114 * c - 0.497 * s),
    vec3(0.299 - 0.299 * c - 0.328 * s, 0.587 + 0.413 * c + 0.035 * s, 0.114 - 0.114 * c + 0.292 * s),
    vec3(0.299 - 0.300 * c + 1.250 * s, 0.587 - 0.588 * c - 1.050 * s, 0.114 + 0.886 * c - 0.203 * s)
    );
    return color * hueRotation;
}

vec3 applyGammaCorrection(vec3 color, float gamma) {
    return pow(color, vec3(1.0 / gamma));
}

void main() {
    vec3 color = vertexColor;

    color = applyBrightness(color, brightness);
    color = applyContrast(color, contrast);
    color = applySaturation(color, saturation);
    color = applyHue(color, hue);
    color = applyGammaCorrection(color, gamma);

    FragColor = vec4(color, 1.0);
}