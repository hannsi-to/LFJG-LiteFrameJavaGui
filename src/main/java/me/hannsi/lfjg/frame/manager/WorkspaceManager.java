package me.hannsi.lfjg.frame.manager;

import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLog;
import me.hannsi.lfjg.utils.toolkit.ANSIFormat;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Setter
public class WorkspaceManager {
    public static final String DEFAULT_WORKSPACE_NAME = "lfjg/workspace";
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

    public void createDirectories() {
        Path nestedDirPath = Paths.get(workspace, DEFAULT_WORKSPACE_NAME);

        try {
            Files.createDirectories(nestedDirPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        currentWorkspace = nestedDirPath.toString(); // スラッシュ追加は不要
        DebugLog.debug(getClass(), "Workspace initialized at: " + currentWorkspace);
    }

    public void copyResourcesToWorkspace() {
        DebugLog.debug(getClass(), "Start copying resources to workspace...");

        try {
            Path jarPath = Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
            Path outputPath = Paths.get(currentWorkspace);

            if (Files.isDirectory(jarPath)) {
                Path resourceRoot = Paths.get("src/main/resources");
                if (Files.exists(resourceRoot)) {
                    DebugLog.debug(getClass(), copyDirectory(resourceRoot, outputPath));
                    DebugLog.debug(getClass(), "Resources copied from development directory.");
                } else {
                    DebugLog.warning(getClass(), "No resources directory found in development source path.");
                }
            } else {
                try (ZipInputStream zip = new ZipInputStream(Files.newInputStream(jarPath))) {
                    ZipEntry entry;
                    while ((entry = zip.getNextEntry()) != null) {
                        String name = entry.getName();
                        if (name.startsWith("resources/") && !entry.isDirectory()) {
                            Path path = outputPath.resolve(name.substring("resources/".length()));
                            Files.createDirectories(path.getParent());
                            Files.copy(zip, path, StandardCopyOption.REPLACE_EXISTING);
                            DebugLog.debug(getClass(), "Copied: " + name + " -> " + path);
                        }
                    }
                    DebugLog.debug(getClass(), "Resources copied from JAR.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to copy resources to workspace", e);
        }
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
}