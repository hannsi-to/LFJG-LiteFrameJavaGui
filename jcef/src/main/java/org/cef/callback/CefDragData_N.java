package org.cef.callback;

import java.io.OutputStream;
import java.util.Vector;

class CefDragData_N extends CefDragData implements CefNative {
    // Used internally to store a pointer to the CEF object.
    private long N_CefHandle = 0;

    CefDragData_N() {
        super();
    }

    public static CefDragData createNative() {
        try {
            return CefDragData_N.N_Create();
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return null;
        }
    }

    private native static CefDragData_N N_Create();

    @Override
    public void setNativeRef(String identifer, long nativeRef) {
        N_CefHandle = nativeRef;
    }

    @Override
    public long getNativeRef(String identifer) {
        return N_CefHandle;
    }

    @Override
    public CefDragData clone() {
        try {
            return N_Clone(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return null;
        }
    }

    @Override
    public void dispose() {
        try {
            N_Dispose(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public boolean isReadOnly() {
        try {
            return N_IsReadOnly(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return true;
        }
    }

    @Override
    public boolean isLink() {
        try {
            return N_IsLink(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isFragment() {
        try {
            return N_IsFragment(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isFile() {
        try {
            return N_IsFile(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    @Override
    public String getLinkURL() {
        try {
            return N_GetLinkURL(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    public void setLinkURL(String url) {
        try {
            N_SetLinkURL(N_CefHandle, url);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public String getLinkTitle() {
        try {
            return N_GetLinkTitle(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    public void setLinkTitle(String title) {
        try {
            N_SetLinkTitle(N_CefHandle, title);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public String getLinkMetadata() {
        try {
            return N_GetLinkMetadata(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    public void setLinkMetadata(String data) {
        try {
            N_SetLinkMetadata(N_CefHandle, data);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public String getFragmentText() {
        try {
            return N_GetFragmentText(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    public void setFragmentText(String text) {
        try {
            N_SetFragmentText(N_CefHandle, text);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public String getFragmentHtml() {
        try {
            return N_GetFragmentHtml(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    public void setFragmentHtml(String html) {
        try {
            N_SetFragmentHtml(N_CefHandle, html);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public String getFragmentBaseURL() {
        try {
            return N_GetFragmentBaseURL(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    public void setFragmentBaseURL(String baseUrl) {
        try {
            N_SetFragmentBaseURL(N_CefHandle, baseUrl);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public int getFileContents(OutputStream writer) {
        try {
            return N_GetFileContents(N_CefHandle, writer);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getFileName() {
        try {
            return N_GetFileName(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean getFileNames(Vector<String> names) {
        try {
            return N_GetFileNames(N_CefHandle, names);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
        return false;
    }

    public void resetFileContents() {
        try {
            N_ResetFileContents(N_CefHandle);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    public void addFile(String path, String displayName) {
        try {
            N_AddFile(N_CefHandle, path, displayName);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    private native CefDragData_N N_Clone(long self);

    private native void N_Dispose(long self);

    private native boolean N_IsReadOnly(long self);

    private native boolean N_IsLink(long self);

    private native boolean N_IsFragment(long self);

    private native boolean N_IsFile(long self);

    private native String N_GetLinkURL(long self);

    private native String N_GetLinkTitle(long self);

    private native String N_GetLinkMetadata(long self);

    private native String N_GetFragmentText(long self);

    private native String N_GetFragmentHtml(long self);

    private native String N_GetFragmentBaseURL(long self);

    private native int N_GetFileContents(long self, OutputStream writer);

    private native String N_GetFileName(long self);

    private native boolean N_GetFileNames(long self, Vector<String> names);

    private native void N_SetLinkURL(long self, String url);

    private native void N_SetLinkTitle(long self, String title);

    private native void N_SetLinkMetadata(long self, String data);

    private native void N_SetFragmentText(long self, String text);

    private native void N_SetFragmentHtml(long self, String html);

    private native void N_SetFragmentBaseURL(long self, String baseUrl);

    private native void N_ResetFileContents(long self);

    private native void N_AddFile(long self, String path, String displayName);

    @Override
    public String toString() {
        Vector<String> names = new Vector<>();
        getFileNames(names);
        StringBuilder fileNamesStr = new StringBuilder("{");
        for (String s : names) {
            fileNamesStr.append(s).append(",");
        }
        fileNamesStr.append("}");

        return "CefDragData_N [isLink()=" + isLink() + ", isFragment()=" + isFragment() + ", isFile()=" + isFile() + ", getLinkURL()=" + getLinkURL() + ", getLinkTitle()=" + getLinkTitle() + ", getLinkMetadata()=" + getLinkMetadata() + ", getFragmentText()=" + getFragmentText() + ", getFragmentHtml()=" + getFragmentHtml() + ", getFragmentBaseURL()=" + getFragmentBaseURL() + ", getFileName()=" + getFileName() + ", getFileNames(vector)=" + fileNamesStr + "]";
    }
}
