package me.hannsi.lfjg.render.gui.system.item;

import me.hannsi.lfjg.core.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.core.utils.toolkit.MouseInfo;

public interface IItem {
    void init();

    void render(MouseInfo mouseInfo, KeyboardInfo keyboardInfo);

    void cleanup();
}
