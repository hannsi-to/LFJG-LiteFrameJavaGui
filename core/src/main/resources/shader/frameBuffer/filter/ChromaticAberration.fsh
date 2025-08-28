uniform float chromaticAberrationOffsetAmount;
uniform float chromaticAberrationAngle;
uniform float chromaticAberrationStrength;
uniform int aberrationType;

vec2 chromaticAberrationGetOffset(float offset, float angle) {
    return vec2(cos(angle), sin(angle)) * offset;
}

vec2 chromaticAberrationClampUV(vec2 uv) {
    return clamp(uv, vec2(0.0), vec2(1.0));
}

vec4 applyChromaticAberration(vec2 texCoords, float offset, float angle, int type) {
    vec2 offsetVec = chromaticAberrationGetOffset(offset, angle);
    vec4 baseColor = texture(frameBufferSampler, texCoords);
    vec4 color;
    vec2 uvP = chromaticAberrationClampUV(texCoords + offsetVec);
    vec2 uvM = chromaticAberrationClampUV(texCoords - offsetVec);

    if (type == 0) {
        vec2 redCoords = uvP;
        vec2 greenCoords = uvM;
        color = vec4(texture(frameBufferSampler, redCoords).r, texture(frameBufferSampler, greenCoords).g, baseColor.b, baseColor.a);
    } else if (type == 1) {
        vec2 redCoords = uvP;
        vec2 blueCoords = uvM;
        color = vec4(texture(frameBufferSampler, redCoords).r, baseColor.g, texture(frameBufferSampler, blueCoords).b, baseColor.a);
    } else if (type == 2) {
        vec2 greenCoords = uvP;
        vec2 blueCoords = uvM;
        color = vec4(baseColor.r, texture(frameBufferSampler, greenCoords).g, texture(frameBufferSampler, blueCoords).b, baseColor.a);
    } else if (type == 3) {
        vec2 redCoords = uvP;
        vec2 greenCoords = uvM;
        color = vec4(texture(frameBufferSampler, redCoords).r, texture(frameBufferSampler, greenCoords).g, baseColor.b, baseColor.a);
    } else if (type == 4) {
        vec2 redCoords = uvP;
        vec2 blueCoords = uvM;
        color = vec4(texture(frameBufferSampler, redCoords).r, baseColor.g, texture(frameBufferSampler, blueCoords).b, baseColor.a);
    } else if (type == 5) {
        vec2 greenCoords = uvP;
        vec2 blueCoords = uvM;
        color = vec4(baseColor.r, texture(frameBufferSampler, greenCoords).g, texture(frameBufferSampler, blueCoords).b, baseColor.a);
    }

    return color * chromaticAberrationStrength + baseColor * (1.0 - chromaticAberrationStrength);
}

void chromaticAberrationMain() {
    fragColor = applyChromaticAberration(outTexture, chromaticAberrationOffsetAmount, chromaticAberrationAngle, aberrationType);
}