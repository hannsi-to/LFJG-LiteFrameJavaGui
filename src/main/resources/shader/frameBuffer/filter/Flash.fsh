#version 330 core

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float intensity;
uniform vec2 screenSize;
uniform vec2 screenPosition;
uniform int blendMode;
uniform vec3 lightColor;

float computeAttenuation(vec2 texCoords, vec2 center, float intensity) {
    float distance = length(texCoords - center);
    return intensity / (distance * distance + 1.0);
}

void main() {
    vec4 baseColor = texture(textureSampler, outTexture);
    vec2 lightUV = screenPosition / screenSize;
    float attenuation = computeAttenuation(outTexture, lightUV, intensity);
    vec3 flashColor = lightColor * attenuation;
    vec3 flashWithBaseColor = baseColor.rgb * attenuation;

    if (blendMode == 0) {
        fragColor = vec4(baseColor.rgb + flashColor, baseColor.a);
    } else if (blendMode == 1) {
        fragColor = vec4(baseColor.rgb * flashColor, baseColor.a);
    } else if (blendMode == 2) {
        fragColor = vec4(flashColor, 1.0);
    } else if (blendMode == 3) {
        fragColor = vec4(baseColor.rgb + flashWithBaseColor, baseColor.a);
    } else {
        fragColor = baseColor;
    }
}