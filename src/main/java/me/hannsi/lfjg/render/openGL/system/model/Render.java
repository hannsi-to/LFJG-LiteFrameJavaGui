package me.hannsi.lfjg.render.openGL.system.model;

import me.hannsi.lfjg.utils.graphics.GLUtil;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class Render {
    private SceneRender sceneRender;
    private GLUtil glUtil;

    public Render() {
        sceneRender = new SceneRender();
        glUtil = new GLUtil();
        glUtil.addGLTarget(GL_DEPTH_TEST);
        glUtil.addGLTarget(GL_CULL_FACE);
    }

    public void cleanup() {
        sceneRender.cleanup();
    }

    public void render(Vector2f resolution, Scene scene) {
        glUtil.enableTargets();

        glCullFace(GL_BACK);
        sceneRender.render(scene);

        glUtil.disableTargets();
    }

    public SceneRender getSceneRender() {
        return sceneRender;
    }

    public void setSceneRender(SceneRender sceneRender) {
        this.sceneRender = sceneRender;
    }

    public GLUtil getGlUtil() {
        return glUtil;
    }

    public void setGlUtil(GLUtil glUtil) {
        this.glUtil = glUtil;
    }
}
