package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.frame.LFJGContext;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;
import me.hannsi.lfjg.utils.graphics.video.VideoCache;
import me.hannsi.lfjg.utils.graphics.video.VideoFrameExtractor;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;

import static org.lwjgl.opengl.GL11.*;

public class TestVideo1 implements IScene {
    public Scene scene;
    public Frame frame;
    VideoFrameExtractor videoFrameExtractor;
    int textureId;

    public TestVideo1(Frame frame) {
        this.scene = new Scene("TestVideo1", this);
        this.frame = frame;
    }

    @Override
    public void init() {
        videoFrameExtractor = new VideoFrameExtractor(new ResourcesLocation("video/test.mp4"));
        videoFrameExtractor.createVideoCache();
    }

    @Override
    public void drawFrame() {
        if (!videoFrameExtractor.getVideoCache().getFrames().isEmpty()) {
            VideoCache.Frame frame = videoFrameExtractor.frame();

            if (textureId == -1) {
                textureId = glGenTextures();
            }

            if (frame != null) {
                if (frame.getFrameData().getTextureId() == -1) {
                    frame.getFrameData().setTextureId(textureId);
                }
                frame.getFrameData().createTexture();

                glEnable(GL_TEXTURE_2D);
                glBindTexture(GL_TEXTURE_2D, frame.getFrameData().getTextureId());
                glBegin(GL_QUADS);
                glVertex2f(0, 0);
                glTexCoord2i(1, 1);

                glVertex2f(LFJGContext.windowSize.x(), 0);
                glTexCoord2i(1, 0);

                glVertex2f(LFJGContext.windowSize.x(), LFJGContext.windowSize.y());
                glTexCoord2i(0, 0);

                glVertex2f(0, LFJGContext.windowSize.y());
                glTexCoord2i(0, 1);

                glEnd();
                glBindTexture(GL_TEXTURE_2D, 0);
                glDisable(GL_TEXTURE_2D);

                frame.getFrameData().cleanup();
            }
        }
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
