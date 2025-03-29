package me.hannsi.lfjg.physic.collision;

import org.joml.Vector2f;

public record AxisAlignedBoundingBox(Vector2f min, Vector2f max) {

    public boolean intersects(AxisAlignedBoundingBox other) {
        return (this.max.x > other.min.x && this.min.x < other.max.x) && (this.max.y > other.min.y && this.min.y < other.max.y);
    }
}
