package me.hannsi.lfjg.render.openGL.effect.system;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;
import me.hannsi.lfjg.render.openGL.renderers.GLObject;
import me.hannsi.lfjg.render.openGL.system.Id;
import me.hannsi.lfjg.render.openGL.system.rendering.FrameBuffer;

import java.util.*;

/**
 * Class representing a cache for OpenGL effects.
 */
public class EffectCache {
    private LinkedHashMap<EffectBase, Identifier> effectBases;
    private FrameBuffer endFrameBuffer;

    /**
     * Constructs a new EffectCache.
     */
    public EffectCache() {
        this.effectBases = new LinkedHashMap<>();

        this.endFrameBuffer = new FrameBuffer();
        this.endFrameBuffer.createFrameBuffer();
        this.endFrameBuffer.createShaderProgram();
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

    public void create(GLObject glObject) {
        for (Map.Entry<EffectBase, Identifier> effectBase : effectBases.entrySet()) {
            effectBase.getKey().create(glObject);
        }
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
    public void createCache(String name, EffectBase effectBase) {
        this.effectBases.put(effectBase, new Identifier(name, Id.latestEffectCacheId++));

        LogGenerator logGenerator = new LogGenerator("EffectCache Debug Message", "Source: EffectCache", "Type: Cache Creation", "ID: " + effectBase.getId(), "Severity: Info", "Message: Create effect cache: " + effectBase.getName());
        logGenerator.logging(DebugLevel.DEBUG);
    }

    public void createCache(String name, EffectBase effectBase, int index) {
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

        LogGenerator logGenerator = new LogGenerator("EffectCache Debug Message", "Source: EffectCache", "Type: Cache Creation", "ID: " + effectBase.getId(), "Severity: Info", "Message: Create effect cache: " + effectBase.getName());
        logGenerator.logging(DebugLevel.DEBUG);
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
        if (index == maxSize || effectBase.getName().equals("FrameBufferContents")) {
            effectBase.getFrameBuffer().getShaderProgramFBO().bind();
            effectBase.setUniform(glObject);
            effectBase.getFrameBuffer().getShaderProgramFBO().unbind();

            endFrameBuffer.bindFrameBuffer();
            effectBase.frameBuffer(glObject);
            endFrameBuffer.unbindFrameBuffer();
        }
        if (index != maxSize || effectBase.getName().equals("FrameBufferContents")) {
            Map.Entry<EffectBase, Identifier> nextEffectBase = getLinkedHashMapEntry(effectBases, index + 1);

            nextEffectBase.getKey().frameBufferPush(glObject);

            effectBase.getFrameBuffer().getShaderProgramFBO().bind();
            effectBase.setUniform(glObject);
            effectBase.getFrameBuffer().getShaderProgramFBO().unbind();
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
        effectBases.forEach((effectBase, id) -> effectBase.cleanup());
        effectBases.clear();
        endFrameBuffer.cleanup();

        LogGenerator logGenerator = new LogGenerator("EffectCache", "Source: EffectCache", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: EffectCache cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }

    /**
     * Gets the effect bases.
     *
     * @return the effect bases
     */
    public LinkedHashMap<EffectBase, Identifier> getEffectBases() {
        return effectBases;
    }

    /**
     * Sets the effect bases.
     *
     * @param effectBases the effect bases
     */
    public void setEffectBases(LinkedHashMap<EffectBase, Identifier> effectBases) {
        this.effectBases = effectBases;
    }

    public FrameBuffer getEndFrameBuffer() {
        return endFrameBuffer;
    }

    public void setEndFrameBuffer(FrameBuffer endFrameBuffer) {
        this.endFrameBuffer = endFrameBuffer;
    }

    public record Identifier(String name, long id) {
    }
}