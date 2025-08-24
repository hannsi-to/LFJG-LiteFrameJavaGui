uniform int modelMaterialType;

void modelMain(){
    if (modelMaterialType == 1){
        fragColor = outColor;
    } else if (modelMaterialType == 2){
        fragColor = texture(textureSampler, outTexture);
    }
}
