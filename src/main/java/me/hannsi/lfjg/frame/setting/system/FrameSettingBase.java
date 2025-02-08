package me.hannsi.lfjg.frame.setting.system;

import me.hannsi.lfjg.frame.Frame;

/**
 * Base class for frame settings.
 *
 * @param <T> the type of the setting value
 */
public class FrameSettingBase<T> {
    private final Frame frame;
    private final T defaultValue;
    private String name;
    private int id;
    private T value;
    private final boolean windowHint;

    /**
     * Constructs a new FrameSettingBase with the specified parameters.
     *
     * @param frame the frame to associate with this setting
     * @param name the name of the setting
     * @param id the identifier of the setting
     * @param defaultValue the default value of the setting
     */
    public FrameSettingBase(Frame frame, String name, int id, T defaultValue) {
        this(frame, name, id, defaultValue, false);
    }

    /**
     * Constructs a new FrameSettingBase with the specified parameters.
     *
     * @param frame the frame to associate with this setting
     * @param name the name of the setting
     * @param id the identifier of the setting
     * @param defaultValue the default value of the setting
     * @param windowHint whether the setting is a window hint
     */
    public FrameSettingBase(Frame frame, String name, int id, T defaultValue, boolean windowHint) {
        this.frame = frame;
        this.name = name;
        this.id = id;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.windowHint = windowHint;
    }

    /**
     * Updates the setting. This method should be overridden by subclasses.
     */
    public void updateSetting() {
    }

    /**
     * Returns the frame associated with this setting.
     *
     * @return the frame
     */
    public Frame getFrame() {
        return frame;
    }

    /**
     * Returns the default value of the setting.
     *
     * @return the default value
     */
    public T getDefaultValue() {
        return defaultValue;
    }

    /**
     * Returns the name of the setting.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the setting.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the identifier of the setting.
     *
     * @return the identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the identifier of the setting.
     *
     * @param id the new identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the current value of the setting.
     *
     * @return the current value
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the current value of the setting.
     *
     * @param value the new value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Returns whether the setting is a window hint.
     *
     * @return true if the setting is a window hint, false otherwise
     */
    public boolean isWindowHint() {
        return windowHint;
    }
}