package me.hannsi.lfjg.render.system.buffer.l1;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static org.lwjgl.opengl.GL15.*;

public enum BufferObjectDrawUsageType implements IEnumTypeBase {
    STATIC(GL_STATIC_DRAW, "Static"),
    DYNAMIC(GL_DYNAMIC_DRAW, "Dynamic"),
    STREAM(GL_STREAM_DRAW, "Stream");

    final int id;
    final String name;

    BufferObjectDrawUsageType(int id, String name) {
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
