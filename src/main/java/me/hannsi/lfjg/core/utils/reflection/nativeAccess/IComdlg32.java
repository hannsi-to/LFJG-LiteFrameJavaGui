package me.hannsi.lfjg.core.utils.reflection.nativeAccess;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import me.hannsi.lfjg.core.utils.reflection.nativeAccess.structure.OPENFILENAME;

public interface IComdlg32 extends StdCallLibrary {
    IComdlg32 INSTANCE = Native.load("comdlg32", IComdlg32.class);

    boolean GetOpenFileNameA(OPENFILENAME ofn);

    boolean GetOpenFileNameW(OPENFILENAME ofn);

    boolean GetSaveFileNameA(OPENFILENAME ofn);

    boolean GetSaveFileNameW(OPENFILENAME ofn);

    int CommDlgExtendedError();
}
