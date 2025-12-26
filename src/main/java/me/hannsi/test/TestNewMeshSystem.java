package me.hannsi.test;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.graphics.ResolutionType;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.core.utils.time.Timer;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.Transform;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.system.mesh.InstanceData;
import me.hannsi.lfjg.render.system.mesh.TestMesh;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.rendering.texture.SparseTexture2DArray;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.AtlasPacker;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.Sprite;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.SpriteMemoryPolicy;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.frame.LFJGFrameContext.frame;
import static me.hannsi.lfjg.render.LFJGRenderContext.*;
import static me.hannsi.lfjg.render.system.mesh.InstanceData.NO_ATTACH_TEXTURE;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

public class TestNewMeshSystem implements LFJGFrame {
    Timer timer = new Timer();
    List<IntRef> objectIds = new ArrayList<>();
    SparseTexture2DArray sparseTexture2DArray;
    GLRect glRect;
    InstanceData instanceData;
    private BlendType blendType;

    public static void main(String[] args) {
        new TestNewMeshSystem().setFrame();
    }

    @Override
    public void init() {
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());

//        glRect = GLRect.createGLRect("GLRect1")
//                .x1_y1_color1_2p(0, 0, Color.RED)
//                .width3_height3_color3_2p(frameBufferSize.x, frameBufferSize.y, Color.GREEN)
//                .fill()
//                .update();

        AtlasPacker atlas = new AtlasPacker(1024, 1024, 1, 0, 0, 0);
        for (int i = 0; i < 100; i++) {
//            int w = 16 + (int) (MathHelper.random() * 100);
//            int h = 16 + (int) (MathHelper.random() * 100);
            int w = 64;
            int h = 64;
            atlas.addSprite("Code: " + i, Sprite.createRandomColor(w, h, false, SpriteMemoryPolicy.STREAMING).setCommited(false));
        }
        atlas.generate();

        sparseTexture2DArray = new SparseTexture2DArray(atlas)
                .commitTexture("Code: 0", true)
                .commitTexture("Code: 1", false)
                .commitTexture("Code: 2", true)
                .commitTexture("Code: 3", true)
                .updateFromAtlas();

        int instanceCount = 4;
        float objectSize = ResolutionType.WQHD.getHeight();
        Transform[] matrix4fs = new Transform[instanceCount];
        Color[] colors = new Color[instanceCount];
        for (int i = 0; i < instanceCount; i++) {
            float scale = ((objectSize / instanceCount) * (instanceCount - i)) / objectSize;
            int layer = NO_ATTACH_TEXTURE;
            if (sparseTexture2DArray.commitedTexture("Code: " + i)) {
                layer = i;
            }
            matrix4fs[i] = new Transform(layer).scale(scale, scale, 1);
            colors[i] = new Color(0, 0, 0, 0);
        }

