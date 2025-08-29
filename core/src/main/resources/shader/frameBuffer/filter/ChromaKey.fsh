uniform vec3 chromaKeyColor;
uniform float chromaKeyHueRange;
uniform float chromaKeySaturationRange;
uniform float chromaKeyBoundarySmoothness;
uniform vec3 chromaKeyColorAdjustment;

float chromaKeySmoothEdge(float value, float edge0, float edge1) {
    return smoothstep(edge0 - chromaKeyBoundarySmoothness, edge1 + chromaKeyBoundarySmoothness, value);
}

void chromaKeyMain() {
    vec4 texColor = texture(frameBufferSampler, outTexture);

    vec3 hsvColor = rgb2hsv(texColor.rgb);
    vec3 hsvChromaKey = rgb2hsv(chromaKeyColor);

    float hueDiff = abs(hsvColor.x - hsvChromaKey.x);
    if (hueDiff > 0.5) {
        hueDiff = 1.0 - hueDiff;
    }

    float saturationDiff = abs(hsvColor.y - hsvChromaKey.y);

    float alpha = 1.0;
    if (hueDiff < chromaKeyHueRange && saturationDiff < chromaKeySaturationRange) {
        float dist = max(hueDiff / chromaKeyHueRange, saturationDiff / chromaKeySaturationRange);
        alpha = 1.0 - chromaKeySmoothEdge(dist, 0.0, 1.0);
    }

    vec3 adjustedColor = texColor.rgb + chromaKeyColorAdjustment;

    fragColor = vec4(adjustedColor, texColor.a * alpha);
}