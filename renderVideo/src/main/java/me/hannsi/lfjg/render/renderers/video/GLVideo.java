package me.hannsi.lfjg.render.renderers.video;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import me.hannsi.lfjg.render.system.video.VideoFrameSystem;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class GLVideo extends GLRect {
    private VideoFrameSystem videoFrameSystem;
    private BlendType blendType;

    /**
     * Constructs a new GLRect with the specified name.
     *
     * @param name the name of the rectangle renderer
     */
    public GLVideo(String name) {
        super(name);
    }

    public void video(Location location, float x, float y, float width, float height) {
        video(location, x, y, width, height, BlendType.NORMAL);
    }

    public void video(Location location, float x, float y, float width, float height, BlendType blendType) {
        this.videoFrameSystem = VideoFrameSystem.initVideoFrameSystem()
                .createFFmpegFrameGrabber(location)
                .createJava2DFrameConverter()
                .startVideoLoad();

        this.blendType = blendType;

        uv(0, 0, 1, 1);
        rectWH(x, y, width, height, new Color(0, 0, 0, 0));
    }

    @Override
    public void drawVAORendering() {
        videoFrameSystem.drawFrame();

        if (videoFrameSystem.getTextureId() != -1) {
            this.getShaderProgram().setUniform("objectReplaceColor", UploadUniformType.ON_CHANGE, false);
            this.getShaderProgram().setUniform("objectBlendMode", UploadUniformType.ON_CHANGE, blendType.getId());

            GLStateCache.enable(GL11.GL_TEXTURE_2D);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, videoFrameSystem.getTextureId());
            super.drawVAORendering();
        }
    }

    @Override
    public void cleanup() {
        videoFrameSystem.cleanup();

        super.cleanup();
    }

    public VideoFrameSystem getVideoFrameSystem() {
        return videoFrameSystem;
    }
}

