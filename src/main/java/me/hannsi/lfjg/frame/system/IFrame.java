package me.hannsi.lfjg.frame.system;

import me.hannsi.lfjg.core.manager.EventManager;
import me.hannsi.lfjg.core.manager.LoggerManager;
import me.hannsi.lfjg.core.manager.WorkspaceManager;
import me.hannsi.lfjg.frame.setting.system.FrameSettingManager;

public interface IFrame {
    EventManager eventManager = new EventManager();
    LoggerManager loggerManager = new LoggerManager();
    FrameSettingManager frameSettingManager = new FrameSettingManager();
    WorkspaceManager workspaceManager = new WorkspaceManager();
}
