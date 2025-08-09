package me.hannsi.lfjg.core.utils.toolkit;

public record UnicodeBlock(String name, int startCodePoint, int endCodePoint) {
    public boolean contains(int codePoint) {
        return codePoint >= startCodePoint && codePoint <= endCodePoint;
    }

    @Override
    public String toString() {
        return String.format("%s: U+%04X - U+%04X", name, startCodePoint, endCodePoint);
    }
}
