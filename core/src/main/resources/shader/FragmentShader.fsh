#version 460 core

#include "shader/frameBuffer/util/Blend.glsl"
#define NO_ATTACH_TEXTURE 0xFFFFFFFF

flat in uint vSpriteIndex;
flat in vec4 vSpriteColor;
in vec4 vColor;
in vec2 vUV;

out vec4 fragColor;

uniform int uTextureBlendMode;
uniform int uSpriteBlendMode;
uniform sampler2DArray uTexArray;

struct UV{
    vec4 rect;
    float layer;
    float _pading1;
    float _pading2;
    float _pading3;
};

layout(std430, binding = 3) readonly buffer SpriteDatum {
    UV datum[];
};

void main() {
    vec4 baseColor = applyBlend(vSpriteColor, vColor, uSpriteBlendMode);
    if (vSpriteIndex != NO_ATTACH_TEXTURE) {
        UV data = datum[vSpriteIndex];
        vec4 uvRect = data.rect;
        float layer = data.layer;
        vec2 uv = uvRect.xy + vUV * uvRect.zw;
        baseColor = applyBlend(texture(uTexArray, vec3(uv, layer)), baseColor, uTextureBlendMode);
    }

    fragColor = baseColor;
}