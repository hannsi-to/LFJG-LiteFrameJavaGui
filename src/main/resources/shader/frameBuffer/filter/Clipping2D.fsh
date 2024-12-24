#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;

uniform bool clippingRect2DInvert = false;
uniform vec4 clippingRect2DSize;
uniform vec2 resolution;

void main() {
    vec2 screenPos = ((outPosition.xy / outPosition.w) * 0.5 + 0.5) * resolution;

    bool insideClippingRect =
    screenPos.x >= clippingRect2DSize.x &&
    screenPos.y >= clippingRect2DSize.y &&
    screenPos.x <= (clippingRect2DSize.x + clippingRect2DSize.z) &&
    screenPos.y <= (clippingRect2DSize.y + clippingRect2DSize.w);

    if (clippingRect2DInvert ? insideClippingRect : !insideClippingRect) {
        discard;
    }

    fragColor = texture(textureSampler, outTexture);
}
