package me.hannsi.lfjg.render.uitl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.NVXGPUMemoryInfo.GL_GPU_MEMORY_INFO_TOTAL_AVAILABLE_MEMORY_NVX;

public class GPUUtil {
    public static String getVendor() {
        return glGetString(GL_VENDOR);
    }

    public static String getRenderer() {
        return glGetString(GL_RENDERER);
    }

    public static String getVersion() {
        return glGetString(GL_VERSION);
    }

    public static String getShadingLanguageVersion() {
        return glGetString(GL_SHADING_LANGUAGE_VERSION);
    }


    public static String[] getExtensions() {
        int count = glGetInteger(GL_NUM_EXTENSIONS);
        String[] result = new String[count];

        for (int i = 0; i < count; i++) {
            result[i] = glGetStringi(GL_EXTENSIONS, i);
        }
        return result;
    }

    public static boolean hasExtension(String ext) {
        for (String e : getExtensions()) {
            if (e.equals(ext)) {
                return true;
            }
        }

        return false;
    }

    public static int getMajorVersion() {
        return glGetInteger(GL_MAJOR_VERSION);
    }

    public static int getMinorVersion() {
        return glGetInteger(GL_MINOR_VERSION);
    }

    public static GPUVendor getVendorType() {
        String vendor = getVendor().toLowerCase();

        if (vendor.contains("nvidia")) {
            return GPUVendor.NVIDIA;
        }
        if (vendor.contains("amd") || vendor.contains("ati")) {
            return GPUVendor.AMD;
        }
        if (vendor.contains("intel")) {
            return GPUVendor.INTEL;
        }

        return GPUVendor.UNKNOWN;
    }

    public static long getTotalVRAM() {
        if (!hasExtension("GL_NVX_gpu_memory_info")) {
            return -1;
        }

        return glGetInteger(GL_GPU_MEMORY_INFO_TOTAL_AVAILABLE_MEMORY_NVX) * 1024L;
    }
}
