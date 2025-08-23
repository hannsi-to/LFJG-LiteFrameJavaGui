// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.callback;

class CefDownloadItemCallback_N extends CefNativeAdapter implements CefDownloadItemCallback, AutoCloseable {
    CefDownloadItemCallback_N() {
    }

    @Override
    public void close() throws Exception {
        try {
            N_Dispose(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public void cancel() {
        try {
            N_Cancel(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public void pause() {
        try {
            N_Pause(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public void resume() {
        try {
            N_Resume(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    private native void N_Dispose(long self);

    private native void N_Cancel(long self);

    private native void N_Pause(long self);

    private native void N_Resume(long self);
}
