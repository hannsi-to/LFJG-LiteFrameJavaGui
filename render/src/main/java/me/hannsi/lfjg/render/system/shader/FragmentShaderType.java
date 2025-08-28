package me.hannsi.lfjg.render.system.shader;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum FragmentShaderType implements IEnumTypeBase {
    OBJECT(0,"Object"),
    MODEL(1,"Model"),
    FRAME_BUFFER(2,"FrameBuffer"),
    MSDF(3,"MSDF"),
    BLOOM(4,"Bloom"),
    BOX_BLUR(5,"BoxBlur");

    final int id;
    final String name;

    FragmentShaderType(int id, String name) {
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
