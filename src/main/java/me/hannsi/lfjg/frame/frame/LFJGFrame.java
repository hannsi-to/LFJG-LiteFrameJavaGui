package me.hannsi.lfjg.frame.frame;

/**
 * The LFJGFrame interface defines the methods required for initializing, drawing, stopping, and setting frame settings.
 */
public interface LFJGFrame {
    /**
     * Initializes the frame.
     */
    void init();

    /**
     * Draws the frame.
     */
    void drawFrame();

    /**
     * Stops the frame.
     */
    void stopFrame();

    /**
     * Sets the frame settings.
     */
    void setFrameSetting();
}
