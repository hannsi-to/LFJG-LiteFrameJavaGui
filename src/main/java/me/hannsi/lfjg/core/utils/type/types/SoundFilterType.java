package me.hannsi.lfjg.core.utils.type.types;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

import static org.lwjgl.openal.EXTEfx.*;

public enum SoundFilterType implements IEnumTypeBase {
    LOWPASS_GAIN(AL_LOWPASS_GAIN, "LowpassGain"),
    LOWPASS_GAINHF(AL_LOWPASS_GAINHF, "LowpassGainHF"),
    HIGHPASS_GAIN(AL_HIGHPASS_GAIN, "HighpassGain"),
    HIGHPASS_GAINLF(AL_HIGHPASS_GAINLF, "HighpassGainLF"),
    BANDPASS_GAIN(AL_BANDPASS_GAIN, "BandpassGain"),
    BANDPASS_GAINLF(AL_BANDPASS_GAINLF, "BandpassGainLF"),
    BANDPASS_GAINHF(AL_BANDPASS_GAINHF, "BandpassGainHF");

    final int id;
    final String name;

    SoundFilterType(int id, String name) {
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
