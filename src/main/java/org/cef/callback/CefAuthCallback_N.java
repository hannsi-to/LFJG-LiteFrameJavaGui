// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.callback;

class CefAuthCallback_N extends CefNativeAdapter implements CefAuthCallback, AutoCloseable {
    CefAuthCallback_N() {
    }

    @Override
    public void close() throws Exception {
        cancel();
    }

    @Override
    public void Continue(String username, String password) {
        try {
            N_Continue(getNativeRef(null), username, password);
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

    private native void N_Continue(long self, String username, String password);

    private native void N_Cancel(long self);
}
