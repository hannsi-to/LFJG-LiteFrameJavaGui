#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;

uniform vec2 direction;
uniform float radius;
uniform float values[256];
uniform vec2 texelSize;

#define offset (texelSize * direction)

void main() {
    vec4 blr;

    if (radius == 0.0) {
        blr = texture(textureSampler, outTexture).rgba;
    } else {
        blr = texture(textureSampler, outTexture).rgba * values[0];
        for (int i = 1; i <= int(radius); i++) {
            float f = float(i);
            blr += texture(textureSampler, outTexture + f * offset).rgba * values[i];
            blr += texture(textureSampler, outTexture - f * offset).rgba * values[i];
        }
    }

    fragColor = vec4(blr);
}