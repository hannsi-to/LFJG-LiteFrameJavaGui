package me.hannsi.lfjg.render.system.text.msdf;

import com.google.gson.Gson;
import me.hannsi.lfjg.core.utils.reflection.location.Location;

import java.io.IOException;
import java.nio.file.Files;

public class MSDFJsonLoader {
    private final Gson gson;
    private Location jsonLocation;

    MSDFJsonLoader() {
        this.gson = new Gson();
    }

    public static MSDFJsonLoader createMSDFJsonLoader() {
        return new MSDFJsonLoader();
    }

    public MSDFFont parseFile() throws IOException {
        String jsonString = new String(Files.readAllBytes(jsonLocation.getFile().toPath()));
        return parseJson(jsonString);
    }

    public MSDFJsonLoader jsonLocation(Location jsonLocation) {
        this.jsonLocation = jsonLocation;

        return this;
    }

    public MSDFFont parseJson(String json) {
        return gson.fromJson(json, MSDFFont.class);
    }
}
