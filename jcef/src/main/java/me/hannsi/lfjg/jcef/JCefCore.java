package me.hannsi.lfjg.jcef;

import me.hannsi.lfjg.core.Service;
import me.hannsi.lfjg.core.ServiceData;

public class JCefCore implements Service {
    @Override
    public ServiceData execute() {
        return new ServiceData(false, "JCef", "v1.0.0");
    }
}
