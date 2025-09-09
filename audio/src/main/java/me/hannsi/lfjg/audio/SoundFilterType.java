package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import org.lwjgl.openal.EXTEfx;

public enum SoundFilterType implements IEnumTypeBase {
    LOWPASS_GAIN(EXTEfx.AL_LOWPASS_GAIN, "LowpassGain"),
    LOWPASS_GAINHF(EXTEfx.AL_LOWPASS_GAINHF, "LowpassGainHF"),
    HIGHPASS_GAIN(EXTEfx.AL_HIGHPASS_GAIN, "HighpassGain"),
    HIGHPASS_GAINLF(EXTEfx.AL_HIGHPASS_GAINLF, "HighpassGainLF"),
    BANDPASS_GAIN(EXTEfx.AL_BANDPASS_GAIN, "BandpassGain"),
    BANDPASS_GAINLF(EXTEfx.AL_BANDPASS_GAINLF, "BandpassGainLF"),
    BANDPASS_GAINHF(EXTEfx.AL_BANDPASS_GAINHF, "BandpassGainHF");

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
