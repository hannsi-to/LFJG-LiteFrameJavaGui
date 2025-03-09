package me.hannsi.lfjg.physics;

import org.joml.Vector2f;

import java.util.List;

public class AABBCollision {
    private float minX;
    private float minY;
    private float maxX;
    private float maxY;

    public AABBCollision(PhysicsObject obj) {
        List<Vector2f> positions = obj.getPositions();
        if (positions.isEmpty()) {
            return;
        }

        minX = maxX = positions.get(0).x;
        minY = maxY = positions.get(0).y;

        for (Vector2f position : positions) {
            if (position.x < minX) {
                minX = position.x;
            } else if (position.x > maxX) {
                maxX = position.x;
            }

            if (position.y < minY) {
                minY = position.y;
            } else if (position.y > maxY) {
                maxY = position.y;
            }
        }
    }

    public boolean intersects(PhysicsObject otherObject) {
        AABBCollision other = otherObject.getAabbCollision();
        return !(this.maxX < other.minX || this.minX > other.maxX || this.maxY < other.minY || this.minY > other.maxY);
    }

    @Override
    public String toString() {
        return "AABBCollision [minX=" + minX + ", maxX=" + maxX + ", minY=" + minY + ", maxY=" + maxY + "]";
    }
}
