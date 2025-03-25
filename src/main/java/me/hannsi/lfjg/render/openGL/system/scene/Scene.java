package me.hannsi.lfjg.render.openGL.system.scene;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.render.openGL.system.Id;

public class Scene {
    private final int sceneId;
    private String sceneName;
    private IScene iScene;

    public Scene(String sceneName, IScene iScene) {
        this.sceneId = Id.latestSceneId++;
        this.sceneName = sceneName;
        this.iScene = iScene;
    }

    public void cleanup() {
        iScene.stopFrame();
        iScene = null;

        LogGenerator logGenerator = new LogGenerator(sceneName, "Source: Scene", "Type: Cleanup", "ID: " + sceneId, "Severity: Debug", "Message: Scene cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
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