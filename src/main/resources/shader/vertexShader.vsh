#version 330 core

layout(location = 0) in vec2 position;
layout(location = 1) in vec4 color;
layout(location = 2) in vec2 texCoord;

out vec4 outColor;
out vec2 outTexCoord;

uniform mat4 uModelMatrix;
uniform int screenWidth;
uniform int screenHeight;

void main() {
    float aX = (2 * position.x) / screenWidth - 1;
    float aY = (2 * position.y) / screenHeight - 1;

    gl_Position = uModelMatrix * vec4(aX, aY, 0.0, 1.0);
    outColor = color;
    outTexCoord = texCoord;
}