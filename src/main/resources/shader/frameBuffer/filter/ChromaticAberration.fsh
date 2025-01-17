#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform float offsetAmount;
uniform float angle;
uniform float strength;
uniform int aberrationType;

vec2 getOffset(float offset, float angle) {
    return vec2(cos(angle), sin(angle)) * offset;
}

vec4 applyChromaticAberration(vec2 texCoords, float offset, float angle, int type) {
    vec2 offsetVec = getOffset(offset, angle);
    vec4 baseColor = texture(textureSampler, texCoords);
    vec4 color;

    if (type == 0) {
        vec2 redCoords = texCoords + offsetVec;
        vec2 greenCoords = texCoords - offsetVec;
        color = vec4(texture(textureSampler, redCoords).r, texture(textureSampler, greenCoords).g, baseColor.b, baseColor.a);
    } else if (type == 1) {
        vec2 redCoords = texCoords + offsetVec;
        vec2 blueCoords = texCoords - offsetVec;
        color = vec4(texture(textureSampler, redCoords).r, baseColor.g, texture(textureSampler, blueCoords).b, baseColor.a);
    } else if (type == 2) {
        vec2 greenCoords = texCoords + offsetVec;
        vec2 blueCoords = texCoords - offsetVec;
        color = vec4(baseColor.r, texture(textureSampler, greenCoords).g, texture(textureSampler, blueCoords).b, baseColor.a);
    } else if (type == 3) {
        vec2 redCoords = texCoords - offsetVec;
        vec2 greenCoords = texCoords + offsetVec;
        color = vec4(texture(textureSampler, redCoords).r, texture(textureSampler, greenCoords).g, baseColor.b, baseColor.a);
    } else if (type == 4) {
        vec2 redCoords = texCoords - offsetVec;
        vec2 blueCoords = texCoords + offsetVec;
        color = vec4(texture(textureSampler, redCoords).r, baseColor.g, texture(textureSampler, blueCoords).b, baseColor.a);
    } else if (type == 5) {
        vec2 greenCoords = texCoords - offsetVec;
        vec2 blueCoords = texCoords + offsetVec;
        color = vec4(baseColor.r, texture(textureSampler, greenCoords).g, texture(textureSampler, blueCoords).b, baseColor.a);
    }

    return color * strength + baseColor * (1.0 - strength);
}

void main() {
    fragColor = applyChromaticAberration(outTexture, offsetAmount, angle, aberrationType);
}