package me.hannsi.lfjg.utils.reflection.nativeAccess;

import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.utils.reflection.nativeAccess.structure.OPENFILENAME;

public class Comdlg32 {
    public static final int CDERR_DIALOGFAILURE = 0xFFFF;
    public static final int CDERR_STRUCTSIZE = 0x0001;
    public static final int CDERR_INITIALIZATION = 0x0002;
    public static final int CDERR_NOTEMPLATE = 0x0003;
    public static final int CDERR_NOHINSTANCE = 0x0004;
    public static final int CDERR_LOADSTRFAILURE = 0x0005;
    public static final int CDERR_FINDRESFAILURE = 0x0006;
    public static final int CDERR_LOADRESFAILURE = 0x0007;
    public static final int CDERR_LOCKRESFAILURE = 0x0008;
    public static final int CDERR_MEMALLOCFAILURE = 0x0009;
    public static final int CDERR_MEMLOCKFAILURE = 0x000A;
    public static final int CDERR_NOHOOK = 0x000B;
    public static final int CDERR_REGISTERMSGFAIL = 0x000C;
    public static final int PDERR_SETUPFAILURE = 0x1001;
    public static final int PDERR_PARSEFAILURE = 0x1002;
    public static final int PDERR_RETDEFFAILURE = 0x1003;
    public static final int PDERR_LOADDRVFAILURE = 0x1004;
    public static final int PDERR_GETDEVMODEFAIL = 0x1005;
    public static final int PDERR_INITFAILURE = 0x1006;
    public static final int PDERR_NODEVICES = 0x1007;
    public static final int PDERR_NODEFAULTPRN = 0x1008;
    public static final int PDERR_DNDMMISMATCH = 0x1009;
    public static final int PDERR_CREATEICFAILURE = 0x100A;
    public static final int PDERR_PRINTERNOTFOUND = 0x100B;
    public static final int PDERR_DEFAULTDIFFERENT = 0x100C;
    public static final int CFERR_NOFONTS = 0x2001;
    public static final int CFERR_MAXLESSTHANMIN = 0x2002;
    public static final int FNERR_SUBCLASSFAILURE = 0x3001;
    public static final int FNERR_INVALIDFILENAME = 0x3002;
    public static final int FNERR_BUFFERTOOSMALL = 0x3003;
    public static final int FRERR_BUFFERLENGTHZERO = 0x4001;

    static {
        new LogGenerator("Java Native", "Source: Comdlg32", "Type: Native access", "Severity: Info", "Message: Access to native library for Comdlg32.dll").logging(DebugLevel.INFO);
    }

    public static boolean getOpenFileNameA(OPENFILENAME ofn) {
        return IComdlg32.INSTANCE.GetOpenFileNameA(ofn);
    }

    public static boolean getOpenFileNameW(OPENFILENAME ofn) {
        return IComdlg32.INSTANCE.GetOpenFileNameW(ofn);
    }

    public static boolean getSaveFileNameA(OPENFILENAME ofn) {
        return IComdlg32.INSTANCE.GetSaveFileNameA(ofn);
    }

    public static boolean getSaveFileNameW(OPENFILENAME ofn) {
        return IComdlg32.INSTANCE.GetSaveFileNameW(ofn);
    }

    public static int commDlgExtendedError() {
        return IComdlg32.INSTANCE.CommDlgExtendedError();
    }

