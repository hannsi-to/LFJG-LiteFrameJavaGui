#version 330 core

in vec2 fTexCoords;
in vec4 fColor;

uniform sampler2D uFontTexture;

out vec4 color;

void main()
{
    float c = texture(uFontTexture, fTexCoords).r;
    color = vec4(1, 1, 1, c) * vec4(fColor);
}