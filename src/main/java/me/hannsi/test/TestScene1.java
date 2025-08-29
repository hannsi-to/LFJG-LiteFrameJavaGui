package me.hannsi.test;

import me.hannsi.lfjg.audio.SoundCache;
import me.hannsi.lfjg.audio.SoundData;
import me.hannsi.lfjg.audio.SoundListener;
import me.hannsi.lfjg.audio.SoundLoaderType;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.graphics.image.ImageCapture;
import me.hannsi.lfjg.core.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.animation.system.AnimationCache;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.renderers.polygon.GLTriangle;
import me.hannsi.lfjg.render.system.rendering.GLObjectCache;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;
import org.joml.Vector3f;
import org.lwjgl.openal.AL11;

import static me.hannsi.lfjg.core.Core.frameBufferSize;

public class TestScene1 implements IScene {
    public Scene scene;

    GLRect glGround;
    GLRect gl1;
    GLRect gl2;
    GLTriangle glTriangle;
    GLObjectCache glObjectCache;
    TextureCache textureCache;

    EffectCache gl1EffectCache;
    EffectCache glFontEffectCache;
    EffectCache glTriangleEffectCache;
    EffectCache gl1SplitObjectEffectCache;
    EffectCache glShaderEffectCache;
    EffectCache glShaderSplitObjectEffectCache;
    EffectCache glSVGEffectCache;

    AnimationCache gl1AnimationCache;

    SoundCache soundCache;

    int first;
    ImageCapture imageCapture;

    //    ThreadCache threadCache;
//    VideoFrameExtractor videoFrameExtractor;
    int textureId = -1;
    int pboId = -1;
    float rotation;

    public TestScene1() {
        this.scene = new Scene("TestScene1", this);
    }

    @Override
    public void init() {
//        videoFrameExtractor = new VideoFrameExtractor(new ResourcesLocation("video/test.mp4"));
//        Thread thread = new Thread(videoFrameExtractor::createVideoCache);
//
//        threadCache = new ThreadCache();
//        threadCache.createCache(thread);
//        threadCache.run(thread.threadId());

        imageCapture = new ImageCapture(Location.fromFile("C:/Users/hanns/idea-project/LFJG-LiteFrameJavaGui/log/png"));

        objectInit();

        glObjectCache = new GLObjectCache();
        glObjectCache.createCache(gl1);
        glObjectCache.createCache(glGround);
//        glObjectCache.createCache(glFont);
//        glObjectCache.createCache(glTriangle);
//        glObjectCache.createCache(glShader);
//        glObjectCache.createCache(glSVG);

        soundCacheInit();
        effectCacheInit();
        animationCacheInit();

//        object3DCacheRender = new Object3DCacheRender();
//
//        Object3DCache object3DCache = new Object3DCache(resolution);
//
//        Model cubeModel = ModelLoader.loadModel("cube-model", new ResourcesLocation("model/cube/cube.obj"), object3DCache.getTextureModelCache());
//        cubeEntity = new Entity("cube-entity", cubeModel.getId());
//        cubeEntity.setPosition(0, 0f, -2);
//        cubeEntity.updateModelMatrix();
//
//        object3DCache.createCache(cubeModel, cubeEntity);
//
//        Lights lights = new Lights();
//        lights.getAmbientLight().setIntensity(0.3f);
//        object3DCache.setSceneLights(lights);
//        lights.getPointLights().add(new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, -1.4f), 1.0f));
//
//        Vector3f coneDir = new Vector3f(0, 0, -1);
//        lights.getSpotLights().add(new SpotLight(new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, -1.4f), 0.0f), coneDir, 140.0f));
//
//        object3DCacheRender.setScene(object3DCache);
    }

