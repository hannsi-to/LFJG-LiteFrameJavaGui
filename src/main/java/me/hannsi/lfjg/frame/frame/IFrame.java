package me.hannsi.lfjg.frame.frame;

import me.hannsi.lfjg.frame.manager.EventManager;
import me.hannsi.lfjg.frame.manager.FrameSettingManager;
import me.hannsi.lfjg.frame.manager.LoggerManager;
import me.hannsi.lfjg.frame.manager.WorkspaceManager;

public interface IFrame {
    EventManager eventManager = new EventManager();
    LoggerManager loggerManager = new LoggerManager();
    FrameSettingManager frameSettingManager = new FrameSettingManager();
    WorkspaceManager workspaceManager = new WorkspaceManager();
}
