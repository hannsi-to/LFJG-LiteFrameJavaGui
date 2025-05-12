package me.hannsi.test.jcef;

import me.hannsi.lfjg.jcef.JCef;
import me.hannsi.lfjg.utils.graphics.color.Color;

public class TestJCef {
    public static void main(String... args){
        JCef jCef = JCef.initJCef(args, 1920,1080,false,false,false, Color.of(255,255,255,255));
    }
}
