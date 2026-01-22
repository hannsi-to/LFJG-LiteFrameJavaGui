package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.event.events.CleanupEvent;
import me.hannsi.lfjg.core.utils.Cleanup;
import me.hannsi.lfjg.render.renderers.InstanceParameter;

import java.util.Arrays;

public class ObjectData implements Cleanup {
    public static final long BYTES = 4 * Integer.BYTES;
    public DrawElementsIndirectCommand drawElementsIndirectCommand;
    private final InstanceParameter[] instanceParameters;

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

    public InstanceParameter[] getInstanceParameters() {
        return instanceParameters;
    }

    @Override
    public boolean cleanup(CleanupEvent event) {
        boolean instanceParametersState = true;
        for (InstanceParameter instanceParameter : instanceParameters) {
            if (!instanceParameter.cleanup(event)) {
                instanceParametersState = false;
            }
        }

        return event.debug(this.getClass(), new CleanupEvent.CleanupData(this.getClass().getSimpleName())
                .addData("drawElementsIndirectCommand", drawElementsIndirectCommand.cleanup(event), drawElementsIndirectCommand)
                .addData("instanceParameters", instanceParametersState, instanceParameters)
        );
    }
}
