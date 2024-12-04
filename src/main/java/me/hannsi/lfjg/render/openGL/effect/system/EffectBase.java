package me.hannsi.lfjg.render.openGL.effect.system;

import me.hannsi.lfjg.render.openGL.renderers.GLObject;

public class EffectBase {
    private int id;
    private String name;
    private Class<GLObject>[] ignoreGLObject;

    @SafeVarargs
    public EffectBase(int id, String name, Class<GLObject>... ignoreGLObject) {
        this.id = id;
        this.name = name;
        this.ignoreGLObject = ignoreGLObject;
    }

    public void draw(GLObject baseGLObject) {

    }

    public void pop(GLObject baseGLObject) {

    }

    public void push(GLObject baseGLObject) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<GLObject>[] getIgnoreGLObject() {
        return ignoreGLObject;
    }

    public void setIgnoreGLObject(Class<GLObject>[] ignoreGLObject) {
        this.ignoreGLObject = ignoreGLObject;
    }
}
