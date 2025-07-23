#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform bool flipVertical;
uniform bool flipHorizontal;
uniform bool invertBrightness;
uniform bool invertHue;
uniform bool invertAlpha;

#include "shader/frameBuffer/util/MathUtil.glsl"

void main() {
    vec2 texCoords = outTexture;
    if (flipVertical) {
        texCoords.y = 1.0 - texCoords.y;
    }
    if (flipHorizontal) {
        texCoords.x = 1.0 - texCoords.x;
    }

    vec4 color = texture(textureSampler, texCoords);

    if (invertBrightness) {
        color.rgb = vec3(1.0) - color.rgb;
    }

    if (invertHue) {
        vec3 hsv = rgb2hsv(color.rgb);
        hsv.x = mod(hsv.x + 0.5, 1.0);
        color.rgb = hsv2rgb(hsv);
    }

    if (invertAlpha) {
        color.a = color.a > 0.5 ? 0.0 : 1.0;
    }

    fragColor = color;
}