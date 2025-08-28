uniform float bloomIntensity;
uniform float bloomSpread;
uniform float bloomThreshold;

vec3 bloomExtractBrightness(vec3 color, float threshold) {
    float brightness = dot(color, vec3(0.2126, 0.7152, 0.0722));
    return brightness < threshold ? color : vec3(0.0, 0.0, 0.0);
}

vec3 bloomApplyBlur(vec2 texCoord, float spread, float threshold) {
    vec2 texSize = vec2(textureSize(frameBufferSampler, 0));
    vec3 result = vec3(0.0);

    int blurRadius = int(spread * 10.0);
    float weight = 1.0 / float((blurRadius * 2 + 1) * (blurRadius * 2 + 1));

    for (int x = -blurRadius; x <= blurRadius; ++x) {
        for (int y = -blurRadius; y <= blurRadius; ++y) {
            vec2 offset = vec2(x, y) / texSize;
            vec3 sampleColor = texture(frameBufferSampler, texCoord + offset).rgb;
            vec3 bright = bloomExtractBrightness(sampleColor, threshold);
            result += bright * weight;
        }
    }
    return result;
}

void bloomMain() {
    vec4 texColor = texture(frameBufferSampler, outTexture);
    vec3 color = texColor.rgb;
    float alpha = texColor.a;

    vec3 blurredBrightColor = bloomApplyBlur(outTexture, bloomSpread, bloomThreshold);

    vec3 finalColor = color + blurredBrightColor * bloomIntensity;

    fragColor = vec4(finalColor, alpha);
}