package me.hannsi.lfjg.physics;

import org.joml.Vector2f;

public class SATCollision {
    private PhysicsObject mainObject;

    public SATCollision(PhysicsObject mainObject) {
        this.mainObject = mainObject;
    }

    public boolean intersects(PhysicsObject otherObject) {
        return checkSAT(mainObject, otherObject) && checkSAT(otherObject, mainObject);
    }

    private boolean checkSAT(PhysicsObject obj1, PhysicsObject obj2) {
        for (int i = 0; i < obj1.getPositions().size(); i++) {
            int next = (i + 1) % obj1.getPositions().size();
            Vector2f edge = new Vector2f(obj1.getPositions().get(next).set(obj1.getPositions().get(i)));
            Vector2f axis = new Vector2f(-edge.y(), edge.x()).normalize();

            float minA = Float.MAX_VALUE;
            float maxA = -Float.MAX_VALUE;
            for (Vector2f position : obj1.getPositions()) {
                float projection = position.dot(axis);
                minA = Math.min(minA, projection);
                maxA = Math.max(maxA, projection);
            }

            float minB = Float.MAX_VALUE;
            float maxB = -Float.MAX_VALUE;
            for (Vector2f position : obj2.getPositions()) {
                float projection = position.dot(axis);
                minB = Math.min(minB, projection);
                maxB = Math.max(maxB, projection);
            }

            if (maxA < minB || maxB < minA) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "PolygonCollision [vertices=" + mainObject.getPositions() + "]";
    }

    public PhysicsObject getMainObject() {
        return mainObject;
    }

    public void setMainObject(PhysicsObject mainObject) {
        this.mainObject = mainObject;
    }
}
