package me.hannsi.lfjg.utils.reflection.nativeAccess;

import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.debug.DebugLevel;

public class User32 {
    public static final int MB_ABORTRETRYIGNORE = 0x00000002;
    public static final int MB_CANCELTRYCONTINUE = 0x00000006;
    public static final int MB_HELP = 0x00004000;
    public static final int MB_OK = 0x00000000;
    public static final int MB_OKCANCEL = 0x00000001;
    public static final int MB_RETRYCANCEL = 0x00000005;
    public static final int MB_YESNO = 0x00000004;
    public static final int MB_YESNOCANCEL = 0x00000003;
    public static final int MB_ICONEXCLAMATION = 0x00000030;
    public static final int MB_ICONWARNING = 0x00000030;
    public static final int MB_ICONINFORMATION = 0x00000040;
    public static final int MB_ICONASTERISK = 0x00000040;
    public static final int MB_ICONQUESTION = 0x00000020;
    public static final int MB_ICONSTOP = 0x00000010;
    public static final int MB_ICONERROR = 0x00000010;
    public static final int MB_ICONHAND = 0x00000010;
    public static final int MB_DEFBUTTON1 = 0x00000000;
    public static final int MB_DEFBUTTON2 = 0x00000100;
    public static final int MB_DEFBUTTON3 = 0x00000200;
    public static final int MB_DEFBUTTON4 = 0x00000300;
    public static final int MB_APPLMODAL = 0x00000000;
    public static final int MB_SYSTEMMODAL = 0x00001000;
    public static final int MB_TASKMODAL = 0x00002000;
    public static final int MB_DEFAULT_DESKTOP_ONLY = 0x00020000;
    public static final int MB_RIGHT = 0x00080000;
    public static final int MB_RTLREADING = 0x00100000;
    public static final int MB_SETFOREGROUND = 0x00010000;
    public static final int MB_TOPMOST = 0x00040000;
    public static final int MB_SERVICE_NOTIFICATION = 0x00200000;
    public static final int IDABORT = 3;
    public static final int IDCANCEL = 2;
    public static final int IDCONTINUE = 11;
    public static final int IDIGNORE = 5;
    public static final int IDNO = 7;
    public static final int IDOK = 1;
    public static final int IDRETRY = 4;
    public static final int IDTRYAGAIN = 10;
    public static final int IDYES = 6;

    static {
        new LogGenerator(
                "Java Native",
                "Source: User32",
                "Type: Native access",
                "Severity: Info",
                "Message: Access to native library for User32.dll"
        ).logging(DebugLevel.INFO);
    }

    public static int messageBox(int hWnd, String text, String caption, int type) {
        return IUser32.INSTANCE.MessageBoxA(hWnd, text, caption, type);
    }

    public static int messageBox(long hWnd, String text, String caption, int type) {
        return IUser32.INSTANCE.MessageBoxA(hWnd, text, caption, type);
    }

    public static boolean messageBeep(int uType) {
        return IUser32.INSTANCE.MessageBeep(uType);
    }
}
