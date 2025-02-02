package me.hannsi.lfjg.render.openGL.system.font;

public class UnicodeRange {
    public static final int ASCII_START = 0x20;  // U+0020
    public static final int ASCII_END = 0x7E;    // U+007E

    public static final int LATIN1_SUPPLEMENT_START = 0x80; // U+0080
    public static final int LATIN1_SUPPLEMENT_END = 0xFF;   // U+00FF

    public static final int GREEK_AND_COPTIC_START = 0x0370; // U+0370
    public static final int GREEK_AND_COPTIC_END = 0x03FF;   // U+03FF

    public static final int CYRILLIC_START = 0x0400; // U+0400
    public static final int CYRILLIC_END = 0x04FF;   // U+04FF

    public static final int CJK_IDEOGRAPHS_START = 0x4E00; // U+4E00
    public static final int CJK_IDEOGRAPHS_END = 0x9FFF;   // U+9FFF

    public static final int HIRAGANA_START = 0x3040; // U+3040
    public static final int HIRAGANA_END = 0x309F;   // U+309F

    public static final int KATAKANA_START = 0x30A0; // U+30A0
    public static final int KATAKANA_END = 0x30FF;   // U+30FF

    public static final int ARABIC_START = 0x0600; // U+0600
    public static final int ARABIC_END = 0x06FF;   // U+06FF

    public static final int DEVANAGARI_START = 0x0900; // U+0900
    public static final int DEVANAGARI_END = 0x097F;   // U+097F

    public static final int EMOJI_START = 0x1F600; // U+1F600
    public static final int EMOJI_END = 0x1F64F;   // U+1F64F

    public static final int CJK_IDEOGRAPHS_EXTENSION_A_START = 0x3400; // U+3400
    public static final int CJK_IDEOGRAPHS_EXTENSION_A_END = 0x4DBF;   // U+4DBF

    public static final int CJK_IDEOGRAPHS_EXTENSION_B_START = 0x20000; // U+20000
    public static final int CJK_IDEOGRAPHS_EXTENSION_B_END = 0x2A6DF;   // U+2A6DF

    public static final int BASIC_LATIN_START = 0x0000; // U+0000
    public static final int BASIC_LATIN_END = 0x007F;   // U+007F

    public static final int SUPPLEMENTARY_PRIVATE_USE_AREA_A_START = 0xF0000; // U+F0000
    public static final int SUPPLEMENTARY_PRIVATE_USE_AREA_A_END = 0xFFFFF;   // U+FFFFF

    public static final int SUPPLEMENTARY_PRIVATE_USE_AREA_B_START = 0x100000; // U+100000
    public static final int SUPPLEMENTARY_PRIVATE_USE_AREA_B_END = 0x10FFFF;   // U+10FFFF

    public static final int LATIN_EXTENDED_A_START = 0x0100; // U+0100
    public static final int LATIN_EXTENDED_A_END = 0x017F;   // U+017F

    public static final int LATIN_EXTENDED_B_START = 0x0180; // U+0180
    public static final int LATIN_EXTENDED_B_END = 0x024F;   // U+024F

    public static final int IPA_EXTENSIONS_START = 0x0250; // U+0250
    public static final int IPA_EXTENSIONS_END = 0x02AF;   // U+02AF

    public static final int PHONETIC_EXTENSIONS_START = 0x1D00; // U+1D00
    public static final int PHONETIC_EXTENSIONS_END = 0x1D7F;   // U+1D7F

    public static final int MATHEMATICAL_ALPHABETIC_SYMBOLS_START = 0x1D400; // U+1D400
    public static final int MATHEMATICAL_ALPHABETIC_SYMBOLS_END = 0x1D7FF;   // U+1D7FF

    public static final int HANGUL_SYLLABLES_START = 0xAC00; // U+AC00
    public static final int HANGUL_SYLLABLES_END = 0xD7AF;   // U+D7AF

    public static final int CJK_IDEOGRAPHS_EXTENSION_C_START = 0x2A700; // U+2A700
    public static final int CJK_IDEOGRAPHS_EXTENSION_C_END = 0x2B73F;   // U+2B73F

    public static final int CJK_IDEOGRAPHS_EXTENSION_D_START = 0x2B740; // U+2B740
    public static final int CJK_IDEOGRAPHS_EXTENSION_D_END = 0x2B81F;   // U+2B81F

