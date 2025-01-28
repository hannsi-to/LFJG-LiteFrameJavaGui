#version 330 core

layout(location = 0) in vec2 aPos;
layout(location = 1) in vec4 aColor;
layout(location = 2) in vec2 aTexCoords;

out vec2 fTexCoords;
out vec4 fColor;

uniform mat4 uProjection;
uniform float uItalicAmount;

void main()
{
    vec2 italicPos = aPos;
    italicPos.x += uItalicAmount * aPos.y;

    fTexCoords = aTexCoords;
    fColor = aColor;
    gl_Position = uProjection * vec4(italicPos, 0, 1);
}