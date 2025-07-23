package me.hannsi.lfjg.core.utils.reflection.nativeAccess;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public interface IUser32 extends StdCallLibrary {
    IUser32 INSTANCE = Native.load("user32", IUser32.class);

    int MessageBoxA(int hWnd, String text, String caption, int type);

    int MessageBoxA(long hWnd, String text, String caption, int type);

    boolean MessageBeep(int uType);
}
