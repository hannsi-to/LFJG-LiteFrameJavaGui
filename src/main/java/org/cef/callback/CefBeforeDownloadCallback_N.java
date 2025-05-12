// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.callback;

class CefBeforeDownloadCallback_N extends CefNativeAdapter implements CefBeforeDownloadCallback, AutoCloseable {
    CefBeforeDownloadCallback_N() {
    }

    @Override
    public void close() throws Exception {
        Continue("", false);
    }

    @Override
    public void Continue(String downloadPath, boolean showDialog) {
        try {
            N_Continue(getNativeRef(null), downloadPath, showDialog);
        } catch (UnsatisfiedLinkError ule) {
            ule.printStackTrace();
        }
    }

    private native void N_Continue(long self, String downloadPath, boolean showDialog);
}