        MESH.addObject(
                TestMesh.Builder.createBuilder()
                        .drawType(DrawType.QUADS)
                        .pointSize(30)
                        .lineWidth(10f)
                        .vertices(
                                new Vertex(0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0),
                                new Vertex(objectSize, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0),
                                new Vertex(objectSize, objectSize, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
                                new Vertex(0, objectSize, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0)
                        )
                        .instanceData(instanceData = new InstanceData(instanceCount, matrix4fs, colors))
        );

//        MESH.addObject(
//                TestMesh.Builder.createBuilder()
//                        .drawType(DrawType.QUADS)
//                        .pointSize(30)
//                        .lineWidth(10f)
//                        .vertices(
//                                new Vertex(0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0),
//                                new Vertex(300, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0),
//                                new Vertex(300, 300, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
//                                new Vertex(0, 300, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0)
//                        )
//        );
//
//        MESH.addObject(
//                TestMesh.Builder.createBuilder()
//                        .drawType(DrawType.QUADS)
//                        .pointSize(30)
//                        .lineWidth(10f)
//                        .vertices(
//                                new Vertex(0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0),
//                                new Vertex(200, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0),
//                                new Vertex(200, 200, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
//                                new Vertex(0, 200, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0)
//                        )
//        );
//
//        MESH.addObject(
//                TestMesh.Builder.createBuilder()
//                        .drawType(DrawType.QUADS)
//                        .pointSize(30)
//                        .lineWidth(10f)
//                        .vertices(
//                                new Vertex(0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0),
//                                new Vertex(100, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0),
//                                new Vertex(100, 100, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0),
//                                new Vertex(0, 100, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0)
//                        )
//        );

//        int numObjects = 100;
//        int numVerticesPerObject = 100;
//        float minX = 0;
//        float maxX = ResolutionType.WQHD.getWidth();
//        float minY = 0;
//        float maxY = ResolutionType.WQHD.getHeight();
//        Random random = new Random();
//
//        for (int i = 0; i < numObjects; i++) {
//            IntRef id = new IntRef();
//            ProjectionType[] projectionTypes = new ProjectionType[numVerticesPerObject];
//            Matrix4f[] instanceModels = new Matrix4f[numVerticesPerObject];
//            Color[] instanceColors = new Color[numVerticesPerObject];
//
//            for (int j = 0; j < numVerticesPerObject; j++) {
//                float x = minX + random.nextFloat() * (maxX - minX);
//                float y = minY + random.nextFloat() * (maxY - minY);
//
//                float r = random.nextFloat();
//                float g = random.nextFloat();
//                float b = random.nextFloat();
//
//                projectionTypes[j] = ProjectionType.ORTHOGRAPHIC_PROJECTION;
//                instanceModels[j] = new Matrix4f().translate(x, y, 0);
//                instanceColors[j] = new Color(r, g, b, 0.5f);
//            }
//
//            MESH.addObject(
//                    TestMesh.Builder.createBuilder()
//                            .objectIdPointer(id)
//                            .drawType(DrawType.POINTS)
//                            .pointSize(30f)
//                            .lineWidth(-1f)
//                            .pointType(PointType.ROUND)
//                            .jointType(JointType.NONE)
//                            .vertices(new Vertex(0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0))
//                            .instanceData(new InstanceData(numVerticesPerObject, projectionTypes, instanceModels, instanceColors))
//            );
//
//            objectIds.add(id);
//        }

//        for (int i = 0; i < numObjects; i++) {
//            float x = minX + random.nextFloat() * (maxX - minX);
//            float y = minY + random.nextFloat() * (maxY - minY);
//
//            List<Vertex> vertices = new ArrayList<>();
//
//            for (int j = 0; j < numVerticesPerStrip; j++) {
//                float r = random.nextFloat();
//                float g = random.nextFloat();
//                float b = random.nextFloat();
//
//                Vertex v = new Vertex(x, y, 0, r, g, b, 0.5f, 0, 0, 0, 0, 1);
//                vertices.add(v);
//
//                float angle = (float) (random.nextFloat() * Math.PI * 2.0);
//                float step = minSize + random.nextFloat() * (maxSize - minSize);
//                x += (float) (Math.cos(angle) * step);
//                y += (float) (Math.sin(angle) * step);
//            }
//
//            testMesh.addObject(
//                    ProjectionType.ORTHOGRAPHIC_PROJECTION,
//                    DrawType.LINE_LOOP,
//                    30,
//                    JointType.MITER,
//                    vertices.toArray(new Vertex[0])
//            );
//        }

//        Vertex vertex1 = new Vertex(100, 100, 0, 1, 1, 1, 0.5f, 0, 0, 0, 0, 1);
//        Vertex vertex2 = new Vertex(300, 500, 0, 1, 1, 1, 0.5f, 0, 0, 0, 0, 1);
//        Vertex vertex3 = new Vertex(600, 100, 0, 1, 1, 1, 0.5f, 0, 0, 0, 0, 1);
//        Vertex vertex4 = new Vertex(1500, 500, 0, 1, 1, 1, 0.5f, 0, 0, 0, 0, 1);
//
//        testMesh.addObject(
//                ProjectionType.ORTHOGRAPHIC_PROJECTION,
//                DrawType.LINE_LOOP,
//                50f,
//                JointType.BEVEL,
//                vertex1, vertex2, vertex3, vertex4
//        );

//        for (int i = 0; i < numObjects; i++) {
//            float x1 = minX + random.nextFloat() * (maxX - minX);
//            float y1 = minY + random.nextFloat() * (maxY - minY);
//
//            float angle = (float) (random.nextFloat() * Math.PI * 2.0);
//            float length = minSize + random.nextFloat() * (maxSize - minSize);
//
//            float x2 = x1 + (float) Math.cos(angle) * length;
//            float y2 = y1 + (float) Math.sin(angle) * length;
//
//            float r = random.nextFloat();
//            float g = random.nextFloat();
//            float b = random.nextFloat();
//
//            Vertex v1 = new Vertex(x1, y1, 0, r, g, b, 1, 0, 0, 0, 0, 1);
//            Vertex v2 = new Vertex(x2, y2, 0, r, g, b, 1, 0, 0, 0, 0, 1);
//
//            testMesh.addObject(
//                    ProjectionType.ORTHOGRAPHIC_PROJECTION,
//                    DrawType.LINES,
//                    1f,
//                    JointType.MITER,
//                    v1, v2
//            );
//        }

//        for (int i = 0; i < numObjects; i++) {
//            float centerX = minX + random.nextFloat() * (maxX - minX);
//            float centerY = minY + random.nextFloat() * (maxY - minY);
//            float size = minSize + random.nextFloat() * (maxSize - minSize);
//
//            float r = random.nextFloat();
//            float g = random.nextFloat();
//            float b = random.nextFloat();
//
//            Vertex v1 = new Vertex(
//                    centerX - size / 2, centerY - size / 2, 0f,
//                    r, g, b, 1f,
//                    0f, 0f,
//                    0f, 0f, 1f
//            );
//            Vertex v2 = new Vertex(
//                    centerX + size / 2, centerY - size / 2, 0f,
//                    r, g, b, 1f,
//                    1f, 0f,
//                    0f, 0f, 1f
//            );
//            Vertex v3 = new Vertex(
//                    centerX, centerY + size / 2, 0f,
//                    r, g, b, 1f,
//                    0.5f, 1f,
//                    0f, 0f, 1f
//            );
//
//            testMesh.addObject(
//                    ProjectionType.ORTHOGRAPHIC_PROJECTION,
//                    DrawType.TRIANGLES,
//                    1f,
//                    JointType.MITER,
//                    v1, v2, v3
//            );
//        }

        MESH.initBufferObject();

        blendType = BlendType.NORMAL;

        PERSISTENT_MAPPED_SSBO.bindBufferRange();
    }

