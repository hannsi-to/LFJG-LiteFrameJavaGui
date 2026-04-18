package me.hannsi.lfjg.testRender.system.rendering;

import me.hannsi.lfjg.testRender.renderers.BlendType;
import me.hannsi.lfjg.testRender.system.mesh.DrawElementsIndirectCommand;
import me.hannsi.lfjg.testRender.system.shader.UploadUniformType;

import static me.hannsi.lfjg.testRender.LFJGRenderContext.*;
import static me.hannsi.lfjg.testRender.RenderSystemSetting.VAO_RENDERING_FRONT_AND_BACK;
import static me.hannsi.lfjg.testRender.RenderSystemSetting.VAO_RENDERING_FRONT_AND_BACK_LINE_WIDTH;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.glMultiDrawElementsIndirect;

public class VAORendering {
    public void draw() {
        if (VAO_RENDERING_FRONT_AND_BACK) {
            glStateCache.polygonMode(GL_FRONT_AND_BACK, GL_LINE);
            glStateCache.lineWidth(VAO_RENDERING_FRONT_AND_BACK_LINE_WIDTH);
        } else {
            glStateCache.polygonMode(GL_FRONT_AND_BACK, GL_FILL);
            glStateCache.lineWidth(1.0f);
        }

        if (mesh.isNeedRepack()) {
            vertexBufferObject.nextFrame();
            elementArrayBuffer.nextFrame();

            mesh.build();
            mesh.initBufferObject();
            mesh.setNeedRepack(false);

//            shaderStorageBufferObject.bindBufferRange();
        }

        mesh.update();

        vertexBufferObject.syncToGPU();
        elementArrayBuffer.syncToGPU();
        persistentMappedIBO.syncToGPU();
//        shaderStorageBufferObject.syncToGPU();
        persistentMappedPUBO.syncToGPU();

        shaderProgram.bind();
        shaderProgram.setUniform("uTexArray", UploadUniformType.ONCE, 0);
        while (drawBatch.nextPass()) {
            DrawBatch.Pass pass = drawBatch.getCurrentPass();

            shaderProgram.setUniform("baseDrawId", UploadUniformType.ON_CHANGE, pass.commandOffset);

            applyBlendState(pass.pipeline.blendType());

            glMultiDrawElementsIndirect(
                    GL_TRIANGLES,
                    GL_UNSIGNED_INT,
                    (long) pass.commandOffset * DrawElementsIndirectCommand.BYTES,
                    pass.commandCount,
                    0
            );
        }

        vertexBufferObject.endFrame();
        elementArrayBuffer.endFrame();
    }

    private void applyBlendState(BlendType type) {
        if (type.isBlend()) {
            glStateCache.enable(GL_BLEND);
            glStateCache.blendFuncSeparate(type.getSrcRGB(), type.getDstRGB(), type.getSrcA(), type.getDstA());
            glStateCache.blendEquationSeparate(type.getEqRGB(), type.getEqA());
        } else {
            glStateCache.disable(GL_BLEND);
        }

        if (type.isDepthTest()) {
            glStateCache.enable(GL_DEPTH_TEST);
        } else {
            glStateCache.disable(GL_DEPTH_TEST);
        }

        glStateCache.depthMask(type.isDepthWrite());
    }
}