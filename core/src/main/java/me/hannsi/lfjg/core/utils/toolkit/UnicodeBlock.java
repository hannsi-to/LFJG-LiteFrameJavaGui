package me.hannsi.lfjg.core.utils.toolkit;

import java.util.Objects;

public final class UnicodeBlock {
    private final String name;
    private final int startCodePoint;
    private final int endCodePoint;

    public UnicodeBlock(String name, int startCodePoint, int endCodePoint) {
        this.name = name;
        this.startCodePoint = startCodePoint;
        this.endCodePoint = endCodePoint;
    }

    public boolean contains(int codePoint) {
        return codePoint >= startCodePoint && codePoint <= endCodePoint;
    }

    @Override
    public String toString() {
        return String.format("%s: U+%04X - U+%04X", name, startCodePoint, endCodePoint);
    }

    public String name() {
        return name;
    }

    public int startCodePoint() {
        return startCodePoint;
    }

    public int endCodePoint() {
        return endCodePoint;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        UnicodeBlock that = (UnicodeBlock) obj;
        return Objects.equals(this.name, that.name) &&
                this.startCodePoint == that.startCodePoint &&
                this.endCodePoint == that.endCodePoint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startCodePoint, endCodePoint);
    }

}
