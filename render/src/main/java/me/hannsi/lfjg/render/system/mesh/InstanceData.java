package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.render.renderers.Transform;

import java.util.Arrays;

public class InstanceData {
    public static final int NO_ATTACH_TEXTURE = -1;
    public final int instanceCount;
    private final Transform[] transforms;
    private final Color[] instanceColors;
    private boolean dirtyFrag = false;

    public InstanceData(int instanceCount, Color defaultColor) {
        this.instanceCount = instanceCount;
        this.transforms = new Transform[instanceCount];
        this.instanceColors = new Color[instanceCount];

        Arrays.fill(transforms, Transform.createBuilder());
        Arrays.fill(instanceColors, defaultColor);
    }

    public InstanceData(int instanceCount, Transform[] transforms, Color[] instanceColors) {
        this.instanceCount = instanceCount;
        this.transforms = transforms;
        this.instanceColors = instanceColors;

        if (transforms.length != instanceCount) {
            throw new RuntimeException("The sizes of InstanceCount and InstanceModels do not match. InstanceCount: " + instanceCount + " != Transforms: " + transforms.length);
        }

        if (instanceColors.length != instanceCount) {
            throw new RuntimeException("The sizes of InstanceCount and InstanceColors do not match. InstanceCount: " + instanceCount + " != InstanceColors: " + instanceColors.length);
        }
    }

    public void resetDirtyFlag() {
        dirtyFrag = false;
    }

    public boolean isDirtyFrag() {
        return dirtyFrag;
    }

    public Transform[] getTransforms() {
        dirtyFrag = true;
        return transforms;
    }

    public Color[] getInstanceColors() {
        dirtyFrag = true;
        return instanceColors;
    }
}
