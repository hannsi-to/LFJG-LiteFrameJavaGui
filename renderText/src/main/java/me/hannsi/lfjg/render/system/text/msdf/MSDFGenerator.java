package me.hannsi.lfjg.render.system.text.msdf;

import me.hannsi.lfjg.core.debug.DebugLog;
import me.hannsi.lfjg.core.debug.LogGenerator;
import me.hannsi.lfjg.core.utils.math.MathHelper;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.toolkit.StringUtil;
import me.hannsi.lfjg.core.utils.toolkit.UnicodeBlock;
import me.hannsi.lfjg.core.utils.type.types.STBImageFormat;
import me.hannsi.lfjg.render.debug.exceptions.UnsupportedImageFileException;
import me.hannsi.lfjg.render.system.text.msdf.type.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class MSDFGenerator {
    protected final File executable;
    protected final boolean generateState;
    protected final List<String> command;
    protected File charsetFile = null;
    protected Location textureOutputLocation;
    protected Location jsonOutputLocation;

    private char[] chars;
    private Location ttfPath;
    private String outputName;

    MSDFGenerator() throws IOException {
        this.command = new ArrayList<>();
        this.generateState = false;

        String resourceName = getExecutableResourceName();
        String tempFilePrefix = "msdf-atlas-gen-";
        String tempFileSuffix = resourceName.endsWith(".exe") ? ".exe" : "";

        try (InputStream is = MSDFGenerator.class.getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new FileNotFoundException("FATAL: Cannot find executable resource in Jar: " + resourceName + "\n" + "Please ensure the executables are located in the 'resources/bin/' directory.");
            }

            this.executable = File.createTempFile(tempFilePrefix, tempFileSuffix);
            Files.copy(is, this.executable.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        if (!this.executable.canExecute()) {
            boolean ignore = this.executable.setExecutable(true);
        }
        this.executable.deleteOnExit();

        command.add(this.executable.getAbsolutePath());

        DebugLog.info(getClass(), "Extracted msdf-atlas-gen to temporary directory: " + this.executable.getAbsolutePath());
    }

    public void cleanup(){
        command.clear();
        charsetFile = null;
        textureOutputLocation = null;
        jsonOutputLocation = null;
    }

    public static MSDFGenerator createMSDFGenerator() throws IOException {
        return new MSDFGenerator();
    }

    public MSDFGenerator ttfPath(Location ttfPath) {
        this.ttfPath = ttfPath;

        switch (ttfPath.locationType()) {
            case RESOURCE:
                break;
            case FILE:
                break;
            case URL:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + ttfPath.locationType());
        }

        command.add("-font");
        command.add(ttfPath.path());
        return this;
    }

    public MSDFGenerator outputName(String outputName) {
        this.outputName = outputName;

        String pngFile = outputName + ".png";
        String jsonFile = outputName + ".json";

        File outDir = new File(outputName).getParentFile();
        if (outDir != null && !outDir.exists()) {
            if (outDir.mkdirs()) {
                DebugLog.info(getClass(), "Created output directory: " + outDir.getAbsolutePath());
            } else {
                DebugLog.warning(getClass(), "Failed to create output directory: " + outDir.getAbsolutePath());
            }
        }

        command.add("-imageout");
        command.add(pngFile);

        command.add("-json");
        command.add(jsonFile);

        return this;
    }

    public MSDFGenerator unicodeBlock(List<UnicodeBlock> unicodeBlocks) {
        StringBuilder charsetBuilder = new StringBuilder();

        for (UnicodeBlock unicodeBlock : unicodeBlocks) {
            for (int codePoint = unicodeBlock.startCodePoint(); codePoint <= unicodeBlock.endCodePoint(); codePoint++) {
                if (!Character.isValidCodePoint(codePoint) || !Character.isDefined(codePoint)) {
                    continue;
                }
                charsetBuilder.append(Character.toChars(codePoint));
            }
        }

        String charset = charsetBuilder.toString();
        chars = charset.toCharArray();
        try {
            charsetFile = File.createTempFile("charset-", ".txt");

            try (BufferedWriter writer = Files.newBufferedWriter(charsetFile.toPath(), StandardCharsets.US_ASCII)) {
                int offset = 0;
                while (offset < charset.length()) {
                    int codePoint = charset.codePointAt(offset);
                    writer.write(String.format("0x%x", codePoint));
                    writer.newLine();
                    offset += Character.charCount(codePoint);
                }
            } catch (IOException e) {
                DebugLog.error(getClass(), e);
                return this;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (charsetFile == null) {
            return this;
        }

        DebugLog.debug(getClass(), "Charset file: " + charsetFile.getAbsolutePath());

        command.add("-charset");
        command.add(charsetFile.getAbsolutePath());
        return this;
    }

    public MSDFGenerator allGlyphs(boolean state) {
        if (state) {
            command.add("-allglyphs");
        }

        return this;
    }

    public MSDFGenerator fontScale(int scale) {
        command.add("-fontScale");
        command.add(String.valueOf(scale));

        return this;
    }

    public MSDFGenerator and(boolean state) {
        if (state) {
            command.add("-and");
        }

        return this;
    }

    public MSDFGenerator type(AtlasType atlasType) {
        command.add("-type");
        command.add(atlasType.getName());

        return this;
    }

    public MSDFGenerator format(STBImageFormat stbImageFormat) {
        switch (stbImageFormat) {
            case PNG:
            case BMP:
                command.add("-format");
                command.add(stbImageFormat.getName());
                break;
            case JPG:
            case TGA:
                throw new UnsupportedImageFileException("Unsupported image file: " + stbImageFormat.getName());
            default:
                throw new IllegalStateException("Unexpected value: " + stbImageFormat);
        }

        return this;
    }

    public MSDFGenerator atlasSize(AtlasSizeType atlasSizeType, boolean state) {
        if (state) {
            command.add("-" + atlasSizeType.getName());
        }

        return this;
    }

    public MSDFGenerator uniformGrid(boolean state) {
        if (state) {
            command.add("-uniformgrid");
        }

        return this;
    }

    public MSDFGenerator uniformCols(int uniformCols) {
        command.add("-uniformcols");
        command.add(String.valueOf(uniformCols));

        return this;
    }

    public MSDFGenerator uniformCell(int width, int height) {
        command.add("-uniformcell");
        command.add(String.valueOf(width));
        command.add(String.valueOf(height));

        return this;
    }

    public MSDFGenerator uniformCellConstraint(AtlasSizeType atlasSizeType) {
        command.add("-uniformcellconstraint");
        command.add(atlasSizeType.getName());

        return this;
    }

    public MSDFGenerator uniformOrigin(AtlasUnifomOrignType atlasUnifomOrignType) {
        command.add("-uniformorigin");
        command.add(atlasUnifomOrignType.getName());

        return this;
    }

    public MSDFGenerator yOrigin(AtlasYOriginType atlasYOriginType) {
        command.add("-yorigin");
        command.add(atlasYOriginType.getName());

        return this;
    }

    public MSDFGenerator size(int size) {
        command.add("-size");
        command.add(String.valueOf(size));

        return this;
    }

    public MSDFGenerator minSize(int minSize) {
        command.add("-minsize");
        command.add(String.valueOf(minSize));

        return this;
    }

    public MSDFGenerator emRange(int emRange) {
        command.add("-emrange");
        command.add(String.valueOf(emRange));

        return this;
    }

    public MSDFGenerator pxRange(int pxRange) {
        command.add("-pmrange");
        command.add(String.valueOf(pxRange));

        return this;
    }

    public MSDFGenerator aemRange(int aemRange) {
        command.add("-aemrange");
        command.add(String.valueOf(aemRange));

        return this;
    }

    public MSDFGenerator apxRange(int apxRange) {
        command.add("-apxrange");
        command.add(String.valueOf(apxRange));

        return this;
    }

    public MSDFGenerator pxAlign(GlyphPXAlign glyphPXAlign) {
        command.add("-pxalign");
        command.add(glyphPXAlign.getName());

        return this;
    }

    public MSDFGenerator noKerning(boolean state) {
        if (state) {
            command.add("-nokerning");
        }

        return this;
    }

    public MSDFGenerator charset(String charset) {
        chars = charset.toCharArray();

        try {
            charsetFile = File.createTempFile("charset-", ".txt");

            try (BufferedWriter writer = Files.newBufferedWriter(charsetFile.toPath(), StandardCharsets.US_ASCII)) {
                int offset = 0;
                while (offset < charset.length()) {
                    int codePoint = charset.codePointAt(offset);
                    writer.write(String.format("0x%x", codePoint));
                    writer.newLine();
                    offset += Character.charCount(codePoint);
                }
            } catch (IOException e) {
                DebugLog.error(getClass(), e);
                return this;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (charsetFile == null) {
            return this;
        }

        DebugLog.debug(getClass(), "Charset file: " + charsetFile.getAbsolutePath());

        command.add("-charset");
        command.add(charsetFile.getAbsolutePath());
        return this;
    }

    public MSDFGenerator textureSize(int textureSize) {
        command.add("-dimensions");
        command.add(String.valueOf(textureSize));
        command.add(String.valueOf(textureSize));
        return this;
    }

    private String getExecutableResourceName() {
        String os = System.getProperty("os.name").toLowerCase();
        String resourcePath = "/bin/";

        if (os.contains("win")) {
            return resourcePath + "msdf-atlas-gen-win.exe";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return resourcePath + "msdf-atlas-gen-linux";
        } else if (os.contains("mac")) {
            return resourcePath + "msdf-atlas-gen-macos";
        }
        throw new UnsupportedOperationException("Unsupported Operating System: " + os);
    }

    public boolean generate() {
        DebugLog.info(getClass(), "Starting SDF font generation (for v1.3.0)...");
        DebugLog.debug(getClass(), "Font: " + ttfPath.path());

        File ttfFile = ttfPath.getFile();
        if (!ttfFile.exists()) {
            DebugLog.error(getClass(), "Input font file not found at: " + ttfPath.path());
            return false;
        }

        try {
            DebugLog.info(getClass(), "Executing msdf-atlas-gen...");
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);

            Process process = pb.start();

            DebugLog.debug(getClass(), StringUtil.repeat("-", MathHelper.max(0, LogGenerator.barCount)) + " " + "msdf-atlas-gen log start" + " " + StringUtil.repeat("-", MathHelper.max(0, LogGenerator.barCount)));

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    DebugLog.debug(getClass(), "\t\t" + line);
                }
            }

            DebugLog.debug(getClass(), StringUtil.repeat("-", MathHelper.max(0, LogGenerator.barCount)) + " " + "msdf-atlas-gen log end" + " " + StringUtil.repeat("-", MathHelper.max(0, LogGenerator.barCount)));
            int exitCode = process.waitFor();

            File pngFile = new File(outputName + ".png");
            File jsonFile = new File(outputName + ".json");

            boolean success = exitCode == 0 && pngFile.exists() && pngFile.length() > 0 && jsonFile.exists() && jsonFile.length() > 0;

            if (success) {
                DebugLog.debug(getClass(), "SUCCESS: SDF font atlas generated successfully!");
                DebugLog.debug(getClass(), "PNG Output: " + pngFile.getAbsolutePath());
                DebugLog.debug(getClass(), "JSON Output: " + jsonFile.getAbsolutePath());

                textureOutputLocation = Location.fromFile(pngFile.getAbsolutePath());
                jsonOutputLocation = Location.fromFile(jsonFile.getAbsolutePath());
                return true;
            } else {
                DebugLog.error(getClass(), "SDF font generation failed.");
                DebugLog.error(getClass(), "Process exit code: " + exitCode + (exitCode == 0 ? " (Warning: process exited with 0 but output files are missing or empty)" : ""));
                if (!pngFile.exists() || pngFile.length() == 0) {
                    DebugLog.error(getClass(), "Verification failed: PNG file was not created or is empty.");
                }
                if (!jsonFile.exists() || jsonFile.length() == 0) {
                    DebugLog.error(getClass(), "Verification failed: JSON file was not created or is empty.");
                }
                DebugLog.error(getClass(), "Troubleshooting: Check the [msdf-atlas-gen log] above for specific errors from the tool.");
                return false;
            }

        } catch (IOException | InterruptedException e) {
            DebugLog.error(getClass(), "An exception occurred while running the process.");
            DebugLog.error(getClass(), e.toString());
            return false;
        } finally {
            if (charsetFile != null) {
                boolean ignore = charsetFile.delete();
            }
        }
    }

    public Location getTextureOutputLocation() {
        return textureOutputLocation;
    }

    public Location getJsonOutputLocation() {
        return jsonOutputLocation;
    }

    public Location getTtfPath() {
        return ttfPath;
    }

    public String getOutputName() {
        return outputName;
    }

    public char[] getChars() {
        return chars;
    }
}