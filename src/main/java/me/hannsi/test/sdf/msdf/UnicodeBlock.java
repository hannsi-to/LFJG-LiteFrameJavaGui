package me.hannsi.test.sdf.msdf;

public class UnicodeBlock {
    public final String name;
    public final int startCodePoint;
    public final int endCodePoint;

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
}
