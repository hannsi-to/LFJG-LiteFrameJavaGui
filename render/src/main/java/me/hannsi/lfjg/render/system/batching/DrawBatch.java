package me.hannsi.lfjg.render.system.batching;

import java.util.*;

public class DrawBatch {
    protected int index;
    protected Batch currentBatch;
    private final List<Batch> batches;

    public DrawBatch() {
        this.batches = new ArrayList<>();
    }

    private static Comparator<Command> commandComparator(DrawBatchComparable<DrawSortKey> sortComparable) {
        return (main, other) -> {
            int compare = sortComparable.compareTo(main.sortKey(), other.sortKey());
            if (compare != 0) {
                return compare;
            }

            return Integer.compare(main.commandOffset(), other.commandOffset());
        };
    }

    public void addCommand(int commandOffset, DrawSortKey sortKey) {
        Objects.requireNonNull(sortKey, "sortKey");

        if (batches.isEmpty() || !batches.getLast().canMerge(commandOffset, sortKey)) {
            batches.add(new Batch(commandOffset, sortKey));
        }

        batches.getLast().commandCount++;
    }

    public DrawBatch build(List<Command> commands, DrawBatchComparable<DrawSortKey> sortComparable) {
        Objects.requireNonNull(commands, "commands");
        Objects.requireNonNull(sortComparable, "sortComparable");

        clear();

        List<Command> sortedCommands = new ArrayList<>(commands);
        sortedCommands.sort(commandComparator(sortComparable));

        for (Command command : sortedCommands) {
            addCommand(command.commandOffset(), command.sortKey());
        }

        return this;
    }

    public DrawBatch build(List<Command> commands, Comparator<DrawSortKey> comparator) {
        Objects.requireNonNull(comparator, "comparator");
        return build(commands, (DrawBatchComparable<DrawSortKey>) comparator::compare);
    }

    public boolean nextBatch() {
        if (index >= batches.size()) {
            index = 0;
            currentBatch = null;
            return false;
        }

        currentBatch = batches.get(index++);
        return true;
    }

    public Batch getCurrentBatch() {
        return currentBatch;
    }

    public DrawBatch clear() {
        batches.clear();
        index = 0;
        currentBatch = null;
        return this;
    }

    public List<Batch> getBatches() {
        return Collections.unmodifiableList(batches);
    }

    public record Command(int commandOffset, DrawSortKey sortKey) {
        public Command {
            Objects.requireNonNull(sortKey, "sortKey");
        }
    }

    public static class Batch {
        public final int commandOffset;
        public final DrawSortKey sortKey;
        public int commandCount;

        public Batch(int commandOffset, DrawSortKey sortKey) {
            this.commandOffset = commandOffset;
            this.sortKey = sortKey;
            this.commandCount = 0;
        }

        public boolean canMerge(int nextCommandOffset, DrawSortKey nextSortKey) {
            return commandOffset + commandCount == nextCommandOffset && sortKey.hasSameBatchState(nextSortKey);
        }
    }
}
