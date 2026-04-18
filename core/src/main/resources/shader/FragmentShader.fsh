#version 460 core
#extension GL_ARB_bindless_texture : require [[define(USE_BINDLESS)]]
#extension GL_NV_gpu_shader5 : enable [[define(USE_INT64)]]

#include "shader/frameBuffer/util/Blend.glsl"
#define NO_ATTACH_TEXTURE 0xFFFFFFFF
#define NO_ATTACH_VIDEO 0xFFFFFFFF

flat in uint vSpriteIndex;
flat in uint vVideoIndex;
flat in vec4 vSpriteColor;
in vec4 vColor;
in vec2 vUV;

out vec4 fragColor;

uniform sampler2DArray uTexArray;

struct UV{
    vec4 rect;
    float layer;
    float _pading1;
    float _pading2;
    float _pading3;
};

struct VideoData {
#if USE_INT64
    uint64_t handleY;
    uint64_t handleUV;
#else
    uvec2 handleY;
    uvec2 handleUV;
#endif
};

layout(std430, binding = 3) readonly buffer SpriteDatum {
    UV spriteDatum[];
};

layout(std430, binding = 4) readonly buffer VideoDatum {
    VideoData videoDatum[];
};

vec3 yuvToRgb(float y, vec2 uv){
    float u = uv.x - 0.5;
    float v = uv.y - 0.5;

    return vec3(y + 1.402 * v, y - 0.344136 * u - 0.714136 * v, y + 1.772 * u);
}

void main() {
    vec4 baseColor = vColor;
    //    vec4 baseColor = vColor * vSpriteColor;
    if (vVideoIndex != NO_ATTACH_VIDEO){
        VideoData vd = videoDatum[vVideoIndex];

        sampler2D texY  = sampler2D(vd.handleY);
        sampler2D texUV = sampler2D(vd.handleUV);

        float y  = texture(texY, vUV).r;
        vec2 uv  = texture(texUV, vUV).rg;

        vec3 rgb = yuvToRgb(y, uv);

        baseColor *= vec4(rgb, 1.0);
    } else if (vSpriteIndex != NO_ATTACH_TEXTURE) {
        UV data = spriteDatum[vSpriteIndex];
        vec4 uvRect = data.rect;
        float layer = data.layer;
        vec2 uv = uvRect.xy + vUV * uvRect.zw;
        baseColor *= texture(uTexArray, vec3(uv, layer));
    }

    fragColor = vec4(1, 0, 0, 1);
}