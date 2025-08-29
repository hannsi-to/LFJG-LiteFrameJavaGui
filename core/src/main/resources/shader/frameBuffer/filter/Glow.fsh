uniform float glowIntensity;
uniform float glowThreshold;
uniform vec3 glowColor;
uniform bool glowUseOriginalColor;
uniform bool glowOnly;
uniform float glowSpread;

#define texelSize (1.0 / resolution.xy)

vec4 applyGlowBlur(vec2 uv, float spread, float threshold){
    vec4 sum = vec4(0.0);
    float count = 0.0;

    for (int x = -5; x <= 5; x++) {
        for (int y = -5; y <= 5; y++) {
            vec2 offset = vec2(x, y) * texelSize * spread;
            vec4 sample1 = texture(frameBufferSampler, uv + offset);
            float lum = luminance(sample1.rgb);

            if (lum > threshold) {
                sum += sample1;
                count += 1.0;
            }
        }
    }

    return count > 0.0 ? sum / count : vec4(0.0);
}

void glowMain() {
    vec4 baseColor = texture(frameBufferSampler, outTexture);
    float baseLuminance = luminance(baseColor.rgb);

    vec4 glow = vec4(0.0);
    if (baseLuminance > glowThreshold) {
        glow = applyGlowBlur(outTexture, glowSpread, glowThreshold);

        if (!glowUseOriginalColor) {
            glow.rgb = glowColor * baseLuminance;
            glow.a = 1.0;
        }
    }

    if (glowOnly) {
        fragColor = glow * glowIntensity;
    } else {
        fragColor = baseColor + glow * glowIntensity;
    }

    fragColor.rgb = clamp(fragColor.rgb, 0.0, 1.0);
}