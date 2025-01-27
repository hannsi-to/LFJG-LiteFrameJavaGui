package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.utils.reflection.FileLocation;
import me.hannsi.lfjg.utils.type.types.SoundLoaderType;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
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

public class SoundBuffer {
    private final int bufferId;
    private final ShortBuffer pcm;
    private final FileLocation fileLocation;
    private final SoundLoaderType soundLoaderType;

    public SoundBuffer(SoundLoaderType soundLoaderType, FileLocation fileLocation) {
        this.fileLocation = fileLocation;
        this.soundLoaderType = soundLoaderType;

        this.bufferId = alGenBuffers();

        switch (soundLoaderType) {
            case STBVorbis -> {
                try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
                    pcm = readVorbis(fileLocation.getPath(), info);

                    alBufferData(bufferId, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
                }
            }
            case JavaCV -> {
                try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(fileLocation.getPath())) {
                    pcm = readAudio(grabber);

                    int sampleRate = grabber.getSampleRate();
                    int channels = grabber.getAudioChannels();
                    int format = (channels == 1) ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;
                    alBufferData(bufferId, format, pcm, sampleRate);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to load audio file: " + fileLocation.getPath(), e);
                }

            }
            default -> throw new IllegalStateException("Unexpected value: " + soundLoaderType);
        }
    }

    public void cleanup() {
        alDeleteBuffers(this.bufferId);
        if (pcm != null) {
            MemoryUtil.memFree(pcm);
        }
    }

    public int getBufferId() {
        return this.bufferId;
    }

    private ShortBuffer readAudio(FFmpegFrameGrabber grabber) throws FFmpegFrameGrabber.Exception {
        grabber.start();

        int channels = grabber.getAudioChannels();

        if (channels == 0) {
            throw new RuntimeException("No audio channels found in file: " + fileLocation.getPath());
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

    public ShortBuffer getPcm() {
        return pcm;
    }

    public FileLocation getFileLocation() {
        return fileLocation;
    }

    public SoundLoaderType getSoundLoaderType() {
        return soundLoaderType;
    }
}