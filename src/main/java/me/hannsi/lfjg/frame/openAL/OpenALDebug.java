package me.hannsi.lfjg.frame.openAL;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALCapabilities;

import static org.lwjgl.openal.AL10.*;

public class OpenALDebug {
    public static void getOpenALError() {
        ALCapabilities alCapabilities;
        try {
            alCapabilities = AL.getCapabilities();
        } catch (Exception e) {
            return;
        }

        if (alCapabilities.OpenAL11) {
            int error = alGetError();
            if (error != AL_NO_ERROR) {
                String errorMessage = getALErrorString(error);

                LogGenerator logGenerator = new LogGenerator(" OpenAL Debug Message", "Type: Error", "ID: " + error, "Severity: High", "Message: " + errorMessage);

                DebugLog.error(OpenALDebug.class, logGenerator.createLog());
            }
        }
    }

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
