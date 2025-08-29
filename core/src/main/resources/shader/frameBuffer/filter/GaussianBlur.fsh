uniform vec2 gaussianBlurDirection;
uniform float gaussianBlurRadius;
uniform float gaussianBlurValues[256];

#define texelSize (1.0 / gl_FragCoord.xy)

void gaussianBlurMain() {
    vec4 blr = vec4(0.0);
    int radius = int(gaussianBlurRadius);

    blr += texture(frameBufferSampler, outTexture) * gaussianBlurValues[0];
    for (int i = 1; i <= radius; i++) {
        vec2 offset = float(i) * texelSize * gaussianBlurDirection;
        blr += texture(frameBufferSampler, outTexture + offset) * gaussianBlurValues[i];
        blr += texture(frameBufferSampler, outTexture - offset) * gaussianBlurValues[i];
    }

    fragColor = blr;
}