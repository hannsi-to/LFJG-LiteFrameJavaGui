package me.hannsi.lfjg.core.utils.toolkit;

import me.hannsi.lfjg.core.utils.Util;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;

/**
 * Utility class for interacting with the AWT Toolkit.
 */
public class ToolkitUtil extends Util {

    /**
     * Gets the default toolkit.
     *
     * @return the default toolkit
     */
    public static Toolkit getDefaultToolkit() {
        return Toolkit.getDefaultToolkit();
    }

    /**
     * Gets the property value associated with the specified key.
     *
     * @param key          the key of the property
     * @param defaultValue the default value to return if the property is not found
     * @return the property value associated with the specified key, or the default value if the property is not found
     */
    public static String get(String key, String defaultValue) {
        return Toolkit.getProperty(key, defaultValue);
    }

    public static String getClipboardText() throws IOException, UnsupportedFlavorException {
        Clipboard clipboard = getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String) contents.getTransferData(DataFlavor.stringFlavor);
        }

        return null;
    }

    public static void setClipboardText(String text) {
        Clipboard clipboard = getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, null);
    }

    public static void beep() {
        getDefaultToolkit().beep();
    }

    public static Cursor createCustomCursor(Image image, Point hotspot, String name) {
        return getDefaultToolkit().createCustomCursor(image, hotspot, name);
    }

    public static Image loadImage(String path) {
        return getDefaultToolkit().getImage(path);
    }

    public static void postEvent(AWTEvent event) {
        getDefaultToolkit().getSystemEventQueue().postEvent(event);
    }

    public static Icon getSystemIcon(File file) {
        return FileSystemView.getFileSystemView().getSystemIcon(file);
    }
}