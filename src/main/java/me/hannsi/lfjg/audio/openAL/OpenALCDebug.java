package me.hannsi.lfjg.audio.openAL;

import me.hannsi.lfjg.debug.debug.system.DebugLevel;
import me.hannsi.lfjg.debug.debug.log.LogGenerator;
import me.hannsi.lfjg.frame.setting.settings.OpenALDebugSetting;
import me.hannsi.lfjg.utils.reflection.StackTraceUtil;
import org.lwjgl.openal.ALC10;

import static me.hannsi.lfjg.audio.LFJGOpenALContext.openALDevice;
import static me.hannsi.lfjg.frame.frame.LFJGContext.frame;

public class OpenALCDebug {

    /**
     * Checks for OpenALC errors and logs them if any are found.
     *
     * @param ignoreMethod the method name to be ignored in stack trace
     */
    public static void getOpenALCError(String ignoreMethod) {
        int error = ALC10.alcGetError(openALDevice);
        if (error != ALC10.ALC_NO_ERROR) {
            if (!(boolean) frame.getFrameSettingValue(OpenALDebugSetting.class)) {
                return;
            }

            String errorMessage = getALCErrorString(error);
            String stackTrace = StackTraceUtil.getStackTrace(frame.getThreadName(), "getStackTrace", "getOpenALCError", ignoreMethod);

            LogGenerator logGenerator = new LogGenerator(" OpenALC Debug Message", "Type: Error", "ID: " + error, "Severity: High", "Message: " + errorMessage, "Stack Trace: \n" + stackTrace);
            logGenerator.logging(DebugLevel.ERROR);
        }
    }

    /**
     * Converts an OpenALC error code to a human-readable string.
     *
     * @param error the OpenALC error code
     * @return the corresponding error message
     */
    public static String getALCErrorString(int error) {
        return switch (error) {
            case ALC10.ALC_INVALID_DEVICE -> "Invalid Device";
            case ALC10.ALC_INVALID_CONTEXT -> "Invalid Context";
            case ALC10.ALC_INVALID_ENUM -> "Invalid Enum";
            case ALC10.ALC_INVALID_VALUE -> "Invalid Value";
            case ALC10.ALC_OUT_OF_MEMORY -> "Out of Memory";
            default -> "Unknown Error";
        };
    }
}
