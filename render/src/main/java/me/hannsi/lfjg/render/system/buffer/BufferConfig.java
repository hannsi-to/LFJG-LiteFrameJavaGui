package me.hannsi.lfjg.render.system.buffer;

import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;
import me.hannsi.lfjg.render.system.memory.Allocator;
import me.hannsi.lfjg.render.system.memory.AllocatorSystem;
import me.hannsi.lfjg.render.system.memory.GPUHeap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.opengl.GL30.GL_MAP_WRITE_BIT;
import static org.lwjgl.opengl.GL44.*;

public record BufferConfig(AllocationMode allocationMode, WriteConfig writeConfig, BufferProperty bufferProperty, boolean deviceLocal) {
    public BufferConfig {
        Objects.requireNonNull(allocationMode, "AllocationMode cannot be null.");
        Objects.requireNonNull(writeConfig, "WriteConfig cannot be null.");
        Objects.requireNonNull(bufferProperty, "BufferProperty cannot be null");

        List<WriteMode> modes = writeConfig.getWriteModes();
        if (modes.contains(WriteMode.PERSISTENT) && allocationMode != AllocationMode.BUFFER_STORAGE) {
            throw new IllegalArgumentException("WriteMode.PERSISTENT requires AllocationMode.BUFFER_STORAGE.");
        }
        if (modes.contains(WriteMode.PERSISTENT_COHERENT) && !modes.contains(WriteMode.PERSISTENT)) {
            throw new IllegalArgumentException("WriteMode.PERSISTENT_COHERENT requires WriteMode.PERSISTENT.");
        }
        if (deviceLocal) {
            if (modes.contains(WriteMode.MAP) || modes.contains(WriteMode.MAP_RANGE) || modes.contains(WriteMode.PERSISTENT)) {
                throw new IllegalArgumentException("DEVICE_LOCAL buffer cannot use MAP write modes directly.");
            }

            if (!modes.contains(WriteMode.BUFFER_SUB)) {
                throw new IllegalArgumentException("DEVICE_LOCAL buffer requires BUFFER_SUB for staged uploads.");
            }
        }
    }

    public enum AllocationMode implements IEnumTypeBase {
        BUFFER_DATA(0, "BufferData"),
        BUFFER_STORAGE(1, "BufferStorage");

        final int id;
        final String name;

