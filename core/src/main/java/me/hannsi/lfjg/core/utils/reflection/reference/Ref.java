package me.hannsi.lfjg.core.utils.reflection.reference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ref<T> {
    private T value;

    public Ref(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}