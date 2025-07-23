#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec3 chromaKeyColor;
uniform float hueRange;
uniform float saturationRange;
uniform float boundarySmoothness;
uniform vec3 colorAdjustment;

#include "shader/frameBuffer/util/MathUtil.glsl"

float smoothEdge(float value, float edge0, float edge1) {
    return smoothstep(edge0 - boundarySmoothness, edge1 + boundarySmoothness, value);
}

void main() {
    vec4 texColor = texture(textureSampler, outTexture);

    vec3 hsvColor = rgb2hsv(texColor.rgb);
    vec3 hsvChromaKey = rgb2hsv(chromaKeyColor);

    float hueDiff = abs(hsvColor.x - hsvChromaKey.x);
    if (hueDiff > 0.5) {
        hueDiff = 1.0 - hueDiff;
    }

    float saturationDiff = abs(hsvColor.y - hsvChromaKey.y);

    float alpha = 1.0;
    if (hueDiff < hueRange && saturationDiff < saturationRange) {
        float dist = max(hueDiff / hueRange, saturationDiff / saturationRange);
        alpha = 1.0 - smoothEdge(dist, 0.0, 1.0);
    }

    vec3 adjustedColor = texColor.rgb + colorAdjustment;

    fragColor = vec4(adjustedColor, texColor.a * alpha);
}