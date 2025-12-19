package me.hannsi.lfjg.frame.system;

public class Attachment {
    public final int attachment;
    public final LoadOp loadOp;
    public final StoreOp storeOp;
    public final float[] clearValue;

    public Attachment(int attachment, LoadOp loadOp, StoreOp storeOp, float[] clearValue) {
        this.attachment = attachment;
        this.loadOp = loadOp;
        this.storeOp = storeOp;
        this.clearValue = clearValue;
    }

    public enum LoadOp {
        LOAD,
        CLEAR,
        DONT_CARE
    }

    public enum StoreOp {
        STORE,
        DONT_CARE
    }
}
