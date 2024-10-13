package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.IconSetting;
import me.hannsi.lfjg.frame.setting.settings.MonitorSetting;
import me.hannsi.lfjg.frame.setting.settings.RefreshRateSetting;
import me.hannsi.lfjg.render.renderer.bufferObject.VAO;
import me.hannsi.lfjg.render.renderer.bufferObject.VBO;
import me.hannsi.lfjg.render.rendering.VAORendering;
import me.hannsi.lfjg.util.ResourcesLocation;
import me.hannsi.lfjg.util.type.types.DrawType;
import me.hannsi.lfjg.util.type.types.MonitorType;
import org.lwjgl.opengl.GL11;

public class TestGuiFrame implements LFJGFrame {
    private Frame frame;

    public static void main(String[] args) {
        new TestGuiFrame().setFrame();
    }

    public void setFrame() {
        frame = new Frame(this);
    }

    @Override
    public void init() {

    }

    @Override
    public void drawFrame() {
        VBO vboVertex = new VBO(4, 2);
        VBO vboColor = new VBO(4, 4);
        VBO vboTexture = new VBO(4, 2);

        vboVertex.put(0, 0, 500, 0, 500, 500, 0, 500);
        vboColor.put(255, 255, 255, 255, 255, 255, 0, 255, 255, 0, 0, 255, 255, 0, 255, 255);

        vboTexture.put(0, 1, 1, 1, 1, 0, 0, 0);

        VAO vaoVertex = new VAO(vboVertex);
        VAO vaoColor = new VAO(vboColor);
        VAO vaoTexture = new VAO(vboTexture);

        VAORendering vaoRendering = new VAORendering(frame);

        vaoRendering.setVertex(vaoVertex);
        vaoRendering.setColor(vaoColor);
        vaoRendering.setTexture(vaoTexture);

        GL11.glPushMatrix();
        vaoRendering.setTexturePath(new ResourcesLocation("image.png"));
        vaoRendering.drawArrays(DrawType.QUADS);
        GL11.glPopMatrix();
    }

    @Override
    public void keyPress(int key, int scancode, int mods, long window) {
    }

    @Override
    public void keyReleased(int key, int scancode, int mods, long window) {

    }

    @Override
    public void cursorPos(double xpos, double ypos, long window) {

    }

    @Override
    public void mouseButtonPress(int button, int mods, long window) {

    }

    @Override
    public void mouseButtonReleased(int button, int mods, long window) {

    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, 60);
        frame.setFrameSettingValue(MonitorSetting.class, MonitorType.Window);
        //frame.setFrameSettingValue(VSyncSetting.class, VSyncType.VSyncOn);
        //frame.setFrameSettingValue(TransparentFramebufferSetting.class, true);
        frame.setFrameSettingValue(IconSetting.class, new ResourcesLocation("salad_x32.png"));
    }
}
