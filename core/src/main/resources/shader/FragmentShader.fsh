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

layout(std430, binding = 2) readonly buffer SpriteDatum {
    UV datum[];
};

#define NO_ATTACH_TEXTURE 0xFFFFFFFF

void main() {
    vec4 baseColor;

    if (vSpriteIndex == NO_ATTACH_TEXTURE) {
        baseColor = vColor;
    } else {
        UV data = datum[vSpriteIndex];
        vec4 uvRect = data.rect;
        float layer = data.layer;
        vec2 uv = uvRect.xy + vUV * uvRect.zw;
        baseColor = texture(uTexArray, vec3(uv, layer));
    }

    fragColor = baseColor * vSpriteColor;
}