package me.hannsi.test;

import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.jcef.JCef;
import me.hannsi.lfjg.jcef.handler.RenderHandler;
import me.hannsi.lfjg.render.openGL.system.scene.IScene;
import me.hannsi.lfjg.render.openGL.system.scene.Scene;

public class TestScene3 implements IScene {
    public Scene scene;
    public Frame frame;

    public JCef jCef;
    public RenderHandler cefRenderHandler;

    int textureId;

    public TestScene3(Frame frame) {
        this.scene = new Scene("TestScene3", this);
        this.frame = frame;
    }

    @Override
    public void init() {
//        if (!CefApp.startup(args)) {
//            System.out.println("Startup initialization failed!");
//            return;
//        }
//
//        CefSettings settings = new CefSettings();
//        settings.windowless_rendering_enabled = true;
//        CefApp cefApp = CefApp.getInstance(settings);
//        cefRenderHandler = new RenderHandler((int) windowSize.x(), (int) windowSize.y());
//        CefClient client = cefApp.createLFJGCefClient(cefRenderHandler);
//        client.addMessageRouter(CefMessageRouter.create());
//        CefBrowser browser = client.createBrowser("https://google.com", true, false);
//        browser.createImmediately();

//        jCef = JCef.initJCef(args, (int) windowSize.x(), (int) windowSize.y(),true,false,true,Color.of(255,255,255,255),cefRenderHandler);

//        textureId = glGenBuffers();
//        glBindTexture(GL_TEXTURE_2D, textureId);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, cefRenderHandler.getViewWidth(), cefRenderHandler.getViewHeight(), 0, GL_BGRA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
//        glBindTexture(GL_TEXTURE_2D, 0);

//        glEnable(GL_BLEND);
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void drawFrame() {
//        ByteBuffer frameData = cefRenderHandler.getFrame();
//        if (frameData != null) {
//            ImageCapture imageCapture = new ImageCapture(new FileLocation("C:\\Users\\hanns\\idea-project\\LFJG-LiteFrameJavaGui\\log\\png"));
//            imageCapture.setColorFormatType(ColorFormatType.BGRA);
//            imageCapture.setFlip(false);
//            imageCapture.saveImage(frameData, "web");
//
//            glEnable(GL_TEXTURE_2D);
//            glBindTexture(GL_TEXTURE_2D, textureId);
//            glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, cefRenderHandler.getViewWidth(), cefRenderHandler.getViewHeight(), GL_BGRA, GL_UNSIGNED_BYTE, frameData);
//            glColor4f(1f,1f,1f,1f);
//            glBegin(GL_QUADS);
//            glVertex2f(0, 0);
//            glTexCoord2i(1, 1);
//            glVertex2f(cefRenderHandler.getViewWidth(), 0);
//            glTexCoord2i(1, 0);
//            glVertex2f(cefRenderHandler.getViewWidth(), cefRenderHandler.getViewHeight());
//            glTexCoord2i(0, 0);
//            glVertex2f(0, cefRenderHandler.getViewHeight());
//            glTexCoord2i(0, 1);
//            glEnd();
//            glBindTexture(GL_TEXTURE_2D, 0);
//            glDisable(GL_TEXTURE_2D);
//        }
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
