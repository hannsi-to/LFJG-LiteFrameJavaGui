package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.render.renderers.InstanceParameter;

import java.util.Arrays;

public class ObjectData {
    public static final long BYTES = Integer.BYTES + Integer.BYTES + 2 * Integer.BYTES;
    public DrawElementsIndirectCommand drawElementsIndirectCommand;
    private final InstanceParameter[] instanceParameters;
    private boolean instanceParameterFlag = false;

    public ObjectData(int instanceCount) {
        this.drawElementsIndirectCommand = new DrawElementsIndirectCommand(0, instanceCount, 0, 0, 0);
        this.instanceParameters = new InstanceParameter[instanceCount];

        Arrays.fill(instanceParameters, InstanceParameter.createBuilder());
    }

    public ObjectData(int instanceCount, InstanceParameter[] instanceParameters) {
        this.drawElementsIndirectCommand = new DrawElementsIndirectCommand(0, instanceCount, 0, 0, 0);
        this.instanceParameters = instanceParameters;

        if (instanceParameters.length != instanceCount) {
            throw new RuntimeException("The sizes of InstanceCount and InstanceParameters do not match. InstanceCount: " + instanceCount + " != InstanceParameters: " + instanceParameters.length);
        }
    }

    public void resetInstanceParameterFlag() {
        instanceParameterFlag = false;
    }

    public boolean isInstanceParameterFlag() {
        return instanceParameterFlag;
    }

    public InstanceParameter[] getInstanceParameters() {
        instanceParameterFlag = true;
        return instanceParameters;
    }
}
