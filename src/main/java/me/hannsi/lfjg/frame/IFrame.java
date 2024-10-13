package me.hannsi.lfjg.frame;

import me.hannsi.lfjg.frame.manager.managers.EventManager;
import me.hannsi.lfjg.frame.manager.managers.LoggerManager;

public interface IFrame {
    EventManager eventManager = new EventManager();
    LoggerManager loggerManager = new LoggerManager();
}
