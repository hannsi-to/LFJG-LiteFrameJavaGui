package me.hannsi.lfjg.utils.graphics;

import org.lwjgl.opengl.GL11;

import java.util.BitSet;

public class GL11Util {
    private final BitSet targets;

    public GL11Util() {
        this.targets = new BitSet();
    }

    public void addGL11Target(int target) {
        if (target >= targets.size()) {
            targets.set(target);
        } else {
            targets.set(target);
        }
    }

    public void enableTargets() {
        GL11.glPushMatrix();

        for (int i = targets.nextSetBit(0); i >= 0; i = targets.nextSetBit(i + 1)) {
            gl11Enable(i);
        }
    }

    public void disableTargets() {
        for (int i = targets.nextSetBit(0); i >= 0; i = targets.nextSetBit(i + 1)) {
            gl11Disable(i);
        }
        GL11.glPopMatrix();
    }

    private void gl11Enable(int target) {
        GL11.glEnable(target);
    }

    private void gl11Disable(int target) {
        GL11.glDisable(target);
    }

    public void finish() {
        targets.clear();
    }
}
