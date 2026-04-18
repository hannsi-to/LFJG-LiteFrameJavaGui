package me.hannsi.test;

import me.hannsi.lfjg.core.utils.graphics.ResolutionType;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.core.utils.time.Timer;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.testRender.renderers.BlendType;
import me.hannsi.lfjg.testRender.renderers.InstanceParameter;
import me.hannsi.lfjg.testRender.renderers.JointType;
import me.hannsi.lfjg.testRender.renderers.PointType;
import me.hannsi.lfjg.testRender.system.mesh.MeshBuilder;
import me.hannsi.lfjg.testRender.system.mesh.ObjectData;
import me.hannsi.lfjg.testRender.system.mesh.Vertex;
import me.hannsi.lfjg.testRender.system.rendering.DrawType;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.utils.math.MathHelper.getRandomInt;
import static me.hannsi.lfjg.frame.LFJGFrameContext.frame;
import static me.hannsi.lfjg.testRender.LFJGRenderContext.*;

public class TestNewMeshSystem implements LFJGFrame {
    public static List<IntRef> objectIds = new ArrayList<>();
    Timer timer = new Timer();

    public static void main(String[] args) {
        new TestNewMeshSystem().setFrame();
    }

    @Override
    public void init() {
//        GLRect.createGLRect("GLRect1")
//                .x1_y1_color1_2p(0, 0, Color.RED)
//                .width3_height3_color3_2p(frameBufferSize.x, frameBufferSize.y, Color.GREEN)
//                .fill()
//                .update();

        for (int i = 0; i < 100; i++) {
//            int w = 16 + (int) (MathHelper.random() * 100);
//            int h = 16 + (int) (MathHelper.random() * 100);
            int w = 64;
            int h = 64;
//            atlasPacker.addSprite("Code: " + i, Sprite.createRandomColor(w, h, false, SpriteMemoryPolicy.STREAMING).setCommited(false));
        }
//        atlasPacker.addSprite("Test1", AssetManager.getTextureAsset(new Location("texture/test/test1.jpg", LocationType.RESOURCE)));

        atlasPacker.generate();

        sparseTexture2DArray.commitTexture("Code: 0", true)
                .commitTexture("Code: 1", false)
                .commitTexture("Code: 2", true)
                .commitTexture("Test1", true)
                .updateFromAtlas();

        int instanceCount = 4;
        float objectSize = 1920;
        InstanceParameter[] matrix4fs = new InstanceParameter[instanceCount];
        int[] spriteIndices = sparseTexture2DArray.getSpriteIndiesFromName(
                "Test1",
                "Code: 2",
                "Code: 1",
                "Code: 0"
        );
        for (int i = 0; i < instanceCount; i++) {
            float scale = ((objectSize / instanceCount) * (instanceCount - i)) / objectSize;
            int layer = spriteIndices[i];
            matrix4fs[i] = InstanceParameter.createBuilder()
                    .spriteIndex(layer)
                    .scale(scale, scale, 1)
                    .color(new Color(0f, 1f, 0f, 1f));
        }

        InstanceParameter[] instanceParameters1 = new InstanceParameter[]{
                InstanceParameter.createBuilder()
                        .color(new Color(1f, 1f, 1f, 1f))
        };
        mesh.addObject(
                MeshBuilder.createBuilder()
                        .drawType(DrawType.QUADS)
                        .pointSize(30)
                        .strokeWidth(10f)
                        .vertices(
                                new Vertex(0, 0, 0, 1, 0, 1, 1f, 0, 1, 0, 0, 0),
                                new Vertex(objectSize, 0, 0, 0, 1, 0, 1f, 1, 1, 0, 0, 0),
                                new Vertex(objectSize, objectSize, 0, 0, 0, 1, 1f, 1, 0, 0, 0, 0),
                                new Vertex(0, objectSize, 0, 0, 1, 1, 1f, 0, 0, 0, 0, 0)
                        )
                        .objectData(new ObjectData(instanceCount, matrix4fs))
        );

        int numObjects = 100;
        int numVerticesPerObject = 1;

        float minX = 100;
        float minY = 100;
        float maxX = ResolutionType.WQHD.getWidth();
        float maxY = ResolutionType.WQHD.getHeight();

        int columns = 10;
        int rows = 10;

        float cellWidth = (maxX - minX) / columns;
        float cellHeight = (maxY - minY) / rows;

        BlendType blendType = BlendType.NO_BLEND;
        for (int i = 0; i < numObjects; i++) {
            InstanceParameter[] instanceParameters = new InstanceParameter[numVerticesPerObject];

            int col = i % columns;
            int row = i / columns;

            float x = minX + col * cellWidth + cellWidth / 2f;
            float y = minY + row * cellHeight + cellHeight / 2f;

            float r = (float) i / numObjects;
            float g = 1f;
            float b = 0f;

            if (i > 50) {
                blendType = BlendType.ADD;
            }

            instanceParameters[0] = InstanceParameter.createBuilder()
                    .translate(x, y, 0)
                    .color(new Color(r, g, b, 1));

            MeshBuilder builder;
            mesh.addObject(
                    builder = MeshBuilder.createBuilder()
                            .blendType(blendType)
                            .drawType(DrawType.POINTS)
                            .pointSize(30f)
                            .strokeWidth(-1f)
                            .pointType(PointType.SQUARE)
                            .strokeJoinType(JointType.NONE)
                            .vertices(new Vertex(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0))
                            .objectData(new ObjectData(numVerticesPerObject, instanceParameters))
            );

            objectIds.add(builder.getObjectIdPointer());
        }

//        ObjectParameter[] objectParameters2 = new ObjectParameter[1];
//        objectParameters2[0] = ObjectParameter.createBuilder()
//                .scale(0.5f, 0.5f, 1)
//                .color(new Color(1f, 0f, 0f, 0.5f));
//        mesh.addObject(
//                TestMesh.Builder.createBuilder()
//                        .drawType(DrawType.QUADS)
//                        .blendType(BlendType.SUBTRACT)
//                        .vertices(
//                                new Vertex(500, 500, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0),
//                                new Vertex(500 + objectSize, 500, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0),
//                                new Vertex(500 + objectSize, 500 + objectSize, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0),
//                                new Vertex(500, 500 + objectSize, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0)
//                        )
//                        .instanceData(new InstanceData(1, objectParameters2))
//                        .renderOrder(2)
//        );
//
//        ObjectParameter[] objectParameters3 = new ObjectParameter[1];
//        objectParameters3[0] = ObjectParameter.createBuilder()
//                .scale(0.25f, 0.25f, 1)
//                .color(new Color(0f, 1f, 0f, 1));
//        mesh.addObject(
//                TestMesh.Builder.createBuilder()
//                        .drawType(DrawType.QUADS)
//                        .blendType(BlendType.NORMAL)
//                        .vertices(
//                                new Vertex(0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0),
//                                new Vertex(0 + objectSize, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0),
//                                new Vertex(0 + objectSize, 0 + objectSize, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0),
//                                new Vertex(0, 0 + objectSize, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0)
//                        )
//                        .instanceData(new InstanceData(1, objectParameters3))
//                        .renderOrder(1)
//        );

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

    }

    @Override
    public void drawFrame() {
        update();

//        for (ObjectParameter instanceModel : instanceData.getTransforms()) {
//            instanceModel.translate(1, 0, 0);
//        }

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


        if (timer.passed(500)) {
            IntBuffer vp = BufferUtils.createIntBuffer(4);
            GL11.glGetIntegerv(GL11.GL_VIEWPORT, vp);

            System.out.println(
                    "Viewport = x:" + vp.get(0) +
                            " y:" + vp.get(1) +
                            " w:" + vp.get(2) +
                            " h:" + vp.get(3)
            );

            System.out.println("FPS: " + frame.getFps());
            timer.reset();

            if (!objectIds.isEmpty()) {
                IntRef id = objectIds.get(getRandomInt(0, objectIds.size() - 1));

                if (id != null) {
                    int i = id.getValue();

                    System.out.println("id: " + i);

                    for (InstanceParameter instanceParameter : glObjectPool.getBuilder(i).getObjectData().getInstanceParameters()) {
//                        instanceParameter.translate(100, 0, 0);
                    }

//                    mesh.deleteObject(objectIds, i);
                } else {
                    objectIds.removeFirst();
                    System.err.println("Warning: Found null ID at index " + 0);
                }
            }
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
