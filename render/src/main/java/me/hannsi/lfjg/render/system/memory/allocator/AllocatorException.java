package me.hannsi.lfjg.render.system.memory.allocator;

public class AllocatorException extends RuntimeException {
    public AllocatorException(String message) {
        super(message);
    }

    public static class AllocatorOutOfMemoryException extends AllocatorException {
        public AllocatorOutOfMemoryException(String message) {
            super(message);
        }
    }
}
