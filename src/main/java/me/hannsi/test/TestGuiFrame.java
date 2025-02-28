package me.hannsi.test;

import me.hannsi.lfjg.audio.SoundBuffer;
import me.hannsi.lfjg.audio.SoundCache;
import me.hannsi.lfjg.audio.SoundListener;
import me.hannsi.lfjg.audio.SoundSource;
import me.hannsi.lfjg.event.events.user.*;
import me.hannsi.lfjg.event.system.EventHandler;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.IFrame;
import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.render.openGL.animation.system.AnimationCache;
import me.hannsi.lfjg.render.openGL.effect.effects.*;
import me.hannsi.lfjg.render.openGL.effect.system.EffectCache;
import me.hannsi.lfjg.render.openGL.renderers.font.GLFont;
import me.hannsi.lfjg.render.openGL.renderers.model.Object3DCacheRender;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLTriangle;
import me.hannsi.lfjg.render.openGL.renderers.shader.GLShader;
import me.hannsi.lfjg.render.openGL.system.font.CFont;
import me.hannsi.lfjg.render.openGL.system.font.FontCache;
import me.hannsi.lfjg.render.openGL.system.font.UnicodeRange;
import me.hannsi.lfjg.render.openGL.system.model.model.Entity;
import me.hannsi.lfjg.render.openGL.system.rendering.GLObjectCache;
import me.hannsi.lfjg.render.openGL.system.user.Camera;
import me.hannsi.lfjg.render.openGL.system.user.MouseInfo;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.graphics.image.ImageCapture;
import me.hannsi.lfjg.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.utils.math.MathHelper;
import me.hannsi.lfjg.utils.math.Projection;
import me.hannsi.lfjg.utils.math.TextFormat;
import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.openal.AL11;

import static me.hannsi.lfjg.frame.LFJGContext.frame;
import static me.hannsi.lfjg.frame.LFJGContext.resolution;

public class TestGuiFrame implements LFJGFrame {
    GLRect gl1;
    GLFont glFont;
    GLRect gl2;
    GLTriangle glTriangle;
    GLShader glShader;
    GLObjectCache glObjectCache;
    TextureCache textureCache;

    EffectCache gl1EffectCache;
    EffectCache glFontEffectCache;
    EffectCache glTriangleEffectCache;
    EffectCache gl1SplitObjectEffectCache;
    EffectCache glShaderEffectCache;
    EffectCache glShaderSplitObjectEffectCache;

    AnimationCache gl1AnimationCache;
    FontCache fontCache;

    SoundBuffer soundBuffer;
    SoundCache soundCache;

    int first;
    ImageCapture imageCapture;

    //    ThreadCache threadCache;
//    VideoFrameExtractor videoFrameExtractor;
    int textureId = -1;
    int pboId = -1;

    MouseInfo mouseInfo;
    Camera camera;
    float rotation;

    Object3DCacheRender object3DCacheRender;
    Entity cubeEntity;

    public static void main(String[] args) {
        new TestGuiFrame().setFrame();
    }

    public void setFrame() {
        frame = new Frame(this, "TestGuiFrame");
    }

