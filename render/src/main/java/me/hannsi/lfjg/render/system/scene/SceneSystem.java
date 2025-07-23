package me.hannsi.lfjg.render.system.scene;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.render.debug.exceptions.render.scene.ChangeSceneException;
import me.hannsi.lfjg.render.debug.exceptions.render.scene.SetSceneException;

import java.util.ArrayList;
import java.util.List;

public class SceneSystem {
    private List<Scene> scenes;
    private String currentSceneName;
    private Scene currentScene;

    SceneSystem() {
        this.scenes = new ArrayList<>();
        this.currentSceneName = null;
        this.currentScene = null;
    }

    public static SceneSystem initSceneSystem() {
        return new SceneSystem();
    }

    public void changeScene(String newCurrentSceneName) {
        new LogGenerator(
                "Start Scene Change",
                "Source: " + getClass().getName(),
                "Type: Scene change",
                "ID: " + currentScene.getSceneId(),
                "Severity: Info",
                "Message: Start change from " + currentSceneName + " to " + newCurrentSceneName + "."
        ).logging(DebugLevel.INFO);

        currentScene.getIScene().stopFrame();

        currentSceneName = newCurrentSceneName;
        currentScene = getScene(newCurrentSceneName);

        if (currentScene != null) {
            currentScene.getIScene().init();
        } else {
            throw new ChangeSceneException("Scene " + newCurrentSceneName + " not found.");
        }

        new LogGenerator(
                "End Scene Change",
                "Source: " + getClass().getName(),
                "Type: Scene change",
                "ID: " + currentScene.getSceneId(),
                "Severity: Info",
                "Message: Finished changing from " + currentSceneName + " to " + newCurrentSceneName + "."
        ).logging(DebugLevel.INFO);
    }

    public SceneSystem addScene(Scene scene) {
        this.scenes.add(scene);
        return this;
    }

    public void removeScene(Scene scene) {
        this.scenes.remove(scene);
    }

    public void removeScene(String sceneName) {
        this.scenes.removeIf(scene -> scene.getSceneName().equals(sceneName));
    }

    public Scene getScene(String sceneName) {
        return scenes.stream().filter(scene -> scene.getSceneName().equals(sceneName)).findFirst().orElse(null);
    }

    public SceneSystem initScenes() {
        if (currentScene == null) {
            return this;
        }

        currentScene.getIScene().init();
        currentScene.setInitialize(true);

        return this;
    }

    public void drawFrameScenes() {
        if (currentScene == null) {
            return;
        }

        currentScene.getIScene().drawFrame();
    }

    public void stopFrameScenes() {
        if (currentScene == null) {
            return;
        }

        currentScene.getIScene().stopFrame();
    }

    public void cleanup() {
        StringBuilder ids = new StringBuilder();
        for (Scene scene : scenes) {
            ids.append(scene.getSceneId()).append(". ");
            scene.cleanup();
        }
        scenes.forEach(Scene::cleanup);
        scenes.clear();
        currentScene = null;
        currentSceneName = null;

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.substring(0, ids.length() - 2),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public List<Scene> getScenes() {
        return scenes;
    }

    public void setScenes(List<Scene> scenes) {
        this.scenes = scenes;
    }

    public String getCurrentSceneName() {
        return currentSceneName;
    }

    public void setCurrentSceneName(String currentSceneName) {
        this.currentSceneName = currentSceneName;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public SceneSystem setCurrentScene(String currentSceneName) {
        this.currentSceneName = currentSceneName;
        currentScene = getScene(currentSceneName);
        if (currentScene == null) {
            throw new SetSceneException("Scene " + currentSceneName + " not found.");
        }

        new LogGenerator(
                "Set Scene",
                "Source: " + getClass().getName(),
                "Type: Set scene",
                "ID: " + currentScene.getSceneId(),
                "Severity: Info",
                "Message: " + currentSceneName + " is set."
        ).logging(DebugLevel.INFO);

        return this;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }
}
