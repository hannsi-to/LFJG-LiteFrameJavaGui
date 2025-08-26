package me.hannsi.lfjg.render;

import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.render.system.font.FontCache;
import me.hannsi.lfjg.render.system.shader.ShaderProgram;
import me.hannsi.lfjg.render.system.svg.SVGCache;

public class LFJGRenderContext {
    public static ShaderProgram shaderProgram;
    public static SVGCache svgCache;

    static {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Location.fromResource("shader/VertexShader.vsh"));
        shaderProgram.createFragmentShader(Location.fromResource("shader/FragmentShader.fsh"));
        shaderProgram.link();
    }
}