        AllocationMode(int id, String name) {
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

    public enum BufferProperty implements IEnumTypeBase {
        BIND_BUFFER(0, "BindBuffer"),
        DIRECT_STATE_ACCESS(1, "DirectStateAccess"),
        NV_UNIFIED_BINDLESS(2, "NVUnifiedBindless");

        final int id;
        final String name;

        BufferProperty(int id, String name) {
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

    public enum WriteMode implements IEnumTypeBase {
        BUFFER_SUB(0, "BufferSub"),
        MAP(1, "Map"),
        MAP_RANGE(2, "MapRange"),
        PERSISTENT(3, "Persistent"),
        PERSISTENT_COHERENT(4, "PersistentCoherent");

        final int id;
        final String name;

        WriteMode(int id, String name) {
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

    public static class WriteConfig {
        private final List<WriteMode> writeModes;
        private final List<AllocatorSystem> allocatorSystems;
        private final List<Long> sizeBytes;
        private final List<Integer> alignments;

        private WriteConfig(List<WriteMode> writeModes, List<AllocatorSystem> allocatorSystems, List<Long> sizeBytes, List<Integer> alignments) {
            if (writeModes == null || writeModes.isEmpty()) {
                throw new IllegalArgumentException("At least one WriteMode must be specified.");
            }

            if (writeModes.contains(WriteMode.PERSISTENT) && !writeModes.contains(WriteMode.MAP_RANGE)) {
                throw new IllegalArgumentException("WriteMode.PERSISTENT requires WriteMode.MAP_RANGE.");
            }

            if (writeModes.contains(WriteMode.MAP) && writeModes.contains(WriteMode.MAP_RANGE)) {
                throw new IllegalArgumentException("Cannot use both MAP and MAP_RANGE. Choose one.");
            }

            this.writeModes = List.copyOf(writeModes);
            this.allocatorSystems = List.copyOf(allocatorSystems);
            this.sizeBytes = List.copyOf(sizeBytes);
            this.alignments = List.copyOf(alignments);
        }

        public static Builder builder() {
            return new Builder();
        }

        public int getStorageFlags(WriteMode writeMode) {
            int flags = 0;

            if (writeMode == WriteMode.BUFFER_SUB) {
                flags |= GL_DYNAMIC_STORAGE_BIT;
            }

            if (writeMode == WriteMode.MAP || writeMode == WriteMode.MAP_RANGE) {
                flags |= GL_MAP_WRITE_BIT;
            }

            if (writeMode == WriteMode.PERSISTENT) {
                flags |= GL_MAP_WRITE_BIT;
                flags |= GL_MAP_PERSISTENT_BIT;
            }

            if (writeMode == WriteMode.PERSISTENT || writeMode == WriteMode.PERSISTENT_COHERENT) {
                flags |= GL_MAP_WRITE_BIT;
                flags |= GL_MAP_PERSISTENT_BIT;
                if (writeMode == WriteMode.PERSISTENT_COHERENT) {
                    flags |= GL_MAP_COHERENT_BIT;
                }
            }

            return flags;
        }

        public int getMapFlags(WriteMode writeMode) {
            int flags = 0;
            if (writeMode == WriteMode.MAP || writeMode == WriteMode.MAP_RANGE || writeMode == WriteMode.PERSISTENT || writeMode == WriteMode.PERSISTENT_COHERENT) {
                flags |= GL_MAP_WRITE_BIT;
            }

            if (writeMode == WriteMode.PERSISTENT) {
                flags |= GL_MAP_PERSISTENT_BIT;
            }

            if (writeMode == WriteMode.PERSISTENT_COHERENT) {
                flags |= GL_MAP_PERSISTENT_BIT;
                flags |= GL_MAP_COHERENT_BIT;
            }

            if (writeMode == WriteMode.MAP_RANGE) {
                flags |= GL_MAP_INVALIDATE_RANGE_BIT;
            }

            return flags;
        }

        public List<WriteMode> getWriteModes() {
            return writeModes;
        }

        public List<AllocatorSystem> getAllocatorSystems() {
            return allocatorSystems;
        }

        public List<Long> getSizeBytes() {
            return sizeBytes;
        }

        public List<Integer> getAlignments() {
            return alignments;
        }

        public WriteMode getWriteMode(int index) {
            return getWriteModes().get(index);
        }

        public AllocatorSystem getAllocatorSystem(int index) {
            return getAllocatorSystems().get(index);
        }

        public long getSizeByte(int index) {
            return getSizeBytes().get(index);
        }

        public int getAlignment(int index) {
            return getAlignments().get(index);
        }

        public static class Builder {
            private final List<WriteMode> writeModes = new ArrayList<>();
            private final List<AllocatorSystem> allocatorSystems = new ArrayList<>();
            private final List<Long> sizeBytes = new ArrayList<>();
            private final List<Integer> alignments = new ArrayList<>();

            public Builder add(WriteMode mode, AllocatorSystem allocatorSystem) {
                Objects.requireNonNull(mode, "WriteMode cannot be null.");
                long size = allocatorSystem.getAllocatedMemorySize();
                if (size <= 0) {
                    throw new IllegalArgumentException("size <= 0");
                }
                int alignment = 0;
                for (Allocator allocator : allocatorSystem.getAllocators()) {
                    if (alignment < allocator.getAlignment()) {
                        alignment = allocator.getAlignment();
                    }
                }
                size = GPUHeap.align(size, alignment);

                writeModes.add(mode);
                allocatorSystems.add(allocatorSystem);
                sizeBytes.add(size);
                alignments.add(alignment);
                return this;
            }

            public WriteConfig build() {
                return new WriteConfig(writeModes, allocatorSystems, sizeBytes, alignments);
            }
        }
    }
}
