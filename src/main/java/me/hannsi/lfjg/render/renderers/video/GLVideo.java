package me.hannsi.lfjg.render.renderers.video;

import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.video.VideoFrameSystem;
import me.hannsi.lfjg.utils.graphics.color.Color;
import me.hannsi.lfjg.utils.reflection.location.Location;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class GLVideo extends GLRect {
    private VideoFrameSystem videoFrameSystem;

    /**
     * Constructs a new GLRect with the specified name.
     *
     * @param name the name of the rectangle renderer
     */
    public GLVideo(String name) {
        super(name);
    }

    public void video(Location location, float x, float y, float width, float height) {
        this.videoFrameSystem = VideoFrameSystem.initVideoFrameSystem()
                .createFFmpegFrameGrabber(location)
                .createJava2DFrameConverter()
                .startVideoLoad();

        uv(0, 0, 1, 1);
        rectWH(x, y, width, height, new Color(0, 0, 0, 0));
    }

    @Override
    public void draw() {
        videoFrameSystem.drawFrame();

        if (videoFrameSystem.getTextureId() != 1) {
            GLStateCache.enable(GL_TEXTURE_2D);
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, videoFrameSystem.getTextureId());
            super.draw();
            glBindTexture(GL_TEXTURE_2D, 0);
        }
    }

    @Override
    public void cleanup() {
        videoFrameSystem.cleanup();

        super.cleanup();
    }
}

