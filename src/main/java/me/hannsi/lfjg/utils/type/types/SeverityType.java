package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;
import org.lwjgl.opengl.GL43;

public enum SeverityType implements IEnumTypeBase {
    Notification("Low", GL43.GL_DEBUG_SEVERITY_NOTIFICATION), Low("Low", GL43.GL_DEBUG_SEVERITY_LOW), Medium("Medium", GL43.GL_DEBUG_SEVERITY_MEDIUM), High("High", GL43.GL_DEBUG_SEVERITY_HIGH);

    final String name;
    final int id;

    SeverityType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }
}
