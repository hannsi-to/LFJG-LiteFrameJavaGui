package me.hannsi.lfjg.render.openGL.system.shader;

public class UniformDatum<T> {
    private String name;
    private T value;

    public UniformDatum(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
