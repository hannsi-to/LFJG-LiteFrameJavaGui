#version 330 core

in vec3 ourColor;
in vec2 TexCoords;

out vec4 FragColor;

uniform sampler2D uTexture;

void main() {
    FragColor = texture(uTexture, TexCoords) * vec4(ourColor, 1.0);
}