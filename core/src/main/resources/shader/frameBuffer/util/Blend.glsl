#define LFJG_FUNC_ADD              0x8006
#define LFJG_FUNC_SUBTRACT         0x800A
#define LFJG_FUNC_REVERSE_SUBTRACT 0x800B
#define LFJG_MIN                   0x8007
#define LFJG_MAX                   0x8008

#define LFJG_ZERO                       0
#define LFJG_ONE                        1
#define LFJG_SRC_COLOR                  2
#define LFJG_ONE_MINUS_SRC_COLOR        3
#define LFJG_DST_COLOR                  4
#define LFJG_ONE_MINUS_DST_COLOR        5
#define LFJG_SRC_ALPHA                  6
#define LFJG_ONE_MINUS_SRC_ALPHA        7
#define LFJG_DST_ALPHA                  8
#define LFJG_ONE_MINUS_DST_ALPHA        9
#define LFJG_CONSTANT_COLOR            10
#define LFJG_ONE_MINUS_CONSTANT_COLOR  11
#define LFJG_CONSTANT_ALPHA            12
#define LFJG_ONE_MINUS_CONSTANT_ALPHA  13

vec4 blendEquation(vec4 src, vec4 dst, int eq) {
    if (eq == LFJG_FUNC_ADD){
        return src + dst;
    }

    if (eq == LFJG_FUNC_SUBTRACT){
        return src - dst;
    }

    if (eq == LFJG_FUNC_REVERSE_SUBTRACT){
        return dst - src;
    }

    if (eq == LFJG_MIN){
        return min(src, dst);
    }

    if (eq == LFJG_MAX){
        return max(src, dst);
    }

    return src + dst;
}

vec4 blendFactor(int factor, vec4 src, vec4 dst, vec4 constantColor) {
    if (factor == LFJG_ZERO){
        return vec4(0.0);
    }
    if (factor == LFJG_ONE){
        return vec4(1.0);
    }

    if (factor == LFJG_SRC_COLOR){
        return src;
    }
    if (factor == LFJG_ONE_MINUS_SRC_COLOR) {
        return vec4(1.0) - src;
    }
    if (factor == LFJG_DST_COLOR){
        return dst;
    }
    if (factor == LFJG_ONE_MINUS_DST_COLOR){
        return vec4(1.0) - dst;
    }

    if (factor == LFJG_SRC_ALPHA){
        return vec4(src.a);
    }
    if (factor == LFJG_ONE_MINUS_SRC_ALPHA){
        return vec4(1.0 - src.a);
    }
    if (factor == LFJG_DST_ALPHA){
        return vec4(dst.a);
    }
    if (factor == LFJG_ONE_MINUS_DST_ALPHA){
        return vec4(1.0 - dst.a);
    }

    if (factor == LFJG_CONSTANT_COLOR){
        return constantColor;
    }
    if (factor == LFJG_ONE_MINUS_CONSTANT_COLOR){
        return vec4(1.0) - constantColor;
    }
    if (factor == LFJG_CONSTANT_ALPHA){
        return vec4(constantColor.a);
    }
    if (factor == LFJG_ONE_MINUS_CONSTANT_ALPHA){
        return vec4(1.0 - constantColor.a);
    }

    return vec4(1.0);
}

vec4 openglBlend(vec4 srcColor, vec4 dstColor, int srcFactorRGB, int dstFactorRGB, int srcFactorA, int dstFactorA, int eqRGB, int eqA, vec4 constantColor) {
    vec4 srcRGB = srcColor * blendFactor(srcFactorRGB, srcColor, dstColor, constantColor);
    vec4 dstRGB = dstColor * blendFactor(dstFactorRGB, srcColor, dstColor, constantColor);

    vec4 srcA   = srcColor * blendFactor(srcFactorA, srcColor, dstColor, constantColor);
    vec4 dstA   = dstColor * blendFactor(dstFactorA, srcColor, dstColor, constantColor);

    vec3 rgb = blendEquation(srcRGB, dstRGB, eqRGB).rgb;
    float a  = blendEquation(srcA, dstA, eqA).a;

    return clamp(vec4(rgb, a), 0.0, 1.0);
}