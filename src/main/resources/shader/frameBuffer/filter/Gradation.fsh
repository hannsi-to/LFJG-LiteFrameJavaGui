#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;

uniform vec2 center;
uniform float angle;
uniform float width;
uniform int gradientShape;
uniform int blendMode;
uniform vec4 startColor;
uniform vec4 endColor;
uniform float intensity;

uniform float aspectRatio;

#include "shader/frameBuffer/util/Blend.glsl"

vec2 rotate(vec2 pos, float angle) {
    float s = sin(angle);
    float c = cos(angle);
    return vec2(
    pos.x * c - pos.y * s,
    pos.x * s + pos.y * c
    );
}

void main() {
    vec2 uv = outTexture;
    vec2 rel = uv - center;
    rel.x *= aspectRatio;

    vec2 rotated = rotate(rel, angle);

    vec4 gradientColor = vec4(0.0);
    float dist = 0.0;

    if (gradientShape == 0) {
        float pos = dot(rel, vec2(cos(angle), sin(angle))) / width;
        float t = smoothstep(-0.5, 0.5, pos);
        gradientColor = mix(startColor, endColor, t);
    } else if (gradientShape == 1) {
        dist = length(rotated);
        float t = smoothstep(0.0, width, dist);
        gradientColor = mix(startColor, endColor, t);
    } else if (gradientShape == 2) {
        vec2 d = abs(rotated) - vec2(width);
        dist = length(max(d, 0.0));
        float t = smoothstep(0.0, width, dist);
        gradientColor = mix(startColor, endColor, t);
    }

    gradientColor *= intensity;
    fragColor = blend(texture(textureSampler, uv), gradientColor, blendMode);
}