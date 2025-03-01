package me.hannsi.lfjg.utils.reflection;

import java.net.MalformedURLException;
import java.net.URL;

public class URLLocation extends Location {
    public URLLocation(String path) {
        super(path, true, false);
    }

    public URL getURL() {
        try {
            return new URL(path);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
