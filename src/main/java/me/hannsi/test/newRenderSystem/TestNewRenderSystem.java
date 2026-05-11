package me.hannsi.test.newRenderSystem;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.reflection.reference.IntRef;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.system.buffer.*;
import me.hannsi.lfjg.render.system.memory.StaticAllocatorSystem;
import me.hannsi.lfjg.render.system.memory.allocator.LinearAllocator;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static me.hannsi.lfjg.frame.LFJGFrameContext.frame;
import static me.hannsi.lfjg.render.LFJGRenderContext.currentAllocation;
import static org.lwjgl.opengl.GL15.*;

public class TestNewRenderSystem implements LFJGFrame {
    private final IntRef writeConfigPointer = new IntRef();
    private ManagedBuffer managedBuffer;
    private int frameCount = 0;

    public static void main(String[] args) {
        new TestNewRenderSystem().setFrame();
    }

    @Override
    public void init() {
        // コアシステムの初期化
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());

        // 初期容量 1024 バイト (Float.BYTES * 256要素 分) のアロケーターを作成
        LinearAllocator linearAllocator = new LinearAllocator(1024, Float.BYTES);
        StaticAllocatorSystem allocatorSystem = new StaticAllocatorSystem(linearAllocator);

        // ManagedBuffer の構築
        managedBuffer = new ManagedBuffer(
                BufferSystem.Builder.createBuilder()
                        .bufferAllocationMode(BufferAllocationMode.BUFFER_DATA)
                        .bufferProperty(BufferProperty.BIND_BUFFER)
                        .bufferWriteMode(BufferWriteMode.BUFFER_SUB, allocatorSystem, writeConfigPointer)
                        .build(false),
                GL_ARRAY_BUFFER, Float.BYTES
        );

