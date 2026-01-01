#define LFJG_FUNC_ADD              0x8006
#define LFJG_FUNC_SUBTRACT         0x800A
#define LFJG_FUNC_REVERSE_SUBTRACT 0x800B
#define LFJG_MIN                   0x8007
#define LFJG_MAX                   0x8008

#define LFJG_ZERO                      0x0000
#define LFJG_ONE                       0x0001
#define LFJG_SRC_COLOR                 0x0300
#define LFJG_ONE_MINUS_SRC_COLOR       0x0301
#define LFJG_DST_COLOR                 0x0306
#define LFJG_ONE_MINUS_DST_COLOR       0x0307
#define LFJG_SRC_ALPHA                 0x0302
#define LFJG_ONE_MINUS_SRC_ALPHA       0x0303
#define LFJG_DST_ALPHA                 0x0304
#define LFJG_ONE_MINUS_DST_ALPHA       0x0305
#define LFJG_CONSTANT_COLOR            0x8001
#define LFJG_ONE_MINUS_CONSTANT_COLOR  0x8002
#define LFJG_CONSTANT_ALPHA            0x8003
#define LFJG_ONE_MINUS_CONSTANT_ALPHA  0x8004

const int factors[] = int[](
LFJG_ZERO,
LFJG_ONE,
LFJG_SRC_COLOR,
LFJG_ONE_MINUS_SRC_COLOR,
LFJG_DST_COLOR,
LFJG_ONE_MINUS_DST_COLOR,
LFJG_SRC_ALPHA,
LFJG_ONE_MINUS_SRC_ALPHA,
LFJG_DST_ALPHA,
LFJG_ONE_MINUS_DST_ALPHA,
LFJG_CONSTANT_COLOR,
LFJG_ONE_MINUS_CONSTANT_COLOR,
LFJG_CONSTANT_ALPHA,
LFJG_ONE_MINUS_CONSTANT_ALPHA
);

const int equations[] = int[](
LFJG_FUNC_ADD,
LFJG_FUNC_SUBTRACT,
LFJG_FUNC_REVERSE_SUBTRACT,
LFJG_MIN,
LFJG_MAX
);

vec3 blendEqRGB(vec3 s, vec3 d, int eq)
{
    if (eq == LFJG_FUNC_ADD)              return s + d;
    if (eq == LFJG_FUNC_SUBTRACT)         return s - d;
    if (eq == LFJG_FUNC_REVERSE_SUBTRACT) return d - s;
    if (eq == LFJG_MIN)                   return min(s, d);
    if (eq == LFJG_MAX)                   return max(s, d);
    return s + d;
}

float blendEqA(float s, float d, int eq)
{
    if (eq == LFJG_FUNC_ADD)              return s + d;
    if (eq == LFJG_FUNC_SUBTRACT)         return s - d;
    if (eq == LFJG_FUNC_REVERSE_SUBTRACT) return d - s;
    if (eq == LFJG_MIN)                   return min(s, d);
    if (eq == LFJG_MAX)                   return max(s, d);
    return s + d;
}

vec3 blendFactorRGB(int f, vec4 src, vec4 dst, vec4 c)
{
    if (f == LFJG_ZERO)                     return vec3(0);
    if (f == LFJG_ONE)                      return vec3(1);
    if (f == LFJG_SRC_COLOR)                return src.rgb;
    if (f == LFJG_ONE_MINUS_SRC_COLOR)      return 1.0 - src.rgb;
    if (f == LFJG_DST_COLOR)                return dst.rgb;
    if (f == LFJG_ONE_MINUS_DST_COLOR)      return 1.0 - dst.rgb;
    if (f == LFJG_SRC_ALPHA)                return vec3(src.a);
    if (f == LFJG_ONE_MINUS_SRC_ALPHA)      return vec3(1.0 - src.a);
    if (f == LFJG_DST_ALPHA)                return vec3(dst.a);
    if (f == LFJG_ONE_MINUS_DST_ALPHA)      return vec3(1.0 - dst.a);
    if (f == LFJG_CONSTANT_COLOR)           return c.rgb;
    if (f == LFJG_ONE_MINUS_CONSTANT_COLOR) return 1.0 - c.rgb;

    return vec3(0);
}

float blendFactorA(int f, vec4 src, vec4 dst, vec4 c)
{
    if (f == LFJG_ZERO)                     return 0.0;
    if (f == LFJG_ONE)                      return 1.0;
    if (f == LFJG_SRC_ALPHA)                return src.a;
    if (f == LFJG_ONE_MINUS_SRC_ALPHA)      return 1.0 - src.a;
    if (f == LFJG_DST_ALPHA)                return dst.a;
    if (f == LFJG_ONE_MINUS_DST_ALPHA)      return 1.0 - dst.a;
    if (f == LFJG_CONSTANT_ALPHA)           return c.a;
    if (f == LFJG_ONE_MINUS_CONSTANT_ALPHA) return 1.0 - c.a;

    return 0.0;
}

vec4 openglBlend(vec4 src, vec4 dst, int srcRGB, int dstRGB, int srcA, int dstA, int eqRGB, int eqA, vec4 constantColor) {
    vec3 rgb;
    float a;

    if (eqRGB == LFJG_MIN || eqRGB == LFJG_MAX) {
        rgb = blendEqRGB(src.rgb, dst.rgb, eqRGB);
    } else {
        vec3 s = src.rgb * blendFactorRGB(srcRGB, src, dst, constantColor);
        vec3 d = dst.rgb * blendFactorRGB(dstRGB, src, dst, constantColor);
        rgb = blendEqRGB(s, d, eqRGB);
    }

    if (eqA == LFJG_MIN || eqA == LFJG_MAX) {
        a = blendEqA(src.a, dst.a, eqA);
    } else {
        float s = src.a * blendFactorA(srcA, src, dst, constantColor);
        float d = dst.a * blendFactorA(dstA, src, dst, constantColor);
        a = blendEqA(s, d, eqA);
    }

    return clamp(vec4(rgb, a), 0.0, 1.0);
}