    @Override
    public void drawFrame() {
        update();

        SHADER_PROGRAM.bind();

        GL_STATE_CACHE.blendFunc(blendType.getSfactor(), blendType.getDfactor());
        GL_STATE_CACHE.setBlendEquation(blendType.getEquation());
        GL_STATE_CACHE.enable(GL_BLEND);
        GL_STATE_CACHE.disable(GL_DEPTH_TEST);
        GL_STATE_CACHE.depthMask(false);

        SHADER_PROGRAM.setUniform("uTextArray", UploadUniformType.ONCE, 0);

        for (Transform instanceModel : instanceData.getTransforms()) {
//            instanceModel.translate(1, 0, 0);
        }

//        int w = 64;
//        int h = 64;
//        ByteBuffer newData = BufferUtils.createByteBuffer(w * h * 4);
//
//        byte r = (byte) (Math.random() * 255);
//        byte g = (byte) (Math.random() * 255);
//        byte b = (byte) (Math.random() * 255);
//
//        for (int i = 0; i < w * h; i++) {
//            newData.put(r).put(g).put(b).put((byte) 255);
//        }
//        newData.flip();
//
//        sparseTexture2DArray.updateSprite("Code: 0", newData);
        VAO_RENDERING.draw();

        if (timer.passed(2000)) {
            System.out.println("FPS: " + frame.getFps());
            timer.reset();

//            int id = objectIds.get((int) (MathHelper.random() * objectIds.size())).getValue();
//            MESH.deleteObject(objectIds, id);
        }
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, -1);
        frame.setFrameSettingValue(VSyncSetting.class, VSyncType.V_SYNC_OFF);
        frame.setFrameSettingValue(CheckSeveritiesSetting.class, new SeverityType[]{SeverityType.LOW, SeverityType.MEDIUM, SeverityType.HIGH});
    }

    public void setFrame() {
        frame = new Frame(this, "TestNewMeshSystem");
    }
}
