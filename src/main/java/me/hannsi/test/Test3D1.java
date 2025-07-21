package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.renderers.model.ModelRender;
import me.hannsi.lfjg.render.system.model.Entity;
import me.hannsi.lfjg.render.system.model.ModelCache;
import me.hannsi.lfjg.render.system.model.ModelLoader;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;
import me.hannsi.lfjg.core.utils.graphics.image.TextureCache;
import me.hannsi.lfjg.core.utils.reflection.location.Location;

public class Test3D1 implements IScene {
    Scene scene;
    Frame frame;
    TestGuiFrame testGuiFrame;

    ModelRender modelRender;
    Entity entity;
    Entity entity2;
    float rotation;

    public Test3D1(Frame frame, TestGuiFrame testGuiFrame) {
        this.scene = new Scene("Test3D1", this);
        this.frame = frame;
        this.testGuiFrame = testGuiFrame;
    }

    @Override
    public void init() {
        float[] positions = new float[]{
                // V0
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,

                // For text coords in top face
                // V8: V4 repeated
                -0.5f, 0.5f, -0.5f,
                // V9: V5 repeated
                0.5f, 0.5f, -0.5f,
                // V10: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V11: V3 repeated
                0.5f, 0.5f, 0.5f,

                // For text coords in right face
                // V12: V3 repeated
                0.5f, 0.5f, 0.5f,
                // V13: V2 repeated
                0.5f, -0.5f, 0.5f,

                // For text coords in left face
                // V14: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V15: V1 repeated
                -0.5f, -0.5f, 0.5f,

                // For text coords in bottom face
                // V16: V6 repeated
                -0.5f, -0.5f, -0.5f,
                // V17: V7 repeated
                0.5f, -0.5f, -0.5f,
                // V18: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // V19: V2 repeated
                0.5f, -0.5f, 0.5f,
        };
        float[] colors = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };
        float[] textCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,

                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,

                // For text coords in top face
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,

                // For text coords in right face
                0.0f, 0.0f,
                0.0f, 0.5f,

                // For text coords in left face
                0.5f, 0.0f,
                0.5f, 0.5f,

                // For text coords in bottom face
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };
        int[] indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                8, 10, 11, 9, 8, 11,
                // Right face
                12, 13, 7, 5, 12, 7,
                // Left face
                14, 15, 6, 4, 14, 6,
                // Bottom face
                16, 18, 19, 17, 16, 19,
                // Back face
                4, 6, 7, 5, 4, 7,};

        TextureCache textureCache = TextureCache.createTextureCache();

        modelRender = ModelRender.createModelRender()
                .modelCache(
                        ModelCache.createModelCache()
//                                .createModelCache(
//                                        Model
//                                                .createModel("cube-model", materials)
//                                                .addEntity(
//                                                        entity = Entity.createEntity("cube-entity")
//                                                                .setPosition(0, 0, -2)
//                                                                .updateModelMatrix()
//                                                )
//                                )
                                .createModelCache(
                                        ModelLoader.createModelLoader("cube-model")
                                                .textureCache(textureCache)
                                                .modelLocation(Location.fromResource("model/tripo/tripo_pbr_model_2412d272-991e-418d-adb2-6cfe4820dc81.obj"))
                                                .loadModel()
                                                .addEntity(
                                                        entity = Entity.createEntity("cube-entity")
                                                                .setPosition(0, 0, -2)
                                                                .updateModelMatrix()
                                                )
                                )
                )
                .textureCache(textureCache);
    }

    @Override
    public void drawFrame() {
        rotation += 1.5F;
        if (rotation > 360) {
            rotation = 0;
        }
        entity.setRotation(1, 1, 1, (float) Math.toRadians(rotation)).updateModelMatrix();
//        entity2.setRotation(1, 1, 1, (float) Math.toRadians(rotation)).updateModelMatrix();

        modelRender.render();
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
