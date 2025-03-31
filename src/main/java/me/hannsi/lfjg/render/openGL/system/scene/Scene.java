package me.hannsi.lfjg.render.openGL.system.scene;

import me.hannsi.lfjg.debug.debug.system.DebugLevel;
import me.hannsi.lfjg.debug.debug.logger.LogGenerator;
import me.hannsi.lfjg.render.openGL.system.Id;

public class Scene {
    private final int sceneId;
    private String sceneName;
    private boolean isInitialize;
    private IScene iScene;

    public Scene(String sceneName, IScene iScene) {
        this.sceneId = Id.latestSceneId++;
        this.sceneName = sceneName;
        this.isInitialize = false;
        this.iScene = iScene;
    }

    public void cleanup() {
        if (isInitialize) {
            iScene.stopFrame();
        }

        isInitialize = false;
        iScene = null;

        LogGenerator logGenerator = new LogGenerator(sceneName, "Source: Scene", "Type: Cleanup", "ID: " + sceneId, "Severity: Debug", "Message: Scene cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    public boolean isInitialize() {
        return isInitialize;
    }

    public void setInitialize(boolean initialize) {
        isInitialize = initialize;
    }

    public int getSceneId() {
        return sceneId;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public IScene getiScene() {
        return iScene;
    }

    public void setiScene(IScene iScene) {
        this.iScene = iScene;
    }
}