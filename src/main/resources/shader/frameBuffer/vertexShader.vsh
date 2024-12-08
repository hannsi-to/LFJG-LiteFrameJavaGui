#version 330

layout (location=0) in vec3 position;
layout (location=2) in vec2 texture;

out vec4 outPosition;
out vec2 outTexture;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

void main()
{
    gl_Position = projectionMatrix * modelMatrix * viewMatrix * vec4(position, 1.0);
    outTexture = texture;
}