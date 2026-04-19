package me.hannsi.lfjg.render.system.shader;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.math.io.IOUtil;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.testRender.debug.exceptions.shader.CompilingShaderException;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GLSLCode {
    private static final Pattern INCLUDE_PATTERN = Pattern.compile("(//.*)|(/\\*[\\s\\S]*?\\*/)|#include\\s+\"([^\"]+)\"");
    private static final Pattern EXTENSION_PATTERN = Pattern.compile("(//.*)|(/\\*[\\s\\S]*?\\*/)|(#extension\\s+([\\w_]+)\\s*:\\s*(\\w+)\\s*\\[\\[\\s*define\\s*\\(\\s*([\\w_]+)\\s*\\)\\s*]])");
    private static final Pattern FUNCTION_IS_SUPPORTED_PATTERN = Pattern.compile("(//.*)|(/\\*[\\s\\S]*?\\*/)|(__is_supported\\s*\\(\\s*([\\w_]+)\\s*\\))");

    private final Set<String> includedFiles = new HashSet<>();
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
        ).logging(getClass(), DebugLevel.DEBUG);
    }

    public boolean isExtensionSupported(String extensionName) {
        try {
            GLCapabilities caps = GL.getCapabilities();
            java.lang.reflect.Field field = caps.getClass().getField(extensionName);
            return field.getBoolean(caps);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }

    private String processExtensions(String shaderCode) {
        Matcher matcher = EXTENSION_PATTERN.matcher(shaderCode);
        StringBuilder sb = new StringBuilder();
        int lastEnd = 0;

        while (matcher.find()) {
            sb.append(shaderCode, lastEnd, matcher.start());

            if (matcher.group(3) != null) {
                String extensionName = matcher.group(4);
                String behavior = matcher.group(5);
                String defineName = matcher.group(6);

                sb.append("#extension ").append(extensionName).append(" : ").append(behavior);

                int value = isExtensionSupported(extensionName) ? 1 : 0;
                sb.append("\n#define ").append(defineName).append(" ").append(value);
            } else {
                sb.append(matcher.group(0));
            }

            lastEnd = matcher.end();
        }
        sb.append(shaderCode.substring(lastEnd));
        return sb.toString();
    }

    private String processFunctionIsSupported(String shaderCode) {
        Matcher matcher = FUNCTION_IS_SUPPORTED_PATTERN.matcher(shaderCode);
        StringBuilder sb = new StringBuilder();
        int lastEnd = 0;

        while (matcher.find()) {
            sb.append(shaderCode, lastEnd, matcher.start());

            if (matcher.group(3) != null) {
                String extensionName = matcher.group(4);
                sb.append(isExtensionSupported(extensionName) ? "1" : "0");
            } else {
                sb.append(matcher.group(0));
            }

            lastEnd = matcher.end();
        }
        sb.append(shaderCode.substring(lastEnd));
        return sb.toString();
    }

    private String processIncludes(String shaderCode) {
        Matcher matcher = INCLUDE_PATTERN.matcher(shaderCode);
        StringBuilder stringBuilder = new StringBuilder();
        int lastEnd = 0;
        boolean foundAnyInclude = false;

        while (matcher.find()) {
            stringBuilder.append(shaderCode, lastEnd, matcher.start());

            if (matcher.group(3) != null) {
                String includeFilePath = matcher.group(3);

                if (includedFiles.contains(includeFilePath)) {
                    lastEnd = matcher.end();
                    continue;
                }

                includedFiles.add(includeFilePath);
                foundAnyInclude = true;

                String includeCode;
                try {
                    includeCode = IOUtil.readInputStreamToString(
                            Location.fromResource(includeFilePath).openStream()
                    );
                } catch (IOException e) {
                    throw new CompilingShaderException(e);
                }

                stringBuilder.append(processIncludes(includeCode));
            } else {
                stringBuilder.append(matcher.group(0));
            }

            lastEnd = matcher.end();
        }

        stringBuilder.append(shaderCode.substring(lastEnd));
        this.checkInclude = foundAnyInclude;

        return stringBuilder.toString();
    }

    public String createCode() {
        String shaderCode;
        try {
            shaderCode = IOUtil.readInputStreamToString(fileLocation.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        shaderCode = processIncludes(shaderCode);

        shaderCode = processExtensions(shaderCode);

        shaderCode = processFunctionIsSupported(shaderCode);

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