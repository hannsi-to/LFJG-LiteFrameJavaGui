uniform float flashIntensity;
uniform vec2 flashScreenPosition;
uniform int flashBlendMode;
uniform vec3 flashLightColor;

float flashComputeAttenuation(vec2 texCoords, vec2 center, float intensity) {
    float distance = length(texCoords - center);
    float constant = 0.1;
    float linear = 0.2;
    float quadratic = 0.8;
    float attenuation = intensity / (constant + linear * distance + quadratic * (distance * distance));
    return clamp(attenuation, 0.0, 1.0);
}

void flashMain() {
    vec2 screenSize = gl_FragCoord.xy;
    vec4 baseColor = texture(frameBufferSampler, outTexture);
    vec2 lightUV = flashScreenPosition / screenSize;
    float attenuation = flashComputeAttenuation(outTexture, lightUV, flashIntensity);
    vec3 flashColor = flashLightColor * attenuation;
    vec3 flashWithBaseColor = baseColor.rgb + baseColor.rgb * flashColor;

    if (flashBlendMode == 0) {
        fragColor = vec4(baseColor.rgb + flashColor, baseColor.a);
    } else if (flashBlendMode == 1) {
        fragColor = vec4(baseColor.rgb * flashColor, baseColor.a);
    } else if (flashBlendMode == 2) {
        fragColor = vec4(flashColor, 1);
    } else if (flashBlendMode == 3) {
        fragColor = vec4(baseColor.rgb + flashWithBaseColor, baseColor.a);
    } else {
        fragColor = baseColor;
    }
}