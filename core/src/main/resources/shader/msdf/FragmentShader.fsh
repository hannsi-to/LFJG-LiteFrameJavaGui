#version 330 core

in vec2 outTexture;// 頂点シェーダーからのUV座標
out vec4 fragColor;

uniform sampler2D uFontAtlas;// MSDFフォントテクスチャ
uniform vec3 uFontColor;// テキストの色
// uDistanceRangeはJSONのdistanceRangeと同じ値であるべきですが、
// fwidthを使う場合、より繊細な調整に使います。
uniform float uDistanceRange;

// 3つのfloat値の中間値を取得する関数
float median(float r, float g, float b) {
    return max(min(r, g), min(max(r, g), b));
}

void main() {
    // テクスチャから符号付き距離フィールドの値をサンプリング
    vec3 msdf = texture(uFontAtlas, outTexture).rgb;

    // R, G, Bチャンネルから中間値を取得
    float sd = median(msdf.r, msdf.g, msdf.b);

    // 画面上で1ピクセルがどのくらいの距離値に相当するかを計算
    // これがアンチエイリアシングを滑らかにする鍵です
    float screenPxDistance = fwidth(sd);

    // smoothstepを使って、0.5の境界線を挟んで滑らかなアルファ遷移を生成
    // screenPxDistanceの幅でアンチエイリアシングがかかります
    float alpha = smoothstep(0.5 - screenPxDistance, 0.5 + screenPxDistance, sd);

    // テキストの色と計算されたアルファ値を乗算して最終的な色を決定
    fragColor = vec4(uFontColor, alpha);
}

    