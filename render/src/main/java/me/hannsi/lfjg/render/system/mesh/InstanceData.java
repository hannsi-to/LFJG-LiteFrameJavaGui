package me.hannsi.lfjg.render.system.mesh;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import org.joml.Matrix4d;
import org.joml.Matrix4f;

import java.util.Arrays;

public class InstanceData {
    public final int instanceCount;
    public Matrix4f[] instanceModels;
    public Color[] instanceColors;

    public InstanceData(int instanceCount,Color defaultColor){
        this.instanceCount = instanceCount;
        this.instanceColors = new Color[instanceCount];

        Arrays.fill(instanceColors, defaultColor);
    }

    public InstanceData(int instanceCount, Matrix4f[] instanceModels, Color[] instanceColors) {
        this.instanceCount = instanceCount;
        this.instanceModels = instanceModels;
        this.instanceColors = instanceColors;

        if(instanceModels.length != instanceCount){
            throw new RuntimeException("The sizes of InstanceCount and InstanceModels do not match. InstanceCount: " + instanceCount + " != InstanceModels: " + instanceModels.length);
        }

        if(instanceColors.length != instanceCount){
            throw new RuntimeException("The sizes of InstanceCount and InstanceColors do not match. InstanceCount: " +instanceCount + " != InstanceColors: " + instanceColors.length);
        }
    }
}
