package me.hannsi.lfjg.frame.setting.system;

import me.hannsi.lfjg.frame.Frame;

public class FrameSettingBase<T> {
    private final Frame frame;
    private String name;
    private int id;
    private final T defaultValue;
    private T value;
    private boolean windowHint;
    
    public FrameSettingBase(Frame frame,String name,int id,T defaultValue){
        this(frame,name,id,defaultValue,false);
    }

    public FrameSettingBase(Frame frame, String name, int id, T defaultValue, boolean windowHint) {
        this.frame = frame;
        this.name = name;
        this.id = id;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.windowHint = windowHint;
    }

    public void updateSetting(){
    }

    public Frame getFrame() {
        return frame;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isWindowHint() {
        return windowHint;
    }
}
