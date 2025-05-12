// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.callback;

class CefJSDialogCallback_N extends CefNativeAdapter implements CefJSDialogCallback, AutoCloseable {
    CefJSDialogCallback_N() {
    }

    @Override
    public void close() throws Exception {
        Continue(false, "");
    }

    @Override
    public void Continue(boolean success, String user_input) {
        try {
            N_Continue(getNativeRef(null), success, user_input);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    private native void N_Continue(long self, boolean success, String user_input);
}
