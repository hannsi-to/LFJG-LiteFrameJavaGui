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

uniform ivec2 texelSize;

#include "shader/frameBuffer/util/Luminance.glsl"

vec4 applyGlowBlur(vec2 uv, float spread, float threshold){
    vec4 sum = vec4(0.0);
    float count = 0.0;

    for (int x = -5; x <= 5; x++) {
        for (int y = -5; y <= 5; y++) {
            vec2 offset = vec2(x, y) * texelSize * spread;
            vec4 sample1 = texture(textureSampler, uv + offset);
            float lum = luminance(sample1.rgb);

            if (lum > threshold) {
                sum += sample1;
                count += 1.0;
            }
        }
    }

    return count > 0.0 ? sum / count : vec4(0.0);
}

void main() {
    vec4 baseColor = texture(textureSampler, outTexture);
    float baseLuminance = luminance(baseColor.rgb);

    vec4 glow = vec4(0.0);
    if (baseLuminance > threshold) {
        glow = applyGlowBlur(outTexture, spread, threshold);

        if (!useOriginalColor) {
            glow.rgb = glowColor * baseLuminance;
            glow.a = 1.0;
        }
    }

    if (glowOnly) {
        fragColor = glow * intensity;
    } else {
        fragColor = baseColor + glow * intensity;
    }

    fragColor.rgb = clamp(fragColor.rgb, 0.0, 1.0);
}