#version 330 core

layout(location = 0) in vec2 position;
layout (location =1) in vec4 inColour;

out vec4 exColour;

uniform int screenWidth;
uniform int screenHeight;

void main() {
    float aX = (2 * position.x) / screenWidth - 1;
    float aY = (2 * position.y) / screenHeight - 1;

    gl_Position = vec4(aX, aY, 0.0, 1.0);
    exColour = inColour;
}