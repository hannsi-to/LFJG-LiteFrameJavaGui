#version 330

in vec4 outPosition;
in vec4 outColor;
in vec2 outTexture;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform int fragmentShaderType;
uniform ivec2 resolution;

#include "shader/frameBuffer/util/Luminance.glsl"
#include "shader/frameBuffer/util/Blend.glsl"
#include "shader/frameBuffer/util/MathUtil.glsl"

#include "shader/scene/object/FragmentShader.fsh"
#include "shader/scene/model/FragmentShader.fsh"
#include "shader/frameBuffer/FragmentShader.fsh"
#include "shader/msdf/FragmentShader.fsh"
#include "shader/frameBuffer/filter/Bloom.fsh"
#include "shader/frameBuffer/filter/BoxBlur.fsh"
#include "shader/frameBuffer/filter/ChromaKey.fsh"
#include "shader/frameBuffer/filter/ChromaticAberration.fsh"
#include "shader/frameBuffer/filter/ClippingRect.fsh"
#include "shader/frameBuffer/filter/ColorChanger.fsh"
#include "shader/frameBuffer/filter/ColorCorrection.fsh"
#include "shader/frameBuffer/filter/DiagonalClipping.fsh"
#include "shader/frameBuffer/filter/DirectionalBlur.fsh"
#include "shader/frameBuffer/filter/EdgeExtraction.fsh"
#include "shader/frameBuffer/filter/FXAA.fsh"
#include "shader/frameBuffer/filter/Flash.fsh"
#include "shader/frameBuffer/filter/GaussianBlur.fsh"
#include "shader/frameBuffer/filter/Glow.fsh"
#include "shader/frameBuffer/filter/Gradation.fsh"
#include "shader/frameBuffer/filter/Inversion.fsh"
#include "shader/frameBuffer/filter/LensBlur.fsh"
#include "shader/frameBuffer/filter/LuminanceKey.fsh"

void main() {
    switch (fragmentShaderType){
        case 0: objectMain(); break;
        case 1: modelMain(); break;
        case 2: frameBufferMain(); break;
        case 3: msdfMain(); break;
        case 4: bloomMain(); break;
        case 5: boxBlurMain(); break;
        case 6: chromaKeyMain(); break;
        case 7: chromaticAberrationMain(); break;
        case 8: clippingRectMain(); break;
        case 9: colorChangerMain(); break;
        case 10: colorCorrectionMain(); break;
        case 11: diagonalClippingMain(); break;
        case 12: directionalBlurMain(); break;
        case 13: edgeExtractionMain(); break;
        case 14: fxaaMain(); break;
        case 15: flashMain(); break;
        case 16: gaussianBlurMain(); break;
        case 17: gaussianBlurMain(); break;
        case 18: glowMain(); break;
        case 19: gradationMain(); break;
        case 20: inversionMain(); break;
        case 21: lensBlurMain(); break;
        case 22: luminanceKeyMain(); break;
    }
}