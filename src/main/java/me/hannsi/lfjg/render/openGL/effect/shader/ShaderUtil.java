package me.hannsi.lfjg.render.openGL.effect.shader;

import me.hannsi.lfjg.render.openGL.renderers.polygon.GLPolygon;
import me.hannsi.lfjg.utils.graphics.GL11Util;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class ShaderUtil {
    private GLSLSandboxShader glslSandboxShader;

    public ShaderUtil(ResourcesLocation fragmentShader) {
        try {
            this.glslSandboxShader = new GLSLSandboxShader(fragmentShader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GLSLSandboxShader getGlslSandboxShader() {
        return glslSandboxShader;
    }

    public void setGlslSandboxShader(GLSLSandboxShader glslSandboxShader) {
        this.glslSandboxShader = glslSandboxShader;
    }
}
