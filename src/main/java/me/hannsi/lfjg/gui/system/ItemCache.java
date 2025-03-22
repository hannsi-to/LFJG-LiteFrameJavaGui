package me.hannsi.lfjg.gui.system;

import me.hannsi.lfjg.debug.debug.DebugLevel;
import me.hannsi.lfjg.debug.debug.LogGenerator;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemCache {
    private List<ItemBase> itemCache;

    public ItemCache() {
        this.itemCache = new CopyOnWriteArrayList<>();
    }

    public void createCache(ItemBase itemBase) {
        itemCache.add(itemBase);

        LogGenerator logGenerator = new LogGenerator("ItemCache Debug Message", "Source: ItemCache", "Type: Cache Creation", "ID: " + itemBase.id(), "Severity: Info", "Message: Create item cache: " + itemBase.name());
        logGenerator.logging(DebugLevel.DEBUG);
    }

    public void draw() {
        for (ItemBase itemBase : itemCache) {
            itemBase.draw();
        }
    }

    public void cleanup() {
        for (ItemBase itemBase : itemCache) {
            itemBase.cleanup();
        }

        itemCache = null;

        LogGenerator logGenerator = new LogGenerator("ItemCache", "Source: ItemCache", "Type: Cleanup", "ID: " + this.hashCode(), "Severity: Debug", "Message: ItemCache cleanup is complete.");
        logGenerator.logging(DebugLevel.DEBUG);
    }
}
