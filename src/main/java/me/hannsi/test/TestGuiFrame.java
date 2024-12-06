package me.hannsi.test;

import me.hannsi.lfjg.event.events.*;
import me.hannsi.lfjg.event.system.EventHandler;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.IFrame;
import me.hannsi.lfjg.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.render.openGL.effect.effects.Texture;
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
    //GLRoundedRect gl;
    GLRect gl2;
    ResourcesLocation image;
    GLObjectCache glObjectCache;
    TextureCache textureCache;
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

        gl2 = new GLRect("test");
        gl2.setProjectionMatrix(projection.getProjMatrix());
        gl2.setResolution(resolution);
        gl2.uv(0, 1, 1, 0);
        gl2.rect(0, 0, 1920, 1080, new Color(0, 0, 0, 0));

        glObjectCache = new GLObjectCache(resolution);
        glObjectCache.createCache(gl2);

        textureCache = new TextureCache();
        image = new ResourcesLocation("texture/test/test_image_1920x1080.jpg");
        textureCache.createTexture(image);

        //gl2 = new GLRect("test2");
        //gl2.setProjectionMatrix(projection.getProjMatrix());
        //gl2.rect(250, 250, 750, 750, new Color(255, 0, 255, 255));
    }

    @Override
    public void drawFrame(long nvg) {
        //gl2.addEffectBase(new ColorCorrection(0.0f, 0.0f, 0.0f, 0.0f));
        //gl.addEffectBase(new Translate(200,200));
        //gl.addEffectBase(new Clipping2D(100, 100, 400, 400, false));
        //gl2.addEffectBase(new GaussianBlur(1.0f / 1920.0f, 1.0f / 1080.0f, 5, 3));
        gl2.addEffectBase(new Texture(textureCache, image));
        //gl.draw();
        //gl2.addEffectBase(new ColorCorrection(0f,0f,0f,0f,0.5f));

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
