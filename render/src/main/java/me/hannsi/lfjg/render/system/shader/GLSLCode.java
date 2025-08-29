package me.hannsi.lfjg.render.system.shader;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.math.io.IOUtil;
import me.hannsi.lfjg.core.utils.reflection.location.Location;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GLSLCode {
    private final Location fileLocation;
    private boolean checkInclude;

    public GLSLCode(Location fileLocation) {
        this.fileLocation = fileLocation;
        checkInclude = true;
    }

    public void cleanup() {
        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                hashCode(),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    private String processIncludes(String shaderCode) {
        Pattern pattern = Pattern.compile("#include\\s+\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(shaderCode);

        checkInclude = false;
        while (matcher.find()) {
            checkInclude = true;

            String includeFilePath = matcher.group(1);
            String includeCode;
            try {
                includeCode = IOUtil.readInputStreamToString(Location.fromResource(includeFilePath).openStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            shaderCode = shaderCode.replace(matcher.group(0), includeCode);
        }

        if (checkInclude) {
            shaderCode = processIncludes(shaderCode);
        }

        return shaderCode;
    }

    public String createCode() {
        String shaderCode;
        try {
            shaderCode = IOUtil.readInputStreamToString(fileLocation.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        shaderCode = processIncludes(shaderCode);

        return shaderCode;
    }

    public Location getFileLocation() {
        return fileLocation;
    }

    public boolean isCheckInclude() {
        return checkInclude;
    }

    public void setCheckInclude(boolean checkInclude) {
        this.checkInclude = checkInclude;
    }
}