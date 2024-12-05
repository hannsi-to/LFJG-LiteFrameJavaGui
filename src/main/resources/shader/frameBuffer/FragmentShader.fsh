#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform vec2 resolution;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

uniform sampler2D textureSampler;

void main() {
    fragColor = texture(textureSampler, outTexture);
}