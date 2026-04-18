package me.hannsi.lfjg.testRender.system.mesh;

import me.hannsi.lfjg.core.event.events.CleanupEvent;
import me.hannsi.lfjg.core.utils.Cleanup;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static me.hannsi.lfjg.testRender.RenderSystemSetting.CORNER_DEFAULT_SEGMENT_COUNT;

public class Corner implements Cleanup {
    public CornerType cornerType;
    public float radiusX;
    public float radiusY;
    public int segmentCount;

    Corner(CornerType cornerType, float radiusX, float radiusY, int segmentCount) {
        this.cornerType = cornerType;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.segmentCount = segmentCount;
    }

    public static Corner createNone() {
        return new Corner(CornerType.NONE, 0, 0, 0);
    }

    public static Corner createCircle(float radius, int segmentCount) {
        return new Corner(CornerType.CIRCLE, radius, radius, segmentCount);
    }

    public static Corner createCircle(float radius) {
        return new Corner(CornerType.CIRCLE, radius, radius, CORNER_DEFAULT_SEGMENT_COUNT);
    }

    public static Corner createEllipse(float radiusX, float radiusY, int segmentCount) {
        return new Corner(CornerType.ELLIPSE, radiusX, radiusY, segmentCount);
    }

    public static Corner createEllipse(float radiusX, float radiusY) {
        return new Corner(CornerType.ELLIPSE, radiusX, radiusY, CORNER_DEFAULT_SEGMENT_COUNT);
    }

    public static Corner createBevel(float radius) {
        return new Corner(CornerType.BEVEL, radius, radius, 0);
    }

    public static Corner createChamfer(float radiusX, float radiusY) {
        return new Corner(CornerType.CHAMFER, radiusX, radiusY, 0);
    }

    public static Corner createInset(float radiusX, float radiusY) {
        return new Corner(CornerType.INSET, radiusX, radiusY, 0);
    }

    public static Corner createConcave(float radiusX, float radiusY, int segmentCount) {
        return new Corner(CornerType.CONCAVE, radiusX, radiusY, segmentCount);
    }

    public static Corner createConcave(float radiusX, float radiusY) {
        return new Corner(CornerType.CONCAVE, radiusX, radiusY, CORNER_DEFAULT_SEGMENT_COUNT);
    }

    public Corner replaceRadius(float radiusX, float radiusY) {
        this.radiusX = radiusX;
        this.radiusY = radiusY;

        return this;
    }

    public Corner moveRadius(float radiusX, float radiusY) {
        this.radiusX += radiusX;
        this.radiusY += radiusY;

        return this;
    }

    public Corner copy() {
        return new Corner(cornerType, radiusX, radiusY, segmentCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Corner other = (Corner) obj;

        return other.cornerType == cornerType &&
                Float.compare(other.radiusX, radiusX) == 0 &&
                Float.compare(other.radiusY, radiusY) == 0 &&
                other.segmentCount == segmentCount;
    }

    @Override
    public int hashCode() {
        int result = cornerType.hashCode();
        result = 31 * result + Float.hashCode(radiusX);
        result = 31 * result + Float.hashCode(radiusY);
        result = 31 * result + Integer.hashCode(segmentCount);

        return result;
    }

    public CornerType getCornerType() {
        return cornerType;
    }

    public Corner setCornerType(CornerType cornerType) {
        this.cornerType = cornerType;

        return this;
    }

    public float getRadiusX() {
        return radiusX;
    }

    public Corner setRadiusX(float radiusX) {
        this.radiusX = radiusX;

        return this;
    }

    public float getRadiusY() {
        return radiusY;
    }

    public Corner setRadiusY(float radiusY) {
        this.radiusY = radiusY;

        return this;
    }

    public int getSegmentCount() {
        return segmentCount;
    }

    public Corner setSegmentCount(int segmentCount) {
        this.segmentCount = segmentCount;

        return this;
    }

    @Override
    public String toString() {
        return "Corner{cornerType=(" + cornerType.getName() + ") radius=(" + radiusX + ", " + radiusY + ") segmentCount=(" + segmentCount + ")}";
    }

    @Override
    public boolean cleanup(CleanupEvent event) {
        return event.debug(Corner.class, new CleanupEvent.CleanupData(this.getClass()));
    }

    public enum CornerType implements IEnumTypeBase {
        NONE(0, "None"),
        CIRCLE(1, "Circle"),
        ELLIPSE(2, "Ellipse"),
        BEVEL(3, "Bevel"),
        CHAMFER(4, "Chamfer"),
        INSET(5, "Inset"),
        CONCAVE(6, "Concave"),
        ;

        final int id;
        final String name;

        CornerType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum CornerPosition implements IEnumTypeBase {
        BOTTOM_LEFT(0, "BottomLeft"),
        TOP_LEFT(1, "TopLeft"),
        TOP_RIGHT(2, "TopRight"),
        BOTTOM_RIGHT(3, "BottomRight");

        final int id;
        final String name;

        CornerPosition(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
