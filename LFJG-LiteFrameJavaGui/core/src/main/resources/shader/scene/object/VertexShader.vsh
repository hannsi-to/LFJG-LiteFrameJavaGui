#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec4 color;
layout (location=2) in vec2 texture;

out vec4 outPosition;
out vec4 outColor;
out vec2  outTexture;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

void main()
{
    gl_Position = outPosition = projectionMatrix * viewMatrix * modelMatrix * vec4(position, 1);
    outColor = color;
    outTexture = texture;
}