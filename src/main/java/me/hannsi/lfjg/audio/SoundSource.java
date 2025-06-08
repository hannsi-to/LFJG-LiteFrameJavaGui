package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerator;
import org.joml.Vector3f;

import static org.lwjgl.openal.AL10.*;

/**
 * The SoundSource class is responsible for managing sound sources in the audio environment.
 */
public class SoundSource {
    private final int sourceId;

    private SoundEffect soundEffect;

    /**
     * Constructs a SoundSource object and sets the looping and relative properties.
     *
     * @param loop     Whether the sound source should loop.
     * @param relative Whether the sound source should be relative to the listener.
     */
    public SoundSource(boolean loop, boolean relative) {
        this.sourceId = alGenSources();

        alSourcei(sourceId, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
        alSourcei(sourceId, AL_SOURCE_RELATIVE, relative ? AL_TRUE : AL_FALSE);
    }

    /**
     * Cleans up the resources associated with this SoundSource.
     */
    public void cleanup() {
        stop();
        soundEffect.cleanup();

        alDeleteSources(sourceId);

        LogGenerator logGenerator = new LogGenerator("SoundSource", "Source: SoundSource", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: SoundSource cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Checks if the sound source is currently playing.
     *
     * @return True if the sound source is playing, false otherwise.
     */
    public boolean isPlaying() {
        return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
    }

    /**
     * Pauses the sound source.
     */
    public void pause() {
        alSourcePause(sourceId);
    }

    /**
     * Plays the sound source.
     */
    public void play() {
        alSourcePlay(sourceId);
    }

    /**
     * Sets the buffer for the sound source.
     *
     * @param bufferId The buffer ID to set.
     */
    public void setBuffer(int bufferId) {
        stop();
        alSourcei(sourceId, AL_BUFFER, bufferId);
    }

    /**
     * Sets the gain (volume) of the sound source.
     *
     * @param gain The gain value to set.
     */
    public void setGain(float gain) {
        alSourcef(sourceId, AL_GAIN, gain);
    }

    /**
     * Sets the position of the sound source in the audio environment.
     *
     * @param position The new position of the sound source.
     */
    public void setPosition(Vector3f position) {
        alSource3f(sourceId, AL_POSITION, position.x, position.y, position.z);
    }

    /**
     * Stops the sound source.
     */
    public void stop() {
        alSourceStop(sourceId);
    }

    /**
     * Retrieves the source ID of this SoundSource.
     *
     * @return The source ID.
     */
    public int getSourceId() {
        return sourceId;
    }

    public SoundEffect getSoundEffect() {
        return soundEffect;
    }

    public void setSoundEffect(SoundEffect soundEffect) {
        this.soundEffect = soundEffect;
        soundEffect.sendEffectSlot(sourceId);
    }
}
