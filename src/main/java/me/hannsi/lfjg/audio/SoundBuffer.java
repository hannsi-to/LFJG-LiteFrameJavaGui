package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.debug.debug.system.DebugLevel;
import me.hannsi.lfjg.debug.debug.log.LogGenerator;
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

/**
 * The SoundBuffer class is responsible for loading and managing audio data buffers.
 */
public class SoundBuffer {
    private final int bufferId;
    private final ShortBuffer pcm;
    private final FileLocation fileLocation;
    private final SoundLoaderType soundLoaderType;

    /**
     * Constructs a SoundBuffer object and loads audio data from the specified file location.
     *
     * @param soundLoaderType The type of sound loader to use (STBVorbis or JavaCV).
     * @param fileLocation    The location of the audio file.
     */
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

    /**
     * Cleans up the resources associated with this SoundBuffer.
     */
    public void cleanup() {
        fileLocation.cleanup();
        alDeleteBuffers(this.bufferId);
        if (pcm != null) {
            MemoryUtil.memFree(pcm);
        }

        LogGenerator logGenerator = new LogGenerator("SoundBuffer", "Source: SoundBuffer", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: SoundBuffer cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Retrieves the buffer ID of this SoundBuffer.
     *
     * @return The buffer ID.
     */
    public int getBufferId() {
        return this.bufferId;
    }

    /**
     * Reads audio data from an FFmpegFrameGrabber and returns it as a ShortBuffer.
     *
     * @param grabber The FFmpegFrameGrabber to read audio data from.
     * @return The ShortBuffer containing the audio data.
     * @throws FFmpegFrameGrabber.Exception If an error occurs while reading the audio data.
     */
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

    /**
     * Reads audio data from an Ogg Vorbis file and returns it as a ShortBuffer.
     *
     * @param filePath The path to the Ogg Vorbis file.
     * @param info     The STBVorbisInfo object to store the audio file information.
     * @return The ShortBuffer containing the audio data.
     */
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

    /**
     * Retrieves the PCM data of this SoundBuffer.
     *
     * @return The ShortBuffer containing the PCM data.
     */
    public ShortBuffer getPcm() {
        return pcm;
    }

    /**
     * Retrieves the file location of this SoundBuffer.
     *
     * @return The FileLocation object representing the file location.
     */
    public FileLocation getFileLocation() {
        return fileLocation;
    }

    /**
     * Retrieves the sound loader type of this SoundBuffer.
     *
     * @return The SoundLoaderType of this SoundBuffer.
     */
    public SoundLoaderType getSoundLoaderType() {
        return soundLoaderType;
    }
}
