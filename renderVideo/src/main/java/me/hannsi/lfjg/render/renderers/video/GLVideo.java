package me.hannsi.lfjg.render.renderers.video;

import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.renderers.InstanceParameter;
import me.hannsi.lfjg.render.renderers.PaintType;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.Sprite;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.SpriteMemoryPolicy;
import me.hannsi.lfjg.render.system.video.VideoDecoder;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.hannsi.lfjg.core.Core.ASSET_MANAGER;
import static me.hannsi.lfjg.render.LFJGRenderContext.atlasPacker;
import static me.hannsi.lfjg.render.LFJGRenderContext.sparseTexture2DArray;


public class GLVideo extends GLObject<GLVideo> {
    private final Builder builder;
    private VideoDecoder videoDecoder;

    protected GLVideo(String name, Builder builder) {
        super(name, true);
        this.builder = builder;
    }

    public static RectInputStep<GLVideo> createGLVideo(String name) {
        return new Builder(name);
    }

    @Override
    public GLVideo update() {
        videoDecoder = ASSET_MANAGER.load(builder.assetName, VideoDecoder.class);
        try {
            videoDecoder.grabberStart();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String cacheName = ASSET_MANAGER.getCacheName(builder.assetName, VideoDecoder.class);
        ByteBuffer buffer = videoDecoder.nextFrame();
        if (buffer == null) {
            buffer = ByteBuffer.allocateDirect(videoDecoder.getWidth() * videoDecoder.getHeight() * 4);
            buffer.flip();
        }
        atlasPacker.addSprite(cacheName, new Sprite(videoDecoder.getWidth(), videoDecoder.getHeight(), buffer, SpriteMemoryPolicy.STREAMING));
        atlasPacker.generate();
        videoDecoder.pause();

        for (Vertex vertex : builder.vertices) {
            put(vertex).end();
        }

        if (Objects.requireNonNull(builder.paintType) == PaintType.FILL) {
            drawType(DrawType.QUADS);
        } else {
            throw new IllegalStateException("Unexpected value: " + builder.paintType);
        }

        return super.update();
    }

    @Override
    protected void rendering() {
        String cacheName = ASSET_MANAGER.getCacheName(builder.assetName, VideoDecoder.class);

        int spriteIndex = sparseTexture2DArray.commitTexture(cacheName, true)
                .updateFromAtlas()
                .getSpriteIndexFromName(cacheName);
        for (InstanceParameter instanceParameter : getObjectData().getInstanceParameters()) {
            instanceParameter.spriteIndex(spriteIndex);
        }

        super.rendering();
    }

    @Override
    public void drawFrame() {
        videoDecoder.resume();
        ByteBuffer byteBuffer = videoDecoder.nextFrame();
        String cacheName = ASSET_MANAGER.getCacheName(builder.assetName, VideoDecoder.class);

        if (byteBuffer != null) {
            sparseTexture2DArray.updateSprite(cacheName, byteBuffer);
        }

        super.drawFrame();
    }

    public interface RectInputStep<T> {
        DiagonalStep<T> from(Vertex vertex);

        Vertex2Step<T> vertex1(Vertex vertex);
    }

    public interface DiagonalStep<T> {
        VideoNameStep<T> end(Vertex vertex);
    }

    public interface Vertex2Step<T> {
        Vertex3Step<T> vertex2(Vertex vertex);
    }

    public interface Vertex3Step<T> {
        Vertex4Step<T> vertex3(Vertex vertex);
    }

    public interface Vertex4Step<T> {
        RectInputStep<T> vertex4(Vertex vertex);

        VideoNameStep<T> end(Vertex vertex);
    }

    public interface VideoNameStep<T> {
        T assetVideoName(String assetName);
    }

    public static class Builder extends AbstractGLObjectBuilder<GLVideo> implements RectInputStep<GLVideo>, DiagonalStep<GLVideo>, Vertex2Step<GLVideo>, Vertex3Step<GLVideo>, Vertex4Step<GLVideo>, VideoNameStep<GLVideo> {
        private final String name;
        private final List<Vertex> vertices;
        private String assetName;
        private Vertex lastFrom;

        private GLVideo glVideo;

        public Builder(String name) {
            this.name = name;

            this.vertices = new ArrayList<>();
        }

        @Override
        public DiagonalStep<GLVideo> from(Vertex vertex) {
            this.lastFrom = vertex;
            this.vertices.add(vertex);

            return this;
        }

        private void addDiagonalVertices(Vertex to) {
            vertices.add(lastFrom.copy().setX(to.x));
            vertices.add(to);
            vertices.add(lastFrom.copy().setY(to.y));
        }

        @Override
        public Vertex2Step<GLVideo> vertex1(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        public Vertex3Step<GLVideo> vertex2(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        public Vertex4Step<GLVideo> vertex3(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        public RectInputStep<GLVideo> vertex4(Vertex vertex) {
            this.vertices.add(vertex);

            return this;
        }

        @Override
        public VideoNameStep<GLVideo> end(Vertex vertex) {
            if (lastFrom != null && vertices.size() % 4 == 1) {
                addDiagonalVertices(vertex);
                autoAssignUVs(lastFrom.u, lastFrom.v, vertex.u, vertex.v);
            } else {
                vertices.add(vertex);
            }

            return this;
        }

        private void autoAssignUVs(float minU, float minV, float maxU, float maxV) {
            int size = vertices.size();
            if (size < 4) {
                return;
            }

            List<Vertex> quad = vertices.subList(size - 4, size);

            float minX = (float) quad.stream().mapToDouble(v -> v.x).min().orElse(0);
            float maxX = (float) quad.stream().mapToDouble(v -> v.x).max().orElse(0);
            float minY = (float) quad.stream().mapToDouble(v -> v.y).min().orElse(0);
            float maxY = (float) quad.stream().mapToDouble(v -> v.y).max().orElse(0);

            float rangeX = maxX - minX;
            float rangeY = maxY - minY;

            for (Vertex vertex : quad) {
                float u = (rangeX == 0) ? minU : minU + (vertex.x - minX) / rangeX * (maxU - minU);
                float v = (rangeY == 0) ? minV : minV + (1.0f - (vertex.y - minY) / rangeY) * (maxV - minV);
                vertex.setU(u).setV(v);
            }
        }

        @Override
        public GLVideo assetVideoName(String assetName) {
            this.assetName = assetName;

            return fill();
        }

        @Override
        protected GLVideo createOrGet() {
            if (glVideo == null) {
                return glVideo = new GLVideo(name, this);
            } else {
                return glVideo.update();
            }
        }
    }
}

