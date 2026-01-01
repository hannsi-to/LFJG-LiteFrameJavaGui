package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.render.renderers.ObjectParameter;

import java.util.Arrays;

public class InstanceData {
    public final int instanceCount;
    private final ObjectParameter[] objectParameters;
    private boolean dirtyFrag = false;

    public InstanceData(int instanceCount) {
        this.instanceCount = instanceCount;
        this.objectParameters = new ObjectParameter[instanceCount];

        Arrays.fill(objectParameters, ObjectParameter.createBuilder());
    }

    public InstanceData(int instanceCount, ObjectParameter[] objectParameters) {
        this.instanceCount = instanceCount;
        this.objectParameters = objectParameters;

        if (objectParameters.length != instanceCount) {
            throw new RuntimeException("The sizes of InstanceCount and ObjectParameters do not match. InstanceCount: " + instanceCount + " != ObjectParameters: " + objectParameters.length);
        }
    }

    public void resetDirtyFlag() {
        dirtyFrag = false;
    }

    public boolean isDirtyFrag() {
        return dirtyFrag;
    }

    public ObjectParameter[] getTransforms() {
        dirtyFrag = true;
        return objectParameters;
    }
}
