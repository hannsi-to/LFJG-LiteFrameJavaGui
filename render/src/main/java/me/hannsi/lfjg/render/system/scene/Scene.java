package me.hannsi.lfjg.render.system.scene;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.uitl.id.Id;

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

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                sceneId,
                ""
        ).logging(getClass(), DebugLevel.DEBUG);
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

    public IScene getIScene() {
        return iScene;
    }

    public void setIScene(IScene iScene) {
        this.iScene = iScene;
    }
}