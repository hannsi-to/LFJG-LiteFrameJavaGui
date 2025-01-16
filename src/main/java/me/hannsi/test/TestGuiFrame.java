package me.hannsi.test;

import me.hannsi.lfjg.event.events.user.*;
import me.hannsi.lfjg.event.system.EventHandler;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.IFrame;
import me.hannsi.lfjg.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.render.openGL.effect.effects.DrawObject;
import me.hannsi.lfjg.render.openGL.effect.effects.RadialBlur;
import me.hannsi.lfjg.render.openGL.effect.effects.Texture;
import me.hannsi.lfjg.render.openGL.effect.system.EffectCache;
import me.hannsi.lfjg.render.openGL.renderers.font.GLFont;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.system.font.FontCache;
import me.hannsi.lfjg.render.openGL.system.rendering.GLObjectCache;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.utils.math.Projection;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.AntiAliasingType;
import me.hannsi.lfjg.utils.type.types.MonitorType;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import me.hannsi.lfjg.utils.type.types.VSyncType;
import org.joml.Vector2f;

public class TestGuiFrame implements LFJGFrame {
    GLRect gl1;
    GLFont glFont;
    //  GLRect gl2;
    GLObjectCache glObjectCache;
    TextureCache textureCache;
    EffectCache effectCache;
    FontCache fontCache;
    Vector2f resolution;

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

        Projection projection = new Projection(ProjectionType.OrthographicProjection, frame.getWindowWidth(), frame.getWindowHeight());
        resolution = new Vector2f(frame.getWindowWidth(), frame.getWindowHeight());

        fontCache = new FontCache();
        ResourcesLocation font = new ResourcesLocation("font/default.ttf");
        fontCache.createCache(font, 64);

        gl1 = new GLRect("test1");
        gl1.setProjectionMatrix(projection.getProjMatrix());
        gl1.setResolution(resolution);
        gl1.uv(0, 0, 1, 1);
        gl1.rectWH(0, 0, resolution.x(), resolution.y(), new Color(0, 0, 0, 0));

        glFont = new GLFont("Font");
        glFont.setProjectionMatrix(projection.getProjMatrix());
        glFont.setResolution(resolution);
        glFont.setFont(fontCache, font, 64);
        glFont.font("Kazubonバカ", 200, 200, 2f, Color.of(255, 255, 255, 255));

//        gl2 = new GLRect("test2");
//        gl2.setProjectionMatrix(projection.getProjMatrix());
//        gl2.setResolution(resolution);
//        gl2.rect(0, 0, 1920, 1080, new Color(0, 255, 0, 255));

        textureCache = new TextureCache();
        ResourcesLocation image = new ResourcesLocation("texture/test/test_image_3840x2160.jpg");
        textureCache.createCache(image);

        effectCache = new EffectCache();

        effectCache.createCache(new Texture(resolution, textureCache, image), gl1);
        effectCache.createCache(new DrawObject(resolution), gl1);
        effectCache.createCache(new RadialBlur(resolution, 1f, resolution.x / 2, resolution.y / 2), gl1);
//        effectCache.createCache(new FXAA(resolution, true), glFont);
//        effectCache.createCache(new BoxBlur(resolution, 10, 10), gl1);
//        effectCache.createCache(new DiagonalClipping(resolution, resolution.x / 2, resolution.y / 2, (float) Math.toRadians(0), 1.0f, true), gl1);
//        effectCache.createCache(new EdgeExtraction(resolution, 0.5f, 0.1f, true, false, new Color(255, 255, 0, 255)), gl1);
//        effectCache.createCache(new LuminanceKey(resolution, 0.5f, 0.1f, LuminanceKey.LuminanceMode.Both), gl1);
//        effectCache.createCache(new ChromaKey(resolution, new Color(251, 255, 26), 10f, 0.1f, 01f, new Color(255, 255, 26)), gl1);
//        effectCache.createCache(new Glow(resolution, 01f, 0.1f, 0.02f, false), gl1);
//        effectCache.createCache(new Flash(resolution, 0.5f, 0, 0, Flash.FlashBlendMode.ForwardSynthesis, new Color(0, 255, 0, 255)), gl1);
//        effectCache.createCache(new Bloom(resolution, 3f,0f,2f),gl1);
//        effectCache.createCache(new Pixelate(resolution, 0f), gl1);
//        effectCache.createCache(new ColorCorrection(resolution, 0.5f, 0, 0, 0), gl1);
//        effectCache.createCache(new Clipping2DRect(resolution, 0, 0, 500, 500), gl1);

        effectCache.createCache(new DrawObject(resolution), glFont);
//        effectCache.createCache(new FXAA(resolution,true), glFont);

//        effectCache.createCache(new Texture(resolution, textureCache, image), gl2);
//        effectCache.createCache(new DrawObject(resolution), gl2);
//        effectCache.createCache(new GaussianBlurHorizontal(resolution, 10f), gl2);
//        effectCache.createCache(new GaussianBlurVertical(resolution, 10f), gl2);

        gl1.setEffectCache(effectCache);
        glFont.setEffectCache(effectCache);
//        gl2.setEffectCache(effectCache);

        glObjectCache = new GLObjectCache(resolution);
//        glObjectCache.createCache(gl2);
        glObjectCache.createCache(gl1);
        glObjectCache.createCache(glFont);
    }

    @Override
    public void drawFrame(long nvg) {
        glObjectCache.draw();
    }

    @Override
    public void stopFrame() {
        glObjectCache.cleanup();
    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, 60);
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
