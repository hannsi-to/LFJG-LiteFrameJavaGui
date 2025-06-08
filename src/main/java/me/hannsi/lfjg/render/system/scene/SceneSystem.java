package me.hannsi.lfjg.render.system.scene;

import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.render.debug.exceptions.render.scene.ChangeSceneException;
import me.hannsi.lfjg.render.debug.exceptions.render.scene.SetSceneException;

import java.util.ArrayList;
import java.util.List;

public class SceneSystem {
    private List<Scene> scenes;
    private String currentSceneName;
    private Scene currentScene;

    public SceneSystem() {
        this.scenes = new ArrayList<>();
        this.currentSceneName = null;
        this.currentScene = null;
    }

    public void changeScene(String newCurrentSceneName) {
        LogGenerator logGenerator = new LogGenerator("StartSceneChange", "Source: SceneSystem", "Type: Scene change", "ID: " + currentScene.getSceneId(), "Severity: Info", "Message: Start change from " + currentSceneName + " to " + newCurrentSceneName + ".");
        logGenerator.logging(DebugLevel.INFO);

        currentScene.getiScene().stopFrame();

        currentSceneName = newCurrentSceneName;
        currentScene = getScene(newCurrentSceneName);

        if (currentScene != null) {
            currentScene.getiScene().init();
        } else {
            throw new ChangeSceneException("Scene " + newCurrentSceneName + " not found.");
        }

        logGenerator = new LogGenerator("EndSceneChange", "Source: SceneSystem", "Type: Scene change", "ID: " + currentScene.getSceneId(), "Severity: Info", "Message: Finished changing from " + currentSceneName + " to " + newCurrentSceneName + ".");
        logGenerator.logging(DebugLevel.INFO);
    }

    public void addScene(Scene scene) {
        this.scenes.add(scene);
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

    public void initScenes() {
        if (currentScene == null) {
            return;
        }

        currentScene.getiScene().init();
        currentScene.setInitialize(true);
    }

    public void drawFrameScenes() {
        if (currentScene == null) {
            return;
        }

        currentScene.getiScene().drawFrame();
    }

    public void stopFrameScenes() {
        if (currentScene == null) {
            return;
        }

        currentScene.getiScene().stopFrame();
    }

    public void cleanup() {
        scenes.forEach(Scene::cleanup);
        scenes.clear();
        currentScene = null;
        currentSceneName = null;

        LogGenerator logGenerator = new LogGenerator("SceneSystem", "Source: SceneSystem", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: SceneSystem cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
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

    public void setCurrentScene(String currentSceneName) {
        this.currentSceneName = currentSceneName;
        currentScene = getScene(currentSceneName);
        if (currentScene == null) {
            throw new SetSceneException("Scene " + currentSceneName + " not found.");
        }

        LogGenerator logGenerator = new LogGenerator("SetScene", "Source: SceneSystem", "Type: Set scene", "ID: " + currentScene.getSceneId(), "Severity: Info", "Message: " + currentSceneName + " is set.");
        logGenerator.logging(DebugLevel.INFO);
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }
}
