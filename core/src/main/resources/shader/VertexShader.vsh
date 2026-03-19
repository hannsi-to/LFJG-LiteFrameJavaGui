#version 460 core

layout(location=0) in vec3 inPosition;
layout(location=1) in vec4 inColor;
layout(location=2) in vec2 inUV;
layout(location=3) in vec3 inNormal;

flat out uint vSpriteIndex;
flat out vec4 vSpriteColor;
out vec4 vColor;
out vec2 vUV;

uniform int baseDrawId;

struct ObjectData {
    uint instanceOffset;
    uint instanceCount;
    uint _padding3;
    uint _padding4;
};

struct InstanceParameter {
    mat4 transform;
    vec4 spriteColor;
    uvec4 sprite;
};

layout(std430, binding = 1) buffer ObjectDatum {
    ObjectData objectDatum[];
};

layout(std430, binding = 2) buffer InstanceParameters {
    InstanceParameter instanceParameters[];
};

void main() {
    uint index = baseDrawId + gl_DrawID;
    ObjectData o = objectDatum[index];
    InstanceParameter t = instanceParameters[gl_BaseInstance + gl_InstanceID];

    gl_Position = t.transform * vec4(inPosition, 1.0);
    vSpriteIndex = t.sprite[0];
    vColor = inColor;
    vSpriteColor = t.spriteColor;
    vUV = inUV;
}