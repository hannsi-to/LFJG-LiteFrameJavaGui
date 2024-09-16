package me.hannsi.test;

import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.LFJGFrame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.render.renderer.bufferObject.VAO;
import me.hannsi.lfjg.render.renderer.bufferObject.VBO;
import me.hannsi.lfjg.render.rendering.VAORendering;
import me.hannsi.lfjg.util.ImageLoader;
import me.hannsi.lfjg.util.ResourcesLocation;
import me.hannsi.lfjg.util.TextureLoader;
import me.hannsi.lfjg.util.type.types.AntiAliasingType;
import me.hannsi.lfjg.util.type.types.DrawType;
import me.hannsi.lfjg.util.type.types.MonitorType;
import me.hannsi.lfjg.util.type.types.VSyncType;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TestGuiFrame implements LFJGFrame {
    private Frame frame;

    public static void main(String[] args) {
        new TestGuiFrame().setFrame();
    }

    public void setFrame(){
        frame = new Frame(this);
    }

    @Override
    public void init() {
    }

    @Override
    public void drawFrame() {
        VBO vboVertex = new VBO(4, 2);
        vboVertex.put(
                0, 0,
                500,0,
                500,500,
                0,500
        );

        VBO vboColor = new VBO(4, 4);
        vboColor.put(
                255, 255, 255, 255,
                255, 255, 0, 255,
                255, 0, 0, 255,
                255, 0, 255, 255
        );

        VBO vboTexture = new VBO(4,2);
        vboTexture.put(
                0,0,
                1,0,
                1,1,
                0,1
        );

        VAO vaoVertex = new VAO(vboVertex);
        VAO vaoColor = new VAO(vboColor);
        VAO vaoTexture = new VAO(vboTexture);

        VAORendering vaoRendering = new VAORendering(vaoVertex,null,vaoTexture);

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
        frame.setFrameSettingValue(RefreshRateSetting.class,60);
        frame.setFrameSettingValue(MonitorSetting.class, MonitorType.Window);
        frame.setFrameSettingValue(VSyncSetting.class, VSyncType.VSyncOn);
        frame.setFrameSettingValue(TransparentFramebufferSetting.class,true);
        frame.setFrameSettingValue(IconSetting.class,new ResourcesLocation("salad_x32.png"));
    }
}
