package me.hannsi.lfjg.nativeAccess;

import me.hannsi.lfjg.nativeAccess.structure.OPENFILENAME;

public class Comdlg32 {
    public static boolean getOpenFileNameA(OPENFILENAME ofn) {
        return IComdlg32.INSTANCE.GetOpenFileNameA(ofn);
    }

    public static boolean getOpenFileNameW(OPENFILENAME ofn) {
        return IComdlg32.INSTANCE.GetOpenFileNameW(ofn);
    }


    public static int commDlgExtendedError() {
        return IComdlg32.INSTANCE.CommDlgExtendedError();
    }
}
