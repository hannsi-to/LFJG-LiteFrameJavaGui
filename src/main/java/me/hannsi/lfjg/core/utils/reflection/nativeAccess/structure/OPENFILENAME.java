package me.hannsi.lfjg.core.utils.reflection.nativeAccess.structure;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.debug.DebugLevel;

import java.util.Arrays;
import java.util.List;

public class OPENFILENAME extends Structure {
    public static final int OFN_ALLOWMULTISELECT = 0x00000200;
    public static final int OFN_CREATEPROMPT = 0x00002000;
    public static final int OFN_DONTADDTORECENT = 0x02000000;
    public static final int OFN_ENABLEHOOK = 0x00000020;
    public static final int OFN_ENABLEINCLUDENOTIFY = 0x00400000;
    public static final int OFN_ENABLESIZING = 0x00800000;
    public static final int OFN_ENABLETEMPLATE = 0x00000040;
    public static final int OFN_ENABLETEMPLATEHANDLE = 0x00000080;
    public static final int OFN_EXPLORER = 0x00080000;
    public static final int OFN_EXTENSIONDIFFERENT = 0x00000400;
    public static final int OFN_FILEMUSTEXIST = 0x00001000;
    public static final int OFN_FORCESHOWHIDDEN = 0x10000000;
    public static final int OFN_HIDEREADONLY = 0x00000004;
    public static final int OFN_LONGNAMES = 0x00200000;
    public static final int OFN_NOCHANGEDIR = 0x00000008;
    public static final int OFN_NODEREFERENCELINKS = 0x00100000;
    public static final int OFN_NOLONGNAMES = 0x00040000;
    public static final int OFN_NONETWORKBUTTON = 0x00020000;
    public static final int OFN_NOREADONLYRETURN = 0x00008000;
    public static final int OFN_NOTESTFILECREATE = 0x00010000;
    public static final int OFN_NOVALIDATE = 0x00000100;
    public static final int OFN_OVERWRITEPROMPT = 0x00000002;
    public static final int OFN_PATHMUSTEXIST = 0x00000800;
    public static final int OFN_READONLY = 0x00000001;
    public static final int OFN_SHAREAWARE = 0x00004000;
    public static final int OFN_SHOWHELP = 0x00000010;

    public static final int OFN_EX_NOPLACESBAR = 0x00000001;

    public int lStructSize;
    public Pointer hwndOwner;
    public Pointer hInstance;
    public WString lpstrFilter;
    public WString lpstrCustomFilter;
    public int nMaxCustFilter;
    public int nFilterIndex;
    public Pointer lpstrFile;
    public int nMaxFile;
    public WString lpstrFileTitle;
    public int nMaxFileTitle;
    public WString lpstrInitialDir;
    public WString lpstrTitle;
    public int Flags;
    public short nFileOffset;
    public short nFileExtension;
    public WString lpstrDefExt;
    public Pointer lCustData;
    public Pointer lpfnHook;
    public WString lpTemplateName;
    public Pointer pvReserved;
    public int dwReserved;
    public int FlagsEx;

    public OPENFILENAME() {
        this.lStructSize = size();

        new LogGenerator(
                "Create OPENFILENAME Structure",
                "Source: OPENFILENAME",
                "Type: Create structure",
                "Severity: info",
                "Message: Create OPENFILENAME structure (lStructureSize=" + lStructSize + ")"
        ).logging(DebugLevel.INFO);
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("lStructSize", "hwndOwner", "hInstance", "lpstrFilter", "lpstrCustomFilter", "nMaxCustFilter", "nFilterIndex", "lpstrFile", "nMaxFile", "lpstrFileTitle", "nMaxFileTitle", "lpstrInitialDir", "lpstrTitle", "Flags", "nFileOffset", "nFileExtension", "lpstrDefExt", "lCustData", "lpfnHook", "lpTemplateName", "pvReserved", "dwReserved", "FlagsEx");
    }
}