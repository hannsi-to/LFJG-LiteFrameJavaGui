package me.hannsi.lfjg.core.utils.reflection.reference;

public class Ref<T> {
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

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}