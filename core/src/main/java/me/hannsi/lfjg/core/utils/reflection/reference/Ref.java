package me.hannsi.lfjg.core.utils.reflection.reference;

public class Ref<T> {
    private boolean isFinal = false;
    private T value;

    public Ref(T value) {
        this.value = value;
    }

    public Ref() {
        this.value = null;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public boolean isNullptr() {
        return value == null;
    }

    public void setNullptr() {
        value = null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (isFinal) {
            throw new RuntimeException("An attempt was made to change the value pointed to by the reference, but the value was set as a constant and cannot be changed.");
        }
        this.value = value;
    }

    public void setFinal() {
        isFinal = true;
    }

    public boolean isFinal() {
        return isFinal;
    }
}