package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.render.renderers.ObjectParameter;

import java.util.Arrays;

public class InstanceData {
    public DrawElementsIndirectCommand drawElementsIndirectCommand;
    private final ObjectParameter[] objectParameters;
    private boolean dirtyFrag = false;

    public InstanceData(int instanceCount) {
        this.drawElementsIndirectCommand = new DrawElementsIndirectCommand(0, instanceCount, 0, 0, 0);
        this.objectParameters = new ObjectParameter[instanceCount];

        Arrays.fill(objectParameters, ObjectParameter.createBuilder());
    }

    public InstanceData(int instanceCount, ObjectParameter[] objectParameters) {
        this.drawElementsIndirectCommand = new DrawElementsIndirectCommand(0, instanceCount, 0, 0, 0);
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

    public ObjectParameter[] getObjectParameters() {
        dirtyFrag = true;
        return objectParameters;
    }
}
