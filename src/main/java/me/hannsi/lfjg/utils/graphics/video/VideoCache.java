package me.hannsi.lfjg.utils.graphics.video;

import org.bytedeco.ffmpeg.avcodec.AVCodecContext;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class VideoCache {
    private List<Frame> frames;

    public VideoCache() {
        this.frames = new ArrayList<>();
    }

    public void createCache(FrameData frameData, AudioData audioData) {
        frames.add(new Frame(frameData, audioData));
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public void setFrames(List<Frame> frames) {
        this.frames = frames;
    }

    public static class Frame {
        private FrameData frameData;
        private AudioData audioData;

        public Frame(FrameData frameData, AudioData audioData) {
            this.frameData = frameData;
            this.audioData = audioData;
        }

        public FrameData getFrameData() {
            return frameData;
        }

        public void setFrameData(FrameData frameData) {
            this.frameData = frameData;
        }

        public AudioData getAudioData() {
            return audioData;
        }

        public void setAudioData(AudioData audioData) {
            this.audioData = audioData;
        }
    }
}
