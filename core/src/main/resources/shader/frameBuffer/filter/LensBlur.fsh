uniform float lensBlurRange;
uniform float lensBlurIntensity;
uniform float lensBlurSigma;
uniform int lensBlurRadialSteps;
uniform int lensBlurAngularSamples;

void lensBlurMain() {
    vec2 texSize = vec2(textureSize(frameBufferSampler, 0));
    vec2 texCoord = outTexture;

    vec4 color = vec4(0.0);
    float totalWeight = 0.0;

    for (int i = 0; i < lensBlurAngularSamples; i++) {
        float angle = float(i) * 6.2831853 / float(lensBlurAngularSamples);
        vec2 dir = vec2(cos(angle), sin(angle));

        for (int j = 0; j <= lensBlurRadialSteps; j++) {
            float r = float(j) * lensBlurRange / float(lensBlurRadialSteps);
            vec2 offset = dir * r / texSize;

            float weight = exp(-(r * r) / (2.0 * lensBlurSigma * lensBlurSigma));
            vec4 sampleT = texture(frameBufferSampler, texCoord + offset);

            color.rgb += sampleT.rgb * weight;
            totalWeight += weight;
        }
    }

    color.rgb /= totalWeight;
    color.rgb *= lensBlurIntensity;
    color.a = texture(frameBufferSampler, texCoord).a;

    fragColor = color;
}