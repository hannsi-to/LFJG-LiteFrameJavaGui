#version 460 core

layout(location=0) in vec3 inPosition;
layout(location=1) in vec4 inColor;
layout(location=2) in vec2 inUV;
layout(location=2) in vec3 inNormal;

flat out uint vSpriteIndex;
flat out vec4 vSpriteColor;
out vec4 vColor;
out vec2 vUV;

struct Transform {
    mat4 transform;
    vec4 spriteColor;
    uint spriteIndex;
    uint _pading1;
    uint _pading2;
    uint _pading3;
};

layout(std430, binding = 2) buffer Transforms {
    Transform transforms[];
};

void main() {
    uint index = gl_BaseInstance + gl_InstanceID;
    Transform t = transforms[index];

    gl_Position = t.transform * vec4(inPosition, 1.0);
    vSpriteIndex = t.spriteIndex;
    vColor = inColor;
    vSpriteColor = t.spriteColor;
    vUV = inUV;
}