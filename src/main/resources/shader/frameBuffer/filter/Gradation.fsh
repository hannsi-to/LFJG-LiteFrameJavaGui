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

#include "shader/frameBuffer/util/Blend.glsl"

void main() {
    vec2 uv = outTexture;
    vec4 gradientColor;
    float dist;

    if (gradientShape == 0) {
        float pos = dot(uv - center, vec2(cos(angle), sin(angle))) / width;
        float t = smoothstep(-0.5, 0.5, pos);
        gradientColor = mix(startColor, endColor, t);
    } else if (gradientShape == 1) {
        dist = distance(uv, center);
        float t = smoothstep(0.0, width, dist);
        gradientColor = mix(startColor, endColor, t);
    } else if (gradientShape == 2) {
        vec2 d = abs(uv - center) - vec2(width);
        dist = length(max(d, 0.0));
        float t = smoothstep(0.0, width, dist);
        gradientColor = mix(startColor, endColor, t);
    }

    gradientColor *= intensity;
    fragColor = blend(texture(textureSampler, uv), gradientColor, blendMode);
}