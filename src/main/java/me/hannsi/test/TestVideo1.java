package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.event.events.user.KeyEvent;
import me.hannsi.lfjg.frame.event.events.user.MouseButtonEvent;
import me.hannsi.lfjg.frame.event.system.EventHandler;
import me.hannsi.lfjg.render.effect.effects.DrawObject;
import me.hannsi.lfjg.render.effect.system.EffectCache;
import me.hannsi.lfjg.render.renderers.video.GLVideo;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;
import me.hannsi.lfjg.utils.reflection.location.Location;

import static me.hannsi.lfjg.frame.frame.IFrame.eventManager;

public class TestVideo1 implements IScene {
    public Scene scene;
    public Frame frame;
    public GLVideo glVideo;
    public EffectCache effectCache;

    public TestVideo1(Frame frame) {
        this.scene = new Scene("TestVideo1", this);
        this.frame = frame;

        eventManager.register(this);
    }

    @Override
    public void init() {
        glVideo = new GLVideo("TestVideo1");
        glVideo.video(Location.fromResource("video/[FMV] world.execute(me); - MILI [cn4M-fH08XY].webm"), 0, 0, frame.getWindowWidth(), frame.getWindowHeight());

        effectCache = EffectCache.initEffectCache();
        effectCache.createCache("DrawObject1", DrawObject.createDrawObject());
        effectCache.create(glVideo);
        glVideo.setEffectCache(effectCache);
    }

    @Override
    public void drawFrame() {
        glVideo.draw();
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
