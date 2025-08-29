uniform bool colorChangerAplha;
uniform vec4 colorChangerTargetColor;
uniform vec4 colorChangerNewColor;

void colorChangerMain() {
    vec4 baseColor = texture(frameBufferSampler, outTexture);

    if (colorChangerAplha){
        if (baseColor.rgba == colorChangerTargetColor.rgba){
            baseColor.rgba = colorChangerNewColor.rgba;
        }
    } else {
        if (baseColor.rgb == colorChangerTargetColor.rgb){
            baseColor.rgb = colorChangerNewColor.rgb;
        }
    }

    fragColor = baseColor;
}