package me.hannsi.lfjg.utils.graphics.video;

import me.hannsi.lfjg.utils.graphics.audio.AudioFrameData;

public class FrameData {
    private VideoFrameData videoFrameData;
    private AudioFrameData audioFrameData;

    public FrameData(VideoFrameData videoFrameData, AudioFrameData audioFrameData) {
        this.videoFrameData = videoFrameData;
        this.audioFrameData = audioFrameData;
    }

    public VideoFrameData getVideoFrameData() {
        return videoFrameData;
    }

    public void setVideoFrameData(VideoFrameData videoFrameData) {
        this.videoFrameData = videoFrameData;
    }

    public AudioFrameData getAudioFrameData() {
        return audioFrameData;
    }

    public void setAudioFrameData(AudioFrameData audioFrameData) {
        this.audioFrameData = audioFrameData;
    }
}