    @Override
    public void init() {
        IFrame.eventManager.register(this);

//        videoFrameExtractor = new VideoFrameExtractor(new ResourcesLocation("video/test.mp4"));
//        Thread thread = new Thread(videoFrameExtractor::createVideoCache);
//
//        threadCache = new ThreadCache();
//        threadCache.createCache(thread);
//        threadCache.run(thread.threadId());

        LFJGContext.projection = new Projection(ProjectionType.OrthographicProjection, frame.getWindowWidth(), frame.getWindowHeight());
        resolution = new Vector2f(frame.getWindowWidth() / frame.getContentScaleX(), frame.getWindowHeight() / frame.getContentScaleY());

        imageCapture = new ImageCapture(new FileLocation("C:/Users/hanns/idea-project/LFJG-LiteFrameJavaGui/log/png"));

        CFont.addUnicodeRange(UnicodeRange.HIRAGANA_START, UnicodeRange.HIRAGANA_END);
        CFont.addUnicodeRange(UnicodeRange.KATAKANA_START, UnicodeRange.KATAKANA_END);
        CFont.addUnicodeRange(UnicodeRange.CJK_IDEOGRAPHS_START, UnicodeRange.CJK_IDEOGRAPHS_END);

        objectInit();

        glObjectCache = new GLObjectCache();
//        glObjectCache.createCache(gl2);
        glObjectCache.createCache(gl1);
        glObjectCache.createCache(glFont);
        glObjectCache.createCache(glTriangle);
        glObjectCache.createCache(glShader);

        soundCacheInit();
        effectCacheInit();
        animationCacheInit();

        mouseInfo = new MouseInfo();
        camera = new Camera();

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
        fontCache = new FontCache();
        ResourcesLocation font = new ResourcesLocation("font/default.ttf");
        fontCache.createCache(font, 64);

        gl1 = new GLRect("test1");
        gl1.uv(0, 1, 1, 0);
        gl1.rectWH(0, 0, frame.getWindowWidth(), frame.getWindowHeight(), Color.of(0, 0, 0, 0));

        int alpha = 255;
        glFont = new GLFont("Font");
        glFont.setFont(fontCache, font, 64);
        glFont.font(TextFormat.SPASE_X + "{100}" + "字間を確認" + TextFormat.RESET + "字間を確認" + TextFormat.SPASE_Y + "{100}" + TextFormat.NEWLINE + TextFormat.RESET_POINT_X + TextFormat.RED + "Ka" + TextFormat.BOLD + "zu" + TextFormat.ITALIC + "bon" + "です!" + TextFormat.OBFUSCATED + "test sdaasd aaaa", 0, 200, 1f, Color.of(255, 255, 255, alpha));

        glTriangle = new GLTriangle("test3");
        glTriangle.triangle(0, 0, 500, 0, 250, 500, Color.of(255, 255, 255, alpha), Color.of(255, 255, 0, alpha), Color.of(255, 255, 255, alpha));

        glShader = new GLShader("Shader1");
        glShader.shader(new ResourcesLocation("shader/test/test.fsh"), 0, 0, frame.getWindowWidth(), frame.getWindowHeight());
    }

    public void effectCacheInit() {
        textureCache = new TextureCache();
        ResourcesLocation image = new ResourcesLocation("texture/test/test_image_3840x2160.jpg");
        textureCache.createCache(image);

        gl1EffectCache = new EffectCache();
        gl1SplitObjectEffectCache = new EffectCache();
        glFontEffectCache = new EffectCache();
        glTriangleEffectCache = new EffectCache();
        glShaderEffectCache = new EffectCache();
        glShaderSplitObjectEffectCache = new EffectCache();

        gl1SplitObjectEffectCache.createCache("Rotate1", new Rotate(0, 0, MathHelper.toRadians(0), true));

        gl1EffectCache.createCache("Texture1", new Texture(textureCache, image, BlendType.Normal));
        gl1EffectCache.createCache("DrawObject1", new DrawObject());
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

        glFontEffectCache.createCache("DrawObject1", new DrawObject());
        glFontEffectCache.createCache("Translate1", new Translate(200, 0));

        glTriangleEffectCache.createCache("DrawObject1", new DrawObject());

        glShaderSplitObjectEffectCache.createCache("Rotate1", new Rotate(0, 0, MathHelper.toRadians(0), true));

        glShaderEffectCache.createCache("DrawObject1", new DrawObject());
        glShaderEffectCache.createCache("ObjectClipping1", new ObjectClipping(glObjectCache, "test3", true));
//        glShaderEffectCache.createCache("SplitObject1", new SplitObject(20, 20, 5, 5, glShaderSplitObjectEffectCache));

        gl1EffectCache.create(gl1);
        gl1SplitObjectEffectCache.create(gl1);
        glFontEffectCache.create(glFont);
        glTriangleEffectCache.create(glTriangle);
        glShaderEffectCache.create(glShader);
        glShaderSplitObjectEffectCache.create(glShader);

        glShader.setEffectCache(glShaderEffectCache);
        gl1.setEffectCache(gl1EffectCache);
        glFont.setEffectCache(glFontEffectCache);
        glTriangle.setEffectCache(glTriangleEffectCache);
    }

