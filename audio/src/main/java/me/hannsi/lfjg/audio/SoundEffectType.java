package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import org.lwjgl.openal.EXTEfx;

public enum SoundEffectType implements IEnumTypeBase {
    NULL(EXTEfx.AL_EFFECT_NULL, "Null"),
    REVERB(EXTEfx.AL_EFFECT_REVERB, "Reverb"),
    CHORUS(EXTEfx.AL_EFFECT_CHORUS, "Chorus"),
    DISTORTION(EXTEfx.AL_EFFECT_DISTORTION, "Distortion"),
    ECHO(EXTEfx.AL_EFFECT_ECHO, "ECHO"),
    FLANGER(EXTEfx.AL_EFFECT_FLANGER, "Flanger"),
    FREQUENCY_SHIFTER(EXTEfx.AL_EFFECT_FREQUENCY_SHIFTER, "FrequencyShifter"),
    VOCAL_MORPHER(EXTEfx.AL_EFFECT_VOCAL_MORPHER, "VocalMorpher"),
    PITCH_SHIFTER(EXTEfx.AL_EFFECT_PITCH_SHIFTER, "PitchShifter"),
    RING_MODULATOR(EXTEfx.AL_EFFECT_RING_MODULATOR, "RingModulator"),
    AUTOWAH(EXTEfx.AL_EFFECT_AUTOWAH, "Autowah"),
    COMPRESSOR(EXTEfx.AL_EFFECT_COMPRESSOR, "Compressor"),
    EQUALIZER(EXTEfx.AL_EFFECT_EQUALIZER, "Equalizer"),
    EAXREVERB(EXTEfx.AL_EFFECT_EAXREVERB, "Eaxreverb");

    final int id;
    final String name;

    SoundEffectType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
