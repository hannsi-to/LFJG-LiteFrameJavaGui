package me.hannsi.lfjg.render.openGL.system.svg;

import me.hannsi.lfjg.debug.debug.logger.LogGenerator;
import me.hannsi.lfjg.debug.debug.system.DebugLevel;

import java.util.HashMap;

public class SVGCache {
    private HashMap<String, SVG> svgCache;

    private SVGCache() {
        svgCache = new HashMap<>();
    }

    public static SVGCache initSVGCache() {
        return new SVGCache();
    }

    public SVGCache createCache(SVG svg) {
        svgCache.put(svg.getName(), svg);

        new LogGenerator("SVGCache Debug Message", "Source: FontCache", "Type: Cache Creation", "ID: " + svg.getName(), "Severity: Info", "Message: Create svg cache: " + svg.getName()).logging(DebugLevel.DEBUG);

        return this;
    }

    public SVGCache loadSVG() {
        svgCache.forEach((key, value) -> value.loadSVG());

        return this;
    }

    public SVG getSVG(String svgName) {
        return svgCache.get(svgName);
    }

    public void cleanup(String... svgNames) {
        for (String name : svgNames) {
            SVG svg = svgCache.remove(name);
            if (svg != null) {
                svg.cleanup();

                new LogGenerator("SVGCache Debug Message", "Source: SVGCache", "Type: Cache Cleanup", "ID: " + name, "Severity: Info", "Message: Cleanup svg: " + name).logging(DebugLevel.DEBUG);
            } else {
                new LogGenerator("SVGCache Debug Message", "Source: SVGCache", "Type: Cache Cleanup", "ID: " + name, "Severity: Warning", "Message: SVG not found in cache for cleanup: " + name).logging(DebugLevel.WARNING);
            }
        }
    }

    public void cleanup() {
        svgCache.forEach((key, value) -> value.cleanup());
        svgCache.clear();

        new LogGenerator("SVGCache Debug Message", "Source: SVGCache", "Type: Cache Cleanup", "ID: All", "Severity: Info", "Message: Cleanup svg cache").logging(DebugLevel.DEBUG);
    }

    public HashMap<String, SVG> getSvgCache() {
        return svgCache;
    }

    public void setSvgCache(HashMap<String, SVG> svgCache) {
        this.svgCache = svgCache;
    }
}
