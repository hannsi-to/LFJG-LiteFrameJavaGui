package me.hannsi.test;

import me.hannsi.lfjg.event.events.*;
import me.hannsi.lfjg.event.system.EventHandler;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.IFrame;
import me.hannsi.lfjg.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.render.nanoVG.renderers.polygon.NanoVGPolygon;
import me.hannsi.lfjg.utils.color.Color;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.AntiAliasingType;
import me.hannsi.lfjg.utils.type.types.MonitorType;
import me.hannsi.lfjg.utils.type.types.RenderingType;
import me.hannsi.lfjg.utils.type.types.VSyncType;
import org.joml.Vector2f;

public class TestGuiFrame implements LFJGFrame {
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
    }

    @Override
    public void drawFrame(long nvg) {
        //VBO vboVertex = new VBO(4, 2);
        //VBO vboColor = new VBO(4, 4);
        //VBO vboTexture = new VBO(4, 2);

        //vboVertex.put(0, 0, 1920, 0, 1920, 1080, 0, 1080);
        //vboColor.put(255, 255, 255, 255, 255, 255, 0, 255, 255, 0, 0, 255, 255, 0, 255, 255);

        //vboTexture.put(0, 1, 1, 1, 1, 0, 0, 0);

        //VAO vaoVertex = new VAO(vboVertex);
        //VAO vaoColor = new VAO(vboColor);
        //VAO vaoTexture = new VAO(vboTexture);

        //VAORendering vaoRendering = new VAORendering(frame);

        //vaoRendering.setVertex(vaoVertex);
        //vaoRendering.setColor(null);
        //vaoRendering.setTexture(vaoTexture);

        //GL11.glPushMatrix();
        //vaoRendering.setTexturePath(new ResourcesLocation("image.png"));
        //vaoRendering.drawArrays(DrawType.QUADS);
        //GL11.glPopMatrix();

        NanoVGPolygon nanoVGPolygon = new NanoVGPolygon(nvg);
        nanoVGPolygon.setSize(new Vector2f(300, 400), new Vector2f(400, 300), new Vector2f(500, 400), new Vector2f(500, 500), new Vector2f(400, 600), new Vector2f(300, 500));
        nanoVGPolygon.setBase(true, new Color(255, 255, 255, 255));
        nanoVGPolygon.draw();
    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, 60);
        frame.setFrameSettingValue(MonitorSetting.class, MonitorType.Window);
        frame.setFrameSettingValue(VSyncSetting.class, VSyncType.VSyncOn);
        frame.setFrameSettingValue(FloatingSetting.class, false);
        frame.setFrameSettingValue(IconSetting.class, new ResourcesLocation("salad_x32.png"));
        frame.setFrameSettingValue(RenderingTypeSetting.class, RenderingType.NanoVG);
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
