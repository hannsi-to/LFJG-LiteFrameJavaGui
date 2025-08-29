uniform sampler2D objectClippingTextureSampler;
uniform bool objectClippingInvert;

void objectClippingMain() {
    vec4 color = texture(frameBufferSampler, outTexture);
    vec4 mask = texture(objectClippingTextureSampler, outTexture);

    if (objectClippingInvert ? mask.a != 0.0 : mask.a == 0.0) {
        discard;
    }

    fragColor = color;
}