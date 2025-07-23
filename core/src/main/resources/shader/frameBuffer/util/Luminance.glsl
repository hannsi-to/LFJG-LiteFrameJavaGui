float luminance(vec3 color) {
    return 0.299 * color.r + 0.587 * color.g + 0.114 * color.b;
}

vec3 setLuminance(vec3 color, float lum) {
    float d = lum - luminance(color);
    return color + vec3(d);
}