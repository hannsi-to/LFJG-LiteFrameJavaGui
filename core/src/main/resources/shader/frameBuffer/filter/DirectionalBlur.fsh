uniform float directionalBlurRadius;
uniform float directionalBlurAngle;

void directionalBlurMain() {
    vec2 direction = vec2(cos(directionalBlurAngle), sin(directionalBlurAngle)) / textureSize(frameBufferSampler, 0);// Adjust direction for texture size
    vec4 color = vec4(0.0);
    float totalWeight = 0.0;

    for (float i = -directionalBlurRadius; i <= directionalBlurRadius; i++) {
        vec2 offset = direction * i;
        float weight = 1.0 - abs(i / directionalBlurRadius);
        color += texture(frameBufferSampler, outTexture + offset) * weight;
        totalWeight += weight;
    }

    fragColor = color / totalWeight;
}