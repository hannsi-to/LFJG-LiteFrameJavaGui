package me.hannsi.test;

import me.hannsi.lfjg.event.events.*;
import me.hannsi.lfjg.event.system.EventHandler;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.IFrame;
import me.hannsi.lfjg.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.render.openGL.effect.effects.DrawObject;
import me.hannsi.lfjg.render.openGL.effect.effects.Pixelate;
import me.hannsi.lfjg.render.openGL.effect.effects.Texture;
import me.hannsi.lfjg.render.openGL.effect.system.EffectCache;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.system.GLObjectCache;
import me.hannsi.lfjg.render.openGL.system.Projection;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.image.TextureCache;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.AntiAliasingType;
import me.hannsi.lfjg.utils.type.types.MonitorType;
import me.hannsi.lfjg.utils.type.types.ProjectionType;
import me.hannsi.lfjg.utils.type.types.VSyncType;
import org.joml.Vector2f;

public class TestGuiFrame implements LFJGFrame {
    GLRect gl1;
    GLRect gl2;
    ResourcesLocation image;
    GLObjectCache glObjectCache;
    TextureCache textureCache;
    EffectCache effectCache;
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

        gl1 = new GLRect("test1");
        gl1.setProjectionMatrix(projection.getProjMatrix());
        gl1.setResolution(resolution);
        gl1.uv(0, 0, 1, 1);
        gl1.rect(0, 0, 1920, 1080, new Color(0, 0, 0, 0));

        gl2 = new GLRect("test2");
        gl2.setProjectionMatrix(projection.getProjMatrix());
        gl2.setResolution(resolution);
        gl2.uv(0, 0, 1, 1);
        gl2.rect(0, 0, 1920, 1080, new Color(0, 0, 0, 0));

        textureCache = new TextureCache();
        image = new ResourcesLocation("texture/test/test_image_3840x2160.jpg");
        textureCache.createTexture(image);

        effectCache = new EffectCache();

        effectCache.createCache(new Texture(resolution, textureCache, image), gl1);
        effectCache.createCache(new DrawObject(resolution), gl1);
        effectCache.createCache(new Pixelate(resolution, 1f), gl1);
//        effectCache.createCache(new ColorCorrection(resolution, 0.5f, 0, 0, 0), gl1);
//        effectCache.createCache(new Clipping2DRect(resolution, 0, 0, 500, 500), gl1);

//        effectCache.createCache(new Texture(resolution, textureCache, image), gl2);
//        effectCache.createCache(new DrawObject(resolution), gl2);
//        effectCache.createCache(new GaussianBlurHorizontal(resolution, 10f), gl2);
//        effectCache.createCache(new GaussianBlurVertical(resolution, 10f), gl2);

        gl1.setEffectCache(effectCache);
//        gl2.setEffectCache(effectCache);

        glObjectCache = new GLObjectCache(resolution);
//        glObjectCache.createCache(gl2);
        glObjectCache.createCache(gl1);
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
        frame.setFrameSettingValue(AntiAliasingSetting.class, AntiAliasingType.MSAA);
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
