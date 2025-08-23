package me.hannsi.lfjg.frame.system;

import me.hannsi.lfjg.core.manager.EventManager;
import me.hannsi.lfjg.core.manager.WorkspaceManager;
import me.hannsi.lfjg.frame.setting.system.FrameSettingManager;

public interface IFrame {
    EventManager eventManager = new EventManager();
    FrameSettingManager frameSettingManager = new FrameSettingManager();
    WorkspaceManager workspaceManager = new WorkspaceManager();
}
