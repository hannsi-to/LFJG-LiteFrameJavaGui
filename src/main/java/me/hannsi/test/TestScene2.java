package me.hannsi.test;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.frame.setting.settings.TimeSourceSetting;
import me.hannsi.lfjg.nativeAccess.Comdlg32;
import me.hannsi.lfjg.nativeAccess.structure.OPENFILENAME;
import me.hannsi.lfjg.physic.PhysicObject;
import me.hannsi.lfjg.physic.PhysicWorld;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.openGL.system.scene.IScene;
import me.hannsi.lfjg.render.openGL.system.scene.Scene;
import me.hannsi.lfjg.utils.graphics.color.Color;
import org.joml.Vector2f;

public class TestScene2 implements IScene {
    public Scene scene;
    public Frame frame;

    PhysicWorld physicWorld;

    GLRect glGround1;
    PhysicObject physicObjectGround1;

    GLRect glRect1;
    PhysicObject physicObjectRect1;

    public TestScene2(Frame frame) {
        this.scene = new Scene("TestScene2", this);
        this.frame = frame;
    }

    @Override
    public void init() {
        glGround1 = new GLRect("GLGround1");
        glGround1.rect(0, 0, LFJGContext.resolution.x(), 5, new Color(255, 255, 255, 255));

        glRect1 = new GLRect("GLRect1");
        glRect1.rectWH(LFJGContext.resolution.x() / 2f, LFJGContext.resolution.y(), 50, 50, new Color(255, 255, 255, 255));

        physicWorld = new PhysicWorld();

        physicObjectGround1 = new PhysicObject();
        physicObjectGround1.linkGLObject(glGround1);
        physicObjectGround1.gravity = false;
        physicObjectGround1.move = false;

        physicObjectRect1 = new PhysicObject();
        physicObjectRect1.linkGLObject(glRect1);

        physicWorld.createPhysicObject(physicObjectGround1);
        physicWorld.createPhysicObject(physicObjectRect1);

        OPENFILENAME ofn = new OPENFILENAME();
        final int lenFilenameBufferInChars = 1024;
        Memory buf = new Memory((long) lenFilenameBufferInChars * 2);
        ofn.lStructSize = ofn.size();
        ofn.hwndOwner = Pointer.createConstant(frame.getWin32Window());
//        ofn.lpstrFilter = new WString("テキストファイル (*.txt)\0*.txt\0画像ファイル (*.png, *.jpg)\0*.png;*.jpg\0すべてのファイル (*.*)\0*.*\0");
        ofn.lpstrFile = buf;
        ofn.nMaxFile = lenFilenameBufferInChars;
//        ofn.lpstrTitle = new WString("ファイルを選択してください");
        boolean ret = Comdlg32.getSaveFileNameW(ofn);
        System.out.println(Comdlg32.getCommDlgExtendedError(Comdlg32.commDlgExtendedError()));
        if (ret) {
            String filename = buf.getWideString(0);
            System.out.println(filename);
        } else {
            System.out.println("キャンセルされました");
        }
    }

    @Override
    public void drawFrame() {
        physicWorld.simulation(frame.getFrameSettingValue(TimeSourceSetting.class));

        physicObjectRect1.applyForce(new Vector2f(3f, 0));
        glGround1.draw();
        glRect1.draw();
    }

    @Override
    public void stopFrame() {
        glRect1.cleanup();
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
