package me.hannsi.lfjg.render.openGL.effect.shader;

import me.hannsi.lfjg.utils.graphics.GL11Util;
import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import me.hannsi.lfjg.utils.type.types.DrawType;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class ShaderUtil {
    private GLSLSandboxShader glslSandboxShader;

    public ShaderUtil(ResourcesLocation fragmentShader) {
        try {
            this.glslSandboxShader = new GLSLSandboxShader(fragmentShader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawShader(float x1, float y1, float x2, float y2) {
        GL11.glPushMatrix();

        GL11Util gl11Util = new GL11Util();
        gl11Util.addGL11Target(GL11.GL_CULL_FACE, true);
        gl11Util.enableTargets();

        glslSandboxShader.useShader();

        float screenWidth = 1920f;
        float screenHeight = 1080f;

        float normX1 = (2 * x1) / screenWidth - 1;
        float normY1 = (2 * y1) / screenHeight - 1;

        float normX2 = (2 * x2) / screenWidth - 1;
        float normY2 = (2 * y2) / screenHeight - 1;

        GL11.glBegin(DrawType.QUADS.getId());
        GL11.glVertex2f(normX1, normY1);
        GL11.glVertex2f(normX1, normY2);
        GL11.glVertex2f(normX2, normY2);
        GL11.glVertex2f(normX2, normY1);
        GL11.glEnd();

        glslSandboxShader.finishShader();

        gl11Util.disableTargets();
        gl11Util.finish();
        GL11.glPopMatrix();
    }

    public GLSLSandboxShader getGlslSandboxShader() {
        return glslSandboxShader;
    }

    public void setGlslSandboxShader(GLSLSandboxShader glslSandboxShader) {
        this.glslSandboxShader = glslSandboxShader;
    }


}
