#version 330

layout (location=0) in vec3 position;
layout (location=2) in vec2 texture;

out vec4 outPosition;
out vec2 outTexture;

void main()
{
    gl_Position = vec4(position, 1.0);
    outTexture = texture;
}