package me.hannsi.lfjg.render.effect.system;

import lombok.Getter;
import lombok.Setter;
import me.hannsi.lfjg.debug.DebugLevel;
import me.hannsi.lfjg.debug.LogGenerateType;
import me.hannsi.lfjg.debug.LogGenerator;
import me.hannsi.lfjg.render.Id;
import me.hannsi.lfjg.render.renderers.GLObject;
import me.hannsi.lfjg.render.system.rendering.FrameBuffer;

import java.util.*;

/**
 * Class representing a cache for OpenGL effects.
 */
@Setter
@Getter
public class EffectCache {
    /**
     * -- GETTER --
     * Gets the effect bases.
     * <p>
     * <p>
     * -- SETTER --
     * Sets the effect bases.
     *
     * @return the effect bases
     * @param effectBases the effect bases
     */
    private LinkedHashMap<EffectBase, Identifier> effectBases;
    private FrameBuffer endFrameBuffer;

    /**
     * Constructs a new EffectCache.
     */
    EffectCache() {
        this.effectBases = new LinkedHashMap<>();

        this.endFrameBuffer = new FrameBuffer();
        this.endFrameBuffer.createFrameBuffer();
        this.endFrameBuffer.createShaderProgram();
    }

    public static EffectCache initEffectCache() {
        return new EffectCache();
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

    public EffectCache create(GLObject glObject) {
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
            effectBase.getKey().create(glObject);
        }

        glObject.setEffectCache(this);

        return this;
    }

    public EffectCache create(FrameBuffer frameBuffer) {
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
            effectBase.getKey().create(frameBuffer);
        }

        return this;
    }

    public void updateFrameBufferSize(FrameBuffer frameBuffer) {
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
            effectBase.getKey().getFrameBuffer().setX(frameBuffer.getX());
            effectBase.getKey().getFrameBuffer().setY(frameBuffer.getY());
            effectBase.getKey().getFrameBuffer().setWidth(frameBuffer.getWidth());
            effectBase.getKey().getFrameBuffer().setHeight(frameBuffer.getHeight());
        }
    }

    /**
     * Creates a cache for the specified effect and GL object.
     *
     * @param effectBase the effect to cache
     */
    public EffectCache createCache(String name, EffectBase effectBase) {
        this.effectBases.put(effectBase, new Identifier(name, Id.latestEffectCacheId++));

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                effectBase.getId(),
                ""
        ).logging(DebugLevel.DEBUG);

        return this;
    }

    public EffectCache createCache(String name, EffectBase effectBase, int index) {
        LinkedHashMap<EffectBase, Identifier> newEffectCache = new LinkedHashMap<>();

        int i = 0;
        for (Map.Entry<EffectBase, Identifier> effectBaseIdentifierEntry : effectBases.entrySet()) {
            if (i == index) {
                newEffectCache.put(effectBase, new Identifier(name, Id.latestEffectCacheId++));
            }

            newEffectCache.put(effectBaseIdentifierEntry.getKey(), effectBaseIdentifierEntry.getValue());

            i++;
        }

        this.effectBases = newEffectCache;

        new LogGenerator(
                LogGenerateType.CREATE_CACHE,
                getClass(),
                effectBase.getId(),
                ""
        ).logging(DebugLevel.DEBUG);

        return this;
    }

    /**
     * Pushes the transformations for all effects onto the stack for the specified GL object.
     *
     * @param glObject the GL object
     */
    public void push(GLObject glObject) {
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
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
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
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
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
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
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
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

        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
            applyEffect(index, maxSize, effectBase.getKey(), glObject);
            index++;
        }
    }

    /**
     * Applies the effect to the frame buffer for the specified GL object.
     *
     * @param index      the index of the effect
     * @param maxSize    the maximum size of the effect list
     * @param effectBase the effect to apply
     * @param glObject   the GL object
     */
    public void applyEffect(int index, int maxSize, EffectBase effectBase, GLObject glObject) {
        if (index == maxSize) {
            effectBase.getFrameBuffer().getShaderProgramFBO().bind();
            effectBase.setUniform(glObject);
            effectBase.getFrameBuffer().getShaderProgramFBO().unbind();

            endFrameBuffer.bindFrameBuffer();
            effectBase.frameBuffer(glObject);
            endFrameBuffer.unbindFrameBuffer();
        } else {
            Map.Entry<EffectBase, Identifier> nextEffectBase = getLinkedHashMapEntry(effectBases, index + 1);

            effectBase.getFrameBuffer().getShaderProgramFBO().bind();
            effectBase.setUniform(glObject);
            effectBase.getFrameBuffer().getShaderProgramFBO().unbind();

            nextEffectBase.getKey().frameBufferPush(glObject);
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

    public EffectBase getEffectBase(String name) {
        return effectBases.entrySet().stream().filter(entry -> entry.getValue().name().equals(name)).findFirst().map(Map.Entry::getKey).orElse(null);
    }

    /**
     * Cleans up the effect cache.
     */
    public void cleanup() {
        StringBuilder ids = new StringBuilder();
        for (Map.Entry<EffectBase, Identifier> entry : effectBases.entrySet()) {
            EffectBase effectBase = entry.getKey();
            Identifier id = entry.getValue();
            effectBase.cleanup();
            ids.append(id).append(", ");
        }
        endFrameBuffer.cleanup();
        effectBases.clear();

        new LogGenerator(
                LogGenerateType.CLEANUP,
                getClass(),
                ids.isEmpty() ? "" : ids.substring(0, ids.toString().length() - 2),
                ""
        ).logging(DebugLevel.DEBUG);
    }

    public record Identifier(String name, long id) {
    }
}