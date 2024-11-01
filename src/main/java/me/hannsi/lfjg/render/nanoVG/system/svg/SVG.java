package me.hannsi.lfjg.render.nanoVG.system.svg;

import me.hannsi.lfjg.utils.reflection.ResourcesLocation;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.lwjgl.nanovg.NanoVG;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.*;

import java.io.FileInputStream;
import java.io.InputStream;

public record SVG(long nvg, String name, ResourcesLocation resourcesLocation) {

    private SVGSVGElement loadSVG() {
        try {
            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
            InputStream inputStream = new FileInputStream(resourcesLocation.getPath());
            Document doc = factory.createDocument(resourcesLocation.getPath(), inputStream);
            return (SVGSVGElement) doc.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void drawSVG(long vg) {
        SVGSVGElement svg = loadSVG();
        if (svg == null) {
            System.err.println("Failed to load SVG");
            return;
        }
        NodeList paths = svg.getElementsByTagName("path");

        for (int i = 0; i < paths.getLength(); i++) {
            SVGPathElement path = (SVGPathElement) paths.item(i);

            NanoVG.nvgBeginPath(vg);
            drawSVGPath(vg, path);
            NanoVG.nvgFill(vg);
        }
    }

    public void drawSVGPath(long vg, SVGPathElement pathElement) {
        SVGPathSegList segList = pathElement.getPathSegList();

        float currentX = 0, currentY = 0;

        for (int i = 0; i < segList.getNumberOfItems(); i++) {
            SVGPathSeg seg = segList.getItem(i);

            switch (seg.getPathSegType()) {
                case SVGPathSeg.PATHSEG_MOVETO_ABS: {
                    SVGPathSegMovetoAbs moveTo = (SVGPathSegMovetoAbs) seg;
                    NanoVG.nvgMoveTo(vg, moveTo.getX(), moveTo.getY());
                    currentX = moveTo.getX();
                    currentY = moveTo.getY();
                    break;
                }
                case SVGPathSeg.PATHSEG_LINETO_ABS: {
                    SVGPathSegLinetoAbs lineTo = (SVGPathSegLinetoAbs) seg;
                    NanoVG.nvgLineTo(vg, lineTo.getX(), lineTo.getY());
                    currentX = lineTo.getX();
                    currentY = lineTo.getY();
                    break;
                }
                case SVGPathSeg.PATHSEG_CURVETO_CUBIC_ABS: {
                    SVGPathSegCurvetoCubicAbs curveTo = (SVGPathSegCurvetoCubicAbs) seg;
                    NanoVG.nvgBezierTo(vg, curveTo.getX1(), curveTo.getY1(), curveTo.getX2(), curveTo.getY2(), curveTo.getX(), curveTo.getY());
                    currentX = curveTo.getX();
                    currentY = curveTo.getY();
                    break;
                }
                case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_ABS: {
                    SVGPathSegCurvetoQuadraticAbs curveTo = (SVGPathSegCurvetoQuadraticAbs) seg;
                    NanoVG.nvgQuadTo(vg, curveTo.getX1(), curveTo.getY1(), curveTo.getX(), curveTo.getY());
                    currentX = curveTo.getX();
                    currentY = curveTo.getY();
                    break;
                }
                case SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_ABS: {
                    SVGPathSegCurvetoCubicSmoothAbs smoothCurveTo = (SVGPathSegCurvetoCubicSmoothAbs) seg;
                    NanoVG.nvgBezierTo(vg, currentX, currentY, smoothCurveTo.getX2(), smoothCurveTo.getY2(), smoothCurveTo.getX(), smoothCurveTo.getY());
                    currentX = smoothCurveTo.getX();
                    currentY = smoothCurveTo.getY();
                    break;
                }
                case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS: {
                    SVGPathSegCurvetoQuadraticSmoothAbs smoothCurveTo = (SVGPathSegCurvetoQuadraticSmoothAbs) seg;
                    NanoVG.nvgQuadTo(vg, currentX, currentY, smoothCurveTo.getX(), smoothCurveTo.getY());
                    currentX = smoothCurveTo.getX();
                    currentY = smoothCurveTo.getY();
                    break;
                }
                case SVGPathSeg.PATHSEG_LINETO_HORIZONTAL_ABS: {
                    SVGPathSegLinetoHorizontalAbs lineTo = (SVGPathSegLinetoHorizontalAbs) seg;
                    NanoVG.nvgLineTo(vg, lineTo.getX(), currentY);
                    currentX = lineTo.getX();
                    break;
                }
                case SVGPathSeg.PATHSEG_LINETO_VERTICAL_ABS: {
                    SVGPathSegLinetoVerticalAbs lineTo = (SVGPathSegLinetoVerticalAbs) seg;
                    NanoVG.nvgLineTo(vg, currentX, lineTo.getY());
                    currentY = lineTo.getY();
                    break;
                }
                case SVGPathSeg.PATHSEG_LINETO_REL: {
                    SVGPathSegLinetoRel lineTo = (SVGPathSegLinetoRel) seg;
                    NanoVG.nvgLineTo(vg, currentX + lineTo.getX(), currentY + lineTo.getY());
                    currentX += lineTo.getX();
                    currentY += lineTo.getY();
                    break;
                }
                case SVGPathSeg.PATHSEG_CURVETO_CUBIC_REL: {
                    SVGPathSegCurvetoCubicRel curveTo = (SVGPathSegCurvetoCubicRel) seg;
                    NanoVG.nvgBezierTo(vg, currentX + curveTo.getX1(), currentY + curveTo.getY1(), currentX + curveTo.getX2(), currentY + curveTo.getY2(), currentX + curveTo.getX(), currentY + curveTo.getY());
                    currentX += curveTo.getX();
                    currentY += curveTo.getY();
                    break;
                }
                case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_REL: {
                    SVGPathSegCurvetoQuadraticRel curveTo = (SVGPathSegCurvetoQuadraticRel) seg;
                    NanoVG.nvgQuadTo(vg, currentX + curveTo.getX1(), currentY + curveTo.getY1(), currentX + curveTo.getX(), currentY + curveTo.getY());
                    currentX += curveTo.getX();
                    currentY += curveTo.getY();
                    break;
                }
                case SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_REL: {
                    SVGPathSegCurvetoCubicSmoothRel smoothCurveTo = (SVGPathSegCurvetoCubicSmoothRel) seg;
                    NanoVG.nvgBezierTo(vg, currentX, currentY, currentX + smoothCurveTo.getX2(), currentY + smoothCurveTo.getY2(), currentX + smoothCurveTo.getX(), currentY + smoothCurveTo.getY());
                    currentX += smoothCurveTo.getX();
                    currentY += smoothCurveTo.getY();
                    break;
                }
                case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL: {
                    SVGPathSegCurvetoQuadraticSmoothRel smoothCurveTo = (SVGPathSegCurvetoQuadraticSmoothRel) seg;
                    NanoVG.nvgQuadTo(vg, currentX, currentY, currentX + smoothCurveTo.getX(), currentY + smoothCurveTo.getY());
                    currentX += smoothCurveTo.getX();
                    currentY += smoothCurveTo.getY();
                    break;
                }
                case SVGPathSeg.PATHSEG_CLOSEPATH:
                    NanoVG.nvgClosePath(vg);
                    break;
                default:
                    System.err.println("Unsupported SVG path segment: " + seg.getPathSegTypeAsLetter());
                    break;
            }
        }
    }
}
