package me.hannsi.lfjg.frame.manager.managers;

import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.frame.frame.Frame;
import me.hannsi.lfjg.frame.manager.Manager;
import me.hannsi.lfjg.frame.setting.settings.CheckSeveritiesSetting;
import me.hannsi.lfjg.frame.setting.system.FrameSettingBase;
import me.hannsi.lfjg.frame.setting.system.ReflectionsLevel;
import me.hannsi.lfjg.utils.math.ANSIFormat;
import me.hannsi.lfjg.utils.reflection.ClassUtil;
import me.hannsi.lfjg.utils.reflection.PackagePath;
import me.hannsi.lfjg.utils.time.TimeCalculator;
import me.hannsi.lfjg.utils.type.types.SeverityType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Manages frame settings for a given frame.
 */
public class FrameSettingManager extends Manager {
    private final List<FrameSettingBase<?>> frameSettings;

    /**
     * Constructs a new FrameSettingManager for the specified frame.
     *
     * @param frame the frame to manage settings for
     */
    public FrameSettingManager(Frame frame) {
        super(frame, "FrameSettingManager");
        this.frameSettings = new ArrayList<>();
        loadFrameSettings();
    }

    /**
     * Updates the frame settings.
     *
     * @param windowHint a flag indicating whether to update window hint settings
     */
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

    /**
     * Retrieves a frame setting by its class.
     *
     * @param frameSettingBase the class of the frame setting to retrieve
     * @return the frame setting, or null if not found
     */
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

    /**
     * Loads all frame settings.
     */
    @SuppressWarnings("unchecked")
    public void loadFrameSettings() {
        Set<Class<? extends FrameSettingBase<?>>> subTypes = (Set<Class<? extends FrameSettingBase<?>>>) (Set<?>) ClassUtil.getClassesFormPackage(PackagePath.frameSettings, FrameSettingBase.class);
        List<Class<? extends FrameSettingBase<?>>> sortedClasses = new ArrayList<>(subTypes);

        sortedClasses.sort(Comparator.comparingInt(clazz -> {
            ReflectionsLevel reflectionsLevel = clazz.getAnnotation(ReflectionsLevel.class);
            return reflectionsLevel != null ? reflectionsLevel.level() : Integer.MAX_VALUE;
        }));

        StringBuilder sb = new StringBuilder().append("\n\nFrameSettings loading...\n");
        long tookTime = TimeCalculator.calculateMillis(() -> {
            int count = 0;
            for (Class<? extends FrameSettingBase<?>> subType : sortedClasses) {
                FrameSettingBase<?> frameSettingBase = ClassUtil.createInstance(subType, getFrame());

                if (frameSettingBase != null) {
                    register(frameSettingBase);
                }

                //noinspection DataFlowIssue
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
        ).logging(DebugLevel.DEBUG);
    }

    /**
     * Registers a frame setting.
     *
     * @param frameSetting the frame setting to register
     */
    public void register(FrameSettingBase<?> frameSetting) {
        frameSettings.add(frameSetting);
    }

    /**
     * Retrieves all frame settings.
     *
     * @return a list of all frame settings
     */
    public List<FrameSettingBase<?>> getFrameSettings() {
        return frameSettings;
    }
}