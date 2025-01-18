package me.hannsi.lfjg.render.openGL.system.shader;

import me.hannsi.lfjg.utils.buffer.ByteUtil;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GLSLCode {
    private final ResourcesLocation resourcesLocation;
    private boolean checkInclude;

    public GLSLCode(ResourcesLocation resourcesLocation) {
        this.resourcesLocation = resourcesLocation;
        checkInclude = true;
    }

    private String processIncludes(String shaderCode) {
        Pattern pattern = Pattern.compile("#include\\s+\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(shaderCode);

//        checkInclude = matcher.find();

        checkInclude = false;
        while (matcher.find()) {
            checkInclude = true;

            String includeFilePath = matcher.group(1);
            String includeCode = ByteUtil.readInputStreamToString(new ResourcesLocation(includeFilePath).getInputStream());
            shaderCode = shaderCode.replace(matcher.group(0), includeCode);
        }

        if (checkInclude) {
            shaderCode = processIncludes(shaderCode);
        }

        return shaderCode;
    }

    public String createCode() {
        if (resourcesLocation.getPath().equals(new ResourcesLocation("shader/frameBuffer/filter/Glow.fsh").getPath())) {
            System.out.println("A");
        }

        String shaderCode = ByteUtil.readInputStreamToString(resourcesLocation.getInputStream());

//        while (checkInclude){
        shaderCode = processIncludes(shaderCode);
//        }

        return shaderCode;
    }

    public ResourcesLocation getResourcesLocation() {
        return resourcesLocation;
    }
}
