package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import me.hannsi.lfjg.utils.type.types.SoundLoaderType;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.joml.Vector3f;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class SoundData {
    private final int sourceId;
    private final int bufferId;

    private SoundEffect soundEffect;
    private ShortBuffer pcm;

    SoundData() {
        this.sourceId = alGenSources();
        this.bufferId = alGenBuffers();
    }

    public static SoundData createSoundData() {
        return new SoundData();
    }

    public SoundData createSoundPCM(SoundLoaderType soundLoaderType, FileLocation fileLocation) {
        switch (soundLoaderType) {
            case STB_VORBIS -> {
                try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
                    pcm = readVorbis(fileLocation.getPath(), info);
                    updatePCMData(info.sample_rate(), info.channels());
                }
            }
            case JAVA_CV -> {
                try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(fileLocation.getPath())) {
                    pcm = readAudio(grabber, fileLocation.getPath());

                    int sampleRate = grabber.getSampleRate();
                    int channels = grabber.getAudioChannels();
                    int format = (channels == 1) ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;
                    updatePCMData(sampleRate, format);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to load audio file: " + fileLocation.getPath(), e);
                }

            }
            default -> throw new IllegalStateException("Unexpected value: " + soundLoaderType);
        }

        return this;
    }

    public SoundData attachBuffer() {
        stop();
        setParameter(AL_BUFFER, bufferId);
        return this;
    }

    public SoundData attachEffect(SoundEffect soundEffect) {
        soundEffect.sendEffectSlot(sourceId);

        return this;
    }

    public void cleanup() {
        stop();
        soundEffect.cleanup();

        alDeleteBuffers(sourceId);
        alDeleteBuffers(bufferId);
        if (pcm != null) {
            MemoryUtil.memFree(pcm);
        }

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                sourceId + ", " + bufferId,
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public void updatePCMData(int sampleRate, int format) {
        alBufferData(bufferId, format == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, sampleRate);
    }

    public SoundData play() {
        alSourcePlay(sourceId);
        return this;
    }

    public SoundData stop() {
        alSourceStop(sourceId);
        return this;
    }

    public SoundData pause() {
        alSourcePause(sourceId);
        return this;
    }

    public boolean isPlaying() {
        return getValue(AL_SOURCE_STATE) == AL_PLAYING;
    }

    public SoundData relative(boolean relative) {
        return setParameter(AL_SOURCE_RELATIVE, relative ? AL_TRUE : AL_FALSE);
    }

    public SoundData loop(boolean loop) {
        return setParameter(AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
    }

    public SoundData gain(float gain) {
        return setParameter(AL_GAIN, gain);
    }

    public SoundData position(Vector3f position) {
        return setParameter(AL_POSITION, position);
    }

    public int getValue(int parameter) {
        return alGetSourcei(sourceId, parameter);
    }

    public SoundData setParameter(int parameter, int value) {
        alSourcei(sourceId, parameter, value);
        return this;
    }

    public SoundData setParameter(int parameter, float value) {
        alSourcef(sourceId, parameter, value);
        return this;
    }

    public SoundData setParameter(int parameter, Vector3f values) {
        alSource3f(sourceId, parameter, values.x, values.y, values.z);
        return this;
    }

    private ShortBuffer readAudio(FFmpegFrameGrabber grabber, String filePath) throws FFmpegFrameGrabber.Exception {
        grabber.start();

        int channels = grabber.getAudioChannels();
        if (channels == 0) {
            throw new RuntimeException("No audio channels found in file: " + filePath);
        }

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024).order(ByteOrder.LITTLE_ENDIAN);
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();

        Frame frame;
        while ((frame = grabber.grabSamples()) != null) {
            ShortBuffer audioBuffer = (ShortBuffer) frame.samples[0];
            if (shortBuffer.remaining() < audioBuffer.remaining()) {
                int newCapacity = Math.max(shortBuffer.capacity() * 2, shortBuffer.capacity() + audioBuffer.remaining());
                ByteBuffer newByteBuffer = ByteBuffer.allocateDirect(newCapacity * 2);
                ShortBuffer newShortBuffer = newByteBuffer.asShortBuffer();
                shortBuffer.flip();
                newShortBuffer.put(shortBuffer);
                shortBuffer = newShortBuffer;
            }
            shortBuffer.put(audioBuffer);
        }

        grabber.stop();
        shortBuffer.flip();
        return shortBuffer;
    }

    private ShortBuffer readVorbis(String filePath, STBVorbisInfo info) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer error = stack.mallocInt(1);
            long decoder = stb_vorbis_open_filename(filePath, error, null);
            if (decoder == NULL) {
                throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0) + " path: " + filePath);
            }

            stb_vorbis_get_info(decoder, info);

            int channels = info.channels();
            int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);

            ShortBuffer result = MemoryUtil.memAllocShort(lengthSamples * channels);
            result.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, result) * channels);
            stb_vorbis_close(decoder);

            return result;
        }
    }
}
