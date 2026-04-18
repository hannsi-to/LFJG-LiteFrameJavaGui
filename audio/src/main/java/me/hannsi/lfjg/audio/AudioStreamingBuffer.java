package me.hannsi.lfjg.audio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static me.hannsi.lfjg.audio.AudioSystemSetting.AUDIO_STREAMING_BUFFER_BUFFER_COUNT;
import static me.hannsi.lfjg.audio.AudioSystemSetting.AUDIO_STREAMING_BUFFER_CHUNK_SIZE;
import static me.hannsi.lfjg.core.utils.math.MathHelper.min;
import static org.lwjgl.openal.AL10.*;

public class AudioStreamingBuffer {
    private static final int SILENCE_SAMPLES = 1024;
    private static final int BYTES_PER_SAMPLE = Short.BYTES;
    private static final long STOP_TIMEOUT_MS = 1000L;
    private static final ByteBuffer SILENCE_SENTINEL = ByteBuffer.allocateDirect(0);
    private final int[] buffers = new int[AUDIO_STREAMING_BUFFER_BUFFER_COUNT];
    private final ArrayBlockingQueue<ByteBuffer> bufferPool;
    private final Map<Integer, ByteBuffer> inflightBuffers = new ConcurrentHashMap<>();
    private final ByteBuffer[] silenceBuffers;
    private final int sampleRate;
    private final int channels;
    private final int source;
    private final int format;
    private final AtomicInteger silenceIndex = new AtomicInteger(0);
    private AudioSupplier supplier;

    private Thread streamThread;
    private volatile boolean running = false;

    public AudioStreamingBuffer(int sampleRate, int channels) {
        this.bufferPool = new ArrayBlockingQueue<>(AUDIO_STREAMING_BUFFER_BUFFER_COUNT);
        for (int i = 0; i < AUDIO_STREAMING_BUFFER_BUFFER_COUNT; i++) {
            bufferPool.offer(ByteBuffer.allocateDirect(AUDIO_STREAMING_BUFFER_CHUNK_SIZE).order(ByteOrder.nativeOrder()));
        }

        this.sampleRate = sampleRate;
        this.channels = channels;

        int size = SILENCE_SAMPLES * channels * BYTES_PER_SAMPLE;

        this.silenceBuffers = createSilenceBuffer(size, AUDIO_STREAMING_BUFFER_BUFFER_COUNT);

        if (channels == 2) {
            format = AL_FORMAT_STEREO16;
        } else {
            format = AL_FORMAT_MONO16;
        }

        source = alGenSources();
        alGenBuffers(buffers);

        alSourcef(source, AL_GAIN, 1.0f);
        alSourcei(source, AL_SOURCE_RELATIVE, AL_TRUE);
        alSource3f(source, AL_POSITION, 0f, 0f, 0f);
    }

    private ByteBuffer acquireBuffer() {
        ByteBuffer buffer = bufferPool.poll();
        if (buffer == null) {
            buffer = ByteBuffer.allocateDirect(AUDIO_STREAMING_BUFFER_CHUNK_SIZE).order(ByteOrder.nativeOrder());
        }

        buffer.clear();
        return buffer;
    }

    private void releaseBuffer(ByteBuffer buffer) {
        bufferPool.offer(buffer);
    }

    public void start(AudioSupplier supplier) {
        if (running) {
            throw new IllegalStateException("Already running");
        }

        this.supplier = supplier;
        running = true;
        streamThread = new Thread(() -> streamLoop(this.supplier), getClass().getSimpleName() + "Thread");
        streamThread.start();
    }

    public void stop() {
        running = false;
        if (streamThread != null) {
            streamThread.interrupt();
            try {
                streamThread.join(STOP_TIMEOUT_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (this.supplier != null) {
            this.supplier.close();
        }

        alSourceStop(source);
        alSourcei(source, AL_BUFFER, 0);

        for (ByteBuffer buf : inflightBuffers.values()) {
            releaseBuffer(buf);
        }
        inflightBuffers.clear();

        alDeleteSources(source);
        alDeleteBuffers(buffers);
    }

    private void streamLoop(AudioSupplier supplier) {
        for (int buffer : buffers) {
            ByteBuffer data = getNextChunk(supplier);
            ByteBuffer toQueue = (data == SILENCE_SENTINEL) ? silence() : data;
            alBufferData(buffer, format, toQueue, sampleRate);
            alSourceQueueBuffers(source, buffer);
            inflightBuffers.put(buffer, data);
        }

        alSourcePlay(source);

        while (running && (!supplier.isFinished() || alGetSourcei(source, AL_BUFFERS_QUEUED) > 0)) {
            int processed = alGetSourcei(source, AL_BUFFERS_PROCESSED);

            while (processed-- > 0) {
                int buffer = alSourceUnqueueBuffers(source);

                ByteBuffer prev = inflightBuffers.remove(buffer);
                if (prev != null && prev != SILENCE_SENTINEL) {
                    releaseBuffer(prev);
                }

                ByteBuffer data = getNextChunk(supplier);
                alBufferData(buffer, format, data, sampleRate);
                alSourceQueueBuffers(source, buffer);

                inflightBuffers.put(buffer, data);
            }

            if (alGetSourcei(source, AL_SOURCE_STATE) != AL_PLAYING) {
                alSourcePlay(source);
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private ByteBuffer getNextChunk(AudioSupplier supplier) {
        ByteBuffer localBuffer = acquireBuffer();

        while (localBuffer.position() < AUDIO_STREAMING_BUFFER_CHUNK_SIZE) {
            ByteBuffer next = supplier.getNextBuffer();
            if (next == null) {
                break;
            }

            int remaining = AUDIO_STREAMING_BUFFER_CHUNK_SIZE - localBuffer.position();
            int toCopy = min(remaining, next.remaining());

            ByteBuffer slice = next.slice();
            slice.limit(toCopy);

            localBuffer.put(slice);
        }

        if (localBuffer.position() == 0) {
            releaseBuffer(localBuffer);
            return SILENCE_SENTINEL;
        }

        localBuffer.flip();
        return localBuffer;
    }

    private ByteBuffer[] createSilenceBuffer(int size, int bufferCount) {
        ByteBuffer[] buffers = new ByteBuffer[bufferCount];

        for (int i = 0; i < bufferCount; i++) {
            ByteBuffer buffer = ByteBuffer
                    .allocateDirect(size)
                    .order(ByteOrder.nativeOrder());
            for (int j = 0; j < size; j++) {
                buffer.put((byte) 0);
            }
            buffer.flip();
            buffers[i] = buffer;
        }

        return buffers;
    }

    private ByteBuffer silence() {
        int idx = silenceIndex.getAndUpdate(i -> (i + 1) % silenceBuffers.length);
        return silenceBuffers[idx].duplicate();
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public int getChannels() {
        return channels;
    }

    public int getFormat() {
        return format;
    }

    public interface AudioSupplier {
        ByteBuffer getNextBuffer();

        boolean isFinished();

        void close();
    }
}