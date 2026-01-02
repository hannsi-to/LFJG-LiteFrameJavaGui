#version 460 core

flat in uint vSpriteIndex;
flat in vec4 vSpriteColor;
in vec4 vColor;
in vec2 vUV;

out vec4 fragColor;

uniform sampler2DArray uTexArray;

struct UV{
    vec4 rect;
    float layer;
    float _pad[3];
};

layout(std430, binding = 1) readonly buffer SpriteData {
    UV data[];
} spriteData;

#define NO_ATTACH_TEXTURE 0xFFFFFFFF

void main() {
    vec4 baseColor;

    if (vSpriteIndex == NO_ATTACH_TEXTURE) {
        baseColor = vColor;
    } else {
        vec4 uvRect = spriteData.data[vSpriteIndex].rect;
        float layer = spriteData.data[vSpriteIndex].layer;
        vec2 uv = uvRect.xy + vUV * uvRect.zw;
        baseColor = texture(uTexArray, vec3(uv, layer));
    }

    fragColor = baseColor * vSpriteColor;
}