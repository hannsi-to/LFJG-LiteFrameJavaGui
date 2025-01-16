#version 330

in vec4 outPosition;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;

uniform vec2 texelStep;

void main() {
    vec3 rgbNW = texture(textureSampler, outTexture + vec2(-texelStep.x, -texelStep.y)).rgb;
    vec3 rgbNE = texture(textureSampler, outTexture + vec2(texelStep.x, -texelStep.y)).rgb;
    vec3 rgbSW = texture(textureSampler, outTexture + vec2(-texelStep.x, texelStep.y)).rgb;
    vec3 rgbSE = texture(textureSampler, outTexture + vec2(texelStep.x, texelStep.y)).rgb;
    vec3 rgbM  = texture(textureSampler, outTexture).rgb;

    vec3 luma = vec3(0.299, 0.587, 0.114);
    float lumaNW = dot(rgbNW, luma);
    float lumaNE = dot(rgbNE, luma);
    float lumaSW = dot(rgbSW, luma);
    float lumaSE = dot(rgbSE, luma);
    float lumaM  = dot(rgbM, luma);

    float lumaMin = min(lumaM, min(min(lumaNW, lumaNE), min(lumaSW, lumaSE)));
    float lumaMax = max(lumaM, max(max(lumaNW, lumaNE), max(lumaSW, lumaSE)));

    vec2 dir = vec2(-((lumaNW + lumaNE) - (lumaSW + lumaSE)), ((lumaNW + lumaSW) - (lumaNE + lumaSE)));

    float dirReduce = max((lumaNW + lumaNE + lumaSW + lumaSE) * (0.25 * (1.0 / 4.0)), 1.0 / 128.0);
    float rcpDirMin = 1.0 / (min(abs(dir.x), abs(dir.y)) + dirReduce);
    dir = min(vec2(8.0, 8.0), max(vec2(-8.0, -8.0), dir * rcpDirMin)) * texelStep;

    vec3 rgbA = 0.5 * (texture(textureSampler, outTexture + dir * vec2(1.0/3.0 - 0.5)).rgb + texture(textureSampler, outTexture + dir * vec2(2.0/3.0 - 0.5)).rgb);

    vec3 rgbB = rgbA * 0.5 + 0.25 * (texture(textureSampler, outTexture + dir * vec2(0.0 - 0.5)).rgb + texture(textureSampler, outTexture + dir * vec2(1.0 - 0.5)).rgb);

    float lumaB = dot(rgbB, luma);

    if ((lumaB < lumaMin) || (lumaB > lumaMax)) {
        fragColor = vec4(rgbA, 1.0);
    } else {
        fragColor = vec4(rgbB, 1.0);
    }
}