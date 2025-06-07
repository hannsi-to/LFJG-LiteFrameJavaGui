package me.hannsi.lfjg.utils.type.types;

import me.hannsi.lfjg.utils.type.system.IEnumTypeBase;

import static org.lwjgl.openal.EXTEfx.*;

public enum SoundEffectType implements IEnumTypeBase {
    NULL(AL_EFFECT_NULL, "Null"),
    REVERB(AL_EFFECT_REVERB, "Reverb"),
    CHORUS(AL_EFFECT_CHORUS, "Chorus"),
    DISTORTION(AL_EFFECT_DISTORTION, "Distortion"),
    ECHO(AL_EFFECT_ECHO, "ECHO"),
    FLANGER(AL_EFFECT_FLANGER, "Flanger"),
    FREQUENCY_SHIFTER(AL_EFFECT_FREQUENCY_SHIFTER, "FrequencyShifter"),
    VOCAL_MORPHER(AL_EFFECT_VOCAL_MORPHER, "VocalMorpher"),
    PITCH_SHIFTER(AL_EFFECT_PITCH_SHIFTER, "PitchShifter"),
    RING_MODULATOR(AL_EFFECT_RING_MODULATOR, "RingModulator"),
    AUTOWAH(AL_EFFECT_AUTOWAH, "Autowah"),
    COMPRESSOR(AL_EFFECT_COMPRESSOR, "Compressor"),
    EQUALIZER(AL_EFFECT_EQUALIZER, "Equalizer"),
    EAXREVERB(AL_EFFECT_EAXREVERB, "Eaxreverb");

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
