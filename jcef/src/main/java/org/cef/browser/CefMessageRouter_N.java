// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.browser;

import org.cef.callback.CefNative;
import org.cef.handler.CefMessageRouterHandler;

class CefMessageRouter_N extends CefMessageRouter implements CefNative {
    // Used internally to store a pointer to the CEF object.
    private long N_CefHandle = 0;

    private CefMessageRouter_N() {
        super();
    }

    public static CefMessageRouter createNative(CefMessageRouterConfig config) {
        try {
            return CefMessageRouter_N.N_Create(config);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return null;
        }
    }

    private native static CefMessageRouter_N N_Create(CefMessageRouterConfig config);

    @Override
    public void setNativeRef(String identifer, long nativeRef) {
        N_CefHandle = nativeRef;
    }

    @Override
    public long getNativeRef(String identifer) {
        return N_CefHandle;
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
    public boolean addHandler(CefMessageRouterHandler handler, boolean first) {
        try {
            return N_AddHandler(N_CefHandle, handler, first);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeHandler(CefMessageRouterHandler handler) {
        try {
            return N_RemoveHandler(N_CefHandle, handler);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
            return false;
        }
    }

    @Override
    public void cancelPending(CefBrowser browser, CefMessageRouterHandler handler) {
        try {
            N_CancelPending(N_CefHandle, browser, handler);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    private native void N_Dispose(long self);

    private native boolean N_AddHandler(long self, CefMessageRouterHandler handler, boolean first);

    private native boolean N_RemoveHandler(long self, CefMessageRouterHandler handler);

    private native void N_CancelPending(long self, CefBrowser browser, CefMessageRouterHandler handler);
}
