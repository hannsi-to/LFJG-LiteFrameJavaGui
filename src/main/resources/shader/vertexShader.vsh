#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec4 color;

out vec4 fragPosition;
out vec4 outColor;

uniform vec2 resolution;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

void main()
{
    gl_Position = fragPosition = projectionMatrix * modelMatrix * viewMatrix * vec4(position, 1.0);
    outColor = color;
}