#version 330

in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float intensity;
uniform float threshold;
uniform vec3 glowColor;
uniform bool useOriginalColor;
uniform bool glowOnly;
uniform float spread;

#include "shader/frameBuffer/util/Luminance.glsl"

vec4 applyBlur(vec2 uv, float intensity, float spread) {
    vec4 result = vec4(0.0);
    float weight = 0.0;

    for (int x = -5; x <= 5; x++) {
        for (int y = -5; y <= 5; y++) {
            vec2 offset = vec2(x, y) * spread;
            result += texture(textureSampler, uv + offset);
            weight += 1.0;
        }
    }

    return weight > 0.0 ? result / weight : vec4(0.0);
}

void main() {
    vec4 texColor = texture(textureSampler, outTexture);

    float luminanceValue = luminance(texColor.rgb);

    vec4 glow = vec4(0.0);
    if (luminanceValue > threshold) {
        glow = applyBlur(outTexture, intensity, spread);

        if (!useOriginalColor) {
            glow.rgb = glowColor * glow.a;
        }
    }

    if (glowOnly) {
        fragColor = glow * intensity;
    } else {
        fragColor = texColor + glow * intensity;
    }
}