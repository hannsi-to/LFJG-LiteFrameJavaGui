package me.hannsi.lfjg.render.openGL.effect.shader;

import me.hannsi.lfjg.utils.buffer.ByteUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GLSLCode {
    private final ResourcesLocation resourcesLocation;

    public GLSLCode(ResourcesLocation resourcesLocation) {
        this.resourcesLocation = resourcesLocation;
    }

    public static String processIncludes(String shaderCode) {
        Pattern pattern = Pattern.compile("#include\\s+\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(shaderCode);

        while (matcher.find()) {
            String includeFilePath = matcher.group(1);
            String includeCode = ByteUtil.readInputStreamToString(new ResourcesLocation(includeFilePath).getInputStream());
            shaderCode = shaderCode.replace(matcher.group(0), includeCode);
        }

        return shaderCode;
    }

    public String createCode() {
        String shaderCode = ByteUtil.readInputStreamToString(resourcesLocation.getInputStream());

        return processIncludes(shaderCode);
    }

    public ResourcesLocation getResourcesLocation() {
        return resourcesLocation;
    }
}
