package me.hannsi.lfjg.physic;

import me.hannsi.lfjg.physic.collision.AxisAlignedBoundingBox;
import me.hannsi.lfjg.utils.time.TimeSourceUtil;
import me.hannsi.lfjg.utils.type.types.TimeSourceType;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class PhysicWorld {
    public static float DEFAULT_GRAVITY_VALUE = 9.8f;
    public static Vector2f DEFAULT_GRAVITY = new Vector2f(0, -DEFAULT_GRAVITY_VALUE);
    public static float DEFAULT_SCALE = 20f;

    public Vector2f gravity;
    public List<PhysicObject> physicObjects;
    public float scale;
    public long latestTime;

    public PhysicWorld(Vector2f gravity) {
        this.gravity = gravity;
        this.physicObjects = new ArrayList<>();
        this.scale = DEFAULT_SCALE;
    }

    public PhysicWorld() {
        this(DEFAULT_GRAVITY);
    }

    public void createPhysicObject(PhysicObject physicObject) {
        physicObjects.add(physicObject);
    }

    public void simulation(TimeSourceType timeSourceType) {
        long nowTime = TimeSourceUtil.getTimeMills(timeSourceType);
        float deltaTime = (nowTime - latestTime) / 1000f;

        deltaTime = Math.min(deltaTime, 0.1f);

        for (PhysicObject physicObject : physicObjects) {
            if (physicObject.gravity) {
                physicObject.applyForce(gravity.mul(physicObject.mass));
            }
            physicObject.simulation(deltaTime, scale);
        }

        checkCollisions();

        latestTime = nowTime;
    }

    private void checkCollisions() {
        for (PhysicObject physicObject : physicObjects) {
            for (PhysicObject otherPhysicObject : physicObjects) {
                if (physicObject == otherPhysicObject) {
                    continue;
                }

                AxisAlignedBoundingBox aabb1 = physicObject.getAABB();
                AxisAlignedBoundingBox aabb2 = otherPhysicObject.getAABB();

                if (aabb1.intersects(aabb2)) {

                }
            }
        }
    }
}
