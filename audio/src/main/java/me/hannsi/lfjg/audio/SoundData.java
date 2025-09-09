package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import org.joml.Vector3f;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class SoundData {
    private final int sourceId;
    private final int bufferId;

    private SoundEffect soundEffect;
    private ShortBuffer pcm;

    SoundData() {
        this.sourceId = AL10.alGenSources();
        this.bufferId = AL10.alGenBuffers();
    }

    public static SoundData createSoundData() {
        return new SoundData();
    }

    public SoundData createSoundPCM(SoundLoaderType soundLoaderType, Location fileLocation) {
        switch (soundLoaderType) {
            case STB_VORBIS:
                try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
                    pcm = readVorbis(fileLocation.path(), info);
                    updatePCMData(info.sample_rate(), info.channels());
                }
                break;
            case JAVA_CV://                try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(fileLocation.path())) {
//                    pcm = readAudio(grabber, fileLocation.path());
//
//                    int sampleRate = grabber.getSampleRate();
//                    int channels = grabber.getAudioChannels();
//                    int format = (channels == 1) ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;
//                    updatePCMData(sampleRate, format);
//                } catch (Exception e) {
//                    throw new RuntimeException("Failed to load audio file: " + fileLocation.path(), e);
//                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + soundLoaderType);
        }

        return this;
    }

    public SoundData attachBuffer() {
        stop();
        setParameter(AL10.AL_BUFFER, bufferId);
        return this;
    }

    public SoundData attachEffect(SoundEffect soundEffect) {
        this.soundEffect = soundEffect;
        soundEffect.sendEffectSlot(sourceId);

        return this;
    }

    public void cleanup() {
        stop();
        soundEffect.cleanup();

        AL10.alDeleteBuffers(sourceId);
        AL10.alDeleteBuffers(bufferId);
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
        AL10.alBufferData(bufferId, format == 1 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16, pcm, sampleRate);
    }

    public SoundData play() {
        AL10.alSourcePlay(sourceId);
        return this;
    }

    public SoundData stop() {
        AL10.alSourceStop(sourceId);
        return this;
    }

    public SoundData pause() {
        AL10.alSourcePause(sourceId);
        return this;
    }

    public boolean isPlaying() {
        return getValue(AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

    public SoundData relative(boolean relative) {
        return setParameter(AL10.AL_SOURCE_RELATIVE, relative ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    public SoundData loop(boolean loop) {
        return setParameter(AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    public SoundData gain(float gain) {
        return setParameter(AL10.AL_GAIN, gain);
    }

    public SoundData position(Vector3f position) {
        return setParameter(AL10.AL_POSITION, position);
    }

    public int getValue(int parameter) {
        return AL10.alGetSourcei(sourceId, parameter);
    }

    public SoundData setParameter(int parameter, int value) {
        AL10.alSourcei(sourceId, parameter, value);
        return this;
    }

    public SoundData setParameter(int parameter, float value) {
        AL10.alSourcef(sourceId, parameter, value);
        return this;
    }

    public SoundData setParameter(int parameter, Vector3f values) {
        AL10.alSource3f(sourceId, parameter, values.x, values.y, values.z);
        return this;
    }

//    private ShortBuffer readAudio(FFmpegFrameGrabber grabber, String filePath) throws FFmpegFrameGrabber.Exception {
//        grabber.start();
//
//        int channels = grabber.getAudioChannels();
//        if (channels == 0) {
//            throw new RuntimeException("No audio channels found in file: " + filePath);
//        }
//
//        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024).order(ByteOrder.LITTLE_ENDIAN);
//        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
//
//        Frame frame;
//        while ((frame = grabber.grabSamples()) != null) {
//            ShortBuffer audioBuffer = (ShortBuffer) frame.samples[0];
//            if (shortBuffer.remaining() < audioBuffer.remaining()) {
//                int newCapacity = Math.max(shortBuffer.capacity() * 2, shortBuffer.capacity() + audioBuffer.remaining());
//                ByteBuffer newByteBuffer = ByteBuffer.allocateDirect(newCapacity * 2);
//                ShortBuffer newShortBuffer = newByteBuffer.asShortBuffer();
//                shortBuffer.flip();
//                newShortBuffer.put(shortBuffer);
//                shortBuffer = newShortBuffer;
//            }
//            shortBuffer.put(audioBuffer);
//        }
//
//        grabber.stop();
//        shortBuffer.flip();
//        return shortBuffer;
//    }

    private ShortBuffer readVorbis(String filePath, STBVorbisInfo info) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer error = stack.mallocInt(1);
            long decoder = STBVorbis.stb_vorbis_open_filename(filePath, error, null);
            if (decoder == MemoryUtil.NULL) {
                throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0) + " path: " + filePath);
            }

            STBVorbis.stb_vorbis_get_info(decoder, info);

            int channels = info.channels();
            int lengthSamples = STBVorbis.stb_vorbis_stream_length_in_samples(decoder);

            ShortBuffer result = MemoryUtil.memAllocShort(lengthSamples * channels);
            result.limit(STBVorbis.stb_vorbis_get_samples_short_interleaved(decoder, channels, result) * channels);
            STBVorbis.stb_vorbis_close(decoder);

            return result;
        }
    }
}
