package me.hannsi.lfjg.render.system.scene;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.Id;

public class Scene {
    @Getter
    private final int sceneId;
    @Getter
    @Setter
    private String sceneName;
    private boolean isInitialize;
    @Getter
    @Setter
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
        ).logging(DebugLevel.DEBUG);
    }

    public boolean isInitialize() {
        return isInitialize;
    }

    public void setInitialize(boolean initialize) {
        isInitialize = initialize;
    }

}