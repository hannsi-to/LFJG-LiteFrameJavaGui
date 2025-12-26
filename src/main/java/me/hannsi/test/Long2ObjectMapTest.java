package me.hannsi.test;

import me.hannsi.lfjg.core.utils.math.map.long2Object.ConcurrentLong2ObjectMap;
import me.hannsi.lfjg.core.utils.math.map.long2Object.LinkedLong2ObjectMap;
import me.hannsi.lfjg.core.utils.math.map.long2Object.Long2ObjectMap;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Long2ObjectMapTest {
    public static void main(String[] args) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
        System.out.println("--- Long2ObjectMap Strict Test Start ---");

        try {
            testBasicOperations();
            testZeroKey();
            testUpdateAndSize();
            testRemove();
            testRehashAndCollision();
            testForEachStrict();
            testPerformanceVsHashMap();

            final int THREADS = Runtime.getRuntime().availableProcessors();
            final int OPS_PER_THREAD = 100_000; // 各スレッド10万回操作
            final int TOTAL_OPS = THREADS * OPS_PER_THREAD;
            runFullTest("ConcurrentLong2ObjectMap", new ConcurrentLong2ObjectMap<>(TOTAL_OPS), THREADS, OPS_PER_THREAD);
            runFullTest("ConcurrentHashMap", new ConcurrentHashMap<>(TOTAL_OPS), THREADS, OPS_PER_THREAD);
            runFullTest("Synchronized HashMap", Collections.synchronizedMap(new HashMap<>(TOTAL_OPS)), THREADS, OPS_PER_THREAD);

            testLinkedMapOrderStrict();
            testLinkedMapPerformanceVsLinkedHashMap();

            System.out.println("\n✅ All strict tests passed successfully!");
        } catch (AssertionError e) {
            System.err.println("\n❌ Test failed: ");
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("\n💥 Unexpected exception:");
            System.err.println(t.getMessage());
        }
    }

    // ---------- Basic ----------

    private static void testBasicOperations() {
        log("Basic operations");

        Long2ObjectMap<String> map = new Long2ObjectMap<>(8);
        map.put(10L, "Ten");
        map.put(20L, "Twenty");

        assertEquals("Ten", map.get(10L));
        assertEquals("Twenty", map.get(20L));
        assertNull(map.get(30L));
        assertEquals(2, map.size());
    }

    // ---------- Zero key ----------

    private static void testZeroKey() {
        log("Zero key");

        Long2ObjectMap<String> map = new Long2ObjectMap<>();
        map.put(0L, "Zero");

        assertTrue(map.containsKey(0L));
        assertEquals("Zero", map.get(0L));
        assertEquals(1, map.size());
    }

    // ---------- Update and size ----------

    private static void testUpdateAndSize() {
        log("Update and size");

        Long2ObjectMap<Integer> map = new Long2ObjectMap<>();
        map.put(1, 10);
        map.put(2, 20);
        map.put(1, 30);

        assertEquals(30, map.get(1));
        assertEquals(2, map.size());
    }

    // ---------- Remove ----------

    private static void testRemove() {
        log("Remove");

        Long2ObjectMap<String> map = new Long2ObjectMap<>();
        map.put(1, "A");
        map.put(2, "B");

        assertEquals("A", map.remove(1));
        assertNull(map.get(1));
        assertEquals(1, map.size());

        map.remove(2);
        assertEquals(0, map.size());
    }

    // ---------- Rehash & collision ----------

    private static void testRehashAndCollision() {
        log("Rehash & collision");

        Long2ObjectMap<String> map = new Long2ObjectMap<>(4);

        for (int i = 1; i <= 100; i++) {
            map.put(i, "V" + i);
        }

        assertEquals(100, map.size());

        for (int i = 1; i <= 100; i++) {
            assertEquals("V" + i, map.get(i));
        }
    }

    // ---------- ForEach strict ----------

    private static void testForEachStrict() {
        log("ForEach strict");

        Long2ObjectMap<String> map = new Long2ObjectMap<>();
        map.put(0, "Z");
        map.put(1, "A");
        map.put(2, "B");

        Set<Long> keys = new HashSet<>();
        map.forEach((k, v) -> {
            keys.add(k);
            assertTrue(v != null);
        });

        assertEquals(3, keys.size());
        assertTrue(keys.contains(0L));
        assertTrue(keys.contains(1L));
        assertTrue(keys.contains(2L));
    }

    // ---------- Assertions ----------

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
        System.out.print("[" + name + "] ... ");
        System.out.println("OK");
    }

    private static void testPerformanceVsHashMap() {
        final int ITERATIONS = 100;
        System.out.println("\n=== Performance test (vs HashMap) - Average of " + ITERATIONS + " runs ===");

        final int N = 500_000; // 5000万件
        final int WARMUP = 5;

        double totalPutCustom = 0;
        double totalPutHash = 0;
        double totalGetCustom = 0;
        double totalGetHash = 0;

        for (int iter = 1; iter <= ITERATIONS; iter++) {
            // 現在のイテレーション進捗を表示 (\r で同じ行を更新)
            System.out.printf("\rProcessing iteration: [%d/%d] %s",
                    iter, ITERATIONS, (iter <= WARMUP ? "(Warmup)" : ""));

            Long2ObjectMap<Integer> customMap = new Long2ObjectMap<>(N);
            Map<Long, Integer> hashMap = new HashMap<>(N * 2);

            // Put Measurement
            long t1 = System.nanoTime();
            for (int i = 0; i < N; i++) {
                customMap.put(i, i);
            }
            long t2 = System.nanoTime();

            long t3 = System.nanoTime();
            for (int i = 0; i < N; i++) {
                hashMap.put((long) i, i);
            }
            long t4 = System.nanoTime();

            // Get Measurement
            long t5 = System.nanoTime();
            for (int i = 0; i < N; i++) {
                customMap.get(i);
            }
            long t6 = System.nanoTime();

            long t7 = System.nanoTime();
            for (int i = 0; i < N; i++) {
                hashMap.get((long) i);
            }
            long t8 = System.nanoTime();

            if (iter > WARMUP) {
                totalPutCustom += (t2 - t1) / 1_000_000.0;
                totalPutHash += (t4 - t3) / 1_000_000.0;
                totalGetCustom += (t6 - t5) / 1_000_000.0;
                totalGetHash += (t8 - t7) / 1_000_000.0;
            }

            // メモリ不足を避けるため、各イテレーション後に明示的に参照を切る
            customMap = null;
            hashMap = null;
        }

        // 進捗表示の行をクリアして結果を表示
        System.out.println("\r" + " ".repeat(50) + "\rDone.");

        int count = ITERATIONS - WARMUP;
        System.out.printf("Average Put Long2ObjectMap : %6.2f ms%n", totalPutCustom / count);
        System.out.printf("Average Put HashMap        : %6.2f ms%n", totalPutHash / count);
        System.out.println("----------------------------------------");
        System.out.printf("Average Get Long2ObjectMap : %6.2f ms%n", totalGetCustom / count);
        System.out.printf("Average Get HashMap        : %6.2f ms%n", totalGetHash / count);
    }

    private static void runFullTest(String label, Map<Long, Integer> map, int threadCount, int opsPerThread) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        long start = System.nanoTime();

        // --- 1. 並列書き込みフェーズ ---
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < opsPerThread; j++) {
                        long key = (long) threadId * opsPerThread + j;
                        map.put(key, (int) key); // キーと同じ値をバリューに
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        long end = System.nanoTime();
        double durationMs = (end - start) / 1_000_000.0;

        // --- 2. 整合性チェックフェーズ (Consistency Check) ---
        int errors = 0;
        int missing = 0;
        for (long i = 0; i < (long) threadCount * opsPerThread; i++) {
            Integer val = map.get(i);
            if (val == null) {
                missing++;
            } else if (val != (int) i) {
                errors++;
            }
        }

        // --- 結果表示 ---
        System.out.printf("%-25s | Time: %8.2f ms | Status: ", label, durationMs);
        if (missing == 0 && errors == 0 && map.size() == (threadCount * opsPerThread)) {
            System.out.println("✅ PASS (Integrity OK)");
        } else {
            System.out.printf("❌ FAIL (Missing: %d, WrongVal: %d, Size: %d)%n",
                    missing, errors, map.size());
        }

        executor.shutdown();
    }

    // ConcurrentLong2ObjectMapがMapを実装していない場合のオーバーロード用
    private static void runFullTest(String label, ConcurrentLong2ObjectMap<Integer> map, int threadCount, int opsPerThread) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        long start = System.nanoTime();
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < opsPerThread; j++) {
                        long key = (long) threadId * opsPerThread + j;
                        map.put(key, (int) key);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        long end = System.nanoTime();
        double durationMs = (end - start) / 1_000_000.0;

        int errors = 0;
        int missing = 0;
        for (long i = 0; i < (long) threadCount * opsPerThread; i++) {
            Integer val = map.get(i);
            if (val == null) {
                missing++;
            } else if (val != (int) i) {
                errors++;
            }
        }

        System.out.printf("%-25s | Time: %8.2f ms | Status: ", label, durationMs);
        if (missing == 0 && errors == 0 && map.size() == (threadCount * opsPerThread)) {
            System.out.println("✅ PASS (Integrity OK)");
        } else {
            System.out.printf("❌ FAIL (Missing: %d, WrongVal: %d, Size: %d)%n",
                    missing, errors, map.size());
        }
        executor.shutdown();
    }

    private static void testLinkedMapOrderStrict() {
        log("LinkedLong2ObjectMap Order Strict");

        LinkedLong2ObjectMap<String> map = new LinkedLong2ObjectMap<>(16);
        // 0L を含めた挿入順
        long[] testKeys = {10L, 5L, 20L, 0L, 15L};

        for (long k : testKeys) {
            map.put(k, "Val" + k);
        }

        List<Long> resultKeys = new ArrayList<>();
        map.forEach((k, v) -> resultKeys.add(k));

        // 結果のサイズ確認
        assertEquals(testKeys.length, resultKeys.size());

        // 純粋に挿入した順番通りに並んでいるかを確認する
        for (int i = 0; i < testKeys.length; i++) {
            assertEquals(testKeys[i], resultKeys.get(i));
        }
    }

    private static void testLinkedMapPerformanceVsLinkedHashMap() {
        final int ITERATIONS = 100;
        System.out.println("\n=== Performance test (LinkedMap vs LinkedHashMap) - Average of " + ITERATIONS + " runs ===");

        final int N = 500_000;
        final int WARMUP = 5;

        double totalPutLinkedCustom = 0;
        double totalPutLinkedHash = 0;
        double totalIterLinkedCustom = 0;
        double totalIterLinkedHash = 0;

        for (int iter = 1; iter <= ITERATIONS; iter++) {
            System.out.printf("\rLinked Processing iteration: [%d/%d] %s",
                    iter, ITERATIONS, (iter <= WARMUP ? "(Warmup)" : ""));

            LinkedLong2ObjectMap<Integer> customMap = new LinkedLong2ObjectMap<>(N);
            LinkedHashMap<Long, Integer> linkedHashMap = new LinkedHashMap<>(N, 0.75f);

            // --- Put Measurement ---
            long t1 = System.nanoTime();
            for (int i = 1; i <= N; i++) { // 0を避けて純粋な挿入順リンクをテスト
                customMap.put(i, i);
            }
            long t2 = System.nanoTime();

            long t3 = System.nanoTime();
            for (int i = 1; i <= N; i++) {
                linkedHashMap.put((long) i, i);
            }
            long t4 = System.nanoTime();

            // --- Iteration (forEach) Measurement ---
            // LinkedMapの真骨頂は要素がまばらな時の走査速度
            long t5 = System.nanoTime();
            customMap.forEach((k, v) -> {
                int dummy = v;
            });
            long t6 = System.nanoTime();

            long t7 = System.nanoTime();
            linkedHashMap.forEach((k, v) -> {
                int dummy = v;
            });
            long t8 = System.nanoTime();

            if (iter > WARMUP) {
                totalPutLinkedCustom += (t2 - t1) / 1_000_000.0;
                totalPutLinkedHash += (t4 - t3) / 1_000_000.0;
                totalIterLinkedCustom += (t6 - t5) / 1_000_000.0;
                totalIterLinkedHash += (t8 - t7) / 1_000_000.0;
            }

            customMap = null;
            linkedHashMap = null;
        }

        System.out.println("\r" + " ".repeat(60) + "\rDone.");

        int count = ITERATIONS - WARMUP;
        System.out.printf("Average Put LinkedLong2ObjectMap : %6.2f ms%n", totalPutLinkedCustom / count);
        System.out.printf("Average Put LinkedHashMap        : %6.2f ms%n", totalPutLinkedHash / count);
        System.out.println("----------------------------------------");
        System.out.printf("Average Iter (forEach) Custom    : %6.2f ms%n", totalIterLinkedCustom / count);
        System.out.printf("Average Iter (forEach) Java      : %6.2f ms%n", totalIterLinkedHash / count);
    }
}