    public void objectInit() {
        gl1 = new GLRect("test1");
        gl1.uv(0, 1, 1, 0);
        gl1.rectWH(0, 0, 1920, 1080, Color.of(0, 0, 0, 0));

        glGround = new GLRect("Ground");
        glGround.rect(0, 0, frameBufferSize.x, 1, Color.of(255, 255, 255, 255));

        int alpha = 255;
//        glFont = new GLFont("Font");
//        glFont.setFont(fontCache, font, 64);
//        glFont.font(TextFormat.SPASE_X + "{100}" + "字間を確認" + TextFormat.RESET + "字間を確認" + TextFormat.SPASE_Y + "{100}" + TextFormat.NEWLINE + TextFormat.RESET_POINT_X + TextFormat.RED + "Ka" + TextFormat.BOLD + "zu" + TextFormat.ITALIC + "bon" + "です!" + TextFormat.OBFUSCATED + "test sdaasd aaaa", 0, 200, 1f, Color.of(255, 255, 255, alpha));

        glTriangle = new GLTriangle("test3");
        glTriangle.triangle(0, 0, 500, 0, 250, 500, Color.of(255, 255, 255, alpha), Color.of(255, 255, 0, alpha), Color.of(255, 255, 255, alpha));

//        glSVG = new GLSVG("SVG1");
//        glSVG.svg(new ResourcesLocation("svg/delete.svg"), 100, 100, 5, 5);
//        glSVG.setBlendType(BlendType.NORMAL);
    }

    public void effectCacheInit() {
        textureCache = TextureCache.createTextureCache();
        Location image = Location.fromResource("texture/test/test_image_1920x1080.jpg");
        textureCache.createCache("Name1", image);

        gl1EffectCache = EffectCache.createEffectCache();
        gl1SplitObjectEffectCache = EffectCache.createEffectCache();
        glFontEffectCache = EffectCache.createEffectCache();
        glTriangleEffectCache = EffectCache.createEffectCache();
        glShaderEffectCache = EffectCache.createEffectCache();
        glShaderSplitObjectEffectCache = EffectCache.createEffectCache();
        glSVGEffectCache = EffectCache.createEffectCache();

        //        gl1EffectCache.createCache("SplitObject1", new SplitObject(4, 5, 5, 5, gl1SplitObjectEffectCache));
//        gl1EffectCache.createCache("Gradation1",new Gradation(resolution.x / 2, resolution.y / 2, (float) Math.toRadians(90), 0.2f, Gradation.ShapeMode.Rectangle, BlendType.Screen, new Color(50, 100, 200, 100), new Color(255, 255, 255, 255), 1f));
//        gl1EffectCache.createCache(new Monochrome(resolution, 1f, new Color(255, 0, 255), true), gl1);
//        gl1EffectCache.createCache(new ChromaticAberration(resolution, 0.002f, 90, 5f, ChromaticAberration.AberrationType.RedBlueB), gl1);
//        gl1EffectCache.createCache(new Inversion(resolution, false, false, false, true, false), gl1);
//        gl1EffectCache.createCache(new LensBlur(resolution, 10.0f, 3.0f), gl1);
//        gl1EffectCache.createCache(new DirectionalBlur(resolution, 10f, (float) Math.toRadians(45)), gl1);
//        gl1EffectCache.createCache(new RadialBlur(resolution, 1f, resolution.x / 2, resolution.y / 2), gl1);
//        gl1EffectCache.createCache(new FXAA(resolution, true), glFont);
//        gl1EffectCache.createCache(new BoxBlur(resolution, 10, 10), gl1);
//        gl1EffectCache.createCache(new DiagonalClipping(resolution, resolution.x / 2, resolution.y / 2, (float) Math.toRadians(0), 0.0f, true), gl1);
//        gl1EffectCache.createCache(new EdgeExtraction(resolution, 0.5f, 0.1f, true, false, new Color(255, 255, 0, 255)), gl1);
//        gl1EffectCache.createCache(new LuminanceKey(resolution, 0.5f, 0.1f, LuminanceKey.LuminanceMode.Both), gl1);
//        gl1EffectCache.createCache(new ChromaKey(resolution, new Color(251, 255, 26), 10f, 0.1f, 01f, new Color(255, 255, 26)), gl1);
//        gl1EffectCache.createCache(new Glow(resolution, 01f, 0.1f, 0.02f, false), gl1);
//        gl1EffectCache.createCache(new Flash(resolution, 0.5f, 0, 0, Flash.FlashBlendMode.ForwardSynthesis, new Color(0, 255, 0, 255)), gl1);
//        gl1EffectCache.createCache(new Bloom(resolution, 3f,0f,2f),gl1);
//        gl1EffectCache.createCache(new Pixelate(resolution, 10f), gl1);
//        gl1EffectCache.createCache(new ColorCorrection(resolution, 0.5f, 0, 0, 0), gl1);
//        gl1EffectCache.createCache(new Clipping2DRect(resolution, 0, 0, 500, 500), gl1);

//        gl1SplitObjectEffectCache.create(gl1);

        gl1.setEffectCache(gl1EffectCache);
        glTriangle.setEffectCache(glTriangleEffectCache);
    }

