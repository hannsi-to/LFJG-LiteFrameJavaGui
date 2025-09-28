package me.hannsi.lfjg.render.renderers.video;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import me.hannsi.lfjg.render.system.video.VideoFrameSystem;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class GLVideo extends GLPolygon<GLVideo> {
    private final VideoFrameSystem videoFrameSystem;
    private final Builder builder;

    public GLVideo(String name, Builder builder) {
        super(name);

        this.builder = builder;
        this.videoFrameSystem = VideoFrameSystem.initVideoFrameSystem()
                .createFFmpegFrameGrabber(builder.location)
                .createJava2DFrameConverter()
                .startVideoLoad();

        update();
    }

    public static LocationStep createGLVideo(String name) {
        return new Builder(name);
    }

    private GLVideo update() {
        uv(0, 0, 1, 1);
        put().vertex(new Vector2f(builder.x1, builder.y1)).color(Color.of(0, 0, 0, 0)).end();
        put().vertex(new Vector2f(builder.x2, builder.y2)).color(Color.of(0, 0, 0, 0)).end();
        put().vertex(new Vector2f(builder.x3, builder.y3)).color(Color.of(0, 0, 0, 0)).end();
        put().vertex(new Vector2f(builder.x4, builder.y4)).color(Color.of(0, 0, 0, 0)).end();

        setDrawType(DrawType.QUADS);
        rendering();

        return this;
    }

    @Override
    public void drawVAORendering() {
        videoFrameSystem.drawFrame();

        if (videoFrameSystem.getTextureId() != -1) {
            this.getShaderProgram().setUniform("objectReplaceColor", UploadUniformType.ON_CHANGE, false);
            this.getShaderProgram().setUniform("objectBlendMode", UploadUniformType.ON_CHANGE, BlendType.NORMAL);

            GLStateCache.enable(GL11.GL_TEXTURE_2D);
            GLStateCache.activeTexture(GL13.GL_TEXTURE0);
            GLStateCache.bindTexture(GL11.GL_TEXTURE_2D, videoFrameSystem.getTextureId());
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

    public interface LocationStep {
        VertexData1Step location(Location location);
    }

    public interface VertexData1Step {
        VertexData2Step x1_y1_color1(float x1, float y1);

        VertexData3Step x1_y1_color1_2p(float x1, float y1);
    }

    public interface VertexData2Step {
        VertexData3Step x2_y2_color2(float x2, float y2);

        VertexData3Step width2_height2_color2(float width2, float height2);
    }

    public interface VertexData3Step {
        VertexData4Step x3_y3_color3(float x3, float y3);

        VertexData4Step width3_height3_color3(float width3, float height3);

        GLVideo x3_y3_color3_2p(float x3, float y3);

        GLVideo width3_height3_color3_2p(float width3, float height3);
    }

    public interface VertexData4Step {
        GLVideo x4_y4_color4(float x4, float y4);

        GLVideo width4_height4_color4(float width4, float height4);
    }

    public static class Builder implements LocationStep, VertexData1Step, VertexData2Step, VertexData3Step, VertexData4Step {
        private final String name;
        private Location location;
        private float x1;
        private float y1;
        private float x2;
        private float y2;
        private float x3;
        private float y3;
        private float x4;
        private float y4;

        private GLVideo glVideo;

        public Builder(String name) {
            this.name = name;
        }

        @Override
        public VertexData1Step location(Location location) {
            this.location = location;

            return this;
        }

        @Override
        public VertexData2Step x1_y1_color1(float x1, float y1) {
            this.x1 = x1;
            this.y1 = y1;

            return this;
        }

        @Override
        public VertexData3Step x1_y1_color1_2p(float x1, float y1) {
            this.x1 = x1;
            this.y1 = y1;

            return this;
        }

        @Override
        public VertexData3Step x2_y2_color2(float x2, float y2) {
            this.x2 = x2;
            this.y2 = y2;

            return this;
        }

        @Override
        public VertexData3Step width2_height2_color2(float width2, float height2) {
            this.x2 = x1 + width2;
            this.y2 = y1 + height2;

            return this;
        }

        @Override
        public VertexData4Step x3_y3_color3(float x3, float y3) {
            this.x3 = x3;
            this.y3 = y3;

            return this;
        }

        @Override
        public VertexData4Step width3_height3_color3(float width3, float height3) {
            this.x3 = x2 + width3;
            this.y3 = y2 + height3;

            return this;
        }

        @Override
        public GLVideo x3_y3_color3_2p(float x3, float y3) {
            this.x2 = x3;
            this.y2 = y1;
            this.x3 = x3;
            this.y3 = y3;
            this.x4 = x1;
            this.y4 = y3;

            return build();
        }

        @Override
        public GLVideo width3_height3_color3_2p(float width3, float height3) {
            this.x2 = x1 + width3;
            this.y2 = y1;
            this.x3 = x1 + width3;
            this.y3 = y1 + height3;
            this.x4 = x1;
            this.y4 = y1 + height3;

            return build();
        }

        @Override
        public GLVideo x4_y4_color4(float x4, float y4) {
            this.x4 = x4;
            this.y4 = y4;

            return build();
        }

        @Override
        public GLVideo width4_height4_color4(float width4, float height4) {
            this.x4 = x3 + width4;
            this.y4 = y3 + height4;

            return build();
        }

        private GLVideo build() {
            if (glVideo == null) {
                return glVideo = new GLVideo(name, this);
            } else {
                return glVideo.update();
            }
        }
    }
}

