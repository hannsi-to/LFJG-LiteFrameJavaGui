package me.hannsi.lfjg.core.utils.toolkit;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class FastStringBuilder {
    public static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    private byte[] buffer;
    private Charset charset;
    private int count;
    private int counter;

    public FastStringBuilder(byte[] buffer, Charset charset) {
        this.buffer = buffer;
        this.charset = charset;
        this.count = 0;
    }

    public FastStringBuilder(int capacity, Charset charset) {
        this(new byte[Math.max(8, capacity)], charset);
    }

    public FastStringBuilder(Object o, Charset charset) {
        this(o.toString(), charset);
    }

    public FastStringBuilder(Object o) {
        this(o.toString().getBytes(DEFAULT_CHARSET), DEFAULT_CHARSET);
    }

    public FastStringBuilder(String s, Charset charset) {
        this(s.getBytes(charset), charset);
    }

    public FastStringBuilder(String s) {
        this(s.getBytes(DEFAULT_CHARSET), DEFAULT_CHARSET);
    }

    public FastStringBuilder(int capacity) {
        this(new byte[Math.max(8, capacity)], DEFAULT_CHARSET);
    }

    public FastStringBuilder(Charset charset) {
        this(32, charset);
    }

    public FastStringBuilder() {
        this(32);
    }

    public FastStringBuilder clear(byte[] buffer, Charset charset) {
        this.buffer = buffer;
        this.charset = charset;
        this.count = 0;

        return this;
    }

    public FastStringBuilder clear(int capacity, Charset charset) {
        return clear(new byte[Math.max(8, capacity)], charset);
    }

    public FastStringBuilder clear(String s, Charset charset) {
        return clear(s.getBytes(charset), charset);
    }

    public FastStringBuilder clear(String s) {
        return clear(s.getBytes(DEFAULT_CHARSET), DEFAULT_CHARSET);
    }

    public FastStringBuilder clear(Object o, Charset charset) {
        return clear(o.toString().getBytes(charset), charset);
    }

    public FastStringBuilder clear(Object o) {
        return clear(o.toString().getBytes(DEFAULT_CHARSET), DEFAULT_CHARSET);
    }

    public FastStringBuilder clear(int capacity) {
        return clear(new byte[Math.max(8, capacity)], DEFAULT_CHARSET);
    }

    public FastStringBuilder clear() {
        return clear(32);
    }

    public FastStringBuilder counterPush() {
        counter = count;

        return this;
    }

    public int counterPop() {
        return count - counter;
    }

    public FastStringBuilder reset() {
        count = 0;
        return this;
    }

    public FastStringBuilder append(Object o) {
        if (o == null) {
            return this;
        }

        if (o instanceof String) {
            return append((String) o);
        }
        if (o instanceof CharSequence) {
            return append((CharSequence) o);
        }
        if (o instanceof Character) {
            return append(((Character) o).charValue());
        }
        if (o instanceof Integer) {
            return append(((Integer) o).intValue());
        }
        if (o instanceof Long) {
            return append(((Long) o).longValue());
        }
        if (o instanceof Boolean) {
            return append(((Boolean) o).booleanValue());
        }
        if (o instanceof Byte) {
            return append(((Byte) o).byteValue());
        }
        if (o instanceof Short) {
            return append(((Short) o).shortValue());
        }
        if (o instanceof Float) {
            return append(((Float) o).floatValue());
        }
        if (o instanceof Double) {
            return append(((Double) o).doubleValue());
        }

        return append(o.toString());
    }

    public FastStringBuilder append(CharSequence seq) {
        if (seq == null) {
            return this;
        }

        return append(seq.toString());
    }

    public FastStringBuilder append(int value) {
        if (value == 0) {
            ensure(count + 1);
            buffer[count++] = '0';
            return this;
        }

        boolean negative = value < 0;
        if (negative) {
            value = -value;
        }

        int tmp = value;
        int digits = 0;
        while (tmp != 0) {
            tmp /= 10;
            digits++;
        }

        int required = count + digits + (negative ? 1 : 0);
        ensure(required);

        int pos = required;
        while (value != 0) {
            int digit = value % 10;
            buffer[--pos] = (byte) ('0' + digit);
            value /= 10;
        }

        if (negative) {
            buffer[--pos] = '-';
        }

        count = required;
        return this;
    }

    public FastStringBuilder append(long value) {
        if (value == 0) {
            ensure(count + 1);
            buffer[count++] = '0';
            return this;
        }

        boolean negative = value < 0;
        if (negative) {
            value = -value;
        }

        long tmp = value;
        int digits = 0;
        while (tmp != 0) {
            tmp /= 10;
            digits++;
        }

        int required = count + digits + (negative ? 1 : 0);
        ensure(required);

        int pos = required;
        while (value != 0) {
            int digit = (int) (value % 10);
            buffer[--pos] = (byte) ('0' + digit);
            value /= 10;
        }

        if (negative) {
            buffer[--pos] = '-';
        }

        count = required;
        return this;
    }

    public FastStringBuilder append(boolean b) {
        return append(b ? "true" : "false");
    }

    public FastStringBuilder append(short v) {
        return append((int) v);
    }

    public FastStringBuilder append(float v) {
        return append(Float.toString(v));
    }

    public FastStringBuilder append(double v) {
        return append(Double.toString(v));
    }

    public FastStringBuilder append(String s) {
        if (s == null || s.isEmpty()) {
            return this;
        }

        if (charset == StandardCharsets.UTF_8) {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);

                if (Character.isHighSurrogate(c)) {
                    if (i + 1 < s.length()) {
                        char c2 = s.charAt(i + 1);
                        if (Character.isLowSurrogate(c2)) {
                            int cp = Character.toCodePoint(c, c2);
                            writeUtf8CodePoint(cp);
                            i++;
                            continue;
                        }
                    }
                    writeUtf8CodePoint(0xFFFD);
                    continue;
                }

                if (Character.isLowSurrogate(c)) {
                    writeUtf8CodePoint(0xFFFD);
                    continue;
                }

                writeUtf8CodePoint(c);
            }
        } else {
            CharsetEncoder encoder = charset.newEncoder()
                    .onMalformedInput(CodingErrorAction.REPLACE)
                    .onUnmappableCharacter(CodingErrorAction.REPLACE);

            CharBuffer in = CharBuffer.wrap(s);
            ByteBuffer out = ByteBuffer.allocate((int) (encoder.maxBytesPerChar() * s.length()));

            encoder.encode(in, out, true);
            encoder.flush(out);

            int len = out.position();
            ensure(count + len);
            System.arraycopy(out.array(), 0, buffer, count, len);
            count += len;
        }

        return this;
    }

    public FastStringBuilder append(char c) {
        if (charset == StandardCharsets.UTF_8) {
            if (Character.isHighSurrogate(c)) {
                writeUtf8CodePoint(0xFFFD);
                return this;
            }
            if (Character.isLowSurrogate(c)) {
                writeUtf8CodePoint(0xFFFD);
                return this;
            }

            writeUtf8CodePoint(c);
        } else {
            if (c <= 0x7F) {
                ensure(count + 1);
                buffer[count++] = (byte) c;
            } else {
                append(Character.toString(c));
            }
        }
        return this;
    }

    public FastStringBuilder append(byte b) {
        ensure(count + 1);
        buffer[count++] = b;

        return this;
    }

    private void ensure(int required) {
        if (required <= buffer.length) {
            return;
        }

        buffer = Arrays.copyOf(buffer, Math.max(buffer.length << 1, required));
    }

    private void writeUtf8CodePoint(int codePoint) {
        if (codePoint <= 0x7F) {
            ensure(count + 1);
            buffer[count++] = (byte) codePoint;

        } else if (codePoint <= 0x7FF) {
            ensure(count + 2);
            buffer[count++] = (byte) (0xC0 | (codePoint >> 6));
            buffer[count++] = (byte) (0x80 | (codePoint & 0x3F));

        } else if (codePoint <= 0xFFFF) {
            ensure(count + 3);
            buffer[count++] = (byte) (0xE0 | (codePoint >> 12));
            buffer[count++] = (byte) (0x80 | ((codePoint >> 6) & 0x3F));
            buffer[count++] = (byte) (0x80 | (codePoint & 0x3F));

        } else {
            ensure(count + 4);
            buffer[count++] = (byte) (0xF0 | (codePoint >> 18));
            buffer[count++] = (byte) (0x80 | ((codePoint >> 12) & 0x3F));
            buffer[count++] = (byte) (0x80 | ((codePoint >> 6) & 0x3F));
            buffer[count++] = (byte) (0x80 | (codePoint & 0x3F));
        }
    }

    public byte[] toBytes() {
        return Arrays.copyOf(buffer, count);
    }

    @Override
    public String toString() {
        return new String(buffer, 0, count, charset);
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public int getCount() {
        return count;
    }
}