    public void animationCacheInit() {
        gl1AnimationCache = new AnimationCache();

//        gl1AnimationCache.createCache(new Bounce(0, 500, 100f, 45));
//        gl1AnimationCache.createCache(new Trembling(0, 1000, 90, resolution.x / 2, resolution.y / 2));

        gl1.setAnimationCache(gl1AnimationCache);
        gl1AnimationCache.start(gl1);
    }

    public void soundCacheInit() {
        soundCache = new SoundCache();
        soundCache.setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
        soundCache.setListener(new SoundListener(new Vector3f(0, 0, 0)));

        soundBuffer = new SoundBuffer(SoundLoaderType.STBVorbis, new ResourcesLocation("sound/test.ogg"));
        SoundSource playerSoundSource = new SoundSource(false, false);
        playerSoundSource.setPosition(new Vector3f(0, 0, 0));
        playerSoundSource.setBuffer(soundBuffer.getBufferId());

        soundCache.createCache("test", soundBuffer, playerSoundSource);
    }

    @Override
    public void drawFrame() {
        soundCache.getSoundSource("test").setGain(0.05f);
        soundCache.playSoundSource("test");

//        Translate translate = (Translate) glFontEffectCache.getEffectBase("Translate1");
//        translate.setX(translate.getX() + 0.1f);

        Rotate rotate = (Rotate) glShaderSplitObjectEffectCache.getEffectBase("Rotate1");
        rotate.setZ(rotate.getZ() + 0.01f);

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

        float move = 0.01f;
        if (isKeyPressed(GLFW.GLFW_KEY_W)) {
            camera.moveForward(move);
        } else if (isKeyPressed(GLFW.GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_A)) {
            camera.moveLeft(move);
        } else if (isKeyPressed(GLFW.GLFW_KEY_D)) {
            camera.moveRight(move);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            camera.moveUp(move);
        } else if (isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            camera.moveDown(move);
        }

        float MOUSE_SENSITIVITY = 0.1f;

        if (mouseInfo.isRightButtonPressed()) {
            Vector2f displVec = mouseInfo.getDisplVec();
            camera.addRotation((float) Math.toRadians(-displVec.x * MOUSE_SENSITIVITY), (float) Math.toRadians(-displVec.y * MOUSE_SENSITIVITY));
        }

        mouseInfo.input();
    }

    public boolean isKeyPressed(int keyCode) {
        return GLFW.glfwGetKey(frame.getWindowID(), keyCode) == GLFW.GLFW_PRESS;
    }

    @Override
    public void stopFrame() {
        glObjectCache.cleanup();
        textureCache.cleanup();
        fontCache.cleanup();
        soundCache.cleanup();
        imageCapture.cleanup();
//        threadCache.cleanup();

//        object3DCacheRender.cleanup();

    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, 120);
        frame.setFrameSettingValue(MonitorSetting.class, MonitorType.Window);
        frame.setFrameSettingValue(VSyncSetting.class, VSyncType.VSyncOn);
        frame.setFrameSettingValue(FloatingSetting.class, false);
        frame.setFrameSettingValue(IconSetting.class, new ResourcesLocation("salad_x32.png"));
        frame.setFrameSettingValue(AntiAliasingSetting.class, AntiAliasingType.OFF);
        frame.setFrameSettingValue(CheckSeveritiesSetting.class, SeverityType.High);
    }

    @EventHandler
    public void keyPressEvent(KeyPressEvent event) {

    }

    @EventHandler
    public void mouseButtonCallbackEvent(MouseButtonCallbackEvent event) {
        mouseInfo.updateMouseButton(event.getButton(), event.getAction());
    }

    @EventHandler
    public void cursorEnterEvent(CursorEnterEvent event) {
        mouseInfo.updateInWindow(event.isEntered());
    }

    @EventHandler
    public void keyReleasedEvent(KeyReleasedEvent event) {

    }

    @EventHandler
    public void cursorPosEvent(CursorPosEvent event) {
        mouseInfo.updateCursorPos((float) event.getXPos(), (float) event.getYPos());
    }

    @EventHandler
    public void mouseButtonPressEvent(MouseButtonPressEvent event) {

    }

    @EventHandler
    public void mouseButtonReleasedEvent(MouseButtonReleasedEvent event) {

    }
}
