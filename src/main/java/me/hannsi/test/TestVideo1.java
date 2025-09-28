package me.hannsi.test;

import me.hannsi.lfjg.core.event.EventHandler;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.event.events.user.KeyEvent;
import me.hannsi.lfjg.frame.event.events.user.MouseButtonEvent;
import me.hannsi.lfjg.frame.system.IFrame;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.video.GLVideo;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;

public class TestVideo1 implements IScene {
    public Scene scene;
    public Frame frame;
    public GLVideo glVideo;
    public EffectCache effectCache;

    public TestVideo1(Frame frame) {
        this.scene = new Scene("TestVideo1", this);
        this.frame = frame;

        IFrame.eventManager.register(this);
    }

    @Override
    public void init() {
        glVideo = GLVideo.createGLVideo("TestVideo1")
                .location(Location.fromResource("video/[FMV] world.execute(me); - MILI [cn4M-fH08XY].webm"))
                .x1_y1_color1_2p(0, 0)
                .width3_height3_color3_2p(frame.getWindowWidth(), frame.getWindowHeight());
    }

    @Override
    public void drawFrame() {
        glVideo.draw();
//        DebugLog.debug(getClass(), "FPS:" + frame.getFps());
    }

    @Override
    public void stopFrame() {
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @EventHandler
    public void mouseButtonEvent(MouseButtonEvent event) {
//        if(event.getAction() == GLFW_PRESS){
//            if(videoFrameSystem.isPaused()){
//                videoFrameSystem.resume();
//            }else {
//                videoFrameSystem.pause();
//            }
//        }
    }

    @EventHandler
    public void keyEvent(KeyEvent event) {
//        if(event.getAction() == GLFW_PRESS){
//            if(event.getKey() == GLFW_KEY_RIGHT){
//                videoFrameSystem.seek(videoFrameSystem.getCurrentTimestampMicros() + 5_000_000);
//            }else if(event.getKey() == GLFW_KEY_LEFT){
//                videoFrameSystem.seek(videoFrameSystem.getCurrentTimestampMicros()- 5_000_000);
//            }
//        }
    }
}
