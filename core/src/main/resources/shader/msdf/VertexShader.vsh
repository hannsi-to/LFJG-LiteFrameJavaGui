#version 330

layout (location=0) in vec3 position;
layout (location=2) in vec2 texture;

out vec4 outPosition;
out vec2 outTexture;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

uniform float italicSkew;

void main(){
    vec4 pos = vec4(position, 1.0);
    pos.x += italicSkew * pos.y;

    gl_Position = outPosition = projectionMatrix * modelMatrix * viewMatrix * pos;
    outTexture = texture;
}