package me.hannsi.lfjg.core.event.events;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.event.Event;
import me.hannsi.lfjg.core.utils.toolkit.ANSIFormat;

import java.util.ArrayList;
import java.util.List;

import static me.hannsi.lfjg.core.utils.math.MathHelper.max;

public class CleanupEvent extends Event {
    private static List<String> toLines(Object value) {
        return value.toString().lines().toList();
    }

    public boolean debug(Class<?> cleanupClass, CleanupData... cleanupDatum) {
        List<String> texts = new ArrayList<>();

        boolean allState = true;
        for (CleanupData cleanupData : cleanupDatum) {
            texts.add(cleanupData.targetCleanupObjectName);
            texts.add("\t" + String.format("%-" + cleanupData.width + "s | Cleanup State", "Key"));
            texts.add("\t" + "-".repeat(cleanupData.width) + "-+-" + "-".repeat("Cleanup State".length()));

            int index = 0;
            for (String name : cleanupData.names) {
                boolean state = cleanupData.states.get(index);
                if (!state) {
                    allState = false;
                }

                String color = state ? ANSIFormat.GREEN : ANSIFormat.RED;
                List<String> lines = toLines(cleanupData.values.get(index));

                texts.add("\t" + String.format("%-" + cleanupData.width + "s | %s%s%s", name, color, lines.getFirst(), ANSIFormat.RESET));

                for (int i = 1; i < lines.size(); i++) {
                    texts.add("\t" + " ".repeat(cleanupData.width) + " | " + color + lines.get(i) + ANSIFormat.RESET);
                }

                index++;
            }
        }

        new LogGenerator(LogGenerateType.CLEANUP, cleanupClass, "", "", texts.toArray(new String[0])).logging(cleanupClass, DebugLevel.DEBUG);

        return allState;
    }

    public static class CleanupData {
        public String targetCleanupObjectName;
        public int width;
        public List<String> names;
        public List<Boolean> states;
        public List<Object> values;

        public CleanupData(String targetCleanupObjectName) {
            this.targetCleanupObjectName = targetCleanupObjectName;
            this.width = "key".length();
            this.names = new ArrayList<>();
            this.states = new ArrayList<>();
            this.values = new ArrayList<>();
        }

        public CleanupData addData(String name, boolean state) {
            return addData(name, state, state);
        }

        public CleanupData addData(String name, boolean state, Object value) {
            names.add(name);
            states.add(state);
            values.add(value);

            width = max(width, name.length());

            return this;
        }
    }
}
