uniform bool inversionFlipVertical;
uniform bool inversionFlipHorizontal;
uniform bool inversionInvertBrightness;
uniform bool inversionInvertHue;
uniform bool inversionInvertAlpha;

void inversionMain() {
    vec2 texCoords = outTexture;
    if (inversionFlipVertical) {
        texCoords.y = 1.0 - texCoords.y;
    }
    if (inversionFlipHorizontal) {
        texCoords.x = 1.0 - texCoords.x;
    }

    vec4 color = texture(textureSampler, texCoords);

    if (inversionInvertBrightness) {
        color.rgb = vec3(1.0) - color.rgb;
    }

    if (inversionInvertHue) {
        vec3 hsv = rgb2hsv(color.rgb);
        hsv.x = mod(hsv.x + 0.5, 1.0);
        color.rgb = hsv2rgb(hsv);
    }

    if (inversionInvertAlpha) {
        color.a = color.a > 0.5 ? 0.0 : 1.0;
    }

    fragColor = color;
}