package me.hannsi.lfjg.utils.graphics.video;

import java.nio.ShortBuffer;

public class AudioData {
    private ShortBuffer samplesBuffer;
    private int sampleRate;
    private int channels;

    public AudioData(ShortBuffer samplesBuffer, int sampleRate, int channels) {
        this.samplesBuffer = samplesBuffer;
        this.sampleRate = sampleRate;
        this.channels = channels;
    }

    public ShortBuffer getSamplesBuffer() {
        return samplesBuffer;
    }

    public void setSamplesBuffer(ShortBuffer samplesBuffer) {
        this.samplesBuffer = samplesBuffer;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }
}
