package me.hannsi.lfjg.utils.graphics.video;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class for managing a cache of video frames.
 */
public class VideoCache {
    private List<Frame> frames;

    /**
     * Constructs a VideoCache instance and initializes the frame list.
     */
    public VideoCache() {
        this.frames = new CopyOnWriteArrayList<>();
    }

    /**
     * Creates a cache entry for the specified frame and audio data.
     *
     * @param frameData the frame data
     * @param audioData the audio data
     */
    public void createCache(FrameData frameData, AudioData audioData) {
        frames.add(new Frame(frameData, audioData));
    }

    /**
     * Gets the list of frames.
     *
     * @return the list of frames
     */
    public List<Frame> getFrames() {
        return frames;
    }

    /**
     * Sets the list of frames.
     *
     * @param frames the new list of frames
     */
    public void setFrames(List<Frame> frames) {
        this.frames = frames;
    }

    public void cleanup() {
        frames.forEach(frame -> {
            frame.getFrameData().cleanup();
            frame.getAudioData().cleanup();
        });

        frames.clear();
    }

    /**
     * Class representing a video frame, including frame data and audio data.
     */
    public static class Frame {
        private FrameData frameData;
        private AudioData audioData;

        /**
         * Constructs a Frame instance with the specified frame data and audio data.
         *
         * @param frameData the frame data
         * @param audioData the audio data
         */
        public Frame(FrameData frameData, AudioData audioData) {
            this.frameData = frameData;
            this.audioData = audioData;
        }

        /**
         * Gets the frame data.
         *
         * @return the frame data
         */
        public FrameData getFrameData() {
            return frameData;
        }

        /**
         * Sets the frame data.
         *
         * @param frameData the new frame data
         */
        public void setFrameData(FrameData frameData) {
            this.frameData = frameData;
        }

        /**
         * Gets the audio data.
         *
         * @return the audio data
         */
        public AudioData getAudioData() {
            return audioData;
        }

        /**
         * Sets the audio data.
         *
         * @param audioData the new audio data
         */
        public void setAudioData(AudioData audioData) {
            this.audioData = audioData;
        }
    }
}