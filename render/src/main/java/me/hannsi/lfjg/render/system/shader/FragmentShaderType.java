package me.hannsi.lfjg.render.system.shader;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum FragmentShaderType implements IEnumTypeBase {
    OBJECT(0,"Object"),
    MODEL(1,"Model"),
    FRAME_BUFFER(2,"FrameBuffer"),
    MSDF(3,"MSDF"),
    BLOOM(4,"Bloom"),
    BOX_BLUR(5,"BoxBlur"),
    CHROMA_KEY(6,"ChromaKey"),
    CHROMATIC_ABERRATION(7,"ChromaticAberration"),
    CLIPPING_RECT(8,"ClippingRect"),
    COLOR_CHANGER(9,"ColorChanger"),
    COLOR_CORRECTION(10,"ColorCorrection"),
    DIAGONAL_CLIPPING(11,"DiagonalClipping"),
    DIRECTION_BLUR(12,"DirectionBlur"),
    EDGE_EXTRACTION(13,"EdgeExtraction"),
    FXAA(14,"FXAA"),
    FLASH(15,"Flash"),
    FRAME_BUFFER_CONTENTS(16,"FrameBufferContents"),
    GAUSSIAN_BLUR_HORIZONTAL(17,"GaussianBlurHorizontal"),
    GAUSSIAN_BLUR_VERTICAL(17,"GaussianBlurVertical"),
    GLOW(18,"Glow"),
    GRADATION(19,"Gradation"),
    INVERSION(20,"Inversion"),
    LENS_BLUR(21,"LensBlur"),
    LUMINANCE_KEY(22,"LuminanceKey"),
    MONOCHROME(23,"Monochrome"),
    OBJECT_CLIPPING(24,"ObjectClipping"),
    PIXELATE(25,"Pixelate"),
    RADIAL_BLUR(26,"RadialBlur"),
    SPLIT_OBJECT(27,"SplitObject");

    final int id;
    final String name;

    FragmentShaderType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
