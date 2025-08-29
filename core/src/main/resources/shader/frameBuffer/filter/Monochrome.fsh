uniform float monochromeIntensity;
uniform vec3 monochromeColor;
uniform bool monochromePreserveBrightness;

void monochromeMain() {
    vec4 texColor = texture(frameBufferSampler, outTexture);

    vec3 monoColor;
    if (monochromePreserveBrightness) {
        float brightness = dot(texColor.rgb, vec3(0.299, 0.587, 0.114));
        monoColor = mix(texColor.rgb, monochromeColor * brightness, monochromeIntensity);
    } else {
        monoColor = mix(texColor.rgb, monochromeColor, monochromeIntensity);
    }

    fragColor = vec4(monoColor, texColor.a);
}