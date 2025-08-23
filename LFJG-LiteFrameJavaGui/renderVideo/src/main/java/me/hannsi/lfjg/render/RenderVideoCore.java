package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.Service;
import me.hannsi.lfjg.core.ServiceData;

public class RenderVideoCore implements Service {
    @Override
    public ServiceData execute() {
        return new ServiceData(true, "RenderVideo", "v1.0.0");
    }
}
