uniform sampler2D frameBufferSampler;

void frameBufferMain() {
    fragColor = texture(frameBufferSampler, outTexture);
}