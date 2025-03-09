package me.hannsi.lfjg.physics;

import me.hannsi.lfjg.utils.math.MathHelper;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class CollisionUtil {
    public static boolean checkCollision(PhysicsObject mainObject, PhysicsObject otherObject) {
        if (!mainObject.getAabbCollision().intersects(otherObject)) {
            return false;
        }

        return mainObject.getSatCollision().intersects(otherObject);
    }

    public static List<Vector2f> getEdgeNormals(PhysicsObject mainObject) {
        List<Vector2f> normals = new ArrayList<>();
        int size = mainObject.getPositions().size();

        for (int i = 0; i < size; i++) {
            Vector2f p1 = mainObject.getPositions().get(i);
            Vector2f p2 = mainObject.getPositions().get((i + 1) % size);
            Vector2f edge = new Vector2f(p2).sub(p1);

            Vector2f normal = new Vector2f(-edge.y, edge.x);
            normal.normalize();
            normals.add(normal);
        }
        return normals;
    }

    public static Vector2f getCollisionNormal(PhysicsObject mainObject, PhysicsObject otherObject) {
        List<Vector2f> normals = getEdgeNormals(mainObject);
        normals.addAll(getEdgeNormals(otherObject));

        float minOverlap = Float.MAX_VALUE;
        Vector2f bestNormal = null;
        for (Vector2f axis : normals) {
            float minA = Float.MAX_VALUE, maxA = -Float.MAX_VALUE;
            float minB = Float.MAX_VALUE, maxB = -Float.MAX_VALUE;

            for (Vector2f pos : mainObject.getPositions()) {
                float projection = axis.dot(pos);
                if (projection < minA) {
                    minA = projection;
                }
                if (projection > maxA) {
                    maxA = projection;
                }
            }

            for (Vector2f pos : otherObject.getPositions()) {
                float projection = axis.dot(pos);
                if (projection < minB) {
                    minB = projection;
                }
                if (projection > maxB) {
                    maxB = projection;
                }
            }

            float overlap = Math.min(maxA, maxB) - Math.max(minA, minB);
            if (overlap <= 0) {
                return null;
            }

            if (overlap < minOverlap) {
                minOverlap = overlap;
                bestNormal = new Vector2f(axis);
            }
        }

        return bestNormal;
    }

    public static Vector2f getCollisionPoint(PhysicsObject physicsObject, PhysicsObject other) {
        Vector2f closestPoint = null;
        float minDist = Float.MAX_VALUE;

        for (int i = 0; i < physicsObject.getPositions().size(); i++) {
            Vector2f a1 = physicsObject.getPositions().get(i);
            Vector2f a2 = physicsObject.getPositions().get((i + 1) % physicsObject.getPositions().size());

            for (int j = 0; j < other.getPositions().size(); j++) {
                Vector2f b1 = other.getPositions().get(j);
                Vector2f b2 = other.getPositions().get((j + 1) % other.getPositions().size());

                Vector2f intersection = MathHelper.getLineIntersection(a1, a2, b1, b2);
                if (intersection != null) {
                    float dist = intersection.distanceSquared(a1);
                    if (dist < minDist) {
                        minDist = dist;
                        closestPoint = intersection;
                    }
                }
            }
        }

        return closestPoint;
    }
}
