package me.hannsi.lfjg.utils.reflection.location;

import lombok.Getter;

public class Location {
    @Getter
    public String path;
    public boolean isUrl;
    public boolean isPath;

    public Location(String path, boolean isUrl, boolean isPath) {
        this.path = path;
        this.isUrl = isUrl;
        this.isPath = isPath;
    }

    @Override
    public String toString() {
        return path;
    }

    public void cleanup() {
        this.path = "";
    }

    public boolean isUrl() {
        return isUrl;
    }

    public void setUrl(boolean url) {
        isUrl = url;
    }

    public boolean isPath() {
        return isPath;
    }

    public void setPath(boolean path) {
        isPath = path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
