#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

vec4 colorFilter;

uniform sampler2D textureSampler;
uniform vec2 resolution;

void main() {
    colorFilter = texture(textureSampler, outTexture);

    fragColor = colorFilter;
}