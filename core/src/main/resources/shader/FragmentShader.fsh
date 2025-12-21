#version 460 core

flat in uint vSpriteIndex;
in vec4 vColor;
in vec2 vUV;

out vec4 fragColor;

uniform sampler2DArray uTexArray;

layout(std430, binding = 1) buffer SpriteData {
    ivec4 spriteDataSize;
    vec4 spriteUVs[];
} spriteData;

#define NO_ATTACH_TEXTURE 0xFFFFFFFF

void main() {
    if (vSpriteIndex == NO_ATTACH_TEXTURE) {
        fragColor = vColor;
        return;
    }

    vec4 uvRect = spriteData.spriteUVs[vSpriteIndex];
    vec2 uv = uvRect.xy + vUV * uvRect.zw;
    fragColor = texture(uTexArray, vec3(uv, 0));
}