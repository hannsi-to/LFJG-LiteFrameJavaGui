package me.hannsi.test;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.reflection.reference.LongRef;
import me.hannsi.lfjg.core.utils.time.Timer;
import me.hannsi.lfjg.core.utils.type.types.ProjectionType;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.LFJGContext;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.renderers.BlendType;
import me.hannsi.lfjg.render.renderers.JointType;
import me.hannsi.lfjg.render.renderers.PointType;
import me.hannsi.lfjg.render.system.mesh.GLObjectData;
import me.hannsi.lfjg.render.system.mesh.TestMesh;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.DrawType;
import me.hannsi.lfjg.render.system.rendering.GLStateCache;
import me.hannsi.lfjg.render.system.shader.FragmentShaderType;
import me.hannsi.lfjg.render.system.shader.UploadUniformType;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static me.hannsi.lfjg.render.LFJGRenderContext.glObjectPool;
import static me.hannsi.lfjg.render.LFJGRenderContext.shaderProgram;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glBindBufferBase;
import static org.lwjgl.opengl.GL31.*;

public class TestNewMeshSystem implements LFJGFrame {
    Timer timer = new Timer();
    List<LongRef> objectIds = new ArrayList<>();
    private TestMesh testMesh;
    private Matrix4f modelMatrix;
    private Matrix4f viewMatrix;
    private BlendType blendType;
    private int uboMatrices;

    public static void main(String[] args) {
        new TestNewMeshSystem().setFrame();
    }

    public void updateUBO(Matrix4f projection, Matrix4f view, Matrix4f model) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16 * 3);

        projection.get(0, buffer);
        view.get(16, buffer);
        model.get(32, buffer);

        glBindBuffer(GL_UNIFORM_BUFFER, uboMatrices);
        glBufferSubData(GL_UNIFORM_BUFFER, 0, buffer);
        glBindBuffer(GL_UNIFORM_BUFFER, 0);
    }

    @Override
    public void init() {
        LFJGContext.frame.updateLFJGLContext();

        uboMatrices = glGenBuffers();
        glBindBuffer(GL_UNIFORM_BUFFER, uboMatrices);
        glBufferData(GL_UNIFORM_BUFFER, 3 * 16 * Float.BYTES, GL_DYNAMIC_DRAW);
        glBindBufferBase(GL_UNIFORM_BUFFER, 0, uboMatrices);
        glBindBuffer(GL_UNIFORM_BUFFER, 0);

        int blockIndex = glGetUniformBlockIndex(shaderProgram.getProgramId(), "Matrices");
        glUniformBlockBinding(shaderProgram.getProgramId(), blockIndex, 0);

        int numObjects = 100;
        int numVerticesPerStrip = 100;
        float minX = 0;
        float maxX = 1920;
        float minY = 0;
        float maxY = 1080;
        float minSize = 100.0f;
        float maxSize = 500.0f;

        Random random = new Random();

        testMesh = TestMesh.createMesh(
                1,
                1,
                1
        );

        for (int i = 0; i < numObjects; i++) {
            float x = minX + random.nextFloat() * (maxX - minX);
            float y = minY + random.nextFloat() * (maxY - minY);

            List<Vertex> vertices = new ArrayList<>();

            for (int j = 0; j < numVerticesPerStrip; j++) {
                float r = random.nextFloat();
                float g = random.nextFloat();
                float b = random.nextFloat();

                Vertex v = new Vertex(x, y, 0, r, g, b, 0.5f, 0, 0, 0, 0, 1);
                vertices.add(v);

                float angle = (float) (random.nextFloat() * Math.PI * 2.0);
                float step = minSize + random.nextFloat() * (maxSize - minSize);
                x += (float) (Math.cos(angle) * step);
                y += (float) (Math.sin(angle) * step);
            }

            LongRef id = new LongRef();
            testMesh.addObject(
                    id,
                    ProjectionType.ORTHOGRAPHIC_PROJECTION,
                    DrawType.POINTS,
                    30,
                    JointType.MITER,
                    10f,
                    PointType.ROUND,
                    vertices.toArray(new Vertex[0])
            );

            objectIds.add(id);
        }

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

        testMesh.initBufferObject();

        modelMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        blendType = BlendType.NORMAL;
    }

    @Override
    public void drawFrame() {
        shaderProgram.bind();

        GLStateCache.blendFunc(blendType.getSfactor(), blendType.getDfactor());
        GLStateCache.setBlendEquation(blendType.getEquation());
        GLStateCache.enable(GL11.GL_BLEND);
        GLStateCache.disable(GL11.GL_DEPTH_TEST);

        shaderProgram.setUniform("fragmentShaderType", UploadUniformType.ON_CHANGE, FragmentShaderType.OBJECT.getId());
        shaderProgram.setUniform("resolution", UploadUniformType.ON_CHANGE, Core.frameBufferSize);
        updateUBO(Core.projection2D.getProjMatrix(), viewMatrix, modelMatrix);

        testMesh.debugDraw(DrawType.TRIANGLES.getId(), false);

        if (timer.passed(2000)) {
            System.out.println("FPS: " + LFJGContext.frame.getFps());
            testMesh.debugLogging(
                    true,
                    true,
                    true,
                    true,
                    true,
                    true
            );
            timer.reset();

            System.out.println(objectIds.toString());

            long id = objectIds.get((int) (MathHelper.random() * 100)).getValue();
            for (Map.Entry<Long, GLObjectData> entry : glObjectPool.getObjects().entrySet()) {
                if (entry.getKey() == id) {
                    entry.getValue().draw = false;
                }
            }
        }
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public void setFrameSetting() {
        LFJGContext.frame.setFrameSettingValue(RefreshRateSetting.class, -1);
        LFJGContext.frame.setFrameSettingValue(VSyncSetting.class, VSyncType.V_SYNC_OFF);
        LFJGContext.frame.setFrameSettingValue(CheckSeveritiesSetting.class, new SeverityType[]{SeverityType.LOW, SeverityType.MEDIUM, SeverityType.HIGH});
    }

    public void setFrame() {
        LFJGContext.frame = new Frame(this, "TestNewMeshSystem");
    }
}
