package me.hannsi.lfjg.utils.graphics.video;

import java.nio.ShortBuffer;

/**
 * Class representing audio data, including the samples buffer, sample rate, and number of channels.
 */
public class AudioData {
    private ShortBuffer samplesBuffer;
    private int sampleRate;
    private int channels;

    /**
     * Constructs an AudioData instance with the specified samples buffer, sample rate, and number of channels.
     *
     * @param samplesBuffer the buffer containing the audio samples
     * @param sampleRate the sample rate of the audio
     * @param channels the number of audio channels
     */
    public AudioData(ShortBuffer samplesBuffer, int sampleRate, int channels) {
        this.samplesBuffer = samplesBuffer;
        this.sampleRate = sampleRate;
        this.channels = channels;
    }

    /**
     * Gets the buffer containing the audio samples.
     *
     * @return the samples buffer
     */
    public ShortBuffer getSamplesBuffer() {
        return samplesBuffer;
    }

    /**
     * Sets the buffer containing the audio samples.
     *
     * @param samplesBuffer the new samples buffer
     */
    public void setSamplesBuffer(ShortBuffer samplesBuffer) {
        this.samplesBuffer = samplesBuffer;
    }

    /**
     * Gets the sample rate of the audio.
     *
     * @return the sample rate
     */
    public int getSampleRate() {
        return sampleRate;
    }

    /**
     * Sets the sample rate of the audio.
     *
     * @param sampleRate the new sample rate
     */
    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    /**
     * Gets the number of audio channels.
     *
     * @return the number of channels
     */
    public int getChannels() {
        return channels;
    }

    /**
     * Sets the number of audio channels.
     *
     * @param channels the new number of channels
     */
    public void setChannels(int channels) {
        this.channels = channels;
    }
}