        System.out.println("[Test] ManagedBuffer initialized with initial size: 1024 bytes");
        System.out.println("[Test] Config pointer: " + writeConfigPointer.getValue());
    }

    @Override
    public void drawFrame() {
        // startFrame() 内で移行フェンスのチェックと完了、BufferSystem.startFrame() が行われます
        managedBuffer.startFrame();

        frameCount++;
        System.out.println("\n--- Frame " + frameCount + " ---");

        if (frameCount == 1) {
            // --- シナリオ 1: 初回データ書き込み (安全な範囲) ---
            System.out.println("[Test] Allocating 512 bytes...");
            managedBuffer.alloc(writeConfigPointer.getValue(), 512, Float.BYTES);

            // CPUアドレス（メモリマップ、またはBUFFER_SUB用のローカル確保メモリ）へのデータ書き込み
            long offset = currentAllocation.offset;
            long size = currentAllocation.memorySize;
            long cpuAddress = managedBuffer.getActiveBufferSystem().getBufferWriteConfigs().get(writeConfigPointer.getValue()).cpuAddress;

            // テストデータを準備
            FloatBuffer data = MemoryUtil.memAllocFloat(128); // 128 * 4 = 512 bytes
            for (int i = 0; i < 128; i++) {
                data.put(i, 1.0f); // テスト用に 1.0f を敷き詰める
            }

            // メモリコピー
            MemoryUtil.memCopy(MemoryUtil.memAddress(data), cpuAddress + offset, size);
            MemoryUtil.memFree(data);

            // GPUへのデータ送信（BUFFER_SUB モードの場合、明示的な転送や通知が必要であればここに記述）
            System.out.println("[Test] First allocation and copy complete. Offset: " + offset + ", Size: " + size);

        } else if (frameCount == 2) {
            // --- シナリオ 2: 容量超過 (1024バイトのバッファに対して、さらに800バイト要求する) ---
            System.out.println("[Test] Requesting additional 800 bytes (Total 1312/1024, should overflow)...");

            try {
                // 内部アロケーターの物理制限に引っかかるため、例外が発生する前に（もしくは例外検知後に）
                // 本来は自動リサイズをトリガーさせます。
                // ここでは手動で非同期リサイズを要求し、同一フレーム内でフォールバック/新バッファへのallocを行います。

                managedBuffer.requestAsyncResize();
                System.out.println("[Test] Resize triggered successfully. Active buffer expanded to 2048 bytes.");

                // リサイズ後の容量に alloc を試みる
                managedBuffer.alloc(writeConfigPointer.getValue(), 800, Float.BYTES);
                long offset = currentAllocation.offset;
                long size = currentAllocation.memorySize;
                long cpuAddress = managedBuffer.getActiveBufferSystem().getBufferWriteConfigs().get(writeConfigPointer.getValue()).cpuAddress;

                // 2つ目のテストデータを準備
                FloatBuffer data2 = MemoryUtil.memAllocFloat(200); // 200 * 4 = 800 bytes
                for (int i = 0; i < 200; i++) {
                    data2.put(i, 2.0f); // 2.0f を敷き詰める
                }

                // 移行中のダブル・ライトにより、移行先バッファにも同時に書き込まれます
                MemoryUtil.memCopy(MemoryUtil.memAddress(data2), cpuAddress + offset, size);
                MemoryUtil.memFree(data2);

                System.out.println("[Test] Overflow allocation and double-write complete. Offset: " + offset + ", Size: " + size);

            } catch (Exception e) {
                System.err.println("[Test] Failed to allocate even after resize request: " + e.getMessage());
                e.printStackTrace();
            }

        } else if (frameCount == 3) {
            // --- シナリオ 3: 移行が完了しているかどうかの確認とGPUバッファデータのベリファイ ---
            System.out.println("[Test] Checking if migration is complete...");

            // 2フレーム目の終わりに completeMigration() が走っていれば、activeBufferSystem のIDが変わっています
            int activeBufferId = managedBuffer.getActiveBufferSystem().getBufferId();
            System.out.println("[Test] Current Active Buffer ID: " + activeBufferId);

            // 実際にGPUに格納されているデータを読み出して整合性を検証する (デバッグ検証)
            // ※ BUFFER_SUBモードの場合、一度glBufferSubData等でGPUにアップロードされている必要があります。
            // (通常は BufferSystem 内、もしくはレンダラー側でデータの転送/フラッシュが行われます)

            // ここではGPUに格納されているデータをローカルバッファに吸い上げてチェックします
            glBindBuffer(GL_ARRAY_BUFFER, activeBufferId);
            float[] checkData = new float[512]; // 全体で2048バイト分
            glGetBufferSubData(GL_ARRAY_BUFFER, 0, checkData);

            int oneCount = 0;
            int twoCount = 0;
            for (float val : checkData) {
                if (val == 1.0f) {
                    oneCount++;
                }
                if (val == 2.0f) {
                    twoCount++;
                }
            }

            System.out.println("[Test] GPU Buffer Verification Results:");
            System.out.println("  - 1.0f detected (First alloc): " + oneCount + " elements (Expected: 128)");
            System.out.println("  - 2.0f detected (Second alloc): " + twoCount + " elements (Expected: 200)");

            if (oneCount == 128 && twoCount == 200) {
                System.out.println("[Test] SUCCESS: Dynamic migration and data consistency verified perfectly!");
            } else {
                System.out.println("[Test] WARNING: Data discrepancy detected. Check double-write timing or GPU upload sync.");
            }

            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }

        // フレーム終了処理
        managedBuffer.endFrame();
    }

    @Override
    public void stopFrame() {
        // アプリケーション終了時にバッファを完全に破棄してリークを防ぐ
        if (managedBuffer != null && managedBuffer.getActiveBufferSystem() != null) {
            managedBuffer.getActiveBufferSystem().destroy();
            System.out.println("[Test] Buffer system destroyed. Native memory freed.");
        }
    }

    @Override
    public void setFrameSetting() {
        // ウィンドウやコンテキストの設定
    }

    private void setFrame() {
        frame = new Frame(this, getClass().getSimpleName());
    }
}
