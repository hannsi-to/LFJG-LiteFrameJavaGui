package me.hannsi.lfjg.render.openGL.effect.system;

import me.hannsi.lfjg.debug.debug.DebugLog;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.Id;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;

import java.util.*;

/**
 * Class representing a cache for OpenGL effects.
 */
public class EffectCache {
    public static long latestEffectCacheId;
    private LinkedHashMap<EffectBase, Long> effectBases;

    /**
     * Constructs a new EffectCache.
     */
    public EffectCache() {
        this.effectBases = new LinkedHashMap<>();
        latestEffectCacheId = Id.initialEffectCacheId;
    }

    /**
     * Sets a new LinkedHashMap with the specified value.
     *
     * @param originalMap the original LinkedHashMap
     * @param value       the value to set
     * @param <K>         the type of keys in the map
     * @param <V>         the type of values in the map
     * @return a new LinkedHashMap with the specified value
     */
    private static <K, V> LinkedHashMap<K, V> setHashMap(LinkedHashMap<K, V> originalMap, V value) {
        return new LinkedHashMap<>(originalMap);
    }

    /**
     * Gets the entry at the specified index from the LinkedHashMap.
     *
     * @param originalMap the original LinkedHashMap
     * @param index       the index of the entry to get
     * @param <K>         the type of keys in the map
     * @param <V>         the type of values in the map
     * @return the entry at the specified index
     */
    private static <K, V> Map.Entry<K, V> getLinkedHashMapEntry(LinkedHashMap<K, V> originalMap, int index) {
        List<Map.Entry<K, V>> entryList = new ArrayList<>(originalMap.entrySet());
        return entryList.get(index);
    }

    /**
     * Reverses the order of the entries in the LinkedHashMap.
     *
     * @param originalMap the original LinkedHashMap
     * @param <K>         the type of keys in the map
     * @param <V>         the type of values in the map
     * @return a new LinkedHashMap with the entries in reverse order
     */
    private static <K, V> LinkedHashMap<K, V> reverseLinkedHashMap(LinkedHashMap<K, V> originalMap) {
        List<Map.Entry<K, V>> entryList = new ArrayList<>(originalMap.entrySet());
        Collections.reverse(entryList);

        LinkedHashMap<K, V> reversedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : entryList) {
            reversedMap.put(entry.getKey(), entry.getValue());
        }

        return reversedMap;
    }

    /**
     * Creates a cache for the specified effect and GL object.
     *
     * @param effectBase the effect to cache
     * @param glObject   the GL object associated with the effect
     */
    public void createCache(EffectBase effectBase, GLObject glObject) {
        effectBase.getFrameBuffer().setUesStencil(true);
        effectBase.getFrameBuffer().setGlObject(glObject);
        this.effectBases.put(effectBase, latestEffectCacheId++);

        LogGenerator logGenerator = new LogGenerator("EffectCache Debug Message", "Source: EffectCache", "Type: Cache Creation", "ID: " + effectBase.getId(), "Severity: Info", "Message: Create effect cache: " + effectBase.getName() + " | Object name: " + glObject.getName());

        DebugLog.debug(getClass(), logGenerator.createLog());
    }

    /**
     * Pushes the transformations for all effects onto the stack for the specified GL object.
     *
     * @param glObject the GL object
     */
    public void push(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            effectBase.getKey().push(glObject);
        }

        effectBases = reverseLinkedHashMap(effectBases);
    }

    /**
     * Pops the transformations for all effects from the stack for the specified GL object.
     *
     * @param glObject the GL object
     */
    public void pop(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            effectBase.getKey().pop(glObject);
        }

        effectBases = reverseLinkedHashMap(effectBases);
    }

    /**
     * Pushes the frame buffer for all effects onto the stack for the specified GL object.
     *
     * @param glObject the GL object
     */
    public void frameBufferPush(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            effectBase.getKey().frameBufferPush(glObject);
        }

        effectBases = reverseLinkedHashMap(effectBases);
    }

    /**
     * Pops the frame buffer for all effects from the stack for the specified GL object.
     *
     * @param glObject the GL object
     */
    public void frameBufferPop(GLObject glObject) {
        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            effectBase.getKey().frameBufferPop(glObject);
        }

        effectBases = reverseLinkedHashMap(effectBases);
    }

    /**
     * Draws the frame buffer for all effects for the specified GL object.
     *
     * @param glObject the GL object
     */
    public void frameBuffer(GLObject glObject) {
        int index = 0;
        int maxSize = new ArrayList<>(effectBases.entrySet()).size() - 1;

        for (Map.Entry<EffectBase, Long> effectBase : effectBases.entrySet()) {
            FrameBuffer frameBuffer = effectBase.getKey().getFrameBuffer();
            applyEffect(frameBuffer, index, maxSize, effectBase.getKey(), glObject);
            index++;
        }
    }

    /**
     * Applies the effect to the frame buffer for the specified GL object.
     *
     * @param frameBuffer the frame buffer
     * @param index       the index of the effect
     * @param maxSize     the maximum size of the effect list
     * @param effectBase  the effect to apply
     * @param glObject    the GL object
     */
    public void applyEffect(FrameBuffer frameBuffer, int index, int maxSize, EffectBase effectBase, GLObject glObject) {
        if (index == maxSize || effectBase.getName().equals("FrameBufferContents")) {
            frameBuffer.getShaderProgramFBO().bind();
            effectBase.setUniform(glObject);
            frameBuffer.getShaderProgramFBO().unbind();
            effectBase.frameBuffer(glObject);
        }
        if (index != maxSize || effectBase.getName().equals("FrameBufferContents")) {
            Map.Entry<EffectBase, Long> nextEffectBase = getLinkedHashMapEntry(effectBases, index + 1);

            nextEffectBase.getKey().frameBufferPush(glObject);

            frameBuffer.getShaderProgramFBO().bind();
            effectBase.setUniform(glObject);
            frameBuffer.getShaderProgramFBO().unbind();
            effectBase.frameBuffer(glObject);

            nextEffectBase.getKey().frameBufferPop(glObject);
        }
    }

    /**
     * Gets the effect at the specified index.
     *
     * @param index the index of the effect
     * @return the effect at the specified index
     */
    public EffectBase getEffectBase(int index) {
        List<EffectBase> keys = new ArrayList<>(effectBases.keySet());
        return keys.get(index);
    }

    /**
     * Cleans up the effect cache.
     */
    public void cleanup() {
        effectBases.forEach((effectBase, id) -> effectBase.cleanup());
        effectBases.clear();
    }

    /**
     * Gets the effect bases.
     *
     * @return the effect bases
     */
    public LinkedHashMap<EffectBase, Long> getEffectBases() {
        return effectBases;
    }

    /**
     * Sets the effect bases.
     *
     * @param effectBases the effect bases
     */
    public void setEffectBases(LinkedHashMap<EffectBase, Long> effectBases) {
        this.effectBases = effectBases;
    }
}