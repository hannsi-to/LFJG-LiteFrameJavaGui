uniform vec4 objectColor;
uniform int objectBlendMode;
uniform bool objectReplaceColor;

void objectMain() {
    vec4 texColor = texture(textureSampler, outTexture);
    vec4 newColor;
    if (objectReplaceColor){
        newColor = objectColor;
    } else {
        newColor = outColor;
    }

    fragColor = blend(texColor, newColor, objectBlendMode);
}