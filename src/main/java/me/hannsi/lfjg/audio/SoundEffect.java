package me.hannsi.lfjg.audio;

import me.hannsi.lfjg.utils.type.types.SoundEffectType;

import static org.lwjgl.openal.AL11.alSource3i;
import static org.lwjgl.openal.EXTEfx.*;

public class SoundEffect {
    private final int effectId;
    private final int effectSlot;

    public SoundEffect() {
        this.effectId = alGenEffects();
        this.effectSlot = alGenAuxiliaryEffectSlots();
    }

    public SoundEffect addSoundEffect(SoundEffectType effectType) {
        alEffecti(effectId, AL_EFFECT_TYPE, effectType.getId());
        return this;
    }

    public void sendEffectSlot(int sourceId) {
        alAuxiliaryEffectSloti(effectSlot, AL_EFFECTSLOT_EFFECT, effectId);
        alSource3i(sourceId, AL_AUXILIARY_SEND_FILTER, effectSlot, 0, AL_FILTER_NULL);
    }
}
