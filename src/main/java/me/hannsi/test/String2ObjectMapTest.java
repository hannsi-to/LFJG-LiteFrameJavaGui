package me.hannsi.test;

import me.hannsi.lfjg.core.utils.math.AssetPath;
import me.hannsi.lfjg.core.utils.math.map.string2objectMap.LinkedString2ObjectMap;
import me.hannsi.lfjg.core.utils.math.map.string2objectMap.String2ObjectMap;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class String2ObjectMapTest {
    public static void main(String[] args) {
        // コンソール出力のセットアップ
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
        System.out.println("--- String2ObjectMap Strict Test Start ---");

        try {
            testBasicOperations();
            testNullKey(); // String特有のnullキーテスト
            testUpdateAndSize();
            testRemove();
            testCollisionResilience(); // 衝突回避テスト
            testForEachStrict();
            testPerformanceVsHashMap();
            testPerformanceWithAsserPathVsHashMap();

            testLinkedInsertionOrder();
            testLinkedRemoveAndOrder();
            testPerformanceComparison();

            System.out.println("\n✅ All String2ObjectMap tests passed successfully!");
        } catch (AssertionError e) {
            System.err.println("\n❌ Test failed: ");
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("\n💥 Unexpected exception:");
            t.printStackTrace();
        }
    }

    // ---------- Basic ----------
    private static void testBasicOperations() {
        log("Basic operations");
        String2ObjectMap<String> map = new String2ObjectMap<>(8);
        map.put("K1", "Value1");
        map.put("K2", "Value2");

        assertEquals("Value1", map.get("K1"));
        assertEquals("Value2", map.get("K2"));
        assertNull(map.get("K3"));
        assertEquals(2, map.size());
    }

    // ---------- Null key (Special Key) ----------
    private static void testNullKey() {
        log("Null key handling");
        String2ObjectMap<String> map = new String2ObjectMap<>();
        map.put((String) null, "NullValue");

        assertTrue(map.containsKey(null));
        assertEquals("NullValue", map.get((String) null));
        assertEquals(1, map.size());

        map.remove((String) null);
        assertEquals(0, map.size());
        assertNull(map.get((String) null));
    }

    // ---------- Update and size ----------
    private static void testUpdateAndSize() {
        log("Update and size");
        String2ObjectMap<Integer> map = new String2ObjectMap<>();
        map.put("A", 10);
        map.put("B", 20);
        map.put("A", 30); // 上書き

        assertEquals(30, map.get("A"));
        assertEquals(2, map.size());
    }

    // ---------- Remove and Chain maintenance ----------
    private static void testRemove() {
        log("Remove and Chain maintenance");
        String2ObjectMap<String> map = new String2ObjectMap<>();
        map.put("A", "Alpha");
        map.put("B", "Beta");

        assertEquals("Alpha", map.remove("A"));
        assertNull(map.get("A"));
        assertEquals(1, map.size());
        assertEquals("Beta", map.get("B"));

        map.remove("B");
        assertEquals(0, map.size());
    }

    // ---------- Collision resilience ----------
    private static void testCollisionResilience() {
        log("Collision resilience (Large Scale)");
        String2ObjectMap<String> map = new String2ObjectMap<>(100);

        // 10,000件のデータを投入して、ハッシュのズレやrehashを検証
        for (int i = 0; i < 10000; i++) {
            map.put("Key" + i, "Val" + i);
        }

        assertEquals(10000, map.size());
        for (int i = 0; i < 10000; i++) {
            assertEquals("Val" + i, map.get("Key" + i));
        }
    }

    // ---------- ForEach ----------
    private static void testForEachStrict() {
        log("ForEach iteration");
        String2ObjectMap<String> map = new String2ObjectMap<>();
        map.put((String) null, "Z");
        map.put("A", "Apple");
        map.put("B", "Banana");

        Set<String> keys = new HashSet<>();
        map.forEach((k, v) -> {
            keys.add(k);
            assertTrue(v != null);
        });

        assertEquals(3, keys.size());
        assertTrue(keys.contains(null));
        assertTrue(keys.contains("A"));
        assertTrue(keys.contains("B"));
    }

    // ---------- Performance Bench ----------
    private static void testPerformanceVsHashMap() {
        final int ITERATIONS = 100;
        final int N = 300_000;
        final int WARMUP = 5;

        System.out.println("\n=== Performance: String2ObjectMap vs java.util.HashMap ===");
        System.out.println("Items: " + N + " | Iterations: " + ITERATIONS);

        double totalPutCustom = 0, totalPutHash = 0;
        double totalGetCustom = 0, totalGetHash = 0;

        // キーの事前生成（ハッシュ計算時間を純粋に計るため）
        String[] keys = new String[N];
        for (int i = 0; i < N; i++) {
            keys[i] = "PerformanceTestKey_" + i;
        }

        for (int iter = 1; iter <= ITERATIONS; iter++) {
            System.out.printf("\rIteration: [%d/%d] %s", iter, ITERATIONS, (iter <= WARMUP ? "(Warmup)" : ""));

            String2ObjectMap<String> customMap = new String2ObjectMap<>(N);
            HashMap<String, String> hashMap = new HashMap<>(N);

            // PUT
            long t1 = System.nanoTime();
            for (int i = 0; i < N; i++) {
                customMap.put(keys[i], "PerformanceTestKey_" + i);
            }
            long t2 = System.nanoTime();

            long t3 = System.nanoTime();
            for (int i = 0; i < N; i++) {
                hashMap.put(keys[i], "PerformanceTestKey_" + i);
            }
            long t4 = System.nanoTime();

            // GET
            long t5 = System.nanoTime();
            for (int i = 0; i < N; i++) {
                customMap.get(keys[i]);
            }
            long t6 = System.nanoTime();

            long t7 = System.nanoTime();
            for (int i = 0; i < N; i++) {
                hashMap.get(keys[i]);
            }
            long t8 = System.nanoTime();

            if (iter > WARMUP) {
                totalPutCustom += (t2 - t1) / 1_000_000.0;
                totalPutHash += (t4 - t3) / 1_000_000.0;
                totalGetCustom += (t6 - t5) / 1_000_000.0;
                totalGetHash += (t8 - t7) / 1_000_000.0;
            }
        }

        System.out.println("\rDone.                                     ");
        int count = ITERATIONS - WARMUP;
        System.out.printf("Average Put String2ObjectMap : %6.2f ms%n", totalPutCustom / count);
        System.out.printf("Average Put HashMap          : %6.2f ms%n", totalPutHash / count);
        System.out.println("----------------------------------------");
        System.out.printf("Average Get String2ObjectMap : %6.2f ms%n", totalGetCustom / count);
        System.out.printf("Average Get HashMap          : %6.2f ms%n", totalGetHash / count);
    }

    private static void testPerformanceWithAsserPathVsHashMap() {
        final int ITERATIONS = 100;
        final int N = 300_000;
        final int WARMUP = 5;

        System.out.println("\n=== Performance: String2ObjectMap vs java.util.HashMap ===");
        System.out.println("Items: " + N + " | Iterations: " + ITERATIONS);

        double totalPutCustom = 0, totalPutHash = 0;
        double totalGetCustom = 0, totalGetHash = 0;

        // キーの事前生成（ハッシュ計算時間を純粋に計るため）
        AssetPath[] assetPaths = new AssetPath[N];
        for (int i = 0; i < N; i++) {
            assetPaths[i] = new AssetPath("PerformanceTestKey_" + i);
        }

        for (int iter = 1; iter <= ITERATIONS; iter++) {
            System.out.printf("\rIteration: [%d/%d] %s", iter, ITERATIONS, (iter <= WARMUP ? "(Warmup)" : ""));

            String2ObjectMap<String> customMap = new String2ObjectMap<>(N);
            HashMap<String, String> hashMap = new HashMap<>(N);

            // PUT
            long t1 = System.nanoTime();
            for (int i = 0; i < N; i++) {
                customMap.put(assetPaths[i], "PerformanceTestKey_" + i);
            }
            long t2 = System.nanoTime();

            long t3 = System.nanoTime();
            for (int i = 0; i < N; i++) {
                hashMap.put(assetPaths[i].path(), "PerformanceTestKey_" + i);
            }
            long t4 = System.nanoTime();

            // GET
            long t5 = System.nanoTime();
            for (int i = 0; i < N; i++) {
                customMap.get(assetPaths[i]);
            }
            long t6 = System.nanoTime();

            long t7 = System.nanoTime();
            for (int i = 0; i < N; i++) {
                hashMap.get(assetPaths[i].path());
            }
            long t8 = System.nanoTime();

            if (iter > WARMUP) {
                totalPutCustom += (t2 - t1) / 1_000_000.0;
                totalPutHash += (t4 - t3) / 1_000_000.0;
                totalGetCustom += (t6 - t5) / 1_000_000.0;
                totalGetHash += (t8 - t7) / 1_000_000.0;
            }
        }

        System.out.println("\rDone.                                     ");
        int count = ITERATIONS - WARMUP;
        System.out.printf("Average Put String2ObjectMap : %6.2f ms%n", totalPutCustom / count);
        System.out.printf("Average Put HashMap          : %6.2f ms%n", totalPutHash / count);
        System.out.println("----------------------------------------");
        System.out.printf("Average Get String2ObjectMap : %6.2f ms%n", totalGetCustom / count);
        System.out.printf("Average Get HashMap          : %6.2f ms%n", totalGetHash / count);
    }

    private static void testLinkedInsertionOrder() {
        log("LinkedMap: Insertion Order");
        LinkedString2ObjectMap<Integer> map = new LinkedString2ObjectMap<>();
        String[] expectedOrder = {"First", "Second", "Third", "Fourth"};

        for (int i = 0; i < expectedOrder.length; i++) {
            map.put(expectedOrder[i], i);
        }

        List<String> actualOrder = new ArrayList<>();
        map.forEach((k, v) -> actualOrder.add(k));

        for (int i = 0; i < expectedOrder.length; i++) {
            assertEquals(expectedOrder[i], actualOrder.get(i));
        }
    }

    private static void testLinkedRemoveAndOrder() {
        log("LinkedMap: Remove and Order Consistency");
        LinkedString2ObjectMap<String> map = new LinkedString2ObjectMap<>();
        map.put("A", "1");
        map.put("B", "2");
        map.put("C", "3");
        map.put("D", "4");

        // 途中の要素を削除
        map.remove("B");
        map.remove("C");

        // 再挿入（末尾に来るはず）
        map.put("B", "2-new");

        String[] expected = {"A", "D", "B"};
        List<String> actual = new ArrayList<>();
        map.forEach((k, v) -> actual.add(k));

        assertEquals(expected.length, actual.size());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual.get(i));
        }
    }

    // ---------- Performance Comparison ----------
    private static void testPerformanceComparison() {
        final int ITERATIONS = 50;
        final int N = 200_000;
        final int WARMUP = 10;

        System.out.println("\n=== Performance: Normal vs Linked vs HashMap ===");
        System.out.println("Items: " + N + " | Iterations: " + ITERATIONS);

        double pNormal = 0, pLinked = 0, pHash = 0;
        double gNormal = 0, gLinked = 0, gHash = 0;
        double fNormal = 0, fLinked = 0; // Iteration speed

        String[] keys = new String[N];
        for (int i = 0; i < N; i++) {
            keys[i] = "Key_" + i;
        }

        for (int iter = 1; iter <= ITERATIONS; iter++) {
            System.out.printf("\rIteration: [%d/%d]", iter, ITERATIONS);

            String2ObjectMap<String> normal = new String2ObjectMap<>(N);
            LinkedString2ObjectMap<String> linked = new LinkedString2ObjectMap<>(N);
            HashMap<String, String> hash = new HashMap<>(N);

            // PUT
            long t1 = System.nanoTime();
            for (String k : keys) {
                normal.put(k, "V");
            }
            long t2 = System.nanoTime();

            long t3 = System.nanoTime();
            for (String k : keys) {
                linked.put(k, "V");
            }
            long t4 = System.nanoTime();

            long t5 = System.nanoTime();
            for (String k : keys) {
                hash.put(k, "V");
            }
            long t6 = System.nanoTime();

            // GET
            long t7 = System.nanoTime();
            for (String k : keys) {
                normal.get(k);
            }
            long t8 = System.nanoTime();

            long t9 = System.nanoTime();
            for (String k : keys) {
                linked.get(k);
            }
            long t10 = System.nanoTime();

            // FOR EACH (スキャンスピードの違いを見る)
            long t11 = System.nanoTime();
            normal.forEach((k, v) -> {});
            long t12 = System.nanoTime();

            long t13 = System.nanoTime();
            linked.forEach((k, v) -> {});
            long t14 = System.nanoTime();

            if (iter > WARMUP) {
                pNormal += (t2 - t1);
                pLinked += (t4 - t3);
                pHash += (t6 - t5);
                gNormal += (t8 - t7);
                gLinked += (t10 - t9);
                fNormal += (t12 - t11);
                fLinked += (t14 - t13);
            }
        }

        double count = ITERATIONS - WARMUP;
        System.out.println("\rDone.                                     ");
        System.out.printf("PUT (ms)    | Normal: %6.2f | Linked: %6.2f | HashMap: %6.2f%n",
                pNormal / count / 1e6, pLinked / count / 1e6, pHash / count / 1e6);
        System.out.printf("GET (ms)    | Normal: %6.2f | Linked: %6.2f | (LinkedはNormalを継承)%n",
                gNormal / count / 1e6, gLinked / count / 1e6);
        System.out.printf("SCAN (ms)   | Normal: %6.2f | Linked: %6.2f (forEach速度)%n",
                fNormal / count / 1e6, fLinked / count / 1e6);

        System.out.println("\n> Linked版は挿入時にリンク更新(long pack)を行うため、Normalより若干PUTが遅くなります。");
        System.out.println("> 一方で、forEachは空スロットをスキップするため、データ密度が低いほどLinkedが有利です。");
    }

    // ---------- Helpers ----------
    private static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("Expected=" + expected + ", Actual=" + actual);
        }
    }

    private static void assertNull(Object obj) {
        if (obj != null) {
            throw new AssertionError("Expected null but got " + obj);
        }
    }

    private static void assertTrue(boolean cond) {
        if (!cond) {
            throw new AssertionError("Condition is false");
        }
    }

    private static void log(String name) {
        System.out.printf("[%-30s] ... OK%n", name);
    }
}