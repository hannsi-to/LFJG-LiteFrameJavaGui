#version 330

in vec4 outColor;
in vec2 outTextureCoord;

out vec4 fragColor;

uniform int materialType;
uniform sampler2D textureSampler;

void main()
{
    if (materialType == 1){
        fragColor = outColor;
    } else if (materialType == 2){
        fragColor = texture(textureSampler, outTextureCoord);
    }
}