    public static final int CJK_IDEOGRAPHS_EXTENSION_E_START = 0x2B820; // U+2B820
    public static final int CJK_IDEOGRAPHS_EXTENSION_E_END = 0x2CEAF;   // U+2CEAF

    public static final int CJK_IDEOGRAPHS_EXTENSION_F_START = 0x2CEB0; // U+2CEB0
    public static final int CJK_IDEOGRAPHS_EXTENSION_F_END = 0x2EBEF;   // U+2EBEF

    public static final int MATHEMATICAL_OPERATORS_START = 0x2200; // U+2200
    public static final int MATHEMATICAL_OPERATORS_END = 0x22FF;   // U+22FF

    public static final int ARROWS_START = 0x2190; // U+2190
    public static final int ARROWS_END = 0x21FF;   // U+21FF

    public static final int MISCELLANEOUS_SYMBOLS_START = 0x2600; // U+2600
    public static final int MISCELLANEOUS_SYMBOLS_END = 0x26FF;   // U+26FF

    public static final int CURRENCY_SYMBOLS_START = 0x20A0; // U+20A0
    public static final int CURRENCY_SYMBOLS_END = 0x20CF;   // U+20CF

    public static final int ENCLOSED_ALPHANUMERIC_SUPPLEMENT_START = 0x1F100; // U+1F100
    public static final int ENCLOSED_ALPHANUMERIC_SUPPLEMENT_END = 0x1F1FF;   // U+1F1FF

    public static final int SUPPLEMENTAL_MATHEMATICAL_OPERATORS_START = 0x2A00; // U+2A00
    public static final int SUPPLEMENTAL_MATHEMATICAL_OPERATORS_END = 0x2AFF;   // U+2AFF

    public static final int GEOMETRIC_SHAPES_START = 0x25A0; // U+25A0
    public static final int GEOMETRIC_SHAPES_END = 0x25FF;   // U+25FF

    public static final int SUPPLEMENTAL_ARROWS_A_START = 0x2B00; // U+2B00
    public static final int SUPPLEMENTAL_ARROWS_A_END = 0x2B1F;   // U+2B1F

    public static final int SUPPLEMENTAL_ARROWS_B_START = 0x2900; // U+2900
    public static final int SUPPLEMENTAL_ARROWS_B_END = 0x297F;   // U+297F

    public static final int MATHEMATICAL_BOLD_SYMBOLS_START = 0x1D400; // U+1D400
    public static final int MATHEMATICAL_BOLD_SYMBOLS_END = 0x1D7FF;   // U+1D7FF

    public static final int LATIN_EXTENDED_C_START = 0x02C0; // U+02C0
    public static final int LATIN_EXTENDED_C_END = 0x02FF;   // U+02FF

    public static final int CJK_IDEOGRAPHS_EXTENSION_G_START = 0x30000; // U+30000
    public static final int CJK_IDEOGRAPHS_EXTENSION_G_END = 0x3134F;   // U+3134F

    public static final int ANCIENT_SCRIPTS_START = 0x10100; // U+10100
    public static final int ANCIENT_SCRIPTS_END = 0x1013F;   // U+1013F

    public static final int TIBETAN_START = 0x0F00; // U+0F00
    public static final int TIBETAN_END = 0x0FFF;   // U+0FFF

    public static final int ETHIOPIC_START = 0x1200; // U+1200
    public static final int ETHIOPIC_END = 0x137F;   // U+137F

    public static final int GEORGIAN_START = 0x10A0; // U+10A0
    public static final int GEORGIAN_END = 0x10FF;   // U+10FF

    public static final int KHMER_START = 0x1780; // U+1780
    public static final int KHMER_END = 0x17FF;   // U+17FF

    public static final int LAO_START = 0x0E80; // U+0E80
    public static final int LAO_END = 0x0EFF;   // U+0EFF

    public static final int MYANMAR_START = 0x1000; // U+1000
    public static final int MYANMAR_END = 0x109F;   // U+109F

    public static final int HANGUL_JAMO_START = 0x1100; // U+1100
    public static final int HANGUL_JAMO_END = 0x11FF;   // U+11FF

    public static final int SYRIAC_START = 0x0700; // U+0700
    public static final int SYRIAC_END = 0x074F;   // U+074F
}
