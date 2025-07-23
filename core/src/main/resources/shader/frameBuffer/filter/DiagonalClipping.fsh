#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;

uniform vec2 clipCenter;
uniform float clipAngle;
uniform float blurWidth;
uniform bool invertClip;

void main() {
    vec4 texColor = texture(textureSampler, outTexture);

    vec2 fragCoord = outPosition.xy;

    vec2 toFrag = fragCoord - clipCenter;
    float distanceToLine = toFrag.x * cos(clipAngle) + toFrag.y * sin(clipAngle);

    float edge = smoothstep(-blurWidth, 0.0, distanceToLine);

    if (invertClip) {
        edge = 1.0 - edge;
    }

    if (edge <= 0.0) {
        discard;
    }

    fragColor = texColor * edge;
}