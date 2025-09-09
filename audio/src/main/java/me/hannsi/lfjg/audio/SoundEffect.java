package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.core.debug.DebugLevel;
import me.hannsi.lfjg.core.debug.LogGenerateType;
import me.hannsi.lfjg.core.debug.LogGenerator;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.EXTEfx;

public class SoundEffect {
    private int effectId;
    private int effectSlot;
    private int filterId;

    SoundEffect() {
        this.effectId = EXTEfx.AL_EFFECT_NULL;
        this.effectSlot = EXTEfx.AL_EFFECTSLOT_NULL;
        this.filterId = EXTEfx.AL_FILTER_NULL;
    }

    public static SoundEffect builderCreate() {
        return new SoundEffect();
    }

    public SoundEffect createEffect() {
        this.effectId = EXTEfx.alGenEffects();
        this.effectSlot = EXTEfx.alGenAuxiliaryEffectSlots();
        return this;
    }

    public SoundEffect createFilter() {
        this.filterId = EXTEfx.alGenFilters();
        EXTEfx.alFilteri(filterId, EXTEfx.AL_FILTER_TYPE, EXTEfx.AL_FILTER_LOWPASS);
        return this;
    }

    public SoundEffect addSoundFilter(SoundFilterType filterType, float value) {
        EXTEfx.alFilterf(filterId, filterType.getId(), value);
        return this;
    }

    public SoundEffect addSoundEffect(SoundEffectType effectType) {
        EXTEfx.alEffecti(effectId, EXTEfx.AL_EFFECT_TYPE, effectType.getId());
        return this;
    }

    public void sendEffectSlot(int sourceId) {
        EXTEfx.alAuxiliaryEffectSloti(effectSlot, EXTEfx.AL_EFFECTSLOT_EFFECT, effectId);
        AL10.alSourcei(sourceId, EXTEfx.AL_DIRECT_FILTER, filterId);
        AL11.alSource3i(sourceId, EXTEfx.AL_AUXILIARY_SEND_FILTER, effectSlot, 0, filterId);
    }

    public void cleanup() {
        String ids = "";
        if (effectId != EXTEfx.AL_EFFECT_NULL) {
            EXTEfx.alDeleteEffects(effectId);
            ids = ids + "EffectId: " + effectId + " | ";
        }
        if (effectSlot != EXTEfx.AL_EFFECTSLOT_NULL) {
            EXTEfx.alDeleteAuxiliaryEffectSlots(effectSlot);
            ids = ids + "EffectSlot: " + effectSlot + " | ";
        }
        if (filterId != EXTEfx.AL_FILTER_NULL) {
            EXTEfx.alDeleteFilters(filterId);
            ids = ids + "FilterId: " + filterId;
        }

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids,
                ""
        ).logging(DebugLevel.DEBUG);
    }
}
