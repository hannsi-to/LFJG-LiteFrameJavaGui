#version 330 core

layout(location = 0) in vec3 aPos;
layout(location = 1) in vec4 aColor;
layout(location = 2) in vec2 aTexture;

out vec4 vertexColor;
out vec2 TexCoords;

uniform int screenWidth;
uniform int screenHeight;

void main() {
    float aX = (2 * aPos.x) / screenWidth - 1;
    float aY = (2 * aPos.y) / screenHeight - 1;

    gl_Position = vec4(aX,aY, aPos.z, 1.0);
    vertexColor = aColor;
    TexCoords = aTexture;
}