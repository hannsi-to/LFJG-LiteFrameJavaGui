// Copyright (c) 2024 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package org.cef.browser;

import lombok.Getter;

import java.awt.*;
import java.nio.ByteBuffer;

public class CefPaintEvent {
    @Getter
    private final CefBrowser browser;
    private final boolean popup;
    @Getter
    private final Rectangle[] dirtyRects;
    @Getter
    private final ByteBuffer renderedFrame;
    @Getter
    private final int width;
    @Getter
    private final int height;

    public CefPaintEvent(CefBrowser browser, boolean popup, Rectangle[] dirtyRects, ByteBuffer renderedFrame, int width, int height) {
        this.browser = browser;
        this.popup = popup;
        this.dirtyRects = dirtyRects;
        this.renderedFrame = renderedFrame;
        this.width = width;
        this.height = height;
    }

    public boolean getPopup() {
        return popup;
    }

}