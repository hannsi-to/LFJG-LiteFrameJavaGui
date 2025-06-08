package me.hannsi.lfjg.render.system.shader;

import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.utils.reflection.location.FileLocation;
import me.hannsi.lfjg.utils.reflection.location.ResourcesLocation;
import me.hannsi.lfjg.utils.toolkit.IOUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a GLSL (OpenGL Shading Language) code processor.
 * This class handles the inclusion of other shader files within a shader code.
 */
public class GLSLCode {
    private final FileLocation fileLocation;
    private boolean checkInclude;

    /**
     * Constructs a new GLSLCode instance with the specified resource location.
     *
     * @param fileLocation the location of the shader resource
     */
    public GLSLCode(FileLocation fileLocation) {
        this.fileLocation = fileLocation;
        checkInclude = true;
    }

    public void cleanup() {
        fileLocation.cleanup();

        LogGenerator logGenerator = new LogGenerator("GLSLCode", "Source: GLSLCode", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: GLSLCode cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Processes the includes within the shader code.
     * Recursively replaces #include directives with the content of the included files.
     *
     * @param shaderCode the shader code to process
     * @return the processed shader code with includes replaced
     */
    private String processIncludes(String shaderCode) {
        Pattern pattern = Pattern.compile("#include\\s+\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(shaderCode);

        checkInclude = false;
        while (matcher.find()) {
            checkInclude = true;

            String includeFilePath = matcher.group(1);
            String includeCode = IOUtil.readInputStreamToString(new ResourcesLocation(includeFilePath).getInputStream());
            shaderCode = shaderCode.replace(matcher.group(0), includeCode);
        }

        if (checkInclude) {
            shaderCode = processIncludes(shaderCode);
        }

        return shaderCode;
    }

    /**
     * Creates the final shader code by reading the shader file and processing includes.
     *
     * @return the final shader code
     */
    public String createCode() {
        String shaderCode = IOUtil.readInputStreamToString(fileLocation.getInputStream());
        shaderCode = processIncludes(shaderCode);

        return shaderCode;
    }

    /**
     * Gets the resource location of the shader.
     *
     * @return the resource location of the shader
     */
    public FileLocation getFileLocation() {
        return fileLocation;
    }
}