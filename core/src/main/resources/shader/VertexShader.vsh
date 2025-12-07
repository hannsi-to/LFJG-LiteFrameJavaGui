#version 460 core

layout (location=0) in vec3 position;
layout (location=1) in vec4 color;
layout (location=2) in vec2 texture;

out vec4 outPosition;
out vec4 outColor;
out vec2  outTexture;

flat out int instanceLayer;

layout(std140, binding = 0) uniform Matrices{
    mat4 projectionMatrix;
    mat4 viewMatrix;
    mat4 modelMatrix;
} matrices;

uniform float italicSkew = 0;

void main(){
    vec4 pos = vec4(position, 1.0);
    pos.x += italicSkew * pos.y;

    gl_Position = matrices.projectionMatrix * matrices.viewMatrix * matrices.modelMatrix * pos;

    outPosition = gl_Position;
    outColor = color;
    outTexture = texture;

    instanceLayer = gl_InstanceID + gl_BaseInstance;
}