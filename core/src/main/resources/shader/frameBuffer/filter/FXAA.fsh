uniform bool fxaaUseAlpha;

#define texelStep vec2(1 / gl_FragCoord.x, 1 / gl_FragCoord.y)

void fxaaMain() {
    vec4 rgbaNW = texture(frameBufferSampler, outTexture + vec2(-texelStep.x, -texelStep.y));
    vec4 rgbaNE = texture(frameBufferSampler, outTexture + vec2(texelStep.x, -texelStep.y));
    vec4 rgbaSW = texture(frameBufferSampler, outTexture + vec2(-texelStep.x, texelStep.y));
    vec4 rgbaSE = texture(frameBufferSampler, outTexture + vec2(texelStep.x, texelStep.y));
    vec4 rgbaM  = texture(frameBufferSampler, outTexture);

    vec3 luma = vec3(0.299, 0.587, 0.114);
    float lumaNW = dot(rgbaNW.rgb, luma);
    float lumaNE = dot(rgbaNE.rgb, luma);
    float lumaSW = dot(rgbaSW.rgb, luma);
    float lumaSE = dot(rgbaSE.rgb, luma);
    float lumaM  = dot(rgbaM.rgb, luma);

    float lumaMin = min(lumaM, min(min(lumaNW, lumaNE), min(lumaSW, lumaSE)));
    float lumaMax = max(lumaM, max(max(lumaNW, lumaNE), max(lumaSW, lumaSE)));

    vec2 dir = vec2(-((lumaNW + lumaNE) - (lumaSW + lumaSE)), ((lumaNW + lumaSW) - (lumaNE + lumaSE)));

    float dirReduce = max((lumaNW + lumaNE + lumaSW + lumaSE) * (0.25 * (1.0 / 4.0)), 1.0 / 128.0);
    float rcpDirMin = 1.0 / (min(abs(dir.x), abs(dir.y)) + dirReduce);
    dir = min(vec2(8.0, 8.0), max(vec2(-8.0, -8.0), dir * rcpDirMin)) * texelStep;

    vec3 rgbA = 0.5 * (texture(frameBufferSampler, outTexture + dir * vec2(1.0/3.0 - 0.5)).rgb + texture(frameBufferSampler, outTexture + dir * vec2(2.0/3.0 - 0.5)).rgb);
    vec3 rgbB = rgbA * 0.5 + 0.25 * (texture(frameBufferSampler, outTexture + dir * vec2(0.0 - 0.5)).rgb + texture(frameBufferSampler, outTexture + dir * vec2(1.0 - 0.5)).rgb);

    float lumaB = dot(rgbB, luma);

    vec3 finalColor;
    if ((lumaB < lumaMin) || (lumaB > lumaMax)) {
        finalColor = rgbA;
    } else {
        finalColor = rgbB;
    }

    float alpha = fxaaUseAlpha ? rgbaM.a : 1.0;
    fragColor = vec4(finalColor, alpha);
}