    public static String getCommDlgExtendedError(int errorCode) {
        return switch (errorCode) {
            case CDERR_DIALOGFAILURE ->
                    "Could not create dialog box. DialogBox function call failed. For example, this error occurs when an invalid window handle is specified in a generic dialog box call.";
            case CDERR_FINDRESFAILURE -> "Common dialog box functions could not find the specified resource.";
            case CDERR_INITIALIZATION ->
                    "A common dialog box function failed during initialization. This error often occurs when not enough memory is available.";
            case CDERR_LOADRESFAILURE -> "Common dialog box functions could not load the specified resource.";
            case CDERR_LOADSTRFAILURE -> "Common dialog box functions could not read the specified string.";
            case CDERR_LOCKRESFAILURE -> "Common dialog box functions could not lock the specified resource.";
            case CDERR_MEMALLOCFAILURE ->
                    "Common dialog box functions could not allocate memory for internal structures.";
            case CDERR_MEMLOCKFAILURE ->
                    "Common dialog box functions could not lock the memory associated with a handle.";
            case CDERR_NOHINSTANCE ->
                    "The ENABLETEMPLATE flag was set in the Flags member of the corresponding common dialog box initialization structure, but the corresponding instance handle could not be specified.";
            case CDERR_NOHOOK ->
                    "The ENABLEHOOK flag was set in the Flags member of the corresponding common dialog box initialization structure, but a pointer to the corresponding hook procedure could not be specified.";
            case CDERR_NOTEMPLATE ->
                    "The ENABLETEMPLATE flag was set in the Flags member of the corresponding common dialog box initialization structure, but the corresponding template could not be specified.";
            case CDERR_REGISTERMSGFAIL ->
                    "The RegisterWindowMessage function returned an error code when called by a common dialog box function.";
            case CDERR_STRUCTSIZE ->
                    "The lStructSize member of the corresponding common dialog box initialization structure is invalid.";
            case PDERR_CREATEICFAILURE ->
                    "The PrintDlg function failed when attempting to create an information context.";
            case PDERR_DEFAULTDIFFERENT ->
                    "You called the PrintDlg function using the DN_DEFAULTPRN flag specified in the wDefault member of the DEVNAMES structure, but the printer described by the other structure members did not match the current default printer. This error occurs when the DEVNAMES structure is stored and the user has changed the default printer using the control panel. To use the printer described in the DEVNAMES structure, clear the DN_DEFAULTPRN flag and call PrintDlg again. To use the default printer, replace the DEVNAMES structure (and structure if present) with NULL. Call PrintDlg again.";
            case PDERR_DNDMMISMATCH ->
                    "The data in the DEVMODE and DEVNAMES structures describe two different printers.";
            case PDERR_GETDEVMODEFAIL -> "The printer driver could not initialize the DEVMODE structure.";
            case PDERR_INITFAILURE ->
                    "The PrintDlg function failed during initialization and there is no further extended error code to describe the error. This is the general default error code for the function.";
            case PDERR_LOADDRVFAILURE ->
                    "The PrintDlg function could not load the device driver for the specified printer.";
            case PDERR_NODEFAULTPRN -> "Default printer does not exist.";
            case PDERR_NODEVICES -> "Printer driver not found.";
            case PDERR_PARSEFAILURE ->
                    "The PrintDlg function could not parse the string in the [devices] section of the WIN.INI file.";
            case PDERR_PRINTERNOTFOUND ->
                    "The [devices] section of the WIN.INI file did not contain an entry for the requested printer.";
            case PDERR_RETDEFFAILURE ->
                    "The PD_RETURNDEFAULT flag was specified in the Flags member of the PRINTDLG structure, but the hDevMode or hDevNames member is not null.";
            case PDERR_SETUPFAILURE -> "The PrintDlg function could not read the required resource.";
            case CFERR_MAXLESSTHANMIN ->
                    "The size specified by the nSizeMax member of the CHOOSEFONT structure is smaller than the size specified by the nSizeMin member.";
            case CFERR_NOFONTS -> "The font does not exist.";
            case FNERR_BUFFERTOOSMALL ->
                    "The buffer pointed to by the lpstrFile member of the OPENFILENAME structure is too small for the user-specified file name. The first 2 bytes of the lpstrFile buffer contain an integer value that specifies the size, in characters, required to receive the complete name.";
            case FNERR_INVALIDFILENAME -> "Invalid file name.";
            case FNERR_SUBCLASSFAILURE ->
                    "The list box could not be subclassed because not enough memory was available.";
            case FRERR_BUFFERLENGTHZERO -> "A member of the FINDREPLACE structure points to an invalid buffer.";
            default -> "";
        };
    }
}
