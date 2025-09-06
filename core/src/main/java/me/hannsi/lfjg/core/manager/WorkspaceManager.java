package me.hannsi.lfjg.core.manager;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.utils.toolkit.ANSIFormat;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class WorkspaceManager {
    public static String DEFAULT_WORKSPACE_NAME = "lfjg/workspace";
    public static String currentWorkspace;

    private String workspace;

    public WorkspaceManager() {
        try {
            File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
            File jarDir = jarFile.getParentFile();
            workspace = jarDir.getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCurrentWorkspace() {
        return currentWorkspace;
    }

    private static byte[] readAllBytes(InputStream in) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[8192];
        int n;
        while ((n = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, n);
        }
        return buffer.toByteArray();
    }

    public void createDirectories() {
        Path nestedDirPath = Paths.get(workspace, DEFAULT_WORKSPACE_NAME);

        try {
            if (Files.notExists(nestedDirPath)) {
                Files.createDirectories(nestedDirPath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        currentWorkspace = nestedDirPath.toString();
        DebugLog.debug(getClass(), "Workspace initialized at: " + currentWorkspace);
    }

    public void copyResourcesToWorkspace() {
        DebugLog.debug(getClass(), "Start copying resources to workspace...");

        try {
            Path jarPath = Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
            Path outputPath = Paths.get(currentWorkspace);

            if (Files.isDirectory(jarPath)) {
                Path resourceRoot = Paths.get("core/src/main/resources");
                if (Files.exists(resourceRoot)) {
                    DebugLog.debug(getClass(), copyDirectory(resourceRoot, outputPath));
                    DebugLog.debug(getClass(), "Resources copied from development directory.");
                } else {
                    DebugLog.warning(getClass(), "No resources directory found in development source path.");
                }
            } else {
                try (ZipInputStream zip = new ZipInputStream(Files.newInputStream(jarPath))) {
                    ZipEntry entry;
                    StringBuilder debugBuilder = new StringBuilder();

                    while ((entry = zip.getNextEntry()) != null) {
                        String name = entry.getName();

                        if (isTargetResource(name) && !entry.isDirectory()) {
                            Path path = outputPath.resolve(name);
                            Files.createDirectories(path.getParent());

                            byte[] jarBytes = readAllBytes(zip);

                            if (Files.exists(path)) {
                                byte[] existingBytes = Files.readAllBytes(path);
                                if (Arrays.equals(jarBytes, existingBytes)) {
                                    debugBuilder.append("\tSkipped (no change): ").append(name).append(" -> ").append(path).append("\n");
                                    continue;
                                }
                            }

                            Files.write(path, jarBytes);
                            debugBuilder.append("\tCopied: ").append(name).append(" -> ").append(path).append("\n");
                        }
                    }

                    DebugLog.debug(getClass(), debugBuilder.toString());
                    DebugLog.debug(getClass(), "Resources copied from JAR.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to copy resources to workspace", e);
        }
    }

    private boolean isTargetResource(String name) {
        return name.startsWith("shader/") || name.startsWith("icon/") || name.startsWith("texture/") || name.startsWith("font/");
    }

    private String copyDirectory(Path srcDir, Path dstDir) throws IOException {
        StringBuilder debugBuilder = new StringBuilder("\n");

        if (!Files.exists(srcDir)) {
            return debugBuilder.toString();
        }

        try (Stream<Path> paths = Files.walk(srcDir)) {
            paths.forEach(src -> {
                try {
                    Path relative = srcDir.relativize(src);
                    Path dest = dstDir.resolve(relative);

                    if (Files.isDirectory(src)) {
                        Files.createDirectories(dest);
                    } else {
                        if (Files.exists(dest)) {
                            byte[] srcBytes = Files.readAllBytes(src);
                            byte[] dstBytes = Files.readAllBytes(dest);
                            if (Arrays.equals(srcBytes, dstBytes)) {
                                debugBuilder.append("\tSkipped (no change): ").append(dest).append("\n");
                                return;
                            }
                        }

                        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                        debugBuilder.append(ANSIFormat.BLUE)
                                .append("\tCopied: ").append(src).append(" -> ").append(dest)
                                .append(ANSIFormat.RESET).append("\n");

                    }
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        }

        return debugBuilder.toString();
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }
}