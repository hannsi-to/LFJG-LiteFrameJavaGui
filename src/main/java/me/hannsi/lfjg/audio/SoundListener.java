package me.hannsi.lfjg.audio;

import org.joml.Vector3f;

import static org.lwjgl.openal.AL10.*;

/**
 * The SoundListener class is responsible for managing the listener's position, orientation, and speed in the audio environment.
 */
public class SoundListener {

    /**
     * Constructs a SoundListener object and sets the initial position of the listener.
     *
     * @param position The initial position of the listener.
     */
    public SoundListener(Vector3f position) {
        alListener3f(AL_POSITION, position.x, position.y, position.z);
        alListener3f(AL_VELOCITY, 0, 0, 0);
    }

    /**
     * Sets the orientation of the listener in the audio environment.
     *
     * @param at The "at" vector representing the direction the listener is facing.
     * @param up The "up" vector representing the upward direction for the listener.
     */
    public void setOrientation(Vector3f at, Vector3f up) {
        float[] data = new float[6];
        data[0] = at.x;
        data[1] = at.y;
        data[2] = at.z;
        data[3] = up.x;
        data[4] = up.y;
        data[5] = up.z;
        alListenerfv(AL_ORIENTATION, data);
    }

    /**
     * Sets the position of the listener in the audio environment.
     *
     * @param position The new position of the listener.
     */
    public void setPosition(Vector3f position) {
        alListener3f(AL_POSITION, position.x, position.y, position.z);
    }

    /**
     * Sets the speed of the listener in the audio environment.
     *
     * @param speed The new speed of the listener.
     */
    public void setSpeed(Vector3f speed) {
        alListener3f(AL_VELOCITY, speed.x, speed.y, speed.z);
    }
}
