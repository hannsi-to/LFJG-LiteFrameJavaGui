#version 330 core

in vec4 outPosition;
in vec2 outTexture;
out vec4 fragColor;

uniform sampler2D fontAtlas;
uniform vec4 fontColor;
uniform float distanceRange;
uniform float boldness;
uniform bool box;
uniform vec4 uvSize;
uniform bool outline;
uniform float outlineWidth;
uniform float blurSize;

float median(float r, float g, float b) {
    return max(min(r, g), min(max(r, g), b));
}

float distanceToLine(vec2 p, vec2 a, vec2 b) {
    vec2 pa = p - a;
    vec2 ba = b - a;
    float h = clamp(dot(pa, ba) / dot(ba, ba), 0.0, 1.0);
    return length(pa - ba * h);
}

void main() {
    vec3 msdf = texture(fontAtlas, outTexture).rgb;
    float sd = median(msdf.r, msdf.g, msdf.b);
    sd += boldness;

    float screenPxDistance = fwidth(sd);

    float innerEdge = 0.5;
    float outerEdge = 0.5 + outlineWidth * 0.5;

    float fillAlpha = smoothstep(innerEdge - screenPxDistance, innerEdge + screenPxDistance, sd);
    float outlineAlpha = smoothstep(outerEdge - screenPxDistance, outerEdge + screenPxDistance, sd);
    float outlineOnlyAlpha = clamp(outlineAlpha * (1.0 - fillAlpha), 0.0, 1.0);

    vec4 resultColor;
    vec4 uOutlineColor = vec4(1, 0, 0, 1);

    if (outline) {
        resultColor = vec4(uOutlineColor.rgb, outlineOnlyAlpha * uOutlineColor.a);
    } else {
        resultColor = vec4(fontColor.rgb, fillAlpha * fontColor.a);
    }

    bool isBorder =
    outTexture.x < uvSize.x || outTexture.x > uvSize.z ||
    outTexture.y < uvSize.y || outTexture.y > uvSize.w;

    if (isBorder && box) {
        fragColor = fontColor;
    } else {
        fragColor = resultColor;
    }
}