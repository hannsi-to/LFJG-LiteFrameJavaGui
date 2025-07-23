#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;

uniform float intensity;
uniform float spread;
uniform float threshold;

vec3 extractBrightness(vec3 color, float threshold) {
    float brightness = dot(color, vec3(0.2126, 0.7152, 0.0722));
    return brightness < threshold ? color : vec3(0.0, 0.0, 0.0);
}

vec3 applyBlur(vec2 texCoord, float spread, float threshold) {
    vec2 texSize = vec2(textureSize(textureSampler, 0));
    vec3 result = vec3(0.0);

    int blurRadius = int(spread * 10.0);
    float weight = 1.0 / float((blurRadius * 2 + 1) * (blurRadius * 2 + 1));

    for (int x = -blurRadius; x <= blurRadius; ++x) {
        for (int y = -blurRadius; y <= blurRadius; ++y) {
            vec2 offset = vec2(x, y) / texSize;
            vec3 sampleColor = texture(textureSampler, texCoord + offset).rgb;
            vec3 bright = extractBrightness(sampleColor, threshold);
            result += bright * weight;
        }
    }
    return result;
}

void main() {
    vec4 texColor = texture(textureSampler, outTexture);
    vec3 color = texColor.rgb;
    float alpha = texColor.a;

    vec3 blurredBrightColor = applyBlur(outTexture, spread, threshold);

    vec3 finalColor = color + blurredBrightColor * intensity;

    fragColor = vec4(finalColor, alpha);
}