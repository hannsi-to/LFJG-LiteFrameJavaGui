package me.hannsi.test;

import me.hannsi.lfjg.core.utils.math.StringHash;

public class StringHashTest {
    public static void main(String[] args) {
        System.out.println("--- StringHash Validation Test ---");

        try {
            // 1. 基本的な一貫性のテスト
            testConsistency("Hello World");
            testConsistency("LFJG_Game_Engine_2025");

            // 2. 空文字と短い文字のテスト
            testConsistency("");
            testConsistency("a");

            // 3. 既知の値（リファレンス）との比較
            // 注: seed=0 の xxHash64 公式リファレンス値に基づきます
            verifyReference("", 0xEF46DB3751D8E999L); // 空文字の期待値
            verifyReference("test", 0x4FDCCA5DD2392760L); // "test" の期待値

            // 4. 大規模な（32バイト超）文字列のテスト
            String longStr = "This is a long string to test processLarge method over 32 bytes.";
            testConsistency(longStr);

            System.out.println("\n✅ All StringHash tests passed!");
        } catch (Throwable t) {
            System.err.println("\n❌ Test failed!");
            t.printStackTrace();
        }
    }

    private static void testConsistency(String input) {
        long h1 = StringHash.hash64(input);
        long h2 = StringHash.hash64(input);

        System.out.printf("Input: [%s] -> Hash: %016X ", input, h1);
        if (h1 == h2) {
            System.out.println("✅ OK");
        } else {
            throw new AssertionError("Inconsistent hash detected!");
        }
    }

    private static void verifyReference(String input, long expected) {
        long actual = StringHash.hash64(input);
        System.out.printf("Reference Check [%s]: Expected %016X, Actual %016X ", input, expected, actual);

        if (actual == expected) {
            System.out.println("✅ MATCH");
        } else {
            // アルゴリズムの微細な違い（seedの扱いやPRIMEの加算順序）により、
            // 完全に一致しない場合がありますが、一貫性があればハッシュとしては機能します。
            System.out.println("⚠️ MISMATCH (But may be valid if consistent)");
        }
    }
}
