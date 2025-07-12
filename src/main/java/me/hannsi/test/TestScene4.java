package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.effect.effects.DrawObject;
import me.hannsi.lfjg.render.effect.effects.LuminanceKey;
import me.hannsi.lfjg.render.effect.effects.Texture;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.utils.reflection.location.Location;
import me.hannsi.lfjg.utils.type.types.BlendType;

public class TestScene4 implements IScene {
    public Scene scene;

    public GLRect glRect;
    public TextureCache textureCache;

    public TestScene4(Frame frame) {
        this.scene = new Scene("TestScene4", this);
    }

    @Override
    public void init() {
        glRect = new GLRect("glRect");
        glRect.uv(0, 1f, 1f, 0);
        glRect.rect(0, 0, 1920, 1080, Color.of(0, 0, 0, 0));

        textureCache = TextureCache.createTextureCache()
                .createCache("Texture1", Location.fromResource("texture/test/test_image_3840x2160.jpg"));

        EffectCache.initEffectCache()
                .createCache("Texture", new Texture(textureCache, "Texture1", BlendType.NORMAL))
                .createCache("DrawObject", DrawObject.createDrawObject())
//                .createCache("Bloom", Bloom.createBloom())
//                .createCache("BoxBlur", BoxBlur.createBoxBlur())
//                .createCache("ChromaKey", ChromaKey.createChromaKey().chromaKeyColor(Color.YELLOW))
//                .createCache("ChromaticAberration", ChromaticAberration.createChromaticAberration())
//                .createCache("Clipping2DRect", Clipping2DRect.createClipping2DRect().x1(100).y1(100).x2(500).y2(500))
//                .createCache("ColorChanger", ColorChanger.createColorChanger().targetColor(Color.of(251, 232, 44, 255)))
//                .createCache("ColorCorrection", ColorCorrection.createColorCorrection())
//                .createCache("DiagonalClipping", DiagonalClipping.createDiagonalClipping())
//                .createCache("DirectionalBlur", DirectionalBlur.createDirectionBlur())
//                .createCache("DrawFrameBuffer", DrawFrameBuffer.createDrawFrameBuffer().translateX(100))
//                .createCache("EdgeExtraction", EdgeExtraction.createEdgeExtraction())
//                .createCache("Flash", Flash.createFlash())
//                .createCache("FrameBufferContents", FrameBufferContents.createFrameBufferContents().translateX(500))
//                .createCache("FXAA", FXAA.createFXAA())
//                .createCache("GaussianBlurHorizontal", GaussianBlurHorizontal.createGaussianBlurHorizontal())
//                .createCache("GaussianBlurVertical", GaussianBlurVertical.createGaussianBlurVertical())
//                .createCache("Glow", Glow.createGlow())
//                .createCache("Gradation", Gradation.createGradation())
//                .createCache("Inversion", Inversion.createInversion())
//                .createCache("LensBlur", LensBlur.createLensBlur())
                .createCache("LuminanceKey", LuminanceKey.createLuminanceKey())
                .create(glRect);
    }

    @Override
    public void drawFrame() {
        glRect.draw();
    }

    @Override
    public void stopFrame() {
        glRect.cleanup();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
