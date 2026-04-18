package me.hannsi.test;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class ComputeShaderExample {

    public static void main(String[] args) {
        // GLFW初期化
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("GLFW init failed");
        }

        // 非表示ウィンドウ（ComputeだけならOK）
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        long window = GLFW.glfwCreateWindow(640, 480, "Compute", NULL, NULL);

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        // ===== データ作成 =====
        float[] input = {1, 2, 3, 4};
        FloatBuffer buffer = BufferUtils.createFloatBuffer(input.length);
        buffer.put(input).flip();

        int ssbo = glGenBuffers();
        glBindBuffer(GL_SHADER_STORAGE_BUFFER, ssbo);
        glBufferData(GL_SHADER_STORAGE_BUFFER, buffer, GL_DYNAMIC_COPY);
        glBindBufferBase(GL_SHADER_STORAGE_BUFFER, 0, ssbo);

        // ===== シェーダー作成 =====
        String shaderSrc =
                "#version 430\n" +
                        "layout(local_size_x = 1) in;\n" +
                        "layout(std430, binding = 0) buffer Data { float values[]; };\n" +
                        "void main(){ uint id = gl_GlobalInvocationID.x; values[id] *= 2.0; }";

        int shader = glCreateShader(GL_COMPUTE_SHADER);
        glShaderSource(shader, shaderSrc);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException(glGetShaderInfoLog(shader));
        }

        int program = glCreateProgram();
        glAttachShader(program, shader);
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException(glGetProgramInfoLog(program));
        }

        // ===== 実行 =====
        glUseProgram(program);
        glDispatchCompute(input.length, 1, 1);

        // メモリ同期（超重要）
        glMemoryBarrier(GL_SHADER_STORAGE_BARRIER_BIT);

        // ===== 結果取得 =====
        glBindBuffer(GL_SHADER_STORAGE_BUFFER, ssbo);
        FloatBuffer result = glMapBuffer(GL_SHADER_STORAGE_BUFFER, GL_READ_ONLY).asFloatBuffer();

        System.out.println("結果:");
        for (int i = 0; i < input.length; i++) {
            System.out.println(result.get(i));
        }

        glUnmapBuffer(GL_SHADER_STORAGE_BUFFER);

        // 後処理
        glDeleteBuffers(ssbo);
        glDeleteProgram(program);
        glDeleteShader(shader);

        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}
