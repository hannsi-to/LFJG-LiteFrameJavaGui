package me.hannsi.test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.security.CodeSource;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GLWrapperGenerator {

    private static final List<Class<?>> ALL_GL_CLASSES = new ArrayList<>();
    private static final Map<String, CommandData> COMMAND_MAP = new HashMap<>();
    private static final Map<String, Set<String>> FEATURE_MAP = new HashMap<>();
    private static final Map<String, Set<String>> EXTENSION_MAP = new HashMap<>();

    public static void main(String[] args) {
        autoLoadLWJGLClasses();
        parseXml("gl.xml");
        generate();
    }

    private static void parseXml(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return;
            }
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

            // コマンド解析
            NodeList cmds = doc.getElementsByTagName("command");
            for (int i = 0; i < cmds.getLength(); i++) {
                Node node = cmds.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element el = (Element) node;

                NodeList protoList = el.getElementsByTagName("proto");
                if (protoList.getLength() == 0) {
                    continue;
                }
                Element proto = (Element) protoList.item(0);
                String name = proto.getElementsByTagName("name").item(0).getTextContent();

                CommandData data = new CommandData(name);
                NodeList params = el.getElementsByTagName("param");
                for (int j = 0; j < params.getLength(); j++) {
                    Node pNode = params.item(j);
                    if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element pEl = (Element) pNode;
                        data.paramNames.add(pEl.getElementsByTagName("name").item(0).getTextContent());
                    }
                }

                NodeList alias = el.getElementsByTagName("alias");
                if (alias.getLength() > 0) {
                    data.aliasOf = ((Element) alias.item(0)).getAttribute("name");
                }
                COMMAND_MAP.put(name, data);
            }

            // フィーチャー(コアバージョン)解析
            NodeList features = doc.getElementsByTagName("feature");
            for (int i = 0; i < features.getLength(); i++) {
                Element f = (Element) features.item(i);
                if (!f.getAttribute("api").contains("gl")) {
                    continue;
                }
                String ver = f.getAttribute("name");
                Set<String> c = new HashSet<>();
                NodeList reqs = f.getElementsByTagName("command");
                for (int j = 0; j < reqs.getLength(); j++) {
                    c.add(((Element) reqs.item(j)).getAttribute("name"));
                }
                FEATURE_MAP.put(ver, c);
            }

            // 拡張機能解析
            NodeList exts = doc.getElementsByTagName("extension");
            for (int i = 0; i < exts.getLength(); i++) {
                Element e = (Element) exts.item(i);
                String name = e.getAttribute("name");
                Set<String> c = new HashSet<>();
                NodeList reqs = e.getElementsByTagName("command");
                for (int j = 0; j < reqs.getLength(); j++) {
                    c.add(((Element) reqs.item(j)).getAttribute("name"));
                }
                EXTENSION_MAP.put(name, c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generate() {
        ALL_GL_CLASSES.stream()
                .filter(c -> c.getSimpleName().matches("GL\\d+"))
                .sorted(Comparator.comparing(Class::getSimpleName))
                .forEach(coreClass -> {
                    System.out.println("// --- " + coreClass.getSimpleName() + " ---");
                    for (Method m : coreClass.getDeclaredMethods()) {
                        if (!Modifier.isStatic(m.getModifiers()) || !Modifier.isPublic(m.getModifiers())) {
                            continue;
                        }
                        if (m.getName().startsWith("ngl") || m.getName().contains("check")) {
                            continue;
                        }
                        processMethod(coreClass, m);
                    }
                });
    }

    private static void processMethod(Class<?> coreClass, Method method) {
        String coreName = method.getName();
        String returnType = method.getReturnType().getSimpleName();
        CommandData data = COMMAND_MAP.get(coreName);

        // 引数リスト
        List<String> pNames = (data != null) ? data.paramNames : new ArrayList<>();
        StringBuilder formal = new StringBuilder();
        StringBuilder actual = new StringBuilder();
        Class<?>[] pTypes = method.getParameterTypes();
        for (int i = 0; i < pTypes.length; i++) {
            String pName = (i < pNames.size()) ? pNames.get(i) : "arg" + i;
            if (Arrays.asList("class", "enum", "default", "package").contains(pName)) {
                pName += "_";
            }
            formal.append(pTypes[i].getSimpleName()).append(" ").append(pName);
            actual.append(pName);
            if (i < pTypes.length - 1) {
                formal.append(", ");
                actual.append(", ");
            }
        }

        // コアCapabilityの特定
        String coreCap = null;
        for (var entry : FEATURE_MAP.entrySet()) {
            if (entry.getValue().contains(coreName)) {
                coreCap = entry.getKey().replace("GL_VERSION_", "OpenGL").replace("_", "");
                if (coreCap.equals("OpenGL10")) {
                    coreCap = "OpenGL11";
                }
                break;
            }
        }

        // 拡張機能の検索
        Map<String, Class<?>> fallbacks = new LinkedHashMap<>();
        for (var entry : EXTENSION_MAP.entrySet()) {
            String extName = entry.getKey();
            for (String cmdInExt : entry.getValue()) {
                CommandData extCmdData = COMMAND_MAP.get(cmdInExt);
                if (cmdInExt.equals(coreName) || (extCmdData != null && coreName.equals(extCmdData.aliasOf))) {
                    Class<?> extClazz = findLwjglClassForMethod(cmdInExt, pTypes);
                    if (extClazz != null) {
                        fallbacks.put(extName, extClazz);
                    }
                }
            }
        }

        System.out.println("    public static " + returnType + " " + coreName + "(" + formal + ") {");
        boolean hasIf = false;
        if (coreCap != null) {
            System.out.println("        if (GL_CAPABILITIES." + coreCap + ") {");
            System.out.println("            " + (returnType.equals("void") ? "" : "return ") + coreClass.getSimpleName() + "." + coreName + "(" + actual + ");");
            hasIf = true;
        }

        for (var entry : fallbacks.entrySet()) {
            String extCap = entry.getKey();
            Class<?> extClazz = entry.getValue();
            String extMethodName = findMethodNameInClass(extClazz, coreName, pTypes);

            System.out.println("        " + (hasIf ? "} else if" : "if") + " (GL_CAPABILITIES." + extCap + ") {");
            System.out.println("            " + (returnType.equals("void") ? "" : "return ") + extClazz.getSimpleName() + "." + extMethodName + "(" + actual + ");");
            hasIf = true;
        }

        if (hasIf) {
            System.out.println("        } else {");
            System.out.println("            throw new UnsupportedOperationException(\"" + coreName + "\");");
            System.out.println("        }");
        } else {
            System.out.println("        throw new UnsupportedOperationException(\"" + coreName + " has no caps\");");
        }
        System.out.println("    }\n");
    }

    private static Class<?> findLwjglClassForMethod(String methodName, Class<?>[] params) {
        for (Class<?> c : ALL_GL_CLASSES) {
            if (c.getSimpleName().startsWith("GL")) {
                continue;
            }
            try {
                c.getMethod(methodName, params);
                return c;
            } catch (NoSuchMethodException ignored) {
            }
        }
        return null;
    }

    private static String findMethodNameInClass(Class<?> clazz, String coreName, Class<?>[] params) {
        String[] targets = {coreName, coreName + "ARB", coreName + "EXT", coreName + "NV", coreName + "KHR"};
        for (String t : targets) {
            try {
                clazz.getMethod(t, params);
                return t;
            } catch (NoSuchMethodException ignored) {
            }
        }
        return coreName;
    }

    private static void autoLoadLWJGLClasses() {
        try {
            CodeSource src = org.lwjgl.opengl.GL.class.getProtectionDomain().getCodeSource();
            URL jar = src.getLocation();
            try (ZipInputStream zip = new ZipInputStream(jar.openStream())) {
                ZipEntry entry;
                while ((entry = zip.getNextEntry()) != null) {
                    if (entry.getName().startsWith("org/lwjgl/opengl/") && entry.getName().endsWith(".class") && !entry.getName().contains("$")) {
                        String name = entry.getName().replace("/", ".").replace(".class", "");
                        try {
                            ALL_GL_CLASSES.add(Class.forName(name));
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class CommandData {
        String name;
        String aliasOf;
        List<String> paramNames = new ArrayList<>();

        CommandData(String n) {
            this.name = n;
        }
    }
}