package me.hannsi.lfjg.render.openGL.gui.system.item;

import me.hannsi.lfjg.utils.toolkit.KeyboardInfo;
import me.hannsi.lfjg.utils.toolkit.MouseInfo;

public interface IItem {
    void init();

    void render(MouseInfo mouseInfo, KeyboardInfo keyboardInfo);

    void cleanup();
}
