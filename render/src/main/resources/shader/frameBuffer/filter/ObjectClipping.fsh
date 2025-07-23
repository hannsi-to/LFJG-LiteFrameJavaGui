#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform sampler2D clippingTextureSampler;
uniform bool invert;

void main() {
    vec4 color = texture(textureSampler, outTexture);
    vec4 mask = texture(clippingTextureSampler, outTexture);

    if (invert ? mask.a != 0.0 : mask.a == 0.0) {
        discard;
    }

    fragColor = color;
}