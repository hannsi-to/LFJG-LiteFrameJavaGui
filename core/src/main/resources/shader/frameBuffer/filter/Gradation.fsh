uniform vec2 gradationCenter;
uniform float gradationAngle;
uniform float gradationWidth;
uniform int gradientShape;
uniform int gradationBlendMode;
uniform vec4 gradationStartColor;
uniform vec4 gradationEndColor;
uniform float gradationIntensity;

vec2 gradationRotate(vec2 pos, float angle) {
    float s = sin(angle);
    float c = cos(angle);
    return vec2(
    pos.x * c - pos.y * s,
    pos.x * s + pos.y * c
    );
}

void gradationMain() {
    vec2 uv = outTexture;
    vec2 rel = uv - gradationCenter / resolution.xy;
    rel.x *= resolution.x / resolution.y;

    vec2 rotated = gradationRotate(rel, gradationAngle);

    vec4 gradientColor = vec4(0.0);
    float dist = 0.0;

    if (gradientShape == 0) {
        float pos = dot(rel, vec2(cos(gradationAngle), sin(gradationAngle))) / gradationWidth;
        float t = smoothstep(-0.5, 0.5, pos);
        gradientColor = mix(gradationStartColor, gradationEndColor, t);
    } else if (gradientShape == 1) {
        dist = length(rotated);
        float t = smoothstep(0.0, gradationWidth, dist);
        gradientColor = mix(gradationStartColor, gradationEndColor, t);
    } else if (gradientShape == 2) {
        vec2 d = abs(rotated) - vec2(gradationWidth);
        dist = length(max(d, 0.0));
        float t = smoothstep(0.0, gradationWidth, dist);
        gradientColor = mix(gradationStartColor, gradationEndColor, t);
    }

    gradientColor *= gradationIntensity;
    fragColor = blend(texture(frameBufferSampler, uv), gradientColor, gradationBlendMode);
}