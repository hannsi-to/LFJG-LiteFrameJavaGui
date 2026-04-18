package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.Service;
import me.hannsi.lfjg.core.ServiceData;

public class RenderCore implements Service {
    @Override
    public ServiceData execute() {
        return new ServiceData(true, "Render", "v1.0.0");
    }
}
