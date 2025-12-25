#version 460 core

layout(location=0) in vec3 position;
layout(location=1) in vec4 color;
layout(location=2) in vec2 uv;

flat out uint vSpriteIndex;
out vec4 vColor;
out vec2 vUV;

struct Transform {
    mat4 transform;
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

    gl_Position = t.transform * vec4(position, 1.0);
    vSpriteIndex = t.spriteIndex;
    vColor = color;
    vUV = uv;
}