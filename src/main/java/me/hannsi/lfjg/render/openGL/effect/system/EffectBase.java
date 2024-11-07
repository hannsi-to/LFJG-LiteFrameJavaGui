package me.hannsi.lfjg.render.openGL.effect.system;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.render.openGL.renderers.polygon.GLPolygon;

import java.awt.*;

public class EffectBase {
    private int id;
    private String name;
    private Polygon[] ingnorePolygon;

    public EffectBase(int id, String name, Polygon[] ingnorePolygon) {
        this.id = id;
        this.name = name;
        this.ingnorePolygon = ingnorePolygon;
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

    public Polygon[] getIngnorePolygon() {
        return ingnorePolygon;
    }

    public void setIngnorePolygon(Polygon[] ingnorePolygon) {
        this.ingnorePolygon = ingnorePolygon;
    }
}
