package me.hannsi.lfjg.render.openGL.effect.system;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLPolygon;

public class EffectBase {
    private int id;
    private String name;
    private Class<GLPolygon>[] ingnorePolygon;

    @SafeVarargs
    public EffectBase(int id, String name, Class<GLPolygon>... ingnorePolygon) {
        this.id = id;
        this.name = name;
        this.ingnorePolygon = ingnorePolygon;
    }

    public void rendering(Frame frame, GLPolygon basePolygon) {

    }

    public void pop(Frame frame, GLPolygon basePolygon) {

    }

    public void push(Frame frame, GLPolygon basePolygon) {

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

    public Class<GLPolygon>[] getIngnorePolygon() {
        return ingnorePolygon;
    }

    public void setIngnorePolygon(Class<GLPolygon>[] ingnorePolygon) {
        this.ingnorePolygon = ingnorePolygon;
    }
}
