#version 330 core

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float intensity;
uniform ivec2 screenSize;
uniform vec2 screenPosition;
uniform int blendMode;
uniform vec3 lightColor;

float computeAttenuation(vec2 texCoords, vec2 center, float intensity) {
    float distance = length(texCoords - center);
    float constant = 0.1;
    float linear = 0.2;
    float quadratic = 0.8;
    float attenuation = intensity / (constant + linear * distance + quadratic * (distance * distance));
    return clamp(attenuation, 0.0, 1.0);
}

void main() {
    vec4 baseColor = texture(textureSampler, outTexture);
    vec2 lightUV = screenPosition / screenSize;
    float attenuation = computeAttenuation(outTexture, lightUV, intensity);
    vec3 flashColor = lightColor * attenuation;
    vec3 flashWithBaseColor = baseColor.rgb + baseColor.rgb * flashColor;

    if (blendMode == 0) {
        fragColor = vec4(baseColor.rgb + flashColor, baseColor.a);
    } else if (blendMode == 1) {
        fragColor = vec4(baseColor.rgb * flashColor, baseColor.a);
    } else if (blendMode == 2) {
        fragColor = vec4(flashColor, 1);
    } else if (blendMode == 3) {
        fragColor = vec4(baseColor.rgb + flashWithBaseColor, baseColor.a);
    } else {
        fragColor = baseColor;
    }
}