#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float radius;
uniform float angle;

void main() {
    vec2 direction = vec2(cos(angle), sin(angle)) / textureSize(textureSampler, 0);// Adjust direction for texture size
    vec4 color = vec4(0.0);
    float totalWeight = 0.0;

    for (float i = -radius; i <= radius; i++) {
        vec2 offset = direction * i;
        float weight = 1.0 - abs(i / radius);
        color += texture(textureSampler, outTexture + offset) * weight;
        totalWeight += weight;
    }

    fragColor = color / totalWeight;
}