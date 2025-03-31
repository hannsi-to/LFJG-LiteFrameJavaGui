package me.hannsi.lfjg.audio.openAL;

import me.hannsi.lfjg.debug.debug.system.DebugLevel;
import me.hannsi.lfjg.debug.debug.log.LogGenerator;
import me.hannsi.lfjg.frame.setting.settings.OpenALDebugSetting;
import me.hannsi.lfjg.utils.reflection.StackTraceUtil;

import static me.hannsi.lfjg.frame.frame.LFJGContext.frame;
import static org.lwjgl.openal.AL10.*;

/**
 * Provides debugging utilities for OpenAL.
 */
public class OpenALDebug {
    /**
     * Checks for OpenAL errors and logs them if any are found.
     */
    public static void getOpenALError(String ignoreMethod) {
        int error = alGetError();
        if (error != AL_NO_ERROR) {
            if (!(boolean) frame.getFrameSettingValue(OpenALDebugSetting.class)) {
                return;
            }

            String errorMessage = getALErrorString(error);
            String stackTrace = StackTraceUtil.getStackTrace(frame.getThreadName(), "getStackTrace", "getOpenALError", ignoreMethod);

            LogGenerator logGenerator = new LogGenerator(" OpenAL Debug Message", "Type: Error", "ID: " + error, "Severity: High", "Message: " + errorMessage, "Stack Trace: \n" + stackTrace);
            logGenerator.logging(DebugLevel.ERROR);
        }
    }

    /**
     * Converts an OpenAL error code to a human-readable string.
     *
     * @param error the OpenAL error code
     * @return the corresponding error message
     */
    public static String getALErrorString(int error) {
        return switch (error) {
            case AL_INVALID_NAME -> "Invalid Name";
            case AL_INVALID_ENUM -> "Invalid Enum";
            case AL_INVALID_VALUE -> "Invalid Value";
            case AL_INVALID_OPERATION -> "Invalid Operation";
            case AL_OUT_OF_MEMORY -> "Out of Memory";
            default -> "Unknown Error";
        };
    }
}