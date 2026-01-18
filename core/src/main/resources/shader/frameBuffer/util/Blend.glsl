vec4 blendNoBlend(vec4 src, vec4 dst){
    return dst;
}

vec4 blendNormal(vec4 src, vec4 dst){
    vec3 rgb = src.rgb;
    float a  = src.a;
    return vec4(rgb, a);
}

vec4 blendAlpha(vec4 src, vec4 dst){
    vec3 rgb = src.rgb * src.a + dst.rgb * (1.0 - src.a);
    float a  = src.a + dst.a * (1.0 - src.a);
    return vec4(rgb, a);
}

vec4 blendAdd(vec4 src, vec4 dst){
    vec3 rgb = src.rgb * src.a + dst.rgb;
    float a  = src.a + dst.a;
    return vec4(rgb, a);
}

vec4 blendMultiply(vec4 src, vec4 dst){
    vec3 rgb = src.rgb * dst.rgb;
    float a  = src.a * dst.a;
    return vec4(rgb, a);
}

vec4 blendScreen(vec4 src, vec4 dst){
    vec3 rgb = src.rgb + dst.rgb * (1.0 - src.rgb);
    float a  = src.a + dst.a * (1.0 - src.a);
    return vec4(rgb, a);
}

vec4 blendSubtract(vec4 src, vec4 dst){
    vec3 rgb = dst.rgb - src.rgb * src.a;
    float a  = dst.a;
    return vec4(rgb, a);
}

vec4 blendMax(vec4 src, vec4 dst){
    return vec4(max(src.rgb, dst.rgb), max(src.a, dst.a));
}

vec4 blendMin(vec4 src, vec4 dst){
    return vec4(min(src.rgb, dst.rgb), min(src.a, dst.a));
}

vec4 blendInvert(vec4 src, vec4 dst){
    vec3 rgb = (1.0 - dst.rgb) * src.rgb;
    float a  = src.a;
    return vec4(rgb, a);
}

vec4 blendPremultipliedAlpha(vec4 src, vec4 dst){
    vec3 rgb = src.rgb + dst.rgb * (1.0 - src.a);
    float a  = src.a + dst.a * (1.0 - src.a);
    return vec4(rgb, a);
}

vec4 blendAddNoAlpha(vec4 src, vec4 dst){
    vec3 rgb = src.rgb + dst.rgb;
    return vec4(rgb, dst.a);
}

vec4 blendSubtractNormal(vec4 src, vec4 dst){
    vec3 rgb = src.rgb - dst.rgb;
    float a  = src.a;
    return vec4(rgb, a);
}

vec4 blendDarken(vec4 src, vec4 dst){
    return vec4(min(src.rgb, dst.rgb), dst.a);
}

vec4 blendLighten(vec4 src, vec4 dst){
    return vec4(max(src.rgb, dst.rgb), dst.a);
}

vec4 blendColorOnly(vec4 src, vec4 dst){
    return vec4(src.rgb, dst.a);
}

vec4 blendAlphaOnly(vec4 src, vec4 dst){
    return vec4(dst.rgb, src.a);
}

vec4 blendMultiplyAlpha(vec4 src, vec4 dst){
    vec3 rgb = src.rgb * dst.rgb;
    float a  = src.a + dst.a * (1.0 - src.a);
    return vec4(rgb, a);
}

vec4 applyBlend(vec4 src, vec4 dst, uint mode){
    switch (mode){
        case -1: return blendNoBlend(src, dst);
        case 0:  return blendNormal(src, dst);
        case 1:  return blendAlpha(src, dst);
        case 2:  return blendAdd(src, dst);
        case 3:  return blendMultiply(src, dst);
        case 4:  return blendScreen(src, dst);
        case 5:  return blendSubtract(src, dst);
        case 6:  return blendMax(src, dst);
        case 7:  return blendMin(src, dst);
        case 8:  return blendInvert(src, dst);
        case 9:  return blendPremultipliedAlpha(src, dst);
        case 10: return blendAddNoAlpha(src, dst);
        case 11: return blendSubtractNormal(src, dst);
        case 12: return blendDarken(src, dst);
        case 13: return blendLighten(src, dst);
        case 14: return blendColorOnly(src, dst);
        case 15: return blendAlphaOnly(src, dst);
        case 16: return blendMultiplyAlpha(src, dst);
        case 17: return blendPremultipliedAlpha(src, dst);
    }
    return src;
}