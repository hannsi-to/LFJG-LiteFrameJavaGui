package me.hannsi.lfjg.frame.setting.system;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.reflection.ClassUtil;
import me.hannsi.lfjg.core.utils.time.TimeCalculator;
import me.hannsi.lfjg.core.utils.toolkit.ANSIFormat;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.CheckSeveritiesSetting;
import me.hannsi.lfjg.frame.setting.settings.SeverityType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class FrameSettingManager {
    private final List<FrameSettingBase<?>> frameSettings;

    private Frame frame;

    public FrameSettingManager() {
        this.frameSettings = new ArrayList<>();
    }

    public void updateFrameSettings(boolean windowHint) {
        StringBuilder sb = new StringBuilder().append("\n\nFrameSettings Updating...\n\n");
        long tookTime = TimeCalculator.calculateMillis(() -> {
            for (FrameSettingBase<?> frameSettingBase : frameSettings) {
                boolean shouldUpdate = (windowHint && frameSettingBase.isWindowHint()) || (!windowHint && !frameSettingBase.isWindowHint());

                if (shouldUpdate) {
                    frameSettingBase.updateSetting();
                    StringBuilder value = new StringBuilder();
                    if (frameSettingBase instanceof CheckSeveritiesSetting) {
                        SeverityType[] values = ((CheckSeveritiesSetting) frameSettingBase).getValue();
                        int index = 0;
                        for (SeverityType severityType : values) {
                            if (index != 0) {
                                value.append(", ");
                            }
                            value.append(severityType);
                            index++;
                        }
                    } else {
                        value.append(frameSettingBase.getValue());
                    }
                    sb.append("\t[Updated FrameSetting] ").append(frameSettingBase.getName()).append(": ").append(value).append("\n");
                }
            }
        });

        sb.append("\n").append(ANSIFormat.GREEN + "FrameSettings took ").append(tookTime).append("ms to update!\n");
        DebugLog.debug(getClass(), sb.toString());
    }

    public FrameSettingBase<?> getFrameSetting(Class<? extends FrameSettingBase<?>> frameSettingBase) {
        FrameSettingBase<?> result = null;

        for (FrameSettingBase<?> subType : frameSettings) {
            if (subType.getClass() == frameSettingBase) {
                result = subType;

                break;
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public void loadFrameSettings() {
        Set<Class<? extends FrameSettingBase<?>>> subTypes = (Set<Class<? extends FrameSettingBase<?>>>) (Set<?>) ClassUtil.getClassesFromPackage("me.hannsi.lfjg.frame.setting.settings", FrameSettingBase.class);
        List<Class<? extends FrameSettingBase<?>>> sortedClasses = new ArrayList<>(subTypes);
        sortedClasses.sort(Comparator.comparingInt(clazz -> {
            ReflectionsLevel reflectionsLevel = clazz.getAnnotation(ReflectionsLevel.class);
            return reflectionsLevel != null ? reflectionsLevel.level() : Integer.MAX_VALUE;
        }));

        StringBuilder sb = new StringBuilder().append("\n\nFrameSettings loading...\n");
        long tookTime = TimeCalculator.calculateMillis(() -> {
            int count = 0;
            for (Class<? extends FrameSettingBase<?>> subType : sortedClasses) {
                FrameSettingBase<?> frameSettingBase = ClassUtil.createInstance(subType, frame);

                if (frameSettingBase != null) {
                    register(frameSettingBase);
                }

                assert frameSettingBase != null;
                sb.append("\n\t").append(count).append(".\t").append("Loaded FrameSetting: ").append(frameSettingBase.getName());

                count++;
            }

        });

        sb.append("\n\n").append(ANSIFormat.GREEN).append("FrameSettings took ").append(tookTime).append("ms to load!").append("\n");

        DebugLog.debug(getClass(), sb.toString());
    }

    public void cleanup() {
        frameSettings.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                hashCode(),
                ""
        ).logging(getClass(), DebugLevel.DEBUG);
    }

    public void register(FrameSettingBase<?> frameSetting) {
        frameSettings.add(frameSetting);
    }

    public List<FrameSettingBase<?>> getFrameSettings() {
        return frameSettings;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }
}