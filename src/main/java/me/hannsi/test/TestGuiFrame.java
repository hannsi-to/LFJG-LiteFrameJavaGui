package me.hannsi.test;

import me.hannsi.lfjg.audio.SoundBuffer;
import me.hannsi.lfjg.audio.SoundCache;
import me.hannsi.lfjg.audio.SoundListener;
import me.hannsi.lfjg.audio.SoundSource;
import me.hannsi.lfjg.event.events.user.*;
import me.hannsi.lfjg.event.system.EventHandler;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.IFrame;
import me.hannsi.lfjg.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.render.openGL.effect.effects.DrawObject;
import me.hannsi.lfjg.render.openGL.effect.effects.Pixelate;
import me.hannsi.lfjg.render.openGL.effect.effects.Texture;
import me.hannsi.lfjg.render.openGL.effect.effects.Translate;
import me.hannsi.lfjg.render.openGL.effect.system.EffectCache;
import me.hannsi.lfjg.render.openGL.renderers.font.GLFont;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.system.Camera;
import me.hannsi.lfjg.render.openGL.system.font.FontCache;
import me.hannsi.lfjg.render.openGL.system.rendering.GLObjectCache;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.utils.graphics.video.VideoFrameExtractor;
import me.hannsi.lfjg.utils.math.Projection;
import me.hannsi.lfjg.utils.math.animation.Easing;
import me.hannsi.lfjg.utils.math.animation.EasingUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.toolkit.ThreadCache;
import me.hannsi.lfjg.utils.type.types.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.openal.AL11;

public class TestGuiFrame implements LFJGFrame {
    GLRect gl1;
    GLFont glFont;
    //    GLRect gl2;
    GLObjectCache glObjectCache;
    TextureCache textureCache;
    EffectCache gl1EffectCache;
    EffectCache glFontEffectCache;
    FontCache fontCache;
    Vector2f resolution;

    SoundCache soundCache;

    EasingUtil easingUtil;

    ThreadCache threadCache;

    VideoFrameExtractor videoFrameExtractor;

    private Frame frame;

    public static void main(String[] args) {
        new TestGuiFrame().setFrame();
    }

    public void setFrame() {
        frame = new Frame(this, "TestGuiFrame");
    }

    @Override
    public void init() {
        IFrame.eventManager.register(this);

        videoFrameExtractor = new VideoFrameExtractor(new ResourcesLocation("video/test.mp4"));
        Thread thread = new Thread(videoFrameExtractor::createVideoCache);

        threadCache = new ThreadCache();
        threadCache.createCache(thread);
        threadCache.run(thread.threadId());

        objectInit();

        glObjectCache = new GLObjectCache(resolution);
//        glObjectCache.createCache(gl2);
        glObjectCache.createCache(gl1);
        glObjectCache.createCache(glFont);

        effectCacheInit();
        soundCacheInit();

        this.easingUtil = new EasingUtil(Easing.easeOutBounce);
        this.easingUtil.reset();


    }

    public void objectInit() {
        Projection projection = new Projection(ProjectionType.OrthographicProjection, frame.getWindowWidth(), frame.getWindowHeight());
        resolution = new Vector2f(frame.getWindowWidth(), frame.getWindowHeight());

        Camera camera = new Camera();

        fontCache = new FontCache();
        ResourcesLocation font = new ResourcesLocation("font/default.ttf");
        fontCache.createCache(font, 64);

        gl1 = new GLRect("test1");
        gl1.setProjectionMatrix(projection.getProjMatrix());
        gl1.setViewMatrix(camera.getViewMatrix());
        gl1.setResolution(resolution);
        gl1.uv(0, 0, 1, 1);
        gl1.rectWH(0, 0, resolution.x(), resolution.y(), new Color(0, 0, 0, 0));

        glFont = new GLFont("Font");
        glFont.setProjectionMatrix(projection.getProjMatrix());
        glFont.setViewMatrix(camera.getViewMatrix());
        glFont.setResolution(resolution);
        glFont.setFont(fontCache, font, 64);
        glFont.font("Kazubonバカ", 0, 200, 5f, Color.of(255, 255, 255, 255));

//        gl2 = new GLRect("test2");
//        gl2.setProjectionMatrix(projection.getProjMatrix());
//        gl2.setResolution(resolution);
//        gl2.rect(0, 0, 1920, 1080, new Color(0, 255, 0, 255));
    }

