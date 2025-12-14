package me.hannsi.lfjg.core.utils.graphics;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum ResolutionType implements IEnumTypeBase {
    QQVGA("Quarter QVGA", 0, 160, 120),
    QCIF("Quarter CIF", 1, 176, 144),
    QVGA("Quarter VGA", 2, 320, 240),
    SIF("Source Input Format", 3, 352, 240),
    CGA("Color Graphics Adapter", 4, 640, 200),
    HVGA("Half VGA", 5, 480, 320),
    MAC_9_GRAY("Mac 9″ gray", 6, 512, 342),
    MAC_9_COLOR("Mac 9″ color", 7, 512, 384),
    EGA("Enhanced Graphics Adapter", 8, 640, 350),
    PC_98("PC-98", 9, 640, 400),
    DCGA("Double Scan CGA", 10, 640, 400),
    X68000("X68000 16bit", 11, 512, 512),
    VGA("Video Graphics Array", 12, 640, 480),
    SD("SD", 13, 640, 480),
    WVGA("Wide VGA", 14, 800, 480),
    FWVGA("Full Wide VGA", 15, 854, 480),
    FWVGAPP("Full Wide VGA++", 16, 960, 480),
    SVGA("Super-VGA", 17, 800, 600),
    UWVGA("Ultra Wide VGA", 18, 1024, 480),
    HXGA("Half XGA", 19, 1024, 480),
    QHD("Quarter HD", 20, 960, 540),
    MAC_16("Mac 16″", 21, 832, 624),
    MAC_15("Mac 15″", 22, 640, 870),
    WSVGA("Wide Super VGA", 23, 1024, 600),
    DOUBLE_VGA("Double VGA", 24, 960, 640),
    IPHONE4("iPhone 4″", 25, 1136, 640),
    UWSVGA("Ultra Wide SVGA", 26, 1280, 600),
    XGA("Extended Graphics Array", 27, 1024, 768),
    PC_98_HR("PC-98-High Resolution", 28, 1120, 750),
    HD("HD(720p)", 29, 1280, 720),
    WXGA("Wide XGA", 30, 1280, 768),
    XGAP("XGA+", 31, 1152, 864),
    IPHONE4_7("iPhone 4.7″", 32, 1334, 750),
    MAC_21("Mac 21″", 33, 1152, 870),
    WXGA2("Wide XGA2", 34, 1280, 800),
    FWXGA("Full-WXGA", 35, 1366, 768),
    HDP("HD+", 36, 1520, 720),
    HDP2("HD+2", 37, 1600, 720),
    ULTRA_WIDE_XGA("Ultra Wide XGA", 38, 1600, 768),
    QUAD_VGA("Quad VGA", 39, 1280, 960),
    WXGAP("Wide XGA+", 40, 1440, 900),
    SXGA("Super XGA", 41, 1280, 1024),
    HDP3("HD+3", 42, 1600, 900),
    WXGAPP("Wide XGA++", 43, 1600, 900),
    SXGAP("SXGA+", 44, 1400, 1050),
    HDP4("HD+4", 45, 1792, 828),
    WSXGA("WSXGA", 46, 1600, 1024),
    WSXGAP("WSXGA+", 47, 1680, 1050),
    SUPER_XGAP("Super XGA+", 48, 1400, 1050),
    WIDE_SUPER_XGA("Wide Super XGA", 49, 1600, 1024),
    UXGA("Ultra XGA", 50, 1600, 1200),
    FHD("Full-HD(1080p)", 51, 1920, 1080),
    DCI_2K("DCK 2K", 52, 2048, 1080),
    WUXGA("Wide Ultra-XGA", 53, 1920, 1200),
    QWXGA("QWXGA", 54, 2048, 1152),
    FHDP("FHD+", 55, 1920, 1280),
    WUXGAP("WUXGA+", 56, 1920, 1280),
    QUAD_HD("Quad HD", 57, 2160, 1440),
    FHDP2("FHD+2", 58, 2160, 1080),
    FHDP3("FHD+3", 59, 2280, 1080),
    FHDP4("FHD+4", 60, 2312, 1080),
    FHDP5("FHD+5", 61, 2340, 1080),
    FHDP6("FHD+6", 62, 2520, 1080),
    FHDP7("FHD+7", 63, 2436, 1125),
    ULTRA_WIDE_FHD("Ultra Wide FHD", 64, 2560, 1080),
    QXGA("Quad XGA", 65, 2048, 1536),
    MAC_BOOK_12("MacBook 12″", 66, 2304, 1440),
    FHDP8("FHD+8", 67, 2688, 1242),
    RESOLUTION_2K("2K", 68, 2256, 1504),
    WQHD("Wide Quad-HD(1440p)", 69, 2560, 1440),
    FHD_SQUARE("FHD Square", 70, 1920, 1920),
    WQXGA("WQXGA", 71, 2560, 1600),
    FULL_VISION_QHD("Full Vision QHD", 72, 2880, 1440),
    DUAL_FHD("Dual FHD", 73, 3840, 1080),
    RESOLUTION_2K_SQUARE("2K Square", 74, 2048, 2048),
    RESOLUTION_2_5K_QHD("2.5K QHD", 75, 2520, 1680),
    WQHDP("WQHD+", 76, 2960, 1440),
    PIXEL_A5("Pixel A5", 77, 2560, 1800),
    UWQHD("Ultra-Wide QHD", 78, 3440, 1440),
    SURFACE_12_3("Surface 12.3″", 79, 2736, 1824),
    QHDP("QHD+", 80, 3008, 1692),
    QWXGAP("Quad WXGA+", 81, 2880, 1800),
    QSXGA("Quad SXGA", 82, 2560, 2048),
    IPAD_PRO_12_9("iPad Pro 12.9″", 83, 2732, 2048),
    QUAD_HDP("Quad HD+", 84, 3200, 1800),
    WQXGAP("WQXGA+", 85, 3200, 1800),
    RESOLUTION_3K("3K", 86, 2880, 1620),
    SURAFACE_13_5("Surface 13.5″", 87, 3000, 2000),
    ULTRA_WIDE_QHDP("Ultra Wide QHD+", 88, 3840, 1600),
    RESOLUTION_4K_HDR("4K HDR", 89, 3840, 1644),
    DUAL_WQHD("Dual WQHD", 90, 5120, 1440),
    QUXGA("Quad UXGA", 91, 3200, 2400),
    RESOLUTION_4K("4K(2160p)", 92, 3840, 2160),
    QFHD("Quad Full-HD", 93, 3840, 2160),
    DCI_4K("DCI 4K", 94, 4092, 2160),
    WQUXGA("Wide QUXGA", 95, 3840, 2400),
    IMAC_RETINA_4K("iMac Retina 4K", 96, 4096, 2304),
    RESOLUTION_4KP("4K+", 97, 3840, 2560),
    DCI_4KP("DCI DK+", 98, 4096, 2560),
    IMAC_RETINA_4_5K("iMac Retina 4.5K", 99, 4480, 2520),
    DUAL_4K("Dual 4K", 100, 7680, 2160),
    RESOLUTION_5K("5K", 101, 5120, 2880),
    UHDP("UHD+", 102, 5120, 2880),
    XDR("Extreme Dynamic Range", 103, 6016, 3384),
    RESOLUTION_6K("6K", 104, 6144, 3456),
    RESOLUTION_8K_FUHD("8K FUHD(4320p)", 105, 7680, 4320),
    RESOLUTION_8K("8K", 106, 8192, 4320),
    RESOLUTION_10K("10K", 107, 10240, 4320),
    RESOLUTION_16K("16K", 108, 15360, 8640);

    final String name;
    final int id;
    final int width;
    final int height;

    ResolutionType(String name, int id, int width, int height) {
        this.name = name;
        this.id = id;
        this.width = width;
        this.height = height;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTotalNumberOfPixels() {
        return width * height;
    }

    public float getAspectRatio() {
        return (float) width / height;
    }
}
