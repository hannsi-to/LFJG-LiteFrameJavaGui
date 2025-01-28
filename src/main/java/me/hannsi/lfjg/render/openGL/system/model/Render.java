package me.hannsi.lfjg.render.openGL.system.model;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;

public class Render {
    private SceneRender sceneRender;

    public Render() {
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        sceneRender = new SceneRender();
    }

    public void cleanup() {
        sceneRender.cleanup();
    }

    public void render(Vector2f resolution, Scene scene) {
        sceneRender.render(scene);
    }

    public SceneRender getSceneRender() {
        return sceneRender;
    }

    public void setSceneRender(SceneRender sceneRender) {
        this.sceneRender = sceneRender;
    }
}