    public void animationCacheInit() {
//        gl1AnimationCache = AnimationCache.createAnimationCache()
//                .createCache(new Bounce(0, 500, 100f, 45));
//        gl1AnimationCache.createCache(new Trembling(0, 1000, 90, resolution.x / 2, resolution.y / 2));

//        gl1.setAnimationCache(gl1AnimationCache);
//        gl1AnimationCache.start(gl1);
    }

    public void soundCacheInit() {
        soundCache = SoundCache.createSoundCache()
                .setAttenuationModel(AL11.AL_EXPONENT_DISTANCE)
                .setListener(new SoundListener(new Vector3f(0, 0, 0)))
                .createCache(
                        "test",
                        SoundData.createSoundData()
                                .loop(false)
                                .relative(false)
                                .position(new Vector3f(0, 0, 0))
                                .createSoundPCM(SoundLoaderType.STB_VORBIS, Location.fromResource("sound/test.ogg"))
                );
    }

    @Override
    public void drawFrame() {
        soundCache.getSoundData("test").gain(0.05f);
        soundCache.playSoundData("test");

//        Rotate rotate = (Rotate) glShaderSplitObjectEffectCache.getEffectBase("Rotate1");
//        rotate.setZ(rotate.getZ() + 0.01f);

        glObjectCache.draw("test3");

//        if (first <= 10) {
//            imageCapture.saveImage("ScreenShot");
//
//            SplitFrameBuffer splitFrameBuffer = new SplitFrameBuffer(glObjectCache.getGLObject(gl1.getObjectId()).getFrameBuffer(), 5, 5);
//            splitFrameBuffer.createSmallFrameBuffers();
//            splitFrameBuffer.blitToSmallFrameBuffers();
//
//            FrameBuffer sFb;
//            int index = 0;
//            while ((sFb = splitFrameBuffer.getNextFrameBuffer()) != null) {
//                imageCapture.saveImage(sFb, index + "");
//                index++;
//            }
//
//            first++;
//        }

//        if (!videoFrameExtractor.getVideoCache().getFrames().isEmpty()) {
//            VideoCache.Frame frame = videoFrameExtractor.frame();
//
//            if (textureId == -1) {
//                textureId = glGenTextures();
//            }
//
//            if (pboId == -1) {
//                pboId = glGenBuffers();
//            }
//
//            if (frame != null) {
//                if (frame.getFrameData().getTextureId() == -1) {
//                    frame.getFrameData().setTextureId(textureId);
//                }
//                if (frame.getFrameData().getPboId() == -1) {
//                    frame.getFrameData().setPboId(pboId);
//                }
//                frame.getFrameData().createTexture();
//
//                GL11.glEnable(GL_TEXTURE_2D);
//                GL11.glBindTexture(GL_TEXTURE_2D, frame.getFrameData().getTextureId());
//                GL11.glBegin(GL11.GL_QUADS);
//                GL11.glVertex2f(0, 0);
//                GL11.glTexCoord2i(1, 1);
//
//                GL11.glVertex2f(LFJGContext.frame.getWindowWidth(), 0);
//                GL11.glTexCoord2i(1, 0);
//
//                GL11.glVertex2f(LFJGContext.frame.getWindowWidth(), LFJGContext.frame.getWindowHeight());
//                GL11.glTexCoord2i(0, 0);
//
//                GL11.glVertex2f(0, LFJGContext.frame.getWindowHeight());
//                GL11.glTexCoord2i(0, 1);
//
//                GL11.glEnd();
//                GL11.glBindTexture(GL_TEXTURE_2D, 0);
//                GL11.glDisable(GL_TEXTURE_2D);
//
//                frame.getFrameData().cleanup();
//            }
//        }

//        object3DCacheRender.render(camera);

//        rotation += 1.5;
//        if (rotation > 360) {
//            rotation = 0;
//        }
//        cubeEntity.setRotation(1, 1, 1, (float) Math.toRadians(rotation));
//        cubeEntity.updateModelMatrix();
    }

    @Override
    public void stopFrame() {
        glObjectCache.cleanup();
        textureCache.cleanup();
        soundCache.cleanup();
        imageCapture.cleanup();
//        threadCache.cleanup();

//        object3DCacheRender.cleanup();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