    public void effectCacheInit() {
        textureCache = new TextureCache();
        ResourcesLocation image = new ResourcesLocation("texture/test/test_image_3840x2160.jpg");
        textureCache.createCache(image);

        gl1EffectCache = new EffectCache();
        glFontEffectCache = new EffectCache();

        gl1EffectCache.createCache(new Texture(resolution, textureCache, image), gl1);
        gl1EffectCache.createCache(new DrawObject(resolution), gl1);
//        gl1EffectCache.createCache(new Gradation(resolution, resolution.x / 2, resolution.y / 2, (float) Math.toRadians(90), 0.1f, Gradation.ShapeMode.Rectangle, BlendType.Multiply, new Color(0, 0, 0, 255), new Color(255, 255, 255, 255), 1f), gl1);
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

        glFontEffectCache.createCache(new DrawObject(resolution), glFont);
        glFontEffectCache.createCache(new Translate(resolution, 200, 0), glFont);
        glFontEffectCache.createCache(new Pixelate(resolution, 10f), glFont);

//        effectCache.createCache(new Texture(resolution, textureCache, image), gl2);
//        effectCache.createCache(new DrawObject(resolution), gl2);
//        effectCache.createCache(new GaussianBlurHorizontal(resolution, 10f), gl2);
//        effectCache.createCache(new GaussianBlurVertical(resolution, 10f), gl2);

        gl1.setEffectCache(gl1EffectCache);
        glFont.setEffectCache(glFontEffectCache);
//        gl2.setEffectCache(effectCache);
    }

    public void soundCacheInit() {
        soundCache = new SoundCache();
        soundCache.setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
        soundCache.setListener(new SoundListener(new Vector3f(0, 0, 0)));

        SoundBuffer buffer = new SoundBuffer(SoundLoaderType.STBVorbis, new ResourcesLocation("sound/test.ogg"));
        SoundSource playerSoundSource = new SoundSource(false, false);
        playerSoundSource.setPosition(new Vector3f(0, 0, 0));
        playerSoundSource.setBuffer(buffer.getBufferId());

        soundCache.createCache("test", buffer, playerSoundSource);
    }

    @Override
    public void drawFrame(long nvg) {
        float value = easingUtil.get(1000);
        if (easingUtil.done(value)) {
            easingUtil.setReverse(!easingUtil.isReverse());
            easingUtil.reset();
        }

//        Translate translate = (Translate) glObjectCache.getGLObject(glFont.getObjectId()).getEffectBase(1);
//        translate.setX(value * resolution.x());

//        soundCache.getSoundSource("test").setGain(1 * value);
//        soundCache.playSoundSource("test");

        glObjectCache.draw();

//        FrameData frameData = videoFrameExtractor.getFrameRender();
//        if (frameData == null){
//            return;
//        }
//        frameData.getVideoFrameData().createTexture();
//        int id = frameData.getVideoFrameData().getTextureId();
//
//        if(id == 0){
//            return;
//        }

//        GL11.glBindTexture(GL11.GL_TEXTURE_2D,id);
//        GL11.glBegin(GL11.GL_QUADS);
//        GL11.glVertex2f(0,0);
//        GL11.glTexCoord2i(0,0);
//        GL11.glVertex2f(1920,0);
//        GL11.glTexCoord2i(1,0);
//        GL11.glVertex2f(1920,1080);
//        GL11.glTexCoord2i(1,1);
//        GL11.glVertex2f(0,1080);
//        GL11.glTexCoord2i(0,1);
//        GL11.glEnd();
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
    }

    @Override
    public void stopFrame() {
        glObjectCache.cleanup();
        textureCache.cleanup();
        fontCache.cleanup();
        soundCache.cleanup();
        threadCache.cleanup();
    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, 240);
        frame.setFrameSettingValue(MonitorSetting.class, MonitorType.Window);
        frame.setFrameSettingValue(VSyncSetting.class, VSyncType.VSyncOn);
        frame.setFrameSettingValue(FloatingSetting.class, false);
        frame.setFrameSettingValue(IconSetting.class, new ResourcesLocation("salad_x32.png"));
        frame.setFrameSettingValue(AntiAliasingSetting.class, AntiAliasingType.OFF);
    }

    @EventHandler
    public void keyPressEvent(KeyPressEvent event) {

    }

    @EventHandler
    public void keyReleasedEvent(KeyReleasedEvent event) {

    }

    @EventHandler
    public void cursorPosEvent(CursorPosEvent event) {

    }

    @EventHandler
    public void mouseButtonPressEvent(MouseButtonPressEvent event) {

    }

    @EventHandler
    public void mouseButtonReleasedEvent(MouseButtonReleasedEvent event) {

    }
}
