#version 460 core

layout(location=0) in vec3 inPosition;
layout(location=1) in vec4 inColor;
layout(location=2) in vec2 inUV;
layout(location=3) in vec3 inNormal;

flat out uint vSpriteIndex;
flat out vec4 vSpriteColor;
out vec4 vColor;
out vec2 vUV;

struct ObjectData {
    uint _padding1;
    uint _padding2;
    uint _padding3;
    uint _padding4;
};

struct InstanceParameter {
    mat4 transform;
    vec4 spriteColor;
    uint spriteIndex;
    uint objectId;
    uint _pading1;
    uint _pading2;
};

layout(std430, binding = 1) buffer ObjectDatum {
    ObjectData objectDatum[];
};

layout(std430, binding = 2) buffer InstanceParameters {
    InstanceParameter instanceParameters[];
};

void main() {
    uint index = gl_BaseInstance + gl_InstanceID;
    InstanceParameter t = instanceParameters[index];
    ObjectData o = objectDatum[t.objectId];

    gl_Position = t.transform * vec4(inPosition, 1.0);
    vSpriteIndex = t.spriteIndex;
    vColor = inColor;
    vSpriteColor = t.spriteColor;
    vUV = inUV;
}