package me.hannsi.lfjg.render.nanoVG.system.font;

import org.lwjgl.nanovg.NanoVG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlignExtractor {
    /**
     * A map that associates alignment names with their corresponding NanoVG alignment constants.
     */
    public static final Map<String, Integer> alignMap = new HashMap<>();

    static {
        alignMap.put("NVG_ALIGN_LEFT", NanoVG.NVG_ALIGN_LEFT);
        alignMap.put("NVG_ALIGN_CENTER", NanoVG.NVG_ALIGN_CENTER);
        alignMap.put("NVG_ALIGN_RIGHT", NanoVG.NVG_ALIGN_RIGHT);
        alignMap.put("NVG_ALIGN_TOP", NanoVG.NVG_ALIGN_TOP);
        alignMap.put("NVG_ALIGN_MIDDLE", NanoVG.NVG_ALIGN_MIDDLE);
        alignMap.put("NVG_ALIGN_BOTTOM", NanoVG.NVG_ALIGN_BOTTOM);
        alignMap.put("NVG_ALIGN_BASELINE", NanoVG.NVG_ALIGN_BASELINE);
    }

    /**
     * Converts an alignment integer to a list of corresponding NanoVG alignment constants.
     *
     * @param align the alignment integer
     * @return a list of NanoVG alignment constants as integers
     */
    public static List<Integer> getAlignmentsAsInteger(int align) {
        List<String> alignmentsString = getAlignmentsAsList(align);

        List<Integer> alignmentsInteger = new ArrayList<>();
        for (String alignment : alignmentsString) {
            alignmentsInteger.add(getAlignFromString(alignment));
        }

        return alignmentsInteger;
    }

    /**
     * Converts an alignment integer to a list of corresponding alignment names.
     *
     * @param align the alignment integer
     * @return a list of alignment names
     */
    public static List<String> getAlignmentsAsList(int align) {
        List<String> alignments = new ArrayList<>();

        if ((align & NanoVG.NVG_ALIGN_LEFT) != 0) {
            alignments.add("NVG_ALIGN_LEFT");
        }
        if ((align & NanoVG.NVG_ALIGN_CENTER) != 0) {
            alignments.add("NVG_ALIGN_CENTER");
        }
        if ((align & NanoVG.NVG_ALIGN_RIGHT) != 0) {
            alignments.add("NVG_ALIGN_RIGHT");
        }
        if ((align & NanoVG.NVG_ALIGN_TOP) != 0) {
            alignments.add("NVG_ALIGN_TOP");
        }
        if ((align & NanoVG.NVG_ALIGN_MIDDLE) != 0) {
            alignments.add("NVG_ALIGN_MIDDLE");
        }
        if ((align & NanoVG.NVG_ALIGN_BOTTOM) != 0) {
            alignments.add("NVG_ALIGN_BOTTOM");
        }
        if ((align & NanoVG.NVG_ALIGN_BASELINE) != 0) {
            alignments.add("NVG_ALIGN_BASELINE");
        }

        return alignments;
    }

    /**
     * Retrieves the NanoVG alignment constant corresponding to the given alignment name.
     *
     * @param align the alignment name
     * @return the NanoVG alignment constant
     */
    public static int getAlignFromString(String align) {
        return alignMap.get(align);
    }
}