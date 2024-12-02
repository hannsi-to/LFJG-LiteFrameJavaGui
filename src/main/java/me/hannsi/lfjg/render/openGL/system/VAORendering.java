package me.hannsi.lfjg.render.openGL.system;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.utils.graphics.GLUtil;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class VAORendering {
    private GLObject glObject;
    private GLUtil glUtil;

    public VAORendering() {
        this.glUtil = new GLUtil();
    }

    public void draw(GLObject glObject) {
        this.glObject = glObject;

        glUtil.enableTargets();

        glBindVertexArray(glObject.getMesh().getVaoId());

        int count;
        switch (glObject.getMesh().getProjectionType()) {
            case OrthographicProjection -> {
                count = glObject.getMesh().getPositions().length / 2;
            }
            case PerspectiveProjection -> {
                count = glObject.getMesh().getPositions().length / 3;
            }
            default -> throw new IllegalStateException("Unexpected value: " + glObject.getMesh().getProjectionType());
        }

        glDrawArrays(glObject.getDrawType().getId(), 0, count);

        glBindVertexArray(0);

        glUtil.disableTargets();
    }

    public void cleanup() {
        glObject.getMesh().cleanup();
        glUtil = null;
    }

    public GLObject getGlObject() {
        return glObject;
    }

    public void setGlObject(GLObject glObject) {
        this.glObject = glObject;
    }

    public GLUtil getGlUtil() {
        return glUtil;
    }

    public void setGlUtil(GLUtil glUtil) {
        this.glUtil = glUtil;
    }
}
