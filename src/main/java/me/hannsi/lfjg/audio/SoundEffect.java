package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.utils.type.types.SoundEffectType;
import me.hannsi.lfjg.utils.type.types.SoundFilterType;

import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.openal.AL11.alSource3i;
import static org.lwjgl.openal.EXTEfx.*;

public class SoundEffect {
    private int effectId;
    private int effectSlot;
    private int filterId;

    SoundEffect() {
        this.effectId = AL_EFFECT_NULL;
        this.effectSlot = AL_EFFECTSLOT_NULL;
        this.filterId = AL_FILTER_NULL;
    }

    public static SoundEffect builderCreate() {
        return new SoundEffect();
    }

    public SoundEffect initEffect() {
        this.effectId = alGenEffects();
        this.effectSlot = alGenAuxiliaryEffectSlots();
        return this;
    }

    public SoundEffect initFilter() {
        this.filterId = alGenFilters();
        alFilteri(filterId, AL_FILTER_TYPE, AL_FILTER_LOWPASS);
        return this;
    }

    public SoundEffect addSoundFilter(SoundFilterType filterType, float value) {
        alFilterf(filterId, filterType.getId(), value);
        return this;
    }

    public SoundEffect addSoundEffect(SoundEffectType effectType) {
        alEffecti(effectId, AL_EFFECT_TYPE, effectType.getId());
        return this;
    }

    public void sendEffectSlot(int sourceId) {
        alAuxiliaryEffectSloti(effectSlot, AL_EFFECTSLOT_EFFECT, effectId);
        alSourcei(sourceId, AL_DIRECT_FILTER, filterId);
        alSource3i(sourceId, AL_AUXILIARY_SEND_FILTER, effectSlot, 0, filterId);
    }

    public void cleanup() {
        String ids = "ID: ";
        if (effectId != AL_EFFECT_NULL) {
            alDeleteEffects(effectId);
            ids = ids + "EffectId: " + effectId + " | ";
        }
        if (effectSlot != AL_EFFECTSLOT_NULL) {
            alDeleteAuxiliaryEffectSlots(effectSlot);
            ids = ids + "EffectSlot: " + effectSlot + " | ";
        }
        if (filterId != AL_FILTER_NULL) {
            alDeleteFilters(filterId);
            ids = ids + "FilterId: " + filterId + " | ";
        }

        new LogGenerator(
                "SoundEffect",
                "Source: " + getClass().getSimpleName(),
                "Type: Cleanup",
                ids,
                "Severity: Debug",
                "Message: " + getClass().getSimpleName() + " cleanup is complete."
        ).logging(DebugLevel.DEBUG);
    }
}
