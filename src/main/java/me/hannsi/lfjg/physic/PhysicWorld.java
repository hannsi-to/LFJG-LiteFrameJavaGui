package me.hannsi.lfjg.physic;

import me.hannsi.lfjg.debug.debug.DebugLog;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class PhysicWorld {
    public static float DEFAULT_GRAVITY_VALUE = 9.8f;
    public static Vector2f DEFAULT_GRAVITY = new Vector2f(0, -DEFAULT_GRAVITY_VALUE);

    public Vector2f gravity;
    public List<PhysicObject> physicObjects;

    public PhysicWorld(Vector2f gravity) {
        this.gravity = gravity;
        this.physicObjects = new ArrayList<>();
    }

    public PhysicWorld() {
        this(DEFAULT_GRAVITY);
    }

    public void createPhysicObject(PhysicObject physicObject) {
        physicObjects.add(physicObject);
    }

    public void simulation(float fps) {
        if (fps == 0) {
            fps = 1;
        }
        float deltaTime = 1 / fps;

        for (PhysicObject physicObject : physicObjects) {
            physicObject.velocity = new Vector2f(gravity.x(), gravity.y());
            physicObject.simulation(deltaTime);
            DebugLog.debug(getClass(), physicObject.glObject.getCenterX() + " : " + physicObject.glObject.getCenterY());
        }
    }
}
