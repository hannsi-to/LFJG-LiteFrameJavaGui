#version 330 core

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;

uniform float edgeStrength;
uniform float threshold;
uniform bool enableLuminanceEdge;
uniform bool enableAlphaEdge;
uniform vec4 edgeColor;

void main() {
    vec2 texCoord = outTexture;

    float offset = 1.0 / 512.0;

    vec4 texCenter = texture(textureSampler, texCoord);
    vec3 center = texCenter.rgb;
    vec3 left = texture(textureSampler, texCoord + vec2(-offset, 0.0)).rgb;
    vec3 right = texture(textureSampler, texCoord + vec2(offset, 0.0)).rgb;
    vec3 up = texture(textureSampler, texCoord + vec2(0.0, offset)).rgb;
    vec3 down = texture(textureSampler, texCoord + vec2(0.0, -offset)).rgb;

    float luminanceCenter = dot(center, vec3(0.299, 0.587, 0.114));
    float luminanceLeft = dot(left, vec3(0.299, 0.587, 0.114));
    float luminanceRight = dot(right, vec3(0.299, 0.587, 0.114));
    float luminanceUp = dot(up, vec3(0.299, 0.587, 0.114));
    float luminanceDown = dot(down, vec3(0.299, 0.587, 0.114));

    float edgeX = luminanceRight - luminanceLeft;
    float edgeY = luminanceUp - luminanceDown;
    float edgeMagnitude = sqrt(edgeX * edgeX + edgeY * edgeY);
    edgeMagnitude *= edgeStrength;

    float edge = step(threshold, edgeMagnitude);

    fragColor = texCenter;

    if (enableLuminanceEdge) {
        fragColor = edge * edgeColor;
    }

    if (enableAlphaEdge) {
        fragColor.a *= edge;
    }
}