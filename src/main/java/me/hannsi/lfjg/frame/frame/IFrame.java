package me.hannsi.lfjg.frame.frame;

import me.hannsi.lfjg.frame.manager.managers.EventManager;
import me.hannsi.lfjg.frame.manager.managers.LoggerManager;

/**
 * The IFrame interface provides access to the EventManager and LoggerManager instances.
 */
public interface IFrame {
    EventManager eventManager = new EventManager();
    LoggerManager loggerManager = new LoggerManager();
}
