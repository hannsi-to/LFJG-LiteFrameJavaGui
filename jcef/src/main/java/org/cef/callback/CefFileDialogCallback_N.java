// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.callback;

import java.util.Vector;

class CefFileDialogCallback_N extends CefNativeAdapter implements CefFileDialogCallback, AutoCloseable {
    CefFileDialogCallback_N() {
    }

    @Override
    public void close() throws Exception {
        Cancel();
    }

    @Override
    public void Continue(Vector<String> filePaths) {
        try {
            N_Continue(getNativeRef(null), filePaths);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    @Override
    public void Cancel() {
        try {
            N_Cancel(getNativeRef(null));
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    private native void N_Continue(long self, Vector<String> filePaths);

    private native void N_Cancel(long self);
}
