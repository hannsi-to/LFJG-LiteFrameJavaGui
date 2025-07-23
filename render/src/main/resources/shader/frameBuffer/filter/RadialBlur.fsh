#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float range;
uniform float centerX;
uniform float centerY;

void main() {
    vec2 uv = outTexture;
    vec2 center = vec2(centerX, centerY);

    vec4 color = vec4(0.0);
    float total = 0.0;

    for (float t = 0.0; t < 1.0; t += 0.01) {
        vec2 offset = uv + (uv - center) * t * range;
        color += texture(textureSampler, offset);
        total += 1.0;
    }

    fragColor = color / total;
}